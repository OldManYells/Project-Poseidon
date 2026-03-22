package com.legacyminecraft.poseidon.migration;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LegacyTileEntityWrapperThinnessTest {
    private static final Path TILE_ENTITY_PATH = Paths.get("src/main/java/net/minecraft/server/TileEntity.java");
    private static final Path TILE_ENTITY_SIGN_PATH = Paths.get("src/main/java/net/minecraft/server/TileEntitySign.java");
    private static final Path TILE_ENTITY_CHEST_PATH = Paths.get("src/main/java/net/minecraft/server/TileEntityChest.java");
    private static final Path TILE_ENTITY_NOTE_PATH = Paths.get("src/main/java/net/minecraft/server/TileEntityNote.java");
    private static final Path TILE_ENTITY_RECORD_PLAYER_PATH = Paths.get("src/main/java/net/minecraft/server/TileEntityRecordPlayer.java");

    @Test
    public void tileEntityNoteAndRecordPlayerDelegateNbtAndStatePoliciesToCanonicalBehaviours() throws IOException {
        String tileEntity = new String(Files.readAllBytes(TILE_ENTITY_PATH), StandardCharsets.UTF_8);
        String sign = new String(Files.readAllBytes(TILE_ENTITY_SIGN_PATH), StandardCharsets.UTF_8);
        String chest = new String(Files.readAllBytes(TILE_ENTITY_CHEST_PATH), StandardCharsets.UTF_8);
        String note = new String(Files.readAllBytes(TILE_ENTITY_NOTE_PATH), StandardCharsets.UTF_8);
        String recordPlayer = new String(Files.readAllBytes(TILE_ENTITY_RECORD_PLAYER_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(tileEntity.contains("TileEntityRegistryBehaviour"));
        Assert.assertTrue(tileEntity.contains("TILE_ENTITY_REGISTRY_BEHAVIOUR.register"));
        Assert.assertTrue(tileEntity.contains("TILE_ENTITY_REGISTRY_BEHAVIOUR.writeBaseData"));
        Assert.assertTrue(tileEntity.contains("TILE_ENTITY_REGISTRY_BEHAVIOUR.createFromTag"));
        Assert.assertTrue(tileEntity.contains("TILE_ENTITY_REGISTRY_BEHAVIOUR.notifyUpdated"));
        Assert.assertFalse(tileEntity.contains("Class oclass = (Class) a.get(nbttagcompound.getString(\"id\"))"));
        Assert.assertFalse(tileEntity.contains("this.world.b(this.x, this.y, this.z, this)"));

        Assert.assertTrue(sign.contains("SignTileBehaviour"));
        Assert.assertTrue(sign.contains("SIGN_TILE_BEHAVIOUR.writeLines"));
        Assert.assertTrue(sign.contains("SIGN_TILE_BEHAVIOUR.readLines"));
        Assert.assertTrue(sign.contains("SIGN_TILE_BEHAVIOUR.createUpdatePacket"));
        Assert.assertFalse(sign.contains("nbttagcompound.setString(\"Text1\""));
        Assert.assertFalse(sign.contains("return new Packet130UpdateSign"));

        Assert.assertTrue(chest.contains("ChestTileInventoryBehaviour"));
        Assert.assertTrue(chest.contains("CHEST_TILE_INVENTORY_BEHAVIOUR.createStorage"));
        Assert.assertTrue(chest.contains("CHEST_TILE_INVENTORY_BEHAVIOUR.splitStack"));
        Assert.assertTrue(chest.contains("CHEST_TILE_INVENTORY_BEHAVIOUR.writeItems"));
        Assert.assertFalse(chest.contains("this.items = new ItemStack[this.getSize()]"));
        Assert.assertFalse(chest.contains("nbttagcompound.a(\"Items\", (NBTBase) nbttaglist)"));

        Assert.assertTrue(note.contains("NoteBlockTileBehaviour"));
        Assert.assertTrue(note.contains("NOTE_BLOCK_TILE_BEHAVIOUR.readNote"));
        Assert.assertTrue(note.contains("NOTE_BLOCK_TILE_BEHAVIOUR.incrementNote"));
        Assert.assertTrue(note.contains("NOTE_BLOCK_TILE_BEHAVIOUR.resolveInstrument"));
        Assert.assertFalse(note.contains("if (this.note > 24)"));
        Assert.assertFalse(note.contains("if (material == Material.STONE)"));

        Assert.assertTrue(recordPlayer.contains("JukeboxTileBehaviour"));
        Assert.assertTrue(recordPlayer.contains("JUKEBOX_TILE_BEHAVIOUR.readRecordId"));
        Assert.assertTrue(recordPlayer.contains("JUKEBOX_TILE_BEHAVIOUR.writeRecordIdIfPresent"));
        Assert.assertFalse(recordPlayer.contains("this.a = nbttagcompound.e(\"Record\")"));
        Assert.assertFalse(recordPlayer.contains("if (this.a > 0)"));
    }
}
