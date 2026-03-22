package com.legacyminecraft.poseidon.migration;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LegacyWeatherWrapperThinnessTest {
    private static final Path ENTITY_WEATHER_STORM_PATH = Paths.get("src/main/java/net/minecraft/server/EntityWeatherStorm.java");

    @Test
    public void entityWeatherStormDelegatesLifecycleToCanonicalBehaviour() throws IOException {
        String text = new String(Files.readAllBytes(ENTITY_WEATHER_STORM_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("LightningStormLifecycleBehaviour"));
        Assert.assertTrue(text.contains("LIGHTNING_STORM_LIFECYCLE_BEHAVIOUR.initialize"));
        Assert.assertTrue(text.contains("LIGHTNING_STORM_LIFECYCLE_BEHAVIOUR.tick"));
        Assert.assertTrue(text.contains("LIGHTNING_STORM_LIFECYCLE_BEHAVIOUR.readFromNbt"));
        Assert.assertTrue(text.contains("LIGHTNING_STORM_LIFECYCLE_BEHAVIOUR.writeToNbt"));
        Assert.assertFalse(text.contains("new BlockIgniteEvent"));
        Assert.assertFalse(text.contains("world.makeSound(this.locX, this.locY, this.locZ, \"ambient.weather.thunder\""));
        Assert.assertFalse(text.contains("List list = this.world.b((Entity) this"));
    }
}
