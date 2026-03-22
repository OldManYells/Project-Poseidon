package net.minecraft.server;

import com.legacyminecraft.poseidon.world.gen.BigTreeGenerationBehaviour;
import org.bukkit.BlockChangeDelegate;

import java.util.Random;

public class WorldGenBigTree extends WorldGenerator {

    static final byte[] a = new byte[] { (byte) 2, (byte) 0, (byte) 0, (byte) 1, (byte) 2, (byte) 1};
    Random b = new Random();
    BlockChangeDelegate c;
    int[] d = new int[] { 0, 0, 0};
    int e = 0;
    int f;
    double g = 0.618D;
    double h = 1.0D;
    double i = 0.381D;
    double j = 1.0D;
    double k = 1.0D;
    int l = 1;
    int m = 12;
    int n = 4;
    int[][] o;

    private final BigTreeGenerationBehaviour behaviour = new BigTreeGenerationBehaviour();

    public WorldGenBigTree() {}

    private void syncToBehaviour() {
        if (this.c != null) {
            this.behaviour.setWorld(this.c);
        }
        this.behaviour.setBasePosition(this.d[0], this.d[1], this.d[2]);
        this.behaviour.setHeightLimit(this.e);
        this.behaviour.setHeight(this.f);
        this.behaviour.setHeightAttenuation(this.g);
        this.behaviour.setBranchDensity(this.h);
        this.behaviour.setBranchSlope(this.i);
        this.behaviour.setScaleWidth(this.j);
        this.behaviour.setLeafDensity(this.k);
        this.behaviour.setTrunkSize(this.l);
        this.behaviour.setHeightLimitLimit(this.m);
        this.behaviour.setLeafDistanceLimit(this.n);
        this.behaviour.setLeafNodes(this.o);
    }

    private void syncFromBehaviour() {
        int[] base = this.behaviour.getBasePosition();
        this.d[0] = base[0];
        this.d[1] = base[1];
        this.d[2] = base[2];
        this.e = this.behaviour.getHeightLimit();
        this.f = this.behaviour.getHeight();
        this.g = this.behaviour.getHeightAttenuation();
        this.h = this.behaviour.getBranchDensity();
        this.i = this.behaviour.getBranchSlope();
        this.j = this.behaviour.getScaleWidth();
        this.k = this.behaviour.getLeafDensity();
        this.l = this.behaviour.getTrunkSize();
        this.m = this.behaviour.getHeightLimitLimit();
        this.n = this.behaviour.getLeafDistanceLimit();
        this.o = this.behaviour.getLeafNodes();
    }

    void a() {
        this.syncToBehaviour();
        this.behaviour.prepareLeafNodes();
        this.syncFromBehaviour();
    }

    void a(int i, int j, int k, float f, byte b0, int l) {
        this.syncToBehaviour();
        this.behaviour.generateLeafDisc(i, j, k, f, b0, l);
        this.syncFromBehaviour();
    }

    float a(int i) {
        this.syncToBehaviour();
        float result = this.behaviour.computeLeafNodeLayerSize(i);
        this.syncFromBehaviour();
        return result;
    }

    float b(int i) {
        this.syncToBehaviour();
        float result = this.behaviour.computeLeafClusterRadius(i);
        this.syncFromBehaviour();
        return result;
    }

    void a(int i, int j, int k) {
        this.syncToBehaviour();
        this.behaviour.generateLeafCluster(i, j, k);
        this.syncFromBehaviour();
    }

    void a(int[] aint, int[] aint1, int i) {
        this.syncToBehaviour();
        this.behaviour.drawLine(aint, aint1, i);
        this.syncFromBehaviour();
    }

    void b() {
        this.syncToBehaviour();
        this.behaviour.generateLeafClusters();
        this.syncFromBehaviour();
    }

    boolean c(int i) {
        this.syncToBehaviour();
        boolean result = this.behaviour.shouldGenerateLeafNodeBase(i);
        this.syncFromBehaviour();
        return result;
    }

    void c() {
        this.syncToBehaviour();
        this.behaviour.generateTrunk();
        this.syncFromBehaviour();
    }

    void d() {
        this.syncToBehaviour();
        this.behaviour.generateLeafNodeBases();
        this.syncFromBehaviour();
    }

    int a(int[] aint, int[] aint1) {
        this.syncToBehaviour();
        int result = this.behaviour.checkLineClear(aint, aint1);
        this.syncFromBehaviour();
        return result;
    }

    boolean e() {
        this.syncToBehaviour();
        boolean result = this.behaviour.validateLocation();
        this.syncFromBehaviour();
        return result;
    }

    public void a(double d0, double d1, double d2) {
        this.syncToBehaviour();
        this.behaviour.configureScale(d0, d1, d2);
        this.syncFromBehaviour();
    }

    public boolean a(World world, Random random, int i, int j, int k) {
        return this.generate((BlockChangeDelegate) world, random, i, j, k);
    }

    public boolean generate(BlockChangeDelegate world, Random random, int i, int j, int k) {
        this.c = world;
        this.syncToBehaviour();
        boolean result = this.behaviour.generate(world, random, i, j, k);
        this.syncFromBehaviour();
        return result;
    }
}
