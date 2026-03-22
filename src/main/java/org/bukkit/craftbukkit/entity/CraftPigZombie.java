package org.bukkit.craftbukkit.entity;

import com.legacyminecraft.poseidon.compat.bukkit.MobPropertyBehaviour;
import net.minecraft.server.EntityPigZombie;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.entity.PigZombie;

public class CraftPigZombie extends CraftZombie implements PigZombie {
    private static final MobPropertyBehaviour MOB_PROPERTY_BEHAVIOUR = MobPropertyBehaviour.getInstance();

    public CraftPigZombie(CraftServer server, EntityPigZombie entity) {
        super(server, entity);
    }

    @Override
    public EntityPigZombie getHandle() {
        return (EntityPigZombie) super.getHandle();
    }

    @Override
    public String toString() {
        return "CraftPigZombie";
    }

    public int getAnger() {
        return MOB_PROPERTY_BEHAVIOUR.getPigZombieAnger(getHandle());
    }

    public void setAnger(int level) {
        MOB_PROPERTY_BEHAVIOUR.setPigZombieAnger(getHandle(), level);
    }

    public void setAngry(boolean angry) {
        MOB_PROPERTY_BEHAVIOUR.setPigZombieAngry(getHandle(), angry);
    }

    public boolean isAngry() {
        return MOB_PROPERTY_BEHAVIOUR.isPigZombieAngry(getHandle());
    }

}
