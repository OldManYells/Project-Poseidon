package com.legacyminecraft.poseidon.entity;

public final class SpiderCombatBehaviour {
    private static final SpiderCombatBehaviour INSTANCE = new SpiderCombatBehaviour();

    private SpiderCombatBehaviour() {
    }

    public static SpiderCombatBehaviour getInstance() {
        return INSTANCE;
    }

    public Entity findTarget(EntitySpider spider, float brightness) {
        if (brightness < 0.5F) {
            return spider.world.findNearbyPlayer(spider, 16.0D);
        }
        return null;
    }

    public String getAmbientSound() {
        return "mob.spider";
    }

    public String getHurtSound() {
        return "mob.spider";
    }

    public String getDeathSound() {
        return "mob.spiderdeath";
    }

    public boolean handleAttack(EntitySpider spider, Entity target, float distance, float brightness, int forgetTargetRoll, int leapRoll) {
        if (brightness > 0.5F && forgetTargetRoll == 0) {
            spider.setTarget(null);
            return true;
        }

        if (distance > 2.0F && distance < 6.0F && leapRoll == 0 && spider.onGround) {
            double deltaX = target.locX - spider.locX;
            double deltaZ = target.locZ - spider.locZ;
            float horizontalDistance = MathHelper.a(deltaX * deltaX + deltaZ * deltaZ);

            spider.motX = deltaX / (double) horizontalDistance * 0.5D * 0.800000011920929D + spider.motX * 0.20000000298023224D;
            spider.motZ = deltaZ / (double) horizontalDistance * 0.5D * 0.800000011920929D + spider.motZ * 0.20000000298023224D;
            spider.motY = 0.4000000059604645D;
            return true;
        }

        return false;
    }

    public int getDropItemId() {
        return com.legacyminecraft.compat.bukkit.Item.STRING.id;
    }
}
