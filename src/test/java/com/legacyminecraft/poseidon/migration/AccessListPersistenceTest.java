package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.auth.login.AccessListPersistence;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

public class AccessListPersistenceTest {
    private final AccessListPersistence persistence = AccessListPersistence.getInstance();

    @Test
    public void normalizesAddAndRemove() {
        Set values = new HashSet();
        persistence.addNormalized(values, "  PlayerOne ");
        Assert.assertTrue(values.contains("playerone"));

        persistence.removeNormalized(values, "PLAYERONE");
        Assert.assertFalse(values.contains("playerone"));
    }

    @Test
    public void savesAndLoadsNormalizedEntries() throws Exception {
        File tempFile = File.createTempFile("poseidon-access-list", ".txt");
        tempFile.deleteOnExit();

        Set values = new HashSet();
        persistence.addNormalized(values, "Alpha");
        persistence.addNormalized(values, "Beta");

        persistence.saveSet(values, tempFile, Logger.getLogger("test"), "save fail: ");

        Set loaded = new HashSet();
        persistence.loadNormalizedSet(loaded, tempFile, Logger.getLogger("test"), "load fail: ");

        Assert.assertTrue(loaded.contains("alpha"));
        Assert.assertTrue(loaded.contains("beta"));
    }
}
