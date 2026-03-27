package com.legacyminecraft.compat.bukkit;


/**
 * Canonical behaviour for CraftServer reload bootstrap wrapper glue.
 */
public final class CraftServerReloadBootstrapBehaviour {
    public interface ReloadBootstrapDelegate {
        void loadPlugins();

        void enablePlugins(PluginLoadOrder type);
    }

    private static final CraftServerReloadBootstrapBehaviour INSTANCE =
            new CraftServerReloadBootstrapBehaviour();

    private CraftServerReloadBootstrapBehaviour() {
    }

    public static CraftServerReloadBootstrapBehaviour getInstance() {
        return INSTANCE;
    }

    public void bootstrap(ReloadBootstrapDelegate delegate) {
        delegate.loadPlugins();
        delegate.enablePlugins(PluginLoadOrder.STARTUP);
        delegate.enablePlugins(PluginLoadOrder.POSTWORLD);
    }
}
