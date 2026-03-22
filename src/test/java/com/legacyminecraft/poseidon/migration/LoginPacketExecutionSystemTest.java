package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.api.network.ConnectionType;
import com.legacyminecraft.poseidon.auth.login.LoginGatekeepingExecutionSystem;
import com.legacyminecraft.poseidon.auth.login.LoginPacketGatekeepingPolicy;
import com.legacyminecraft.poseidon.auth.login.LoginPacketExecutionSystem;
import com.legacyminecraft.poseidon.network.LoginProxyAssignmentSystem;
import com.legacyminecraft.poseidon.network.LoginProxySessionApplySystem;
import com.legacyminecraft.poseidon.network.LoginProxySupport;
import com.legacyminecraft.poseidon.network.LoginSessionStateSystem;
import net.minecraft.server.Packet1Login;
import org.junit.Assert;
import org.junit.Test;

public class LoginPacketExecutionSystemTest {
    private final LoginPacketExecutionSystem loginPacketExecutionSystem = LoginPacketExecutionSystem.getInstance();
    private final LoginPacketGatekeepingPolicy loginPacketGatekeepingPolicy = LoginPacketGatekeepingPolicy.getInstance();
    private final LoginGatekeepingExecutionSystem loginGatekeepingExecutionSystem = LoginGatekeepingExecutionSystem.getInstance();
    private final LoginProxyAssignmentSystem loginProxyAssignmentSystem = LoginProxyAssignmentSystem.getInstance();
    private final LoginProxySessionApplySystem loginProxySessionApplySystem = LoginProxySessionApplySystem.getInstance();
    private final LoginSessionStateSystem loginSessionStateSystem = LoginSessionStateSystem.getInstance();

    @Test
    public void executeRejectsDuplicateLoginWithoutProxyOrLoginStart() {
        Packet1Login loginPacket = new Packet1Login("Notch", 14, 0L, (byte) 0);
        GatekeepingCapture gatekeepingCapture = new GatekeepingCapture();
        ProxyResolverCapture proxyResolverCapture = new ProxyResolverCapture(
                acceptedAssignment(ConnectionType.NORMAL, 0, false)
        );
        SessionStateCapture sessionStateCapture = new SessionStateCapture();
        LoginStartCapture loginStartCapture = new LoginStartCapture();

        boolean started = loginPacketExecutionSystem.execute(
                loginPacket,
                true,
                loginPacketGatekeepingPolicy,
                loginGatekeepingExecutionSystem,
                gatekeepingCapture,
                proxyResolverCapture,
                loginProxySessionApplySystem,
                loginSessionStateSystem,
                sessionStateCapture,
                loginStartCapture
        );

        Assert.assertFalse(started);
        Assert.assertEquals("Multiple login packets received.", gatekeepingCapture.disconnectMessage);
        Assert.assertEquals(0, proxyResolverCapture.resolveCalls);
        Assert.assertFalse(loginStartCapture.started);
    }

    @Test
    public void executeStopsWhenProxyAssignmentRejected() {
        Packet1Login loginPacket = new Packet1Login("Player", 14, 0L, (byte) 0);
        GatekeepingCapture gatekeepingCapture = new GatekeepingCapture();
        ProxyResolverCapture proxyResolverCapture = new ProxyResolverCapture(
                rejectedAssignment(ConnectionType.BUNGEECORD_OFFLINE_MODE_IP_FORWARDING, 2, false)
        );
        SessionStateCapture sessionStateCapture = new SessionStateCapture();
        LoginStartCapture loginStartCapture = new LoginStartCapture();

        boolean started = loginPacketExecutionSystem.execute(
                loginPacket,
                false,
                loginPacketGatekeepingPolicy,
                loginGatekeepingExecutionSystem,
                gatekeepingCapture,
                proxyResolverCapture,
                loginProxySessionApplySystem,
                loginSessionStateSystem,
                sessionStateCapture,
                loginStartCapture
        );

        Assert.assertFalse(started);
        Assert.assertTrue(gatekeepingCapture.loginPacketMarked);
        Assert.assertEquals("Player", gatekeepingCapture.username);
        Assert.assertEquals(1, proxyResolverCapture.resolveCalls);
        Assert.assertEquals(ConnectionType.BUNGEECORD_OFFLINE_MODE_IP_FORWARDING, sessionStateCapture.connectionType);
        Assert.assertFalse(loginStartCapture.started);
    }

