package com.legacyminecraft.poseidon.entity;

import net.minecraft.server.Entity;
import net.minecraft.server.EntityHuman;
import net.minecraft.server.EntityLiving;
import net.minecraft.server.EntityMinecart;
import net.minecraft.server.MathHelper;
import org.bukkit.entity.Vehicle;
import org.bukkit.event.vehicle.VehicleEnterEvent;
import org.bukkit.event.vehicle.VehicleEntityCollisionEvent;

public final class MinecartCollisionBehaviour {
    private static final MinecartCollisionBehaviour INSTANCE = new MinecartCollisionBehaviour();

    private MinecartCollisionBehaviour() {
    }

    public static MinecartCollisionBehaviour getInstance() {
        return INSTANCE;
    }

    public void handleCollision(EntityMinecart minecart, Entity other) {
        if (minecart.world.isStatic) {
            return;
        }

        if (other == minecart.passenger) {
            return;
        }

        Vehicle vehicle = (Vehicle) minecart.getBukkitEntity();
        org.bukkit.entity.Entity hitEntity = other == null ? null : other.getBukkitEntity();

        VehicleEntityCollisionEvent collisionEvent = new VehicleEntityCollisionEvent(vehicle, hitEntity);
        minecart.world.getServer().getPluginManager().callEvent(collisionEvent);

        if (collisionEvent.isCancelled()) {
            return;
        }

        if (canAutoPickupRider(minecart, other) && !collisionEvent.isPickupCancelled()) {
            VehicleEnterEvent enterEvent = new VehicleEnterEvent(vehicle, hitEntity);
            minecart.world.getServer().getPluginManager().callEvent(enterEvent);
            if (!enterEvent.isCancelled()) {
                other.mount(minecart);
            }
        }

        double deltaX = other.locX - minecart.locX;
        double deltaZ = other.locZ - minecart.locZ;
        double distanceSq = deltaX * deltaX + deltaZ * deltaZ;

        if (distanceSq < 9.999999747378752E-5D || collisionEvent.isCollisionCancelled()) {
            return;
        }

        double distance = (double) MathHelper.a(distanceSq);
        deltaX /= distance;
        deltaZ /= distance;
        double inverseDistance = 1.0D / distance;
        if (inverseDistance > 1.0D) {
            inverseDistance = 1.0D;
        }

        deltaX *= inverseDistance;
        deltaZ *= inverseDistance;
        deltaX *= 0.10000000149011612D;
        deltaZ *= 0.10000000149011612D;
        deltaX *= minecart.poseidonCollisionReductionFactor();
        deltaZ *= minecart.poseidonCollisionReductionFactor();
        deltaX *= 0.5D;
        deltaZ *= 0.5D;

        if (other instanceof EntityMinecart) {
            collideMinecartToMinecart(minecart, (EntityMinecart) other, deltaX, deltaZ);
            return;
        }

        minecart.poseidonApplyCollisionPush(-deltaX, 0.0D, -deltaZ);
        other.b(deltaX / 4.0D, 0.0D, deltaZ / 4.0D);
    }

    private boolean canAutoPickupRider(EntityMinecart minecart, Entity other) {
        return other instanceof EntityLiving
                && !(other instanceof EntityHuman)
                && minecart.type == 0
                && minecart.motX * minecart.motX + minecart.motZ * minecart.motZ > 0.01D
                && minecart.passenger == null
                && other.vehicle == null;
    }

    private void collideMinecartToMinecart(EntityMinecart minecart, EntityMinecart otherMinecart, double deltaX, double deltaZ) {
        double relativeX = otherMinecart.locX - minecart.locX;
        double relativeZ = otherMinecart.locZ - minecart.locZ;
        double alignment = relativeX * otherMinecart.motZ + relativeZ * otherMinecart.lastX;
        alignment *= alignment;
        if (alignment > 5.0D) {
            return;
        }

        double combinedMotX = otherMinecart.motX + minecart.motX;
        double combinedMotZ = otherMinecart.motZ + minecart.motZ;

        if (otherMinecart.type == 2 && minecart.type != 2) {
            minecart.motX *= 0.20000000298023224D;
            minecart.motZ *= 0.20000000298023224D;
            minecart.poseidonApplyCollisionPush(otherMinecart.motX - deltaX, 0.0D, otherMinecart.motZ - deltaZ);
            otherMinecart.motX *= 0.699999988079071D;
            otherMinecart.motZ *= 0.699999988079071D;
        } else if (otherMinecart.type != 2 && minecart.type == 2) {
            otherMinecart.motX *= 0.20000000298023224D;
            otherMinecart.motZ *= 0.20000000298023224D;
            otherMinecart.poseidonApplyCollisionPush(minecart.motX + deltaX, 0.0D, minecart.motZ + deltaZ);
            minecart.motX *= 0.699999988079071D;
            minecart.motZ *= 0.699999988079071D;
        } else {
            combinedMotX /= 2.0D;
            combinedMotZ /= 2.0D;
            minecart.motX *= 0.20000000298023224D;
            minecart.motZ *= 0.20000000298023224D;
            minecart.poseidonApplyCollisionPush(combinedMotX - deltaX, 0.0D, combinedMotZ - deltaZ);
            otherMinecart.motX *= 0.20000000298023224D;
            otherMinecart.motZ *= 0.20000000298023224D;
            otherMinecart.poseidonApplyCollisionPush(combinedMotX + deltaX, 0.0D, combinedMotZ + deltaZ);
        }
    }
}
