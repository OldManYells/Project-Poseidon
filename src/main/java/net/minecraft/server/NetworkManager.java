package net.minecraft.server;

import com.legacyminecraft.poseidon.PoseidonConfig;
import com.legacyminecraft.poseidon.network.InboundQueueReadSystem;
import com.legacyminecraft.poseidon.network.NetworkCloseMonitorStartSystem;
import com.legacyminecraft.poseidon.network.NetworkDisconnectLifecycleSystem;
import com.legacyminecraft.poseidon.network.NetworkManagerTickSystem;
import com.legacyminecraft.poseidon.network.NetworkManagerConsoleLogBehaviour;
import com.legacyminecraft.poseidon.network.NetworkSocketSystem;
import com.legacyminecraft.poseidon.network.NetworkSocketInitializationFailureBehaviour;
import com.legacyminecraft.poseidon.network.NetworkTickFinalizationSystem;
import com.legacyminecraft.poseidon.network.InboundQueueReadExecutionSystem;
import com.legacyminecraft.poseidon.network.OutboundQueueDrainExecutionSystem;
import com.legacyminecraft.poseidon.network.OutboundQueueEnqueueExecutionSystem;
import com.legacyminecraft.poseidon.network.OutboundQueueSystem;
import com.legacyminecraft.poseidon.network.NetworkTransportConfigPolicy;
import com.legacyminecraft.poseidon.network.OutboundQueueDelayPolicy;
import com.legacyminecraft.poseidon.network.NetworkExceptionDisconnectSystem;
import com.legacyminecraft.poseidon.network.NetworkExceptionLogBehaviour;
import com.legacyminecraft.poseidon.network.NetworkDisconnectArgumentPolicy;
import com.legacyminecraft.poseidon.network.NetworkDisconnectKeyPolicy;
import com.legacyminecraft.poseidon.network.PacketEventConfigPolicy;
import com.legacyminecraft.poseidon.network.PacketSpamDetectionConfigPolicy;
import com.legacyminecraft.poseidon.network.NetworkThreadInterruptSystem;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

public class NetworkManager {
    private static final Logger LOGGER = Logger.getLogger(NetworkManager.class.getName());

    public static final Object a = new Object();
    public static int b;
    public static int c;
    private Object g = new Object();
    public Socket socket; // CraftBukkit - private -> public
    private SocketAddress i; //Project Poseidon - remove final statement
    private DataInputStream input;
    private DataOutputStream output;
    private boolean l = true;
    private List m = Collections.synchronizedList(new ArrayList());
    private List highPriorityQueue = Collections.synchronizedList(new ArrayList());
    private List lowPriorityQueue = Collections.synchronizedList(new ArrayList());
    private NetHandler p;
    private boolean q = false;
    private Thread r;
    private Thread s;
    private boolean t = false;
    private String u = "";
    private Object[] v;
    private int w = 0;
    private int x = 0;
    public static int[] d = new int[256];
    public static int[] e = new int[256];
    public int f = 0;
    private final OutboundQueueDelayPolicy outboundQueueDelayPolicy = OutboundQueueDelayPolicy.getInstance();
    private final PacketEventConfigPolicy packetEventConfigPolicy = PacketEventConfigPolicy.getInstance();
    private final PacketSpamDetectionConfigPolicy packetSpamDetectionConfigPolicy =
            PacketSpamDetectionConfigPolicy.getInstance();
    private final NetworkTransportConfigPolicy networkTransportConfigPolicy = NetworkTransportConfigPolicy.getInstance();
    private int lowPriorityQueueDelay = outboundQueueDelayPolicy.initialLowPriorityQueueDelay();
    private final boolean firePacketEvents;

    private final boolean spamDetection;