    @Test
    public void executeStartsLoginWhenGatekeepingAndProxySucceed() {
        Packet1Login loginPacket = new Packet1Login("Alex", 14, 0L, (byte) 0);
        GatekeepingCapture gatekeepingCapture = new GatekeepingCapture();
        ProxyResolverCapture proxyResolverCapture = new ProxyResolverCapture(
                acceptedAssignment(ConnectionType.RELEASE2BETA_ONLINE_MODE_IP_FORWARDING, 26, true)
        );
        SessionStateCapture sessionStateCapture = new SessionStateCapture();
        LoginStartCapture loginStartCapture = new LoginStartCapture();

        boolean started = loginPacketExecutionSystem.execute(
                loginPacket,
                false,
                loginPacketGatekeepingPolicy,
                loginGatekeepingExecutionSystem,
                gatekeepingCapture,
                proxyResolverCapture,
                loginProxySessionApplySystem,
                loginSessionStateSystem,
                sessionStateCapture,
                loginStartCapture
        );

        Assert.assertTrue(started);
        Assert.assertTrue(gatekeepingCapture.loginPacketMarked);
        Assert.assertEquals("Alex", gatekeepingCapture.username);
        Assert.assertEquals(1, proxyResolverCapture.resolveCalls);
        Assert.assertEquals(ConnectionType.RELEASE2BETA_ONLINE_MODE_IP_FORWARDING, sessionStateCapture.connectionType);
        Assert.assertEquals(26, sessionStateCapture.rawConnectionType);
        Assert.assertTrue(sessionStateCapture.usingReleaseToBeta);
        Assert.assertTrue(loginStartCapture.started);
        Assert.assertSame(loginPacket, loginStartCapture.packet1Login);
    }

    private LoginProxyAssignmentSystem.ProxyAssignment acceptedAssignment(
            ConnectionType connectionType,
            int rawConnectionType,
            boolean usingReleaseToBeta
    ) {
        return loginProxyAssignmentSystem.map(
                LoginProxySupport.ProxyHandlingResult.accepted(connectionType, rawConnectionType, usingReleaseToBeta)
        );
    }

    private LoginProxyAssignmentSystem.ProxyAssignment rejectedAssignment(
            ConnectionType connectionType,
            int rawConnectionType,
            boolean usingReleaseToBeta
    ) {
        return loginProxyAssignmentSystem.map(
                LoginProxySupport.ProxyHandlingResult.rejected(connectionType, rawConnectionType, usingReleaseToBeta)
        );
    }

    private static final class GatekeepingCapture implements LoginGatekeepingExecutionSystem.GatekeepingActions {
        private boolean loginPacketMarked;
        private String username;
        private String disconnectMessage;

        @Override
        public void markLoginPacketReceived() {
            this.loginPacketMarked = true;
        }

        @Override
        public void updateUsername(String username) {
            this.username = username;
        }

        @Override
        public void disconnect(String message) {
            this.disconnectMessage = message;
        }
    }

    private static final class ProxyResolverCapture implements LoginPacketExecutionSystem.ProxyAssignmentResolver {
        private final LoginProxyAssignmentSystem.ProxyAssignment proxyAssignment;
        private int resolveCalls;

        private ProxyResolverCapture(LoginProxyAssignmentSystem.ProxyAssignment proxyAssignment) {
            this.proxyAssignment = proxyAssignment;
        }

        @Override
        public LoginProxyAssignmentSystem.ProxyAssignment resolveProxy(Packet1Login loginPacket) {
            this.resolveCalls++;
            return this.proxyAssignment;
        }
    }

    private static final class SessionStateCapture implements LoginProxySessionApplySystem.SessionStateSink {
        private ConnectionType connectionType;
        private int rawConnectionType;
        private boolean usingReleaseToBeta;

        @Override
        public void apply(ConnectionType connectionType, int rawConnectionType, boolean usingReleaseToBeta) {
            this.connectionType = connectionType;
            this.rawConnectionType = rawConnectionType;
            this.usingReleaseToBeta = usingReleaseToBeta;
        }
    }

    private static final class LoginStartCapture implements LoginPacketExecutionSystem.LoginStartActions {
        private boolean started;
        private Packet1Login packet1Login;

        @Override
        public void finishLogin(Packet1Login loginPacket) {
            this.started = true;
            this.packet1Login = loginPacket;
        }
    }
}
