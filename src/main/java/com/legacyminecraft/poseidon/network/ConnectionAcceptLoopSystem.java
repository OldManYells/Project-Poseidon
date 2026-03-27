package com.legacyminecraft.poseidon.network;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

/**
 * Canonical accept-loop for inbound socket connections.
 */
public final class ConnectionAcceptLoopSystem {
    private static final ConnectionAcceptLoopSystem INSTANCE = new ConnectionAcceptLoopSystem();

    private final ConnectionAttemptThrottleSystem connectionAttemptThrottleSystem = ConnectionAttemptThrottleSystem.getInstance();
    private final ConnectionAcceptThrottlePolicy connectionAcceptThrottlePolicy =
            ConnectionAcceptThrottlePolicy.getInstance();

    private ConnectionAcceptLoopSystem() {
    }

    public static ConnectionAcceptLoopSystem getInstance() {
        return INSTANCE;
    }

    public void runLoop(AcceptLoopOperations operations) {
        Map connectionAttemptsByAddress = new HashMap();

        while (operations.isRunning()) {
            try {
                Socket socket = operations.accept();
                if (socket == null) {
                    continue;
                }

                InetAddress inetaddress = socket.getInetAddress();
                if (connectionAttemptThrottleSystem.shouldThrottle(
                        connectionAttemptsByAddress,
                        inetaddress,
                        operations.currentTimeMillis(),
                        connectionAcceptThrottlePolicy.defaultThrottleMillis()
                )) {
                    socket.close();
                } else {
                    operations.onAccepted(socket);
                }
            } catch (Exception exception) {
                operations.onAcceptError(exception);
            }
        }
    }

    public interface AcceptLoopOperations {
        boolean isRunning();

        Socket accept() throws IOException;

        long currentTimeMillis();

        void onAccepted(Socket socket) throws Exception;

        void onAcceptError(Exception exception);
    }
}
