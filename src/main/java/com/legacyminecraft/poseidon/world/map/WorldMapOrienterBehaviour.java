package com.legacyminecraft.poseidon.world.map;

import net.minecraft.server.WorldMap;

public final class WorldMapOrienterBehaviour {
    private static final WorldMapOrienterBehaviour INSTANCE = new WorldMapOrienterBehaviour();

    private WorldMapOrienterBehaviour() {
    }

    public static WorldMapOrienterBehaviour getInstance() {
        return INSTANCE;
    }

    public static final class OrientationState {
        public final WorldMap worldMap;
        public final byte iconType;
        public final byte iconX;
        public final byte iconZ;
        public final byte iconRotation;

        public OrientationState(WorldMap worldMap, byte iconType, byte iconX, byte iconZ, byte iconRotation) {
            this.worldMap = worldMap;
            this.iconType = iconType;
            this.iconX = iconX;
            this.iconZ = iconZ;
            this.iconRotation = iconRotation;
        }
    }

    public OrientationState initialize(WorldMap worldMap, byte iconType, byte iconX, byte iconZ, byte iconRotation) {
        return new OrientationState(worldMap, iconType, iconX, iconZ, iconRotation);
    }
}
