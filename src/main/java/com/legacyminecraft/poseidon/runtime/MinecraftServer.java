package com.legacyminecraft.poseidon.runtime;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Runtime-local MinecraftServer facade used by migrated runtime systems.
 */
public class MinecraftServer implements ICommandListener, Runnable {
    public static final Logger log = Logger.getLogger("MinecraftServer");

    public final ServerConfigurationManager serverConfigurationManager = new ServerConfigurationManager();
    public final List<WorldServer> worlds = new ArrayList<WorldServer>();
    public PropertyManager propertyManager = new PropertyManager();
    public NetworkListenThread networkListenThread = new NetworkListenThread();
    public final Server server = new Server();
    public final ConsoleCommandSender console = new ConsoleCommandSender();

    public boolean spawnAnimals = true;
    public boolean isStopped = false;

    public MinecraftServer() {
    }

    public MinecraftServer(Object options) {
    }

    public WorldServer getWorldServer(int dimension) {
        for (WorldServer world : worlds) {
            if (world.dimension == dimension) {
                return world;
            }
        }
        WorldServer worldServer = new WorldServer();
        worldServer.dimension = dimension;
        worlds.add(worldServer);
        return worldServer;
    }

    public void issueCommand(String command, ICommandListener source) {
    }

    public void c(String message) {
        log.info(message);
    }

    public void a() {
        isStopped = true;
    }

    @Override
    public void run() {
        isStopped = true;
    }

    @Override
    public String getName() {
        return "Server";
    }

    @Override
    public void sendMessage(String message) {
        log.info(message);
    }

    public static void main(Object options) {
    }

    public static boolean isRunning(MinecraftServer server) {
        return server != null && !server.isStopped;
    }
}
