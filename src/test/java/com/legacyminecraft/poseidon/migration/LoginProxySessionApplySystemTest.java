package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.api.network.ConnectionType;
import com.legacyminecraft.poseidon.network.LoginProxyAssignmentSystem;
import com.legacyminecraft.poseidon.network.LoginProxySessionApplySystem;
import com.legacyminecraft.poseidon.network.LoginProxySupport;
import com.legacyminecraft.poseidon.network.LoginSessionStateSystem;
import org.junit.Assert;
import org.junit.Test;

public class LoginProxySessionApplySystemTest {
    private final LoginProxyAssignmentSystem loginProxyAssignmentSystem = LoginProxyAssignmentSystem.getInstance();
    private final LoginProxySessionApplySystem loginProxySessionApplySystem = LoginProxySessionApplySystem.getInstance();
    private final LoginSessionStateSystem loginSessionStateSystem = LoginSessionStateSystem.getInstance();

    @Test
    public void applyProxySessionStateAppliesAcceptedStateAndReturnsTrue() {
        LoginProxySupport.ProxyHandlingResult proxyHandlingResult =
                LoginProxySupport.ProxyHandlingResult.accepted(ConnectionType.RELEASE2BETA, 1, true);
        LoginProxyAssignmentSystem.ProxyAssignment proxyAssignment =
                loginProxyAssignmentSystem.map(proxyHandlingResult);
        SessionStateCapture capture = new SessionStateCapture();

        boolean accepted = loginProxySessionApplySystem.applyProxySessionState(
                proxyAssignment,
                loginSessionStateSystem,
                capture
        );

        Assert.assertTrue(accepted);
        Assert.assertEquals(ConnectionType.RELEASE2BETA, capture.connectionType);
        Assert.assertEquals(1, capture.rawConnectionType);
        Assert.assertTrue(capture.usingReleaseToBeta);
    }

    @Test
    public void applyProxySessionStateAppliesRejectedStateAndReturnsFalse() {
        LoginProxySupport.ProxyHandlingResult proxyHandlingResult =
                LoginProxySupport.ProxyHandlingResult.rejected(ConnectionType.BUNGEECORD_OFFLINE_MODE_IP_FORWARDING, 2, false);
        LoginProxyAssignmentSystem.ProxyAssignment proxyAssignment =
                loginProxyAssignmentSystem.map(proxyHandlingResult);
        SessionStateCapture capture = new SessionStateCapture();

        boolean accepted = loginProxySessionApplySystem.applyProxySessionState(
                proxyAssignment,
                loginSessionStateSystem,
                capture
        );

        Assert.assertFalse(accepted);
        Assert.assertEquals(ConnectionType.BUNGEECORD_OFFLINE_MODE_IP_FORWARDING, capture.connectionType);
        Assert.assertEquals(2, capture.rawConnectionType);
        Assert.assertFalse(capture.usingReleaseToBeta);
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
}
