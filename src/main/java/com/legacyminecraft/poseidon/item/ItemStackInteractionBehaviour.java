package com.legacyminecraft.poseidon.item;

import com.legacyminecraft.poseidon.block.Block;
import com.legacyminecraft.poseidon.entity.EntityHuman;
import com.legacyminecraft.poseidon.world.Entity;
import com.legacyminecraft.poseidon.world.World;

public final class ItemStackInteractionBehaviour {
    private static final ItemStackInteractionBehaviour INSTANCE = new ItemStackInteractionBehaviour();

    private ItemStackInteractionBehaviour() {
    }

    public static ItemStackInteractionBehaviour getInstance() {
        return INSTANCE;
    }

    public boolean placeItem(ItemStack stack, EntityHuman entityhuman, World world, int blockX, int blockY, int blockZ, int face) {
        boolean wasPlaced = stack.getItem().a(stack, entityhuman, world, blockX, blockY, blockZ, face);
        if (wasPlaced) {
            entityhuman.a(StatisticList.E[stack.id], 1);
        }
        return wasPlaced;
    }

    public ItemStack useItem(ItemStack stack, World world, EntityHuman entityhuman) {
        return stack.getItem().a(stack, world, entityhuman);
    }

    @SuppressWarnings("deprecation")
    public void damage(ItemStack stack, int amount, Entity entity) {
        if (!stack.d()) {
            return;
        }

        if (isEntityPlayer(entity)) {
            PlayerItemDamageEvent event = new PlayerItemDamageEvent(
                    (Player) entity.getBukkitEntity(),
                    new ItemStack(stack.id, stack.count, stack.damage),
                    amount
            );
            event.getPlayer().getServer().getPluginManager().callEvent(event);
            if (amount != event.getDamage() || event.isCancelled()) {
                event.getPlayer().updateInventory();
            }
            if (event.isCancelled()) {
                return;
            }
            amount = event.getDamage();
        }

        stack.damage += amount;
        if (stack.damage > stack.i()) {
            if (isEntityHuman(entity)) {
                awardStat(entity, StatisticList.F[stack.id], 1);
            }

            --stack.count;
            if (stack.count < 0) {
                stack.count = 0;
            }

            stack.damage = 0;
        }
    }

    public void onHitEntity(ItemStack stack, EntityLiving target, EntityHuman entityhuman) {
        boolean didHit = Item.byId[stack.id].a(stack, target, null);
        if (didHit) {
            entityhuman.a(StatisticList.E[stack.id], 1);
        }
    }

    public void onDestroyBlock(ItemStack stack, int blockX, int blockY, int blockZ, int blockId, EntityHuman entityhuman) {
        boolean didDestroy = Item.byId[stack.id].a(stack, blockX, blockY, blockZ, blockId, entityhuman);
        if (didDestroy) {
            entityhuman.a(StatisticList.E[stack.id], 1);
        }
    }

    public int attackDamage(ItemStack stack, Entity entity) {
        return Item.byId[stack.id].a(entity);
    }

    public boolean canHarvest(ItemStack stack, Block block) {
        return Item.byId[stack.id].a(block);
    }

    public void useOnLiving(ItemStack stack, EntityLiving entityliving) {
        Item.byId[stack.id].a(stack, entityliving);
    }

    public void onInventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean isHeld) {
        if (stack.b > 0) {
            --stack.b;
        }
        Item.byId[stack.id].a(stack, world, entity, slot, isHeld);
    }

    public void onCrafted(ItemStack stack, World world, EntityHuman entityhuman) {
        entityhuman.a(StatisticList.D[stack.id], stack.count);
        Item.byId[stack.id].c(stack, world, entityhuman);
    }

    private static boolean isEntityPlayer(Entity entity) {
        return entity != null && hasClassName(entity, "EntityPlayer");
    }

    private static boolean isEntityHuman(Entity entity) {
        return entity != null && hasClassName(entity, "EntityHuman");
    }

    private static boolean hasClassName(Object value, String simpleName) {
        Class<?> current = value.getClass();
        while (current != null) {
            if (simpleName.equals(current.getSimpleName())) {
                return true;
            }
            current = current.getSuperclass();
        }
        return false;
    }

    private static void awardStat(Object entity, Object statistic, int count) {
        try {
            java.lang.reflect.Method[] methods = entity.getClass().getMethods();
            for (int index = 0; index < methods.length; index++) {
                java.lang.reflect.Method method = methods[index];
                if (method.getName().equals("a") && method.getParameterTypes().length == 2) {
                    method.invoke(entity, statistic, Integer.valueOf(count));
                    return;
                }
            }
        } catch (ReflectiveOperationException ignored) {
        }
    }
}
