package net.minecraft.server;

import com.legacyminecraft.poseidon.api.network.ConnectionType;
import com.legacyminecraft.poseidon.auth.login.LoginConnectionLifecycleSystem;
import com.legacyminecraft.poseidon.auth.login.LoginConnectionLossExecutionSystem;
import com.legacyminecraft.poseidon.auth.login.LoginAuthenticatedSessionExecutionSystem;
import com.legacyminecraft.poseidon.auth.login.LoginCompletionStateApplySystem;
import com.legacyminecraft.poseidon.auth.login.LoginDisconnectExecutionSystem;
import com.legacyminecraft.poseidon.auth.login.LoginFlowStartExecutionSystem;
import com.legacyminecraft.poseidon.auth.login.LoginGatekeepingExecutionSystem;
import com.legacyminecraft.poseidon.auth.login.LoginIdentityPolicy;
import com.legacyminecraft.poseidon.auth.login.LoginPacketGatekeepingPolicy;
import com.legacyminecraft.poseidon.auth.login.LoginPacketExecutionSystem;
import com.legacyminecraft.poseidon.auth.login.LoginPendingPacketExecutionSystem;
import com.legacyminecraft.poseidon.auth.login.LoginProtocolErrorExecutionSystem;
import com.legacyminecraft.poseidon.auth.login.LoginTickExecutionSystem;
import com.legacyminecraft.poseidon.auth.login.LoginTickOrchestrationSystem;
import com.legacyminecraft.poseidon.auth.login.LoginTickPolicy;
import com.legacyminecraft.poseidon.auth.login.LoginTransitionSystem;
import com.legacyminecraft.poseidon.network.LoginHandshakePacketHandler;
import com.legacyminecraft.poseidon.network.LoginHandshakeExecutionSystem;
import com.legacyminecraft.poseidon.network.LoginShutdownMessageConfigPolicy;
import com.legacyminecraft.poseidon.network.LoginProxyAssignmentSystem;
import com.legacyminecraft.poseidon.network.LoginProxySessionApplySystem;
import com.legacyminecraft.poseidon.network.LoginSessionStateSystem;
import com.legacyminecraft.poseidon.PoseidonConfig;
import org.bukkit.Server;

import java.net.Socket;
import java.util.Random;
import java.util.logging.Logger;

public class NetLoginHandler extends NetHandler {

