package net.minecraft.server;

import com.legacyminecraft.poseidon.world.WorldMapCollectionSystem;

import java.util.*;

public class WorldMapCollection {

    private IDataManager a;
    private Map b = new HashMap();
    private List c = new ArrayList();
    private Map d = new HashMap();
    private final WorldMapCollectionSystem worldMapCollectionSystem = WorldMapCollectionSystem.getInstance();

    public WorldMapCollection(IDataManager idatamanager) {
        this.a = idatamanager;
        this.b();
    }

    public WorldMapBase a(Class oclass, String s) {
        return worldMapCollectionSystem.getOrLoadMap(oclass, s, this.a, this.b, this.c);
    }

    public void a(String s, WorldMapBase worldmapbase) {
        worldMapCollectionSystem.putMap(s, worldmapbase, this.b, this.c);
    }

    public void a() {
        worldMapCollectionSystem.flushDirtyMaps(this.c, this.a);
    }

    private void a(WorldMapBase worldmapbase) {
        worldMapCollectionSystem.saveMap(this.a, worldmapbase);
    }

    private void b() {
        worldMapCollectionSystem.loadIdCounts(this.a, this.d);
    }

    public int a(String s) {
        return worldMapCollectionSystem.nextId(s, this.d, this.a);
    }
}
