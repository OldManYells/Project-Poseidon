package com.legacyminecraft.poseidon.migration;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LegacyWeatherBaseWrapperThinnessTest {
    private static final Path ENTITY_WEATHER_PATH = Paths.get("src/main/java/net/minecraft/server/EntityWeather.java");

    @Test
    public void entityWeatherDelegatesBaseInitializationToCanonicalBehaviour() throws IOException {
        String text = new String(Files.readAllBytes(ENTITY_WEATHER_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("WeatherEntityBaseBehaviour"));
        Assert.assertTrue(text.contains("WEATHER_ENTITY_BASE_BEHAVIOUR.initialize(this, world)"));
    }
}
