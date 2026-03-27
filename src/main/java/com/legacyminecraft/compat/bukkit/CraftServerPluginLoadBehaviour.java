package com.legacyminecraft.compat.bukkit;


import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Canonical behaviour for CraftServer plugin-loading wrapper glue.
 */
public final class CraftServerPluginLoadBehaviour {
    private static final CraftServerPluginLoadBehaviour INSTANCE =
            new CraftServerPluginLoadBehaviour();

    private CraftServerPluginLoadBehaviour() {
    }

    public static CraftServerPluginLoadBehaviour getInstance() {
        return INSTANCE;
    }

    public void loadPlugins(PluginManager pluginManager, File pluginFolder, Logger logger) {
        pluginManager.registerInterface(JavaPluginLoader.class);

        if (pluginFolder.exists()) {
            Plugin[] plugins = pluginManager.loadPlugins(pluginFolder);
            for (Plugin plugin : plugins) {
                try {
                    plugin.onLoad();
                } catch (Throwable ex) {
                    logger.log(
                            Level.SEVERE,
                            ex.getMessage() + " initializing " + plugin.getDescription().getFullName() + " (Is it up to date?)",
                            ex
                    );
                }
            }
        } else {
            pluginFolder.mkdir();
        }
    }
}
