package com.legacyminecraft.poseidon.migration;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LegacyCraftPlayerWrapperThinnessTest {
    private static final Path CRAFT_PLAYER_PATH =
            Paths.get("src/main/java/org/bukkit/craftbukkit/entity/CraftPlayer.java");

    @Test
    public void craftPlayerDelegatesIdentityAndStatusPoliciesToCanonicalBehaviours() throws IOException {
        String text = new String(Files.readAllBytes(CRAFT_PLAYER_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("PlayerOnlineStatusBridgeBehaviour"));
        Assert.assertTrue(text.contains("PlayerEyeHeightPolicy"));
        Assert.assertTrue(text.contains("PlayerIdentityBridgeBehaviour"));
        Assert.assertTrue(text.contains("PlayerStatisticDispatchBehaviour"));
        Assert.assertTrue(text.contains("PlayerChunkChangePacketBehaviour"));
        Assert.assertTrue(text.contains("PlayerLocalEffectPacketBehaviour"));
        Assert.assertTrue(text.contains("PlayerOperatorStateBehaviour"));
        Assert.assertTrue(text.contains("PlayerModerationListBehaviour"));
        Assert.assertTrue(text.contains("PlayerVisibilityBridgeBehaviour"));
        Assert.assertTrue(text.contains("PlayerMessagingBridgeBehaviour"));
        Assert.assertTrue(text.contains("PLAYER_ONLINE_STATUS_BRIDGE_BEHAVIOUR.isOnline(server.getHandle().players, getName())"));
        Assert.assertTrue(text.contains("PLAYER_ONLINE_STATUS_BRIDGE_BEHAVIOUR.resolveAddress(getHandle().netServerHandler)"));
        Assert.assertTrue(text.contains("PLAYER_EYE_HEIGHT_POLICY.resolveEyeHeight(ignoreSneaking, isSneaking())"));
        Assert.assertTrue(text.contains("PLAYER_IDENTITY_BRIDGE_BEHAVIOUR.equalsByName(this, obj)"));
        Assert.assertTrue(text.contains("PLAYER_IDENTITY_BRIDGE_BEHAVIOUR.hashByName(getName())"));
        Assert.assertTrue(text.contains("PLAYER_STATISTIC_DISPATCH_BEHAVIOUR.validateSubStatisticRequest(statistic, material)"));
        Assert.assertTrue(text.contains("PLAYER_STATISTIC_DISPATCH_BEHAVIOUR.resolveStatisticId(statistic, material)"));
        Assert.assertTrue(text.contains("PLAYER_STATISTIC_DISPATCH_BEHAVIOUR.sendStatistic(getHandle().netServerHandler, id, amount)"));
        Assert.assertTrue(text.contains("PLAYER_CHUNK_CHANGE_PACKET_BEHAVIOUR.createChunkChangePacket(loc, sx, sy, sz, data)"));
        Assert.assertTrue(text.contains("PLAYER_LOCAL_EFFECT_PACKET_BEHAVIOUR.createPlayNotePacket("));
        Assert.assertTrue(text.contains("PLAYER_LOCAL_EFFECT_PACKET_BEHAVIOUR.createEffectPacket(loc, effect, data)"));
        Assert.assertTrue(text.contains("PLAYER_LOCAL_EFFECT_PACKET_BEHAVIOUR.createBlockChangePacket(loc, material, data)"));
        Assert.assertTrue(text.contains("PLAYER_OPERATOR_STATE_BEHAVIOUR.isOperator(server.getHandle(), getName())"));
        Assert.assertTrue(text.contains("PLAYER_OPERATOR_STATE_BEHAVIOUR.updateOperatorState("));
        Assert.assertTrue(text.contains("PLAYER_MODERATION_LIST_BEHAVIOUR.isBanned(server.getHandle(), getName())"));
        Assert.assertTrue(text.contains("PLAYER_MODERATION_LIST_BEHAVIOUR.setBanned(server.getHandle(), getName(), value)"));
        Assert.assertTrue(text.contains("PLAYER_MODERATION_LIST_BEHAVIOUR.isWhitelisted(server.getHandle(), getName())"));
        Assert.assertTrue(text.contains("PLAYER_MODERATION_LIST_BEHAVIOUR.setWhitelisted(server.getHandle(), getName(), value)"));
        Assert.assertTrue(text.contains("PLAYER_VISIBILITY_BRIDGE_BEHAVIOUR.hidePlayer(hiddenPlayers, entity, getHandle(), player)"));
        Assert.assertTrue(text.contains("PLAYER_VISIBILITY_BRIDGE_BEHAVIOUR.showPlayer(hiddenPlayers, entity, getHandle(), player)"));
        Assert.assertTrue(text.contains("PLAYER_VISIBILITY_BRIDGE_BEHAVIOUR.canSee(hiddenPlayers, player)"));
        Assert.assertTrue(text.contains("PLAYER_MESSAGING_BRIDGE_BEHAVIOUR.sendRawMessage(getHandle().netServerHandler, message, getName())"));
        Assert.assertTrue(text.contains("PLAYER_MESSAGING_BRIDGE_BEHAVIOUR.kickIfOnline(isOnline(), getHandle().netServerHandler, message)"));
        Assert.assertTrue(text.contains("PLAYER_MESSAGING_BRIDGE_BEHAVIOUR.sendPacketIfOnline(player, getHandle().netServerHandler, packet)"));
        Assert.assertFalse(text.contains("EntityPlayer player = (EntityPlayer) obj;"));
        Assert.assertFalse(text.contains("SocketAddress addr = getHandle().netServerHandler.networkManager.getSocketAddress();"));
        Assert.assertFalse(text.contains("if (isSneaking()) {"));
        Assert.assertFalse(text.contains("hash = 97 * hash + (this.getName() != null ? this.getName().hashCode() : 0);"));
        Assert.assertFalse(text.contains("while (amount > Byte.MAX_VALUE) {"));
        Assert.assertFalse(text.contains("sendStatistic(statistic.getId() + mat, amount);"));
        Assert.assertFalse(text.contains("int cx = x >> 4;"));
        Assert.assertFalse(text.contains("if (sx <= 0 || sy <= 0 || sz <= 0) {"));
        Assert.assertFalse(text.contains("Packet51MapChunk packet = new Packet51MapChunk(x, y, z, sx, sy, sz, data);"));
        Assert.assertFalse(text.contains("new Packet54PlayNoteBlock(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ(), instrument, note)"));
        Assert.assertFalse(text.contains("Packet61 packet = new Packet61(packetData, loc.getBlockX(), loc.getBlockY(), loc.getBlockZ(), data);"));
        Assert.assertFalse(text.contains("Packet53BlockChange packet = new Packet53BlockChange(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ(), ((CraftWorld) loc.getWorld()).getHandle());"));
        Assert.assertFalse(text.contains("if (value == isOp()) return;"));
        Assert.assertFalse(text.contains("server.getHandle().banByName.contains(getName().toLowerCase())"));
        Assert.assertFalse(text.contains("server.getHandle().a(getName().toLowerCase());"));
        Assert.assertFalse(text.contains("hiddenPlayers.add(player.getUniqueId());"));
        Assert.assertFalse(text.contains("EntityTracker tracker = ((WorldServer) entity.world).tracker;"));
        Assert.assertFalse(text.contains("getHandle().netServerHandler.sendPacket(new Packet3Chat(message));"));
        Assert.assertFalse(text.contains("if (this.isOnline() && !getHandle().netServerHandler.disconnected)"));
        Assert.assertFalse(text.contains("NetServerHandler nsh = getHandle().netServerHandler;"));
    }
}
