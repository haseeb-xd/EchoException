package com.github.haseebxd.echoexception.ui.tabs;

import com.github.haseebxd.echoexception.achievements.Achievement;
import com.github.haseebxd.echoexception.achievements.AchievementManager;
import com.github.haseebxd.echoexception.achievements.AchievementProgress;
import com.intellij.openapi.project.Project;
import com.intellij.ui.components.JBLabel;
import com.intellij.ui.components.JBPanel;
import com.intellij.ui.components.JBScrollPane;
import com.intellij.ui.components.JBTabbedPane;
import com.intellij.util.ui.JBFont;
import com.intellij.util.ui.JBUI;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Achievement tab that displays user achievements, progress, and statistics.
 */
public class AchievementTab implements ToolTab {
    private static final int SPACING = 20;
    private final AchievementManager achievementManager;

    public AchievementTab() {
        this.achievementManager = AchievementManager.getInstance();
    }

    @Override
    public @NotNull String getTitle() {
        return "Achievements";
    }



    public String getIcon() {
        return "🏆";
    }

    @Override
    public @NotNull JPanel getContent(@NotNull Project project) {
        JBPanel<JBPanel<?>> mainPanel = new JBPanel<>(new BorderLayout());
        
        JBTabbedPane tabbedPane = new JBTabbedPane();
        
        // Overview tab
        tabbedPane.addTab("Overview", createOverviewPanel());
        
        // Unlocked achievements tab
        tabbedPane.addTab("Unlocked", createUnlockedAchievementsPanel());
        
        // Progress tab
        tabbedPane.addTab("Progress", createProgressPanel());
        
        // Statistics tab
        tabbedPane.addTab("Statistics", createStatisticsPanel());

        mainPanel.add(tabbedPane, BorderLayout.CENTER);
        return mainPanel;
    }

    private JPanel createOverviewPanel() {
        JBPanel<JBPanel<?>> panel = new JBPanel<>();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        // Header
        JBLabel headerLabel = new JBLabel("Achievement Overview");
        headerLabel.setFont(JBFont.h1());
        headerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(headerLabel);
        panel.add(Box.createVerticalStrut(SPACING));

        // Stats summary
        List<Achievement> allAchievements = achievementManager.getAllAchievements();
        List<Achievement> unlockedAchievements = achievementManager.getUnlockedAchievements();
        int totalCount = allAchievements.size();
        int unlockedCount = unlockedAchievements.size();
        double completionPercentage = totalCount > 0 ? (double) unlockedCount / totalCount * 100 : 0;

        JBPanel<JBPanel<?>> statsPanel = createStatsPanel(unlockedCount, totalCount, completionPercentage);
        panel.add(statsPanel);
        panel.add(Box.createVerticalStrut(SPACING));

        // Recent achievements
        if (!unlockedAchievements.isEmpty()) {
            JBLabel recentLabel = new JBLabel("Recent Achievements");
            recentLabel.setFont(JBFont.h3());
            recentLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            panel.add(recentLabel);
            panel.add(Box.createVerticalStrut(5));

            // Show last 3 achievements
            int showCount = Math.min(3, unlockedAchievements.size());
            for (int i = 0; i < showCount; i++) {
                Achievement achievement = unlockedAchievements.get(i);
                panel.add(createAchievementCard(achievement, true));
                panel.add(Box.createVerticalStrut(5));
            }
        }

        return panel;
    }

    private JPanel createUnlockedAchievementsPanel() {
        JBPanel<JBPanel<?>> panel = new JBPanel<>(new BorderLayout());
        
        List<Achievement> unlockedAchievements = achievementManager.getUnlockedAchievements();
        
        if (unlockedAchievements.isEmpty()) {
            JBPanel<JBPanel<?>> emptyPanel = new JBPanel<>(new BorderLayout());
            
            JBLabel noAchievementsLabel = new JBLabel("No achievements unlocked yet! Keep coding to unlock your first achievement! 🚀");
            noAchievementsLabel.setHorizontalAlignment(SwingConstants.CENTER);
            noAchievementsLabel.setFont(JBFont.h3());
            emptyPanel.add(noAchievementsLabel, BorderLayout.CENTER);
            panel.add(emptyPanel, BorderLayout.CENTER);
        } else {
            JBPanel<JBPanel<?>> contentPanel = new JBPanel<>();
            contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
            
            for (Achievement achievement : unlockedAchievements) {
                contentPanel.add(createAchievementCard(achievement, true));
                contentPanel.add(Box.createVerticalStrut(15));
            }
            
            JBScrollPane scrollPane = new JBScrollPane(contentPanel);
            scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
            scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
            panel.add(scrollPane, BorderLayout.CENTER);
        }
        
        return panel;
    }

