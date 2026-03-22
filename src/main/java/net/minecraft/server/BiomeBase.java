package net.minecraft.server;

import com.legacyminecraft.poseidon.world.biome.BiomeClimateSelectionBehaviour;
import com.legacyminecraft.poseidon.world.biome.BiomeSpawnListLookupBehaviour;
import com.legacyminecraft.poseidon.world.biome.BiomeTreeGeneratorSelectionBehaviour;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BiomeBase {

    public static final BiomeBase RAINFOREST = (new BiomeRainforest()).b(588342).a("Rainforest").a(2094168);
    public static final BiomeBase SWAMPLAND = (new BiomeSwamp()).b(522674).a("Swampland").a(9154376);
    public static final BiomeBase SEASONAL_FOREST = (new BiomeBase()).b(10215459).a("Seasonal Forest");
    public static final BiomeBase FOREST = (new BiomeForest()).b(353825).a("Forest").a(5159473);
    public static final BiomeBase SAVANNA = (new BiomeDesert()).b(14278691).a("Savanna");
    public static final BiomeBase SHRUBLAND = (new BiomeBase()).b(10595616).a("Shrubland");
    public static final BiomeBase TAIGA = (new BiomeTaiga()).b(3060051).a("Taiga").b().a(8107825);
    public static final BiomeBase DESERT = (new BiomeDesert()).b(16421912).a("Desert").e();
    public static final BiomeBase PLAINS = (new BiomeDesert()).b(16767248).a("Plains");
    public static final BiomeBase ICE_DESERT = (new BiomeDesert()).b(16772499).a("Ice Desert").b().e().a(12899129);
    public static final BiomeBase TUNDRA = (new BiomeBase()).b(5762041).a("Tundra").b().a(12899129);
    public static final BiomeBase HELL = (new BiomeHell()).b(16711680).a("Hell").e();
    public static final BiomeBase SKY = (new BiomeSky()).b(8421631).a("Sky").e();
    public String n;
    public int o;
    public byte p;
    public byte q;
    public int r;
    protected List s;
    protected List t;
    protected List u;
    private boolean v;
    private boolean w;
    private static BiomeBase[] x = new BiomeBase[4096];
    private static final BiomeClimateSelectionBehaviour BIOME_CLIMATE_SELECTION_SERVICE = BiomeClimateSelectionBehaviour.getInstance();
    private static final BiomeTreeGeneratorSelectionBehaviour BIOME_TREE_GENERATOR_SELECTION_SERVICE = BiomeTreeGeneratorSelectionBehaviour.getInstance();
    private static final BiomeSpawnListLookupBehaviour BIOME_SPAWN_LIST_LOOKUP_SERVICE = BiomeSpawnListLookupBehaviour.getInstance();

    protected BiomeBase() {
        this.p = (byte) Block.GRASS.id;
        this.q = (byte) Block.DIRT.id;
        this.r = 5169201;
        this.s = new ArrayList();
        this.t = new ArrayList();
        this.u = new ArrayList();
        this.w = true;
        this.s.add(new BiomeMeta(EntitySpider.class, 10));
        this.s.add(new BiomeMeta(EntityZombie.class, 10));
        this.s.add(new BiomeMeta(EntitySkeleton.class, 10));
        this.s.add(new BiomeMeta(EntityCreeper.class, 10));
        this.s.add(new BiomeMeta(EntitySlime.class, 10));
        this.t.add(new BiomeMeta(EntitySheep.class, 12));
        this.t.add(new BiomeMeta(EntityPig.class, 10));
        this.t.add(new BiomeMeta(EntityChicken.class, 10));
        this.t.add(new BiomeMeta(EntityCow.class, 8));
        this.u.add(new BiomeMeta(EntitySquid.class, 10));
    }

    private BiomeBase e() {
        this.w = false;
        return this;
    }

    public static void a() {
        BIOME_CLIMATE_SELECTION_SERVICE.bootstrapClimateLookupTable(x, DESERT, ICE_DESERT, (byte) Block.SAND.id);
    }

    public WorldGenerator a(Random random) {
        return BIOME_TREE_GENERATOR_SELECTION_SERVICE.selectDefaultTreeGenerator(random);
    }

    protected BiomeBase b() {
        this.v = true;
        return this;
    }

    protected BiomeBase a(String s) {
        this.n = s;
        return this;
    }

    protected BiomeBase a(int i) {
        this.r = i;
        return this;
    }

    protected BiomeBase b(int i) {
        this.o = i;
        return this;
    }

    public static BiomeBase a(double d0, double d1) {
        return BIOME_CLIMATE_SELECTION_SERVICE.lookupBiome(x, d0, d1);
    }

    public static BiomeBase a(float f, float f1) {
        return BIOME_CLIMATE_SELECTION_SERVICE.selectClimateBiome(f, f1);
    }

    public List a(EnumCreatureType enumcreaturetype) {
        return BIOME_SPAWN_LIST_LOOKUP_SERVICE.resolveSpawnList(enumcreaturetype, this.s, this.t, this.u);
    }

    public boolean c() {
        return this.v;
    }

    public boolean d() {
        return this.v ? false : this.w;
    }

    static {
        a();
    }
}
