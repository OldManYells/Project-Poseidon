package org.bukkit.craftbukkit.entity;

import com.legacyminecraft.poseidon.compat.bukkit.MobPropertyBehaviour;
import net.minecraft.server.EntitySheep;
import org.bukkit.DyeColor;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.entity.Sheep;

public class CraftSheep extends CraftAnimals implements Sheep {
    private static final MobPropertyBehaviour MOB_PROPERTY_BEHAVIOUR = MobPropertyBehaviour.getInstance();

    public CraftSheep(CraftServer server, EntitySheep entity) {
        super(server, entity);
    }

    @Override
    public EntitySheep getHandle() {
        return (EntitySheep) entity;
    }

    @Override
    public String toString() {
        return "CraftSheep";
    }

    public DyeColor getColor() {
        return MOB_PROPERTY_BEHAVIOUR.getSheepColor(getHandle());
    }

    public void setColor(DyeColor color) {
        MOB_PROPERTY_BEHAVIOUR.setSheepColor(getHandle(), color);
    }

    public boolean isSheared() {
        return MOB_PROPERTY_BEHAVIOUR.isSheepSheared(getHandle());
    }

    public void setSheared(boolean flag) {
        MOB_PROPERTY_BEHAVIOUR.setSheepSheared(getHandle(), flag);
    }

}
