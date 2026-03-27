package com.legacyminecraft.compat.bukkit;


/**
 * Canonical behaviour for CraftServer plugin-activation wrapper glue.
 */
public final class CraftServerPluginActivationBehaviour {
    public interface PluginLoadDispatcher {
        void loadPlugin(Plugin plugin);
    }

    public interface PostWorldBootstrapAction {
        void run();
    }

    private static final CraftServerPluginActivationBehaviour INSTANCE =
            new CraftServerPluginActivationBehaviour();

    private CraftServerPluginActivationBehaviour() {
    }

    public static CraftServerPluginActivationBehaviour getInstance() {
        return INSTANCE;
    }

    public void enablePlugins(Plugin[] plugins, PluginLoadOrder type, PluginLoadDispatcher dispatcher, PostWorldBootstrapAction postWorldBootstrapAction) {
        for (Plugin plugin : plugins) {
            if ((!plugin.isEnabled()) && (plugin.getDescription().getLoad() == type)) {
                dispatcher.loadPlugin(plugin);
            }
        }

        if (type == PluginLoadOrder.POSTWORLD) {
            postWorldBootstrapAction.run();
        }
    }
}
