package com.legacyminecraft.poseidon.world.map;

import com.legacyminecraft.compat.bukkit.WorldDimensionBridgeBehaviour;

import java.util.UUID;

public final class WorldMapPersistenceBehaviour {
    private static final WorldMapPersistenceBehaviour INSTANCE = new WorldMapPersistenceBehaviour();
    private final WorldDimensionBridgeBehaviour worldDimensionBridge = WorldDimensionBridgeBehaviour.getInstance();

    private WorldMapPersistenceBehaviour() {
    }

    public static WorldMapPersistenceBehaviour getInstance() {
        return INSTANCE;
    }

    public LoadedState loadFromNbt(Object mapTag, Object server) {
        byte dimension = ((Number) invoke(mapTag, "c", "dimension")).byteValue();
        UUID uniqueId = null;

        if (dimension >= 10) {
            long least = ((Number) invoke(mapTag, "getLong", "UUIDLeast")).longValue();
            long most = ((Number) invoke(mapTag, "getLong", "UUIDMost")).longValue();

            if (least != 0L && most != 0L) {
                uniqueId = new UUID(most, least);

                Object world = invoke(server, "getWorld", uniqueId);
                if (world == null) {
                    dimension = 127;
                } else {
                    dimension = (byte) worldDimensionBridge.resolveDimension(world, 127);
                }
            }
        }

        int xCenter = ((Number) invoke(mapTag, "e", "xCenter")).intValue();
        int zCenter = ((Number) invoke(mapTag, "e", "zCenter")).intValue();
        byte scale = clampScale(((Number) invoke(mapTag, "c", "scale")).byteValue());

        short width = ((Number) invoke(mapTag, "d", "width")).shortValue();
        short height = ((Number) invoke(mapTag, "d", "height")).shortValue();
        byte[] colors;

        if (width == 128 && height == 128) {
            colors = cast(invoke(mapTag, "j", "colors"));
        } else {
            byte[] sourceColors = cast(invoke(mapTag, "j", "colors"));
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

    public UUID writeToNbt(Object mapTag, Object server, byte dimension, int xCenter, int zCenter, byte scale, byte[] colors, UUID uniqueId) {
        if (dimension >= 10) {
            if (uniqueId == null) {
                Iterable worlds = cast(invoke(server, "getWorlds"));
                for (Object world : worlds) {
                    if (worldDimensionBridge.resolveDimension(world, Integer.MIN_VALUE) == dimension) {
                        uniqueId = cast(invoke(world, "getUID"));
                        break;
                    }
                }
            }

            if (uniqueId != null) {
                invoke(mapTag, "setLong", "UUIDLeast", uniqueId.getLeastSignificantBits());
                invoke(mapTag, "setLong", "UUIDMost", uniqueId.getMostSignificantBits());
            }
        }

        invoke(mapTag, "a", "dimension", dimension);
        invoke(mapTag, "a", "xCenter", xCenter);
        invoke(mapTag, "a", "zCenter", zCenter);
        invoke(mapTag, "a", "scale", scale);
        invoke(mapTag, "a", "width", (short) 128);
        invoke(mapTag, "a", "height", (short) 128);
        invoke(mapTag, "a", "colors", colors);
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

    private Object invoke(Object target, String methodName, Object... args) {
        try {
            java.lang.reflect.Method[] methods = target.getClass().getMethods();
            for (java.lang.reflect.Method method : methods) {
                if (!method.getName().equals(methodName) || method.getParameterTypes().length != args.length) {
                    continue;
                }
                method.setAccessible(true);
                return method.invoke(target, args);
            }
            throw new IllegalStateException("Method not found: " + methodName);
        } catch (Exception exception) {
            throw new IllegalStateException("Unable to invoke: " + methodName, exception);
        }
    }

    private <T> T cast(Object value) {
        return (T) value;
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
