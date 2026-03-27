
package org.bukkit.craftbukkit.entity;

import com.legacyminecraft.compat.bukkit.EntityWeatherHandleBehaviour;
import net.minecraft.server.EntityWeather;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.entity.Weather;

public class CraftWeather extends CraftEntity implements Weather {
    private static final EntityWeatherHandleBehaviour ENTITY_WEATHER_HANDLE_BEHAVIOUR =
            EntityWeatherHandleBehaviour.getInstance();

    public CraftWeather(final CraftServer server, final EntityWeather entity) {
        super(server, entity);
    }

    @Override
    public EntityWeather getHandle() {
        return ENTITY_WEATHER_HANDLE_BEHAVIOUR.resolveHandle(super.getHandle());
    }
}
