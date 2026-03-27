package org.bukkit.craftbukkit.entity;

import com.projectposeidon.ConnectionType;
import com.legacyminecraft.compat.bukkit.PlayerEyeHeightPolicy;
import com.legacyminecraft.compat.bukkit.PlayerIdentityBridgeBehaviour;
import com.legacyminecraft.compat.bukkit.PlayerActionDispatchBehaviour;
import com.legacyminecraft.compat.bukkit.PlayerOnlineStatusBridgeBehaviour;
import com.legacyminecraft.compat.bukkit.PlayerProfileBridgeBehaviour;
import com.legacyminecraft.compat.bukkit.PlayerStatisticDispatchBehaviour;
import com.legacyminecraft.compat.bukkit.PlayerTeleportBridgeBehaviour;
import com.legacyminecraft.compat.bukkit.PlayerChunkChangePacketBehaviour;
import com.legacyminecraft.compat.bukkit.EntityWrapperDescriptionBehaviour;
import com.legacyminecraft.compat.bukkit.PlayerLocalEffectPacketBehaviour;
import com.legacyminecraft.compat.bukkit.PlayerMessagingBridgeBehaviour;
import com.legacyminecraft.compat.bukkit.PlayerMapPacketBehaviour;
import com.legacyminecraft.compat.bukkit.PlayerModerationListBehaviour;
import com.legacyminecraft.compat.bukkit.PlayerOperatorStateBehaviour;
import com.legacyminecraft.compat.bukkit.PlayerStateBridgeBehaviour;
import com.legacyminecraft.compat.bukkit.PlayerVisibilityBridgeBehaviour;
import com.legacyminecraft.compat.bukkit.PlayerContextBridgeBehaviour;
import com.legacyminecraft.compat.bukkit.PlayerServerContextBridgeBehaviour;
import com.legacyminecraft.compat.bukkit.PlayerConnectionContextBridgeBehaviour;
import com.legacyminecraft.compat.bukkit.EntityTypedHandleCastBehaviour;
import com.legacyminecraft.compat.bukkit.EntityHandleMutationBehaviour;
import net.minecraft.server.*;
import org.bukkit.Achievement;
import org.bukkit.Material;
import org.bukkit.Statistic;
import org.bukkit.*;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.entity.Player;
import org.bukkit.map.MapView;