    private final int threshold;
    private final NetworkManagerTickSystem networkManagerTickSystem = NetworkManagerTickSystem.getInstance();
    private final NetworkManagerConsoleLogBehaviour networkManagerConsoleLogBehaviour =
            NetworkManagerConsoleLogBehaviour.getInstance();
    private final InboundQueueReadSystem inboundQueueReadSystem = InboundQueueReadSystem.getInstance();
    private final InboundQueueReadExecutionSystem inboundQueueReadExecutionSystem = InboundQueueReadExecutionSystem.getInstance();
    private final OutboundQueueSystem outboundQueueSystem = OutboundQueueSystem.getInstance();
    private final OutboundQueueEnqueueExecutionSystem outboundQueueEnqueueExecutionSystem =
            OutboundQueueEnqueueExecutionSystem.getInstance();
    private final OutboundQueueDrainExecutionSystem outboundQueueDrainExecutionSystem =
            OutboundQueueDrainExecutionSystem.getInstance();
    private final NetworkThreadInterruptSystem networkThreadInterruptSystem = NetworkThreadInterruptSystem.getInstance();
    private final NetworkExceptionDisconnectSystem networkExceptionDisconnectSystem =
            NetworkExceptionDisconnectSystem.getInstance();
    private final NetworkExceptionLogBehaviour networkExceptionLogBehaviour =
            NetworkExceptionLogBehaviour.getInstance();
    private final NetworkDisconnectLifecycleSystem networkDisconnectLifecycleSystem = NetworkDisconnectLifecycleSystem.getInstance();
    private final NetworkCloseMonitorStartSystem networkCloseMonitorStartSystem = NetworkCloseMonitorStartSystem.getInstance();
    private final NetworkSocketSystem networkSocketSystem = NetworkSocketSystem.getInstance();
    private final NetworkSocketInitializationFailureBehaviour networkSocketInitializationFailureBehaviour =
            NetworkSocketInitializationFailureBehaviour.getInstance();
    private final NetworkDisconnectKeyPolicy networkDisconnectKeyPolicy = NetworkDisconnectKeyPolicy.getInstance();
    private final NetworkDisconnectArgumentPolicy networkDisconnectArgumentPolicy =
            NetworkDisconnectArgumentPolicy.getInstance();
    private final NetworkTickFinalizationSystem networkTickFinalizationSystem = NetworkTickFinalizationSystem.getInstance();
    private final NetworkDisconnectLifecycleSystem.DisconnectActions disconnectActions =
            new NetworkDisconnectLifecycleSystem.DisconnectActions() {
                @Override
                public void startMasterThread() {
                    (new NetworkMasterThread(NetworkManager.this)).start();
                }

                @Override
                public void closeResources() {
                    networkSocketSystem.closeQuietly(NetworkManager.this.input, NetworkManager.this.output, NetworkManager.this.socket);
                    NetworkManager.this.input = null;
                    NetworkManager.this.output = null;
                    NetworkManager.this.socket = null;
                }
            };
    private final NetworkManagerTickSystem.TickActions tickActions = new NetworkManagerTickSystem.TickActions() {
        @Override
        public void disconnect(String key) {
            NetworkManager.this.a(key, networkDisconnectArgumentPolicy.emptyArgs());
        }

        @Override
        public void kickPlayer(String reason) {
            NetServerHandler playerHandler = resolvePlayerHandler();
            if (playerHandler != null) {
                playerHandler.disconnect(reason);
            }
        }

        @Override
        public void log(String message) {
            networkManagerConsoleLogBehaviour.log(LOGGER, message);
        }
    };
    private final NetworkTickFinalizationSystem.TickFinalizationActions tickFinalizationActions =
            new NetworkTickFinalizationSystem.TickFinalizationActions() {
                @Override
                public void interruptNetworkThreads() {
                    NetworkManager.this.a();
                }

                @Override
                public void notifyDisconnect(String key, Object[] args) {
                    NetworkManager.this.p.a(key, args);
                }
            };
    private final InboundQueueReadExecutionSystem.InboundReadActions inboundReadActions =
            new InboundQueueReadExecutionSystem.InboundReadActions() {
                @Override
                public void disconnectEndOfStream() {
                    NetworkManager.this.a(
                            networkDisconnectKeyPolicy.endOfStream(),
                            networkDisconnectArgumentPolicy.emptyArgs()
                    );
                }

                @Override
                public void handleException(Exception exception) {
                    if (!NetworkManager.this.t) {
                        NetworkManager.this.a(exception);
                    }
                }
            };
    private final InboundQueueReadSystem.PacketReader inboundPacketReader = new InboundQueueReadSystem.PacketReader() {
        @Override
        public Object read(DataInputStream input, Object handler) throws java.io.IOException {
            return Packet.a(input, ((NetHandler) handler).c());
        }
    };
    private final OutboundQueueDrainExecutionSystem.OutboundDrainActions outboundDrainActions =
            new OutboundQueueDrainExecutionSystem.OutboundDrainActions() {
                @Override
                public void handleException(Exception exception) {
                    if (!NetworkManager.this.t) {
                        NetworkManager.this.a(exception);
                    }
                }
            };
    private final NetworkCloseMonitorStartSystem.CloseMonitorActions closeMonitorActions =
            new NetworkCloseMonitorStartSystem.CloseMonitorActions() {
                @Override
                public void interruptNetworkThreads() {
                    NetworkManager.this.a();
                }

                @Override
                public void markShuttingDown() {
                    NetworkManager.this.q = true;
                }

                @Override
                public void interruptReaderThread() {
                    NetworkManager.this.s.interrupt();
                }

                @Override
                public void startCloseMonitorThread() {
                    (new ThreadMonitorConnection(NetworkManager.this)).start();
                }
            };
    private final NetworkExceptionDisconnectSystem.ExceptionActions exceptionActions =
            new NetworkExceptionDisconnectSystem.ExceptionActions() {
                @Override
                public void printStackTrace(Exception exception) {
                    networkExceptionLogBehaviour.logUnexpectedException(LOGGER, exception);
                }

                @Override
                public void disconnectWithGenericReason(String reason) {
                    NetworkManager.this.a(
                            networkDisconnectKeyPolicy.genericReason(),
                            networkDisconnectArgumentPolicy.genericReasonArgs(reason)
                    );
                }
            };

