package com.legacyminecraft.poseidon.network;

/**
 * Canonical disconnect lifecycle state machine for legacy NetworkManager wrappers.
 */
public final class NetworkDisconnectLifecycleSystem {
    private static final NetworkDisconnectLifecycleSystem INSTANCE = new NetworkDisconnectLifecycleSystem();

    private NetworkDisconnectLifecycleSystem() {
    }

    public static NetworkDisconnectLifecycleSystem getInstance() {
        return INSTANCE;
    }

    public DisconnectState disconnectIfOpen(
            boolean connectionOpen,
            String disconnectKey,
            Object[] disconnectArgs,
            DisconnectActions disconnectActions
    ) {
        if (!connectionOpen) {
            return DisconnectState.unchanged();
        }

        disconnectActions.startMasterThread();
        disconnectActions.closeResources();
        return DisconnectState.closed(disconnectKey, disconnectArgs);
    }

    public interface DisconnectActions {
        void startMasterThread();

        void closeResources();
    }

    public static final class DisconnectState {
        private final boolean changed;
        private final boolean open;
        private final boolean terminating;
        private final String disconnectKey;
        private final Object[] disconnectArgs;

        private DisconnectState(boolean changed, boolean open, boolean terminating, String disconnectKey, Object[] disconnectArgs) {
            this.changed = changed;
            this.open = open;
            this.terminating = terminating;
            this.disconnectKey = disconnectKey;
            this.disconnectArgs = disconnectArgs;
        }

        public static DisconnectState unchanged() {
            return new DisconnectState(false, true, false, null, null);
        }

        public static DisconnectState closed(String disconnectKey, Object[] disconnectArgs) {
            return new DisconnectState(true, false, true, disconnectKey, disconnectArgs);
        }

        public boolean isChanged() {
            return changed;
        }

        public boolean isOpen() {
            return open;
        }

        public boolean isTerminating() {
            return terminating;
        }

        public String getDisconnectKey() {
            return disconnectKey;
        }

        public Object[] getDisconnectArgs() {
            return disconnectArgs;
        }
    }
}
