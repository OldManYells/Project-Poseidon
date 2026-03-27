package com.legacyminecraft.poseidon.block;

import com.legacyminecraft.compat.bukkit.EntityPlayer;
import com.legacyminecraft.compat.bukkit.FontAllowedCharacters;
import com.legacyminecraft.compat.bukkit.MinecraftServer;
import com.legacyminecraft.compat.bukkit.Packet130UpdateSign;
import com.legacyminecraft.compat.bukkit.Player;
import com.legacyminecraft.compat.bukkit.Server;
import com.legacyminecraft.compat.bukkit.SignChangeEvent;
import com.legacyminecraft.compat.bukkit.TileEntity;
import com.legacyminecraft.compat.bukkit.TileEntitySign;
import com.legacyminecraft.compat.bukkit.WorldServer;
import com.legacyminecraft.compat.bukkit.CraftWorld;
import com.legacyminecraft.compat.bukkit.block.Block;


/**
 * Canonical processor for sign-edit packet validation and application flow.
 */
public final class SignUpdateProcessor {
    private static final SignUpdateProcessor INSTANCE = new SignUpdateProcessor();

    private SignUpdateProcessor() {
    }

    public static SignUpdateProcessor getInstance() {
        return INSTANCE;
    }

    public void processSignUpdate(Object minecraftServerRaw, Object serverRaw, Object playerRaw, Object signUpdatePacketRaw) {
        MinecraftServer minecraftServer = (MinecraftServer) minecraftServerRaw;
        Server server = (Server) serverRaw;
        EntityPlayer player = (EntityPlayer) playerRaw;
        Packet130UpdateSign signUpdatePacket = (Packet130UpdateSign) signUpdatePacketRaw;
        WorldServer worldserver = minecraftServer.getWorldServer(player.dimension);
        if (!worldserver.isLoaded(signUpdatePacket.x, signUpdatePacket.y, signUpdatePacket.z)) {
            return;
        }

        TileEntity tileEntity = worldserver.getTileEntity(signUpdatePacket.x, signUpdatePacket.y, signUpdatePacket.z);
        if (!(tileEntity instanceof TileEntitySign)) {
            return;
        }

        TileEntitySign signTileEntity = (TileEntitySign) tileEntity;
        if (!signTileEntity.a()) {
            minecraftServer.c("Player " + player.name + " just tried to change non-editable sign");
            player.netServerHandler.sendPacket(new Packet130UpdateSign(signUpdatePacket.x, signUpdatePacket.y, signUpdatePacket.z, signTileEntity.lines));
            return;
        }

        sanitizeLines(signUpdatePacket.lines, 15, FontAllowedCharacters.allowedCharacters, "!?");

        int signX = signUpdatePacket.x;
        int signY = signUpdatePacket.y;
        int signZ = signUpdatePacket.z;

        Player bukkitPlayer = (Player) player.getBukkitEntity();
        Block signBlock = ((CraftWorld) bukkitPlayer.getWorld()).getBlockAt(signX, signY, signZ);
        SignChangeEvent event = new SignChangeEvent(signBlock, bukkitPlayer, signUpdatePacket.lines);
        server.getPluginManager().callEvent(event);

        if (!event.isCancelled()) {
            for (int lineIndex = 0; lineIndex < 4; ++lineIndex) {
                signTileEntity.lines[lineIndex] = event.getLine(lineIndex);
            }
            signTileEntity.a(false);
        }

        signTileEntity.update();
        worldserver.notify(signX, signY, signZ);
    }

    public void sanitizeLines(String[] lines, int maxLength, String allowedCharacters, String replacement) {
        for (int lineIndex = 0; lineIndex < lines.length; ++lineIndex) {
            boolean isLineValid = true;

            if (lines[lineIndex].length() > maxLength) {
                isLineValid = false;
            } else {
                for (int characterIndex = 0; characterIndex < lines[lineIndex].length(); ++characterIndex) {
                    if (allowedCharacters.indexOf(lines[lineIndex].charAt(characterIndex)) < 0) {
                        isLineValid = false;
                    }
                }
            }

            if (!isLineValid) {
                lines[lineIndex] = replacement;
            }
        }
    }
}
