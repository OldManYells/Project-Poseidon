package com.legacyminecraft.poseidon.compat.projectposeidon;

import com.legacyminecraft.poseidon.auth.login.LoginProcessHandler;
import com.legacyminecraft.poseidon.auth.login.LoginProcessCallbacks;
import com.legacyminecraft.poseidon.auth.login.NetLoginHandler;
import com.legacyminecraft.poseidon.auth.login.Packet1Login;
import com.legacyminecraft.poseidon.auth.login.Server;

/**
 * Bridge that starts the legacy login pipeline behind a canonical callback contract.
 */
public final class LegacyLoginProcessBootstrap {
    private LegacyLoginProcessBootstrap() {
    }

    public static LoginProcessCallbacks start(NetLoginHandler handler, Packet1Login loginPacket, Server server, boolean onlineMode) {
        return new LoginProcessHandler(handler, loginPacket, server, onlineMode, null);
    }
}
