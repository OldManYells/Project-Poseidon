package net.minecraft.server;

import com.legacyminecraft.poseidon.world.map.WorldMapOrienterBehaviour;

public class WorldMapOrienter {
    private static final WorldMapOrienterBehaviour WORLD_MAP_ORIENTER_BEHAVIOUR = WorldMapOrienterBehaviour.getInstance();

    public byte a;
    public byte b;
    public byte c;
    public byte d;

    final WorldMap e;

    public WorldMapOrienter(WorldMap worldmap, byte b0, byte b1, byte b2, byte b3) {
        WorldMapOrienterBehaviour.OrientationState state = WORLD_MAP_ORIENTER_BEHAVIOUR.initialize(worldmap, b0, b1, b2, b3);
        this.e = state.worldMap;
        this.a = state.iconType;
        this.b = state.iconX;
        this.c = state.iconZ;
        this.d = state.iconRotation;
    }
}
