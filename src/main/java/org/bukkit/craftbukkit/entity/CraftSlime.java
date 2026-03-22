package org.bukkit.craftbukkit.entity;

import com.legacyminecraft.poseidon.compat.bukkit.MobPropertyBehaviour;
import net.minecraft.server.EntitySlime;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.entity.Slime;

public class CraftSlime extends CraftLivingEntity implements Slime {
    private static final MobPropertyBehaviour MOB_PROPERTY_BEHAVIOUR = MobPropertyBehaviour.getInstance();

    public CraftSlime(CraftServer server, EntitySlime entity) {
        super(server, entity);
    }

    @Override
    public String toString() {
        return "CraftSlime";
    }

    public EntitySlime getHandle() {
        return (EntitySlime) super.getHandle();
    }

    public int getSize() {
        return MOB_PROPERTY_BEHAVIOUR.getSlimeSize(getHandle());
    }

    public void setSize(int size) {
        MOB_PROPERTY_BEHAVIOUR.setSlimeSize(getHandle(), size);
    }
}
