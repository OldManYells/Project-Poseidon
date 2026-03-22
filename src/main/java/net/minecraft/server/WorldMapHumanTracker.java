package net.minecraft.server;

import com.legacyminecraft.poseidon.world.map.WorldMapHumanTrackerBehaviour;

public class WorldMapHumanTracker {
    private static final WorldMapHumanTrackerBehaviour WORLD_MAP_HUMAN_TRACKER_BEHAVIOUR = WorldMapHumanTrackerBehaviour.getInstance();

    public final EntityHuman trackee;
    public int[] b;
    public int[] c;
    private int e;
    private int f;
    private byte[] g;

    final WorldMap d;

    public WorldMapHumanTracker(WorldMap worldmap, EntityHuman entityhuman) {
        this.d = worldmap;
        this.b = new int[128];
        this.c = new int[128];
        this.e = 0;
        this.f = 0;
        this.trackee = entityhuman;
        WORLD_MAP_HUMAN_TRACKER_BEHAVIOUR.initializeBounds(this.b, this.c);
    }

    public byte[] a(ItemStack itemstack) {
        WorldMapHumanTrackerBehaviour.UpdateState update = WORLD_MAP_HUMAN_TRACKER_BEHAVIOUR.createUpdate(this.d, this.trackee, itemstack, this.b, this.c, this.e, this.f, this.g);
        this.e = update.updateCursor;
        this.f = update.markerCooldown;
        this.g = update.lastMarkerPacket;
        return update.payload;
    }
}
