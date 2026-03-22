package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.network.NetworkSocketSystem;
import org.junit.Assert;
import org.junit.Test;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketException;

public class NetworkSocketServiceTest {
    @Test
    public void configuresSocketAndCreatesStreams() throws Exception {
        NetworkSocketSystem service = NetworkSocketSystem.getInstance();
        FakeSocket socket = new FakeSocket();

        NetworkSocketSystem.StreamPair streams = service.openConfiguredStreams(socket, true);

        Assert.assertEquals(24, socket.trafficClass);
        Assert.assertEquals(30000, socket.soTimeout);
        Assert.assertTrue(socket.tcpNoDelay);
        Assert.assertNotNull(streams.getInput());
        Assert.assertNotNull(streams.getOutput());
    }

    @Test
    public void closesInputOutputAndSocketQuietly() {
        NetworkSocketSystem service = NetworkSocketSystem.getInstance();
        CloseTrackingInputStream inputStream = new CloseTrackingInputStream();
        CloseTrackingOutputStream outputStream = new CloseTrackingOutputStream();
        FakeSocket socket = new FakeSocket();

        service.closeQuietly(new DataInputStream(inputStream), new DataOutputStream(outputStream), socket);

        Assert.assertTrue(inputStream.closed);
        Assert.assertTrue(outputStream.closed);
        Assert.assertTrue(socket.closed);
    }

    private static final class FakeSocket extends Socket {
        private int trafficClass = -1;
        private int soTimeout = -1;
        private boolean tcpNoDelay = false;
        private boolean closed = false;
        private final InputStream inputStream = new CloseTrackingInputStream();
        private final OutputStream outputStream = new CloseTrackingOutputStream();

        @Override
        public void setTrafficClass(int tc) throws SocketException {
            this.trafficClass = tc;
        }

        @Override
        public void setSoTimeout(int timeout) throws SocketException {
            this.soTimeout = timeout;
        }

        @Override
        public void setTcpNoDelay(boolean on) throws SocketException {
            this.tcpNoDelay = on;
        }

        @Override
        public InputStream getInputStream() {
            return inputStream;
        }

        @Override
        public OutputStream getOutputStream() {
            return outputStream;
        }

        @Override
        public synchronized void close() throws IOException {
            this.closed = true;
        }
    }

    private static final class CloseTrackingInputStream extends InputStream {
        private boolean closed = false;

        @Override
        public int read() {
            return -1;
        }

        @Override
        public void close() {
            this.closed = true;
        }
    }

    private static final class CloseTrackingOutputStream extends OutputStream {
        private boolean closed = false;

        @Override
        public void write(int b) {
        }

        @Override
        public void close() {
            this.closed = true;
        }
    }
}
