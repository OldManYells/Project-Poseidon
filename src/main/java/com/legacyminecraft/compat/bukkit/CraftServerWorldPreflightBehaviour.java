package com.legacyminecraft.compat.bukkit;


import java.io.File;

/**
 * Canonical behaviour for CraftServer create-world preflight wrapper glue.
 */
public final class CraftServerWorldPreflightBehaviour {
    private static final CraftServerWorldPreflightBehaviour INSTANCE =
            new CraftServerWorldPreflightBehaviour();

    private CraftServerWorldPreflightBehaviour() {
    }

    public static CraftServerWorldPreflightBehaviour getInstance() {
        return INSTANCE;
    }

    public World resolveExistingWorldOrThrow(World existingWorld, File folder, String name) {
        if (existingWorld != null) {
            return existingWorld;
        }

        if ((folder.exists()) && (!folder.isDirectory())) {
            throw new IllegalArgumentException("File exists with the name '" + name + "' and isn't a folder");
        }

        return null;
    }
}
