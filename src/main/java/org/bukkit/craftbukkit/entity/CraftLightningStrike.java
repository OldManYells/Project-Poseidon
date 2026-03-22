package org.bukkit.craftbukkit.entity;

import com.legacyminecraft.poseidon.compat.bukkit.LightningStrikePropertyBehaviour;
import net.minecraft.server.EntityWeatherStorm;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.entity.LightningStrike;

public class CraftLightningStrike extends CraftEntity implements LightningStrike {
    private static final LightningStrikePropertyBehaviour LIGHTNING_STRIKE_PROPERTY_BEHAVIOUR =
            LightningStrikePropertyBehaviour.getInstance();

    public CraftLightningStrike(final CraftServer server, final EntityWeatherStorm entity) {
        super(server, entity);
    }

    @Override
    public EntityWeatherStorm getHandle() {
        return (EntityWeatherStorm) super.getHandle();
    }

    public boolean isEffect() {
        return LIGHTNING_STRIKE_PROPERTY_BEHAVIOUR.isEffect(getHandle());
    }
}
