package com.legacyminecraft.poseidon.migration;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LegacyCraftTileBlockWrapperThinnessTest {
    private static final Path CRAFT_CHEST_PATH =
            Paths.get("src/main/java/org/bukkit/craftbukkit/block/CraftChest.java");
    private static final Path CRAFT_DISPENSER_PATH =
            Paths.get("src/main/java/org/bukkit/craftbukkit/block/CraftDispenser.java");
    private static final Path CRAFT_CREATURE_SPAWNER_PATH =
            Paths.get("src/main/java/org/bukkit/craftbukkit/block/CraftCreatureSpawner.java");
    private static final Path CRAFT_NOTE_BLOCK_PATH =
            Paths.get("src/main/java/org/bukkit/craftbukkit/block/CraftNoteBlock.java");
    private static final Path CRAFT_SIGN_PATH =
            Paths.get("src/main/java/org/bukkit/craftbukkit/block/CraftSign.java");

    @Test
    public void craftChestDelegatesInventoryAndUpdateToCanonicalBehaviour() throws IOException {
        String text = new String(Files.readAllBytes(CRAFT_CHEST_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("TileEntityInventoryBridgeBehaviour"));
        Assert.assertTrue(text.contains("TileEntityBlockStateUpdateBehaviour"));
        Assert.assertTrue(text.contains("createInventory(chest)"));
        Assert.assertTrue(text.contains("finalizeUpdate(super.update(force), chest)"));
        Assert.assertFalse(text.contains("return new CraftInventory(chest);"));
        Assert.assertFalse(text.contains("chest.update();"));
    }

    @Test
    public void craftDispenserDelegatesActivationInventoryAndUpdateToCanonicalBehaviour() throws IOException {
        String text = new String(Files.readAllBytes(CRAFT_DISPENSER_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("DispenserActivationBehaviour"));
        Assert.assertTrue(text.contains("TileEntityInventoryBridgeBehaviour"));
        Assert.assertTrue(text.contains("TileEntityBlockStateUpdateBehaviour"));
        Assert.assertTrue(text.contains("tryDispense(getBlock(), world.getHandle(), getX(), getY(), getZ())"));
        Assert.assertTrue(text.contains("createInventory(dispenser)"));
        Assert.assertTrue(text.contains("finalizeUpdate(super.update(force), dispenser)"));
        Assert.assertFalse(text.contains("BlockDispenser dispense ="));
        Assert.assertFalse(text.contains("new Random()"));
        Assert.assertFalse(text.contains("synchronized (block)"));
    }

    @Test
    public void craftSignDelegatesUpdateToCanonicalBehaviour() throws IOException {
        String text = new String(Files.readAllBytes(CRAFT_SIGN_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("TileEntityBlockStateUpdateBehaviour"));
        Assert.assertTrue(text.contains("finalizeUpdate(super.update(force), sign)"));
        Assert.assertFalse(text.contains("if (result) {"));
        Assert.assertFalse(text.contains("sign.update();"));
    }

    @Test
    public void craftCreatureSpawnerDelegatesStatePolicyToCanonicalBehaviour() throws IOException {
        String text = new String(Files.readAllBytes(CRAFT_CREATURE_SPAWNER_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("SpawnerStateBehaviour"));
        Assert.assertTrue(text.contains("SPAWNER_STATE_BEHAVIOUR"));
        Assert.assertTrue(text.contains("setCreatureTypeId(spawner, creatureType)"));
        Assert.assertTrue(text.contains("setDelay(spawner, delay)"));
        Assert.assertFalse(text.contains("CreatureType type = CreatureType.fromName(creatureType);"));
        Assert.assertFalse(text.contains("spawner.mobName = type.getName();"));
    }

    @Test
    public void craftNoteBlockDelegatesPlaybackPolicyToCanonicalBehaviour() throws IOException {
        String text = new String(Files.readAllBytes(CRAFT_NOTE_BLOCK_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("NoteBlockPlaybackBehaviour"));
        Assert.assertTrue(text.contains("NOTE_BLOCK_PLAYBACK_BEHAVIOUR"));
        Assert.assertTrue(text.contains("playStoredNote(getBlock(), note, world.getHandle(), getX(), getY(), getZ())"));
        Assert.assertTrue(text.contains("playRawNote(getBlock(), world.getHandle(), getX(), getY(), getZ(), instrument, note)"));
        Assert.assertFalse(text.contains("note.play(world.getHandle(), getX(), getY(), getZ());"));
        Assert.assertFalse(text.contains("synchronized (block)"));
        Assert.assertFalse(text.contains("if (block.getType() == Material.NOTE_BLOCK)"));
    }
}
