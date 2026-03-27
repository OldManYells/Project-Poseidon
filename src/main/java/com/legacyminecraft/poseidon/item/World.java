package com.legacyminecraft.poseidon.item;

import com.legacyminecraft.compat.bukkit.CraftServer;
import com.legacyminecraft.compat.bukkit.CraftWorld;
import com.legacyminecraft.compat.bukkit.WorldServer;

import java.util.Random;

/**
 * Item-local world scaffold.
 */
public class World {
    public final Random random = new Random();
    public boolean isStatic;
    public final WorldProvider worldProvider = new WorldProvider();

    public int getTypeId(int x, int y, int z) {
        return 0;
    }

    public int getData(int x, int y, int z) {
        return 0;
    }

    public boolean isEmpty(int x, int y, int z) {
        return true;
    }

    public boolean e(int x, int y, int z) {
        return false;
    }

    public Material getMaterial(int x, int y, int z) {
        return Material.AIR;
    }

    public void setTypeId(int x, int y, int z, int typeId) {
    }

    public void setTypeIdAndData(int x, int y, int z, int typeId, int data) {
    }

    public boolean setRawTypeId(int x, int y, int z, int typeId) {
        return true;
    }

    public boolean setRawTypeIdAndData(int x, int y, int z, int typeId, int data) {
        return true;
    }

    public void setRawData(int x, int y, int z, int data) {
    }

    public boolean a(int blockId, int x, int y, int z, boolean canReplace, int face) {
        return true;
    }

    public void a(EntityHuman source, int effectId, int x, int y, int z, int data) {
    }

    public void a(String particle, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
    }

    public void makeSound(Object source, String sound, float volume, float pitch) {
    }

    public void makeSound(double x, double y, double z, String sound, float volume, float pitch) {
    }

    public void addEntity(Object entity) {
    }

    public MovingObjectPosition rayTrace(Vec3D start, Vec3D end, boolean includeFluids) {
        return null;
    }

    public CraftServer getServer() {
        return new CraftServer();
    }

    public CraftWorld getWorld() {
        return new CraftWorld();
    }

    public WorldServer toCompatWorldServer() {
        return new WorldServer();
    }

    public static final class WorldProvider {
        public boolean d;
    }
}
