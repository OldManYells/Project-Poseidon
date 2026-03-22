package net.minecraft.server;

import com.legacyminecraft.poseidon.world.map.WorldMapPersistenceBehaviour;
import com.legacyminecraft.poseidon.world.map.WorldMapTrackingBehaviour;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.craftbukkit.map.CraftMapView;

import java.util.*;

// CraftBukkit start
// CraftBukkit end

public class WorldMap extends WorldMapBase {
    private static final WorldMapPersistenceBehaviour WORLD_MAP_PERSISTENCE_BEHAVIOUR = WorldMapPersistenceBehaviour.getInstance();
    private static final WorldMapTrackingBehaviour WORLD_MAP_TRACKING_BEHAVIOUR = WorldMapTrackingBehaviour.getInstance();

    public int b;
    public int c;
    public byte map;
    public byte e;
    public byte[] f = new byte[16384];
    public int g;
    public List h = new ArrayList();
    private Map j = new HashMap();
    public List i = new ArrayList();

    // CraftBukkit start
    public final CraftMapView mapView;
    private CraftServer server;
    private UUID uniqueId = null;
    // CraftBukkit end

    public WorldMap(String s) {
        super(s);
        // CraftBukkit start
        mapView = new CraftMapView(this);
        server = (CraftServer) Bukkit.getServer();
        // CraftBukkit end
    }

    public void a(NBTTagCompound nbttagcompound) {
        WorldMapPersistenceBehaviour.LoadedState loaded = WORLD_MAP_PERSISTENCE_BEHAVIOUR.loadFromNbt(nbttagcompound, this.server);
        this.map = loaded.dimension;
        this.b = loaded.xCenter;
        this.c = loaded.zCenter;
        this.e = loaded.scale;
        this.f = loaded.colors;
        this.uniqueId = loaded.uniqueId;
    }

    public void b(NBTTagCompound nbttagcompound) {
        this.uniqueId = WORLD_MAP_PERSISTENCE_BEHAVIOUR.writeToNbt(nbttagcompound, this.server, this.map, this.b, this.c, this.e, this.f, this.uniqueId);
    }

    public void a(EntityHuman entityhuman, ItemStack itemstack) {
        WORLD_MAP_TRACKING_BEHAVIOUR.updateTrackers(this, entityhuman, itemstack, this.j, this.h, this.i, this.b, this.c, this.e, this.map, this.g);
    }

    public byte[] a(ItemStack itemstack, World world, EntityHuman entityhuman) {
        return WORLD_MAP_TRACKING_BEHAVIOUR.createUpdatePacket(this.j, entityhuman, itemstack);
    }

    public void a(int i, int j, int k) {
        WORLD_MAP_TRACKING_BEHAVIOUR.markDirty(this, this.h, i, j, k);
    }
}
