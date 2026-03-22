package net.minecraft.server;

import com.legacyminecraft.poseidon.inventory.FurnaceResultSlotBehaviour;

public class SlotResult2 extends Slot {
    private static final FurnaceResultSlotBehaviour FURNACE_RESULT_SLOT_BEHAVIOUR = FurnaceResultSlotBehaviour.getInstance();

    private EntityHuman d;

    public SlotResult2(EntityHuman entityhuman, IInventory iinventory, int i, int j, int k) {
        super(iinventory, i, j, k);
        this.d = entityhuman;
    }

    public boolean isAllowed(ItemStack itemstack) {
        return FURNACE_RESULT_SLOT_BEHAVIOUR.isAllowed(itemstack);
    }

    public void a(ItemStack itemstack) {
        FURNACE_RESULT_SLOT_BEHAVIOUR.onSmelted(itemstack, this.d);
        super.a(itemstack);
    }
}
