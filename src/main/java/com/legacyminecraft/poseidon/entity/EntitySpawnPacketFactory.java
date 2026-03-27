package com.legacyminecraft.poseidon.entity;


/**
 * Canonical spawn packet resolver for tracked entities.
 */
public final class EntitySpawnPacketFactory {
    private static final EntitySpawnPacketFactory INSTANCE = new EntitySpawnPacketFactory();

    private EntitySpawnPacketFactory() {
    }

    public static EntitySpawnPacketFactory getInstance() {
        return INSTANCE;
    }

    public Packet createSpawnPacket(Entity tracker) {
        if (tracker.dead) {
            return null;
        }

        if (tracker instanceof EntityItem) {
            EntityItem entityitem = (EntityItem) tracker;
            return new Packet21PickupSpawn(entityitem);
        } else if (tracker instanceof EntityPlayer) {
            EntityHuman entityHuman = (EntityHuman) tracker;
            entityHuman.name = sanitizePlayerNameForSpawnPacket(entityHuman.name);
            return new Packet20NamedEntitySpawn(entityHuman);
        } else {
            if (tracker instanceof EntityMinecart) {
                EntityMinecart entityminecart = (EntityMinecart) tracker;
                int spawnType = resolveMinecartSpawnType(entityminecart.type);
                if (spawnType != -1) {
                    return new Packet23VehicleSpawn(tracker, spawnType);
                }
            }

            if (tracker instanceof EntityBoat) {
                return new Packet23VehicleSpawn(tracker, 1);
            } else if (tracker instanceof IAnimal) {
                return new Packet24MobSpawn((EntityLiving) tracker);
            } else if (tracker instanceof EntityFish) {
                return new Packet23VehicleSpawn(tracker, 90);
            } else if (tracker instanceof EntityArrow) {
                EntityLiving entityliving = ((EntityArrow) tracker).shooter;
                return new Packet23VehicleSpawn(tracker, 60, entityliving != null ? entityliving.id : tracker.id);
            } else if (tracker instanceof EntitySnowball) {
                return new Packet23VehicleSpawn(tracker, 61);
            } else if (tracker instanceof EntityFireball) {
                EntityFireball entityfireball = (EntityFireball) tracker;
                int shooter = entityfireball.shooter != null ? entityfireball.shooter.id : 1;
                Packet23VehicleSpawn packet23vehiclespawn = new Packet23VehicleSpawn(tracker, 63, shooter);

                packet23vehiclespawn.e = (int) (entityfireball.c * 8000.0D);
                packet23vehiclespawn.f = (int) (entityfireball.d * 8000.0D);
                packet23vehiclespawn.g = (int) (entityfireball.e * 8000.0D);
                return packet23vehiclespawn;
            } else if (tracker instanceof EntityEgg) {
                return new Packet23VehicleSpawn(tracker, 62);
            } else if (tracker instanceof EntityTNTPrimed) {
                return new Packet23VehicleSpawn(tracker, 50);
            } else {
                if (tracker instanceof EntityFallingSand) {
                    EntityFallingSand entityfallingsand = (EntityFallingSand) tracker;
                    int spawnType = resolveFallingSandSpawnType(entityfallingsand.a);
                    if (spawnType != -1) {
                        return new Packet23VehicleSpawn(tracker, spawnType);
                    }
                }

                if (tracker instanceof EntityPainting) {
                    return new Packet25EntityPainting((EntityPainting) tracker);
                } else {
                    throw new IllegalArgumentException("Don\'t know how to add " + tracker.getClass() + "!");
                }
            }
        }
    }

    public String sanitizePlayerNameForSpawnPacket(String originalName) {
        if (originalName == null) {
            return null;
        }
        if (originalName.length() <= 16) {
            return originalName;
        }
        return originalName.substring(0, 16);
    }

    public int resolveMinecartSpawnType(int minecartType) {
        if (minecartType == 0) {
            return 10;
        }
        if (minecartType == 1) {
            return 11;
        }
        if (minecartType == 2) {
            return 12;
        }
        return -1;
    }

    public int resolveFallingSandSpawnType(int blockId) {
        if (blockId == Block.SAND.id) {
            return 70;
        }
        if (blockId == Block.GRAVEL.id) {
            return 71;
        }
        return -1;
    }
}
