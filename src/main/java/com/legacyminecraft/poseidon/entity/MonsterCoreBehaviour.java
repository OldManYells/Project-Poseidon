package com.legacyminecraft.poseidon.entity;

import com.legacyminecraft.compat.bukkit.EntityHandleBridgeBehaviour;

public final class MonsterCoreBehaviour {
    private static final MonsterCoreBehaviour INSTANCE = new MonsterCoreBehaviour();
    private static final EntityHandleBridgeBehaviour ENTITY_HANDLE_BRIDGE = EntityHandleBridgeBehaviour.getInstance();

    private MonsterCoreBehaviour() {
    }

    public static MonsterCoreBehaviour getInstance() {
        return INSTANCE;
    }

    public int updateSunlightPenalty(float brightness, int currentPenalty) {
        return brightness > 0.5F ? currentPenalty + 2 : currentPenalty;
    }

    public boolean shouldDespawnWhenMonstersDisabled(boolean worldStatic, int spawnMonstersFlag) {
        return !worldStatic && spawnMonstersFlag == 0;
    }

    public Entity findTarget(EntityMonster monster) {
        EntityHuman nearbyPlayer = monster.world.findNearbyPlayer(monster, 16.0D);
        return nearbyPlayer != null && monster.e(nearbyPlayer) ? nearbyPlayer : null;
    }

    public boolean onDamaged(EntityMonster monster, Entity attacker) {
        if (monster.passenger == attacker || monster.vehicle == attacker) {
            return true;
        }

        if (attacker != monster) {
            com.legacyminecraft.compat.bukkit.entity.Entity bukkitTarget = attacker == null ? null : attacker.getBukkitEntity();
            EntityTargetEvent event = new EntityTargetEvent(monster.getBukkitEntity(), bukkitTarget, EntityTargetEvent.TargetReason.TARGET_ATTACKED_ENTITY);
            monster.world.getServer().getPluginManager().callEvent(event);

            if (!event.isCancelled()) {
                if (event.getTarget() == null) {
                    monster.setTarget(null);
                } else {
                    monster.setTarget(ENTITY_HANDLE_BRIDGE.resolveHandle(event.getTarget()));
                }
            }
        }

        return true;
    }

    public void attackTarget(EntityMonster monster, Entity target, float distance, int damage) {
        if (monster.attackTicks > 0 || distance >= 2.0F || target.boundingBox.e <= monster.boundingBox.b || target.boundingBox.b >= monster.boundingBox.e) {
            return;
        }

        monster.attackTicks = 20;

        if (target instanceof EntityLiving && !(target instanceof EntityHuman)) {
            com.legacyminecraft.compat.bukkit.entity.Entity damagee = target.getBukkitEntity();
            EntityDamageByEntityEvent event = new EntityDamageByEntityEvent(monster.getBukkitEntity(), damagee, EntityDamageEvent.DamageCause.ENTITY_ATTACK, damage);
            monster.world.getServer().getPluginManager().callEvent(event);
            if (!event.isCancelled()) {
                target.damageEntity(monster, event.getDamage());
            }
            return;
        }

        target.damageEntity(monster, damage);
    }

    public float resolvePathWeight(float lightAtBlock) {
        return 0.5F - lightAtBlock;
    }
}
