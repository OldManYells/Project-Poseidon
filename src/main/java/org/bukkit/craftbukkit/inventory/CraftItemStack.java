package org.bukkit.craftbukkit.inventory;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class CraftItemStack extends ItemStack {
    protected net.minecraft.server.ItemStack item;

    public CraftItemStack(net.minecraft.server.ItemStack item) {
        super(
                item != null ? item.id : 0,
                item != null ? item.count : 0,
                (short) (item != null ? item.damage : 0)
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

    private void syncSuperState() {
        if (item == null) {
            super.setTypeId(0);
            super.setAmount(0);
            super.setDurability((short) 0);
            return;
        }

        super.setTypeId(item.id);
        super.setAmount(item.count);
        super.setDurability((short) item.damage);
    }

    @Override
    public Material getType() {
        syncSuperState();
        return super.getType();
    }

    @Override
    public int getTypeId() {
        syncSuperState();
        return super.getTypeId();
    }

    @Override
    public void setTypeId(int type) {
        if (type == 0) {
            item = null;
            syncSuperState();
            return;
        }

        if (item == null) {
            item = new net.minecraft.server.ItemStack(type, Math.max(1, super.getAmount()), super.getDurability());
        } else {
            item.id = type;
        }

        syncSuperState();
    }

    @Override
    public int getAmount() {
        return item != null ? item.count : super.getAmount();
    }

    @Override
    public void setAmount(int amount) {
        if (amount <= 0) {
            item = null;
            syncSuperState();
            return;
        }

        if (item == null) {
            item = new net.minecraft.server.ItemStack(super.getTypeId(), amount, super.getDurability());
        } else {
            item.count = amount;
        }

        syncSuperState();
    }

    @Override
    public void setDurability(final short durability) {
        if (item == null) {
            if (durability == 0) {
                super.setDurability(durability);
                return;
            }
            item = new net.minecraft.server.ItemStack(super.getTypeId(), Math.max(1, super.getAmount()), durability);
        } else {
            item.damage = durability;
        }

        syncSuperState();
    }

    @Override
    public short getDurability() {
        return item != null ? (short) item.damage : super.getDurability();
    }

    @Override
    public int getMaxStackSize() {
        return item != null ? item.getMaxStackSize() : super.getMaxStackSize();
    }
}
