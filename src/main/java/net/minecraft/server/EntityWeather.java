package net.minecraft.server;

import com.legacyminecraft.poseidon.entity.WeatherEntityBaseBehaviour;

public abstract class EntityWeather extends Entity {
    private static final WeatherEntityBaseBehaviour WEATHER_ENTITY_BASE_BEHAVIOUR = WeatherEntityBaseBehaviour.getInstance();

    public EntityWeather(World world) {
        super(world);
        WEATHER_ENTITY_BASE_BEHAVIOUR.initialize(
                (com.legacyminecraft.poseidon.entity.EntityWeather) (Object) this,
                (com.legacyminecraft.poseidon.entity.World) (Object) world
        );
    }
}
