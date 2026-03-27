package com.legacyminecraft.poseidon.network;

/**
 * Canonical config-key policy for legacy ModLoader compatibility support toggles.
 */
public final class ModLoaderSupportConfigPolicy {
    private static final ModLoaderSupportConfigPolicy INSTANCE = new ModLoaderSupportConfigPolicy();
    private static final String MODLOADER_SUPPORT_ENABLED_KEY = "settings.support.modloader.enable";
    private static final boolean MODLOADER_SUPPORT_ENABLED_DEFAULT = false;

    private ModLoaderSupportConfigPolicy() {
    }

    public static ModLoaderSupportConfigPolicy getInstance() {
        return INSTANCE;
    }

    public String modLoaderSupportEnabledKey() {
        return MODLOADER_SUPPORT_ENABLED_KEY;
    }

    public boolean modLoaderSupportEnabledDefault() {
        return MODLOADER_SUPPORT_ENABLED_DEFAULT;
    }
}
