package com.legacyminecraft.poseidon.world.player;

import com.legacyminecraft.compat.bukkit.Packet39AttachEntity;


/**
 * Canonical attachment/mount synchronization for player wrapper flows.
 */
public final class PlayerAttachmentSyncSystem {
    private static final PlayerAttachmentSyncSystem INSTANCE = new PlayerAttachmentSyncSystem();

    private PlayerAttachmentSyncSystem() {
    }

    public static PlayerAttachmentSyncSystem getInstance() {
        return INSTANCE;
    }

    public void syncPassengerAttachment(EntityPlayer player) {
        if (!shouldSyncAttachment(player.netServerHandler != null)) {
            return;
        }
        player.netServerHandler.sendPacket(new Packet39AttachEntity(player, player.vehicle));
        player.netServerHandler.a(player.locX, player.locY, player.locZ, player.yaw, player.pitch);
    }

    public void restoreSessionState(EntityPlayer player) {
        if (player.vehicle != null) {
            player.setPassengerOf(player.vehicle);
        }

        if (player.passenger != null) {
            player.passenger.setPassengerOf(player);
        }

        if (isSleeping(player)) {
            invokeIfPresent(player, "a", true, false, false);
        }
    }

    public boolean shouldSyncAttachment(boolean hasNetServerHandler) {
        return hasNetServerHandler;
    }

    private boolean isSleeping(Object player) {
        Object sleeping = readField(player, "sleeping");
        if (sleeping instanceof Boolean) {
            return (Boolean) sleeping;
        }

        Object fauxSleeping = readField(player, "fauxSleeping");
        return Boolean.TRUE.equals(fauxSleeping);
    }

    private Object readField(Object target, String fieldName) {
        if (target == null) {
            return null;
        }

        Class<?> type = target.getClass();
        while (type != null) {
            try {
                java.lang.reflect.Field field = type.getDeclaredField(fieldName);
                field.setAccessible(true);
                return field.get(target);
            } catch (NoSuchFieldException ignored) {
                type = type.getSuperclass();
            } catch (Exception exception) {
                throw new IllegalStateException(exception);
            }
        }

        return null;
    }

    private void invokeIfPresent(Object target, String methodName, Object... args) {
        if (target == null) {
            return;
        }

        Class<?> type = target.getClass();
        while (type != null) {
            for (java.lang.reflect.Method method : type.getDeclaredMethods()) {
                if (!method.getName().equals(methodName) || method.getParameterTypes().length != args.length) {
                    continue;
                }
                try {
                    method.setAccessible(true);
                    method.invoke(target, args);
                    return;
                } catch (Exception ignored) {
                    // Try another overload.
                }
            }
            type = type.getSuperclass();
        }
    }
}