    private JPanel createProgressPanel() {
        JBPanel<JBPanel<?>> panel = new JBPanel<>(new BorderLayout());
        
        Map<String, Integer> exceptionCounts = achievementManager.getAllExceptionCounts();
        
        if (exceptionCounts.isEmpty()) {
            JBPanel<JBPanel<?>> emptyPanel = new JBPanel<>(new BorderLayout());
            
            JBLabel noProgressLabel = new JBLabel("No exceptions recorded yet! Start coding to see your progress! 💻");
            noProgressLabel.setHorizontalAlignment(SwingConstants.CENTER);
            noProgressLabel.setFont(JBFont.h3());
            emptyPanel.add(noProgressLabel, BorderLayout.CENTER);
            panel.add(emptyPanel, BorderLayout.CENTER);
        } else {
            JBPanel<JBPanel<?>> contentPanel = new JBPanel<>();
            contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
            
            for (Map.Entry<String, Integer> entry : exceptionCounts.entrySet()) {
                String exceptionType = entry.getKey();
                if (!exceptionType.equals("TOTAL")) { // Skip total count in progress view
                    AchievementProgress progress = achievementManager.getAchievementProgress(exceptionType);
                    contentPanel.add(createProgressCard(progress));
                    contentPanel.add(Box.createVerticalStrut(15));
                }
            }
            
            JBScrollPane scrollPane = new JBScrollPane(contentPanel);
            scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
            scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
            panel.add(scrollPane, BorderLayout.CENTER);
        }
        
        return panel;
    }

    private JPanel createStatisticsPanel() {
        JBPanel<JBPanel<?>> panel = new JBPanel<>(new BorderLayout());

        // Header
        JBPanel<JBPanel<?>> headerPanel = new JBPanel<>();
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        headerPanel.setBorder(JBUI.Borders.emptyBottom(20));

        JBLabel headerLabel = new JBLabel("Exception Statistics");
        headerLabel.setFont(JBFont.h1());
        headerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        headerPanel.add(headerLabel);

        panel.add(headerPanel, BorderLayout.NORTH);

        Map<String, Integer> exceptionCounts = achievementManager.getAllExceptionCounts();
        
        if (exceptionCounts.isEmpty()) {
            JBPanel<JBPanel<?>> emptyPanel = new JBPanel<>(new BorderLayout());
            
            JBLabel noStatsLabel = new JBLabel("No statistics available yet. Start coding to generate some data! 📈");
            noStatsLabel.setHorizontalAlignment(SwingConstants.CENTER);
            noStatsLabel.setFont(JBFont.h3());
            emptyPanel.add(noStatsLabel, BorderLayout.CENTER);
            panel.add(emptyPanel, BorderLayout.CENTER);
        } else {
            JBPanel<JBPanel<?>> contentPanel = new JBPanel<>();
            contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));

            // Total exceptions card
            int totalExceptions = exceptionCounts.getOrDefault("TOTAL", 0);
            JBPanel<JBPanel<?>> totalCard = createTotalStatsCard(totalExceptions);
            contentPanel.add(totalCard);
            contentPanel.add(Box.createVerticalStrut(20));

            // Exception breakdown header
            JBLabel breakdownLabel = new JBLabel("Exception Breakdown");
            breakdownLabel.setFont(JBFont.h2());
            breakdownLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            contentPanel.add(breakdownLabel);
            contentPanel.add(Box.createVerticalStrut(15));

