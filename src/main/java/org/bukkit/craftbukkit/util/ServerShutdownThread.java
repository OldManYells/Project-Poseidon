
package org.bukkit.craftbukkit.util;

import com.legacyminecraft.poseidon.compat.bukkit.ServerShutdownThreadBehaviour;
import net.minecraft.server.MinecraftServer;

public class ServerShutdownThread extends Thread {
    private final MinecraftServer server;
    private final ServerShutdownThreadBehaviour serverShutdownThreadBehaviour = ServerShutdownThreadBehaviour.getInstance();

    public ServerShutdownThread(MinecraftServer server) {
        this.server = server;
        this.setName("Server Shutdown Thread");
    }

    @Override
    public void run() {
        serverShutdownThreadBehaviour.requestStop(this.server);
    }
}
