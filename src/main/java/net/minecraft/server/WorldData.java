package net.minecraft.server;

import com.legacyminecraft.poseidon.world.WorldDataStateBehaviour;

import java.util.List;

public class WorldData {
    private static final WorldDataStateBehaviour WORLD_DATA_STATE_BEHAVIOUR = WorldDataStateBehaviour.getInstance();

    private long a;
    private int b;
    private int c;
    private int d;
    private float yaw; // Poseidon
    private float pitch; // Poseidon
    private long e;
    private long f;
    private long g;
    private NBTTagCompound h;
    private int i;
    public String name; // CraftBukkit - private -> public
    private int k;
    private boolean l;
    private int m;
    private boolean n;
    private int o;

    public WorldData(NBTTagCompound nbttagcompound) {
        WORLD_DATA_STATE_BEHAVIOUR.readFromTag(this, nbttagcompound);
    }

    public WorldData(long i, String s) {
        this.a = i;
        this.name = s;
    }

    public WorldData(WorldData worlddata) {
        this.a = worlddata.a;
        this.b = worlddata.b;
        this.c = worlddata.c;
        this.d = worlddata.d;
        this.yaw = worlddata.yaw; // Poseidon
        this.pitch = worlddata.pitch; // Poseidon
        this.e = worlddata.e;
        this.f = worlddata.f;
        this.g = worlddata.g;
        this.h = worlddata.h;
        this.i = worlddata.i;
        this.name = worlddata.name;
        this.k = worlddata.k;
        this.m = worlddata.m;
        this.l = worlddata.l;
        this.o = worlddata.o;
        this.n = worlddata.n;
    }

    public NBTTagCompound a() {
        return WORLD_DATA_STATE_BEHAVIOUR.createSaveTag(this);
    }

    public NBTTagCompound a(List list) {
        return WORLD_DATA_STATE_BEHAVIOUR.createSaveTagWithFirstPlayer(this, list);
    }

    public long getSeed() {
        return this.a;
    }

    public int c() {
        return this.b;
    }

    public int d() {
        return this.c;
    }

    public int e() {
        return this.d;
    }

    // Poseidon start
    public float getYaw() {
        return this.yaw;
    }

    public float getPitch() {
        return this.pitch;
    }

    // Poseidon end

    public long f() {
        return this.e;
    }

    public long g() {
        return this.g;
    }

    public int h() {
        return this.i;
    }

    public void a(long i) {
        this.e = i;
    }

    public void b(long i) {
        this.g = i;
    }

    public void setSpawn(int i, int j, int k) {
        this.b = i;
        this.c = j;
        this.d = k;
    }

    // Poseidon start
    public void setSpawn(int i, int j, int k, float yaw, float pitch) {
        this.b = i;
        this.c = j;
        this.d = k;
        this.yaw = yaw;
        this.pitch = pitch;
    }

    // Poseidon end

    public void a(String s) {
        this.name = s;
    }

    public int i() {
        return this.k;
    }

    public void a(int i) {
        this.k = i;
    }

    public boolean isThundering() {
        return this.n;
    }

    public void setThundering(boolean flag) {
        this.n = flag;
    }

    public int getThunderDuration() {
        return this.o;
    }

    public void setThunderDuration(int i) {
        this.o = i;
    }

    public boolean hasStorm() {
        return this.l;
    }

    public void setStorm(boolean flag) {
        this.l = flag;
    }

    public int getWeatherDuration() {
        return this.m;
    }

    public void setWeatherDuration(int i) {
        this.m = i;
    }

    public void poseidonSetSeed(long seed) {
        this.a = seed;
    }

    public void poseidonSetLastPlayed(long lastPlayed) {
        this.f = lastPlayed;
    }

    public void poseidonSetCachedPlayerData(NBTTagCompound playerTag) {
        this.h = playerTag;
        if (playerTag != null) {
            this.i = playerTag.e("Dimension");
        }
    }

    public NBTTagCompound poseidonGetCachedPlayerData() {
        return this.h;
    }
}
