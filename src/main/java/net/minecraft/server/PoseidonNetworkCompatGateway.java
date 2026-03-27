package net.minecraft.server;

import com.legacyminecraft.poseidon.network.NetworkCompatGateway;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.packet.PacketReceivedEvent;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.poseidon.PlayerReceivePacketEvent;
import org.bukkit.event.poseidon.PlayerSendPacketEvent;

import java.io.DataOutputStream;
import java.io.IOException;

final class PoseidonNetworkCompatGateway implements NetworkCompatGateway {
    @Override
    public String allowedCharacters() {
        return FontAllowedCharacters.allowedCharacters;
    }

    @Override
    public boolean isNetServerHandler(Object handler) {
        return handler instanceof NetServerHandler;
    }

    @Override
    public Object createPlayerReceivePacketEvent(String username, Object packet) {
        return new PlayerReceivePacketEvent(username, (Packet) packet);
    }

    @Override
    public Object createPlayerSendPacketEvent(String username, Object packet) {
        return new PlayerSendPacketEvent(username, (Packet) packet);
    }

    @Override
    public Object createPacketReceivedEvent(Object bukkitPlayer, Object packet) {
        return new PacketReceivedEvent((Player) bukkitPlayer, (Packet) packet);
    }

    @Override
    public void callGlobalEvent(Object event) {
        Bukkit.getPluginManager().callEvent((Event) event);
    }

    @Override
    public Object createPlayerChatEvent(Object player, String message) {
        return new PlayerChatEvent((Player) player, message);
    }

    @Override
    public Object createPlayerCommandPreprocessEvent(Object player, String message) {
        return new PlayerCommandPreprocessEvent((Player) player, message);
    }

    @Override
    public Object createPlayerItemHeldEvent(Object player, int previousSlot, int newSlot) {
        return new PlayerItemHeldEvent((Player) player, previousSlot, newSlot);
    }

    @Override
    public Object createPlayerKickEvent(Object bukkitPlayer, String reason, String leaveMessage) {
        return new PlayerKickEvent((Player) bukkitPlayer, reason, leaveMessage);
    }

    @Override
    public Object createPlayerMoveEvent(Object player, Object from, Object to) {
        return new PlayerMoveEvent((Player) player, (Location) from, (Location) to);
    }

    @Override
    public Object createPlayerTeleportEvent(Object player, Object from, Object to) {
        return new PlayerTeleportEvent((Player) player, (Location) from, (Location) to);
    }

    @Override
    public Object createLocation(Object world, double x, double y, double z) {
        return new Location((World) world, x, y, z);
    }

    @Override
    public Object createLocation(Object world, double x, double y, double z, float yaw, float pitch) {
        return new Location((World) world, x, y, z, yaw, pitch);
    }

    @Override
    public Object createChatPacket(String message) {
        return new Packet3Chat(message);
    }

    @Override
    public Object createKickPacket(String reason) {
        return new Packet255KickDisconnect(reason);
    }

    @Override
    public Object createHandshakePacket(String token) {
        return new Packet2Handshake(token);
    }

    @Override
    public Object createTransactionPacket(int windowId, short actionNumber, boolean accepted) {
        return new Packet106Transaction(windowId, actionNumber, accepted);
    }

    @Override
    public Object createNbtTagCompound() {
        return new NBTTagCompound();
    }

    @Override
    public Object createNbtTagList() {
        return new NBTTagList();
    }

    @Override
    public Object createItemStackFromNbt(Object nbtTagCompound) {
        return new ItemStack((NBTTagCompound) nbtTagCompound);
    }

    @Override
    public Object[] createItemStackArray(int length) {
        return new ItemStack[length];
    }

    @Override
    public Object getFurnaceRecipeResult(int itemId) {
        return FurnaceRecipes.getInstance().a(itemId);
    }

    @Override
    public Object createNetServerHandler(Object minecraftServer, Object networkManager, Object entityPlayer) {
        return new NetServerHandler((MinecraftServer) minecraftServer, (NetworkManager) networkManager, (EntityPlayer) entityPlayer);
    }

    @Override
    public Object createLoginPacket(String username, int entityId, long worldSeed, byte dimension) {
        return new Packet1Login(username, entityId, worldSeed, dimension);
    }

    @Override
    public Object createSpawnPositionPacket(int x, int y, int z) {
        return new Packet6SpawnPosition(x, y, z);
    }

    @Override
    public Object createUpdateTimePacket(long playerTime) {
        return new Packet4UpdateTime(playerTime);
    }

    @Override
    public void writePacket(Object packet, DataOutputStream output) throws IOException {
        Packet.a((Packet) packet, output);
    }
}
