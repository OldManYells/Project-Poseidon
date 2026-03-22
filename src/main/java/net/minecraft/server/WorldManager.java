package net.minecraft.server;

import com.legacyminecraft.poseidon.world.WorldAccessDispatchBehaviour;

public class WorldManager implements IWorldAccess {
    private static final WorldAccessDispatchBehaviour WORLD_ACCESS_DISPATCH_BEHAVIOUR = WorldAccessDispatchBehaviour.getInstance();

    private MinecraftServer server;
    public WorldServer world; // CraftBukkit - private -> public

    public WorldManager(MinecraftServer minecraftserver, WorldServer worldserver) {
        this.server = minecraftserver;
        this.world = worldserver;
    }

    public void a(String s, double d0, double d1, double d2, double d3, double d4, double d5) {}

    public void a(Entity entity) {
        WORLD_ACCESS_DISPATCH_BEHAVIOUR.onEntityAdded(this.server, this.world, entity);
    }

    public void b(Entity entity) {
        WORLD_ACCESS_DISPATCH_BEHAVIOUR.onEntityRemoved(this.server, this.world, entity);
    }

    public void a(String s, double d0, double d1, double d2, float f, float f1) {}

    public void a(int i, int j, int k, int l, int i1, int j1) {}

    public void a() {}

    public void a(int i, int j, int k) {
        WORLD_ACCESS_DISPATCH_BEHAVIOUR.markBlockDirty(this.server, this.world, i, j, k);
    }

    public void a(String s, int i, int j, int k) {}

    public void a(int i, int j, int k, TileEntity tileentity) {
        WORLD_ACCESS_DISPATCH_BEHAVIOUR.onTileEntityChanged(this.server, i, j, k, tileentity);
    }

    public void a(EntityHuman entityhuman, int i, int j, int k, int l, int i1) {
        WORLD_ACCESS_DISPATCH_BEHAVIOUR.sendAuxSfx(this.server, this.world, entityhuman, i, j, k, l, i1);
    }
}
