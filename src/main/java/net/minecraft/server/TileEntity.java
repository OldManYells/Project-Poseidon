package net.minecraft.server;

import com.legacyminecraft.poseidon.world.tile.TileEntityRegistryBehaviour;

import java.util.HashMap;
import java.util.Map;

public class TileEntity {
    private static final TileEntityRegistryBehaviour TILE_ENTITY_REGISTRY_BEHAVIOUR = TileEntityRegistryBehaviour.getInstance();

    private static Map a = new HashMap();
    private static Map b = new HashMap();
    public World world;
    public int x;
    public int y;
    public int z;
    protected boolean h;

    public TileEntity() {}

    private static void a(Class oclass, String s) {
        TILE_ENTITY_REGISTRY_BEHAVIOUR.register(a, b, oclass, s);
    }

    public void a(NBTTagCompound nbttagcompound) {
        TILE_ENTITY_REGISTRY_BEHAVIOUR.readCoordinates(this, nbttagcompound);
    }

    public void b(NBTTagCompound nbttagcompound) {
        TILE_ENTITY_REGISTRY_BEHAVIOUR.writeBaseData(this, b, nbttagcompound);
    }

    public void g_() {}

    public static TileEntity c(NBTTagCompound nbttagcompound) {
        return TILE_ENTITY_REGISTRY_BEHAVIOUR.createFromTag(a, nbttagcompound);
    }

    public int e() {
        return TILE_ENTITY_REGISTRY_BEHAVIOUR.getBlockData(this.world, this.x, this.y, this.z);
    }

    public void update() {
        TILE_ENTITY_REGISTRY_BEHAVIOUR.notifyUpdated(this.world, this.x, this.y, this.z, this);
    }

    public Packet f() {
        return null;
    }

    public boolean g() {
        return TILE_ENTITY_REGISTRY_BEHAVIOUR.isInvalid(this.h);
    }

    public void h() {
        this.h = TILE_ENTITY_REGISTRY_BEHAVIOUR.markInvalid();
    }

    public void j() {
        this.h = TILE_ENTITY_REGISTRY_BEHAVIOUR.markValid();
    }

    static {
        a(TileEntityFurnace.class, "Furnace");
        a(TileEntityChest.class, "Chest");
        a(TileEntityRecordPlayer.class, "RecordPlayer");
        a(TileEntityDispenser.class, "Trap");
        a(TileEntitySign.class, "Sign");
        a(TileEntityMobSpawner.class, "MobSpawner");
        a(TileEntityNote.class, "Music");
        a(TileEntityPiston.class, "Piston");
    }
}
