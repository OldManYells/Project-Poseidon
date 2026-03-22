package com.legacyminecraft.poseidon.item;

import net.minecraft.server.Block;
import net.minecraft.server.Entity;
import net.minecraft.server.EntityHuman;
import net.minecraft.server.EntityLiving;
import net.minecraft.server.EntityPlayer;
import net.minecraft.server.Item;
import net.minecraft.server.ItemStack;
import net.minecraft.server.StatisticList;
import net.minecraft.server.World;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerItemDamageEvent;

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

        if (entity instanceof EntityPlayer) {
            PlayerItemDamageEvent event = new PlayerItemDamageEvent(
                    (Player) entity.getBukkitEntity(),
                    new org.bukkit.inventory.ItemStack(stack.id, stack.count, (short) stack.damage),
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
            if (entity instanceof EntityHuman) {
                ((EntityHuman) entity).a(StatisticList.F[stack.id], 1);
            }

            --stack.count;
            if (stack.count < 0) {
                stack.count = 0;
            }

            stack.damage = 0;
        }
    }

    public void onHitEntity(ItemStack stack, EntityLiving target, EntityHuman entityhuman) {
        boolean didHit = Item.byId[stack.id].a(stack, target, (EntityLiving) entityhuman);
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
}
