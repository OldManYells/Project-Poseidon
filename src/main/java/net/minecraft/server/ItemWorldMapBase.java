package net.minecraft.server;

import com.legacyminecraft.poseidon.item.WorldMapBaseItemBehaviour;

public class ItemWorldMapBase extends Item {
    private static final WorldMapBaseItemBehaviour WORLD_MAP_BASE_ITEM_BEHAVIOUR = WorldMapBaseItemBehaviour.getInstance();

    protected ItemWorldMapBase(int i) {
        super(i);
    }

    public boolean b() {
        return WORLD_MAP_BASE_ITEM_BEHAVIOUR.isMapRendererItem();
    }

    public Packet b(ItemStack itemstack, World world, EntityHuman entityhuman) {
        return WORLD_MAP_BASE_ITEM_BEHAVIOUR.createUpdatePacket(itemstack, world, entityhuman);
    }
}
