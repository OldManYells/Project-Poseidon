package com.legacyminecraft.poseidon.auth.login;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.Set;
import java.util.logging.Logger;

/**
 * Canonical persistence and normalization service for legacy access-control lists.
 */
public final class AccessListPersistence {
    private static final AccessListPersistence INSTANCE = new AccessListPersistence();

    private AccessListPersistence() {
    }

    public static AccessListPersistence getInstance() {
        return INSTANCE;
    }

    public void addNormalized(Set target, String value) {
        target.add(normalize(value));
    }

    public void removeNormalized(Set target, String value) {
        target.remove(normalize(value));
    }

    public String normalize(String value) {
        if (value == null) {
            return "";
        }
        return value.trim().toLowerCase();
    }

    public void loadNormalizedSet(Set target, File sourceFile, Logger logger, String failurePrefix) {
        try {
            target.clear();
            BufferedReader reader = new BufferedReader(new FileReader(sourceFile));
            try {
                String line = "";
                while ((line = reader.readLine()) != null) {
                    target.add(normalize(line));
                }
            } finally {
                reader.close();
            }
        } catch (Exception exception) {
            logger.warning(failurePrefix + exception);
        }
    }

    public void saveSet(Set source, File targetFile, Logger logger, String failurePrefix) {
        try {
            PrintWriter writer = new PrintWriter(new FileWriter(targetFile, false));
            try {
                Iterator iterator = source.iterator();
                while (iterator.hasNext()) {
                    writer.println((String) iterator.next());
                }
            } finally {
                writer.close();
            }
        } catch (Exception exception) {
            logger.warning(failurePrefix + exception);
        }
    }
}
