package com.legacyminecraft.compat.bukkit;


/**
 * Canonical behaviour for CraftServer settings-wrapper accessors.
 */
public final class CraftServerSettingsBehaviour {
    private static final CraftServerSettingsBehaviour INSTANCE = new CraftServerSettingsBehaviour();

    private CraftServerSettingsBehaviour() {
    }

    public static CraftServerSettingsBehaviour getInstance() {
        return INSTANCE;
    }

    public String getUpdateFolder(Configuration configuration) {
        return configuration.getString("settings.update-folder", "update");
    }

    public int getSpawnRadius(Configuration configuration) {
        return configuration.getInt("settings.spawn-radius", 16);
    }

    public void setSpawnRadius(Configuration configuration, int value) {
        configuration.setProperty("settings.spawn-radius", value);
        configuration.save();
    }
}
