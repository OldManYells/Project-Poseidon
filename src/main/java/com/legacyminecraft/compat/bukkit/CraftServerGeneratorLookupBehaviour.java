package com.legacyminecraft.compat.bukkit;


import java.util.logging.Logger;

/**
 * Canonical behaviour for CraftServer world-generator lookup wrapper glue.
 */
public final class CraftServerGeneratorLookupBehaviour {
    private static final CraftServerGeneratorLookupBehaviour INSTANCE =
            new CraftServerGeneratorLookupBehaviour();

    private CraftServerGeneratorLookupBehaviour() {
    }

    public static CraftServerGeneratorLookupBehaviour getInstance() {
        return INSTANCE;
    }

    public ChunkGenerator getGenerator(Configuration configuration, PluginManager pluginManager, Logger logger, String worldName) {
        ConfigurationNode node = configuration.getNode("worlds");
        ChunkGenerator result = null;

        if (node != null) {
            node = node.getNode(worldName);

            if (node != null) {
                String name = node.getString("generator");

                if ((name != null) && (!name.equals(""))) {
                    String[] split = name.split(":", 2);
                    String id = (split.length > 1) ? split[1] : null;
                    Plugin plugin = pluginManager.getPlugin(split[0]);

                    if (plugin == null) {
                        logger.severe("Could not set generator for default world '" + worldName + "': Plugin '" + split[0] + "' does not exist");
                    } else if (!plugin.isEnabled()) {
                        logger.severe("Could not set generator for default world '" + worldName + "': Plugin '" + split[0] + "' is not enabled yet (is it load:STARTUP?)");
                    } else {
                        result = plugin.getDefaultWorldGenerator(worldName, id);
                    }
                }
            }
        }

        return result;
    }
}
