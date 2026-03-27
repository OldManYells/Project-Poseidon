package com.legacyminecraft.poseidon.migration;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class NetworkConnectionPumpPolicyWiringTest {
    private static final Path NETWORK_CONNECTION_PUMP_SYSTEM_PATH =
            Paths.get("src/main/java/com/legacyminecraft/poseidon/network/NetworkConnectionPumpSystem.java");

    @Test
    public void usesInternalServerErrorPolicyInsteadOfInlineLiteral() throws IOException {
        String text = new String(Files.readAllBytes(NETWORK_CONNECTION_PUMP_SYSTEM_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("NetworkInternalServerErrorPolicy"));
        Assert.assertTrue(text.contains("networkInternalServerErrorPolicy"));
        Assert.assertFalse(text.contains("\"Internal server error\""));
    }
}
