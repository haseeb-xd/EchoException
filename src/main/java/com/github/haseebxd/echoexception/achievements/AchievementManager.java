package com.github.haseebxd.echoexception.achievements;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.Service;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Manages achievements and tracks exception counts for users.
 * This service persists achievement data and provides methods to unlock achievements.
 */
@Service(Service.Level.APP)
@State(
        name = "EchoExceptionAchievements",
        storages = {@Storage("EchoExceptionAchievements.xml")}
)
public final class AchievementManager implements PersistentStateComponent<AchievementManager.State> {
    private static final Logger LOG = Logger.getInstance(AchievementManager.class);
    
    private State state = new State();
    private final Map<String, Achievement> achievements = new ConcurrentHashMap<>();
    private Map<String, Integer> exceptionCounts = new ConcurrentHashMap<>();

    public static class State {
        private Map<String, Integer> exceptionCounts = new HashMap<>();
        private Map<String, Boolean> unlockedAchievements = new HashMap<>();
        private Map<String, Long> achievementUnlockTimes = new HashMap<>();

        public Map<String, Integer> getExceptionCounts() { return exceptionCounts; }
        public void setExceptionCounts(Map<String, Integer> exceptionCounts) { this.exceptionCounts = exceptionCounts; }
        
        public Map<String, Boolean> getUnlockedAchievements() { return unlockedAchievements; }
        public void setUnlockedAchievements(Map<String, Boolean> unlockedAchievements) { this.unlockedAchievements = unlockedAchievements; }
        
        public Map<String, Long> getAchievementUnlockTimes() { return achievementUnlockTimes; }
        public void setAchievementUnlockTimes(Map<String, Long> achievementUnlockTimes) { this.achievementUnlockTimes = achievementUnlockTimes; }
    }

    public AchievementManager() {
        initializeAchievements();
    }


    @Override
    public @NotNull State getState() {
        return state;
    }

    @Override
    public void loadState(@NotNull State state) {
        this.state = state;
        this.exceptionCounts = new ConcurrentHashMap<>(state.getExceptionCounts());
        
        // Restore achievement unlock states
        for (Achievement achievement : achievements.values()) {
            Boolean unlocked = state.getUnlockedAchievements().get(achievement.getId());
            if (unlocked != null) {
                achievement.setUnlocked(unlocked);
                Long unlockTime = state.getAchievementUnlockTimes().get(achievement.getId());
                if (unlockTime != null) {
                    achievement.setUnlockedAt(unlockTime);
                }
            }
        }
    }

    public static AchievementManager getInstance() {
        return ApplicationManager.getApplication().getService(AchievementManager.class);
    }

    /**
     * Record an exception occurrence and check for new achievements
     */
    public void recordException(Class<? extends Throwable> exceptionClass) {
        String exceptionName = exceptionClass.getSimpleName();
        int newCount = exceptionCounts.merge(exceptionName, 1, Integer::sum);
        
        // Calculate total count from all individual exceptions
        int totalCount = exceptionCounts.entrySet().stream()
                .filter(entry -> !entry.getKey().equals("TOTAL"))
                .mapToInt(Map.Entry::getValue)
                .sum();
        
        // Update total count
        exceptionCounts.put("TOTAL", totalCount);
        
        LOG.info("EchoException: Recorded exception " + exceptionName + " (count: " + newCount + ", total: " + totalCount + ")");
        
        // Check for new achievements
        checkAchievements(exceptionName, newCount);
        checkAchievements("TOTAL", totalCount);
        
        // Save state
        saveState();
    }

    /**
     * Get the count of a specific exception type
     */
    public int getExceptionCount(String exceptionName) {
        return exceptionCounts.getOrDefault(exceptionName, 0);
    }

    /**
     * Get all exception counts
     */
    public Map<String, Integer> getAllExceptionCounts() {
        return new HashMap<>(exceptionCounts);
    }

    /**
     * Get all achievements
     */
    public List<Achievement> getAllAchievements() {
        return new ArrayList<>(achievements.values());
    }

    /**
     * Get unlocked achievements
     */
    public List<Achievement> getUnlockedAchievements() {
        return achievements.values().stream()
                .filter(Achievement::isUnlocked)
                .sorted(Comparator.comparing(Achievement::getUnlockedAt).reversed())
                .toList();
    }

    /**
     * Get locked achievements
     */
    public List<Achievement> getLockedAchievements() {
        return achievements.values().stream()
                .filter(achievement -> !achievement.isUnlocked())
                .sorted(Comparator.comparing(Achievement::getRequiredCount))
                .toList();
    }

    /**
     * Get achievement progress for a specific exception
     */
    public AchievementProgress getAchievementProgress(String exceptionName) {
        int currentCount = getExceptionCount(exceptionName);
        List<Achievement> relevantAchievements = achievements.values().stream()
                .filter(a -> a.getExceptionType().equals(exceptionName))
                .sorted(Comparator.comparing(Achievement::getRequiredCount))
                .toList();
        
        return new AchievementProgress(exceptionName, currentCount, relevantAchievements);
    }

    private void checkAchievements(String exceptionName, int count) {
        for (Achievement achievement : achievements.values()) {
            if (achievement.getExceptionType().equals(exceptionName) && 
                count >= achievement.getRequiredCount() && 
                !achievement.isUnlocked()) {
                
                unlockAchievement(achievement);
            }
        }
    }

