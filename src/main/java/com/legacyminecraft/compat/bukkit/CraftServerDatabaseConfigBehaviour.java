package com.legacyminecraft.compat.bukkit;

import com.avaje.ebean.config.DataSourceConfig;
import com.avaje.ebean.config.ServerConfig;
import com.avaje.ebean.config.dbplatform.SQLitePlatform;
import com.avaje.ebeaninternal.server.lib.sql.TransactionIsolation;

/**
 * Canonical behaviour for CraftServer database configuration wrapper glue.
 */
public final class CraftServerDatabaseConfigBehaviour {
    private static final CraftServerDatabaseConfigBehaviour INSTANCE =
            new CraftServerDatabaseConfigBehaviour();

    private CraftServerDatabaseConfigBehaviour() {
    }

    public static CraftServerDatabaseConfigBehaviour getInstance() {
        return INSTANCE;
    }

    public void configureDbConfig(Configuration configuration, ServerConfig config) {
        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        dataSourceConfig.setDriver(configuration.getString("database.driver"));
        dataSourceConfig.setUrl(configuration.getString("database.url"));
        dataSourceConfig.setUsername(configuration.getString("database.username"));
        dataSourceConfig.setPassword(configuration.getString("database.password"));
        dataSourceConfig.setIsolationLevel(TransactionIsolation.getLevel(configuration.getString("database.isolation")));

        if (dataSourceConfig.getDriver().contains("sqlite")) {
            config.setDatabasePlatform(new SQLitePlatform());
            config.getDatabasePlatform().getDbDdlSyntax().setIdentity("");
        }

        config.setDataSourceConfig(dataSourceConfig);
    }
}
