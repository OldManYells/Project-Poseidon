package com.legacyminecraft.poseidon.auth.login;

import com.legacyminecraft.poseidon.api.network.ConnectionType;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.NetLoginHandler;
import net.minecraft.server.Packet1Login;
import org.bukkit.Server;

/**
 * Role-aligned canonical facade for login-to-session transition orchestration.
 */
public final class LoginTransitionSystem {
    private static final LoginTransitionSystem INSTANCE = new LoginTransitionSystem();
    private final LoginTransitionService delegate = LoginTransitionService.getInstance();

    private LoginTransitionSystem() {
    }

    public static LoginTransitionSystem getInstance() {
        return INSTANCE;
    }

    public void startLoginFlow(
            NetLoginHandler loginHandler,
            Packet1Login loginPacket,
            MinecraftServer minecraftServer,
            Server bukkitServer,
            String shutdownKickMessage
    ) {
        delegate.startLoginFlow(loginHandler, loginPacket, minecraftServer, bukkitServer, shutdownKickMessage);
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
        return CompletionResult.wrap(delegate.completeAuthenticatedSession(
                loginHandler,
                loginPacket,
                minecraftServer,
                usingReleaseToBeta,
                connectionType,
                rawConnectionType,
                receivedKeepAlive
        ));
    }

    public static final class CompletionResult {
        private final LoginTransitionService.CompletionResult delegateResult;

        private CompletionResult(LoginTransitionService.CompletionResult delegateResult) {
            this.delegateResult = delegateResult;
        }

        static CompletionResult wrap(LoginTransitionService.CompletionResult delegateResult) {
            return new CompletionResult(delegateResult);
        }

        LoginTransitionService.CompletionResult toServiceResult() {
            return delegateResult;
        }

        public static CompletionResult completed() {
            return wrap(LoginTransitionService.CompletionResult.completed());
        }

        public boolean shouldMarkLoginComplete() {
            return delegateResult.shouldMarkLoginComplete();
        }
    }
}
