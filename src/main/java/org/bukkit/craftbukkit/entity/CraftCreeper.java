package org.bukkit.craftbukkit.entity;

import com.legacyminecraft.poseidon.compat.bukkit.CreeperPowerEventBridgeBehaviour;
import net.minecraft.server.EntityCreeper;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.entity.Creeper;

public class CraftCreeper extends CraftMonster implements Creeper {
    private static final CreeperPowerEventBridgeBehaviour CREEPER_POWER_EVENT_BRIDGE_BEHAVIOUR =
            CreeperPowerEventBridgeBehaviour.getInstance();

    public CraftCreeper(CraftServer server, EntityCreeper entity) {
        super(server, entity);
    }

    @Override
    public EntityCreeper getHandle() {
        return (EntityCreeper) super.getHandle();
    }

    @Override
    public String toString() {
        return "CraftCreeper";
    }

    public boolean isPowered() {
        return getHandle().isPowered();
    }

    public void setPowered(boolean powered) {
        CREEPER_POWER_EVENT_BRIDGE_BEHAVIOUR.setPoweredWithEvent(this.server, getHandle(), powered);
    }

}
