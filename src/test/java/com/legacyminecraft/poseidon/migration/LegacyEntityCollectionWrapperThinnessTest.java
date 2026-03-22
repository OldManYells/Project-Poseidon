package com.legacyminecraft.poseidon.migration;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LegacyEntityCollectionWrapperThinnessTest {
    private static final Path PLAYER_LIST_PATH = Paths.get("src/main/java/net/minecraft/server/PlayerList.java");
    private static final Path PLAYER_LIST_ENTRY_PATH = Paths.get("src/main/java/net/minecraft/server/PlayerListEntry.java");
    private static final Path ENTITY_TYPES_PATH = Paths.get("src/main/java/net/minecraft/server/EntityTypes.java");
    private static final Path ENTITY_LIST_ENTRY_PATH = Paths.get("src/main/java/net/minecraft/server/EntityListEntry.java");

    @Test
    public void playerListDelegatesHashMapStyleStorageToCanonicalBehaviour() throws IOException {
        String text = new String(Files.readAllBytes(PLAYER_LIST_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("PlayerListBehaviour"));
        Assert.assertTrue(text.contains("PLAYER_LIST_BEHAVIOUR.get"));
        Assert.assertTrue(text.contains("PLAYER_LIST_BEHAVIOUR.put"));
        Assert.assertTrue(text.contains("PLAYER_LIST_BEHAVIOUR.remove"));
        Assert.assertFalse(text.contains("new PlayerListEntry(i, j, object, playerlistentry)"));
        Assert.assertFalse(text.contains("if (this.b++ >= this.c)"));
        Assert.assertFalse(text.contains("playerlistentry1.a == i"));
    }

    @Test
    public void playerListEntryExposesAccessorsForCanonicalBehaviour() throws IOException {
        String text = new String(Files.readAllBytes(PLAYER_LIST_ENTRY_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("public class PlayerListEntry"));
        Assert.assertTrue(text.contains("public final int c()"));
        Assert.assertTrue(text.contains("public final PlayerListEntry d()"));
        Assert.assertTrue(text.contains("public final void a(Object object)"));
        Assert.assertTrue(text.contains("public final void a(PlayerListEntry playerlistentry)"));
    }

    @Test
    public void entityTypesDelegatesRegistryLifecycleToCanonicalBehaviour() throws IOException {
        String text = new String(Files.readAllBytes(ENTITY_TYPES_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("EntityTypeRegistryBehaviour"));
        Assert.assertTrue(text.contains("ENTITY_TYPE_REGISTRY_BEHAVIOUR.register"));
        Assert.assertTrue(text.contains("ENTITY_TYPE_REGISTRY_BEHAVIOUR.createByName"));
        Assert.assertTrue(text.contains("ENTITY_TYPE_REGISTRY_BEHAVIOUR.createFromNbt"));
        Assert.assertTrue(text.contains("ENTITY_TYPE_REGISTRY_BEHAVIOUR.bootstrapDefaultRegistrations"));
        Assert.assertFalse(text.contains("oclass.getConstructor(new Class[] { World.class}).newInstance"));
        Assert.assertFalse(text.contains("System.out.println(\"Skipping Entity with id \""));
        Assert.assertFalse(text.contains("a(EntityArrow.class, \"Arrow\", 10)"));
    }

    @Test
    public void entityListEntryDelegatesEqualityAndHashPoliciesToCanonicalBehaviour() throws IOException {
        String text = new String(Files.readAllBytes(ENTITY_LIST_ENTRY_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("EntityListEntryBehaviour"));
        Assert.assertTrue(text.contains("ENTITY_LIST_ENTRY_BEHAVIOUR.equalsEntry"));
        Assert.assertTrue(text.contains("ENTITY_LIST_ENTRY_BEHAVIOUR.hashCode"));
        Assert.assertTrue(text.contains("ENTITY_LIST_ENTRY_BEHAVIOUR.stringify"));
        Assert.assertTrue(text.contains("poseidonGetSlot"));
        Assert.assertFalse(text.contains("if (!(object instanceof EntityListEntry))"));
        Assert.assertFalse(text.contains("return this.a() + \"=\" + this.b()"));
    }
}
