package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.item.BedItemPlacementBehaviour;
import com.legacyminecraft.poseidon.item.BoatItemPlacementBehaviour;
import com.legacyminecraft.poseidon.item.ArmorItemStatsBehaviour;
import com.legacyminecraft.poseidon.item.DoorItemPlacementBehaviour;
import com.legacyminecraft.poseidon.item.ItemPlacementMathBehaviour;
import com.legacyminecraft.poseidon.item.PaintingItemPlacementBehaviour;
import com.legacyminecraft.poseidon.item.PickaxeHarvestBehaviour;
import com.legacyminecraft.poseidon.item.RecordItemPlacementBehaviour;
import com.legacyminecraft.poseidon.item.RedstoneItemPlacementBehaviour;
import com.legacyminecraft.poseidon.item.ReedItemPlacementBehaviour;
import com.legacyminecraft.poseidon.item.SignItemPlacementBehaviour;
import com.legacyminecraft.poseidon.item.SpadeHarvestBehaviour;
import com.legacyminecraft.poseidon.item.SwordItemBehaviour;
import com.legacyminecraft.poseidon.item.ThrowableItemBehaviour;
import com.legacyminecraft.poseidon.item.ToolItemCombatAndMiningBehaviour;
import com.legacyminecraft.poseidon.item.WorldMapBaseItemBehaviour;
import com.legacyminecraft.poseidon.item.WorldMapItemBehaviour;
import com.legacyminecraft.poseidon.item.AxeToolProfileBehaviour;
import com.legacyminecraft.poseidon.item.CoalItemBehaviour;
import com.legacyminecraft.poseidon.item.CookieItemBehaviour;
import com.legacyminecraft.poseidon.item.ItemCoreBehaviour;
import com.legacyminecraft.poseidon.item.ItemStackInteractionBehaviour;
import com.legacyminecraft.poseidon.item.ItemStackStateBehaviour;
import org.junit.Assert;
import org.junit.Test;

import net.minecraft.server.Block;
import net.minecraft.server.EnumToolMaterial;
import net.minecraft.server.NBTTagCompound;
import net.minecraft.server.ItemStack;

public class ItemPlacementBehaviourTest {
    @Test
    public void bedAndMathPlacementRulesMatchLegacyBehavior() {
        ItemPlacementMathBehaviour math = ItemPlacementMathBehaviour.getInstance();
        BedItemPlacementBehaviour bed = BedItemPlacementBehaviour.getInstance();

        Assert.assertTrue(math.isTopFace(1));
        Assert.assertFalse(math.isTopFace(0));
        ItemPlacementMathBehaviour.Position up = math.applyFaceOffset(10, 64, 10, 1);
        Assert.assertEquals(10, up.x);
        Assert.assertEquals(65, up.y);
        Assert.assertEquals(10, up.z);
        Assert.assertEquals(0, math.yawToCardinal4(0.0F));
        Assert.assertEquals(1, math.yawToCardinal4(90.0F));
        Assert.assertEquals(2, math.yawToCardinal4(180.0F));
        Assert.assertEquals(3, math.yawToCardinal4(270.0F));
        Assert.assertEquals(8, math.yawToSignRotation16(0.0F));
        Assert.assertTrue(math.isSnow(78, 78));
        Assert.assertFalse(math.isSnow(1, 78));

        ItemPlacementMathBehaviour.Offset offset = bed.resolveOffset(1, math);
        Assert.assertEquals(-1, offset.x);
        Assert.assertEquals(0, offset.z);
        Assert.assertTrue(bed.canPlaceBed(true, true, true, true));
        Assert.assertFalse(bed.canPlaceBed(true, false, true, true));
        Assert.assertEquals(10, bed.resolveHeadPartData(2));
    }