import java.net.InetSocketAddress;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class CraftPlayer extends CraftHumanEntity implements Player {
    private static final PlayerOnlineStatusBridgeBehaviour PLAYER_ONLINE_STATUS_BRIDGE_BEHAVIOUR =
            PlayerOnlineStatusBridgeBehaviour.getInstance();
    private static final PlayerProfileBridgeBehaviour PLAYER_PROFILE_BRIDGE_BEHAVIOUR =
            PlayerProfileBridgeBehaviour.getInstance();
    private static final PlayerActionDispatchBehaviour PLAYER_ACTION_DISPATCH_BEHAVIOUR =
            PlayerActionDispatchBehaviour.getInstance();
    private static final PlayerEyeHeightPolicy PLAYER_EYE_HEIGHT_POLICY =
            PlayerEyeHeightPolicy.getInstance();
    private static final PlayerIdentityBridgeBehaviour PLAYER_IDENTITY_BRIDGE_BEHAVIOUR =
            PlayerIdentityBridgeBehaviour.getInstance();
    private static final PlayerStatisticDispatchBehaviour PLAYER_STATISTIC_DISPATCH_BEHAVIOUR =
            PlayerStatisticDispatchBehaviour.getInstance();
    private static final PlayerTeleportBridgeBehaviour PLAYER_TELEPORT_BRIDGE_BEHAVIOUR =
            PlayerTeleportBridgeBehaviour.getInstance();
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
    private static final PlayerStateBridgeBehaviour PLAYER_STATE_BRIDGE_BEHAVIOUR =
            PlayerStateBridgeBehaviour.getInstance();
    private static final PlayerVisibilityBridgeBehaviour PLAYER_VISIBILITY_BRIDGE_BEHAVIOUR =
            PlayerVisibilityBridgeBehaviour.getInstance();
    private static final PlayerMapPacketBehaviour PLAYER_MAP_PACKET_BEHAVIOUR =
            PlayerMapPacketBehaviour.getInstance();
    private static final PlayerContextBridgeBehaviour PLAYER_CONTEXT_BRIDGE_BEHAVIOUR =
            PlayerContextBridgeBehaviour.getInstance();
    private static final PlayerServerContextBridgeBehaviour PLAYER_SERVER_CONTEXT_BRIDGE_BEHAVIOUR =
            PlayerServerContextBridgeBehaviour.getInstance();
    private static final PlayerConnectionContextBridgeBehaviour PLAYER_CONNECTION_CONTEXT_BRIDGE_BEHAVIOUR =
            PlayerConnectionContextBridgeBehaviour.getInstance();
    private static final EntityWrapperDescriptionBehaviour ENTITY_WRAPPER_DESCRIPTION_BEHAVIOUR =
            EntityWrapperDescriptionBehaviour.getInstance();
    private static final EntityTypedHandleCastBehaviour ENTITY_TYPED_HANDLE_CAST_BEHAVIOUR =
            EntityTypedHandleCastBehaviour.getInstance();
    private static final EntityHandleMutationBehaviour ENTITY_HANDLE_MUTATION_BEHAVIOUR =
            EntityHandleMutationBehaviour.getInstance();
    private Set<UUID> hiddenPlayers = new HashSet<UUID>();

    public CraftPlayer(CraftServer server, EntityPlayer entity) {
        super(server, entity);
    }

    @Override
    public boolean isOp() {
        return PLAYER_OPERATOR_STATE_BEHAVIOUR.isOperator(
                PLAYER_SERVER_CONTEXT_BRIDGE_BEHAVIOUR.resolveServerHandle(server),
                getName()
        );
    }

    @Override
    public void setOp(boolean value) {
        boolean changed = PLAYER_OPERATOR_STATE_BEHAVIOUR.updateOperatorState(
                PLAYER_SERVER_CONTEXT_BRIDGE_BEHAVIOUR.resolveServerHandle(server),
                getName(),
                value,
                isOp()
        );
        PLAYER_OPERATOR_STATE_BEHAVIOUR.recalculatePermissionsIfChanged(perm, changed);
    }

    public boolean isPlayer() {
        return PLAYER_STATE_BRIDGE_BEHAVIOUR.isPlayer();
    }

    public boolean isOnline() {
        return PLAYER_ONLINE_STATUS_BRIDGE_BEHAVIOUR.isOnline(
                PLAYER_SERVER_CONTEXT_BRIDGE_BEHAVIOUR.resolveOnlinePlayers(server),
                getName()
        );
    }

    public InetSocketAddress getAddress() {
        return PLAYER_ONLINE_STATUS_BRIDGE_BEHAVIOUR.resolveAddress(
                PLAYER_CONNECTION_CONTEXT_BRIDGE_BEHAVIOUR.resolveConnection(getHandle())
        );
    }

    @Override
    public EntityPlayer getHandle() {
        return ENTITY_TYPED_HANDLE_CAST_BEHAVIOUR.castHandle(entity, EntityPlayer.class);
    }

    public double getEyeHeight() {
        return getEyeHeight(false);
    }

    public double getEyeHeight(boolean ignoreSneaking) {
        return PLAYER_EYE_HEIGHT_POLICY.resolveEyeHeight(ignoreSneaking, isSneaking());
    }

    public void setHandle(final EntityPlayer entity) {
        ENTITY_HANDLE_MUTATION_BEHAVIOUR.applyHandle(entity, new EntityHandleMutationBehaviour.HandleMutationCallbacks() {
            @Override
            public void setSuperHandle(Object updatedHandle) {
                CraftPlayer.super.setHandle((EntityHuman) updatedHandle);
            }

            @Override
            public void assignHandleField(Object updatedHandle) {
                CraftPlayer.this.entity = (EntityPlayer) updatedHandle;
            }

            @Override
            public void afterHandleAssignment(Object updatedHandle) {
            }
        });
    }

    public void sendRawMessage(String message) {
        PLAYER_MESSAGING_BRIDGE_BEHAVIOUR.sendRawMessage(
                PLAYER_CONNECTION_CONTEXT_BRIDGE_BEHAVIOUR.resolveConnection(getHandle()),
                message,
                getName()
        );
    }

    public void sendMessage(String message) {
        this.sendRawMessage(message);
    }

    public String getDisplayName() {
        return PLAYER_PROFILE_BRIDGE_BEHAVIOUR.getDisplayName(getHandle());
    }

    public void setDisplayName(final String name) {
        PLAYER_PROFILE_BRIDGE_BEHAVIOUR.setDisplayName(getHandle(), name);
    }

    @Override
    public String toString() {
        return ENTITY_WRAPPER_DESCRIPTION_BEHAVIOUR.craftPlayerToString(getName());
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
        PLAYER_MESSAGING_BRIDGE_BEHAVIOUR.kickIfOnline(
                isOnline(),
                PLAYER_CONNECTION_CONTEXT_BRIDGE_BEHAVIOUR.resolveConnection(getHandle()),
                message
        );
    }

    public void setCompassTarget(Location loc) {
        PLAYER_ACTION_DISPATCH_BEHAVIOUR.setCompassTarget(getHandle(), loc, PLAYER_MAP_PACKET_BEHAVIOUR);
    }

    //Project Poseidon Start
    public UUID getUniqueId() {
        //return UUIDPlayerStorage.getInstance().getPlayerUUID(getName());
        return PLAYER_PROFILE_BRIDGE_BEHAVIOUR.getUniqueId(getHandle());
    }
    //Project Poseidon End

    public UUID getPlayerUUID() {
        return getUniqueId();
    }

    public Location getCompassTarget() {
        return PLAYER_PROFILE_BRIDGE_BEHAVIOUR.getCompassTarget(getHandle());
    }

    public void chat(String msg) {
        PLAYER_ACTION_DISPATCH_BEHAVIOUR.chat(getHandle(), msg);
    }

    public boolean performCommand(String command) {
        return PLAYER_ACTION_DISPATCH_BEHAVIOUR.performCommand(server, this, command);
    }

    public void playNote(Location loc, byte instrument, byte note) {
        PLAYER_ACTION_DISPATCH_BEHAVIOUR.playRawNote(
                getHandle(),
                loc,
                instrument,
                note,
                PLAYER_LOCAL_EFFECT_PACKET_BEHAVIOUR
        );
    }

    public void playNote(Location loc, Instrument instrument, Note note) {
        PLAYER_ACTION_DISPATCH_BEHAVIOUR.playRawNote(
                getHandle(),
                loc,
                instrument.getType(),
                note.getId(),
                PLAYER_LOCAL_EFFECT_PACKET_BEHAVIOUR
        );
    }

    public void playEffect(Location loc, Effect effect, int data) {
        PLAYER_ACTION_DISPATCH_BEHAVIOUR.playEffect(
                getHandle(),
                loc,
                effect,
                data,
                PLAYER_LOCAL_EFFECT_PACKET_BEHAVIOUR
        );
    }

    public void sendBlockChange(Location loc, Material material, byte data) {
        sendBlockChange(loc, material.getId(), data);
    }

    public void sendBlockChange(Location loc, int material, byte data) {
        PLAYER_ACTION_DISPATCH_BEHAVIOUR.sendBlockChange(
                getHandle(),
                loc,
                material,
                data,
                PLAYER_LOCAL_EFFECT_PACKET_BEHAVIOUR
        );
    }

    public boolean sendChunkChange(Location loc, int sx, int sy, int sz, byte[] data) {
        return PLAYER_ACTION_DISPATCH_BEHAVIOUR.sendChunkChange(
                getHandle(),
                loc,
                sx,
                sy,
                sz,
                data,
                PLAYER_CHUNK_CHANGE_PACKET_BEHAVIOUR
        );
    }

    public void sendMap(MapView map) {
        org.bukkit.craftbukkit.map.RenderData renderData = PLAYER_CONTEXT_BRIDGE_BEHAVIOUR.renderMapData(map, this);
        PLAYER_ACTION_DISPATCH_BEHAVIOUR.sendMap(getHandle(), map.getId(), renderData, PLAYER_MAP_PACKET_BEHAVIOUR);
    }

    @Override
    public boolean teleport(Location location) {
        return PLAYER_TELEPORT_BRIDGE_BEHAVIOUR.teleport(server, this, getHandle(), location);
    }

    public void setSneaking(boolean sneak) {
        PLAYER_STATE_BRIDGE_BEHAVIOUR.setSneaking(getHandle(), sneak);
    }

    public boolean isSneaking() {
        return PLAYER_STATE_BRIDGE_BEHAVIOUR.isSneaking(getHandle());
    }

    public void loadData() {
        PLAYER_STATE_BRIDGE_BEHAVIOUR.loadData(
                PLAYER_SERVER_CONTEXT_BRIDGE_BEHAVIOUR.resolveServerHandle(server),
                getHandle()
        );
    }

    public void saveData() {
        PLAYER_STATE_BRIDGE_BEHAVIOUR.saveData(
                PLAYER_SERVER_CONTEXT_BRIDGE_BEHAVIOUR.resolveServerHandle(server),
                getHandle()
        );
    }

    public void updateInventory() {
        PLAYER_STATE_BRIDGE_BEHAVIOUR.updateInventory(getHandle());
    }

    public void setSleepingIgnored(boolean isSleeping) {
        net.minecraft.server.WorldServer worldHandle = PLAYER_CONTEXT_BRIDGE_BEHAVIOUR.resolveWorldHandle(getWorld());
        PLAYER_STATE_BRIDGE_BEHAVIOUR.setSleepingIgnored(getHandle(), isSleeping, worldHandle);
    }

    public boolean isSleepingIgnored() {
        return PLAYER_STATE_BRIDGE_BEHAVIOUR.isSleepingIgnored(getHandle());
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
        PLAYER_STATISTIC_DISPATCH_BEHAVIOUR.sendStatistic(
                PLAYER_CONNECTION_CONTEXT_BRIDGE_BEHAVIOUR.resolveConnection(getHandle()),
                id,
                amount
        );
    }

    public void setPlayerTime(long time, boolean relative) {
        PLAYER_STATE_BRIDGE_BEHAVIOUR.setPlayerTime(getHandle(), time, relative);
    }

    public long getPlayerTimeOffset() {
        return PLAYER_STATE_BRIDGE_BEHAVIOUR.getPlayerTimeOffset(getHandle());
    }

    public long getPlayerTime() {
        return PLAYER_STATE_BRIDGE_BEHAVIOUR.getPlayerTime(getHandle());
    }

    public boolean isPlayerTimeRelative() {
        return PLAYER_STATE_BRIDGE_BEHAVIOUR.isPlayerTimeRelative(getHandle());
    }

    public ConnectionType getConnectionType() {
        return PLAYER_PROFILE_BRIDGE_BEHAVIOUR.getConnectionType(getHandle());
    }

    @Override
    public com.legacyminecraft.poseidon.api.network.ConnectionType getCanonicalConnectionType() {
        return PLAYER_PROFILE_BRIDGE_BEHAVIOUR.getCanonicalConnectionType(getHandle());
    }

    public boolean hasReceivedPacket0() {
        return PLAYER_STATE_BRIDGE_BEHAVIOUR.hasReceivedKeepAlive(getHandle());
    }

    public boolean isUsingReleaseToBeta() {
        return PLAYER_STATE_BRIDGE_BEHAVIOUR.isUsingReleaseToBeta(getHandle());
    }

    public void resetPlayerTime() {
        PLAYER_PROFILE_BRIDGE_BEHAVIOUR.resetPlayerTime(new PlayerProfileBridgeBehaviour.TimeResetCallbacks() {
            @Override
            public void setPlayerTime(long time, boolean relative) {
                CraftPlayer.this.setPlayerTime(time, relative);
            }
        });
    }

    public boolean isBanned() {
        return PLAYER_MODERATION_LIST_BEHAVIOUR.isBanned(
                PLAYER_SERVER_CONTEXT_BRIDGE_BEHAVIOUR.resolveServerHandle(server),
                getName()
        );
    }

    public void setBanned(boolean value) {
        PLAYER_MODERATION_LIST_BEHAVIOUR.setBanned(
                PLAYER_SERVER_CONTEXT_BRIDGE_BEHAVIOUR.resolveServerHandle(server),
                getName(),
                value
        );
    }

    public boolean isWhitelisted() {
        return PLAYER_MODERATION_LIST_BEHAVIOUR.isWhitelisted(
                PLAYER_SERVER_CONTEXT_BRIDGE_BEHAVIOUR.resolveServerHandle(server),
                getName()
        );
    }

    public void setWhitelisted(boolean value) {
        PLAYER_MODERATION_LIST_BEHAVIOUR.setWhitelisted(
                PLAYER_SERVER_CONTEXT_BRIDGE_BEHAVIOUR.resolveServerHandle(server),
                getName(),
                value
        );
    }

    public void hidePlayer(Player player) {
        PLAYER_VISIBILITY_BRIDGE_BEHAVIOUR.hidePlayer(hiddenPlayers, this, player);
    }

    public void showPlayer(Player player) {
        PLAYER_VISIBILITY_BRIDGE_BEHAVIOUR.showPlayer(hiddenPlayers, this, player);
    }

    public boolean canSee(Player player) {
        return PLAYER_VISIBILITY_BRIDGE_BEHAVIOUR.canSee(hiddenPlayers, player);
    }

    public void sendPacket(final Player player, final Packet packet) {
        PLAYER_MESSAGING_BRIDGE_BEHAVIOUR.sendPacketIfOnline(
                player,
                PLAYER_CONNECTION_CONTEXT_BRIDGE_BEHAVIOUR.resolveConnection(getHandle()),
                packet
        );
    }
}
