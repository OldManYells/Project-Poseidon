package com.legacyminecraft.compat.bukkit;


import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Canonical behaviour for CraftServer plugin-enable wrapper glue.
 */
public final class CraftServerPluginEnableBehaviour {
    private static final CraftServerPluginEnableBehaviour INSTANCE =
            new CraftServerPluginEnableBehaviour();

    private CraftServerPluginEnableBehaviour() {
    }

    public static CraftServerPluginEnableBehaviour getInstance() {
        return INSTANCE;
    }

    public void loadPlugin(PluginManager pluginManager, Plugin plugin, Logger logger) {
        try {
            pluginManager.enablePlugin(plugin);

            List<Permission> permissions = plugin.getDescription().getPermissions();
            for (Permission permission : permissions) {
                try {
                    pluginManager.addPermission(permission);
                } catch (IllegalArgumentException ex) {
                    logger.log(
                            Level.WARNING,
                            "Plugin " + plugin.getDescription().getFullName() + " tried to register permission '" + permission.getName() + "' but it's already registered",
                            ex
                    );
                }
            }
        } catch (Throwable ex) {
            logger.log(Level.SEVERE, ex.getMessage() + " loading " + plugin.getDescription().getFullName() + " (Is it up to date?)", ex);
        }
    }
}