    public static Logger a = Logger.getLogger("Minecraft");
    private static Random d = new Random();
    private static final int LOGIN_TIMEOUT_TICKS = 600;
    public NetworkManager networkManager;
    public boolean c = false;
    private MinecraftServer minecraftServer;
    private int f = 0;
    private String g = null;
    private Packet1Login h = null;
    private String serverId = "";
    private ConnectionType connectionType;
    private boolean usingReleaseToBeta = false; //Poseidon -> Release2Beta support
    private boolean receivedLoginPacket = false;
    private int rawConnectionType;
    private boolean receivedKeepAlive = false;
    private final LoginConnectionLifecycleSystem loginConnectionLifecycleSystem = LoginConnectionLifecycleSystem.getInstance();
    private final LoginHandshakePacketHandler loginHandshakePacketHandler = LoginHandshakePacketHandler.getInstance();
    private final LoginHandshakeExecutionSystem loginHandshakeExecutionSystem = LoginHandshakeExecutionSystem.getInstance();
    private final LoginSessionStateSystem loginSessionStateSystem = LoginSessionStateSystem.getInstance();
    private final LoginGatekeepingExecutionSystem loginGatekeepingExecutionSystem = LoginGatekeepingExecutionSystem.getInstance();
    private final LoginPacketExecutionSystem loginPacketExecutionSystem = LoginPacketExecutionSystem.getInstance();
    private final LoginIdentityPolicy loginIdentityPolicy = LoginIdentityPolicy.getInstance();
    private final LoginPacketGatekeepingPolicy loginPacketGatekeepingPolicy = LoginPacketGatekeepingPolicy.getInstance();
    private final LoginProxyAssignmentSystem loginProxyAssignmentSystem = LoginProxyAssignmentSystem.getInstance();
    private final LoginProxySessionApplySystem loginProxySessionApplySystem = LoginProxySessionApplySystem.getInstance();
    private final LoginFlowStartExecutionSystem loginFlowStartExecutionSystem = LoginFlowStartExecutionSystem.getInstance();
    private final LoginTickExecutionSystem loginTickExecutionSystem = LoginTickExecutionSystem.getInstance();
    private final LoginTickOrchestrationSystem loginTickOrchestrationSystem = LoginTickOrchestrationSystem.getInstance();
    private final LoginTickPolicy loginTickPolicy = LoginTickPolicy.getInstance();
    private final LoginTransitionSystem loginTransitionSystem = LoginTransitionSystem.getInstance();
    private final LoginAuthenticatedSessionExecutionSystem loginAuthenticatedSessionExecutionSystem =
            LoginAuthenticatedSessionExecutionSystem.getInstance();
    private final LoginDisconnectExecutionSystem loginDisconnectExecutionSystem = LoginDisconnectExecutionSystem.getInstance();
    private final LoginConnectionLossExecutionSystem loginConnectionLossExecutionSystem = LoginConnectionLossExecutionSystem.getInstance();
    private final LoginCompletionStateApplySystem loginCompletionStateApplySystem = LoginCompletionStateApplySystem.getInstance();
    private final LoginPendingPacketExecutionSystem loginPendingPacketExecutionSystem =
            LoginPendingPacketExecutionSystem.getInstance();
    private final LoginProtocolErrorExecutionSystem loginProtocolErrorExecutionSystem = LoginProtocolErrorExecutionSystem.getInstance();
    private final LoginShutdownMessageConfigPolicy loginShutdownMessageConfigPolicy =
            LoginShutdownMessageConfigPolicy.getInstance();
    private Packet1Login pendingLoginFlowPacket;
    private Packet1Login pendingAuthenticatedSessionPacket;
    private final LoginTickExecutionSystem.TickActions loginTickActions = new LoginTickExecutionSystem.TickActions() {
        @Override
        public void processDeferredLogin() {
            NetLoginHandler.this.b(NetLoginHandler.this.h);
            NetLoginHandler.this.h = null;
        }

        @Override
        public void disconnect(String message) {
            NetLoginHandler.this.disconnect(message);
        }

        @Override
        public void pollNetwork() {
            NetLoginHandler.this.networkManager.b();
        }
    };
    private final LoginProxySessionApplySystem.SessionStateSink loginProxySessionStateSink =
            new LoginProxySessionApplySystem.SessionStateSink() {
                @Override
                public void apply(ConnectionType connectionType, int rawConnectionType, boolean usingReleaseToBeta) {
                    NetLoginHandler.this.connectionType = connectionType;
                    NetLoginHandler.this.rawConnectionType = rawConnectionType;
                    NetLoginHandler.this.usingReleaseToBeta = usingReleaseToBeta;
                }
            };
    private final LoginGatekeepingExecutionSystem.GatekeepingActions loginGatekeepingActions =
            new LoginGatekeepingExecutionSystem.GatekeepingActions() {
                @Override
                public void markLoginPacketReceived() {
                    NetLoginHandler.this.receivedLoginPacket = true;
                }

                @Override
                public void updateUsername(String username) {
                    NetLoginHandler.this.g = username;
                }

                @Override
                public void disconnect(String message) {
                    NetLoginHandler.this.disconnect(message);
                }
            };
    private final LoginHandshakeExecutionSystem.HandshakeActions loginHandshakeActions =
            new LoginHandshakeExecutionSystem.HandshakeActions() {
                @Override
                public void queueResponsePacket(Object responsePacket) {
                    NetLoginHandler.this.networkManager.queue((Packet) responsePacket);
                }
            };
    private final LoginCompletionStateApplySystem.CompletionStateSink loginCompletionStateSink =
            new LoginCompletionStateApplySystem.CompletionStateSink() {
                @Override
                public void markLoginComplete(boolean loginComplete) {
                    NetLoginHandler.this.c = loginComplete;
                }
            };
    private final LoginConnectionLossExecutionSystem.ConnectionLossActions loginConnectionLossActions =
            new LoginConnectionLossExecutionSystem.ConnectionLossActions() {
                @Override
                public void reportConnectionLost() {
                    loginConnectionLifecycleSystem.reportConnectionLost(a, NetLoginHandler.this.b());
                }

                @Override
                public void markLoginComplete() {
                    NetLoginHandler.this.c = true;
                }
            };
    private final LoginProtocolErrorExecutionSystem.ProtocolErrorActions loginProtocolErrorActions =
            new LoginProtocolErrorExecutionSystem.ProtocolErrorActions() {
                @Override
                public void disconnect(String message) {
                    NetLoginHandler.this.disconnect(message);
                }
            };
    private final LoginDisconnectExecutionSystem.DisconnectActions loginDisconnectActions =
            new LoginDisconnectExecutionSystem.DisconnectActions() {
                @Override
                public void disconnect(String message) {
                    loginConnectionLifecycleSystem.disconnect(
                            NetLoginHandler.this.networkManager,
                            a,
                            NetLoginHandler.this.b(),
                            message
                    );
                }

                @Override
                public void markLoginComplete() {
                    NetLoginHandler.this.c = true;
                }
            };
    private final LoginFlowStartExecutionSystem.LoginFlowActions loginFlowStartActions =
            new LoginFlowStartExecutionSystem.LoginFlowActions() {
                @Override
                public void startLoginFlow() {
                    Packet1Login packet1login = requirePendingLoginPacket(
                            NetLoginHandler.this.pendingLoginFlowPacket,
                            "login flow start"
                    );
                    Server bukkitServer = NetLoginHandler.this.minecraftServer.server;
                    loginTransitionSystem.startLoginFlow(
                            NetLoginHandler.this,
                            packet1login,
                            NetLoginHandler.this.minecraftServer,
                            bukkitServer,
                            NetLoginHandler.this.msgKickShutdown
                    );
                }
            };
    private final LoginAuthenticatedSessionExecutionSystem.CompletionActions loginAuthenticatedSessionActions =
            new LoginAuthenticatedSessionExecutionSystem.CompletionActions() {
                @Override
                public LoginTransitionSystem.CompletionResult completeAuthenticatedSession() {
                    Packet1Login packet1login = requirePendingLoginPacket(
                            NetLoginHandler.this.pendingAuthenticatedSessionPacket,
                            "authenticated session completion"
                    );
                    return loginTransitionSystem.completeAuthenticatedSession(
                            NetLoginHandler.this,
                            packet1login,
                            NetLoginHandler.this.minecraftServer,
                            NetLoginHandler.this.usingReleaseToBeta,
                            NetLoginHandler.this.connectionType,
                            NetLoginHandler.this.rawConnectionType,
                            NetLoginHandler.this.receivedKeepAlive
                    );
                }

                @Override
                public void applyCompletionState(LoginTransitionSystem.CompletionResult completionResult) {
                    loginCompletionStateApplySystem.applyCompletionState(
                            completionResult,
                            NetLoginHandler.this.loginCompletionStateSink
                    );
                }
            };
    private final LoginPacketExecutionSystem.ProxyAssignmentResolver loginProxyAssignmentResolver =
            new LoginPacketExecutionSystem.ProxyAssignmentResolver() {
                @Override
                public LoginProxyAssignmentSystem.ProxyAssignment resolveProxy(Object loginPacket) {
                    return loginProxyAssignmentSystem.resolveProxy(NetLoginHandler.this, loginPacket);
                }
            };
    private final LoginPacketExecutionSystem.LoginStartActions loginStartActions =
            new LoginPacketExecutionSystem.LoginStartActions() {
                @Override
                public void finishLogin(Object loginPacket) {
                    NetLoginHandler.this.finishLogin((Packet1Login) loginPacket);
                }
            };
    private final LoginPendingPacketExecutionSystem.PendingPacketState loginFlowPendingPacketState =
            new LoginPendingPacketExecutionSystem.PendingPacketState() {
                @Override
                public void set(Object loginPacket) {
                    NetLoginHandler.this.pendingLoginFlowPacket = (Packet1Login) loginPacket;
                }

                @Override
                public void clear() {
                    NetLoginHandler.this.pendingLoginFlowPacket = null;
                }
            };
    private final Runnable executeLoginFlowStart = new Runnable() {
        @Override
        public void run() {
            loginFlowStartExecutionSystem.execute(NetLoginHandler.this.loginFlowStartActions);
        }
    };
    private final LoginPendingPacketExecutionSystem.PendingPacketState authenticatedSessionPendingPacketState =
            new LoginPendingPacketExecutionSystem.PendingPacketState() {
                @Override
                public void set(Object loginPacket) {
                    NetLoginHandler.this.pendingAuthenticatedSessionPacket = (Packet1Login) loginPacket;
                }

                @Override
                public void clear() {
                    NetLoginHandler.this.pendingAuthenticatedSessionPacket = null;
                }
            };
    private final Runnable executeAuthenticatedSessionCompletion = new Runnable() {
        @Override
        public void run() {
            loginAuthenticatedSessionExecutionSystem.execute(NetLoginHandler.this.loginAuthenticatedSessionActions);
        }
    };

