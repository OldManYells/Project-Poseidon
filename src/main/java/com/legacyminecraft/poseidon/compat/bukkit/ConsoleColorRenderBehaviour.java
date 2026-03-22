package com.legacyminecraft.poseidon.compat.bukkit;

import jline.ANSIBuffer.ANSICodes;
import org.bukkit.ChatColor;

import java.util.Map;

/**
 * Canonical behavior for translating Bukkit ChatColor codes into ANSI console output.
 */
public final class ConsoleColorRenderBehaviour {
    private static final ConsoleColorRenderBehaviour INSTANCE = new ConsoleColorRenderBehaviour();

    private ConsoleColorRenderBehaviour() {
    }

    public static ConsoleColorRenderBehaviour getInstance() {
        return INSTANCE;
    }

    public void initializeDefaultReplacements(Map<ChatColor, String> replacements) {
        replacements.put(ChatColor.BLACK, ANSICodes.attrib(0));
        replacements.put(ChatColor.DARK_BLUE, ANSICodes.attrib(34));
        replacements.put(ChatColor.DARK_GREEN, ANSICodes.attrib(32));
        replacements.put(ChatColor.DARK_AQUA, ANSICodes.attrib(36));
        replacements.put(ChatColor.DARK_RED, ANSICodes.attrib(31));
        replacements.put(ChatColor.DARK_PURPLE, ANSICodes.attrib(35));
        replacements.put(ChatColor.GOLD, ANSICodes.attrib(33));
        replacements.put(ChatColor.GRAY, ANSICodes.attrib(37));
        replacements.put(ChatColor.DARK_GRAY, ANSICodes.attrib(0));
        replacements.put(ChatColor.BLUE, ANSICodes.attrib(34));
        replacements.put(ChatColor.GREEN, ANSICodes.attrib(32));
        replacements.put(ChatColor.AQUA, ANSICodes.attrib(36));
        replacements.put(ChatColor.RED, ANSICodes.attrib(31));
        replacements.put(ChatColor.LIGHT_PURPLE, ANSICodes.attrib(35));
        replacements.put(ChatColor.YELLOW, ANSICodes.attrib(33));
        replacements.put(ChatColor.WHITE, ANSICodes.attrib(37));
    }

    public String renderAnsiMessage(String message, ChatColor[] colors, Map<ChatColor, String> replacements) {
        String renderedMessage = message;

        for (ChatColor color : colors) {
            if (replacements.containsKey(color)) {
                renderedMessage = renderedMessage.replaceAll(color.toString(), replacements.get(color));
            } else {
                renderedMessage = renderedMessage.replaceAll(color.toString(), "");
            }
        }

        return renderedMessage;
    }

    public String resetAnsiCode() {
        return ANSICodes.attrib(0);
    }
}

