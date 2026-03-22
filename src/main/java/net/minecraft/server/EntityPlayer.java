package net.minecraft.server;

import com.legacyminecraft.poseidon.PoseidonConfig;
import com.legacyminecraft.poseidon.inventory.PlayerContainerSyncBehaviour;
import com.legacyminecraft.poseidon.api.uuid.PoseidonUUID;
import com.legacyminecraft.poseidon.inventory.PlayerWindowLifecycleBehaviour;
import com.legacyminecraft.poseidon.world.player.PlayerAttachmentSyncSystem;
import com.legacyminecraft.poseidon.world.player.PlayerChunkSyncCoordinator;
import com.legacyminecraft.poseidon.world.player.PlayerCollectionPacketSystem;
import com.legacyminecraft.poseidon.world.player.PlayerDamagePolicySystem;
import com.legacyminecraft.poseidon.world.player.PlayerDeathHandlingSystem;
import com.legacyminecraft.poseidon.world.player.PlayerEquipmentSyncSystem;
import com.legacyminecraft.poseidon.world.player.PlayerSessionTickSystem;
import com.legacyminecraft.poseidon.world.player.PlayerSleepSynchronizationSystem;
import com.legacyminecraft.poseidon.world.player.PlayerStatisticPacketSystem;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.CraftWorld;
import org.bukkit.event.entity.EntityRegainHealthEvent.RegainReason;

import java.util.*;

// CraftBukkit start

public class EntityPlayer extends EntityHuman implements ICrafting {
    private static final PlayerChunkSyncCoordinator chunkSyncCoordinator = PlayerChunkSyncCoordinator.getInstance();
    private static final PlayerCollectionPacketSystem playerCollectionPacketService = PlayerCollectionPacketSystem.getInstance();
    private static final PlayerDamagePolicySystem playerDamagePolicyService = PlayerDamagePolicySystem.getInstance();
    private static final PlayerDeathHandlingSystem playerDeathHandlingService = PlayerDeathHandlingSystem.getInstance();
    private static final PlayerEquipmentSyncSystem playerEquipmentSyncService = PlayerEquipmentSyncSystem.getInstance();
    private static final PlayerAttachmentSyncSystem playerAttachmentSyncService = PlayerAttachmentSyncSystem.getInstance();
    private static final PlayerContainerSyncBehaviour playerContainerSyncService = PlayerContainerSyncBehaviour.getInstance();
    private static final PlayerSessionTickSystem playerSessionTickService = PlayerSessionTickSystem.getInstance();
    private static final PlayerSleepSynchronizationSystem playerSleepSynchronizationService = PlayerSleepSynchronizationSystem.getInstance();
    private static final PlayerStatisticPacketSystem playerStatisticPacketService = PlayerStatisticPacketSystem.getInstance();
    private static final PlayerWindowLifecycleBehaviour playerWindowLifecycleService = PlayerWindowLifecycleBehaviour.getInstance();

    public NetServerHandler netServerHandler;
    public MinecraftServer b;
    public ItemInWorldManager itemInWorldManager;
    public double d;
    public double e;
    public List chunkCoordIntPairQueue = new LinkedList();
    public Set playerChunkCoordIntPairs = new HashSet();
    public final List removeQueue = new LinkedList(); // poseidon
    private int bL = -99999999;
    private int bM = 60;
    private ItemStack[] bN = new ItemStack[]{null, null, null, null, null};
    private int bO = 0;
    public boolean h;

    public EntityPlayer(MinecraftServer minecraftserver, World world, String s, ItemInWorldManager iteminworldmanager) {
        super(world);
        iteminworldmanager.player = this;
        this.itemInWorldManager = iteminworldmanager;
        ChunkCoordinates chunkcoordinates = world.getSpawn();
        int i = chunkcoordinates.x;
        int j = chunkcoordinates.z;
        int k = chunkcoordinates.y;
        float yaw = world.worldData.getYaw(); // Poseidon
        float pitch = world.worldData.getPitch(); // Poseidon

        if (!world.worldProvider.e) {
            k = world.f(i, j); //Project Poseidon: This finds a solid block, this needs to be left outside of the setting
            if ((boolean) PoseidonConfig.getInstance().getProperty("world-settings.randomize-spawn")) { //Project Poseidon: Moved randomizing X and Y axis into a config option
                i += this.random.nextInt(20) - 10;
                j += this.random.nextInt(20) - 10;
            }
        }

        this.setPositionRotation((double) i + 0.5D, (double) k, (double) j + 0.5D, yaw, pitch);
        this.b = minecraftserver;
        this.bs = 0.0F;
        this.name = s;
        this.height = 0.0F;

        // CraftBukkit start
        this.displayName = this.name;
        this.playerUUID = PoseidonUUID.getPlayerGracefulUUID(this.name); //Project Poseidon
    }

