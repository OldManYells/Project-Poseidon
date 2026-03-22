package org.bukkit.craftbukkit.entity;

import com.projectposeidon.ConnectionType;
import com.legacyminecraft.poseidon.compat.bukkit.PlayerEyeHeightPolicy;
import com.legacyminecraft.poseidon.compat.bukkit.PlayerIdentityBridgeBehaviour;
import com.legacyminecraft.poseidon.compat.bukkit.PlayerOnlineStatusBridgeBehaviour;
import com.legacyminecraft.poseidon.compat.bukkit.PlayerStatisticDispatchBehaviour;
import com.legacyminecraft.poseidon.compat.bukkit.PlayerChunkChangePacketBehaviour;
import com.legacyminecraft.poseidon.compat.bukkit.PlayerLocalEffectPacketBehaviour;
import com.legacyminecraft.poseidon.compat.bukkit.PlayerMessagingBridgeBehaviour;
import com.legacyminecraft.poseidon.compat.bukkit.PlayerMapPacketBehaviour;
import com.legacyminecraft.poseidon.compat.bukkit.PlayerModerationListBehaviour;
import com.legacyminecraft.poseidon.compat.bukkit.PlayerOperatorStateBehaviour;
import com.legacyminecraft.poseidon.compat.bukkit.PlayerVisibilityBridgeBehaviour;
import net.minecraft.server.*;
import org.bukkit.Achievement;
import org.bukkit.Material;
import org.bukkit.Statistic;
import org.bukkit.*;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.craftbukkit.CraftWorld;
import org.bukkit.craftbukkit.map.CraftMapView;
import org.bukkit.craftbukkit.map.RenderData;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.map.MapView;

