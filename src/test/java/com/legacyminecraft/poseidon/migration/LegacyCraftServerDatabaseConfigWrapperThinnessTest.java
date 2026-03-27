package com.legacyminecraft.poseidon.migration;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LegacyCraftServerDatabaseConfigWrapperThinnessTest {
    private static final Path CRAFT_SERVER_PATH = Paths.get("src/main/java/org/bukkit/craftbukkit/CraftServer.java");
    private static final Path CRAFT_SERVER_DATABASE_CONFIG_BEHAVIOUR_PATH =
            Paths.get("src/main/java/com/legacyminecraft/poseidon/compat/bukkit/CraftServerDatabaseConfigBehaviour.java");

    @Test
    public void craftServerDelegatesDatabaseConfigWrapperGlueToCanonicalBehaviour() throws IOException {
        String craftServerText = read(CRAFT_SERVER_PATH);
        String behaviourText = read(CRAFT_SERVER_DATABASE_CONFIG_BEHAVIOUR_PATH);
        String section = section(craftServerText, "public void configureDbConfig(ServerConfig config) {", "public boolean addRecipe(Recipe recipe) {");

        Assert.assertTrue(craftServerText.contains("CraftServerDatabaseConfigBehaviour"));
        Assert.assertTrue(craftServerText.contains("CRAFT_SERVER_DATABASE_CONFIG_BEHAVIOUR.configureDbConfig(configuration, config);"));

        Assert.assertFalse(section.contains("new DataSourceConfig()"));
        Assert.assertFalse(section.contains("configuration.getString(\"database.driver\")"));
        Assert.assertFalse(section.contains("TransactionIsolation.getLevel"));
        Assert.assertFalse(section.contains("new SQLitePlatform()"));
        Assert.assertFalse(section.contains("config.setDataSourceConfig"));

        Assert.assertTrue(behaviourText.contains("configureDbConfig(Configuration configuration, ServerConfig config)"));
        Assert.assertTrue(behaviourText.contains("DataSourceConfig dataSourceConfig = new DataSourceConfig();"));
        Assert.assertTrue(behaviourText.contains("dataSourceConfig.setDriver(configuration.getString(\"database.driver\"));"));
        Assert.assertTrue(behaviourText.contains("dataSourceConfig.setUrl(configuration.getString(\"database.url\"));"));
        Assert.assertTrue(behaviourText.contains("dataSourceConfig.setUsername(configuration.getString(\"database.username\"));"));
        Assert.assertTrue(behaviourText.contains("dataSourceConfig.setPassword(configuration.getString(\"database.password\"));"));
        Assert.assertTrue(behaviourText.contains("dataSourceConfig.setIsolationLevel(TransactionIsolation.getLevel(configuration.getString(\"database.isolation\")));"));
        Assert.assertTrue(behaviourText.contains("if (dataSourceConfig.getDriver().contains(\"sqlite\")) {"));
        Assert.assertTrue(behaviourText.contains("config.setDatabasePlatform(new SQLitePlatform());"));
        Assert.assertTrue(behaviourText.contains("config.getDatabasePlatform().getDbDdlSyntax().setIdentity(\"\");"));
        Assert.assertTrue(behaviourText.contains("config.setDataSourceConfig(dataSourceConfig);"));
    }

    private static String read(Path path) throws IOException {
        return new String(Files.readAllBytes(path), StandardCharsets.UTF_8);
    }

    private static String section(String text, String startMarker, String endMarker) {
        int startIndex = text.indexOf(startMarker);
        int endIndex = text.indexOf(endMarker);
        Assert.assertTrue(startIndex >= 0);
        Assert.assertTrue(endIndex >= 0);
        Assert.assertTrue(endIndex > startIndex);
        return text.substring(startIndex, endIndex);
    }
}
