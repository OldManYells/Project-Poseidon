package com.legacyminecraft.poseidon.network;


import java.util.List;

/**
 * Canonical per-tick coordinator for NetworkManager queue policy, spam checks, and inbound packet dispatch.
 */
public final class NetworkManagerTickSystem {
    private static final NetworkManagerTickSystem INSTANCE = new NetworkManagerTickSystem();

    private final NetworkDisconnectKeyPolicy networkDisconnectKeyPolicy = NetworkDisconnectKeyPolicy.getInstance();
    private final NetworkTimeoutPolicy networkTimeoutPolicy = NetworkTimeoutPolicy.getInstance();
    private final ConnectionQueuePolicy connectionQueuePolicy = ConnectionQueuePolicy.getInstance();
    private final InboundPacketProcessingSystem inboundPacketProcessingSystem = InboundPacketProcessingSystem.getInstance();
    private final PacketSpamGuardSystem packetSpamGuardSystem = PacketSpamGuardSystem.getInstance();

    private NetworkManagerTickSystem() {
    }

    public static NetworkManagerTickSystem getInstance() {
        return INSTANCE;
    }

    public TickState tick(TickRequest request, TickActions actions) {
        if (connectionQueuePolicy.isOverflow(request.getQueuedBytes(), request.isFastModeEnabled())) {
            actions.disconnect(networkDisconnectKeyPolicy.overflow());
        }

        ConnectionQueuePolicy.TimeoutDecision timeoutDecision =
                connectionQueuePolicy.evaluateTimeout(
                        request.isInboundQueueEmpty(),
                        request.getIdleTicks(),
                        networkTimeoutPolicy.idleTimeoutTicks()
                );
        if (timeoutDecision.shouldDisconnect()) {
            actions.disconnect(networkDisconnectKeyPolicy.timeout());
        }

        PacketSpamGuardSystem.SpamDecision spamDecision = packetSpamGuardSystem.evaluate(
                request.isSpamDetectionEnabled(),
                request.getInboundQueueSize(),
                request.getSpamThreshold(),
                request.getUsername(),
                request.isPlayerConnection()
        );
        if (spamDecision.shouldKickPlayer()) {
            actions.kickPlayer(spamDecision.getKickReason());
        }
        if (spamDecision.shouldDisconnectConnection()) {
            actions.disconnect(spamDecision.getDisconnectKey());
        }
        if (spamDecision.shouldLog()) {
            actions.log(spamDecision.getLogMessage());
        }

        int processingBudget = connectionQueuePolicy.getProcessingBudget(request.isFastModeEnabled());
        inboundPacketProcessingSystem.processInboundQueue(
                request.getInboundQueue(),
                processingBudget,
                request.getHandler(),
                request.isFirePacketEvents(),
                request.getUsername()
        );

        return new TickState(timeoutDecision.getNextIdleTicks());
    }

    public interface TickActions {
        void disconnect(String key);

        void kickPlayer(String reason);

        void log(String message);
    }

    public static final class TickRequest {
        private final boolean fastModeEnabled;
        private final int queuedBytes;
        private final boolean inboundQueueEmpty;
        private final int idleTicks;
        private final boolean spamDetectionEnabled;
        private final int inboundQueueSize;
        private final int spamThreshold;
        private final String username;
        private final boolean playerConnection;
        private final List inboundQueue;
        private final Object handler;
        private final boolean firePacketEvents;

        public TickRequest(
                boolean fastModeEnabled,
                int queuedBytes,
                boolean inboundQueueEmpty,
                int idleTicks,
                boolean spamDetectionEnabled,
                int inboundQueueSize,
                int spamThreshold,
                String username,
                boolean playerConnection,
                List inboundQueue,
                Object handler,
                boolean firePacketEvents
        ) {
            this.fastModeEnabled = fastModeEnabled;
            this.queuedBytes = queuedBytes;
            this.inboundQueueEmpty = inboundQueueEmpty;
            this.idleTicks = idleTicks;
            this.spamDetectionEnabled = spamDetectionEnabled;
            this.inboundQueueSize = inboundQueueSize;
            this.spamThreshold = spamThreshold;
            this.username = username;
            this.playerConnection = playerConnection;
            this.inboundQueue = inboundQueue;
            this.handler = handler;
            this.firePacketEvents = firePacketEvents;
        }

        public boolean isFastModeEnabled() {
            return fastModeEnabled;
        }

        public int getQueuedBytes() {
            return queuedBytes;
        }

        public boolean isInboundQueueEmpty() {
            return inboundQueueEmpty;
        }

        public int getIdleTicks() {
            return idleTicks;
        }

        public boolean isSpamDetectionEnabled() {
            return spamDetectionEnabled;
        }

        public int getInboundQueueSize() {
            return inboundQueueSize;
        }

        public int getSpamThreshold() {
            return spamThreshold;
        }

        public String getUsername() {
            return username;
        }

        public boolean isPlayerConnection() {
            return playerConnection;
        }

        public List getInboundQueue() {
            return inboundQueue;
        }

        public Object getHandler() {
            return handler;
        }

        public boolean isFirePacketEvents() {
            return firePacketEvents;
        }
    }

    public static final class TickState {
        private final int nextIdleTicks;

        private TickState(int nextIdleTicks) {
            this.nextIdleTicks = nextIdleTicks;
        }

        public int getNextIdleTicks() {
            return nextIdleTicks;
        }
    }
}
