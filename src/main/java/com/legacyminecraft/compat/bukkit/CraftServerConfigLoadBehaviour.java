package com.legacyminecraft.compat.bukkit;


import java.util.ArrayList;
import java.util.List;

/**
 * Canonical behaviour for CraftServer configuration-load wrapper glue.
 */
public final class CraftServerConfigLoadBehaviour {
    private static final CraftServerConfigLoadBehaviour INSTANCE =
            new CraftServerConfigLoadBehaviour();

    private CraftServerConfigLoadBehaviour() {
    }

    public static CraftServerConfigLoadBehaviour getInstance() {
        return INSTANCE;
    }

    public void loadConfig(Configuration configuration) {
        configuration.load();
        configuration.getString("database.url", "jdbc:sqlite:{DIR}{NAME}.db");
        configuration.getString("database.username", "bukkit");
        configuration.getString("database.password", "walrus");
        configuration.getString("database.driver", "org.sqlite.JDBC");
        configuration.getString("database.isolation", "SERIALIZABLE");

        configuration.getString("settings.update-folder", "update");
        configuration.getInt("settings.spawn-radius", 16);

        configuration.getString("settings.permissions-file", "permissions.yml");

        if (configuration.getNode("aliases") == null) {
            List<String> icanhasbukkit = new ArrayList<String>();
            icanhasbukkit.add("version");
            configuration.setProperty("aliases.icanhasbukkit", icanhasbukkit);
        }
        configuration.save();
    }
}
