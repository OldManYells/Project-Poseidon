package com.legacyminecraft.compat.bukkit;


/**
 * Canonical behavior for offline-player constructor snapshot capture.
 */
public final class OfflinePlayerSnapshotCaptureBehaviour {
    private static final OfflinePlayerSnapshotCaptureBehaviour INSTANCE = new OfflinePlayerSnapshotCaptureBehaviour();

    private OfflinePlayerSnapshotCaptureBehaviour() {
    }

    public static OfflinePlayerSnapshotCaptureBehaviour getInstance() {
        return INSTANCE;
    }

    public Snapshot capture(CraftServer server, String name) {
        return new Snapshot(server, name);
    }

    public static final class Snapshot {
        private final CraftServer server;
        private final String name;

        public Snapshot(CraftServer server, String name) {
            this.server = server;
            this.name = name;
        }

        public CraftServer getServer() {
            return server;
        }

        public String getName() {
            return name;
        }
    }
}
