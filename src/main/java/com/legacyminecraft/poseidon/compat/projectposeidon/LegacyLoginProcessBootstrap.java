package com.legacyminecraft.poseidon.compat.projectposeidon;

import com.legacyminecraft.poseidon.auth.login.LoginProcessCallbacks;
import net.minecraft.server.NetLoginHandler;
import net.minecraft.server.Packet1Login;
import org.bukkit.Server;
import org.bukkit.craftbukkit.CraftServer;

/**
 * Bridge that starts the legacy login pipeline behind a canonical callback contract.
 */
public final class LegacyLoginProcessBootstrap {
    private LegacyLoginProcessBootstrap() {
    }

    public static LoginProcessCallbacks start(NetLoginHandler handler, Packet1Login loginPacket, Server server, boolean onlineMode) {
        if (!(server instanceof CraftServer)) {
            throw new IllegalStateException("Legacy login bootstrap requires CraftServer compatibility adapter");
        }
        return new com.projectposeidon.johnymuffin.LoginProcessHandler(
                handler,
                loginPacket,
                (CraftServer) server,
                onlineMode
        );
    }
}
