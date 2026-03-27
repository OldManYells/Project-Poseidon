package org.bukkit.craftbukkit.entity;

import com.legacyminecraft.compat.bukkit.LightningStrikePropertyBehaviour;
import com.legacyminecraft.compat.bukkit.EntityTypedHandleCastBehaviour;
import net.minecraft.server.EntityWeatherStorm;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.entity.LightningStrike;

public class CraftLightningStrike extends CraftEntity implements LightningStrike {
    private static final LightningStrikePropertyBehaviour LIGHTNING_STRIKE_PROPERTY_BEHAVIOUR =
            LightningStrikePropertyBehaviour.getInstance();
    private static final EntityTypedHandleCastBehaviour ENTITY_TYPED_HANDLE_CAST_BEHAVIOUR =
            EntityTypedHandleCastBehaviour.getInstance();

    public CraftLightningStrike(final CraftServer server, final EntityWeatherStorm entity) {
        super(server, entity);
    }

    @Override
    public EntityWeatherStorm getHandle() {
        return ENTITY_TYPED_HANDLE_CAST_BEHAVIOUR.castHandle(super.getHandle(), EntityWeatherStorm.class);
    }

    public boolean isEffect() {
        return LIGHTNING_STRIKE_PROPERTY_BEHAVIOUR.isEffect(getHandle());
    }
}