    public NetworkManager(Socket socket, String s, NetHandler nethandler) {
        PoseidonNetworkCompatGatewayBootstrap.ensureInstalled();
        this.socket = socket;
        this.i = socket.getRemoteSocketAddress();
        this.p = nethandler;

        //Poseidon
        this.firePacketEvents = PoseidonConfig.getInstance().getBoolean(
                packetEventConfigPolicy.packetEventsEnabledKey(),
                packetEventConfigPolicy.packetEventsEnabledDefault()
        );
        this.spamDetection = PoseidonConfig.getInstance().getBoolean(
                packetSpamDetectionConfigPolicy.spamDetectionEnabledKey(),
                packetSpamDetectionConfigPolicy.spamDetectionEnabledDefault()
        );
        this.threshold = PoseidonConfig.getInstance().getInt(
                packetSpamDetectionConfigPolicy.spamDetectionThresholdKey(),
                packetSpamDetectionConfigPolicy.spamDetectionThresholdDefault()
        );

        //Debug for packet spam detection
//        System.out.println("[Poseidon] Packet spam detection is " + (this.spamDetection ? "enabled" : "disabled") + " with a threshold of " + this.threshold + " packets");

        // CraftBukkit start - IPv6 stack in Java on BSD/OSX doesn't support setTrafficClass
        try {
            NetworkSocketSystem.StreamPair streamPair = networkSocketSystem.openConfiguredStreams(
                    socket,
                    PoseidonConfig.getInstance().getBoolean(
                            networkTransportConfigPolicy.tcpNoDelayKey(),
                            networkTransportConfigPolicy.tcpNoDelayDefault()
                    )
            );
            this.input = streamPair.getInput();
            this.output = streamPair.getOutput();
        } catch (java.io.IOException socketexception) {
            // CraftBukkit end
            networkSocketInitializationFailureBehaviour.logInitializationFailure(LOGGER, socketexception);
        }
        this.s = new NetworkReaderThread(this, s + " read thread");
        this.r = new NetworkWriterThread(this, s + " write thread");
        this.s.start();
        this.r.start();
    }

    //Project Poseidon Start
    public void setSocketAddress(SocketAddress socketAddress) {
        this.i = socketAddress;
    }

    public SocketAddress generateSocketAddress(String hostname, int port) {
        return new InetSocketAddress(hostname, port);
    }

    //Project Poseidon End

    public void a(NetHandler nethandler) {
        this.p = nethandler;
    }

    public void queue(Packet packet) {
        OutboundQueueEnqueueExecutionSystem.EnqueueStepResult enqueueStepResult =
                outboundQueueEnqueueExecutionSystem.execute(
                        this.q,
                        this.g,
                        this.highPriorityQueue,
                        this.lowPriorityQueue,
                        packet,
                        this.x,
                        outboundQueueSystem
                );
        this.x = enqueueStepResult.getQueuedBytes();
    }

