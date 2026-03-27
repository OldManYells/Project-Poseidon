package com.legacyminecraft.poseidon.network;


/**
 * Canonical handler for client-initiated disconnect packets.
 */
public final class ClientDisconnectPacketHandler {
    private static final ClientDisconnectPacketHandler INSTANCE = new ClientDisconnectPacketHandler();
    private final NetworkDisconnectKeyPolicy networkDisconnectKeyPolicy = NetworkDisconnectKeyPolicy.getInstance();
    private final NetworkDisconnectArgumentPolicy networkDisconnectArgumentPolicy =
            NetworkDisconnectArgumentPolicy.getInstance();

    private ClientDisconnectPacketHandler() {
    }

    public static ClientDisconnectPacketHandler getInstance() {
        return INSTANCE;
    }

    public void handleClientDisconnect(Object networkManager) {
        invoke(networkManager, "a",
                networkDisconnectKeyPolicy.quitting(),
                networkDisconnectArgumentPolicy.emptyArgs()
        );
    }

    public String getDisconnectReasonKey() {
        return networkDisconnectKeyPolicy.quitting();
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
}
