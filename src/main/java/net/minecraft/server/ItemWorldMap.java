package net.minecraft.server;

import com.legacyminecraft.poseidon.item.WorldMapItemBehaviour;

public class ItemWorldMap extends ItemWorldMapBase {
    private static final WorldMapItemBehaviour WORLD_MAP_ITEM_BEHAVIOUR = WorldMapItemBehaviour.getInstance();

    protected ItemWorldMap(int i) {
        super(i);
        this.c(1);
    }

    public WorldMap a(ItemStack itemstack, World world) {
        return WORLD_MAP_ITEM_BEHAVIOUR.resolveMap(itemstack, world);
    }

    public void a(World world, Entity entity, WorldMap worldmap) {
        WORLD_MAP_ITEM_BEHAVIOUR.updateMapData(world, entity, worldmap);
    }

    public void a(ItemStack itemstack, World world, Entity entity, int i, boolean flag) {
        WORLD_MAP_ITEM_BEHAVIOUR.onUpdate(itemstack, world, entity, flag);
    }

    public void c(ItemStack itemstack, World world, EntityHuman entityhuman) {
        WORLD_MAP_ITEM_BEHAVIOUR.onCrafted(itemstack, world, entityhuman);
    }

    public Packet b(ItemStack itemstack, World world, EntityHuman entityhuman) {
        return WORLD_MAP_ITEM_BEHAVIOUR.createUpdatePacket(itemstack, world, entityhuman);
    }
}
