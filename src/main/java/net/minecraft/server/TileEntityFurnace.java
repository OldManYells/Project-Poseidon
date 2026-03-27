package net.minecraft.server;

import com.legacyminecraft.compat.bukkit.FurnaceEventBridgeBehaviour;
import com.legacyminecraft.poseidon.inventory.FurnaceBurnEligibilityBehaviour;
import com.legacyminecraft.poseidon.inventory.FurnaceFuelConsumptionBehaviour;
import com.legacyminecraft.poseidon.inventory.FurnaceFuelBurnTimeBehaviour;
import com.legacyminecraft.poseidon.inventory.FurnaceLitStateBehaviour;
import com.legacyminecraft.poseidon.inventory.FurnaceNbtCodecBehaviour;
import com.legacyminecraft.poseidon.inventory.FurnaceRefuelFlowBehaviour;
import com.legacyminecraft.poseidon.inventory.FurnaceSmeltOutputBehaviour;
import com.legacyminecraft.poseidon.inventory.FurnaceTickProgressionBehaviour;

public class TileEntityFurnace extends TileEntity implements IInventory {
    private static final FurnaceEventBridgeBehaviour FURNACE_EVENT_BRIDGE_BEHAVIOUR = FurnaceEventBridgeBehaviour.getInstance();
    private static final FurnaceBurnEligibilityBehaviour FURNACE_BURN_ELIGIBILITY_BEHAVIOUR = FurnaceBurnEligibilityBehaviour.getInstance();
    private static final FurnaceFuelConsumptionBehaviour FURNACE_FUEL_CONSUMPTION_BEHAVIOUR = FurnaceFuelConsumptionBehaviour.getInstance();
    private static final FurnaceFuelBurnTimeBehaviour FURNACE_FUEL_BURN_TIME_BEHAVIOUR = FurnaceFuelBurnTimeBehaviour.getInstance();
    private static final FurnaceLitStateBehaviour FURNACE_LIT_STATE_BEHAVIOUR = FurnaceLitStateBehaviour.getInstance();
    private static final FurnaceNbtCodecBehaviour FURNACE_NBT_CODEC_BEHAVIOUR = FurnaceNbtCodecBehaviour.getInstance();
    private static final FurnaceRefuelFlowBehaviour FURNACE_REFUEL_FLOW_BEHAVIOUR = FurnaceRefuelFlowBehaviour.getInstance();
    private static final FurnaceSmeltOutputBehaviour FURNACE_SMELT_OUTPUT_BEHAVIOUR = FurnaceSmeltOutputBehaviour.getInstance();
    private static final FurnaceTickProgressionBehaviour FURNACE_TICK_PROGRESSION_BEHAVIOUR = FurnaceTickProgressionBehaviour.getInstance();

    private ItemStack[] items = new ItemStack[3];
    public int burnTime = 0;
    public int ticksForCurrentFuel = 0;
    public int cookTime = 0;

    // CraftBukkit start
    private int lastTick = (int) (System.currentTimeMillis() / 50);
    public ItemStack[] getContents() {
        return this.items;
    }
    // CraftBukkit end

    public TileEntityFurnace() {}

    public int getSize() {
        return this.items.length;
    }

    public ItemStack getItem(int i) {
        return this.items[i];
    }

    public ItemStack splitStack(int i, int j) {
        if (this.items[i] != null) {
            ItemStack itemstack;

            if (this.items[i].count <= j) {
                itemstack = this.items[i];
                this.items[i] = null;
                return itemstack;
            } else {
                itemstack = this.items[i].a(j);
                if (this.items[i].count == 0) {
                    this.items[i] = null;
                }

                return itemstack;
            }
        } else {
            return null;
        }
    }

    public void setItem(int i, ItemStack itemstack) {
        this.items[i] = itemstack;
        if (itemstack != null && itemstack.count > this.getMaxStackSize()) {
            itemstack.count = this.getMaxStackSize();
        }
    }

    public String getName() {
        return "Furnace";
    }

    public void a(NBTTagCompound nbttagcompound) {
        super.a(nbttagcompound);
        FurnaceNbtCodecBehaviour.FurnaceNbtState state = FURNACE_NBT_CODEC_BEHAVIOUR.readState(nbttagcompound, this.getSize());
        this.items = (ItemStack[]) (Object) state.getItems();
        this.burnTime = state.getBurnTime();
        this.cookTime = state.getCookTime();
        this.ticksForCurrentFuel = this.fuelTime(this.items[1]);
    }

    public void b(NBTTagCompound nbttagcompound) {
        super.b(nbttagcompound);
        FURNACE_NBT_CODEC_BEHAVIOUR.writeState(nbttagcompound, this.items, this.burnTime, this.cookTime);
    }

    public int getMaxStackSize() {
        return 64;
    }

