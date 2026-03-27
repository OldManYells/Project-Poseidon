package com.legacyminecraft.poseidon.migration;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LegacyCraftWorldIdentityWrapperThinnessTest {
    private static final Path CRAFT_WORLD_PATH =
            Paths.get("src/main/java/org/bukkit/craftbukkit/CraftWorld.java");
    private static final Path CRAFT_WORLD_IDENTITY_BEHAVIOUR_PATH =
            Paths.get("src/main/java/com/legacyminecraft/poseidon/compat/bukkit/CraftWorldIdentityBehaviour.java");

    @Test
    public void craftWorldDelegatesIdentityAndSeedReadMethodsToCanonicalBehaviour() throws IOException {
        String craftWorldText = read(CRAFT_WORLD_PATH);
        String identityBehaviourText = read(CRAFT_WORLD_IDENTITY_BEHAVIOUR_PATH);

        String getNameSection = section(craftWorldText, "public String getName() {", "@Deprecated");
        String getIdSection = section(craftWorldText, "public long getId() {", "public UUID getUID() {");
        String getUidSection = section(craftWorldText, "public UUID getUID() {", "@Override");
        String toStringSection = section(craftWorldText, "public String toString() {", "public long getTime() {");
        String getSeedSection = section(craftWorldText, "public long getSeed() {", "public boolean getPVP() {");
        String identityNameSection = section(identityBehaviourText, "public String getName(WorldServer worldServer) {", "public UUID getUID(WorldServer worldServer) {");
        String identityUidSection = section(identityBehaviourText, "public UUID getUID(WorldServer worldServer) {", "public long getId(WorldServer worldServer) {");
        String identityIdSection = section(identityBehaviourText, "public long getId(WorldServer worldServer) {", "public long getSeed(WorldServer worldServer) {");
        String identitySeedSection = section(identityBehaviourText, "public long getSeed(WorldServer worldServer) {", "public String toString(WorldServer worldServer) {");

        Assert.assertTrue(craftWorldText.contains("CraftWorldIdentityBehaviour"));
        Assert.assertTrue(craftWorldText.contains("CRAFT_WORLD_IDENTITY_BEHAVIOUR"));
        Assert.assertTrue(craftWorldText.contains("CRAFT_WORLD_IDENTITY_BEHAVIOUR.getName(world)"));
        Assert.assertTrue(craftWorldText.contains("CRAFT_WORLD_IDENTITY_BEHAVIOUR.getId(world)"));
        Assert.assertTrue(craftWorldText.contains("CRAFT_WORLD_IDENTITY_BEHAVIOUR.getUID(world)"));
        Assert.assertTrue(craftWorldText.contains("CRAFT_WORLD_IDENTITY_BEHAVIOUR.toString(world)"));
        Assert.assertTrue(craftWorldText.contains("CRAFT_WORLD_IDENTITY_BEHAVIOUR.getSeed(world)"));

        Assert.assertFalse(getNameSection.contains("world.worldData.name"));
        Assert.assertFalse(getIdSection.contains("world.worldData.getSeed()"));
        Assert.assertFalse(getUidSection.contains("world.getUUID()"));
        Assert.assertFalse(toStringSection.contains("\"CraftWorld{name=\" + getName() + '}'"));
        Assert.assertFalse(getSeedSection.contains("world.worldData.getSeed()"));

        Assert.assertTrue(identityNameSection.contains("return worldServer.worldData.name;"));
        Assert.assertTrue(identityUidSection.contains("return worldServer.getUUID();"));
        Assert.assertTrue(identityIdSection.contains("return worldServer.worldData.getSeed();"));
        Assert.assertTrue(identitySeedSection.contains("return worldServer.worldData.getSeed();"));
        Assert.assertTrue(identityBehaviourText.contains("return \"CraftWorld{name=\" + getName(worldServer) + '}';"));
    }

    private static String read(Path path) throws IOException {
        return new String(Files.readAllBytes(path), StandardCharsets.UTF_8);
    }

    private static String section(String text, String startMarker, String endMarker) {
        int startIndex = text.indexOf(startMarker);
        int endIndex = text.indexOf(endMarker, startIndex + startMarker.length());
        Assert.assertTrue(startIndex >= 0);
        Assert.assertTrue(endIndex >= 0);
        Assert.assertTrue(endIndex > startIndex);
        return text.substring(startIndex, endIndex);
    }
}
