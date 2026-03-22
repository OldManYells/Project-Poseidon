package com.legacyminecraft.poseidon.entity;

import net.minecraft.server.Entity;
import net.minecraft.server.EntityArrow;
import net.minecraft.server.EntityBoat;
import net.minecraft.server.EntityChicken;
import net.minecraft.server.EntityCow;
import net.minecraft.server.EntityCreeper;
import net.minecraft.server.EntityFallingSand;
import net.minecraft.server.EntityGhast;
import net.minecraft.server.EntityGiantZombie;
import net.minecraft.server.EntityItem;
import net.minecraft.server.EntityLiving;
import net.minecraft.server.EntityMinecart;
import net.minecraft.server.EntityMonster;
import net.minecraft.server.EntityPainting;
import net.minecraft.server.EntityPig;
import net.minecraft.server.EntityPigZombie;
import net.minecraft.server.EntitySheep;
import net.minecraft.server.EntitySkeleton;
import net.minecraft.server.EntitySlime;
import net.minecraft.server.EntitySnowball;
import net.minecraft.server.EntitySpider;
import net.minecraft.server.EntitySquid;
import net.minecraft.server.EntityTNTPrimed;
import net.minecraft.server.EntityWolf;
import net.minecraft.server.EntityZombie;
import net.minecraft.server.NBTTagCompound;
import net.minecraft.server.World;

import java.util.Map;

public final class EntityTypeRegistryBehaviour {
    private static final EntityTypeRegistryBehaviour INSTANCE = new EntityTypeRegistryBehaviour();

    private EntityTypeRegistryBehaviour() {
    }

    public static EntityTypeRegistryBehaviour getInstance() {
        return INSTANCE;
    }

    public void register(Map nameToClass, Map classToName, Map idToClass, Map classToId, Class entityClass, String name, int id) {
        nameToClass.put(name, entityClass);
        classToName.put(entityClass, name);
        idToClass.put(Integer.valueOf(id), entityClass);
        classToId.put(entityClass, Integer.valueOf(id));
    }

    public Entity createByName(Map nameToClass, String name, World world) {
        Entity entity = null;

        try {
            Class entityClass = (Class) nameToClass.get(name);

            if (entityClass != null) {
                entity = (Entity) entityClass.getConstructor(new Class[] { World.class}).newInstance(new Object[] { world});
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        return entity;
    }

    public Entity createFromNbt(Map nameToClass, NBTTagCompound nbt, World world) {
        Entity entity = this.createByName(nameToClass, nbt.getString("id"), world);

        if (entity != null) {
            entity.e(nbt);
        } else {
            System.out.println("Skipping Entity with id " + nbt.getString("id"));
        }

        return entity;
    }

    public int idFor(Map classToId, Entity entity) {
        return ((Integer) classToId.get(entity.getClass())).intValue();
    }

    public String nameFor(Map classToName, Entity entity) {
        return (String) classToName.get(entity.getClass());
    }

    public void bootstrapDefaultRegistrations(Map nameToClass, Map classToName, Map idToClass, Map classToId) {
        this.register(nameToClass, classToName, idToClass, classToId, EntityArrow.class, "Arrow", 10);
        this.register(nameToClass, classToName, idToClass, classToId, EntitySnowball.class, "Snowball", 11);
        this.register(nameToClass, classToName, idToClass, classToId, EntityItem.class, "Item", 1);
        this.register(nameToClass, classToName, idToClass, classToId, EntityPainting.class, "Painting", 9);
        this.register(nameToClass, classToName, idToClass, classToId, EntityLiving.class, "Mob", 48);
        this.register(nameToClass, classToName, idToClass, classToId, EntityMonster.class, "Monster", 49);
        this.register(nameToClass, classToName, idToClass, classToId, EntityCreeper.class, "Creeper", 50);
        this.register(nameToClass, classToName, idToClass, classToId, EntitySkeleton.class, "Skeleton", 51);
        this.register(nameToClass, classToName, idToClass, classToId, EntitySpider.class, "Spider", 52);
        this.register(nameToClass, classToName, idToClass, classToId, EntityGiantZombie.class, "Giant", 53);
        this.register(nameToClass, classToName, idToClass, classToId, EntityZombie.class, "Zombie", 54);
        this.register(nameToClass, classToName, idToClass, classToId, EntitySlime.class, "Slime", 55);
        this.register(nameToClass, classToName, idToClass, classToId, EntityGhast.class, "Ghast", 56);
        this.register(nameToClass, classToName, idToClass, classToId, EntityPigZombie.class, "PigZombie", 57);
        this.register(nameToClass, classToName, idToClass, classToId, EntityPig.class, "Pig", 90);
        this.register(nameToClass, classToName, idToClass, classToId, EntitySheep.class, "Sheep", 91);
        this.register(nameToClass, classToName, idToClass, classToId, EntityCow.class, "Cow", 92);
        this.register(nameToClass, classToName, idToClass, classToId, EntityChicken.class, "Chicken", 93);
        this.register(nameToClass, classToName, idToClass, classToId, EntitySquid.class, "Squid", 94);
        this.register(nameToClass, classToName, idToClass, classToId, EntityWolf.class, "Wolf", 95);
        this.register(nameToClass, classToName, idToClass, classToId, EntityTNTPrimed.class, "PrimedTnt", 20);
        this.register(nameToClass, classToName, idToClass, classToId, EntityFallingSand.class, "FallingSand", 21);
        this.register(nameToClass, classToName, idToClass, classToId, EntityMinecart.class, "Minecart", 40);
        this.register(nameToClass, classToName, idToClass, classToId, EntityBoat.class, "Boat", 41);
    }
}
