package com.legacyminecraft.compat.bukkit;


import java.lang.reflect.Method;

/**
 * Canonical behavior for CraftBukkit server-command listener name/prefix resolution.
 */
public final class ServerCommandListenerBehaviour {
    private static final ServerCommandListenerBehaviour INSTANCE = new ServerCommandListenerBehaviour();

    private ServerCommandListenerBehaviour() {
    }

    public static ServerCommandListenerBehaviour getInstance() {
        return INSTANCE;
    }

    public String resolvePrefix(CommandSender commandSender) {
        String[] classNameParts = commandSender.getClass().getName().split("\\.");
        return classNameParts[classNameParts.length - 1];
    }

    public String resolveNameOrPrefix(CommandSender commandSender, String fallbackPrefix) {
        try {
            Method getNameMethod = commandSender.getClass().getMethod("getName");
            return (String) getNameMethod.invoke(commandSender);
        } catch (Exception ignored) {
            return fallbackPrefix;
        }
    }
}

