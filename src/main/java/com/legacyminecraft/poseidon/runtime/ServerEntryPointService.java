package com.legacyminecraft.poseidon.runtime;

import joptsimple.OptionSet;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.StatisticList;
import net.minecraft.server.ThreadServerApplication;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Canonical entrypoint launcher for legacy MinecraftServer.main wrapper.
 */
public final class ServerEntryPointService {
    private static final ServerEntryPointService INSTANCE = new ServerEntryPointService();

    private ServerEntryPointService() {
    }

    public static ServerEntryPointService getInstance() {
        return INSTANCE;
    }

    public void launch(OptionSet options, Logger logger) {
        StatisticList.a();

        try {
            MinecraftServer minecraftserver = new MinecraftServer(options);
            (new ThreadServerApplication("Server thread", minecraftserver)).start();
        } catch (Exception exception) {
            logger.log(Level.SEVERE, "Failed to start the minecraft server", exception);
        }
    }
}
