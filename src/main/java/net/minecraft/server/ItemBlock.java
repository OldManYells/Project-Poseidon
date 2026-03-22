package net.minecraft.server;

import com.legacyminecraft.poseidon.item.ItemBlockPlacementBehaviour;

public class ItemBlock extends Item {
    private static final ItemBlockPlacementBehaviour ITEM_BLOCK_PLACEMENT_BEHAVIOUR = ItemBlockPlacementBehaviour.getInstance();

    private int id;

    public ItemBlock(int i) {
        super(i);
        this.id = i + 256;
        this.b(Block.byId[i + 256].a(2));
    }

    public boolean a(ItemStack itemstack, EntityHuman entityhuman, World world, int i, int j, int k, int l) {
        ItemBlockPlacementBehaviour.PlacementResult result = ITEM_BLOCK_PLACEMENT_BEHAVIOUR.place(
                itemstack, entityhuman, world, i, j, k, l, this.id, this.filterData(itemstack.getData()));
        if (!result.handled) {
            return false;
        }
        if (!result.placed) {
            return true;
        }

        if (ITEM_BLOCK_PLACEMENT_BEHAVIOUR.usePistonPostPlaceOrderingFix(this.id)) {
            Block.byId[this.id].postPlace(world, result.x, result.y, result.z, result.face);
            Block.byId[this.id].postPlace(world, result.x, result.y, result.z, entityhuman);
            world.update(result.x, result.y, result.z, this.id);
        } else {
            world.update(result.x, result.y, result.z, this.id);
            Block.byId[this.id].postPlace(world, result.x, result.y, result.z, result.face);
            Block.byId[this.id].postPlace(world, result.x, result.y, result.z, entityhuman);
        }

        world.makeSound((double) ((float) result.x + 0.5F), (double) ((float) result.y + 0.5F), (double) ((float) result.z + 0.5F),
                result.block.stepSound.getName(), (result.block.stepSound.getVolume1() + 1.0F) / 2.0F, result.block.stepSound.getVolume2() * 0.8F);
        --itemstack.count;
        return true;
    }

    public String a() {
        return ITEM_BLOCK_PLACEMENT_BEHAVIOUR.resolveTranslationKey(this.id);
    }
}