    public boolean isBurning() {
        return FURNACE_LIT_STATE_BEHAVIOUR.isBurning(this.burnTime);
    }

    public void g_() {
        boolean flag = this.burnTime > 0;
        boolean flag1 = false;

        // CraftBukkit start
        int currentTick = FURNACE_TICK_PROGRESSION_BEHAVIOUR.getCurrentTick();
        FurnaceTickProgressionBehaviour.TickDelta tickDelta = FURNACE_TICK_PROGRESSION_BEHAVIOUR.computeTickDelta(this.lastTick, currentTick);
        int elapsedTicks = tickDelta.getElapsedTicks();
        this.lastTick = tickDelta.getUpdatedLastTick();

        // CraftBukkit - moved from below
        FurnaceTickProgressionBehaviour.CookProgress cookProgress = FURNACE_TICK_PROGRESSION_BEHAVIOUR
                .advanceCookProgress(this.isBurning(), this.canBurn(), this.cookTime, elapsedTicks, 200);
        this.cookTime = cookProgress.getCookTime();
        if (cookProgress.shouldBurnOutput()) {
            this.burn();
            flag1 = true;
        }
        // CraftBukkit end

        this.burnTime = FURNACE_TICK_PROGRESSION_BEHAVIOUR.decreaseBurnTime(this.burnTime, elapsedTicks);

        if (!this.world.isStatic) {
            // CraftBukkit start - handle multiple elapsed ticks
            if (FURNACE_REFUEL_FLOW_BEHAVIOUR.shouldAttemptRefuel(this.burnTime, this.canBurn(), this.items[1])) {
                FurnaceEventBridgeBehaviour.BurnDecision burnDecision = FURNACE_EVENT_BRIDGE_BEHAVIOUR.fireBurnEvent(
                        this.world,
                        this.x,
                        this.y,
                        this.z,
                        this.items[1],
                        this.fuelTime(this.items[1])
                );

                if (burnDecision.isCancelled()) {
                    return;
                }

                this.ticksForCurrentFuel = burnDecision.getBurnTime();
                this.burnTime = FURNACE_REFUEL_FLOW_BEHAVIOUR.applyFuelTicks(this.burnTime, this.ticksForCurrentFuel);
                if (FURNACE_REFUEL_FLOW_BEHAVIOUR.shouldConsumeFuel(this.burnTime, burnDecision.isBurning())) {
                    // CraftBukkit end
                    flag1 = true;
                    FURNACE_FUEL_CONSUMPTION_BEHAVIOUR.consumeFuel(this.items, 1);
                }
            }

            /* CraftBukkit start - moved up
            if (this.f() && this.process()) {
                ++this.cookTime;
                if (this.cookTime == 200) {
                    this.cookTime = 0;
                    this.burn();
                    flag1 = true;
                }
            } else {
                this.cookTime = 0;
            }
            // CraftBukkit end */

            if (FURNACE_LIT_STATE_BEHAVIOUR.applyBurningStateTransition(flag, this.burnTime, new FurnaceLitStateBehaviour.BurningStateApplier() {
                public void apply(boolean burning) {
                    BlockFurnace.a(burning, TileEntityFurnace.this.world, TileEntityFurnace.this.x, TileEntityFurnace.this.y, TileEntityFurnace.this.z);
                }
            })) {
                flag1 = true;
            }
        }

        if (flag1) {
            this.update();
        }
    }

    private boolean canBurn() {
        return FURNACE_BURN_ELIGIBILITY_BEHAVIOUR.canBurn(this.items[0], this.items[2], this.getMaxStackSize());
    }

    public void burn() {
        if (this.canBurn()) {
            ItemStack itemstack = FurnaceRecipes.getInstance().a(this.items[0].getItem().id);

            // CraftBukkit start
            FurnaceEventBridgeBehaviour.SmeltDecision smeltDecision = FURNACE_EVENT_BRIDGE_BEHAVIOUR.fireSmeltEvent(
                    this.world,
                    this.x,
                    this.y,
                    this.z,
                    this.items[0],
                    itemstack
            );

            if (smeltDecision.isCancelled()) {
                return;
            }

            itemstack = (ItemStack) (Object) smeltDecision.getResult();

            FURNACE_SMELT_OUTPUT_BEHAVIOUR.applySmeltResult(this.items, 2, itemstack);
            FURNACE_SMELT_OUTPUT_BEHAVIOUR.consumeInput(this.items, 0);
        }
    }

    private int fuelTime(ItemStack itemstack) {
        return FURNACE_FUEL_BURN_TIME_BEHAVIOUR.getBurnTime(itemstack);
    }

    public boolean a_(EntityHuman entityhuman) {
        return this.world.getTileEntity(this.x, this.y, this.z) != this ? false : entityhuman.e((double) this.x + 0.5D, (double) this.y + 0.5D, (double) this.z + 0.5D) <= 64.0D;
    }
}
