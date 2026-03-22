package com.legacyminecraft.poseidon.migration;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LegacyThreadLoginVerifierWrapperThinnessTest {
    private static final Path THREAD_LOGIN_VERIFIER_PATH = Paths.get("src/main/java/net/minecraft/server/ThreadLoginVerifier.java");

    @Test
    public void threadLoginVerifierUsesCanonicalSystemNaming() throws IOException {
        String text = new String(Files.readAllBytes(THREAD_LOGIN_VERIFIER_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("LoginVerificationThreadSystem"));
        Assert.assertTrue(text.contains("loginVerificationThreadSystem"));
        Assert.assertTrue(text.contains("loginVerificationThreadSystem.verify("));
        Assert.assertFalse(text.contains("LoginVerificationThreadService"));
        Assert.assertFalse(text.contains("loginVerificationThreadService"));
    }
}
