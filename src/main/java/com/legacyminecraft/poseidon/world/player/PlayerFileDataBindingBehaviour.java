package com.legacyminecraft.poseidon.world.player;


/**
 * Canonical policy for binding player file data only once.
 */
public final class PlayerFileDataBindingBehaviour {
    private static final PlayerFileDataBindingBehaviour INSTANCE = new PlayerFileDataBindingBehaviour();

    private PlayerFileDataBindingBehaviour() {
    }

    public static PlayerFileDataBindingBehaviour getInstance() {
        return INSTANCE;
    }

    public PlayerFileData bindIfAbsent(PlayerFileData currentPlayerFileData, PlayerFileDataSupplier playerFileDataSupplier) {
        if (currentPlayerFileData != null) {
            return currentPlayerFileData;
        }
        return playerFileDataSupplier.resolve();
    }

    public interface PlayerFileDataSupplier {
        PlayerFileData resolve();
    }
}
