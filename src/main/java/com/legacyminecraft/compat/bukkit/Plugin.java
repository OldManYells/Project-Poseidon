package com.legacyminecraft.compat.bukkit;

import com.avaje.ebean.EbeanServer;

import java.io.File;

/**
 * Canonical compat plugin scaffold.
 */
public interface Plugin {
    default String getName() {
        return getDescription().getName();
    }

    default boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        return false;
    }

    default File getDataFolder() {
        return new File("plugins");
    }

    PluginDescriptionFile getDescription();

    default Configuration getConfiguration() {
        return null;
    }

    default PluginLoader getPluginLoader() {
        return null;
    }

    default Server getServer() {
        return Bukkit.getServer();
    }

    boolean isEnabled();

    default void onDisable() {
    }

    default void onLoad() {
    }

    default void onEnable() {
    }

    default boolean isNaggable() {
        return false;
    }

    default void setNaggable(boolean canNag) {
    }

    default EbeanServer getDatabase() {
        return null;
    }

    default ChunkGenerator getDefaultWorldGenerator(String worldName, String id) {
        return null;
    }
}
