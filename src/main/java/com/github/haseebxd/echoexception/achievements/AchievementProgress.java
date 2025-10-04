package com.github.haseebxd.echoexception.achievements;

import java.util.List;

/**
 * Represents the progress of achievements for a specific exception type.
 * Contains the current count and all relevant achievements.
 */
public class AchievementProgress {
    private final String exceptionType;
    private final int currentCount;
    private final List<Achievement> achievements;

    public AchievementProgress(String exceptionType, int currentCount, List<Achievement> achievements) {
        this.exceptionType = exceptionType;
        this.currentCount = currentCount;
        this.achievements = achievements;
    }

    public String getExceptionType() { return exceptionType; }
    public int getCurrentCount() { return currentCount; }
    public List<Achievement> getAchievements() { return achievements; }

    /**
     * Get the next achievement to unlock
     */
    public Achievement getNextAchievement() {
        return achievements.stream()
                .filter(achievement -> !achievement.isUnlocked())
                .findFirst()
                .orElse(null);
    }

    /**
     * Get the last unlocked achievement
     */
    public Achievement getLastUnlockedAchievement() {
        return achievements.stream()
                .filter(Achievement::isUnlocked)
                .reduce((first, second) -> second)
                .orElse(null);
    }

    /**
     * Get progress percentage for the next achievement
     */
    public double getProgressPercentage() {
        Achievement next = getNextAchievement();
        if (next == null) {
            return 100.0; // All achievements unlocked
        }
        
        Achievement last = getLastUnlockedAchievement();
        int baseCount = last != null ? last.getRequiredCount() : 0;
        int targetCount = next.getRequiredCount();
        
        if (targetCount == baseCount) {
            return 100.0;
        }
        
        return Math.min(100.0, ((double) (currentCount - baseCount) / (targetCount - baseCount)) * 100.0);
    }
}