    private final String msgKickShutdown;

    public NetLoginHandler(MinecraftServer minecraftserver, Socket socket, String s) {
        this.minecraftServer = minecraftserver;
        this.networkManager = new NetworkManager(socket, s, this);
        this.networkManager.f = 0;

        this.msgKickShutdown = PoseidonConfig.getInstance().getConfigString(
                loginShutdownMessageConfigPolicy.kickShutdownKey()
        );
    }

    // CraftBukkit start
    public Socket getSocket() {
        return this.networkManager.socket;
    }
    // CraftBukkit end

    public void a() {
        this.f = loginTickOrchestrationSystem.execute(
                this.h,
                this.f,
                LOGIN_TIMEOUT_TICKS,
                loginTickPolicy,
                loginTickExecutionSystem,
                this.loginTickActions
        );
    }

    public void disconnect(String s) {
        loginDisconnectExecutionSystem.execute(s, this.loginDisconnectActions);
    }

    public void a(Packet2Handshake packet2handshake) {
        this.serverId = loginHandshakeExecutionSystem.executeHandshake(
                this.minecraftServer.onlineMode,
                this.serverId,
                d,
                loginHandshakePacketHandler,
                this.loginHandshakeActions
        );
    }

    public void a(Packet0KeepAlive packet0KeepAlive) {
        receivedKeepAlive = loginSessionStateSystem.markReceivedKeepAlive(receivedKeepAlive);
    }

