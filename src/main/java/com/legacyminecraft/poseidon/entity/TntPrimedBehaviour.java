package com.legacyminecraft.poseidon.entity;

import net.minecraft.server.EntityTNTPrimed;
import net.minecraft.server.MathHelper;
import net.minecraft.server.NBTTagCompound;
import org.bukkit.Server;
import org.bukkit.entity.Explosive;
import org.bukkit.event.entity.ExplosionPrimeEvent;

public final class TntPrimedBehaviour {
    private static final TntPrimedBehaviour INSTANCE = new TntPrimedBehaviour();

    private TntPrimedBehaviour() {
    }

    public static TntPrimedBehaviour getInstance() {
        return INSTANCE;
    }

    public void initializeSpawnMotion(EntityTNTPrimed tnt, float randomAngle) {
        tnt.motX = (double) (-MathHelper.sin(randomAngle * 3.1415927F / 180.0F) * 0.02F);
        tnt.motY = 0.20000000298023224D;
        tnt.motZ = (double) (-MathHelper.cos(randomAngle * 3.1415927F / 180.0F) * 0.02F);
        tnt.fuseTicks = 80;
    }

    public void tickPreMove(EntityTNTPrimed tnt) {
        tnt.lastX = tnt.locX;
        tnt.lastY = tnt.locY;
        tnt.lastZ = tnt.locZ;
        tnt.motY -= 0.03999999910593033D;
    }

    public void tickPostMove(EntityTNTPrimed tnt) {
        tnt.motX *= 0.9800000190734863D;
        tnt.motY *= 0.9800000190734863D;
        tnt.motZ *= 0.9800000190734863D;

        if (tnt.onGround) {
            tnt.motX *= 0.699999988079071D;
            tnt.motZ *= 0.699999988079071D;
            tnt.motY *= -0.5D;
        }
    }

    public FuseTickResult tickFuse(int fuseTicks) {
        int updatedFuse = fuseTicks - 1;
        boolean explodeNow = fuseTicks <= 0;
        return new FuseTickResult(updatedFuse, explodeNow);
    }

    public void spawnFuseSmoke(EntityTNTPrimed tnt) {
        tnt.world.a("smoke", tnt.locX, tnt.locY + 0.5D, tnt.locZ, 0.0D, 0.0D, 0.0D);
    }

    public void explode(EntityTNTPrimed tnt) {
        Server server = tnt.world.getServer();
        ExplosionPrimeEvent event = new ExplosionPrimeEvent((Explosive) tnt.getBukkitEntity());
        server.getPluginManager().callEvent(event);

        if (!event.isCancelled()) {
            tnt.world.createExplosion(tnt, tnt.locX, tnt.locY, tnt.locZ, event.getRadius(), event.getFire());
        }
    }

    public void writeFuseNbt(NBTTagCompound nbt, int fuseTicks) {
        nbt.a("Fuse", (byte) fuseTicks);
    }

    public int readFuseNbt(NBTTagCompound nbt) {
        return nbt.c("Fuse");
    }

    public static final class FuseTickResult {
        public final int fuseTicks;
        public final boolean explodeNow;

        public FuseTickResult(int fuseTicks, boolean explodeNow) {
            this.fuseTicks = fuseTicks;
            this.explodeNow = explodeNow;
        }
    }
}
