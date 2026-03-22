package com.legacyminecraft.poseidon.entity;

import net.minecraft.server.EntityZombie;
import net.minecraft.server.Item;
import net.minecraft.server.MathHelper;
import org.bukkit.event.entity.EntityCombustEvent;

public final class ZombieLifecycleBehaviour {
    private static final ZombieLifecycleBehaviour INSTANCE = new ZombieLifecycleBehaviour();

    private ZombieLifecycleBehaviour() {
    }

    public static ZombieLifecycleBehaviour getInstance() {
        return INSTANCE;
    }

    public void tickSunlightCombustion(EntityZombie zombie, float brightness, float randomRoll) {
        if (!zombie.world.d()) {
            return;
        }

        boolean brightEnough = brightness > 0.5F;
        boolean chunkLoaded = zombie.world.isChunkLoaded(MathHelper.floor(zombie.locX), MathHelper.floor(zombie.locY), MathHelper.floor(zombie.locZ));
        boolean shouldIgnite = randomRoll * 30.0F < (brightness - 0.4F) * 2.0F;

        if (brightEnough && chunkLoaded && shouldIgnite) {
            EntityCombustEvent event = new EntityCombustEvent(zombie.getBukkitEntity());
            zombie.world.getServer().getPluginManager().callEvent(event);

            if (!event.isCancelled()) {
                zombie.fireTicks = 300;
            }
        }
    }

    public String getAmbientSound() {
        return "mob.zombie";
    }

    public String getHurtSound() {
        return "mob.zombiehurt";
    }

    public String getDeathSound() {
        return "mob.zombiedeath";
    }

    public int getDropItemId() {
        return Item.FEATHER.id;
    }
}
