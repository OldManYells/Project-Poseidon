package com.legacyminecraft.poseidon.auth.login;

import com.legacyminecraft.poseidon.network.LoginProxyAssignmentSystem;
import com.legacyminecraft.poseidon.network.LoginProxySessionApplySystem;
import com.legacyminecraft.poseidon.network.LoginSessionStateSystem;
import net.minecraft.server.Packet1Login;

/**
 * Canonical orchestration flow for processing login packets from gatekeeping through proxy/session setup.
 */
public final class LoginPacketExecutionSystem {
    private static final LoginPacketExecutionSystem INSTANCE = new LoginPacketExecutionSystem();

    private LoginPacketExecutionSystem() {
    }

    public static LoginPacketExecutionSystem getInstance() {
        return INSTANCE;
    }

    public boolean execute(
            Packet1Login loginPacket,
            boolean receivedLoginPacket,
            LoginPacketGatekeepingPolicy loginPacketGatekeepingPolicy,
            LoginGatekeepingExecutionSystem loginGatekeepingExecutionSystem,
            LoginGatekeepingExecutionSystem.GatekeepingActions gatekeepingActions,
            ProxyAssignmentResolver proxyAssignmentResolver,
            LoginProxySessionApplySystem loginProxySessionApplySystem,
            LoginSessionStateSystem loginSessionStateSystem,
            LoginProxySessionApplySystem.SessionStateSink loginProxySessionStateSink,
            LoginStartActions loginStartActions
    ) {
        LoginPacketGatekeepingPolicy.GatekeepingResult gatekeepingResult =
                loginPacketGatekeepingPolicy.evaluate(loginPacket, receivedLoginPacket);
        if (!loginGatekeepingExecutionSystem.applyGatekeepingResult(gatekeepingResult, gatekeepingActions)) {
            return false;
        }

        LoginProxyAssignmentSystem.ProxyAssignment proxyAssignment = proxyAssignmentResolver.resolveProxy(loginPacket);
        if (!loginProxySessionApplySystem.applyProxySessionState(
                proxyAssignment,
                loginSessionStateSystem,
                loginProxySessionStateSink
        )) {
            return false;
        }

        loginStartActions.finishLogin(loginPacket);
        return true;
    }

    public interface ProxyAssignmentResolver {
        LoginProxyAssignmentSystem.ProxyAssignment resolveProxy(Packet1Login loginPacket);
    }

    public interface LoginStartActions {
        void finishLogin(Packet1Login loginPacket);
    }
}
