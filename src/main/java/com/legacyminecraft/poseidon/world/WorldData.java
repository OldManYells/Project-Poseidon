package com.legacyminecraft.poseidon.world;

/**
 * World-local metadata state scaffold.
 */
public class WorldData {
    public String name = "world";

    public WorldData() {
    }

    public WorldData(NBTTagCompound dataTag) {
        WorldDataStateBehaviour.getInstance().readFromTag(this, dataTag);
    }

    private long seed;
    private int spawnX;
    private int spawnY;
    private int spawnZ;
    private float spawnYaw;
    private float spawnPitch;
    private long time;
    private long sizeOnDisk;
    private long lastPlayed;
    private int version;
    private int weatherDuration;
    private boolean storm;
    private int thunderDuration;
    private boolean thundering;
    private NBTTagCompound cachedPlayerData;

    public void poseidonSetSeed(long seed) {
        this.seed = seed;
    }

    public long getSeed() {
        return seed;
    }

    public void setSpawn(int spawnX, int spawnY, int spawnZ, float spawnYaw, float spawnPitch) {
        this.spawnX = spawnX;
        this.spawnY = spawnY;
        this.spawnZ = spawnZ;
        this.spawnYaw = spawnYaw;
        this.spawnPitch = spawnPitch;
    }

    public int c() {
        return spawnX;
    }

    public int d() {
        return spawnY;
    }

    public int e() {
        return spawnZ;
    }

    public float getYaw() {
        return spawnYaw;
    }

    public float getPitch() {
        return spawnPitch;
    }

    public void a(long time) {
        this.time = time;
    }

    public long f() {
        return time;
    }

    public void poseidonSetLastPlayed(long lastPlayed) {
        this.lastPlayed = lastPlayed;
    }

    public long getLastPlayed() {
        return lastPlayed;
    }

    public void b(long sizeOnDisk) {
        this.sizeOnDisk = sizeOnDisk;
    }

    public long g() {
        return sizeOnDisk;
    }

    public void a(String name) {
        this.name = name;
    }

    public void a(int version) {
        this.version = version;
    }

    public int i() {
        return version;
    }

    public void setWeatherDuration(int weatherDuration) {
        this.weatherDuration = weatherDuration;
    }

    public int getWeatherDuration() {
        return weatherDuration;
    }

    public void setStorm(boolean storm) {
        this.storm = storm;
    }

    public boolean hasStorm() {
        return storm;
    }

    public void setThunderDuration(int thunderDuration) {
        this.thunderDuration = thunderDuration;
    }

    public int getThunderDuration() {
        return thunderDuration;
    }

    public void setThundering(boolean thundering) {
        this.thundering = thundering;
    }

    public boolean isThundering() {
        return thundering;
    }

    public void poseidonSetCachedPlayerData(NBTTagCompound cachedPlayerData) {
        this.cachedPlayerData = cachedPlayerData;
    }

    public NBTTagCompound poseidonGetCachedPlayerData() {
        return cachedPlayerData;
    }

    public NBTTagCompound a() {
        return WorldDataStateBehaviour.getInstance().createSaveTag(this);
    }

    public NBTTagCompound a(java.util.List players) {
        return WorldDataStateBehaviour.getInstance().createSaveTagWithFirstPlayer(this, players);
    }
}
