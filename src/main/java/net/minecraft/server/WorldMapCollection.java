package net.minecraft.server;

import java.util.*;

public class WorldMapCollection {

    private IDataManager a;
    private Map b = new HashMap();
    private List c = new ArrayList();
    private Map d = new HashMap();

    public WorldMapCollection(IDataManager idatamanager) {
        this.a = idatamanager;
        this.loadIdCounts();
    }

    public WorldMapBase a(Class oclass, String s) {
        WorldMapBase map = (WorldMapBase) this.b.get(s);
        if (map != null) {
            return map;
        }
        try {
            map = (WorldMapBase) oclass.getConstructor(new Class[] {String.class}).newInstance(new Object[] {s});
            this.b.put(s, map);
            this.c.add(map);
            return map;
        } catch (Exception exception) {
            throw new RuntimeException("Failed to instantiate " + oclass, exception);
        }
    }

    public void a(String s, WorldMapBase worldmapbase) {
        if (worldmapbase == null) {
            throw new RuntimeException("Can\'t set null data");
        }
        if (this.b.containsKey(s)) {
            this.c.remove(this.b.remove(s));
        }
        this.b.put(s, worldmapbase);
        this.c.add(worldmapbase);
    }

    public void a() {
        for (int i = 0; i < this.c.size(); ++i) {
            WorldMapBase map = (WorldMapBase) this.c.get(i);
            if (map.b()) {
                map.a(false);
            }
        }
    }

    private void a(WorldMapBase worldmapbase) {
        // no-op in lean migration scaffold
    }

    private void loadIdCounts() {
        this.d.clear();
    }

    public int a(String s) {
        Short value = (Short) this.d.get(s);
        if (value == null) {
            value = Short.valueOf((short) 0);
        } else {
            value = Short.valueOf((short) (value.shortValue() + 1));
        }
        this.d.put(s, value);
        return value.shortValue();
    }
}
