package net.minecraft.server;

import com.legacyminecraft.poseidon.entity.EntityTrackerSystem;

import java.util.HashSet;
import java.util.Set;

public class EntityTracker {

    private Set a = new HashSet();
    public EntityList b = new EntityList(); //Project Poseidon: private -> public
    private MinecraftServer c;
    private int d;
    private int e;
    private final EntityTrackerSystem trackerSystem = EntityTrackerSystem.getInstance();

    public EntityTracker(MinecraftServer minecraftserver, int i) {
        this.c = minecraftserver;
        this.e = i;
        this.d = minecraftserver.serverConfigurationManager.a();
    }

    // CraftBukkit - synchronized
    public synchronized void track(Entity entity) {
        trackerSystem.trackEntity(entity, this.d, this.a, this.b, this.c.getWorldServer(this.e).players);
    }

    public void a(Entity entity, int i, int j) {
        this.a(entity, i, j, false);
    }

    // CraftBukkit - synchronized
    public synchronized void a(Entity entity, int i, int j, boolean flag) {
        trackerSystem.registerEntity(this.a, this.b, entity, i, this.d, j, flag, this.c.getWorldServer(this.e).players);
    }

    // CraftBukkit - synchronized
    public synchronized void untrackEntity(Entity entity) {
        trackerSystem.untrackEntity(this.a, this.b, entity);
    }

    // CraftBukkit - synchronized
    public synchronized void updatePlayers() {
        trackerSystem.updatePlayers(this.a, this.c.getWorldServer(this.e).players);
    }

    // CraftBukkit - synchronized
    public synchronized void a(Entity entity, Packet packet) {
        trackerSystem.sendPacketToTracked(this.b, entity, packet);
    }

    // CraftBukkit - synchronized
    public synchronized void sendPacketToEntity(Entity entity, Packet packet) {
        trackerSystem.sendPacketToTrackedAndSelf(this.b, entity, packet);
    }

    // CraftBukkit - synchronized
    public synchronized void untrackPlayer(EntityPlayer entityplayer) {
        trackerSystem.untrackPlayer(this.a, entityplayer);
    }
    
    // Poseidon
    // CraftBukkit - synchronized
    public synchronized void a(EntityPlayer entityplayer, Chunk chunk) {
        trackerSystem.onPlayerChunkLoad(this.a, entityplayer, chunk);
    }
}
