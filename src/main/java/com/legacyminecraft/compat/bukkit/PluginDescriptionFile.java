package com.legacyminecraft.compat.bukkit;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Canonical compat plugin description scaffold.
 */
public class PluginDescriptionFile {
    private final String name;
    private final String version;
    private final String mainClass;
    private PluginLoadOrder loadOrder = PluginLoadOrder.POSTWORLD;
    private final List<String> authors = new ArrayList<String>();
    private final List<Permission> permissions = new ArrayList<Permission>();

    public PluginDescriptionFile(String name, String version, String mainClass) {
        this.name = name == null ? "UnknownPlugin" : name;
        this.version = version == null ? "0.0.0" : version;
        this.mainClass = mainClass == null ? "" : mainClass;
    }

    public String getName() {
        return name;
    }

    public String getVersion() {
        return version;
    }

    public String getMain() {
        return mainClass;
    }

    public String getFullName() {
        return name + " v" + version;
    }

    public PluginLoadOrder getLoad() {
        return loadOrder;
    }

    public void setLoad(PluginLoadOrder loadOrder) {
        this.loadOrder = loadOrder == null ? PluginLoadOrder.POSTWORLD : loadOrder;
    }

    public List<String> getAuthors() {
        return Collections.unmodifiableList(authors);
    }

    public List<Permission> getPermissions() {
        return Collections.unmodifiableList(permissions);
    }
}