    public String displayName;
    public UUID playerUUID; //Project Poseidon
    public org.bukkit.Location compassTarget;
    // CraftBukkit end

    public void spawnIn(World world) {
        super.spawnIn(world);
        // CraftBukkit - world fallback code, either respawn location or global spawn
        if (world == null) {
            this.dead = false;
            ChunkCoordinates position = null;
            if (this.spawnWorld != null && !this.spawnWorld.equals("")) {
                CraftWorld cworld = (CraftWorld) Bukkit.getServer().getWorld(this.spawnWorld);
                if (cworld != null && this.getBed() != null) {
                    world = cworld.getHandle();
                    position = EntityHuman.getBed(cworld.getHandle(), this.getBed());
                }
            }
            if (world == null || position == null) {
                world = ((CraftWorld) Bukkit.getServer().getWorlds().get(0)).getHandle();
                position = world.getSpawn();
            }
            this.world = world;
            this.setPosition(position.x + 0.5, position.y, position.z + 0.5);
        }
        this.dimension = ((WorldServer) this.world).dimension;
        // CraftBukkit end
        this.itemInWorldManager = new ItemInWorldManager((WorldServer) world);
        this.itemInWorldManager.player = this;
    }

    public void syncInventory() {
        this.activeContainer.a((ICrafting) this);
    }

    public ItemStack[] getEquipment() {
        return this.bN;
    }

    protected void s() {
        this.height = 0.0F;
    }

    public float t() {
        return 1.62F;
    }

    public void m_() {
        this.itemInWorldManager.a();
        --this.bM;
        this.activeContainer.a();
        playerEquipmentSyncService.syncTrackedEquipment(this, this.bN);
    }

    public ItemStack c_(int i) {
        return i == 0 ? this.inventory.getItemInHand() : this.inventory.armor[i - 1];
    }

    public void die(Entity entity) {
        playerDeathHandlingService.handleDeath(this);
    }

    public boolean damageEntity(Entity entity, int i) {
        boolean attackerIsHuman = entity instanceof EntityHuman;
        boolean arrowShotByHuman = false;
        if (entity instanceof EntityArrow) {
            EntityArrow entityarrow = (EntityArrow) entity;
            arrowShotByHuman = entityarrow.shooter instanceof EntityHuman;
        }

        if (!playerDamagePolicyService.shouldApplyDamage(this.bM, this.world.pvpMode, attackerIsHuman, arrowShotByHuman)) {
            return false;
        }

        return super.damageEntity(entity, i);
    }

    protected boolean j_() {
        return this.b.pvpMode;
    }

    public void b(int i) {
        super.b(i, RegainReason.EATING);
    }

    public WorldServer getWorldServer() {
        return (WorldServer) this.world;
    }
    
    public void a(boolean flag) {
        super.m_();
        chunkSyncCoordinator.flushEntityRemovalQueue(this);
        chunkSyncCoordinator.sendMapUpdatePackets(this);
        chunkSyncCoordinator.processChunkSendQueue(this, flag);

        PlayerSessionTickSystem.PortalTickDecision portalTickDecision =
                playerSessionTickService.evaluatePortalTick(
                        this.E,
                        this.F,
                        this.D,
                        this.vehicle != null,
                        this.activeContainer != this.defaultContainer
                );
        if (portalTickDecision.shouldCloseContainer()) {
            this.y();
        }
        if (portalTickDecision.shouldRemountVehicle()) {
            this.mount(this.vehicle);
        }
        if (portalTickDecision.shouldTriggerWorldTransfer()) {
            this.b.serverConfigurationManager.f(this);
        }
        this.E = portalTickDecision.isPortalTriggered();
        this.F = portalTickDecision.getPortalProgress();
        this.D = portalTickDecision.getPortalCooldown();

        PlayerSessionTickSystem.HealthSyncDecision healthSyncDecision =
                playerSessionTickService.evaluateHealthSync(this.health, this.bL);
        if (healthSyncDecision.shouldSendHealthPacket()) {
            this.netServerHandler.sendPacket(new Packet8UpdateHealth(this.health));
            this.bL = healthSyncDecision.getNextReportedHealth();
        }
    }

    public void v() {
        super.v();
    }

