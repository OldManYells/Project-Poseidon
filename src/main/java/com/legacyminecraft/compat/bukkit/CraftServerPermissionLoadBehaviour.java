package com.legacyminecraft.compat.bukkit;

import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.error.MarkedYAMLException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Canonical behaviour for CraftServer custom-permissions loading wrapper glue.
 */
public final class CraftServerPermissionLoadBehaviour {
    private static final CraftServerPermissionLoadBehaviour INSTANCE =
            new CraftServerPermissionLoadBehaviour();

    private CraftServerPermissionLoadBehaviour() {
    }

    public static CraftServerPermissionLoadBehaviour getInstance() {
        return INSTANCE;
    }

    @SuppressWarnings("unchecked")
    public void loadCustomPermissions(Configuration configuration, Yaml yaml, Logger logger, PluginManager pluginManager) {
        File file = new File(configuration.getString("settings.permissions-file"));
        FileInputStream stream;

        try {
            stream = new FileInputStream(file);
        } catch (FileNotFoundException ex) {
            try {
                file.createNewFile();
            } catch (IOException ignored) {
            }
            return;
        }

        Map<String, Map<String, Object>> permissions;

        try {
            permissions = (Map<String, Map<String, Object>>) yaml.load(stream);
        } catch (MarkedYAMLException ex) {
            logger.log(Level.WARNING, "Server permissions file " + file + " is not valid YAML: " + ex.toString());
            return;
        } catch (Throwable ex) {
            logger.log(Level.WARNING, "Server permissions file " + file + " is not valid YAML.", ex);
            return;
        } finally {
            try {
                stream.close();
            } catch (IOException ignored) {
            }
        }

        if (permissions == null) {
            logger.log(Level.INFO, "Server permissions file " + file + " is empty, ignoring it");
            return;
        }

        Set<String> keys = permissions.keySet();

        for (String name : keys) {
            try {
                pluginManager.addPermission(Permission.loadPermission(name, permissions.get(name)));
            } catch (Throwable ex) {
                Bukkit.getServer().getLogger().log(Level.SEVERE, "Permission node '" + name + "' in server config is invalid", ex);
            }
        }
    }
}
