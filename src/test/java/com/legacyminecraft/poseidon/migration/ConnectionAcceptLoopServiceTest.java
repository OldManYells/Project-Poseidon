package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.network.ConnectionAcceptLoopSystem;
import org.junit.Assert;
import org.junit.Test;

import java.net.InetAddress;
import java.net.Socket;

public class ConnectionAcceptLoopServiceTest {
    @Test
    public void throttlesSecondRapidRemoteConnection() throws Exception {
        final TestSocket first = new TestSocket(InetAddress.getByName("203.0.113.30"));
        final TestSocket second = new TestSocket(InetAddress.getByName("203.0.113.30"));
        final TestSocket[] sockets = new TestSocket[]{first, second};
        final int[] index = new int[]{0};
        final int[] accepted = new int[]{0};

        ConnectionAcceptLoopSystem.getInstance().runLoop(new ConnectionAcceptLoopSystem.AcceptLoopOperations() {
            @Override
            public boolean isRunning() {
                return index[0] < sockets.length;
            }

            @Override
            public Socket accept() {
                return sockets[index[0]++];
            }

            @Override
            public long currentTimeMillis() {
                return 1000L + index[0] * 1000L;
            }

            @Override
            public void onAccepted(Socket socket) {
                accepted[0]++;
            }

            @Override
            public void onAcceptError(Exception exception) {
                Assert.fail("Unexpected accept error: " + exception.getMessage());
            }
        });

        Assert.assertEquals(1, accepted[0]);
        Assert.assertFalse(first.closed);
        Assert.assertTrue(second.closed);
    }

    @Test
    public void allowsLoopbackRapidConnections() throws Exception {
        final TestSocket first = new TestSocket(InetAddress.getByName("127.0.0.1"));
        final TestSocket second = new TestSocket(InetAddress.getByName("127.0.0.1"));
        final TestSocket[] sockets = new TestSocket[]{first, second};
        final int[] index = new int[]{0};
        final int[] accepted = new int[]{0};

        ConnectionAcceptLoopSystem.getInstance().runLoop(new ConnectionAcceptLoopSystem.AcceptLoopOperations() {
            @Override
            public boolean isRunning() {
                return index[0] < sockets.length;
            }

            @Override
            public Socket accept() {
                return sockets[index[0]++];
            }

            @Override
            public long currentTimeMillis() {
                return 1000L + index[0] * 1000L;
            }

            @Override
            public void onAccepted(Socket socket) {
                accepted[0]++;
            }

            @Override
            public void onAcceptError(Exception exception) {
                Assert.fail("Unexpected accept error: " + exception.getMessage());
            }
        });

        Assert.assertEquals(2, accepted[0]);
        Assert.assertFalse(first.closed);
        Assert.assertFalse(second.closed);
    }

    @Test
    public void reportsRuntimeAcceptHandlerErrorsAndContinuesLoop() throws Exception {
        final TestSocket first = new TestSocket(InetAddress.getByName("198.51.100.10"));
        final TestSocket second = new TestSocket(InetAddress.getByName("198.51.100.11"));
        final TestSocket[] sockets = new TestSocket[]{first, second};
        final int[] index = new int[]{0};
        final int[] accepted = new int[]{0};
        final int[] acceptErrors = new int[]{0};

        ConnectionAcceptLoopSystem.getInstance().runLoop(new ConnectionAcceptLoopSystem.AcceptLoopOperations() {
            @Override
            public boolean isRunning() {
                return index[0] < sockets.length;
            }

            @Override
            public Socket accept() {
                return sockets[index[0]++];
            }

            @Override
            public long currentTimeMillis() {
                return 1000L + index[0] * 1000L;
            }

            @Override
            public void onAccepted(Socket socket) {
                if (accepted[0] == 0) {
                    accepted[0]++;
                    throw new IllegalStateException("login handler construction failed");
                }
                accepted[0]++;
            }

            @Override
            public void onAcceptError(Exception exception) {
                acceptErrors[0]++;
            }
        });

        Assert.assertEquals(2, accepted[0]);
        Assert.assertEquals(1, acceptErrors[0]);
    }

    private static final class TestSocket extends Socket {
        private final InetAddress inetAddress;
        private boolean closed;

        private TestSocket(InetAddress inetAddress) {
            this.inetAddress = inetAddress;
            this.closed = false;
        }

        @Override
        public InetAddress getInetAddress() {
            return inetAddress;
        }

        @Override
        public synchronized void close() {
            this.closed = true;
        }
    }
}
