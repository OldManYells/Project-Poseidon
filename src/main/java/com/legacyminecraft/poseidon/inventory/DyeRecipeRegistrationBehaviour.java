package com.legacyminecraft.poseidon.inventory;


public final class DyeRecipeRegistrationBehaviour {
    private static final DyeRecipeRegistrationBehaviour INSTANCE = new DyeRecipeRegistrationBehaviour();

    private DyeRecipeRegistrationBehaviour() {
    }

    public static DyeRecipeRegistrationBehaviour getInstance() {
        return INSTANCE;
    }

    public void registerAll(CraftingManager craftingmanager) {
        // Intentionally no-op in this migration slice.
        // Recipe registration remains owned by the legacy wrapper path while
        // inventory models are being reconciled.
    }
}
