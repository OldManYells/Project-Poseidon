package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.network.NetworkListenBootstrap;
import net.minecraft.server.NetLoginHandler;
import org.junit.Assert;
import org.junit.Test;

import java.net.InetAddress;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;

public class NetworkListenBootstrapServiceTest {
    @Test
    public void opensServerSocket() throws Exception {
        NetworkListenBootstrap service = NetworkListenBootstrap.getInstance();
        ServerSocket serverSocket = service.openServerSocket(0, InetAddress.getByName("127.0.0.1"));

        try {
            Assert.assertTrue(serverSocket.isBound());
            Assert.assertTrue(serverSocket.getLocalPort() > 0);
        } finally {
            serverSocket.close();
        }
    }

    @Test
    public void startsAcceptThread() throws Exception {
        NetworkListenBootstrap service = NetworkListenBootstrap.getInstance();
        final boolean[] ran = new boolean[]{false};
        Thread thread = new Thread() {
            @Override
            public void run() {
                ran[0] = true;
            }
        };

        Thread started = service.startAcceptThread(thread);
        started.join(1000L);

        Assert.assertTrue(ran[0]);
    }

    @Test(expected = IllegalArgumentException.class)
    public void rejectsNullPendingLoginHandler() {
        NetworkListenBootstrap service = NetworkListenBootstrap.getInstance();
        List pending = new ArrayList();
        NetLoginHandler handler = null;

        service.addPendingLogin(pending, handler);
    }
}
