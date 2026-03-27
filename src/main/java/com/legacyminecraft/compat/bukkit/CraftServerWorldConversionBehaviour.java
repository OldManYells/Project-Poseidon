package com.legacyminecraft.compat.bukkit;


import java.io.File;
import java.util.logging.Logger;
import net.minecraft.server.ConvertProgressUpdater;
import net.minecraft.server.Convertable;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.WorldLoaderServer;

/**
 * Canonical behaviour for CraftServer world-conversion wrapper glue.
 */
public final class CraftServerWorldConversionBehaviour {
    private static final CraftServerWorldConversionBehaviour INSTANCE =
            new CraftServerWorldConversionBehaviour();

    private CraftServerWorldConversionBehaviour() {
    }

    public static CraftServerWorldConversionBehaviour getInstance() {
        return INSTANCE;
    }

    public void convertIfNeeded(File folder, String name, MinecraftServer console, Logger logger) {
        Convertable converter = new WorldLoaderServer(folder);
        if (converter.isConvertable(name)) {
            logger.info("Converting world '" + name + "'");
            converter.convert(name, new ConvertProgressUpdater(console));
        }
    }
}
