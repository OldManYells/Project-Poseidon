package net.minecraft.server;

import com.legacyminecraft.poseidon.world.map.WorldMapDirtyFlagBehaviour;

public abstract class WorldMapBase {
    private static final WorldMapDirtyFlagBehaviour WORLD_MAP_DIRTY_FLAG_BEHAVIOUR = WorldMapDirtyFlagBehaviour.getInstance();

    public final String a;
    private boolean b;

    public WorldMapBase(String s) {
        this.a = s;
    }

    public abstract void a(NBTTagCompound nbttagcompound);

    public abstract void b(NBTTagCompound nbttagcompound);

    public void a() {
        this.a(WORLD_MAP_DIRTY_FLAG_BEHAVIOUR.markDirty());
    }

    public void a(boolean flag) {
        this.b = WORLD_MAP_DIRTY_FLAG_BEHAVIOUR.setDirty(flag);
    }

    public boolean b() {
        return WORLD_MAP_DIRTY_FLAG_BEHAVIOUR.isDirty(this.b);
    }
}