    private boolean f() {
        OutboundQueueDrainExecutionSystem.DrainStepResult drainStepResult = outboundQueueDrainExecutionSystem.execute(
                this.g,
                this.highPriorityQueue,
                this.lowPriorityQueue,
                this.x,
                this.lowPriorityQueueDelay,
                this.f,
                System.currentTimeMillis(),
                this.output,
                e,
                outboundQueueSystem,
                this.outboundDrainActions
        );
        this.x = drainStepResult.getQueuedBytes();
        this.lowPriorityQueueDelay = drainStepResult.getLowPriorityQueueDelay();
        return drainStepResult.wrotePacket();
    }

    public void a() {
        networkThreadInterruptSystem.interrupt(this.s, this.r);
    }

    private boolean g() {
        InboundQueueReadExecutionSystem.ReadStepResult readStepResult = inboundQueueReadExecutionSystem.execute(
                this.input,
                this.p,
                d,
                this.m,
                inboundQueueReadSystem,
                this.inboundPacketReader,
                this.inboundReadActions
        );
        return readStepResult.isPacketQueued();
    }

    private void a(Exception exception) {
        networkExceptionDisconnectSystem.execute(exception, this.exceptionActions);
    }

    public void a(String s, Object... aobject) {
        NetworkDisconnectLifecycleSystem.DisconnectState disconnectState = networkDisconnectLifecycleSystem.disconnectIfOpen(
                this.l,
                s,
                aobject,
                this.disconnectActions
        );

        if (disconnectState.isChanged()) {
            this.t = disconnectState.isTerminating();
            this.u = disconnectState.getDisconnectKey();
            this.v = disconnectState.getDisconnectArgs();
            this.l = disconnectState.isOpen();
        }
    }

    public void b() {
        boolean fastPacketsEnabled = PoseidonConfig.getInstance().getBoolean(
                networkTransportConfigPolicy.fasterPacketsEnabledKey(),
                networkTransportConfigPolicy.fasterPacketsEnabledDefault()
        );
        String playerUsername = resolvePlayerUsername();

        NetworkManagerTickSystem.TickState tickState = networkManagerTickSystem.tick(
                new NetworkManagerTickSystem.TickRequest(
                        fastPacketsEnabled,
                        this.x,
                        this.m.isEmpty(),
                        this.w,
                        spamDetection,
                        this.m.size(),
                        threshold,
                        playerUsername,
                        hasPlayerHandler(),
                        this.m,
                        this.p,
                        firePacketEvents
                ),
                this.tickActions
        );
        this.w = tickState.getNextIdleTicks();
        networkTickFinalizationSystem.finalizeTick(
                this.t,
                this.m.isEmpty(),
                this.u,
                this.v,
                this.tickFinalizationActions
        );
    }

    public SocketAddress getSocketAddress() {
        return this.i;
    }

    public void d() {
        networkCloseMonitorStartSystem.start(this.closeMonitorActions);
    }

    public int e() {
        return this.lowPriorityQueue.size();
    }

    private String resolvePlayerUsername() {
        NetServerHandler playerHandler = resolvePlayerHandler();
        if (playerHandler != null) {
            return playerHandler.player.name;
        }
        return "Unknown";
    }

    private NetServerHandler resolvePlayerHandler() {
        if (this.p instanceof NetServerHandler) {
            return (NetServerHandler) this.p;
        }
        return null;
    }

    private boolean hasPlayerHandler() {
        return resolvePlayerHandler() != null;
    }

    static boolean a(NetworkManager networkmanager) {
        return networkmanager.l;
    }

    static boolean b(NetworkManager networkmanager) {
        return networkmanager.q;
    }

    static boolean c(NetworkManager networkmanager) {
        return networkmanager.g();
    }

    static boolean d(NetworkManager networkmanager) {
        return networkmanager.f();
    }

    static DataOutputStream e(NetworkManager networkmanager) {
        return networkmanager.output;
    }

    static boolean f(NetworkManager networkmanager) {
        return networkmanager.t;
    }

    static void a(NetworkManager networkmanager, Exception exception) {
        networkmanager.a(exception);
    }

    static Thread g(NetworkManager networkmanager) {
        return networkmanager.s;
    }

    static Thread h(NetworkManager networkmanager) {
        return networkmanager.r;
    }
}
