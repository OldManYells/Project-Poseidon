package com.legacyminecraft.poseidon.migration;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LegacyWorldMapWrapperThinnessTest {
    private static final Path WORLD_MAP_PATH = Paths.get("src/main/java/net/minecraft/server/WorldMap.java");
    private static final Path WORLD_MAP_BASE_PATH = Paths.get("src/main/java/net/minecraft/server/WorldMapBase.java");
    private static final Path WORLD_MAP_HUMAN_TRACKER_PATH = Paths.get("src/main/java/net/minecraft/server/WorldMapHumanTracker.java");
    private static final Path WORLD_MAP_ORIENTER_PATH = Paths.get("src/main/java/net/minecraft/server/WorldMapOrienter.java");

    @Test
    public void worldMapDelegatesNbtPersistenceToCanonicalBehaviour() throws IOException {
        String text = new String(Files.readAllBytes(WORLD_MAP_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("WorldMapPersistenceBehaviour"));
        Assert.assertTrue(text.contains("WORLD_MAP_PERSISTENCE_BEHAVIOUR.loadFromNbt"));
        Assert.assertTrue(text.contains("WORLD_MAP_PERSISTENCE_BEHAVIOUR.writeToNbt"));
        Assert.assertTrue(text.contains("WorldMapTrackingBehaviour"));
        Assert.assertTrue(text.contains("WORLD_MAP_TRACKING_BEHAVIOUR.updateTrackers"));
        Assert.assertTrue(text.contains("WORLD_MAP_TRACKING_BEHAVIOUR.createUpdatePacket"));
        Assert.assertTrue(text.contains("WORLD_MAP_TRACKING_BEHAVIOUR.markDirty"));
        Assert.assertFalse(text.contains("byte dimension = nbttagcompound.c(\"dimension\")"));
        Assert.assertFalse(text.contains("nbttagcompound.a(\"width\", (short) 128)"));
        Assert.assertFalse(text.contains("this.i.add(new WorldMapOrienter"));
        Assert.assertFalse(text.contains("WorldMapHumanTracker worldmaphumantracker = (WorldMapHumanTracker) this.j.get(entityhuman)"));
    }

    @Test
    public void worldMapBaseDelegatesDirtyFlagPolicyToCanonicalBehaviour() throws IOException {
        String text = new String(Files.readAllBytes(WORLD_MAP_BASE_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("WorldMapDirtyFlagBehaviour"));
        Assert.assertTrue(text.contains("WORLD_MAP_DIRTY_FLAG_BEHAVIOUR.markDirty"));
        Assert.assertTrue(text.contains("WORLD_MAP_DIRTY_FLAG_BEHAVIOUR.setDirty"));
        Assert.assertTrue(text.contains("WORLD_MAP_DIRTY_FLAG_BEHAVIOUR.isDirty"));
        Assert.assertFalse(text.contains("this.b = flag"));
        Assert.assertFalse(text.contains("return this.b"));
    }

    @Test
    public void worldMapHumanTrackerDelegatesRenderPacketAssemblyToCanonicalBehaviour() throws IOException {
        String text = new String(Files.readAllBytes(WORLD_MAP_HUMAN_TRACKER_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("WorldMapHumanTrackerBehaviour"));
        Assert.assertTrue(text.contains("WORLD_MAP_HUMAN_TRACKER_BEHAVIOUR.initializeBounds"));
        Assert.assertTrue(text.contains("WORLD_MAP_HUMAN_TRACKER_BEHAVIOUR.createUpdate"));
        Assert.assertFalse(text.contains("RenderData render = this.d.mapView.render"));
        Assert.assertFalse(text.contains("MapCursor cursor = render.cursors.get"));
        Assert.assertFalse(text.contains("this.e * 11 % 128"));
    }

    @Test
    public void worldMapOrienterDelegatesOrientationStateInitializationToCanonicalBehaviour() throws IOException {
        String text = new String(Files.readAllBytes(WORLD_MAP_ORIENTER_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("WorldMapOrienterBehaviour"));
        Assert.assertTrue(text.contains("WORLD_MAP_ORIENTER_BEHAVIOUR.initialize"));
        Assert.assertFalse(text.contains("this.e = worldmap"));
        Assert.assertFalse(text.contains("this.a = b0"));
    }
}
