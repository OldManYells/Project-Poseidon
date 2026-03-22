package com.legacyminecraft.poseidon.world.player;

/**
 * Canonical policy service for per-tick player portal state and health sync decisions.
 */
public final class PlayerSessionTickSystem {
    private static final PlayerSessionTickSystem INSTANCE = new PlayerSessionTickSystem();
    private static final float PORTAL_ENTRY_INCREMENT = 0.0125F;
    private static final float PORTAL_DECAY_PER_TICK = 0.05F;
    private static final int PORTAL_COOLDOWN_RESET = 10;

    private PlayerSessionTickSystem() {
    }

    public static PlayerSessionTickSystem getInstance() {
        return INSTANCE;
    }

    public PortalTickDecision evaluatePortalTick(
            boolean portalTriggered,
            float portalProgress,
            int portalCooldown,
            boolean hasVehicle,
            boolean hasNonDefaultContainer
    ) {
        boolean shouldCloseContainer = false;
        boolean shouldRemountVehicle = false;
        boolean shouldTriggerWorldTransfer = false;

        if (portalTriggered) {
            if (hasNonDefaultContainer) {
                shouldCloseContainer = true;
            }

            if (hasVehicle) {
                shouldRemountVehicle = true;
            } else {
                portalProgress += PORTAL_ENTRY_INCREMENT;
                if (portalProgress >= 1.0F) {
                    portalProgress = 1.0F;
                    portalCooldown = PORTAL_COOLDOWN_RESET;
                    shouldTriggerWorldTransfer = true;
                }
            }

            portalTriggered = false;
        } else {
            if (portalProgress > 0.0F) {
                portalProgress -= PORTAL_DECAY_PER_TICK;
            }

            if (portalProgress < 0.0F) {
                portalProgress = 0.0F;
            }
        }

        if (portalCooldown > 0) {
            --portalCooldown;
        }

        return new PortalTickDecision(
                portalTriggered,
                portalProgress,
                portalCooldown,
                shouldCloseContainer,
                shouldRemountVehicle,
                shouldTriggerWorldTransfer
        );
    }

    public HealthSyncDecision evaluateHealthSync(int currentHealth, int lastReportedHealth) {
        if (currentHealth == lastReportedHealth) {
            return HealthSyncDecision.noUpdate(lastReportedHealth);
        }
        return HealthSyncDecision.sendUpdate(currentHealth);
    }

    public static final class PortalTickDecision {
        private final boolean portalTriggered;
        private final float portalProgress;
        private final int portalCooldown;
        private final boolean shouldCloseContainer;
        private final boolean shouldRemountVehicle;
        private final boolean shouldTriggerWorldTransfer;

        private PortalTickDecision(
                boolean portalTriggered,
                float portalProgress,
                int portalCooldown,
                boolean shouldCloseContainer,
                boolean shouldRemountVehicle,
                boolean shouldTriggerWorldTransfer
        ) {
            this.portalTriggered = portalTriggered;
            this.portalProgress = portalProgress;
            this.portalCooldown = portalCooldown;
            this.shouldCloseContainer = shouldCloseContainer;
            this.shouldRemountVehicle = shouldRemountVehicle;
            this.shouldTriggerWorldTransfer = shouldTriggerWorldTransfer;
        }

        public boolean isPortalTriggered() {
            return portalTriggered;
        }

        public float getPortalProgress() {
            return portalProgress;
        }

        public int getPortalCooldown() {
            return portalCooldown;
        }

        public boolean shouldCloseContainer() {
            return shouldCloseContainer;
        }

        public boolean shouldRemountVehicle() {
            return shouldRemountVehicle;
        }

        public boolean shouldTriggerWorldTransfer() {
            return shouldTriggerWorldTransfer;
        }
    }

    public static final class HealthSyncDecision {
        private final boolean shouldSendHealthPacket;
        private final int nextReportedHealth;

        private HealthSyncDecision(boolean shouldSendHealthPacket, int nextReportedHealth) {
            this.shouldSendHealthPacket = shouldSendHealthPacket;
            this.nextReportedHealth = nextReportedHealth;
        }

        public static HealthSyncDecision noUpdate(int nextReportedHealth) {
            return new HealthSyncDecision(false, nextReportedHealth);
        }

        public static HealthSyncDecision sendUpdate(int nextReportedHealth) {
            return new HealthSyncDecision(true, nextReportedHealth);
        }

        public boolean shouldSendHealthPacket() {
            return shouldSendHealthPacket;
        }

        public int getNextReportedHealth() {
            return nextReportedHealth;
        }
    }
}