            // Create exception cards
            List<Map.Entry<String, Integer>> sortedEntries = exceptionCounts.entrySet().stream()
                    .filter(entry -> !entry.getKey().equals("TOTAL"))
                    .sorted((e1, e2) -> Integer.compare(e2.getValue(), e1.getValue())) // Sort by count descending
                    .toList();

            for (Map.Entry<String, Integer> entry : sortedEntries) {
                JBPanel<JBPanel<?>> exceptionCard = createExceptionStatCard(entry.getKey(), entry.getValue());
                contentPanel.add(exceptionCard);
                contentPanel.add(Box.createVerticalStrut(10));
            }

            JBScrollPane scrollPane = new JBScrollPane(contentPanel);
            scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
            scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
            panel.add(scrollPane, BorderLayout.CENTER);
        }

        return panel;
    }

    private JBPanel createStatsPanel(int unlocked, int total, double percentage) {
        JBPanel<JBPanel<?>> panel = new JBPanel<>(new GridLayout(1, 3, 10, 0));
        panel.setOpaque(false);

        // Unlocked count
        JBPanel<JBPanel<?>> unlockedPanel = createStatCard("Unlocked", String.valueOf(unlocked), "🏆");
        panel.add(unlockedPanel);

        // Total count
        JBPanel<JBPanel<?>> totalPanel = createStatCard("Total", String.valueOf(total), "🎯");
        panel.add(totalPanel);

        // Completion percentage
        JBPanel<JBPanel<?>> percentagePanel = createStatCard("Complete", String.format("%.1f%%", percentage), "📈");
        panel.add(percentagePanel);

        return panel;
    }

    private JBPanel createStatCard(String title, String value, String icon) {
        JBPanel<JBPanel<?>> card = new JBPanel<>();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(JBUI.Borders.compound(
                JBUI.Borders.customLine(JBUI.CurrentTheme.DefaultTabs.borderColor(), 1),
                JBUI.Borders.empty(15)
        ));

        JBLabel iconLabel = new JBLabel(icon);
        iconLabel.setFont(JBFont.h0());
        iconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(iconLabel);
        card.add(Box.createVerticalStrut(5));

        JBLabel valueLabel = new JBLabel(value);
        valueLabel.setFont(JBFont.h1());
        valueLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(valueLabel);
        card.add(Box.createVerticalStrut(5));

        JBLabel titleLabel = new JBLabel(title);
        titleLabel.setFont(JBFont.h4());
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(titleLabel);

        return card;
    }

    private JPanel createAchievementCard(Achievement achievement, boolean isUnlocked) {
        JBPanel<JBPanel<?>> card = new JBPanel<>(new BorderLayout());
        
        // Set card appearance based on unlock status
        card.setBorder(JBUI.Borders.empty(15));

        // Left side - icon and type
        JBPanel<JBPanel<?>> leftPanel = new JBPanel<>();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setOpaque(false);
        leftPanel.setBorder(JBUI.Borders.emptyRight(15));

        JBLabel iconLabel = new JBLabel(achievement.getIcon());
        iconLabel.setFont(JBFont.h0());
        iconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        leftPanel.add(iconLabel);
        leftPanel.add(Box.createVerticalStrut(5));

        JBLabel typeLabel = new JBLabel(achievement.getType().getDisplayName());
        typeLabel.setFont(JBFont.small());
        typeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        leftPanel.add(typeLabel);

        // Center - name and description
        JBPanel<JBPanel<?>> centerPanel = new JBPanel<>();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setOpaque(false);

        JBLabel nameLabel = new JBLabel(achievement.getName());
        nameLabel.setFont(JBFont.h2());
        nameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        centerPanel.add(nameLabel);
        centerPanel.add(Box.createVerticalStrut(3));

        JBLabel descLabel = new JBLabel(achievement.getDescription());
        descLabel.setFont(JBFont.regular());
        descLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        centerPanel.add(descLabel);

        // Right side - unlock info or progress
        JBPanel<JBPanel<?>> rightPanel = new JBPanel<>();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.setOpaque(false);
        rightPanel.setBorder(JBUI.Borders.emptyLeft(15));

        if (isUnlocked) {
            SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy");
            String unlockDate = sdf.format(new Date(achievement.getUnlockedAt()));
            JBLabel dateLabel = new JBLabel("Unlocked: " + unlockDate);
            dateLabel.setFont(JBFont.small());
            dateLabel.setAlignmentX(Component.RIGHT_ALIGNMENT);
            rightPanel.add(dateLabel);
        } else {
            JBLabel progressLabel = new JBLabel("Required: " + achievement.getRequiredCount());
            progressLabel.setFont(JBFont.small());
            progressLabel.setAlignmentX(Component.RIGHT_ALIGNMENT);
            rightPanel.add(progressLabel);
        }

        card.add(leftPanel, BorderLayout.WEST);
        card.add(centerPanel, BorderLayout.CENTER);
        card.add(rightPanel, BorderLayout.EAST);

        return card;
    }

    private JPanel createProgressCard(AchievementProgress progress) {
        JBPanel<JBPanel<?>> card = new JBPanel<>(new BorderLayout());
        card.setBorder(JBUI.Borders.empty(15));

        // Header
        JBLabel headerLabel = new JBLabel(progress.getExceptionType() + " Progress");
        headerLabel.setFont(JBFont.h2());
        card.add(headerLabel, BorderLayout.NORTH);

        // Progress content
        JBPanel<JBPanel<?>> contentPanel = new JBPanel<>();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setOpaque(false);
        contentPanel.setBorder(JBUI.Borders.emptyTop(10));

        // Current count
        JBLabel countLabel = new JBLabel("Current: " + progress.getCurrentCount());
        countLabel.setFont(JBFont.h3());
        contentPanel.add(countLabel);

        // Next achievement
        Achievement nextAchievement = progress.getNextAchievement();
        if (nextAchievement != null) {
            contentPanel.add(Box.createVerticalStrut(8));
            
            JBLabel nextLabel = new JBLabel("Next: " + nextAchievement.getName());
            nextLabel.setFont(JBFont.h4());
            contentPanel.add(nextLabel);
            contentPanel.add(Box.createVerticalStrut(5));
            
            // Progress bar
            double percentage = progress.getProgressPercentage();
            JProgressBar progressBar = new JProgressBar(0, 100);
            progressBar.setValue((int) percentage);
            progressBar.setStringPainted(true);
            progressBar.setString(String.format("%.1f%%", percentage));
            contentPanel.add(progressBar);
        } else {
            JBLabel completeLabel = new JBLabel("All achievements unlocked! 🎉");
            completeLabel.setFont(JBFont.h3());
            contentPanel.add(completeLabel);
        }

        card.add(contentPanel, BorderLayout.CENTER);

        return card;
    }

    private JBPanel createTotalStatsCard(int totalExceptions) {
        JBPanel<JBPanel<?>> card = new JBPanel<>(new BorderLayout());
        card.setBorder(JBUI.Borders.empty(20));

        // Left side - icon
        JBPanel<JBPanel<?>> leftPanel = new JBPanel<>();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setOpaque(false);

        JBLabel iconLabel = new JBLabel("🎯");
        iconLabel.setFont(JBFont.h0());
        iconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        leftPanel.add(iconLabel);

        // Center - main stats
        JBPanel<JBPanel<?>> centerPanel = new JBPanel<>();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setOpaque(false);

        JBLabel totalLabel = new JBLabel("Total Exceptions");
        totalLabel.setFont(JBFont.h2());
        totalLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        centerPanel.add(totalLabel);
        centerPanel.add(Box.createVerticalStrut(5));

        JBLabel countLabel = new JBLabel(String.valueOf(totalExceptions));
        countLabel.setFont(JBFont.h0());
        countLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        centerPanel.add(countLabel);

        // Right side - description
        JBPanel<JBPanel<?>> rightPanel = new JBPanel<>();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.setOpaque(false);

        JBLabel descLabel = new JBLabel("Across all types");
        descLabel.setFont(JBFont.h4());
        descLabel.setAlignmentX(Component.RIGHT_ALIGNMENT);
        rightPanel.add(descLabel);

        card.add(leftPanel, BorderLayout.WEST);
        card.add(centerPanel, BorderLayout.CENTER);
        card.add(rightPanel, BorderLayout.EAST);

        return card;
    }

    private JBPanel createExceptionStatCard(String exceptionType, int count) {
        JBPanel<JBPanel<?>> card = new JBPanel<>(new BorderLayout());
        card.setBorder(JBUI.Borders.empty(15));

        // Left side - exception icon
        JBPanel<JBPanel<?>> leftPanel = new JBPanel<>();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setOpaque(false);
        leftPanel.setBorder(JBUI.Borders.emptyRight(15));

        String icon = getExceptionIcon(exceptionType);
        JBLabel iconLabel = new JBLabel(icon);
        iconLabel.setFont(JBFont.h2());
        iconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        leftPanel.add(iconLabel);

        // Center - exception info
        JBPanel<JBPanel<?>> centerPanel = new JBPanel<>();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setOpaque(false);

        JBLabel nameLabel = new JBLabel(exceptionType);
        nameLabel.setFont(JBFont.h3());
        nameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        centerPanel.add(nameLabel);
        centerPanel.add(Box.createVerticalStrut(3));

        JBLabel descLabel = new JBLabel(getExceptionDescription(exceptionType));
        descLabel.setFont(JBFont.small());
        descLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        centerPanel.add(descLabel);

        // Right side - count
        JBPanel<JBPanel<?>> rightPanel = new JBPanel<>();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.setOpaque(false);
        rightPanel.setBorder(JBUI.Borders.emptyLeft(15));

        JBLabel countLabel = new JBLabel(String.valueOf(count));
        countLabel.setFont(JBFont.h1());
        countLabel.setAlignmentX(Component.RIGHT_ALIGNMENT);
        rightPanel.add(countLabel);

        JBLabel unitLabel = new JBLabel("Times");
        unitLabel.setFont(JBFont.small());
        unitLabel.setAlignmentX(Component.RIGHT_ALIGNMENT);
        rightPanel.add(unitLabel);

        card.add(leftPanel, BorderLayout.WEST);
        card.add(centerPanel, BorderLayout.CENTER);
        card.add(rightPanel, BorderLayout.EAST);

        return card;
    }

    private String getExceptionIcon(String exceptionType) {
        return switch (exceptionType) {
            case "NullPointerException" -> "💥";
            case "ArrayIndexOutOfBoundsException" -> "📊";
            case "IllegalArgumentException" -> "⚠️";
            case "RuntimeException" -> "⚡";
            case "IOException" -> "💾";
            case "SQLException" -> "🗄️";
            case "ClassNotFoundException" -> "🔍";
            case "NoSuchMethodException" -> "🔧";
            case "SecurityException" -> "🔒";
            case "OutOfMemoryError" -> "🧠";
            case "StackOverflowError" -> "📚";
            case "ConcurrentModificationException" -> "🔄";
            case "UnsupportedOperationException" -> "❌";
            case "NumberFormatException" -> "🔢";
            case "IndexOutOfBoundsException" -> "📍";
            case "ArithmeticException" -> "🧮";
            default -> "❓";
        };
    }

    private String getExceptionDescription(String exceptionType) {
        return switch (exceptionType) {
            case "NullPointerException" -> "Trying to use a null reference";
            case "ArrayIndexOutOfBoundsException" -> "Accessing array with invalid index";
            case "IllegalArgumentException" -> "Invalid argument passed to method";
            case "RuntimeException" -> "General runtime error occurred";
            case "IOException" -> "Input/output operation failed";
            case "SQLException" -> "Database operation failed";
            case "ClassNotFoundException" -> "Requested class not found";
            case "NoSuchMethodException" -> "Requested method not found";
            case "SecurityException" -> "Security violation detected";
            case "OutOfMemoryError" -> "Insufficient memory available";
            case "StackOverflowError" -> "Stack overflow in recursion";
            case "ConcurrentModificationException" -> "Collection modified during iteration";
            case "UnsupportedOperationException" -> "Operation not supported";
            case "NumberFormatException" -> "Invalid number format";
            case "IndexOutOfBoundsException" -> "Index out of valid range";
            case "ArithmeticException" -> "Arithmetic operation failed";
            default -> "Unknown exception type";
        };
    }
}
