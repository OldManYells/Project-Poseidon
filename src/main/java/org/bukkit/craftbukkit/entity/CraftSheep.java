package org.bukkit.craftbukkit.entity;

import com.legacyminecraft.compat.bukkit.EntityWrapperStringBehaviour;
import com.legacyminecraft.compat.bukkit.EntityTypedHandleCastBehaviour;
import com.legacyminecraft.compat.bukkit.MobPropertyBehaviour;
import net.minecraft.server.EntitySheep;
import org.bukkit.DyeColor;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.entity.Sheep;

public class CraftSheep extends CraftAnimals implements Sheep {
    private static final EntityWrapperStringBehaviour ENTITY_WRAPPER_STRING_BEHAVIOUR =
            EntityWrapperStringBehaviour.getInstance();
    private static final EntityTypedHandleCastBehaviour ENTITY_TYPED_HANDLE_CAST_BEHAVIOUR =
            EntityTypedHandleCastBehaviour.getInstance();
    private static final MobPropertyBehaviour MOB_PROPERTY_BEHAVIOUR = MobPropertyBehaviour.getInstance();

    public CraftSheep(CraftServer server, EntitySheep entity) {
        super(server, entity);
    }

    @Override
    public EntitySheep getHandle() {
        return ENTITY_TYPED_HANDLE_CAST_BEHAVIOUR.castHandle(entity, EntitySheep.class);
    }

    @Override
    public String toString() {
        return ENTITY_WRAPPER_STRING_BEHAVIOUR.toString("CraftSheep");
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
