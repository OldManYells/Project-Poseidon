package com.legacyminecraft.poseidon.compat.bukkit;

import org.bukkit.command.CommandSender;

/**
 * Canonical bridge for legacy command listeners backed by a Bukkit CommandSender.
 */
public interface CommandSenderBackedListener {
    CommandSender getSender();
}

