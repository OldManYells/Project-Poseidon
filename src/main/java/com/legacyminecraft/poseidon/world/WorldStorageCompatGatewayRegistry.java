package com.legacyminecraft.poseidon.world;

/**
 * Runtime registry for world storage compatibility bridge.
 */
public final class WorldStorageCompatGatewayRegistry {
    private static volatile WorldStorageCompatGateway gateway = new MissingGateway();

    private WorldStorageCompatGatewayRegistry() {
    }

    public static WorldStorageCompatGateway gateway() {
        return gateway;
    }

    public static void install(WorldStorageCompatGateway worldStorageCompatGateway) {
        if (worldStorageCompatGateway == null) {
            throw new IllegalArgumentException("worldStorageCompatGateway");
        }
        gateway = worldStorageCompatGateway;
    }

    private static final class MissingGateway implements WorldStorageCompatGateway {
        private UnsupportedOperationException missing() {
            return new UnsupportedOperationException("WorldStorageCompatGateway is not installed");
        }

        @Override
        public Object readCompressed(java.io.InputStream inputStream) {
            throw missing();
        }

        @Override
        public void writeCompressed(Object rootTag, java.io.OutputStream outputStream) {
            throw missing();
        }

        @Override
        public Object extractDataTag(Object rootTag) {
            throw missing();
        }

        @Override
        public Object createWorldData(Object dataTag) {
            throw missing();
        }

        @Override
        public Object createRootTag() {
            throw missing();
        }

        @Override
        public void setDataTag(Object rootTag, Object dataTag) {
            throw missing();
        }
    }
}
