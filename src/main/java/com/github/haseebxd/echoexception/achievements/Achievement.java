package com.github.haseebxd.echoexception.achievements;

import java.util.Objects;

/**
 * Represents an achievement that can be unlocked by users.
 * Each achievement has a unique ID, name, description, and unlock condition.
 */
public class Achievement {
    private final String id;
    private final String name;
    private final String description;
    private final String icon;
    private final AchievementType type;
    private final int requiredCount;
    private final String exceptionType;
    private boolean unlocked;
    private long unlockedAt;

    public Achievement(String id, String name, String description, String icon, 
                      AchievementType type, int requiredCount, String exceptionType) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.icon = icon;
        this.type = type;
        this.requiredCount = requiredCount;
        this.exceptionType = exceptionType;
        this.unlocked = false;
        this.unlockedAt = 0;
    }

    // Getters
    public String getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public String getIcon() { return icon; }
    public AchievementType getType() { return type; }
    public int getRequiredCount() { return requiredCount; }
    public String getExceptionType() { return exceptionType; }
    public boolean isUnlocked() { return unlocked; }
    public long getUnlockedAt() { return unlockedAt; }

    // Setters
    public void setUnlocked(boolean unlocked) { 
        this.unlocked = unlocked; 
        if (unlocked && this.unlockedAt == 0) {
            this.unlockedAt = System.currentTimeMillis();
        }
    }

    public void setUnlockedAt(long unlockedAt) { this.unlockedAt = unlockedAt; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Achievement that = (Achievement) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Achievement{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", unlocked=" + unlocked +
                '}';
    }
}
