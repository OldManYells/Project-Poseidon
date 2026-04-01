package org.bukkit.craftbukkit.entity;

import com.legacy.minecraft.poseidon.EntityEgg;
import com.legacy.minecraft.poseidon.EntityLiving;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.entity.Egg;
import org.bukkit.entity.LivingEntity;

public class CraftEgg extends AbstractProjectile implements Egg {

    public CraftEgg(CraftServer server, EntityEgg entity) {
        super(server, entity);
    }

    @Override
    public String toString() {
        return "CraftEgg";
    }

    public LivingEntity getShooter() {
        if (((EntityEgg) getHandle()).thrower != null) {
            return (LivingEntity) ((EntityEgg) getHandle()).thrower.getBukkitEntity();
        }

        return null;

    }

    public void setShooter(LivingEntity shooter) {
        if (shooter instanceof CraftLivingEntity) {
            ((EntityEgg) getHandle()).thrower = (EntityLiving) ((CraftLivingEntity) shooter).entity;
        }
    }
}