import java.net.InetSocketAddress;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class CraftPlayer extends CraftHumanEntity implements Player {
    private static final PlayerOnlineStatusBridgeBehaviour PLAYER_ONLINE_STATUS_BRIDGE_BEHAVIOUR =
            PlayerOnlineStatusBridgeBehaviour.getInstance();
    private static final PlayerEyeHeightPolicy PLAYER_EYE_HEIGHT_POLICY =
            PlayerEyeHeightPolicy.getInstance();
    private static final PlayerIdentityBridgeBehaviour PLAYER_IDENTITY_BRIDGE_BEHAVIOUR =
            PlayerIdentityBridgeBehaviour.getInstance();
    private static final PlayerStatisticDispatchBehaviour PLAYER_STATISTIC_DISPATCH_BEHAVIOUR =
            PlayerStatisticDispatchBehaviour.getInstance();
    private static final PlayerChunkChangePacketBehaviour PLAYER_CHUNK_CHANGE_PACKET_BEHAVIOUR =
            PlayerChunkChangePacketBehaviour.getInstance();
    private static final PlayerLocalEffectPacketBehaviour PLAYER_LOCAL_EFFECT_PACKET_BEHAVIOUR =
            PlayerLocalEffectPacketBehaviour.getInstance();
    private static final PlayerMessagingBridgeBehaviour PLAYER_MESSAGING_BRIDGE_BEHAVIOUR =
            PlayerMessagingBridgeBehaviour.getInstance();
    private static final PlayerModerationListBehaviour PLAYER_MODERATION_LIST_BEHAVIOUR =
            PlayerModerationListBehaviour.getInstance();
    private static final PlayerOperatorStateBehaviour PLAYER_OPERATOR_STATE_BEHAVIOUR =
            PlayerOperatorStateBehaviour.getInstance();
    private static final PlayerVisibilityBridgeBehaviour PLAYER_VISIBILITY_BRIDGE_BEHAVIOUR =
            PlayerVisibilityBridgeBehaviour.getInstance();
    private static final PlayerMapPacketBehaviour PLAYER_MAP_PACKET_BEHAVIOUR =
            PlayerMapPacketBehaviour.getInstance();
    private Set<UUID> hiddenPlayers = new HashSet<UUID>();

    public CraftPlayer(CraftServer server, EntityPlayer entity) {
        super(server, entity);
    }

    @Override
    public boolean isOp() {
        return PLAYER_OPERATOR_STATE_BEHAVIOUR.isOperator(server.getHandle(), getName());
    }

    @Override
    public void setOp(boolean value) {
        boolean changed = PLAYER_OPERATOR_STATE_BEHAVIOUR.updateOperatorState(
                server.getHandle(), getName(), value, isOp());
        if (!changed) {
            return;
        }

        perm.recalculatePermissions();
    }

    public boolean isPlayer() {
        return true;
    }

    public boolean isOnline() {
        return PLAYER_ONLINE_STATUS_BRIDGE_BEHAVIOUR.isOnline(server.getHandle().players, getName());
    }

    public InetSocketAddress getAddress() {
        return PLAYER_ONLINE_STATUS_BRIDGE_BEHAVIOUR.resolveAddress(getHandle().netServerHandler);
    }

    @Override
    public EntityPlayer getHandle() {
        return (EntityPlayer) entity;
    }

    public double getEyeHeight() {
        return getEyeHeight(false);
    }

    public double getEyeHeight(boolean ignoreSneaking) {
        return PLAYER_EYE_HEIGHT_POLICY.resolveEyeHeight(ignoreSneaking, isSneaking());
    }

    public void setHandle(final EntityPlayer entity) {
        super.setHandle((EntityHuman) entity);
        this.entity = entity;
    }

    public void sendRawMessage(String message) {
        PLAYER_MESSAGING_BRIDGE_BEHAVIOUR.sendRawMessage(getHandle().netServerHandler, message, getName());
    }

    public void sendMessage(String message) {
        this.sendRawMessage(message);
    }

    public String getDisplayName() {
        return getHandle().displayName;
    }

    public void setDisplayName(final String name) {
        getHandle().displayName = name;
    }

    @Override
    public String toString() {
        return "CraftPlayer{" + "name=" + getName() + '}';
    }

    @Override
    public boolean equals(Object obj) {
        return PLAYER_IDENTITY_BRIDGE_BEHAVIOUR.equalsByName(this, obj);
    }

    @Override
    public int hashCode() {
        return PLAYER_IDENTITY_BRIDGE_BEHAVIOUR.hashByName(getName());
    }

    public void kickPlayer(String message) {
        PLAYER_MESSAGING_BRIDGE_BEHAVIOUR.kickIfOnline(isOnline(), getHandle().netServerHandler, message);
    }

    public void setCompassTarget(Location loc) {
        // Do not directly assign here, from the packethandler we'll assign it.
        getHandle().netServerHandler.sendPacket(PLAYER_MAP_PACKET_BEHAVIOUR.createCompassPacket(loc));
    }

    //Project Poseidon Start
    public UUID getUniqueId() {
        //return UUIDPlayerStorage.getInstance().getPlayerUUID(getName());
        return getHandle().playerUUID;
    }
    //Project Poseidon End

    public UUID getPlayerUUID() {
        return getUniqueId();
    }

    public Location getCompassTarget() {
        return getHandle().compassTarget;
    }

    public void chat(String msg) {
        getHandle().netServerHandler.chat(msg);
    }

    public boolean performCommand(String command) {
        return server.dispatchCommand(this, command);
    }

    public void playNote(Location loc, byte instrument, byte note) {
        getHandle().netServerHandler.sendPacket(
                PLAYER_LOCAL_EFFECT_PACKET_BEHAVIOUR.createPlayNotePacket(loc, instrument, note));
    }

    public void playNote(Location loc, Instrument instrument, Note note) {
        getHandle().netServerHandler.sendPacket(
                PLAYER_LOCAL_EFFECT_PACKET_BEHAVIOUR.createPlayNotePacket(loc, instrument.getType(), note.getId()));
    }

    public void playEffect(Location loc, Effect effect, int data) {
        getHandle().netServerHandler.sendPacket(
                PLAYER_LOCAL_EFFECT_PACKET_BEHAVIOUR.createEffectPacket(loc, effect, data));
    }

    public void sendBlockChange(Location loc, Material material, byte data) {
        sendBlockChange(loc, material.getId(), data);
    }

    public void sendBlockChange(Location loc, int material, byte data) {
        Packet53BlockChange packet = PLAYER_LOCAL_EFFECT_PACKET_BEHAVIOUR.createBlockChangePacket(loc, material, data);
        getHandle().netServerHandler.sendPacket(packet);
    }

    public boolean sendChunkChange(Location loc, int sx, int sy, int sz, byte[] data) {
        Packet51MapChunk packet = PLAYER_CHUNK_CHANGE_PACKET_BEHAVIOUR.createChunkChangePacket(loc, sx, sy, sz, data);
        if (packet == null) {
            return false;
        }

        getHandle().netServerHandler.sendPacket(packet);

        return true;
    }

    public void sendMap(MapView map) {
        RenderData renderData = ((CraftMapView) map).render(this);
        List<Packet131> packets = PLAYER_MAP_PACKET_BEHAVIOUR.createMapPackets(map.getId(), renderData);
        for (Packet131 packet : packets) {
            getHandle().netServerHandler.sendPacket(packet);
        }
    }

    @Override
    public boolean teleport(Location location) {
        // From = Players current Location
        Location from = this.getLocation();
        // To = Players new Location if Teleport is Successful
        Location to = location;
        // Create & Call the Teleport Event.
        PlayerTeleportEvent event = new PlayerTeleportEvent((Player) this, from, to);
        server.getPluginManager().callEvent(event);
        // Return False to inform the Plugin that the Teleport was unsuccessful/cancelled.
        if (event.isCancelled() == true) {
            return false;
        }
        // Update the From Location
        from = event.getFrom();
        // Grab the new To Location dependent on whether the event was cancelled.
        to = event.getTo();
        // Grab the To and From World Handles.
        WorldServer fromWorld = ((CraftWorld) from.getWorld()).getHandle();
        WorldServer toWorld = ((CraftWorld) to.getWorld()).getHandle();
        // Grab the EntityPlayer
        EntityPlayer entity = getHandle();

        // Check if the fromWorld and toWorld are the same.
        if (fromWorld == toWorld) {
            entity.netServerHandler.teleport(to);
        } else {
            server.getHandle().moveToWorld(entity, toWorld.dimension, to);
        }
        return true;
    }

    public void setSneaking(boolean sneak) {
        getHandle().setSneak(sneak);
    }

    public boolean isSneaking() {
        return getHandle().isSneaking();
    }

    public void loadData() {
        server.getHandle().playerFileData.b(getHandle());
    }

    public void saveData() {
        server.getHandle().playerFileData.a(getHandle());
    }

    public void updateInventory() {
        getHandle().updateInventory(getHandle().activeContainer);
    }

    public void setSleepingIgnored(boolean isSleeping) {
        getHandle().fauxSleeping = isSleeping;
        ((CraftWorld) getWorld()).getHandle().checkSleepStatus();
    }

    public boolean isSleepingIgnored() {
        return getHandle().fauxSleeping;
    }

    public void awardAchievement(Achievement achievement) {
        sendStatistic(achievement.getId(), 1);
    }

    public void incrementStatistic(Statistic statistic) {
        incrementStatistic(statistic, 1);
    }

    public void incrementStatistic(Statistic statistic, int amount) {
        sendStatistic(statistic.getId(), amount);
    }

    public void incrementStatistic(Statistic statistic, Material material) {
        incrementStatistic(statistic, material, 1);
    }

    public void incrementStatistic(Statistic statistic, Material material, int amount) {
        PLAYER_STATISTIC_DISPATCH_BEHAVIOUR.validateSubStatisticRequest(statistic, material);
        int statisticId = PLAYER_STATISTIC_DISPATCH_BEHAVIOUR.resolveStatisticId(statistic, material);
        sendStatistic(statisticId, amount);
    }

    private void sendStatistic(int id, int amount) {
        PLAYER_STATISTIC_DISPATCH_BEHAVIOUR.sendStatistic(getHandle().netServerHandler, id, amount);
    }

    public void setPlayerTime(long time, boolean relative) {
        getHandle().timeOffset = time;
        getHandle().relativeTime = relative;
    }

    public long getPlayerTimeOffset() {
        return getHandle().timeOffset;
    }

    public long getPlayerTime() {
        return getHandle().getPlayerTime();
    }

    public boolean isPlayerTimeRelative() {
        return getHandle().relativeTime;
    }

    public ConnectionType getConnectionType() {
        return getHandle().netServerHandler.getConnectionType();
    }

    @Override
    public com.legacyminecraft.poseidon.api.network.ConnectionType getCanonicalConnectionType() {
        return getHandle().netServerHandler.getCanonicalConnectionType();
    }

    public boolean hasReceivedPacket0() {
        return getHandle().netServerHandler.isReceivedKeepAlive();
    }

    public boolean isUsingReleaseToBeta() {
        return getHandle().netServerHandler.isUsingReleaseToBeta();
    }

    public void resetPlayerTime() {
        setPlayerTime(0, true);
    }

    public boolean isBanned() {
        return PLAYER_MODERATION_LIST_BEHAVIOUR.isBanned(server.getHandle(), getName());
    }

    public void setBanned(boolean value) {
        PLAYER_MODERATION_LIST_BEHAVIOUR.setBanned(server.getHandle(), getName(), value);
    }

    public boolean isWhitelisted() {
        return PLAYER_MODERATION_LIST_BEHAVIOUR.isWhitelisted(server.getHandle(), getName());
    }

    public void setWhitelisted(boolean value) {
        PLAYER_MODERATION_LIST_BEHAVIOUR.setWhitelisted(server.getHandle(), getName(), value);
    }

    public void hidePlayer(Player player) {
        PLAYER_VISIBILITY_BRIDGE_BEHAVIOUR.hidePlayer(hiddenPlayers, entity, getHandle(), player);
    }

    public void showPlayer(Player player) {
        PLAYER_VISIBILITY_BRIDGE_BEHAVIOUR.showPlayer(hiddenPlayers, entity, getHandle(), player);
    }

    public boolean canSee(Player player) {
        return PLAYER_VISIBILITY_BRIDGE_BEHAVIOUR.canSee(hiddenPlayers, player);
    }

    public void sendPacket(final Player player, final Packet packet) {
        PLAYER_MESSAGING_BRIDGE_BEHAVIOUR.sendPacketIfOnline(player, getHandle().netServerHandler, packet);
    }
}