    public void receive(Entity entity, int i) {
        playerCollectionPacketService.sendCollectPacketIfNeeded(this, entity);
        super.receive(entity, i);
        playerCollectionPacketService.refreshActiveContainer(this);
    }

    public void w() {
        playerSleepSynchronizationService.sendSleepStartAnimationIfNeeded(this);
    }

    public void x() {
    }

    public EnumBedError a(int i, int j, int k) {
        EnumBedError enumbederror = super.a(i, j, k);
        playerSleepSynchronizationService.syncBedEnterResult(this, enumbederror, i, j, k);
        return enumbederror;
    }

    public void a(boolean flag, boolean flag1, boolean flag2) {
        playerSleepSynchronizationService.sendWakeAnimationIfSleeping(this);
        super.a(flag, flag1, flag2);
        playerSleepSynchronizationService.syncPositionIfConnected(this);
    }

    public void mount(Entity entity) {
        // CraftBukkit start
        this.setPassengerOf(entity);
    }

    public void setPassengerOf(Entity entity) {
        // mount(null) doesn't really fly for overloaded methods,
        // so this method is needed

        super.setPassengerOf(entity);
        // CraftBukkit end

        playerAttachmentSyncService.syncPassengerAttachment(this);
    }

    protected void a(double d0, boolean flag) {
    }

    public void b(double d0, boolean flag) {
        super.a(d0, flag);
    }

    private void ai() {
        this.bO = playerWindowLifecycleService.nextWindowId(this.bO);
    }

    public void b(int i, int j, int k) {
        this.ai();
        playerWindowLifecycleService.openWorkbenchWindow(this, this.bO, i, j, k);
    }

    public void a(IInventory iinventory) {
        this.ai();

        if (!playerWindowLifecycleService.fireChestOpenedEvent(this, iinventory)) return;
        playerWindowLifecycleService.openChestWindow(this, this.bO, iinventory);
    }

    public void a(TileEntityFurnace tileentityfurnace) {
        this.ai();
        playerWindowLifecycleService.openFurnaceWindow(this, this.bO, tileentityfurnace);
    }

    public void a(TileEntityDispenser tileentitydispenser) {
        this.ai();
        playerWindowLifecycleService.openDispenserWindow(this, this.bO, tileentitydispenser);
    }

    public void a(Container container, int i, ItemStack itemstack) {
        if (!playerContainerSyncService.shouldSendSlotUpdate(container.b(i) instanceof SlotResult, this.h)) return;
        playerContainerSyncService.sendSlotUpdate(this, container, i, itemstack);
    }

    public void updateInventory(Container container) {
        this.a(container, container.b());
    }

    public void a(Container container, List list) {
        playerContainerSyncService.sendWindowItems(this, container, list);
    }

    public void a(Container container, int i, int j) {
        playerContainerSyncService.sendProgressUpdate(this, container, i, j);
    }

    public void a(ItemStack itemstack) {
    }

    public void y() {
        playerWindowLifecycleService.closeActiveWindow(this);
    }

    public void z() {
        playerWindowLifecycleService.sendCarriedItemIfNeeded(this);
    }

    public void A() {
        playerWindowLifecycleService.resetActiveContainer(this);
    }

    public void a(float f, float f1, boolean flag, boolean flag1, float f2, float f3) {
        this.az = f;
        this.aA = f1;
        this.aC = flag;
        this.setSneak(flag1);
        this.pitch = f2;
        this.yaw = f3;
    }

    public void a(Statistic statistic, int i) {
        playerStatisticPacketService.sendStatisticPackets(this, statistic, i);
    }

    public void B() {
        playerAttachmentSyncService.restoreSessionState(this);
    }

    public void C() {
        this.bL = -99999999;
    }

    public void a(String s) {
        StatisticStorage statisticstorage = StatisticStorage.a();
        String s1 = statisticstorage.a(s);

        this.netServerHandler.sendPacket(new Packet3Chat(s1));
    }

    // CraftBukkit start
    public long timeOffset = 0;
    public boolean relativeTime = true;

    public long getPlayerTime() {
        if (this.relativeTime) {
            // Adds timeOffset to the current server time.
            return this.world.getTime() + this.timeOffset;
        } else {
            // Adds timeOffset to the beginning of this day.
            return this.world.getTime() - (this.world.getTime() % 24000) + this.timeOffset;
        }
    }

    @Override
    public String toString() {
        return super.toString() + "(" + this.name + " at " + this.locX + "," + this.locY + "," + this.locZ + ")";
    }
    // CraftBukkit end
}
