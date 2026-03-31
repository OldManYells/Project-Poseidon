package net.minecraft.server;

import org.bukkit.PoseidonConfig;
import org.bukkit.craftbukkit.entity.*;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.util.AxisAlignedBB;

import java.util.List;

public class TileEntityMobSpawner extends TileEntity {

    public int spawnDelay = -1;
    public String mobName = "Pig";
    public double spawnRotation;
    public double previousSpawnRotation = 0.0D;

    private static boolean poseidonAreaLimit = PoseidonConfig.getInstance().getConfigBoolean("world.settings.mob-spawner-area-limit.enable");
    private static int poseidonAreaLimitRadius = PoseidonConfig.getInstance().getConfigInteger("world.settings.mob-spawner-area-limit.limit");
    private static int poseidonChunkRadius = PoseidonConfig.getInstance().getConfigInteger("world.settings.mob-spawner-area-limit.chunk-radius");

    public TileEntityMobSpawner() {
        this.spawnDelay = 20;
    }

    public void setMobName(String mobName) {
        this.mobName = mobName;
    }

    public boolean isActivated() {
        return this.world.a((double) this.x + 0.5D, (double) this.y + 0.5D, (double) this.z + 0.5D, 16.0D) != null;
    }

    public void updateEntity() {
        this.previousSpawnRotation = this.spawnRotation;
        if (this.isActivated()) {
            double particleX = (double) ((float) this.x + this.world.random.nextFloat());
            double particleY = (double) ((float) this.y + this.world.random.nextFloat());
            double particleZ = (double) ((float) this.z + this.world.random.nextFloat());

            this.world.a("smoke", particleX, particleY, particleZ, 0.0D, 0.0D, 0.0D);
            this.world.a("flame", particleX, particleY, particleZ, 0.0D, 0.0D, 0.0D);

            for (this.spawnRotation += (double) (1000.0F / ((float) this.spawnDelay + 200.0F)); this.spawnRotation > 360.0D; this.previousSpawnRotation -= 360.0D) {
                this.spawnRotation -= 360.0D;
            }

            if (!this.world.isStatic) {
                if (this.spawnDelay == -1) {
                    this.resetSpawnDelay();
                }

                if (this.spawnDelay > 0) {
                    --this.spawnDelay;
                    return;
                }

                byte spawnAttempts = 4;

                for (int attempt = 0; attempt < spawnAttempts; ++attempt) {
                    EntityLiving entity = (EntityLiving) ((EntityLiving) EntityTypes.a(this.mobName, this.world));

                    if (entity == null) {
                        return;
                    }

                    boolean isAnimal = entity instanceof EntityAnimal || entity instanceof EntityWaterAnimal;
                    if ((isAnimal && !this.world.allowAnimals) || (!isAnimal && !this.world.allowMonsters)) {
                        return;
                    }

                    int nearbyEntities = this.world.a(entity.getClass(), AxisAlignedBB.b((double) this.x, (double) this.y, (double) this.z, (double) (this.x + 1), (double) (this.y + 1), (double) (this.z + 1)).b(8.0D, 4.0D, 8.0D)).size();
                    if (nearbyEntities >= 6) {
                        this.resetSpawnDelay();
                        return;
                    }

                    if (poseidonAreaLimit) {
                        double chunkSize = 16.0D;
                        AxisAlignedBB searchArea = AxisAlignedBB.b(this.x - poseidonChunkRadius * chunkSize, 0.0D, this.z - poseidonChunkRadius * chunkSize, this.x + poseidonChunkRadius * chunkSize, 128, this.z + poseidonChunkRadius * chunkSize);
                        List existingEntities = this.world.a(entity.getClass(), searchArea);
                        if (existingEntities.size() >= poseidonAreaLimitRadius) {
                            this.resetSpawnDelay();
                            return;
                        }
                    }

                    double spawnX = (double) this.x + (this.world.random.nextDouble() - this.world.random.nextDouble()) * 4.0D;
                    double spawnY = (double) (this.y + this.world.random.nextInt(3) - 1);
                    double spawnZ = (double) this.z + (this.world.random.nextDouble() - this.world.random.nextDouble()) * 4.0D;

                    entity.setPositionRotation(spawnX, spawnY, spawnZ, this.world.random.nextFloat() * 360.0F, 0.0F);
                    if (entity.d()) {
                        this.world.addEntity(entity, SpawnReason.SPAWNER);

                        for (int particle = 0; particle < 20; ++particle) {
                            particleX = (double) this.x + 0.5D + ((double) this.world.random.nextFloat() - 0.5D) * 2.0D;
                            particleY = (double) this.y + 0.5D + ((double) this.world.random.nextFloat() - 0.5D) * 2.0D;
                            particleZ = (double) this.z + 0.5D + ((double) this.world.random.nextFloat() - 0.5D) * 2.0D;
                            this.world.a("smoke", particleX, particleY, particleZ, 0.0D, 0.0D, 0.0D);
                            this.world.a("flame", particleX, particleY, particleZ, 0.0D, 0.0D, 0.0D);
                        }

                        entity.S();
                        this.resetSpawnDelay();
                    }
                }
            }

            super.updateEntity();
        }
    }

    private void resetSpawnDelay() {
        this.spawnDelay = 200 + this.world.random.nextInt(600);
    }

    public void readFromNBT(NBTTagCompound tag) {
        super.readFromNBT(tag);
        this.mobName = tag.getString("EntityId");
        this.spawnDelay = tag.getShort("Delay");
    }

    public void writeToNBT(NBTTagCompound tag) {
        super.writeToNBT(tag);
        tag.setString("EntityId", this.mobName);
        tag.setShort("Delay", (short) this.spawnDelay);
    }

    @Deprecated
    public void a(String mobName) {
        this.setMobName(mobName);
    }

    @Deprecated
    public boolean a() {
        return this.isActivated();
    }

    @Deprecated
    public void g_() {
        this.updateEntity();
    }

    @Deprecated
    public void a(NBTTagCompound tag) {
        this.readFromNBT(tag);
    }

    @Deprecated
    public void b(NBTTagCompound tag) {
        this.writeToNBT(tag);
    }
}
