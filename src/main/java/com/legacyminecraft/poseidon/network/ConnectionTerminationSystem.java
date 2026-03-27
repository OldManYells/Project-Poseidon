package com.legacyminecraft.poseidon.network;


/**
 * Canonical coordinator for graceful player disconnect termination flow.
 */
public final class ConnectionTerminationSystem {
    private static final ConnectionTerminationSystem INSTANCE = new ConnectionTerminationSystem();
    private final ConnectionKickProcessor connectionKickProcessor = ConnectionKickProcessor.getInstance();

    private ConnectionTerminationSystem() {
    }

    public static ConnectionTerminationSystem getInstance() {
        return INSTANCE;
    }

    public boolean terminate(
            boolean alreadyDisconnected,
            Object server,
            Object player,
            String reason,
            String leaveMessageTemplate,
            PacketSender packetSender,
            Object networkManager,
            Object minecraftServer
    ) {
        if (shouldSkipDisconnect(alreadyDisconnected)) {
            return true;
        }

        ConnectionKickProcessor.KickDecision kickDecision =
                connectionKickProcessor.processKick(server, player, reason, leaveMessageTemplate);
        if (kickDecision.isCancelled()) {
            return false;
        }

        Reflection.invoke(player, "B");
        packetSender.sendPacket(createKickPacket(kickDecision.getReason()));
        Reflection.invoke(networkManager, "d");

        if (shouldBroadcastLeaveMessage(kickDecision.getLeaveMessage())) {
            Object scm = Reflection.getField(minecraftServer, "serverConfigurationManager");
            Reflection.invoke(scm, "sendAll", createChatPacket(kickDecision.getLeaveMessage()));
        }

        Object scm = Reflection.getField(minecraftServer, "serverConfigurationManager");
        Reflection.invoke(scm, "disconnect", player);
        return true;
    }

    public boolean shouldSkipDisconnect(boolean alreadyDisconnected) {
        return alreadyDisconnected;
    }

    public boolean shouldBroadcastLeaveMessage(String leaveMessage) {
        return leaveMessage != null;
    }

    public Object createKickPacket(String reason) {
        return NetworkCompatGatewayRegistry.gateway().createKickPacket(reason);
    }

    public Object createChatPacket(String message) {
        return NetworkCompatGatewayRegistry.gateway().createChatPacket(message);
    }

    public interface PacketSender {
        void sendPacket(Object packet);
    }

    private static final class Reflection {
        private Reflection() {
        }

        static Object getField(Object target, String name) {
            Class<?> type = target.getClass();
            while (type != null) {
                try {
                    java.lang.reflect.Field field = type.getDeclaredField(name);
                    field.setAccessible(true);
                    return field.get(target);
                } catch (NoSuchFieldException ignored) {
                    type = type.getSuperclass();
                } catch (Exception exception) {
                    throw new IllegalStateException("Unable to access field: " + name, exception);
                }
            }
            throw new IllegalStateException("Field not found: " + name);
        }

        static Object invoke(Object target, String methodName, Object... args) {
            Class<?> type = target.getClass();
            while (type != null) {
                for (java.lang.reflect.Method method : type.getDeclaredMethods()) {
                    if (method.getName().equals(methodName) && method.getParameterTypes().length == args.length) {
                        try {
                            method.setAccessible(true);
                            return method.invoke(target, args);
                        } catch (Exception ignored) {
                        }
                    }
                }
                type = type.getSuperclass();
            }
            throw new IllegalStateException("Method not found: " + methodName);
        }
    }
}