    @Test
    public void doorSignRedstoneAndReedPlacementRulesMatchLegacyBehavior() {
        ItemPlacementMathBehaviour math = ItemPlacementMathBehaviour.getInstance();
        DoorItemPlacementBehaviour door = DoorItemPlacementBehaviour.getInstance();
        SignItemPlacementBehaviour sign = SignItemPlacementBehaviour.getInstance();
        RedstoneItemPlacementBehaviour redstone = RedstoneItemPlacementBehaviour.getInstance();
        ReedItemPlacementBehaviour reed = ReedItemPlacementBehaviour.getInstance();

        Assert.assertEquals(64, door.resolveDoorBlockId(true, 64, 71));
        Assert.assertEquals(71, door.resolveDoorBlockId(false, 64, 71));
        Assert.assertEquals(1, door.resolveFacingFromYaw(0.0F, math));
        Assert.assertEquals(1, door.countSupport(true, false));
        Assert.assertTrue(door.hasDoorHalf(false, true));
        Assert.assertTrue(door.shouldMirrorHinge(true, false, 0, 0));
        Assert.assertTrue(door.shouldMirrorHinge(false, false, 0, 1));
        Assert.assertEquals(6, door.resolveBottomData(3, true));
        Assert.assertEquals(14, door.resolveTopData(6));

        Assert.assertTrue(sign.isBottomFace(0));
        Assert.assertFalse(sign.isBottomFace(1));
        Assert.assertTrue(sign.canAttachToClickedBlock(true));
        Assert.assertEquals(63, sign.resolvePlacedBlockId(1, 63, 68));
        Assert.assertEquals(68, sign.resolvePlacedBlockId(2, 63, 68));
        Assert.assertEquals(8, sign.resolvePlacedData(1, 0.0F, math));
        Assert.assertEquals(4, sign.resolvePlacedData(4, 0.0F, math));

        ItemPlacementMathBehaviour.Position redstoneTarget = redstone.resolveTarget(10, 64, 10, 3, 1, 78, math);
        Assert.assertEquals(11, redstoneTarget.z);
        Assert.assertTrue(redstone.requiresEmptyTarget(1, 78, math));
        Assert.assertFalse(redstone.requiresEmptyTarget(78, 78, math));

        ReedItemPlacementBehaviour.Placement reedSnow = reed.resolveTargetAndFace(10, 64, 10, 3, 78, 78, math);
        Assert.assertEquals(0, reedSnow.face);
        Assert.assertEquals(10, reedSnow.x);
        Assert.assertTrue(reed.hasItemsLeft(1));
        Assert.assertFalse(reed.hasItemsLeft(0));
    }

    @Test
    public void paintingThrowableAndRayMathBehavioursMatchLegacyBehavior() {
        PaintingItemPlacementBehaviour painting = PaintingItemPlacementBehaviour.getInstance();
        ThrowableItemBehaviour throwable = ThrowableItemBehaviour.getInstance();
        BoatItemPlacementBehaviour boat = BoatItemPlacementBehaviour.getInstance();

        Assert.assertEquals(0, painting.resolveDirection(2));
        Assert.assertEquals(1, painting.resolveDirection(4));
        Assert.assertEquals(2, painting.resolveDirection(3));
        Assert.assertEquals(3, painting.resolveDirection(5));

        float throwPitch = throwable.resolveThrowPitch(new java.util.Random(0L));
        Assert.assertTrue(throwPitch > 0.3F);
        Assert.assertTrue(throwPitch < 0.5F);
        Assert.assertNotNull(boat);
    }

    @Test
    public void toolAndHarvestBehavioursMatchLegacyBehavior() {
        ToolItemCombatAndMiningBehaviour tool = ToolItemCombatAndMiningBehaviour.getInstance();
        SwordItemBehaviour sword = SwordItemBehaviour.getInstance();
        PickaxeHarvestBehaviour pickaxe = PickaxeHarvestBehaviour.getInstance();
        SpadeHarvestBehaviour spade = SpadeHarvestBehaviour.getInstance();

        Assert.assertEquals(1.0F, tool.resolveDestroySpeed(new Block[] { Block.STONE }, 4.0F, Block.DIRT), 0.0F);
        Assert.assertEquals(4.0F, tool.resolveDestroySpeed(new Block[] { Block.STONE }, 4.0F, Block.STONE), 0.0F);
        Assert.assertEquals(2 + EnumToolMaterial.IRON.c(), tool.resolveToolAttackDamage(2, EnumToolMaterial.IRON));

        Assert.assertEquals(15.0F, sword.resolveDestroySpeed(Block.WEB), 0.0F);
        Assert.assertEquals(1.5F, sword.resolveDestroySpeed(Block.DIRT), 0.0F);
        Assert.assertTrue(sword.canHarvest(Block.WEB));
        Assert.assertFalse(sword.canHarvest(Block.DIRT));

        Assert.assertTrue(pickaxe.canHarvest(Block.OBSIDIAN, EnumToolMaterial.DIAMOND));
        Assert.assertFalse(pickaxe.canHarvest(Block.OBSIDIAN, EnumToolMaterial.IRON));
        Assert.assertTrue(pickaxe.canHarvest(Block.IRON_ORE, EnumToolMaterial.STONE));
        Assert.assertFalse(pickaxe.canHarvest(Block.REDSTONE_ORE, EnumToolMaterial.STONE));

        Assert.assertTrue(spade.canHarvest(Block.SNOW));
        Assert.assertTrue(spade.canHarvest(Block.SNOW_BLOCK));
        Assert.assertFalse(spade.canHarvest(Block.DIRT));
    }

