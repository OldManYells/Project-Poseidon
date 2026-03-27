package com.legacyminecraft.compat.bukkit;

/**
 * Canonical bridge for legacy command listeners backed by a Bukkit CommandSender.
 */
public interface CommandSenderBackedListener {
    Object getSender();
}
