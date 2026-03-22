package com.legacyminecraft.poseidon.compat.bukkit;

import net.minecraft.server.MinecraftServer;

import java.util.logging.Handler;
import java.util.logging.Logger;

/**
 * Canonical behavior for CraftBukkit shutdown-thread stop delegation.
 */
public final class ServerShutdownThreadBehaviour {
    private static final ServerShutdownThreadBehaviour INSTANCE = new ServerShutdownThreadBehaviour();

    private ServerShutdownThreadBehaviour() {
    }

    public static ServerShutdownThreadBehaviour getInstance() {
        return INSTANCE;
    }

    public void requestStop(MinecraftServer server) {
        if (server == null || server.isStopped) {
            return;
        }
        disableConsoleHandlersDuringShutdown();
        server.stop();
    }

    private void disableConsoleHandlersDuringShutdown() {
        removeHandlers(MinecraftServer.log);
        removeHandlers(Logger.getLogger(""));
    }

    private void removeHandlers(Logger logger) {
        Handler[] handlers = logger.getHandlers();
        for (Handler handler : handlers) {
            logger.removeHandler(handler);
            try {
                handler.close();
            } catch (Exception ignored) {
            }
        }
    }
}