    @Test
    public void armorAndRecordBehavioursMatchLegacyBehavior() {
        ArmorItemStatsBehaviour armor = ArmorItemStatsBehaviour.getInstance();
        RecordItemPlacementBehaviour record = RecordItemPlacementBehaviour.getInstance();
        WorldMapBaseItemBehaviour mapBase = WorldMapBaseItemBehaviour.getInstance();
        WorldMapItemBehaviour map = WorldMapItemBehaviour.getInstance();
        AxeToolProfileBehaviour axe = AxeToolProfileBehaviour.getInstance();
        CoalItemBehaviour coal = CoalItemBehaviour.getInstance();
        CookieItemBehaviour cookie = CookieItemBehaviour.getInstance();

        Assert.assertEquals(3, armor.resolveArmorPoints(0));
        Assert.assertEquals(8, armor.resolveArmorPoints(1));
        Assert.assertEquals(33, armor.resolveDurability(0, 0));
        Assert.assertEquals((13 * 3) << 3, armor.resolveDurability(3, 3));
        Assert.assertNotNull(record);
        Assert.assertTrue(mapBase.isMapRendererItem());
        Assert.assertNotNull(map);
        Assert.assertEquals(4, axe.effectiveBlocks().length);
        Assert.assertEquals(3, axe.baseAttackOffset());
        Assert.assertTrue(coal.hasSubtypes());
        Assert.assertEquals(0, coal.defaultDataValue());
        Assert.assertEquals(8, cookie.resolveMaxStackSize(8));
    }

    @Test
    public void itemCoreAndItemStackStateBehavioursMatchLegacyBehavior() {
        ItemCoreBehaviour itemCore = ItemCoreBehaviour.getInstance();
        ItemStackInteractionBehaviour stackInteraction = ItemStackInteractionBehaviour.getInstance();
        ItemStackStateBehaviour stackState = ItemStackStateBehaviour.getInstance();

        Assert.assertEquals(12, itemCore.resolveTextureId(12));
        Assert.assertEquals(34, itemCore.resolveTextureIdFromGrid(2, 2));
        Assert.assertEquals(0, itemCore.filterData(99));
        Assert.assertTrue(itemCore.isDamageableUsable(10, false));
        Assert.assertFalse(itemCore.isDamageableUsable(0, false));
        Assert.assertEquals("item.apple", itemCore.prefixedName("apple"));
        Assert.assertTrue(itemCore.hasCraftingResult(new Object()));
        Assert.assertFalse(itemCore.hasCraftingResult(null));
        Assert.assertEquals("Apple", itemCore.localizeItemName("item.apple"));

        ItemStack stack = new ItemStack(1, 3, 5);
        NBTTagCompound tag = new NBTTagCompound();
        stackState.writeToNbt(stack, tag);
        ItemStack clone = new ItemStack(tag);
        Assert.assertTrue(stackState.countIdDamageEquals(stack, clone));
        Assert.assertTrue(stackState.materialsMatch(stack, clone));
        Assert.assertEquals("3xtile.stone@5", stackState.stringify(stack));
        ItemStack split = stackState.splitStack(stack, 1);
        Assert.assertEquals(2, stack.count);
        Assert.assertEquals(1, split.count);
        Assert.assertTrue(stackState.strictEquals(stack, new ItemStack(1, 2, 5)));
        Assert.assertNotNull(stackInteraction);
    }
}
