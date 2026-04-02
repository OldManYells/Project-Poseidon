package org.bukkit.craftbukkit.entity;
import com.legacyminecraft.poseidon.world.entity.*;

import com.legacyminecraft.poseidon.world.entity.EntityArrow;
import com.legacyminecraft.poseidon.world.entity.EntityLiving;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.LivingEntity;

public class CraftArrow extends AbstractProjectile implements Arrow {

    public CraftArrow(CraftServer server, EntityArrow entity) {
        super(server, entity);
    }

    @Override
    public String toString() {
        return "CraftArrow";
    }

    public LivingEntity getShooter() {
        if (((EntityArrow) getHandle()).shooter != null) {
            return (LivingEntity) ((EntityArrow) getHandle()).shooter.getBukkitEntity();
        }

        return null;

    }

    public void setShooter(LivingEntity shooter) {
        if (shooter instanceof CraftLivingEntity) {
            ((EntityArrow) getHandle()).shooter = (EntityLiving) ((CraftLivingEntity) shooter).entity;
        }
    }
}
