package com.legacyminecraft.poseidon.migration;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LegacyInterfaceBoundaryTest {
    private static final Path CRAFTING_RECIPE_PATH = Paths.get("src/main/java/net/minecraft/server/CraftingRecipe.java");
    private static final Path COUNTER_PATH = Paths.get("src/main/java/net/minecraft/server/Counter.java");
    private static final Path COMMAND_LISTENER_PATH = Paths.get("src/main/java/net/minecraft/server/ICommandListener.java");
    private static final Path INVENTORY_PATH = Paths.get("src/main/java/net/minecraft/server/IInventory.java");
    private static final Path CRAFTING_LISTENER_PATH = Paths.get("src/main/java/net/minecraft/server/ICrafting.java");
    private static final Path BLOCK_ACCESS_PATH = Paths.get("src/main/java/net/minecraft/server/IBlockAccess.java");
    private static final Path PROGRESS_UPDATE_PATH = Paths.get("src/main/java/net/minecraft/server/IProgressUpdate.java");
    private static final Path WORLD_ACCESS_PATH = Paths.get("src/main/java/net/minecraft/server/IWorldAccess.java");
    private static final Path UPDATE_PLAYER_LIST_BOX_PATH = Paths.get("src/main/java/net/minecraft/server/IUpdatePlayerListBox.java");
    private static final Path ANIMAL_PATH = Paths.get("src/main/java/net/minecraft/server/IAnimal.java");
    private static final Path MONSTER_PATH = Paths.get("src/main/java/net/minecraft/server/IMonster.java");
    private static final Path CONVERTABLE_PATH = Paths.get("src/main/java/net/minecraft/server/Convertable.java");
    private static final Path DATA_MANAGER_PATH = Paths.get("src/main/java/net/minecraft/server/IDataManager.java");
    private static final Path CHUNK_LOADER_PATH = Paths.get("src/main/java/net/minecraft/server/IChunkLoader.java");
    private static final Path CHUNK_PROVIDER_PATH = Paths.get("src/main/java/net/minecraft/server/IChunkProvider.java");
    private static final Path PLAYER_FILE_DATA_PATH = Paths.get("src/main/java/net/minecraft/server/PlayerFileData.java");

    @Test
    public void legacyInterfacesExtendCanonicalContracts() throws IOException {
        String craftingRecipeText = new String(Files.readAllBytes(CRAFTING_RECIPE_PATH), StandardCharsets.UTF_8);
        String counterText = new String(Files.readAllBytes(COUNTER_PATH), StandardCharsets.UTF_8);
        String commandListenerText = new String(Files.readAllBytes(COMMAND_LISTENER_PATH), StandardCharsets.UTF_8);
        String inventoryText = new String(Files.readAllBytes(INVENTORY_PATH), StandardCharsets.UTF_8);
        String craftingListenerText = new String(Files.readAllBytes(CRAFTING_LISTENER_PATH), StandardCharsets.UTF_8);
        String blockAccessText = new String(Files.readAllBytes(BLOCK_ACCESS_PATH), StandardCharsets.UTF_8);
        String progressUpdateText = new String(Files.readAllBytes(PROGRESS_UPDATE_PATH), StandardCharsets.UTF_8);
        String worldAccessText = new String(Files.readAllBytes(WORLD_ACCESS_PATH), StandardCharsets.UTF_8);
        String updatePlayerListBoxText = new String(Files.readAllBytes(UPDATE_PLAYER_LIST_BOX_PATH), StandardCharsets.UTF_8);
        String animalText = new String(Files.readAllBytes(ANIMAL_PATH), StandardCharsets.UTF_8);
        String monsterText = new String(Files.readAllBytes(MONSTER_PATH), StandardCharsets.UTF_8);
        String convertableText = new String(Files.readAllBytes(CONVERTABLE_PATH), StandardCharsets.UTF_8);
        String dataManagerText = new String(Files.readAllBytes(DATA_MANAGER_PATH), StandardCharsets.UTF_8);
        String chunkLoaderText = new String(Files.readAllBytes(CHUNK_LOADER_PATH), StandardCharsets.UTF_8);
        String chunkProviderText = new String(Files.readAllBytes(CHUNK_PROVIDER_PATH), StandardCharsets.UTF_8);
        String playerFileDataText = new String(Files.readAllBytes(PLAYER_FILE_DATA_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(craftingRecipeText.contains("import com.legacyminecraft.poseidon.inventory.recipe.CraftingRecipeContract;"));
        Assert.assertTrue(craftingRecipeText.contains("public interface CraftingRecipe extends CraftingRecipeContract"));
        Assert.assertTrue(craftingRecipeText.contains("default boolean matches"));
        Assert.assertTrue(craftingRecipeText.contains("default ItemStack craft"));
        Assert.assertTrue(craftingRecipeText.contains("default int ingredientCount()"));
        Assert.assertTrue(craftingRecipeText.contains("default ItemStack result()"));

        Assert.assertTrue(counterText.contains("import com.legacyminecraft.poseidon.world.stats.CounterContract;"));
        Assert.assertTrue(counterText.contains("public interface Counter extends CounterContract"));

        Assert.assertTrue(commandListenerText.contains("import com.legacyminecraft.poseidon.commands.CommandListenerContract;"));
        Assert.assertTrue(commandListenerText.contains("public interface ICommandListener extends CommandListenerContract"));

        Assert.assertTrue(inventoryText.contains("import com.legacyminecraft.poseidon.inventory.InventoryContract;"));
        Assert.assertTrue(inventoryText.contains("public interface IInventory extends InventoryContract"));
        Assert.assertTrue(inventoryText.contains("default boolean canPlayerUse(EntityHuman player)"));
        Assert.assertTrue(inventoryText.contains("return this.a_(player);"));

        Assert.assertTrue(craftingListenerText.contains("import com.legacyminecraft.poseidon.inventory.ContainerCraftingListenerContract;"));
        Assert.assertTrue(craftingListenerText.contains("public interface ICrafting extends ContainerCraftingListenerContract"));
        Assert.assertTrue(craftingListenerText.contains("default void onContainerInitialized(Container container, List items)"));
        Assert.assertTrue(craftingListenerText.contains("default void onContainerSlotChanged(Container container, int slotIndex, ItemStack stack)"));
        Assert.assertTrue(craftingListenerText.contains("default void onContainerProgressChanged(Container container, int propertyIndex, int propertyValue)"));

        Assert.assertTrue(blockAccessText.contains("import com.legacyminecraft.poseidon.world.BlockAccessContract;"));
        Assert.assertTrue(blockAccessText.contains("public interface IBlockAccess extends BlockAccessContract"));
        Assert.assertTrue(blockAccessText.contains("default boolean isOpaqueCube(int x, int y, int z)"));
        Assert.assertTrue(blockAccessText.contains("return this.e(x, y, z);"));

        Assert.assertTrue(progressUpdateText.contains("import com.legacyminecraft.poseidon.world.ProgressUpdateContract;"));
        Assert.assertTrue(progressUpdateText.contains("public interface IProgressUpdate extends ProgressUpdateContract"));
        Assert.assertTrue(progressUpdateText.contains("default void setPrimaryMessage(String message)"));
        Assert.assertTrue(progressUpdateText.contains("default void setSecondaryMessage(String message)"));
        Assert.assertTrue(progressUpdateText.contains("default void setProgress(int progressPercent)"));

        Assert.assertTrue(worldAccessText.contains("import com.legacyminecraft.poseidon.world.WorldAccessContract;"));
        Assert.assertTrue(worldAccessText.contains("public interface IWorldAccess extends WorldAccessContract"));
        Assert.assertTrue(worldAccessText.contains("default void onBlockChanged(int x, int y, int z)"));
        Assert.assertTrue(worldAccessText.contains("default void onBlockRangeChanged(int minX, int minY, int minZ, int maxX, int maxY, int maxZ)"));
        Assert.assertTrue(worldAccessText.contains("default void playSound(String soundName, double x, double y, double z, float volume, float pitch)"));
        Assert.assertTrue(worldAccessText.contains("default void spawnParticle(String particleName, double x, double y, double z, double motionX, double motionY, double motionZ)"));
        Assert.assertTrue(worldAccessText.contains("default void onEntityAdded(Entity entity)"));
        Assert.assertTrue(worldAccessText.contains("default void onEntityRemoved(Entity entity)"));
        Assert.assertTrue(worldAccessText.contains("default void flush()"));
        Assert.assertTrue(worldAccessText.contains("default void playRecord(String recordName, int x, int y, int z)"));
        Assert.assertTrue(worldAccessText.contains("default void onTileEntityChanged(int x, int y, int z, TileEntity tileEntity)"));
        Assert.assertTrue(worldAccessText.contains("default void playAuxSfx(EntityHuman player, int effectId, int x, int y, int z, int data)"));

        Assert.assertTrue(updatePlayerListBoxText.contains("import com.legacyminecraft.poseidon.runtime.PlayerListTickContract;"));
        Assert.assertTrue(updatePlayerListBoxText.contains("public interface IUpdatePlayerListBox extends PlayerListTickContract"));
        Assert.assertTrue(updatePlayerListBoxText.contains("default void tick()"));
        Assert.assertTrue(updatePlayerListBoxText.contains("this.a();"));

        Assert.assertTrue(animalText.contains("import com.legacyminecraft.poseidon.entity.AnimalMarkerContract;"));
        Assert.assertTrue(animalText.contains("public interface IAnimal extends AnimalMarkerContract"));

        Assert.assertTrue(monsterText.contains("import com.legacyminecraft.poseidon.entity.MonsterMarkerContract;"));
        Assert.assertTrue(monsterText.contains("public interface IMonster extends IAnimal, MonsterMarkerContract"));

        Assert.assertTrue(convertableText.contains("import com.legacyminecraft.poseidon.world.ConvertableContract;"));
        Assert.assertTrue(convertableText.contains("public interface Convertable extends ConvertableContract"));
        Assert.assertTrue(convertableText.contains("default boolean canConvertWorld(String worldName)"));
        Assert.assertTrue(convertableText.contains("default boolean convertWorld(String worldName, IProgressUpdate progressUpdate)"));

        Assert.assertTrue(dataManagerText.contains("import com.legacyminecraft.poseidon.world.DataManagerContract;"));
        Assert.assertTrue(dataManagerText.contains("public interface IDataManager extends DataManagerContract"));
        Assert.assertTrue(dataManagerText.contains("default WorldData loadWorldData()"));
        Assert.assertTrue(dataManagerText.contains("default void verifySessionLock()"));
        Assert.assertTrue(dataManagerText.contains("default IChunkLoader getChunkLoader(WorldProvider worldProvider)"));
        Assert.assertTrue(dataManagerText.contains("default void saveWorldDataWithPlayers(WorldData worldData, List playerData)"));
        Assert.assertTrue(dataManagerText.contains("default void saveWorldData(WorldData worldData)"));
        Assert.assertTrue(dataManagerText.contains("default PlayerFileData getPlayerFileData()"));
        Assert.assertTrue(dataManagerText.contains("default UUID getWorldUuid()"));

        Assert.assertTrue(chunkLoaderText.contains("import com.legacyminecraft.poseidon.world.chunk.ChunkLoaderContract;"));
        Assert.assertTrue(chunkLoaderText.contains("public interface IChunkLoader extends ChunkLoaderContract"));
        Assert.assertTrue(chunkLoaderText.contains("default Chunk loadChunk(World world, int chunkX, int chunkZ) throws IOException"));
        Assert.assertTrue(chunkLoaderText.contains("default void saveChunk(World world, Chunk chunk)"));
        Assert.assertTrue(chunkLoaderText.contains("default void saveChunkExtraData(World world, Chunk chunk)"));
        Assert.assertTrue(chunkLoaderText.contains("default void flush()"));
        Assert.assertTrue(chunkLoaderText.contains("default void close()"));

        Assert.assertTrue(chunkProviderText.contains("import com.legacyminecraft.poseidon.world.chunk.ChunkProviderContract;"));
        Assert.assertTrue(chunkProviderText.contains("public interface IChunkProvider extends ChunkProviderContract"));
        Assert.assertTrue(chunkProviderText.contains("default void populate(IChunkProvider provider, int chunkX, int chunkZ)"));

        Assert.assertTrue(playerFileDataText.contains("import com.legacyminecraft.poseidon.world.PlayerFileDataContract;"));
        Assert.assertTrue(playerFileDataText.contains("public interface PlayerFileData extends PlayerFileDataContract"));
        Assert.assertTrue(playerFileDataText.contains("default void savePlayerData(EntityHuman player)"));
        Assert.assertTrue(playerFileDataText.contains("default void loadPlayerData(EntityHuman player)"));
    }
}
