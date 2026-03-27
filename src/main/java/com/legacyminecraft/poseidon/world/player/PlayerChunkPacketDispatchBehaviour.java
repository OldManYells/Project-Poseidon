package com.legacyminecraft.poseidon.world.player;


import java.util.List;

/**
 * Canonical behaviour for dispatching chunk-scoped packets to subscribed players.
 */
public final class PlayerChunkPacketDispatchBehaviour {
    private static final PlayerChunkPacketDispatchBehaviour INSTANCE = new PlayerChunkPacketDispatchBehaviour();

    private PlayerChunkPacketDispatchBehaviour() {
    }

    public static PlayerChunkPacketDispatchBehaviour getInstance() {
        return INSTANCE;
    }

    public void sendToSubscribedPlayers(List playersInChunk, Object chunkLocation, Object packet) {
        for (int index = 0; index < playersInChunk.size(); ++index) {
            Object player = playersInChunk.get(index);
            Object subscriptions = Bridge.readField(player, "playerChunkCoordIntPairs");
            if (Boolean.TRUE.equals(Bridge.invoke(subscriptions, "contains", chunkLocation))) {
                Object netServerHandler = Bridge.readField(player, "netServerHandler");
                Bridge.invoke(netServerHandler, "sendPacket", packet);
            }
        }
    }

    private static final class Bridge {
        private static Object readField(Object target, String fieldName) {
            try {
                java.lang.reflect.Field field = target.getClass().getField(fieldName);
                field.setAccessible(true);
                return field.get(target);
            } catch (Exception exception) {
                throw new IllegalStateException(exception);
            }
        }

        private static Object invoke(Object target, String methodName, Object... args) {
            try {
                for (java.lang.reflect.Method method : target.getClass().getMethods()) {
                    if (method.getName().equals(methodName) && method.getParameterTypes().length == args.length) {
                        method.setAccessible(true);
                        return method.invoke(target, args);
                    }
                }
                throw new IllegalStateException("Method not found: " + methodName);
            } catch (Exception exception) {
                throw new IllegalStateException(exception);
            }
        }
    }
}
