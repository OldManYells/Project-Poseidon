package com.legacyminecraft.poseidon.network;


/**
 * Canonical execution flow for resolved teleport requests.
 */
public final class PlayerTeleportRequestExecutionSystem {
    private static final PlayerTeleportRequestExecutionSystem INSTANCE = new PlayerTeleportRequestExecutionSystem();

    private PlayerTeleportRequestExecutionSystem() {
    }

    public static PlayerTeleportRequestExecutionSystem getInstance() {
        return INSTANCE;
    }

    public void execute(TeleportDestinationResolver teleportDestinationResolver, TeleportActions teleportActions) {
        teleportActions.teleport(teleportDestinationResolver.resolveDestination());
    }

    public interface TeleportDestinationResolver {
        Object resolveDestination();
    }

    public interface TeleportActions {
        void teleport(Object destination);
    }
}
