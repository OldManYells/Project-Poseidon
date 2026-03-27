package com.legacyminecraft.poseidon.network;

/**
 * Runtime registry for legacy-compat network bridge implementations.
 */
public final class NetworkCompatGatewayRegistry {
    private static volatile NetworkCompatGateway gateway = new MissingGateway();

    private NetworkCompatGatewayRegistry() {
    }

    public static NetworkCompatGateway gateway() {
        return gateway;
    }

    public static void install(NetworkCompatGateway networkCompatGateway) {
        if (networkCompatGateway == null) {
            throw new IllegalArgumentException("networkCompatGateway");
        }
        gateway = networkCompatGateway;
    }

    private static final class MissingGateway implements NetworkCompatGateway {
        private UnsupportedOperationException missing() {
            return new UnsupportedOperationException("NetworkCompatGateway is not installed");
        }

        @Override
        public String allowedCharacters() {
            throw missing();
        }

        @Override
        public boolean isNetServerHandler(Object handler) {
            throw missing();
        }

        @Override
        public Object createPlayerReceivePacketEvent(String username, Object packet) {
            throw missing();
        }

        @Override
        public Object createPlayerSendPacketEvent(String username, Object packet) {
            throw missing();
        }

        @Override
        public Object createPacketReceivedEvent(Object bukkitPlayer, Object packet) {
            throw missing();
        }

        @Override
        public void callGlobalEvent(Object event) {
            throw missing();
        }

        @Override
        public Object createPlayerChatEvent(Object player, String message) {
            throw missing();
        }

        @Override
        public Object createPlayerCommandPreprocessEvent(Object player, String message) {
            throw missing();
        }

        @Override
        public Object createPlayerItemHeldEvent(Object player, int previousSlot, int newSlot) {
            throw missing();
        }

        @Override
        public Object createPlayerKickEvent(Object bukkitPlayer, String reason, String leaveMessage) {
            throw missing();
        }

        @Override
        public Object createPlayerMoveEvent(Object player, Object from, Object to) {
            throw missing();
        }

        @Override
        public Object createPlayerTeleportEvent(Object player, Object from, Object to) {
            throw missing();
        }

        @Override
        public Object createLocation(Object world, double x, double y, double z) {
            throw missing();
        }

        @Override
        public Object createLocation(Object world, double x, double y, double z, float yaw, float pitch) {
            throw missing();
        }

        @Override
        public Object createChatPacket(String message) {
            throw missing();
        }

        @Override
        public Object createKickPacket(String reason) {
            throw missing();
        }

        @Override
        public Object createHandshakePacket(String token) {
            throw missing();
        }

        @Override
        public Object createTransactionPacket(int windowId, short actionNumber, boolean accepted) {
            throw missing();
        }

        @Override
        public Object createNbtTagCompound() {
            throw missing();
        }

        @Override
        public Object createNbtTagList() {
            throw missing();
        }

        @Override
        public Object createItemStackFromNbt(Object nbtTagCompound) {
            throw missing();
        }

        @Override
        public Object[] createItemStackArray(int length) {
            throw missing();
        }

        @Override
        public Object getFurnaceRecipeResult(int itemId) {
            throw missing();
        }

        @Override
        public Object createNetServerHandler(Object minecraftServer, Object networkManager, Object entityPlayer) {
            throw missing();
        }

        @Override
        public Object createLoginPacket(String username, int entityId, long worldSeed, byte dimension) {
            throw missing();
        }

        @Override
        public Object createSpawnPositionPacket(int x, int y, int z) {
            throw missing();
        }

        @Override
        public Object createUpdateTimePacket(long playerTime) {
            throw missing();
        }

        @Override
        public void writePacket(Object packet, java.io.DataOutputStream output) throws java.io.IOException {
            throw missing();
        }
    }
}