    private void unlockAchievement(Achievement achievement) {
        achievement.setUnlocked(true);
        LOG.info("EchoException: Achievement unlocked: " + achievement.getName());
        
        // Update state
        state.getUnlockedAchievements().put(achievement.getId(), true);
        state.getAchievementUnlockTimes().put(achievement.getId(), achievement.getUnlockedAt());
    }

    private void saveState() {
        state.setExceptionCounts(new HashMap<>(exceptionCounts));
    }

    private void initializeAchievements() {
        // NullPointerException achievements
        addAchievement("npe_first", "Null and Void", "Your first NullPointerException!", "💀", 
                      AchievementType.BRONZE, 1, "NullPointerException");
        addAchievement("npe_10", "Null Master", "10 NullPointerExceptions - you're getting good at this!", "👻", 
                      AchievementType.SILVER, 10, "NullPointerException");
        addAchievement("npe_50", "Null Legend", "50 NullPointerExceptions - truly legendary!", "👑", 
                      AchievementType.GOLD, 50, "NullPointerException");
        addAchievement("npe_100", "Null God", "100 NullPointerExceptions - you are the chosen one!", "🏆", 
                      AchievementType.PLATINUM, 100, "NullPointerException");

        // ArrayIndexOutOfBoundsException achievements
        addAchievement("aioob_first", "Out of Bounds", "Your first array adventure!", "🎯", 
                      AchievementType.BRONZE, 1, "ArrayIndexOutOfBoundsException");
        addAchievement("aioob_10", "Array Explorer", "10 array adventures - keep exploring!", "🗺️", 
                      AchievementType.SILVER, 10, "ArrayIndexOutOfBoundsException");
        addAchievement("aioob_50", "Array Master", "50 array adventures - you know no bounds!", "🚀", 
                      AchievementType.GOLD, 50, "ArrayIndexOutOfBoundsException");

        // ArithmeticException achievements
        addAchievement("math_first", "Math Wizard", "Your first division by zero!", "🧮", 
                      AchievementType.BRONZE, 1, "ArithmeticException");
        addAchievement("math_10", "Number Cruncher", "10 math mishaps - you're on fire!", "🔥", 
                      AchievementType.SILVER, 10, "ArithmeticException");
        addAchievement("math_50", "Math Destroyer", "50 math mishaps - you've broken mathematics!", "💥", 
                      AchievementType.GOLD, 50, "ArithmeticException");

        // NumberFormatException achievements
        addAchievement("format_first", "String Breaker", "Your first parsing adventure!", "📝", 
                      AchievementType.BRONZE, 1, "NumberFormatException");
        addAchievement("format_10", "Parser Pro", "10 parsing adventures - you're unstoppable!", "⚡", 
                      AchievementType.SILVER, 10, "NumberFormatException");

        // IllegalArgumentException achievements
        addAchievement("illegal_first", "Rule Breaker", "Your first illegal argument!", "🚫", 
                      AchievementType.BRONZE, 1, "IllegalArgumentException");
        addAchievement("illegal_10", "Law Breaker", "10 illegal arguments - you're a rebel!", "🕶️", 
                      AchievementType.SILVER, 10, "IllegalArgumentException");

        // IOException achievements
        addAchievement("io_first", "File Finder", "Your first file adventure!", "📁", 
                      AchievementType.BRONZE, 1, "IOException");
        addAchievement("io_10", "File Explorer", "10 file adventures - you're a digital nomad!", "🗂️", 
                      AchievementType.SILVER, 10, "IOException");

        // ClassNotFoundException achievements
        addAchievement("class_first", "Class Hunter", "Your first missing class!", "🔍", 
                      AchievementType.BRONZE, 1, "ClassNotFoundException");
        addAchievement("class_10", "Class Detective", "10 missing classes - you're a code detective!", "🕵️", 
                      AchievementType.SILVER, 10, "ClassNotFoundException");

        // StackOverflowError achievements
        addAchievement("stack_first", "Stack Builder", "Your first stack overflow!", "📚", 
                      AchievementType.BRONZE, 1, "StackOverflowError");
        addAchievement("stack_5", "Stack Master", "5 stack overflows - you've mastered recursion!", "♾️", 
                      AchievementType.SILVER, 5, "StackOverflowError");
        addAchievement("stack_10", "Stack God", "10 stack overflows - you've transcended recursion!", "🌟", 
                      AchievementType.GOLD, 10, "StackOverflowError");

        // IllegalStateException achievements
        addAchievement("state_first", "State Changer", "Your first state violation!", "🔄", 
                      AchievementType.BRONZE, 1, "IllegalStateException");
        addAchievement("state_10", "State Master", "10 state violations - you control the flow!", "🎮", 
                      AchievementType.SILVER, 10, "IllegalStateException");

        // Special combo achievements
        addAchievement("combo_100", "Exception Collector", "100 total exceptions across all types!", "🎖️", 
                      AchievementType.PLATINUM, 100, "TOTAL");
        addAchievement("combo_500", "Exception Master", "500 total exceptions - you're a true master!", "🏅", 
                      AchievementType.DIAMOND, 500, "TOTAL");
        addAchievement("combo_1000", "Exception Legend", "1000 total exceptions - you are legendary!", "👑", 
                      AchievementType.RAINBOW, 1000, "TOTAL");
    }

    private void addAchievement(String id, String name, String description, String icon, 
                               AchievementType type, int requiredCount, String exceptionType) {
        Achievement achievement = new Achievement(id, name, description, icon, type, requiredCount, exceptionType);
        achievements.put(id, achievement);
    }
}
