package com.legacyminecraft.poseidon.network;

import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;

/**
 * Canonical socket stream bootstrap and teardown helper for legacy network wrappers.
 */
public final class NetworkSocketSystem {
    private static final NetworkSocketSystem INSTANCE = new NetworkSocketSystem();

    private NetworkSocketSystem() {
    }

    public static NetworkSocketSystem getInstance() {
        return INSTANCE;
    }

    public StreamPair openConfiguredStreams(Socket socket, boolean enableTcpNoDelay) throws IOException {
        try {
            socket.setTrafficClass(24);
        } catch (SocketException e) {
            ;
        }

        socket.setSoTimeout(30000);
        if (enableTcpNoDelay) {
            socket.setTcpNoDelay(true);
        }

        DataInputStream input = new DataInputStream(socket.getInputStream());
        DataOutputStream output = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream(), 5120));
        return new StreamPair(input, output);
    }

    public void closeQuietly(DataInputStream input, DataOutputStream output, Socket socket) {
        try {
            if (input != null) {
                input.close();
            }
        } catch (Throwable throwable) {
            ;
        }

        try {
            if (output != null) {
                output.close();
            }
        } catch (Throwable throwable1) {
            ;
        }

        try {
            if (socket != null) {
                socket.close();
            }
        } catch (Throwable throwable2) {
            ;
        }
    }

    public static final class StreamPair {
        private final DataInputStream input;
        private final DataOutputStream output;

        private StreamPair(DataInputStream input, DataOutputStream output) {
            this.input = input;
            this.output = output;
        }

        public DataInputStream getInput() {
            return input;
        }

        public DataOutputStream getOutput() {
            return output;
        }
    }
}
