package org.bukkit.craftbukkit.inventory;

import com.legacyminecraft.poseidon.compat.bukkit.CraftItemStackStateBridgeBehaviour;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class CraftItemStack extends ItemStack {
    private static final CraftItemStackStateBridgeBehaviour CRAFT_ITEM_STACK_STATE_BRIDGE_BEHAVIOUR =
            CraftItemStackStateBridgeBehaviour.getInstance();

    protected net.minecraft.server.ItemStack item;

    public CraftItemStack(net.minecraft.server.ItemStack item) {
        super(
            item != null ? item.id: 0,
            item != null ? item.count : 0,
            (short)(item != null ? item.damage : 0)
        );
        this.item = item;
    }

    /* 'Overwritten' constructors from ItemStack, yay for Java sucking */
    public CraftItemStack(final int type) {
        this(type, 0);
    }

    public CraftItemStack(final Material type) {
        this(type, 0);
    }

    public CraftItemStack(final int type, final int amount) {
        this(type, amount, (byte) 0);
    }

    public CraftItemStack(final Material type, final int amount) {
        this(type.getId(), amount);
    }

    public CraftItemStack(final int type, final int amount, final short damage) {
        this(type, amount, damage, null);
    }

    public CraftItemStack(final Material type, final int amount, final short damage) {
        this(type.getId(), amount, damage);
    }

    public CraftItemStack(final Material type, final int amount, final short damage, final Byte data) {
        this(type.getId(), amount, damage, data);
    }

    public CraftItemStack(int type, int amount, short damage, Byte data) {
        this(new net.minecraft.server.ItemStack(type, amount, data != null ? data : damage));
    }

    /*
     * Unsure if we have to sync before each of these calls the values in 'item'
     * are all public.
     */

    @Override
    public Material getType() {
        super.setTypeId(CRAFT_ITEM_STACK_STATE_BRIDGE_BEHAVIOUR.syncTypeId(item));
        return super.getType();
    }

    @Override
    public int getTypeId() {
        int typeId = CRAFT_ITEM_STACK_STATE_BRIDGE_BEHAVIOUR.syncTypeId(item);
        super.setTypeId(typeId);
        return typeId;
    }

    @Override
    public void setTypeId(int type) {
        item = CRAFT_ITEM_STACK_STATE_BRIDGE_BEHAVIOUR.applyTypeId(item, type, createSuperItemAccess());
    }

    @Override
    public int getAmount() {
        int amount = CRAFT_ITEM_STACK_STATE_BRIDGE_BEHAVIOUR.syncAmount(item);
        super.setAmount(amount);
        return amount;
    }

    @Override
    public void setAmount(int amount) {
        item = CRAFT_ITEM_STACK_STATE_BRIDGE_BEHAVIOUR.applyAmount(item, amount, createSuperItemAccess());
    }

    @Override
    public void setDurability(final short durability) {
        CRAFT_ITEM_STACK_STATE_BRIDGE_BEHAVIOUR.applyDurability(item, durability, createSuperItemAccess());
    }

    @Override
    public short getDurability() {
        return CRAFT_ITEM_STACK_STATE_BRIDGE_BEHAVIOUR.syncDurability(item, createSuperItemAccess());
    }

    @Override
    public int getMaxStackSize() {
        return CRAFT_ITEM_STACK_STATE_BRIDGE_BEHAVIOUR.maxStackSize(item);
    }

    private CraftItemStackStateBridgeBehaviour.SuperItemAccess createSuperItemAccess() {
        return new CraftItemStackStateBridgeBehaviour.SuperItemAccess() {
            public void setTypeId(int typeId) {
                CraftItemStack.super.setTypeId(typeId);
            }

            public void setAmount(int amount) {
                CraftItemStack.super.setAmount(amount);
            }

            public void setDurability(short durability) {
                CraftItemStack.super.setDurability(durability);
            }
        };
    }
}
