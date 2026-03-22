package com.legacyminecraft.poseidon.compat.bukkit;

/**
 * Canonical behaviour for CraftItemStack state sync/mutation policy.
 */
public final class CraftItemStackStateBridgeBehaviour {
    private static final CraftItemStackStateBridgeBehaviour INSTANCE = new CraftItemStackStateBridgeBehaviour();

    private CraftItemStackStateBridgeBehaviour() {
    }

    public static CraftItemStackStateBridgeBehaviour getInstance() {
        return INSTANCE;
    }

    public int syncTypeId(net.minecraft.server.ItemStack item) {
        return item != null ? item.id : 0;
    }

    public net.minecraft.server.ItemStack applyTypeId(
            net.minecraft.server.ItemStack item,
            int type,
            SuperItemAccess superItemAccess
    ) {
        if (type == 0) {
            superItemAccess.setTypeId(0);
            superItemAccess.setAmount(0);
            return null;
        }

        if (item == null) {
            net.minecraft.server.ItemStack created = new net.minecraft.server.ItemStack(type, 1, 0);
            superItemAccess.setAmount(1);
            return created;
        }

        item.id = type;
        superItemAccess.setTypeId(item.id);
        return item;
    }

    public int syncAmount(net.minecraft.server.ItemStack item) {
        return item != null ? item.count : 0;
    }

    public net.minecraft.server.ItemStack applyAmount(
            net.minecraft.server.ItemStack item,
            int amount,
            SuperItemAccess superItemAccess
    ) {
        if (amount == 0) {
            superItemAccess.setTypeId(0);
            superItemAccess.setAmount(0);
            return null;
        }

        superItemAccess.setAmount(amount);
        item.count = amount;
        return item;
    }

    public void applyDurability(
            net.minecraft.server.ItemStack item,
            short durability,
            SuperItemAccess superItemAccess
    ) {
        if (item != null) {
            superItemAccess.setDurability(durability);
            item.damage = durability;
        }
    }

    public short syncDurability(
            net.minecraft.server.ItemStack item,
            SuperItemAccess superItemAccess
    ) {
        if (item != null) {
            superItemAccess.setDurability((short) item.damage);
            return (short) item.damage;
        }
        return -1;
    }

    public int maxStackSize(net.minecraft.server.ItemStack item) {
        return item.getItem().getMaxStackSize();
    }

    public interface SuperItemAccess {
        void setTypeId(int typeId);

        void setAmount(int amount);

        void setDurability(short durability);
    }
}
