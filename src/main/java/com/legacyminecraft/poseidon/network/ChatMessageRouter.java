package com.legacyminecraft.poseidon.network;


/**
 * Canonical router for player chat message flow.
 */
public final class ChatMessageRouter {
    private static final ChatMessageRouter INSTANCE = new ChatMessageRouter();
    private final ChatCommandPrefixPolicy chatCommandPrefixPolicy = ChatCommandPrefixPolicy.getInstance();

    private ChatMessageRouter() {
    }

    public static ChatMessageRouter getInstance() {
        return INSTANCE;
    }

    public boolean isCommandMessage(String message) {
        return chatCommandPrefixPolicy.isCommand(message);
    }

    public boolean handleNonCommandChat(Object server, Object minecraftServer, Object player, String message) {
        Object event = NetworkCompatGatewayRegistry.gateway().createPlayerChatEvent(player, message);
        Object pluginManager = invoke(server, "getPluginManager");
        invoke(pluginManager, "callEvent", event);

        if (Boolean.TRUE.equals(invoke(event, "isCancelled"))) {
            return true;
        }

        String format = String.valueOf(invoke(event, "getFormat"));
        Object eventPlayer = invoke(event, "getPlayer");
        String displayName = String.valueOf(invoke(eventPlayer, "getDisplayName"));
        String eventMessage = String.valueOf(invoke(event, "getMessage"));
        String formatted = String.format(format, displayName, eventMessage);
        Object console = getField(minecraftServer, "console");
        invoke(console, "sendMessage", formatted);
        Iterable recipients = cast(invoke(event, "getRecipients"));
        for (Object recipient : recipients) {
            invoke(recipient, "sendMessage", formatted);
        }
        return false;
    }

    private Object getField(Object target, String name) {
        try {
            java.lang.reflect.Field field = target.getClass().getField(name);
            field.setAccessible(true);
            return field.get(target);
        } catch (Exception exception) {
            throw new IllegalStateException(exception);
        }
    }

    private Object invoke(Object target, String methodName, Object... args) {
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

    @SuppressWarnings("unchecked")
    private <T> T cast(Object value) {
        return (T) value;
    }
}
