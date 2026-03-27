package com.legacyminecraft.poseidon.network;


/**
 * Canonical processor for player kick events and leave-message resolution.
 */
public final class ConnectionKickProcessor {
    private static final ConnectionKickProcessor INSTANCE = new ConnectionKickProcessor();

    private ConnectionKickProcessor() {
    }

    public static ConnectionKickProcessor getInstance() {
        return INSTANCE;
    }

    public KickDecision processKick(Object server, Object player, String reason, String leaveMessageTemplate) {
        String playerName = (String) Reflection.getField(player, "name");
        String leaveMessage = buildLeaveMessage(leaveMessageTemplate, playerName);
        Object bukkitPlayer = Reflection.invoke(server, "getPlayer", playerName);
        Object event = NetworkCompatGatewayRegistry.gateway().createPlayerKickEvent(bukkitPlayer, reason, leaveMessage);
        Object pluginManager = Reflection.invoke(server, "getPluginManager");
        Reflection.invoke(pluginManager, "callEvent", event);

        if ((Boolean) Reflection.invoke(event, "isCancelled")) {
            return KickDecision.cancelled();
        }

        return KickDecision.allowed((String) Reflection.invoke(event, "getReason"), (String) Reflection.invoke(event, "getLeaveMessage"));
    }

    public String buildLeaveMessage(String leaveMessageTemplate, String playerName) {
        return leaveMessageTemplate.replace("%player%", playerName);
    }

    public static final class KickDecision {
        private final boolean cancelled;
        private final String reason;
        private final String leaveMessage;

        private KickDecision(boolean cancelled, String reason, String leaveMessage) {
            this.cancelled = cancelled;
            this.reason = reason;
            this.leaveMessage = leaveMessage;
        }

        public static KickDecision cancelled() {
            return new KickDecision(true, null, null);
        }

        public static KickDecision allowed(String reason, String leaveMessage) {
            return new KickDecision(false, reason, leaveMessage);
        }

        public boolean isCancelled() {
            return cancelled;
        }

        public String getReason() {
            return reason;
        }

        public String getLeaveMessage() {
            return leaveMessage;
        }
    }

    private static final class Reflection {
        private Reflection() {
        }

        static Object getField(Object target, String fieldName) {
            Class<?> type = target.getClass();
            while (type != null) {
                try {
                    java.lang.reflect.Field field = type.getDeclaredField(fieldName);
                    field.setAccessible(true);
                    return field.get(target);
                } catch (NoSuchFieldException ignored) {
                    type = type.getSuperclass();
                } catch (Exception exception) {
                    throw new IllegalStateException("Unable to read field: " + fieldName, exception);
                }
            }
            throw new IllegalStateException("Field not found: " + fieldName);
        }

        static Object invoke(Object target, String methodName, Object... args) {
            Class<?> type = target.getClass();
            while (type != null) {
                for (java.lang.reflect.Method method : type.getMethods()) {
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
