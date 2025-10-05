package com.github.haseebxd.echoexception.achievements;

/**
 * Enum representing different types of achievements.
 * Each type has a color and rarity level for visual representation.
 */
public enum AchievementType {
    BRONZE("Bronze", "#CD7F32", "Common"),
    SILVER("Silver", "#C0C0C0", "Uncommon"),
    GOLD("Gold", "#FFD700", "Rare"),
    PLATINUM("Platinum", "#E5E4E2", "Epic"),
    DIAMOND("Diamond", "#B9F2FF", "Legendary"),
    RAINBOW("Rainbow", "#FF6B6B", "Mythic");

    private final String displayName;
    private final String color;
    private final String rarity;

    AchievementType(String displayName, String color, String rarity) {
        this.displayName = displayName;
        this.color = color;
        this.rarity = rarity;
    }

    public String getDisplayName() { return displayName; }
    public String getColor() { return color; }
    public String getRarity() { return rarity; }
}
