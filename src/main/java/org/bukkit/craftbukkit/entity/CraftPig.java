package org.bukkit.craftbukkit.entity;

import com.legacyminecraft.poseidon.compat.bukkit.MobPropertyBehaviour;
import net.minecraft.server.EntityPig;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.entity.Pig;

public class CraftPig extends CraftAnimals implements Pig {
    private static final MobPropertyBehaviour MOB_PROPERTY_BEHAVIOUR = MobPropertyBehaviour.getInstance();

    public CraftPig(CraftServer server, EntityPig entity) {
        super(server, entity);
    }

    public boolean hasSaddle() {
        return MOB_PROPERTY_BEHAVIOUR.hasPigSaddle(getHandle());
    }

    public void setSaddle(boolean saddled) {
        MOB_PROPERTY_BEHAVIOUR.setPigSaddle(getHandle(), saddled);
    }

    public EntityPig getHandle() {
        return (EntityPig) super.getHandle();
    }

    @Override
    public String toString() {
        return "CraftPig";
    }
}
