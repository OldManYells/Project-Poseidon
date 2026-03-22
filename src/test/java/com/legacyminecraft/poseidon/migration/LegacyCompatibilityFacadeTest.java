package com.legacyminecraft.poseidon.migration;

import org.junit.Assert;
import org.junit.Test;

import java.util.UUID;

public class LegacyCompatibilityFacadeTest {
    @Test
    public void connectionTypeRoundTrip() {
        com.projectposeidon.ConnectionType legacy = com.projectposeidon.ConnectionType.BUNGEECORD_ONLINE_MODE_IP_FORWARDING;
        com.legacyminecraft.poseidon.api.network.ConnectionType canonical = legacy.toCanonical();
        Assert.assertEquals(legacy, com.projectposeidon.ConnectionType.fromCanonical(canonical));
    }

    @Test
    public void uuidTypeRoundTrip() {
        com.projectposeidon.api.UUIDType legacy = com.projectposeidon.api.UUIDType.OFFLINE;
        com.legacyminecraft.poseidon.api.uuid.UUIDType canonical = legacy.toCanonical();
        Assert.assertEquals(legacy, com.projectposeidon.api.UUIDType.fromCanonical(canonical));
    }

    @Test
    public void offlineUuidFacadeIsConsistent() {
        UUID canonical = com.legacyminecraft.poseidon.api.uuid.PoseidonUUID.getPlayerOfflineUUID("PlayerOne");
        UUID legacyApi = com.projectposeidon.api.PoseidonUUID.getPlayerOfflineUUID("PlayerOne");
        UUID legacyManager = com.projectposeidon.johnymuffin.UUIDManager.generateOfflineUUID("PlayerOne");

        Assert.assertEquals(canonical, legacyApi);
        Assert.assertEquals(canonical, legacyManager);
    }
}
