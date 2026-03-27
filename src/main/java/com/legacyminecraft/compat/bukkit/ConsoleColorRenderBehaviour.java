package com.legacyminecraft.compat.bukkit;

import jline.ANSIBuffer.ANSICodes;

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

    public void initializeDefaultReplacements(Map<String, String> replacements) {
        replacements.put("BLACK", ANSICodes.attrib(0));
        replacements.put("DARK_BLUE", ANSICodes.attrib(34));
        replacements.put("DARK_GREEN", ANSICodes.attrib(32));
        replacements.put("DARK_AQUA", ANSICodes.attrib(36));
        replacements.put("DARK_RED", ANSICodes.attrib(31));
        replacements.put("DARK_PURPLE", ANSICodes.attrib(35));
        replacements.put("GOLD", ANSICodes.attrib(33));
        replacements.put("GRAY", ANSICodes.attrib(37));
        replacements.put("DARK_GRAY", ANSICodes.attrib(0));
        replacements.put("BLUE", ANSICodes.attrib(34));
        replacements.put("GREEN", ANSICodes.attrib(32));
        replacements.put("AQUA", ANSICodes.attrib(36));
        replacements.put("RED", ANSICodes.attrib(31));
        replacements.put("LIGHT_PURPLE", ANSICodes.attrib(35));
        replacements.put("YELLOW", ANSICodes.attrib(33));
        replacements.put("WHITE", ANSICodes.attrib(37));
    }

    public String renderAnsiMessage(String message, Object[] colors, Map<String, String> replacements) {
        String renderedMessage = message;

        for (Object color : colors) {
            String colorToken = color == null ? "" : color.toString();
            String colorName = color == null ? "" : extractEnumName(color);
            if (replacements.containsKey(colorName)) {
                renderedMessage = renderedMessage.replaceAll(colorToken, replacements.get(colorName));
            } else {
                renderedMessage = renderedMessage.replaceAll(colorToken, "");
            }
        }

        return renderedMessage;
    }

    public String resetAnsiCode() {
        return ANSICodes.attrib(0);
    }

    private String extractEnumName(Object color) {
        if (color instanceof Enum) {
            return ((Enum<?>) color).name();
        }
        return color == null ? "" : color.toString();
    }
}
