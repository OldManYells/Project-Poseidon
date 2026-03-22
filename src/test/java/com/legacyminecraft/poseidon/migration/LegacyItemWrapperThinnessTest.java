package com.legacyminecraft.poseidon.migration;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LegacyItemWrapperThinnessTest {
    private static final Path ITEM_BED_PATH = Paths.get("src/main/java/net/minecraft/server/ItemBed.java");
    private static final Path ITEM_DOOR_PATH = Paths.get("src/main/java/net/minecraft/server/ItemDoor.java");
    private static final Path ITEM_SIGN_PATH = Paths.get("src/main/java/net/minecraft/server/ItemSign.java");
    private static final Path ITEM_REDSTONE_PATH = Paths.get("src/main/java/net/minecraft/server/ItemRedstone.java");
    private static final Path ITEM_REED_PATH = Paths.get("src/main/java/net/minecraft/server/ItemReed.java");
    private static final Path ITEM_BOAT_PATH = Paths.get("src/main/java/net/minecraft/server/ItemBoat.java");
    private static final Path ITEM_MINECART_PATH = Paths.get("src/main/java/net/minecraft/server/ItemMinecart.java");
    private static final Path ITEM_PAINTING_PATH = Paths.get("src/main/java/net/minecraft/server/ItemPainting.java");
    private static final Path ITEM_EGG_PATH = Paths.get("src/main/java/net/minecraft/server/ItemEgg.java");
    private static final Path ITEM_SNOWBALL_PATH = Paths.get("src/main/java/net/minecraft/server/ItemSnowball.java");
    private static final Path ITEM_FLINT_AND_STEEL_PATH = Paths.get("src/main/java/net/minecraft/server/ItemFlintAndSteel.java");
    private static final Path ITEM_BUCKET_PATH = Paths.get("src/main/java/net/minecraft/server/ItemBucket.java");
    private static final Path ITEM_SEEDS_PATH = Paths.get("src/main/java/net/minecraft/server/ItemSeeds.java");
    private static final Path ITEM_HOE_PATH = Paths.get("src/main/java/net/minecraft/server/ItemHoe.java");
    private static final Path ITEM_BLOCK_PATH = Paths.get("src/main/java/net/minecraft/server/ItemBlock.java");
    private static final Path ITEM_DYE_PATH = Paths.get("src/main/java/net/minecraft/server/ItemDye.java");
    private static final Path ITEM_FOOD_PATH = Paths.get("src/main/java/net/minecraft/server/ItemFood.java");
    private static final Path ITEM_SOUP_PATH = Paths.get("src/main/java/net/minecraft/server/ItemSoup.java");
    private static final Path ITEM_FISHING_ROD_PATH = Paths.get("src/main/java/net/minecraft/server/ItemFishingRod.java");
    private static final Path ITEM_SADDLE_PATH = Paths.get("src/main/java/net/minecraft/server/ItemSaddle.java");
    private static final Path ITEM_SHEARS_PATH = Paths.get("src/main/java/net/minecraft/server/ItemShears.java");
    private static final Path ITEM_CLOTH_PATH = Paths.get("src/main/java/net/minecraft/server/ItemCloth.java");
    private static final Path ITEM_LEAVES_PATH = Paths.get("src/main/java/net/minecraft/server/ItemLeaves.java");
    private static final Path ITEM_LOG_PATH = Paths.get("src/main/java/net/minecraft/server/ItemLog.java");
    private static final Path ITEM_SAPLING_PATH = Paths.get("src/main/java/net/minecraft/server/ItemSapling.java");
    private static final Path ITEM_STEP_PATH = Paths.get("src/main/java/net/minecraft/server/ItemStep.java");
    private static final Path ITEM_PISTON_PATH = Paths.get("src/main/java/net/minecraft/server/ItemPiston.java");
    private static final Path ITEM_TOOL_PATH = Paths.get("src/main/java/net/minecraft/server/ItemTool.java");
    private static final Path ITEM_SWORD_PATH = Paths.get("src/main/java/net/minecraft/server/ItemSword.java");
    private static final Path ITEM_PICKAXE_PATH = Paths.get("src/main/java/net/minecraft/server/ItemPickaxe.java");
    private static final Path ITEM_SPADE_PATH = Paths.get("src/main/java/net/minecraft/server/ItemSpade.java");
    private static final Path ITEM_RECORD_PATH = Paths.get("src/main/java/net/minecraft/server/ItemRecord.java");
    private static final Path ITEM_ARMOR_PATH = Paths.get("src/main/java/net/minecraft/server/ItemArmor.java");
    private static final Path ITEM_WORLD_MAP_BASE_PATH = Paths.get("src/main/java/net/minecraft/server/ItemWorldMapBase.java");
    private static final Path ITEM_WORLD_MAP_PATH = Paths.get("src/main/java/net/minecraft/server/ItemWorldMap.java");
    private static final Path ITEM_AXE_PATH = Paths.get("src/main/java/net/minecraft/server/ItemAxe.java");
    private static final Path ITEM_COAL_PATH = Paths.get("src/main/java/net/minecraft/server/ItemCoal.java");
    private static final Path ITEM_COOKIE_PATH = Paths.get("src/main/java/net/minecraft/server/ItemCookie.java");
    private static final Path ITEM_BOW_PATH = Paths.get("src/main/java/net/minecraft/server/ItemBow.java");
    private static final Path ITEM_PATH = Paths.get("src/main/java/net/minecraft/server/Item.java");
    private static final Path ITEM_STACK_PATH = Paths.get("src/main/java/net/minecraft/server/ItemStack.java");

    @Test
    public void itemBedDelegatesFacingOffsetAndPlacementChecksToCanonicalBehaviours() throws IOException {
        String text = new String(Files.readAllBytes(ITEM_BED_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("BedItemPlacementBehaviour"));
        Assert.assertTrue(text.contains("ItemPlacementMathBehaviour"));
        Assert.assertTrue(text.contains("resolveFacingFromYaw"));
        Assert.assertTrue(text.contains("resolveOffset"));
        Assert.assertTrue(text.contains("canPlaceBed"));
        Assert.assertTrue(text.contains("resolveHeadPartData"));
        Assert.assertFalse(text.contains("MathHelper.floor((double) (entityhuman.yaw * 4.0F / 360.0F) + 0.5D) & 3"));
        Assert.assertFalse(text.contains("if (i1 == 0)"));
        Assert.assertFalse(text.contains("world.isEmpty(i + b0, j, k + b1)"));
    }

    @Test
    public void itemDoorDelegatesDoorBlockFacingAndHingePolicyToCanonicalBehaviours() throws IOException {
        String text = new String(Files.readAllBytes(ITEM_DOOR_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("DoorItemPlacementBehaviour"));
        Assert.assertTrue(text.contains("ItemPlacementMathBehaviour"));
        Assert.assertTrue(text.contains("resolveDoorBlockId"));
        Assert.assertTrue(text.contains("resolveFacingFromYaw"));
        Assert.assertTrue(text.contains("resolveOffset"));
        Assert.assertTrue(text.contains("countSupport"));
        Assert.assertTrue(text.contains("hasDoorHalf"));
        Assert.assertTrue(text.contains("shouldMirrorHinge"));
        Assert.assertTrue(text.contains("resolveBottomData"));
        Assert.assertTrue(text.contains("resolveTopData"));
        Assert.assertFalse(text.contains("MathHelper.floor((double) ((entityhuman.yaw + 180.0F) * 4.0F / 360.0F) - 0.5D) & 3"));
        Assert.assertFalse(text.contains("if (flag && !flag1)"));
        Assert.assertFalse(text.contains("i1 = i1 - 1 & 3;"));
    }

    @Test
    public void itemSignDelegatesTargetBlockAndDataPolicyToCanonicalBehaviours() throws IOException {
        String text = new String(Files.readAllBytes(ITEM_SIGN_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("SignItemPlacementBehaviour"));
        Assert.assertTrue(text.contains("ItemPlacementMathBehaviour"));
        Assert.assertTrue(text.contains("resolveTarget"));
        Assert.assertTrue(text.contains("resolvePlacedBlockId"));
        Assert.assertTrue(text.contains("resolvePlacedData"));
        Assert.assertFalse(text.contains("if (l == 2)"));
        Assert.assertFalse(text.contains("MathHelper.floor((double) ((entityhuman.yaw + 180.0F) * 16.0F / 360.0F) + 0.5D) & 15"));
        Assert.assertFalse(text.contains("world.setTypeIdAndData(i, j, k, Block.WALL_SIGN.id, l)"));
    }

    @Test
    public void itemRedstoneDelegatesSnowAwareTargetSelectionToCanonicalBehaviours() throws IOException {
        String text = new String(Files.readAllBytes(ITEM_REDSTONE_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("RedstoneItemPlacementBehaviour"));
        Assert.assertTrue(text.contains("ItemPlacementMathBehaviour"));
        Assert.assertTrue(text.contains("resolveTarget"));
        Assert.assertTrue(text.contains("requiresEmptyTarget"));
        Assert.assertFalse(text.contains("if (world.getTypeId(i, j, k) != Block.SNOW.id)"));
        Assert.assertFalse(text.contains("if (l == 0)"));
    }

    @Test
    public void itemReedDelegatesSnowAwareTargetAndFacePolicyToCanonicalBehaviours() throws IOException {
        String text = new String(Files.readAllBytes(ITEM_REED_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("ReedItemPlacementBehaviour"));
        Assert.assertTrue(text.contains("ItemPlacementMathBehaviour"));
        Assert.assertTrue(text.contains("resolveTargetAndFace"));
        Assert.assertTrue(text.contains("hasItemsLeft"));
        Assert.assertFalse(text.contains("if (world.getTypeId(i, j, k) == Block.SNOW.id)"));
        Assert.assertFalse(text.contains("if (l == 5)"));
        Assert.assertFalse(text.contains("if (itemstack.count == 0)"));
    }

    @Test
    public void itemBoatDelegatesRayTraceAndSpawnPolicyToCanonicalBehaviours() throws IOException {
        String text = new String(Files.readAllBytes(ITEM_BOAT_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("BoatItemPlacementBehaviour"));
        Assert.assertTrue(text.contains("BOAT_ITEM_PLACEMENT_BEHAVIOUR.use"));
        Assert.assertFalse(text.contains("world.rayTrace("));
        Assert.assertFalse(text.contains("new EntityBoat("));
    }

    @Test
    public void itemMinecartDelegatesTrackCheckAndSpawnPolicyToCanonicalBehaviours() throws IOException {
        String text = new String(Files.readAllBytes(ITEM_MINECART_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("MinecartItemPlacementBehaviour"));
        Assert.assertTrue(text.contains("MINECART_ITEM_PLACEMENT_BEHAVIOUR.place"));
        Assert.assertFalse(text.contains("BlockMinecartTrack.c("));
        Assert.assertFalse(text.contains("new EntityMinecart("));
    }

    @Test
    public void itemPaintingDelegatesDirectionAndPlacementPolicyToCanonicalBehaviours() throws IOException {
        String text = new String(Files.readAllBytes(ITEM_PAINTING_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("PaintingItemPlacementBehaviour"));
        Assert.assertTrue(text.contains("PAINTING_ITEM_PLACEMENT_BEHAVIOUR.place"));
        Assert.assertFalse(text.contains("new EntityPainting("));
        Assert.assertFalse(text.contains("CraftBlock.notchToBlockFace"));
    }

    @Test
    public void itemEggAndSnowballDelegateProjectileThrowPolicyToCanonicalBehaviours() throws IOException {
        String egg = new String(Files.readAllBytes(ITEM_EGG_PATH), StandardCharsets.UTF_8);
        String snowball = new String(Files.readAllBytes(ITEM_SNOWBALL_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(egg.contains("ThrowableItemBehaviour"));
        Assert.assertTrue(egg.contains("throwEgg"));
        Assert.assertFalse(egg.contains("new EntityEgg("));
        Assert.assertFalse(egg.contains("world.makeSound("));

        Assert.assertTrue(snowball.contains("ThrowableItemBehaviour"));
        Assert.assertTrue(snowball.contains("throwSnowball"));
        Assert.assertFalse(snowball.contains("new EntitySnowball("));
        Assert.assertFalse(snowball.contains("world.makeSound("));
    }

    @Test
    public void itemBowDelegatesArrowUseAndSoundPolicyToCanonicalBehaviour() throws IOException {
        String text = new String(Files.readAllBytes(ITEM_BOW_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("BowUseBehaviour"));
        Assert.assertTrue(text.contains("BOW_USE_BEHAVIOUR.use"));
        Assert.assertFalse(text.contains("PoseidonConfig.getInstance().getProperty"));
        Assert.assertFalse(text.contains("new EntityArrow("));
        Assert.assertFalse(text.contains("world.makeSound("));
    }

    @Test
    public void itemFlintAndSteelDelegatesIgniteAndPlaceEventPolicyToCanonicalBehaviours() throws IOException {
        String text = new String(Files.readAllBytes(ITEM_FLINT_AND_STEEL_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("FlintAndSteelItemPlacementBehaviour"));
        Assert.assertTrue(text.contains("FLINT_AND_STEEL_ITEM_PLACEMENT_BEHAVIOUR.placeFire"));
        Assert.assertFalse(text.contains("new BlockIgniteEvent("));
        Assert.assertFalse(text.contains("callBlockPlaceEvent("));
        Assert.assertFalse(text.contains("world.setTypeId("));
    }

    @Test
    public void itemBucketDelegatesFillEmptyAndRaytracePolicyToCanonicalBehaviours() throws IOException {
        String text = new String(Files.readAllBytes(ITEM_BUCKET_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("BucketItemBehaviour"));
        Assert.assertTrue(text.contains("BUCKET_ITEM_BEHAVIOUR.use"));
        Assert.assertFalse(text.contains("world.rayTrace("));
        Assert.assertFalse(text.contains("callPlayerBucketFillEvent("));
        Assert.assertFalse(text.contains("callPlayerBucketEmptyEvent("));
    }

    @Test
    public void itemSeedsDelegatesFarmlandAndPlacementEventPolicyToCanonicalBehaviours() throws IOException {
        String text = new String(Files.readAllBytes(ITEM_SEEDS_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("SeedsItemPlacementBehaviour"));
        Assert.assertTrue(text.contains("SEEDS_ITEM_PLACEMENT_BEHAVIOUR.place"));
        Assert.assertFalse(text.contains("world.setTypeId(i, j + 1, k, this.id)"));
        Assert.assertFalse(text.contains("callBlockPlaceEvent("));
    }

    @Test
    public void itemHoeDelegatesTillingAndPlacementEventPolicyToCanonicalBehaviours() throws IOException {
        String text = new String(Files.readAllBytes(ITEM_HOE_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("HoeTillingBehaviour"));
        Assert.assertTrue(text.contains("HOE_TILLING_BEHAVIOUR.till"));
        Assert.assertFalse(text.contains("world.setTypeId(i, j, k, block.id)"));
        Assert.assertFalse(text.contains("callBlockPlaceEvent("));
    }

    @Test
    public void itemBlockDelegatesPlacementLifecycleAndPistonOrderingPolicyToCanonicalBehaviours() throws IOException {
        String text = new String(Files.readAllBytes(ITEM_BLOCK_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("ItemBlockPlacementBehaviour"));
        Assert.assertTrue(text.contains("ITEM_BLOCK_PLACEMENT_BEHAVIOUR.place"));
        Assert.assertTrue(text.contains("ITEM_BLOCK_PLACEMENT_BEHAVIOUR.resolveTranslationKey"));
        Assert.assertFalse(text.contains("PoseidonConfig.getInstance().getConfigBoolean"));
        Assert.assertFalse(text.contains("world.setRawTypeIdAndData("));
        Assert.assertFalse(text.contains("callBlockPlaceEvent("));
    }

    @Test
    public void itemDyeDelegatesBonemealAndSheepColorPolicyToCanonicalBehaviours() throws IOException {
        String text = new String(Files.readAllBytes(ITEM_DYE_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("DyeItemBehaviour"));
        Assert.assertTrue(text.contains("DYE_ITEM_BEHAVIOUR.applyToBlock"));
        Assert.assertTrue(text.contains("DYE_ITEM_BEHAVIOUR.applyToEntity"));
        Assert.assertFalse(text.contains("label53:"));
        Assert.assertFalse(text.contains("entitysheep.setColor("));
        Assert.assertFalse(text.contains("Block.CROPS.id"));
    }

    @Test
    public void itemFoodAndSoupDelegateConsumptionPolicyToCanonicalBehaviours() throws IOException {
        String food = new String(Files.readAllBytes(ITEM_FOOD_PATH), StandardCharsets.UTF_8);
        String soup = new String(Files.readAllBytes(ITEM_SOUP_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(food.contains("FoodItemConsumptionBehaviour"));
        Assert.assertTrue(food.contains("FOOD_ITEM_CONSUMPTION_BEHAVIOUR.consume"));
        Assert.assertFalse(food.contains("entityhuman.b(this.a)"));

        Assert.assertTrue(soup.contains("FoodItemConsumptionBehaviour"));
        Assert.assertTrue(soup.contains("FOOD_ITEM_CONSUMPTION_BEHAVIOUR.consumeSoup"));
        Assert.assertFalse(soup.contains("new ItemStack(Item.BOWL)"));
        Assert.assertFalse(soup.contains("super.a(itemstack, world, entityhuman)"));
    }

    @Test
    public void itemFishingRodDelegatesHookAndCastPolicyToCanonicalBehaviours() throws IOException {
        String text = new String(Files.readAllBytes(ITEM_FISHING_ROD_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("FishingRodUseBehaviour"));
        Assert.assertTrue(text.contains("FISHING_ROD_USE_BEHAVIOUR.use"));
        Assert.assertFalse(text.contains("new PlayerFishEvent("));
        Assert.assertFalse(text.contains("new EntityFish("));
    }

    @Test
    public void itemSaddleDelegatesPigSaddlingPolicyToCanonicalBehaviours() throws IOException {
        String text = new String(Files.readAllBytes(ITEM_SADDLE_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("SaddleUseBehaviour"));
        Assert.assertTrue(text.contains("SADDLE_USE_BEHAVIOUR.applyToEntity"));
        Assert.assertTrue(text.contains("SADDLE_USE_BEHAVIOUR.interactEntity"));
        Assert.assertFalse(text.contains("entitypig.setSaddle(true)"));
    }

    @Test
    public void itemShearsDelegatesBreakSpeedAndDurabilityPolicyToCanonicalBehaviours() throws IOException {
        String text = new String(Files.readAllBytes(ITEM_SHEARS_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("ShearsInteractionBehaviour"));
        Assert.assertTrue(text.contains("SHEARS_INTERACTION_BEHAVIOUR.shouldDamageOnBlockBreak"));
        Assert.assertTrue(text.contains("SHEARS_INTERACTION_BEHAVIOUR.canHarvest"));
        Assert.assertTrue(text.contains("SHEARS_INTERACTION_BEHAVIOUR.breakSpeedMultiplier"));
        Assert.assertFalse(text.contains("block.id != Block.WEB.id && block.id != Block.LEAVES.id"));
    }

    @Test
    public void itemVariantWrappersDelegateFilterDataRulesToCanonicalBehaviours() throws IOException {
        String cloth = new String(Files.readAllBytes(ITEM_CLOTH_PATH), StandardCharsets.UTF_8);
        String leaves = new String(Files.readAllBytes(ITEM_LEAVES_PATH), StandardCharsets.UTF_8);
        String log = new String(Files.readAllBytes(ITEM_LOG_PATH), StandardCharsets.UTF_8);
        String sapling = new String(Files.readAllBytes(ITEM_SAPLING_PATH), StandardCharsets.UTF_8);
        String step = new String(Files.readAllBytes(ITEM_STEP_PATH), StandardCharsets.UTF_8);
        String piston = new String(Files.readAllBytes(ITEM_PISTON_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(cloth.contains("ItemDataVariantBehaviour"));
        Assert.assertTrue(cloth.contains("identity(i)"));
        Assert.assertFalse(cloth.contains("return i;"));

        Assert.assertTrue(leaves.contains("ItemDataVariantBehaviour"));
        Assert.assertTrue(leaves.contains("leavesPlacementData(i)"));
        Assert.assertFalse(leaves.contains("return i | 8;"));

        Assert.assertTrue(log.contains("ItemDataVariantBehaviour"));
        Assert.assertTrue(log.contains("identity(i)"));
        Assert.assertFalse(log.contains("return i;"));

        Assert.assertTrue(sapling.contains("ItemDataVariantBehaviour"));
        Assert.assertTrue(sapling.contains("identity(i)"));
        Assert.assertFalse(sapling.contains("return i;"));

        Assert.assertTrue(step.contains("ItemDataVariantBehaviour"));
        Assert.assertTrue(step.contains("identity(i)"));
        Assert.assertFalse(step.contains("return i;"));

        Assert.assertTrue(piston.contains("ItemDataVariantBehaviour"));
        Assert.assertTrue(piston.contains("pistonPlacementData()"));
        Assert.assertFalse(piston.contains("return 7;"));
    }

    @Test
    public void toolFamilyDelegatesCombatMiningAndHarvestPolicyToCanonicalBehaviours() throws IOException {
        String tool = new String(Files.readAllBytes(ITEM_TOOL_PATH), StandardCharsets.UTF_8);
        String sword = new String(Files.readAllBytes(ITEM_SWORD_PATH), StandardCharsets.UTF_8);
        String pickaxe = new String(Files.readAllBytes(ITEM_PICKAXE_PATH), StandardCharsets.UTF_8);
        String spade = new String(Files.readAllBytes(ITEM_SPADE_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(tool.contains("ToolItemCombatAndMiningBehaviour"));
        Assert.assertTrue(tool.contains("resolveDestroySpeed"));
        Assert.assertTrue(tool.contains("damageOnEntityHit"));
        Assert.assertTrue(tool.contains("damageOnBlockBreak"));
        Assert.assertFalse(tool.contains("for (int i = 0; i < this.bk.length; ++i)"));
        Assert.assertFalse(tool.contains("itemstack.damage(2, entityliving1)"));

        Assert.assertTrue(sword.contains("SwordItemBehaviour"));
        Assert.assertTrue(sword.contains("resolveDestroySpeed"));
        Assert.assertTrue(sword.contains("resolveAttackDamage"));
        Assert.assertFalse(sword.contains("block.id == Block.WEB.id ? 15.0F : 1.5F"));
        Assert.assertFalse(sword.contains("this.a = 4 + enumtoolmaterial.c() * 2"));

        Assert.assertTrue(pickaxe.contains("PickaxeHarvestBehaviour"));
        Assert.assertTrue(pickaxe.contains("PICKAXE_HARVEST_BEHAVIOUR.canHarvest"));
        Assert.assertFalse(pickaxe.contains("block == Block.OBSIDIAN ? this.a.d() == 3"));

        Assert.assertTrue(spade.contains("SpadeHarvestBehaviour"));
        Assert.assertTrue(spade.contains("SPADE_HARVEST_BEHAVIOUR.canHarvest"));
        Assert.assertFalse(spade.contains("return block == Block.SNOW ? true : block == Block.SNOW_BLOCK;"));
    }

    @Test
    public void itemRecordAndArmorDelegatePlacementAndStatPolicyToCanonicalBehaviours() throws IOException {
        String record = new String(Files.readAllBytes(ITEM_RECORD_PATH), StandardCharsets.UTF_8);
        String armor = new String(Files.readAllBytes(ITEM_ARMOR_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(record.contains("RecordItemPlacementBehaviour"));
        Assert.assertTrue(record.contains("RECORD_ITEM_PLACEMENT_BEHAVIOUR.placeRecord"));
        Assert.assertFalse(record.contains("((BlockJukeBox) Block.JUKEBOX).f"));

        Assert.assertTrue(armor.contains("ArmorItemStatsBehaviour"));
        Assert.assertTrue(armor.contains("resolveArmorPoints"));
        Assert.assertTrue(armor.contains("resolveDurability"));
        Assert.assertFalse(armor.contains("this.bl = bn[l]"));
        Assert.assertFalse(armor.contains("this.d(bo[l] * 3 << j)"));
    }

    @Test
    public void itemWorldMapFamilyDelegatesMapLifecycleAndPacketPolicyToCanonicalBehaviours() throws IOException {
        String worldMapBase = new String(Files.readAllBytes(ITEM_WORLD_MAP_BASE_PATH), StandardCharsets.UTF_8);
        String worldMap = new String(Files.readAllBytes(ITEM_WORLD_MAP_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(worldMapBase.contains("WorldMapBaseItemBehaviour"));
        Assert.assertTrue(worldMapBase.contains("isMapRendererItem"));
        Assert.assertTrue(worldMapBase.contains("createUpdatePacket"));
        Assert.assertFalse(worldMapBase.contains("return true;"));
        Assert.assertFalse(worldMapBase.contains("return null;"));

        Assert.assertTrue(worldMap.contains("WorldMapItemBehaviour"));
        Assert.assertTrue(worldMap.contains("resolveMap"));
        Assert.assertTrue(worldMap.contains("updateMapData"));
        Assert.assertTrue(worldMap.contains("onUpdate"));
        Assert.assertTrue(worldMap.contains("onCrafted"));
        Assert.assertTrue(worldMap.contains("createUpdatePacket"));
        Assert.assertFalse(worldMap.contains("new MapInitializeEvent("));
        Assert.assertFalse(worldMap.contains("Chunk chunk = world.getChunkAtWorldCoords"));
    }

    @Test
    public void itemAxeCoalAndCookieDelegateProfileAndInitPolicyToCanonicalBehaviours() throws IOException {
        String axe = new String(Files.readAllBytes(ITEM_AXE_PATH), StandardCharsets.UTF_8);
        String coal = new String(Files.readAllBytes(ITEM_COAL_PATH), StandardCharsets.UTF_8);
        String cookie = new String(Files.readAllBytes(ITEM_COOKIE_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(axe.contains("AxeToolProfileBehaviour"));
        Assert.assertTrue(axe.contains("effectiveBlocks()"));
        Assert.assertTrue(axe.contains("baseAttackOffset()"));
        Assert.assertFalse(axe.contains("new Block[] { Block.WOOD, Block.BOOKSHELF, Block.LOG, Block.CHEST}"));

        Assert.assertTrue(coal.contains("CoalItemBehaviour"));
        Assert.assertTrue(coal.contains("hasSubtypes()"));
        Assert.assertTrue(coal.contains("defaultDataValue()"));
        Assert.assertFalse(coal.contains("this.a(true)"));
        Assert.assertFalse(coal.contains("this.d(0)"));

        Assert.assertTrue(cookie.contains("CookieItemBehaviour"));
        Assert.assertTrue(cookie.contains("resolveMaxStackSize(k)"));
        Assert.assertFalse(cookie.contains("this.maxStackSize = k"));
    }

    @Test
    public void itemCoreAndItemStackDelegateStateAndUtilityPolicyToCanonicalBehaviours() throws IOException {
        String item = new String(Files.readAllBytes(ITEM_PATH), StandardCharsets.UTF_8);
        String itemStack = new String(Files.readAllBytes(ITEM_STACK_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(item.contains("ItemCoreBehaviour"));
        Assert.assertTrue(item.contains("resolveTextureId("));
        Assert.assertTrue(item.contains("resolveTextureIdFromGrid"));
        Assert.assertTrue(item.contains("isDamageableUsable"));
        Assert.assertTrue(item.contains("registerOrWarn"));
        Assert.assertTrue(item.contains("prefixedName"));
        Assert.assertTrue(item.contains("validateCraftingResultAssignment"));
        Assert.assertTrue(item.contains("localizeItemName"));
        Assert.assertFalse(item.contains("this.textureId = i + j * 16"));
        Assert.assertFalse(item.contains("System.out.println(\"CONFLICT @ \" + i)"));
        Assert.assertFalse(item.contains("this.name = \"item.\" + s"));
        Assert.assertFalse(item.contains("throw new IllegalArgumentException(\"Max stack size must be 1 for items with crafting results\")"));

        Assert.assertTrue(itemStack.contains("ItemStackStateBehaviour"));
        Assert.assertTrue(itemStack.contains("ItemStackInteractionBehaviour"));
        Assert.assertTrue(itemStack.contains("splitStack(this, i)"));
        Assert.assertTrue(itemStack.contains("writeToNbt(this"));
        Assert.assertTrue(itemStack.contains("readFromNbt(this"));
        Assert.assertTrue(itemStack.contains("ITEM_STACK_INTERACTION_BEHAVIOUR.placeItem"));
        Assert.assertTrue(itemStack.contains("ITEM_STACK_INTERACTION_BEHAVIOUR.damage"));
        Assert.assertTrue(itemStack.contains("stackEqualsNullable"));
        Assert.assertTrue(itemStack.contains("stringify(this)"));
        Assert.assertTrue(itemStack.contains("cloneStack(this)"));
        Assert.assertTrue(itemStack.contains("strictEquals(this, itemstack)"));
        Assert.assertFalse(itemStack.contains("nbttagcompound.a(\"id\", (short) this.id)"));
        Assert.assertFalse(itemStack.contains("return itemstack == null ? null : itemstack.cloneItemStack()"));
        Assert.assertFalse(itemStack.contains("new PlayerItemDamageEvent("));
    }
}
