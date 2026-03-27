package com.legacyminecraft.compat.bukkit;

/**
 * Canonical compat sign-change event scaffold.
 */
public class SignChangeEvent extends Event {
    private final com.legacyminecraft.compat.bukkit.block.Block block;
    private final Player player;
    private final String[] lines;

    public SignChangeEvent(com.legacyminecraft.compat.bukkit.block.Block block, Player player, String[] lines) {
        this.block = block;
        this.player = player;
        this.lines = lines == null ? new String[]{"", "", "", ""} : lines;
    }

    public String getLine(int index) {
        if (index < 0 || index >= lines.length) {
            return "";
        }
        return lines[index];
    }

    public com.legacyminecraft.compat.bukkit.block.Block getBlock() {
        return block;
    }

    public Player getPlayer() {
        return player;
    }
}

