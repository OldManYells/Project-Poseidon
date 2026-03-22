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
    private static final long DEFAULT_THROTTLE_MILLIS = 5000L;

    private final ConnectionAttemptThrottleSystem connectionAttemptThrottleSystem = ConnectionAttemptThrottleSystem.getInstance();

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
                        DEFAULT_THROTTLE_MILLIS
                )) {
                    socket.close();
                } else {
                    operations.onAccepted(socket);
                }
            } catch (IOException ioexception) {
                operations.onAcceptError(ioexception);
            }
        }
    }

    public interface AcceptLoopOperations {
        boolean isRunning();

        Socket accept() throws IOException;

        long currentTimeMillis();

        void onAccepted(Socket socket) throws IOException;

        void onAcceptError(IOException ioexception);
    }
}
