package net.minecraft.server;

import com.legacyminecraft.poseidon.network.ConnectionAcceptLoopSystem;
import com.legacyminecraft.poseidon.network.ConnectionAcceptExceptionPolicy;
import com.legacyminecraft.poseidon.network.NetworkConnectionLabelPolicy;

import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

class NetworkAcceptThread extends Thread {
    private static final Logger LOGGER = Logger.getLogger(NetworkAcceptThread.class.getName());

    final MinecraftServer a;

    final NetworkListenThread b;
    private final ConnectionAcceptLoopSystem connectionAcceptLoopSystem = ConnectionAcceptLoopSystem.getInstance();
    private final ConnectionAcceptExceptionPolicy connectionAcceptExceptionPolicy = ConnectionAcceptExceptionPolicy.getInstance();
    private final NetworkConnectionLabelPolicy networkConnectionLabelPolicy = NetworkConnectionLabelPolicy.getInstance();
    private final ConnectionAcceptLoopSystem.AcceptLoopOperations acceptLoopOperations =
            new ConnectionAcceptLoopSystem.AcceptLoopOperations() {
                @Override
                public boolean isRunning() {
                    return NetworkAcceptThread.this.b.b;
                }

                @Override
                public Socket accept() throws IOException {
                    return NetworkListenThread.a(NetworkAcceptThread.this.b).accept();
                }

                @Override
                public long currentTimeMillis() {
                    return System.currentTimeMillis();
                }

                @Override
                public void onAccepted(Socket socket) {
                    NetLoginHandler netloginhandler = new NetLoginHandler(
                            NetworkAcceptThread.this.a,
                            socket,
                            networkConnectionLabelPolicy.buildConnectionLabel(
                                    NetworkListenThread.b(NetworkAcceptThread.this.b)
                            )
                    );
                    NetworkListenThread.a(NetworkAcceptThread.this.b, netloginhandler);
                }

                @Override
                public void onAcceptError(Exception exception) {
                    if (!(exception instanceof IOException)) {
                        LOGGER.log(Level.WARNING, "Network accept loop failed", exception);
                        return;
                    }
                    IOException ioexception = (IOException) exception;
                    if (!connectionAcceptExceptionPolicy.isExpectedShutdownException(ioexception)) {
                        LOGGER.log(Level.WARNING, "Network accept loop failed", ioexception);
                    }
                }
            };

    NetworkAcceptThread(NetworkListenThread networklistenthread, String s, MinecraftServer minecraftserver) {
        super(s);
        this.b = networklistenthread;
        this.a = minecraftserver;
    }

    public void run() {
        connectionAcceptLoopSystem.runLoop(this.acceptLoopOperations);
    }
}
