package com.legacyminecraft.compat.bukkit;

/**
 * Canonical map font scaffold.
 */
public class MapFont {
    public boolean isValid(String text) {
        return true;
    }

    public int getHeight() {
        return 8;
    }

    public CharacterSprite getChar(char character) {
        return new CharacterSprite(4, 8);
    }
}
