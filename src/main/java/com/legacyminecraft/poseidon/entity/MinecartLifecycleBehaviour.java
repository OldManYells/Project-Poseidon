package com.legacyminecraft.poseidon.entity;

import net.minecraft.server.Block;
import net.minecraft.server.Entity;
import net.minecraft.server.EntityHuman;
import net.minecraft.server.EntityItem;
import net.minecraft.server.EntityMinecart;
import net.minecraft.server.Item;
import net.minecraft.server.ItemStack;
import org.bukkit.entity.Vehicle;
import org.bukkit.event.vehicle.VehicleDamageEvent;
import org.bukkit.event.vehicle.VehicleDestroyEvent;
import org.bukkit.event.vehicle.VehicleEnterEvent;

public final class MinecartLifecycleBehaviour {
    private static final MinecartLifecycleBehaviour INSTANCE = new MinecartLifecycleBehaviour();

    private MinecartLifecycleBehaviour() {
    }

    public static MinecartLifecycleBehaviour getInstance() {
        return INSTANCE;
    }

    public boolean handleDamage(EntityMinecart minecart, Entity attacker, int damageAmount) {
        if (minecart.world.isStatic || minecart.dead) {
            return true;
        }

        Vehicle vehicle = (Vehicle) minecart.getBukkitEntity();
        org.bukkit.entity.Entity bukkitAttacker = attacker == null ? null : attacker.getBukkitEntity();
        VehicleDamageEvent event = new VehicleDamageEvent(vehicle, bukkitAttacker, damageAmount);
        minecart.world.getServer().getPluginManager().callEvent(event);
        if (event.isCancelled()) {
            return true;
        }

        int appliedDamage = event.getDamage();
        minecart.c = -minecart.c;
        minecart.b = 10;
        minecart.poseidonMarkDamaged();
        minecart.damage += appliedDamage * 10;
        if (minecart.damage > 40) {
            if (minecart.passenger != null) {
                minecart.passenger.mount(minecart);
            }

            VehicleDestroyEvent destroyEvent = new VehicleDestroyEvent(vehicle, bukkitAttacker);
            minecart.world.getServer().getPluginManager().callEvent(destroyEvent);
            if (destroyEvent.isCancelled()) {
                minecart.damage = 40;
                return true;
            }

            minecart.die();
            minecart.poseidonDropEntityItem(Item.MINECART.id, 1, 0.0F);
            if (minecart.type == 1) {
                dropInventoryContents(minecart);
                minecart.poseidonDropEntityItem(Block.CHEST.id, 1, 0.0F);
            } else if (minecart.type == 2) {
                minecart.poseidonDropEntityItem(Block.FURNACE.id, 1, 0.0F);
            }
        }

        return true;
    }

    public void dropInventoryContents(EntityMinecart minecart) {
        for (int slot = 0; slot < minecart.getSize(); ++slot) {
            ItemStack stack = minecart.getItem(slot);
            if (stack == null) {
                continue;
            }

            float offsetX = minecart.poseidonRandomFloat() * 0.8F + 0.1F;
            float offsetY = minecart.poseidonRandomFloat() * 0.8F + 0.1F;
            float offsetZ = minecart.poseidonRandomFloat() * 0.8F + 0.1F;

            while (stack.count > 0) {
                int splitCount = minecart.poseidonRandomInt(21) + 10;
                if (splitCount > stack.count) {
                    splitCount = stack.count;
                }

                stack.count -= splitCount;
                EntityItem dropped = new EntityItem(
                        minecart.world,
                        minecart.locX + (double) offsetX,
                        minecart.locY + (double) offsetY,
                        minecart.locZ + (double) offsetZ,
                        new ItemStack(stack.id, splitCount, stack.getData())
                );
                float spread = 0.05F;
                dropped.motX = (double) ((float) minecart.poseidonRandomGaussian() * spread);
                dropped.motY = (double) ((float) minecart.poseidonRandomGaussian() * spread + 0.2F);
                dropped.motZ = (double) ((float) minecart.poseidonRandomGaussian() * spread);
                minecart.world.addEntity(dropped);
            }

            minecart.setItem(slot, null);
        }
    }

    public boolean interact(EntityMinecart minecart, EntityHuman player) {
        if (minecart.type == 0) {
            return interactRideable(minecart, player);
        }
        if (minecart.type == 1) {
            if (!minecart.world.isStatic) {
                player.a(minecart);
            }
            return true;
        }
        if (minecart.type == 2) {
            return interactFurnace(minecart, player);
        }
        return true;
    }

    private boolean interactRideable(EntityMinecart minecart, EntityHuman player) {
        if (minecart.passenger != null && minecart.passenger instanceof EntityHuman && minecart.passenger != player) {
            return true;
        }

        if (!minecart.world.isStatic) {
            VehicleEnterEvent event = new VehicleEnterEvent((Vehicle) minecart.getBukkitEntity(), player.getBukkitEntity());
            minecart.world.getServer().getPluginManager().callEvent(event);
            if (event.isCancelled()) {
                return true;
            }
            player.mount(minecart);
        }

        return true;
    }

    private boolean interactFurnace(EntityMinecart minecart, EntityHuman player) {
        ItemStack inHand = player.inventory.getItemInHand();
        if (inHand != null && inHand.id == Item.COAL.id) {
            if (--inHand.count == 0) {
                player.inventory.setItem(player.inventory.itemInHandIndex, (ItemStack) null);
            }
            minecart.e += 1200;
        }

        minecart.f = minecart.locX - player.locX;
        minecart.g = minecart.locZ - player.locZ;
        return true;
    }
}
