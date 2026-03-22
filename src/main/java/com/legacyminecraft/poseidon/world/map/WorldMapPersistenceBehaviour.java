package com.legacyminecraft.poseidon.world.map;

import com.legacyminecraft.poseidon.compat.bukkit.WorldDimensionBridgeBehaviour;
import net.minecraft.server.NBTTagCompound;
import org.bukkit.Server;
import org.bukkit.World;

import java.util.UUID;

public final class WorldMapPersistenceBehaviour {
    private static final WorldMapPersistenceBehaviour INSTANCE = new WorldMapPersistenceBehaviour();
    private final WorldDimensionBridgeBehaviour worldDimensionBridge = WorldDimensionBridgeBehaviour.getInstance();

    private WorldMapPersistenceBehaviour() {
    }

    public static WorldMapPersistenceBehaviour getInstance() {
        return INSTANCE;
    }

    public LoadedState loadFromNbt(NBTTagCompound mapTag, Server server) {
        byte dimension = mapTag.c("dimension");
        UUID uniqueId = null;

        if (dimension >= 10) {
            long least = mapTag.getLong("UUIDLeast");
            long most = mapTag.getLong("UUIDMost");

            if (least != 0L && most != 0L) {
                uniqueId = new UUID(most, least);

                World world = server.getWorld(uniqueId);
                if (world == null) {
                    dimension = 127;
                } else {
                    dimension = (byte) worldDimensionBridge.resolveDimension(world, 127);
                }
            }
        }

        int xCenter = mapTag.e("xCenter");
        int zCenter = mapTag.e("zCenter");
        byte scale = clampScale(mapTag.c("scale"));

        short width = mapTag.d("width");
        short height = mapTag.d("height");
        byte[] colors;

        if (width == 128 && height == 128) {
            colors = mapTag.j("colors");
        } else {
            byte[] sourceColors = mapTag.j("colors");
            colors = new byte[16384];
            int xOffset = (128 - width) / 2;
            int yOffset = (128 - height) / 2;

            for (int srcY = 0; srcY < height; ++srcY) {
                int dstY = srcY + yOffset;

                if (dstY >= 0 && dstY < 128) {
                    for (int srcX = 0; srcX < width; ++srcX) {
                        int dstX = srcX + xOffset;

                        if (dstX >= 0 && dstX < 128) {
                            colors[dstX + dstY * 128] = sourceColors[srcX + srcY * width];
                        }
                    }
                }
            }
        }

        return new LoadedState(dimension, xCenter, zCenter, scale, colors, uniqueId);
    }

    public UUID writeToNbt(NBTTagCompound mapTag, Server server, byte dimension, int xCenter, int zCenter, byte scale, byte[] colors, UUID uniqueId) {
        if (dimension >= 10) {
            if (uniqueId == null) {
                for (World world : server.getWorlds()) {
                    if (worldDimensionBridge.resolveDimension(world, Integer.MIN_VALUE) == dimension) {
                        uniqueId = world.getUID();
                        break;
                    }
                }
            }

            if (uniqueId != null) {
                mapTag.setLong("UUIDLeast", uniqueId.getLeastSignificantBits());
                mapTag.setLong("UUIDMost", uniqueId.getMostSignificantBits());
            }
        }

        mapTag.a("dimension", dimension);
        mapTag.a("xCenter", xCenter);
        mapTag.a("zCenter", zCenter);
        mapTag.a("scale", scale);
        mapTag.a("width", (short) 128);
        mapTag.a("height", (short) 128);
        mapTag.a("colors", colors);
        return uniqueId;
    }

    private static byte clampScale(byte scale) {
        if (scale < 0) {
            return 0;
        }
        if (scale > 4) {
            return 4;
        }
        return scale;
    }

    public static final class LoadedState {
        public final byte dimension;
        public final int xCenter;
        public final int zCenter;
        public final byte scale;
        public final byte[] colors;
        public final UUID uniqueId;

        LoadedState(byte dimension, int xCenter, int zCenter, byte scale, byte[] colors, UUID uniqueId) {
            this.dimension = dimension;
            this.xCenter = xCenter;
            this.zCenter = zCenter;
            this.scale = scale;
            this.colors = colors;
            this.uniqueId = uniqueId;
        }
    }
}
