package com.legacyminecraft.compat.bukkit;

public class CraftMinecart extends CraftEntity {
    public enum Type {
        Minecart(0),
        StorageMinecart(1),
        PoweredMinecart(2);

        private final int id;

        Type(int id) {
            this.id = id;
        }

        public int getId() {
            return id;
        }
    }

    public CraftMinecart(CraftServer server, EntityMinecart handle) {
        super(handle);
    }
}
