package com.legacyminecraft.poseidon.item;

import net.minecraft.server.Item;
import net.minecraft.server.ItemStack;
import net.minecraft.server.NBTTagCompound;

public final class ItemStackStateBehaviour {
    private static final ItemStackStateBehaviour INSTANCE = new ItemStackStateBehaviour();

    private ItemStackStateBehaviour() {
    }

    public static ItemStackStateBehaviour getInstance() {
        return INSTANCE;
    }

    public ItemStack splitStack(ItemStack original, int amount) {
        original.count -= amount;
        return new ItemStack(original.id, amount, original.damage);
    }

    public NBTTagCompound writeToNbt(ItemStack stack, NBTTagCompound tag) {
        tag.a("id", (short) stack.id);
        tag.a("Count", (byte) stack.count);
        tag.a("Damage", (short) stack.damage);
        return tag;
    }

    public void readFromNbt(ItemStack stack, NBTTagCompound tag) {
        stack.id = tag.d("id");
        stack.count = tag.c("Count");
        stack.damage = tag.d("Damage");
    }

    public boolean isStackable(ItemStack stack) {
        return stack.getMaxStackSize() > 1 && (!stack.d() || !stack.f());
    }

    public boolean isDamaged(ItemStack stack) {
        return stack.d() && stack.damage > 0;
    }

    public int maxDurability(ItemStack stack) {
        return Item.byId[stack.id].e();
    }

    public boolean stackEqualsNullable(ItemStack a, ItemStack b) {
        return a == null && b == null ? true : (a != null && b != null ? a.countIdDamageEquals(b) : false);
    }

    public boolean countIdDamageEquals(ItemStack a, ItemStack b) {
        return a.count == b.count && a.id == b.id && a.damage == b.damage;
    }

    public boolean materialsMatch(ItemStack a, ItemStack b) {
        return a.id == b.id && a.damage == b.damage;
    }

    public ItemStack cloneOrNull(ItemStack stack) {
        return stack == null ? null : stack.cloneItemStack();
    }

    public ItemStack cloneStack(ItemStack stack) {
        return new ItemStack(stack.id, stack.count, stack.damage);
    }

    public boolean strictEquals(ItemStack a, ItemStack b) {
        return a.id == b.id && a.count == b.count && a.damage == b.damage;
    }

    public String stringify(ItemStack stack) {
        return stack.count + "x" + (stack.id < 0 || stack.id >= Item.byId.length ? "missingno" : Item.byId[stack.id].a()) + "@" + stack.damage;
    }
}
