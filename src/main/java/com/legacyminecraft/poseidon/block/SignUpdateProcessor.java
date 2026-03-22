package com.legacyminecraft.poseidon.block;

import net.minecraft.server.EntityPlayer;
import net.minecraft.server.FontAllowedCharacters;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.Packet130UpdateSign;
import net.minecraft.server.TileEntity;
import net.minecraft.server.TileEntitySign;
import net.minecraft.server.WorldServer;
import org.bukkit.Server;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.SignChangeEvent;

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

    public void processSignUpdate(MinecraftServer minecraftServer, Server server, EntityPlayer player, Packet130UpdateSign signUpdatePacket) {
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
        Block signBlock = bukkitPlayer.getWorld().getBlockAt(signX, signY, signZ);
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
