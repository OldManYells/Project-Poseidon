package com.legacyminecraft.poseidon.auth.login;

import com.legacyminecraft.poseidon.api.network.ConnectionType;
import com.legacyminecraft.poseidon.compat.bukkit.ServerShutdownStateBridgeBehaviour;
import com.legacyminecraft.poseidon.network.PlayerSessionConnector;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.NetLoginHandler;
import net.minecraft.server.Packet1Login;
import org.bukkit.Server;

/**
 * Canonical service for transitioning from login handshake into authenticated play session.
 */
public final class LoginTransitionService {
    private static final LoginTransitionService INSTANCE = new LoginTransitionService();
    private final LoginFlowOrchestrator loginFlowOrchestrator = LoginFlowOrchestrator.getInstance();
    private final PlayerSessionConnector playerSessionConnector = PlayerSessionConnector.getInstance();
    private final ServerShutdownStateBridgeBehaviour shutdownStateBridge = ServerShutdownStateBridgeBehaviour.getInstance();

    private LoginTransitionService() {
    }

    public static LoginTransitionService getInstance() {
        return INSTANCE;
    }

    public void startLoginFlow(
            NetLoginHandler loginHandler,
            Packet1Login loginPacket,
            MinecraftServer minecraftServer,
            Server bukkitServer,
            String shutdownKickMessage
    ) {
        loginFlowOrchestrator.startLoginFlow(
                loginHandler,
                loginPacket,
                bukkitServer,
                minecraftServer.onlineMode,
                shutdownStateBridge.isShuttingDown(bukkitServer),
                shutdownKickMessage
        );
    }

    public CompletionResult completeAuthenticatedSession(
            NetLoginHandler loginHandler,
            Packet1Login loginPacket,
            MinecraftServer minecraftServer,
            boolean usingReleaseToBeta,
            ConnectionType connectionType,
            int rawConnectionType,
            boolean receivedKeepAlive
    ) {
        playerSessionConnector.connectAuthenticatedPlayer(
                loginHandler,
                loginPacket,
                minecraftServer,
                usingReleaseToBeta,
                connectionType,
                rawConnectionType,
                receivedKeepAlive
        );

        return CompletionResult.completed();
    }

    public static final class CompletionResult {
        private final boolean markLoginComplete;

        private CompletionResult(boolean markLoginComplete) {
            this.markLoginComplete = markLoginComplete;
        }

        public static CompletionResult completed() {
            return new CompletionResult(true);
        }

        public boolean shouldMarkLoginComplete() {
            return markLoginComplete;
        }
    }
}
