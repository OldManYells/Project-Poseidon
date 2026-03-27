package com.legacyminecraft.compat.bukkit;


/**
 * Canonical behaviour for CraftServer duplicate-world warning wrapper glue.
 */
public final class CraftServerWorldDuplicateWarningBehaviour {
    private static final CraftServerWorldDuplicateWarningBehaviour INSTANCE =
            new CraftServerWorldDuplicateWarningBehaviour();

    private CraftServerWorldDuplicateWarningBehaviour() {
    }

    public static CraftServerWorldDuplicateWarningBehaviour getInstance() {
        return INSTANCE;
    }

    public void warnDuplicateWorld(World world) {
        System.out.println(
                "World " + world.getName() + " is a duplicate of another world and has been prevented from loading. Please delete the uid.dat file from "
                        + world.getName() + "'s world directory if you want to be able to load the duplicate world."
        );
    }
}
