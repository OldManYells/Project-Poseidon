package com.legacyminecraft.poseidon;

import com.avaje.ebean.EbeanServer;
import com.legacyminecraft.compat.bukkit.Bukkit;
import com.legacyminecraft.compat.bukkit.ChunkGenerator;
import com.legacyminecraft.compat.bukkit.Command;
import com.legacyminecraft.compat.bukkit.CommandSender;
import com.legacyminecraft.compat.bukkit.Configuration;
import com.legacyminecraft.compat.bukkit.Plugin;
import com.legacyminecraft.compat.bukkit.PluginDescriptionFile;
import com.legacyminecraft.compat.bukkit.PluginLoader;
import com.legacyminecraft.compat.bukkit.Server;

import java.io.File;

/**
 * Internal virtual plugin used as an owner for scheduler tasks during migration.
 */
public final class PoseidonPlugin implements Plugin {
    private static final PoseidonPlugin INSTANCE = new PoseidonPlugin();
    private static final PluginDescriptionFile DESCRIPTION =
            new PluginDescriptionFile("PoseidonInternal", "1.0.0", "com.legacyminecraft.poseidon.PoseidonPlugin");

    private final File dataFolder = new File("plugins/PoseidonInternal");
    private volatile boolean naggable = false;

    private PoseidonPlugin() {
    }

    public static PoseidonPlugin getInstance() {
        return INSTANCE;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        return false;
    }

    @Override
    public File getDataFolder() {
        if (!dataFolder.exists()) {
            dataFolder.mkdirs();
        }
        return dataFolder;
    }

    @Override
    public PluginDescriptionFile getDescription() {
        return DESCRIPTION;
    }

    @Override
    public Configuration getConfiguration() {
        return null;
    }

    @Override
    public PluginLoader getPluginLoader() {
        return null;
    }

    @Override
    public Server getServer() {
        return Bukkit.getServer();
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public void onDisable() {
    }

    @Override
    public void onLoad() {
    }

    @Override
    public void onEnable() {
    }

    @Override
    public boolean isNaggable() {
        return naggable;
    }

    @Override
    public void setNaggable(boolean canNag) {
        this.naggable = canNag;
    }

    @Override
    public EbeanServer getDatabase() {
        return null;
    }

    @Override
    public ChunkGenerator getDefaultWorldGenerator(String worldName, String id) {
        return null;
    }
}
