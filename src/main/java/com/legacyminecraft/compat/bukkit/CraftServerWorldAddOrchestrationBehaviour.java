package com.legacyminecraft.compat.bukkit;


/**
 * Canonical behaviour for CraftServer add-world orchestration wrapper glue.
 */
public final class CraftServerWorldAddOrchestrationBehaviour {
    public interface WorldRegistryAddAction {
        boolean addWorld(World world);
    }

    public interface DuplicateWorldWarningAction {
        void warnDuplicateWorld(World world);
    }

    private static final CraftServerWorldAddOrchestrationBehaviour INSTANCE =
            new CraftServerWorldAddOrchestrationBehaviour();

    private CraftServerWorldAddOrchestrationBehaviour() {
    }

    public static CraftServerWorldAddOrchestrationBehaviour getInstance() {
        return INSTANCE;
    }

    public void addWorld(World world, WorldRegistryAddAction addAction, DuplicateWorldWarningAction warningAction) {
        if (!addAction.addWorld(world)) {
            warningAction.warnDuplicateWorld(world);
        }
    }
}
