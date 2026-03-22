package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.network.ConnectionAttemptThrottleSystem;
import org.junit.Assert;
import org.junit.Test;

import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;

public class ConnectionAttemptThrottleServiceTest {
    @Test
    public void allowsFirstAttemptAndThrottlesRapidRepeatForRemoteAddress() throws Exception {
        ConnectionAttemptThrottleSystem service = ConnectionAttemptThrottleSystem.getInstance();
        Map attempts = new HashMap();
        InetAddress remote = InetAddress.getByName("203.0.113.10");

        boolean first = service.shouldThrottle(attempts, remote, 1000L, 5000L);
        boolean second = service.shouldThrottle(attempts, remote, 2000L, 5000L);

        Assert.assertFalse(first);
        Assert.assertTrue(second);
    }

    @Test
    public void doesNotThrottleLoopback() throws Exception {
        ConnectionAttemptThrottleSystem service = ConnectionAttemptThrottleSystem.getInstance();
        Map attempts = new HashMap();
        InetAddress loopback = InetAddress.getByName("127.0.0.1");

        boolean first = service.shouldThrottle(attempts, loopback, 1000L, 5000L);
        boolean second = service.shouldThrottle(attempts, loopback, 2000L, 5000L);

        Assert.assertFalse(first);
        Assert.assertFalse(second);
    }
}
