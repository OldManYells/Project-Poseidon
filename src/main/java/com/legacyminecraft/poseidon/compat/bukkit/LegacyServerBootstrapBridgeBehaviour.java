package com.legacyminecraft.poseidon.compat.bukkit;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.ServerConfigurationManager;
import org.bukkit.Server;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.craftbukkit.command.ColouredConsoleSender;

/**
 * Canonical Bukkit bridge for bootstrapping legacy CraftBukkit server bindings.
 */
public final class LegacyServerBootstrapBridgeBehaviour {
    private static final LegacyServerBootstrapBridgeBehaviour INSTANCE = new LegacyServerBootstrapBridgeBehaviour();

    private LegacyServerBootstrapBridgeBehaviour() {
    }

    public static LegacyServerBootstrapBridgeBehaviour getInstance() {
        return INSTANCE;
    }

    public Server bootstrap(MinecraftServer minecraftServer, ServerConfigurationManager configurationManager) {
        CraftServer craftServer = new CraftServer(minecraftServer, configurationManager);
        minecraftServer.server = craftServer;
        minecraftServer.console = new ColouredConsoleSender(craftServer);
        return craftServer;
    }
}
