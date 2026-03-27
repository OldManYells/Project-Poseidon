package com.legacyminecraft.poseidon.network;


/**
 * Canonical resolver for applying PlayerMoveEvent dispatch outcomes.
 */
public final class PlayerMoveEventOutcomeSystem {
    private static final PlayerMoveEventOutcomeSystem INSTANCE = new PlayerMoveEventOutcomeSystem();
    private final PlayerTeleportExecutionSystem playerTeleportExecutionSystem = PlayerTeleportExecutionSystem.getInstance();

    private PlayerMoveEventOutcomeSystem() {
    }

    public static PlayerMoveEventOutcomeSystem getInstance() {
        return INSTANCE;
    }

    public MoveOutcomeDecision resolve(PlayerMoveEventDispatchSystem.MoveEventResult moveEventResult) {
        PlayerMoveEventDispatchSystem.MoveEventResult.Action action = moveEventResult.getAction();
        if (action == PlayerMoveEventDispatchSystem.MoveEventResult.Action.CANCEL_AND_ROLLBACK) {
            return MoveOutcomeDecision.rollbackAndReturn(createRollbackPacket(moveEventResult.getRollbackLocation()));
        }
        if (action == PlayerMoveEventDispatchSystem.MoveEventResult.Action.TELEPORT_TO_EVENT_DESTINATION) {
            return MoveOutcomeDecision.teleportAndReturn(moveEventResult.getTeleportDestination());
        }
        if (action == PlayerMoveEventDispatchSystem.MoveEventResult.Action.ABORT_AFTER_PLUGIN_TELEPORT) {
            return MoveOutcomeDecision.abortAndReturn();
        }
        return MoveOutcomeDecision.continueProcessing();
    }

    public Packet13PlayerLookMove createRollbackPacket(Location rollbackLocation) {
        return playerTeleportExecutionSystem.createLookMovePacket(rollbackLocation);
    }

    public static final class MoveOutcomeDecision {
        private final boolean shouldReturn;
        private final Packet13PlayerLookMove rollbackPacket;
        private final Location teleportDestination;

        private MoveOutcomeDecision(boolean shouldReturn, Packet13PlayerLookMove rollbackPacket, Location teleportDestination) {
            this.shouldReturn = shouldReturn;
            this.rollbackPacket = rollbackPacket;
            this.teleportDestination = teleportDestination;
        }

        public static MoveOutcomeDecision continueProcessing() {
            return new MoveOutcomeDecision(false, null, null);
        }

        public static MoveOutcomeDecision rollbackAndReturn(Packet13PlayerLookMove rollbackPacket) {
            return new MoveOutcomeDecision(true, rollbackPacket, null);
        }

        public static MoveOutcomeDecision teleportAndReturn(Location teleportDestination) {
            return new MoveOutcomeDecision(true, null, teleportDestination);
        }

        public static MoveOutcomeDecision abortAndReturn() {
            return new MoveOutcomeDecision(true, null, null);
        }

        public boolean shouldReturn() {
            return shouldReturn;
        }

        public boolean shouldSendRollbackPacket() {
            return rollbackPacket != null;
        }

        public Packet13PlayerLookMove getRollbackPacket() {
            return rollbackPacket;
        }

        public boolean shouldTeleportPlayer() {
            return teleportDestination != null;
        }

        public Location getTeleportDestination() {
            return teleportDestination;
        }
    }
}
