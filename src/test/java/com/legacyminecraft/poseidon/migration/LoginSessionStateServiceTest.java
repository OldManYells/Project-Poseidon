package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.api.network.ConnectionType;
import com.legacyminecraft.poseidon.network.LoginProxyAssignmentSystem;
import com.legacyminecraft.poseidon.network.LoginSessionStateSystem;
import org.junit.Assert;
import org.junit.Test;

public class LoginSessionStateServiceTest {
    @Test
    public void keepAliveFlagRemainsTrueOnceMarked() {
        LoginSessionStateSystem service = LoginSessionStateSystem.getInstance();

        Assert.assertTrue(service.markReceivedKeepAlive(false));
        Assert.assertTrue(service.markReceivedKeepAlive(true));
    }

    @Test
    public void proxyAssignmentMapsIntoSessionProxyState() {
        LoginSessionStateSystem service = LoginSessionStateSystem.getInstance();
        LoginProxyAssignmentSystem.ProxyAssignment assignment =
                LoginProxyAssignmentSystem.getInstance()
                        .map(com.legacyminecraft.poseidon.network.LoginProxySupport.ProxyHandlingResult.accepted(
                                ConnectionType.RELEASE2BETA,
                                26,
                                true
                        ));

        LoginSessionStateSystem.ProxyState proxyState = service.applyProxyAssignment(assignment);

        Assert.assertEquals(ConnectionType.RELEASE2BETA, proxyState.getConnectionType());
        Assert.assertEquals(26, proxyState.getRawConnectionType());
        Assert.assertTrue(proxyState.isUsingReleaseToBeta());
    }
}
