package com.legacyminecraft.poseidon.network;

/**
 * Canonical post-tick finalization for network manager wrappers.
 */
public final class NetworkTickFinalizationSystem {
    private static final NetworkTickFinalizationSystem INSTANCE = new NetworkTickFinalizationSystem();

    private NetworkTickFinalizationSystem() {
    }

    public static NetworkTickFinalizationSystem getInstance() {
        return INSTANCE;
    }

    public void finalizeTick(
            boolean terminating,
            boolean inboundQueueEmpty,
            String disconnectKey,
            Object[] disconnectArgs,
            TickFinalizationActions tickFinalizationActions
    ) {
        if (terminating && inboundQueueEmpty) {
            tickFinalizationActions.interruptNetworkThreads();
            tickFinalizationActions.notifyDisconnect(disconnectKey, disconnectArgs);
        }
    }

    public interface TickFinalizationActions {
        void interruptNetworkThreads();

        void notifyDisconnect(String key, Object[] args);
    }
}
