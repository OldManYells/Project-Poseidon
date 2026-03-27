package com.legacyminecraft.poseidon.network;

import com.legacyminecraft.poseidon.PoseidonConfig;
import com.legacyminecraft.poseidon.api.network.ConnectionType;

/**
 * Canonical authenticated-session connection flow.
 */
public final class PlayerSessionConnector {
    private static final PlayerSessionConnector INSTANCE = new PlayerSessionConnector();
    private final ModLoaderSupportConfigPolicy modLoaderSupportConfigPolicy =
            ModLoaderSupportConfigPolicy.getInstance();

    private PlayerSessionConnector() {
    }

    public static PlayerSessionConnector getInstance() {
        return INSTANCE;
    }

    public void connectAuthenticatedPlayer(
            Object loginHandler,
            Object loginPacket,
            Object minecraftServer,
            boolean usingReleaseToBeta,
            ConnectionType connectionType,
            int rawConnectionType,
            boolean receivedKeepAlive) {
        Object serverConfigurationManager = Bridge.readField(minecraftServer, "serverConfigurationManager");
        String username = String.valueOf(Bridge.readField(loginPacket, "name"));
        Object entityplayer = Bridge.invoke(serverConfigurationManager, "a", loginHandler, username);

        if (entityplayer == null) {
            return;
        }

        Bridge.invoke(serverConfigurationManager, "b", entityplayer);
        logLogin(loginHandler, entityplayer);

        Object worldserver = Bridge.readField(entityplayer, "world");
        Object chunkcoordinates = Bridge.invoke(worldserver, "getSpawn");
        Object networkManager = Bridge.readField(loginHandler, "networkManager");
        Object netserverhandler =
                NetworkCompatGatewayRegistry.gateway().createNetServerHandler(minecraftServer, networkManager, entityplayer);
        Bridge.invoke(netserverhandler, "setUsingReleaseToBeta", Boolean.valueOf(usingReleaseToBeta));
        Bridge.invoke(netserverhandler, "setConnectionType", connectionType);
        Bridge.invoke(netserverhandler, "setRawConnectionType", Integer.valueOf(rawConnectionType));
        Bridge.invoke(netserverhandler, "setReceivedKeepAlive", Boolean.valueOf(receivedKeepAlive));

        int entityId = ((Number) Bridge.readField(entityplayer, "id")).intValue();
        long worldSeed = ((Number) Bridge.invoke(worldserver, "getSeed")).longValue();
        Object worldProvider = Bridge.readField(worldserver, "worldProvider");
        int dimension = ((Number) Bridge.readField(worldProvider, "dimension")).intValue();
        Object loginResponsePacket = NetworkCompatGatewayRegistry.gateway()
                .createLoginPacket("", entityId, worldSeed, (byte) dimension);
        Bridge.invoke(netserverhandler, "sendPacket", loginResponsePacket);

        int spawnX = ((Number) Bridge.readField(chunkcoordinates, "x")).intValue();
        int spawnY = ((Number) Bridge.readField(chunkcoordinates, "y")).intValue();
        int spawnZ = ((Number) Bridge.readField(chunkcoordinates, "z")).intValue();
        Object spawnPacket = NetworkCompatGatewayRegistry.gateway().createSpawnPositionPacket(spawnX, spawnY, spawnZ);
        Bridge.invoke(netserverhandler, "sendPacket", spawnPacket);

        Bridge.invoke(serverConfigurationManager, "a", entityplayer, worldserver);
        Bridge.invoke(serverConfigurationManager, "c", entityplayer);
        Bridge.invoke(
                netserverhandler,
                "a",
                Bridge.readField(entityplayer, "locX"),
                Bridge.readField(entityplayer, "locY"),
                Bridge.readField(entityplayer, "locZ"),
                Bridge.readField(entityplayer, "yaw"),
                Bridge.readField(entityplayer, "pitch")
        );
        Object networkListenThread = Bridge.readField(minecraftServer, "networkListenThread");
        Bridge.invoke(networkListenThread, "a", netserverhandler);

        long playerTime = ((Number) Bridge.invoke(entityplayer, "getPlayerTime")).longValue();
        Object updateTimePacket = NetworkCompatGatewayRegistry.gateway().createUpdateTimePacket(playerTime);
        Bridge.invoke(netserverhandler, "sendPacket", updateTimePacket);
        Bridge.invoke(entityplayer, "syncInventory");
        if (PoseidonConfig.getInstance().getBoolean(
                modLoaderSupportConfigPolicy.modLoaderSupportEnabledKey(),
                modLoaderSupportConfigPolicy.modLoaderSupportEnabledDefault()
        )) {
            Bridge.invokeStatic("modloadermp.ModLoaderMp", "HandleAllLogins", entityplayer);
        }
    }

    private void logLogin(Object loginHandler, Object entityplayer) {
        try {
            Object world = Bridge.readField(entityplayer, "world");
            Object worldData = Bridge.readField(world, "worldData");
            String worldName = String.valueOf(Bridge.readField(worldData, "name"));
            String identity = String.valueOf(Bridge.invoke(loginHandler, "b"));
            int entityId = ((Number) Bridge.readField(entityplayer, "id")).intValue();
            double x = ((Number) Bridge.readField(entityplayer, "locX")).doubleValue();
            double y = ((Number) Bridge.readField(entityplayer, "locY")).doubleValue();
            double z = ((Number) Bridge.readField(entityplayer, "locZ")).doubleValue();
            Object logger = loginHandler.getClass().getField("a").get(null);
            Bridge.invoke(logger, "info", identity + " logged in with entity id " + entityId + " at ([" + worldName + "] " + x + ", " + y + ", " + z + ")");
        } catch (Exception ignored) {
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

        private static Object invokeStatic(String className, String methodName, Object... args) {
            try {
                Class<?> type = Class.forName(className);
                for (java.lang.reflect.Method method : type.getMethods()) {
                    if (method.getName().equals(methodName) && method.getParameterTypes().length == args.length) {
                        method.setAccessible(true);
                        return method.invoke(null, args);
                    }
                }
                throw new IllegalStateException("Method not found: " + methodName);
            } catch (Exception exception) {
                throw new IllegalStateException(exception);
            }
        }

    }
}
