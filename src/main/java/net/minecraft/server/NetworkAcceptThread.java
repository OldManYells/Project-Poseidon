package net.minecraft.server;

import com.legacyminecraft.poseidon.network.ConnectionAcceptLoopSystem;

import java.io.IOException;
import java.net.Socket;

class NetworkAcceptThread extends Thread {

    final MinecraftServer a;

    final NetworkListenThread b;
    private final ConnectionAcceptLoopSystem connectionAcceptLoopSystem = ConnectionAcceptLoopSystem.getInstance();
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
                            "Connection #" + NetworkListenThread.b(NetworkAcceptThread.this.b)
                    );
                    NetworkListenThread.a(NetworkAcceptThread.this.b, netloginhandler);
                }

                @Override
                public void onAcceptError(IOException ioexception) {
                    ioexception.printStackTrace();
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
