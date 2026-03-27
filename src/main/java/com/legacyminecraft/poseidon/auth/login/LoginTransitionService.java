package com.legacyminecraft.poseidon.auth.login;

import com.legacyminecraft.poseidon.api.network.ConnectionType;
import com.legacyminecraft.compat.bukkit.ServerShutdownStateBridgeBehaviour;
import com.legacyminecraft.poseidon.network.PlayerSessionConnector;

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
            Object loginHandler,
            Object loginPacket,
            Object minecraftServer,
            Object bukkitServer,
            String shutdownKickMessage
    ) {
        loginFlowOrchestrator.startLoginFlow(
                loginHandler,
                loginPacket,
                bukkitServer,
                Boolean.TRUE.equals(Bridge.readField(minecraftServer, "onlineMode")),
                shutdownStateBridge.isShuttingDown(bukkitServer),
                shutdownKickMessage
        );
    }

    public CompletionResult completeAuthenticatedSession(
            Object loginHandler,
            Object loginPacket,
            Object minecraftServer,
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

    private static final class Bridge {
        private static Object readField(Object target, String fieldName) {
            try {
                java.lang.reflect.Field field = target.getClass().getField(fieldName);
                field.setAccessible(true);
                return field.get(target);
            } catch (Exception exception) {
                throw new IllegalStateException(exception);
            }
        }
    }
}
