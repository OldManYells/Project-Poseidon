package com.legacyminecraft.poseidon.entity;

import com.legacyminecraft.poseidon.compat.bukkit.PlayerInteractEventBridgeBehaviour;
import net.minecraft.server.EntityPlayer;
import net.minecraft.server.EnumMovingObjectType;
import net.minecraft.server.MathHelper;
import net.minecraft.server.MovingObjectPosition;
import net.minecraft.server.Packet18ArmAnimation;
import net.minecraft.server.Packet19EntityAction;
import net.minecraft.server.Vec3D;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerAnimationEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;

/**
 * Canonical handler for player action packets (arm animation, sneak state, bed leave).
 */
public final class PlayerActionPacketHandler {
    private static final PlayerActionPacketHandler INSTANCE = new PlayerActionPacketHandler();
    private final PlayerInteractEventBridgeBehaviour playerInteractEventBridge = PlayerInteractEventBridgeBehaviour.getInstance();

    private PlayerActionPacketHandler() {
    }

    public static PlayerActionPacketHandler getInstance() {
        return INSTANCE;
    }

    public void handleArmAnimationPacket(Server server, EntityPlayer player, Packet18ArmAnimation armAnimationPacket) {
        if (player.dead) {
            return;
        }

        if (armAnimationPacket.b != 1) {
            return;
        }

        float interpolation = 1.0F;
        float interpolatedPitch = player.lastPitch + (player.pitch - player.lastPitch) * interpolation;
        float interpolatedYaw = player.lastYaw + (player.yaw - player.lastYaw) * interpolation;
        double interpolatedX = player.lastX + (player.locX - player.lastX) * interpolation;
        double interpolatedY = player.lastY + (player.locY - player.lastY) * interpolation + 1.62D - player.height;
        double interpolatedZ = player.lastZ + (player.locZ - player.lastZ) * interpolation;
        Vec3D eyePosition = Vec3D.create(interpolatedX, interpolatedY, interpolatedZ);

        float yawCos = MathHelper.cos(-interpolatedYaw * 0.017453292F - 3.1415927F);
        float yawSin = MathHelper.sin(-interpolatedYaw * 0.017453292F - 3.1415927F);
        float pitchCos = -MathHelper.cos(-interpolatedPitch * 0.017453292F);
        float pitchSin = MathHelper.sin(-interpolatedPitch * 0.017453292F);
        float lookX = yawSin * pitchCos;
        float lookZ = yawCos * pitchCos;
        double reachDistance = 5.0D;
        Vec3D rayTarget = eyePosition.add(lookX * reachDistance, pitchSin * reachDistance, lookZ * reachDistance);
        MovingObjectPosition hitResult = player.world.rayTrace(eyePosition, rayTarget, true);

        if (hitResult == null || hitResult.type != EnumMovingObjectType.TILE) {
            playerInteractEventBridge.callPlayerInteract(player, Action.LEFT_CLICK_AIR, player.inventory.getItemInHand());
        }

        PlayerAnimationEvent event = new PlayerAnimationEvent((Player) player.getBukkitEntity());
        server.getPluginManager().callEvent(event);

        if (event.isCancelled()) {
            return;
        }

        player.w();
    }

    public boolean handleEntityActionPacket(Server server, EntityPlayer player, Packet19EntityAction entityActionPacket) {
        if (player.dead) {
            return false;
        }

        if (entityActionPacket.animation == 1 || entityActionPacket.animation == 2) {
            PlayerToggleSneakEvent event = new PlayerToggleSneakEvent((Player) player.getBukkitEntity(), entityActionPacket.animation == 1);
            server.getPluginManager().callEvent(event);

            if (event.isCancelled()) {
                return false;
            }
        }

        if (entityActionPacket.animation == 1) {
            player.setSneak(true);
        } else if (entityActionPacket.animation == 2) {
            player.setSneak(false);
        } else if (shouldDisableMovementCheck(entityActionPacket.animation)) {
            player.a(false, true, true);
            return true;
        }

        return false;
    }

    public boolean shouldDisableMovementCheck(int animation) {
        return animation == 3;
    }
}
