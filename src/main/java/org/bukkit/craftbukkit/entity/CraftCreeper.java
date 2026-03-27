package org.bukkit.craftbukkit.entity;

import com.legacyminecraft.compat.bukkit.CreeperPowerEventBridgeBehaviour;
import com.legacyminecraft.compat.bukkit.EntityTypedHandleCastBehaviour;
import com.legacyminecraft.compat.bukkit.EntityWrapperStringBehaviour;
import net.minecraft.server.EntityCreeper;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.entity.Creeper;

public class CraftCreeper extends CraftMonster implements Creeper {
    private static final EntityWrapperStringBehaviour ENTITY_WRAPPER_STRING_BEHAVIOUR =
            EntityWrapperStringBehaviour.getInstance();
    private static final EntityTypedHandleCastBehaviour ENTITY_TYPED_HANDLE_CAST_BEHAVIOUR =
            EntityTypedHandleCastBehaviour.getInstance();
    private static final CreeperPowerEventBridgeBehaviour CREEPER_POWER_EVENT_BRIDGE_BEHAVIOUR =
            CreeperPowerEventBridgeBehaviour.getInstance();

    public CraftCreeper(CraftServer server, EntityCreeper entity) {
        super(server, entity);
    }

    @Override
    public EntityCreeper getHandle() {
        return ENTITY_TYPED_HANDLE_CAST_BEHAVIOUR.castHandle(super.getHandle(), EntityCreeper.class);
    }

    @Override
    public String toString() {
        return ENTITY_WRAPPER_STRING_BEHAVIOUR.toString("CraftCreeper");
    }

    public boolean isPowered() {
        return CREEPER_POWER_EVENT_BRIDGE_BEHAVIOUR.isPowered(getHandle());
    }

    public void setPowered(boolean powered) {
        CREEPER_POWER_EVENT_BRIDGE_BEHAVIOUR.setPoweredWithEvent(this.server, getHandle(), powered);
    }

}
