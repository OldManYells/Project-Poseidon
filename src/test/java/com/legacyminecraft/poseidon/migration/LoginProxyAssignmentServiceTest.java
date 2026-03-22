package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.api.network.ConnectionType;
import com.legacyminecraft.poseidon.network.LoginProxyAssignmentSystem;
import com.legacyminecraft.poseidon.network.LoginProxySupport;
import org.junit.Assert;
import org.junit.Test;

public class LoginProxyAssignmentServiceTest {
    @Test
    public void mappingPreservesProxySupportResultFields() {
        LoginProxyAssignmentSystem service = LoginProxyAssignmentSystem.getInstance();

        LoginProxyAssignmentSystem.ProxyAssignment acceptedAssignment =
                service.map(LoginProxySupport.ProxyHandlingResult.accepted(ConnectionType.NORMAL, 7, false));
        Assert.assertTrue(acceptedAssignment.isAccepted());
        Assert.assertEquals(ConnectionType.NORMAL, acceptedAssignment.getConnectionType());
        Assert.assertEquals(7, acceptedAssignment.getRawConnectionType());
        Assert.assertFalse(acceptedAssignment.isUsingReleaseToBeta());

        LoginProxyAssignmentSystem.ProxyAssignment rejectedAssignment =
                service.map(LoginProxySupport.ProxyHandlingResult.rejected(ConnectionType.RELEASE2BETA, 26, true));
        Assert.assertFalse(rejectedAssignment.isAccepted());
        Assert.assertEquals(ConnectionType.RELEASE2BETA, rejectedAssignment.getConnectionType());
        Assert.assertEquals(26, rejectedAssignment.getRawConnectionType());
        Assert.assertTrue(rejectedAssignment.isUsingReleaseToBeta());
    }
}