    public boolean isCracked() {
        return loginIdentityPolicy.isCrackedUsername(this.g);
    }

    public void a(Packet1Login packet1login) {
        loginPacketExecutionSystem.execute(
                packet1login,
                receivedLoginPacket,
                loginPacketGatekeepingPolicy,
                loginGatekeepingExecutionSystem,
                this.loginGatekeepingActions,
                this.loginProxyAssignmentResolver,
                loginProxySessionApplySystem,
                loginSessionStateSystem,
                this.loginProxySessionStateSink,
                this.loginStartActions
        );
    }

    public void updateUsername(String newUsername) {
        this.g = newUsername;
    }

    public void finishLogin(Packet1Login packet1login) {
        loginPendingPacketExecutionSystem.execute(
                packet1login,
                this.loginFlowPendingPacketState,
                this.executeLoginFlowStart
        );
    }

    public void b(Packet1Login packet1login) {
        loginPendingPacketExecutionSystem.execute(
                packet1login,
                this.authenticatedSessionPendingPacketState,
                this.executeAuthenticatedSessionCompletion
        );
    }

    public void a(String s, Object[] aobject) {
        loginConnectionLossExecutionSystem.execute(this.loginConnectionLossActions);
    }

    public void a(Packet packet) {
        loginProtocolErrorExecutionSystem.execute(
                loginConnectionLifecycleSystem.getProtocolErrorMessage(),
                this.loginProtocolErrorActions
        );
    }

    public String b() {
        return loginIdentityPolicy.describeConnection(this.g, this.networkManager.getSocketAddress().toString());
    }

    //This can and will return null for multiple packets.
    public String getUsername() {
        return this.g;
    }

    public void setDeferredLoginPacket(Packet1Login loginPacket) {
        this.h = loginPacket;
    }

    public boolean c() {
        return true;
    }

    private Packet1Login requirePendingLoginPacket(Packet1Login packet1login, String stage) {
        if (packet1login == null) {
            throw new IllegalStateException("Missing pending login packet for " + stage);
        }
        return packet1login;
    }

    /**
     * @author moderator_man
     * @returns the session id for this player
     */
    public String getServerID() {
        return serverId;
    }

    static String a(NetLoginHandler netloginhandler) {
        return netloginhandler.serverId;
    }

    public static Packet1Login a(NetLoginHandler netloginhandler, Packet1Login packet1login) {
        netloginhandler.setDeferredLoginPacket(packet1login);
        return packet1login;
    }
}
