package com.legacyminecraft.compat.bukkit;


/**
 * Canonical behaviour for projecting between NMS item stacks and CraftItemStack wrappers.
 */
public final class ItemStackProjectionBridgeBehaviour {
    private static final ItemStackProjectionBridgeBehaviour INSTANCE = new ItemStackProjectionBridgeBehaviour();

    private ItemStackProjectionBridgeBehaviour() {
    }

    public static ItemStackProjectionBridgeBehaviour getInstance() {
        return INSTANCE;
    }

    public <T> T toCraftItemStack(Object itemStack) {
        return BridgeReflection.cast(new CraftItemStack(BridgeReflection.cast(itemStack)));
    }

    public <T> T toCraftItemStackFromItem(Object item) {
        try {
            Class<?> nmsItemStackClass = Class.forName("net.minecraft.server.ItemStack");
            Object nmsStack = nmsItemStackClass.getConstructor(Class.forName("net.minecraft.server.Item")).newInstance(item);
            return toCraftItemStack(nmsStack);
        } catch (Exception exception) {
            throw new IllegalStateException("Unable to project item to CraftItemStack", exception);
        }
    }

    public <T> T toCraftItemStackCopy(Object itemStack) {
        return toCraftItemStack(itemStack == null ? null : BridgeReflection.invoke(itemStack, "cloneItemStack"));
    }

    public Object toNmsItemStack(com.legacyminecraft.compat.bukkit.inventory.ItemStack itemStack) {
        if (itemStack == null) {
            return null;
        }
        try {
            Class<?> nmsItemStackClass = Class.forName("net.minecraft.server.ItemStack");
            return nmsItemStackClass
                    .getConstructor(int.class, int.class, int.class)
                    .newInstance(itemStack.getTypeId(), itemStack.getAmount(), itemStack.getDurability());
        } catch (Exception exception) {
            throw new IllegalStateException("Unable to project Bukkit stack to NMS stack", exception);
        }
    }
}
