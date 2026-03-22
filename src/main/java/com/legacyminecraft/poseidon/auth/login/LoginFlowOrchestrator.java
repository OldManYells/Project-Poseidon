package com.legacyminecraft.poseidon.auth.login;

import com.legacyminecraft.poseidon.compat.projectposeidon.LegacyLoginProcessBootstrap;
import net.minecraft.server.NetLoginHandler;
import net.minecraft.server.Packet1Login;
import org.bukkit.Server;

/**
 * Canonical login flow orchestration before transitioning to play state.
 */
public final class LoginFlowOrchestrator {
    private static final LoginFlowOrchestrator INSTANCE = new LoginFlowOrchestrator();

    private LoginFlowOrchestrator() {
    }

    public static LoginFlowOrchestrator getInstance() {
        return INSTANCE;
    }

    public void startLoginFlow(NetLoginHandler loginHandler, Packet1Login loginPacket, Server server, boolean onlineMode, boolean shuttingDown, String shutdownKickMessage) {
        if (shuttingDown) {
            loginHandler.disconnect(shutdownKickMessage);
            return;
        }

        LegacyLoginProcessBootstrap.start(loginHandler, loginPacket, server, onlineMode);
    }
}
