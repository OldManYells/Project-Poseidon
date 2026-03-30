package org.bukkit.craftbukkit.network;

import net.minecraft.server.Packet;
import net.minecraft.server.ThreadMonitorConnection;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.PoseidonConfig;
import org.bukkit.event.player.PlayerReceivePacketEvent;

import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class NetworkManager {

    // Kept for compatibility with old code.
    public static final Object a = new Object();
    public static int b;
    public static int c;
    public static int[] d = new int[256]; // incoming stats by packet id
    public static int[] e = new int[256]; // outgoing stats by packet id

    // Kept public for CraftBukkit compatibility.
    public Socket socket;

    // Kept public for compatibility.
    public int f = 0; // packet send delay / throttling

    private final Object queueLock = new Object();

    private SocketAddress remoteAddress;
    private DataInputStream input;
    private DataOutputStream output;

    private boolean running = true;

    private List inboundPackets = Collections.synchronizedList(new ArrayList());
    private List highPriorityQueue = Collections.synchronizedList(new ArrayList());
    private List lowPriorityQueue = Collections.synchronizedList(new ArrayList());

    private NetHandler netHandler;

    private boolean closing = false;
    private Thread writeThread;
    private Thread readThread;
    private boolean disconnected = false;

    private String disconnectReason = "";
    private Object[] disconnectArgs;

    private int noReadTicks = 0;
    private int queuedBytes = 0;

    private int lowPriorityQueueDelay = 50;

    private final boolean firePacketEvents;
    private final boolean spamDetection;
    private final int threshold;

    public NetworkManager(Socket socket, String threadName, NetHandler netHandler) {
        this.socket = socket;
        this.remoteAddress = socket.getRemoteSocketAddress();
        this.netHandler = netHandler;

        this.firePacketEvents = PoseidonConfig.getInstance().getBoolean("settings.packet-events.enabled", false);
        this.spamDetection = PoseidonConfig.getInstance().getBoolean("settings.packet-spam-detection.enabled", true);
        this.threshold = PoseidonConfig.getInstance().getInt("settings.packet-spam-detection.threshold", 1000);

        try {
            socket.setTrafficClass(24);
        } catch (SocketException ignored) {
        }

        try {
            socket.setSoTimeout(30000);

            if (PoseidonConfig.getEmptyNode().getBoolean("settings.enable-tpc-nodelay", false)) {
                socket.setTcpNoDelay(true);
            }

            this.input = new DataInputStream(socket.getInputStream());
            this.output = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream(), 5120));
        } catch (java.io.IOException ex) {
            System.err.println(ex.getMessage());
        }

        this.readThread = new NetworkReaderThread(this, threadName + " read thread");
        this.writeThread = new NetworkWriterThread(this, threadName + " write thread");
        this.readThread.start();
        this.writeThread.start();
    }

    public void setSocketAddress(SocketAddress socketAddress) {
        this.remoteAddress = socketAddress;
    }

    public SocketAddress generateSocketAddress(String hostname, int port) {
        return new InetSocketAddress(hostname, port);
    }

    public void setNetHandler(NetHandler netHandler) {
        this.netHandler = netHandler;
    }

    public void queue(Packet packet) {
        if (!this.closing) {
            synchronized (this.queueLock) {
                this.queuedBytes += packet.a() + 1;

                if (packet.k) {
                    this.lowPriorityQueue.add(packet);
                } else {
                    this.highPriorityQueue.add(packet);
                }
            }
        }
    }

    private boolean sendQueuedPacket() {
        boolean sentAny = false;

        try {
            Packet packet;
            int[] stats;
            int packetId;

            if (!this.highPriorityQueue.isEmpty()
                    && (this.f == 0 || System.currentTimeMillis() - ((Packet) this.highPriorityQueue.get(0)).timestamp >= (long) this.f)) {

                synchronized (this.queueLock) {
                    packet = (Packet) this.highPriorityQueue.remove(0);
                    this.queuedBytes -= packet.a() + 1;
                }

                Packet.a(packet, this.output);
                stats = e;
                packetId = packet.b();
                stats[packetId] += packet.a() + 1;
                sentAny = true;
            }

            if ((sentAny || this.lowPriorityQueueDelay-- <= 0)
                    && !this.lowPriorityQueue.isEmpty()
                    && (this.highPriorityQueue.isEmpty()
                    || ((Packet) this.highPriorityQueue.get(0)).timestamp > ((Packet) this.lowPriorityQueue.get(0)).timestamp)) {

                synchronized (this.queueLock) {
                    packet = (Packet) this.lowPriorityQueue.remove(0);
                    this.queuedBytes -= packet.a() + 1;
                }

                Packet.a(packet, this.output);
                stats = e;
                packetId = packet.b();
                stats[packetId] += packet.a() + 1;
                this.lowPriorityQueueDelay = 0;
                sentAny = true;
            }

            return sentAny;
        } catch (Exception ex) {
            if (!this.disconnected) {
                this.handleException(ex);
            }

            return false;
        }
    }

    public void interruptThreads() {
        this.readThread.interrupt();
        this.writeThread.interrupt();
    }

    private boolean readPacket() {
        boolean readAny = false;

        try {
            Packet packet = Packet.a(this.input, this.netHandler.c());

            if (packet != null) {
                int[] stats = d;
                int packetId = packet.b();

                stats[packetId] += packet.a() + 1;
                this.inboundPackets.add(packet);
                readAny = true;
            } else {
                this.disconnect("disconnect.endOfStream", new Object[0]);
            }

            return readAny;
        } catch (Exception ex) {
            if (!this.disconnected) {
                this.handleException(ex);
            }

            return false;
        }
    }

    private void handleException(Exception ex) {
        ex.printStackTrace();
        this.disconnect("disconnect.genericReason", new Object[] { "Internal exception: " + ex.toString() });
    }

    public void disconnect(String reason, Object... args) {
        if (this.running) {
            this.disconnected = true;
            this.disconnectReason = reason;
            this.disconnectArgs = args;
            (new NetworkMasterThread(this)).start();
            this.running = false;

            try {
                this.input.close();
                this.input = null;
            } catch (Throwable ignored) {
            }

            try {
                this.output.close();
                this.output = null;
            } catch (Throwable ignored) {
            }

            try {
                this.socket.close();
                this.socket = null;
            } catch (Throwable ignored) {
            }
        }
    }

    public void processPackets() {
        boolean fastPackets = PoseidonConfig.getInstance().getBoolean("settings.faster-packets.enabled", true);

        if (this.queuedBytes > (fastPackets ? 2097152 : 1048576)) {
            this.disconnect("disconnect.overflow", new Object[0]);
        }

        if (this.inboundPackets.isEmpty()) {
            if (this.noReadTicks++ == 1200) {
                this.disconnect("disconnect.timeout", new Object[0]);
            }
        } else {
            this.noReadTicks = 0;
        }

        int packetBudget = fastPackets ? 1000 : 100;

        if (this.spamDetection) {
            if (this.inboundPackets.size() > this.threshold) {
                String playerUsername = "Unknown";

                if (this.netHandler instanceof NetServerHandler) {
                    playerUsername = ((NetServerHandler) this.netHandler).player.name;
                    ((NetServerHandler) this.netHandler).disconnect(ChatColor.RED + "[Poseidon] You have been kicked for packet spamming.");
                } else {
                    this.disconnect("disconnect.spam", new Object[0]);
                }

                System.out.println(
                        "[Poseidon] Player " + playerUsername
                                + " has been kicked for packet spamming. The queue size was "
                                + this.inboundPackets.size()
                                + " and the threshold was " + this.threshold + "."
                );
            }
        }

        while (!this.inboundPackets.isEmpty() && packetBudget-- >= 0) {
            Packet packet = (Packet) this.inboundPackets.remove(0);

            if (this.firePacketEvents && this.netHandler instanceof NetServerHandler) {
                PlayerReceivePacketEvent event =
                        new PlayerReceivePacketEvent(((NetServerHandler) this.netHandler).player.name, packet);

                Bukkit.getPluginManager().callEvent(event);
                packet = event.getPacket();

                if (!event.isCancelled()) {
                    packet.a(this.netHandler);
                }
            } else {
                packet.a(this.netHandler);
            }
        }

        this.interruptThreads();

        if (this.disconnected && this.inboundPackets.isEmpty()) {
            this.netHandler.a(this.disconnectReason, this.disconnectArgs);
        }
    }

    public SocketAddress getSocketAddress() {
        return this.remoteAddress;
    }

    public void closeConnection() {
        this.interruptThreads();
        this.closing = true;
        this.readThread.interrupt();
        (new ThreadMonitorConnection(this)).start();
    }

    public int getLowPriorityQueueSize() {
        return this.lowPriorityQueue.size();
    }

    public boolean isRunning() {
        return this.running;
    }

    // ---------------------------------------------------------------------
    // Compatibility bridge methods for old obfuscated call sites
    // ---------------------------------------------------------------------

    public void a(NetHandler nethandler) {
        this.setNetHandler(nethandler);
    }

    public void a() {
        this.interruptThreads();
    }

    public void a(String s, Object... aobject) {
        this.disconnect(s, aobject);
    }

    public void b() {
        this.processPackets();
    }

    public void d() {
        this.closeConnection();
    }

    public int e() {
        return this.getLowPriorityQueueSize();
    }

    private boolean f() {
        return this.sendQueuedPacket();
    }

    private boolean g() {
        return this.readPacket();
    }

    private void a(Exception exception) {
        this.handleException(exception);
    }

    // ---------------------------------------------------------------------
    // Synthetic-access compatibility helpers used by companion thread classes
    // ---------------------------------------------------------------------

    public static boolean a(NetworkManager networkmanager) {
        return networkmanager.running;
    }

    static boolean b(NetworkManager networkmanager) {
        return networkmanager.closing;
    }

    static boolean c(NetworkManager networkmanager) {
        return networkmanager.readPacket();
    }

    static boolean d(NetworkManager networkmanager) {
        return networkmanager.sendQueuedPacket();
    }

    static DataOutputStream e(NetworkManager networkmanager) {
        return networkmanager.output;
    }

    static boolean f(NetworkManager networkmanager) {
        return networkmanager.disconnected;
    }

    static void a(NetworkManager networkmanager, Exception exception) {
        networkmanager.handleException(exception);
    }

    static Thread g(NetworkManager networkmanager) {
        return networkmanager.readThread;
    }

    public static Thread h(NetworkManager networkmanager) {
        return networkmanager.writeThread;
    }
}