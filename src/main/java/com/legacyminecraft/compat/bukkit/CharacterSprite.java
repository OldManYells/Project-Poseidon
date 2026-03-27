package com.legacyminecraft.compat.bukkit;

/**
 * Canonical map character sprite scaffold.
 */
public class CharacterSprite {
    private final int width;
    private final int height;

    public CharacterSprite() {
        this(0, 0);
    }

    public CharacterSprite(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public boolean get(int row, int column) {
        return row >= 0 && row < height && column >= 0 && column < width;
    }
}
