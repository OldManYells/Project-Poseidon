package com.legacyminecraft.poseidon.world.gen;


import java.util.Random;

public final class BigTreeGenerationBehaviour {
    private static final byte[] AXIS_LOOKUP = new byte[] { (byte) 2, (byte) 0, (byte) 0, (byte) 1, (byte) 2, (byte) 1};

    private final Random random = new Random();
    private BlockChangeDelegate world;
    private final int[] basePosition = new int[] { 0, 0, 0};

    private int heightLimit;
    private int height;
    private double heightAttenuation = 0.618D;
    private double branchDensity = 1.0D;
    private double branchSlope = 0.381D;
    private double scaleWidth = 1.0D;
    private double leafDensity = 1.0D;
    private int trunkSize = 1;
    private int heightLimitLimit = 12;
    private int leafDistanceLimit = 4;
    private int[][] leafNodes;

    public void setRandomSeed(Random sourceRandom) {
        long seed = sourceRandom.nextLong();
        this.random.setSeed(seed);
    }

    public void setBasePosition(int x, int y, int z) {
        this.basePosition[0] = x;
        this.basePosition[1] = y;
        this.basePosition[2] = z;
    }

    public void setWorld(BlockChangeDelegate world) {
        this.world = world;
    }

    public void setHeightLimitIfUnset() {
        if (this.heightLimit == 0) {
            this.heightLimit = 5 + this.random.nextInt(this.heightLimitLimit);
        }
    }

    public void configureScale(double d0, double d1, double d2) {
        this.heightLimitLimit = (int) (d0 * 12.0D);
        if (d0 > 0.5D) {
            this.leafDistanceLimit = 5;
        }

        this.scaleWidth = d1;
        this.leafDensity = d2;
    }

    public boolean generate(World world, Random random, int i, int j, int k) {
        return this.generate((BlockChangeDelegate) world, random, i, j, k);
    }

    public boolean generate(BlockChangeDelegate world, Random random, int i, int j, int k) {
        this.setWorld(world);
        this.setRandomSeed(random);
        this.setBasePosition(i, j, k);
        this.setHeightLimitIfUnset();

        if (!this.validateLocation()) {
            return false;
        }

        this.prepareLeafNodes();
        this.generateLeafClusters();
        this.generateTrunk();
        this.generateLeafNodeBases();
        return true;
    }

    public void prepareLeafNodes() {
        this.height = (int) ((double) this.heightLimit * this.heightAttenuation);
        if (this.height >= this.heightLimit) {
            this.height = this.heightLimit - 1;
        }

        int maxLeafNodesPerLayer = (int) (1.382D + Math.pow(this.leafDensity * (double) this.heightLimit / 13.0D, 2.0D));

        if (maxLeafNodesPerLayer < 1) {
            maxLeafNodesPerLayer = 1;
        }

        int[][] nodes = new int[maxLeafNodesPerLayer * this.heightLimit][4];
        int canopyY = this.basePosition[1] + this.heightLimit - this.leafDistanceLimit;
        int generatedNodes = 1;
        int trunkTopY = this.basePosition[1] + this.height;
        int layer = canopyY - this.basePosition[1];

        nodes[0][0] = this.basePosition[0];
        nodes[0][1] = canopyY;
        nodes[0][2] = this.basePosition[2];
        nodes[0][3] = trunkTopY;
        --canopyY;

        while (layer >= 0) {
            int attempts = 0;
            float layerSize = this.computeLeafNodeLayerSize(layer);

            if (layerSize < 0.0F) {
                --canopyY;
                --layer;
            } else {
                for (double offset = 0.5D; attempts < maxLeafNodesPerLayer; ++attempts) {
                    double radialDistance = this.scaleWidth * (double) layerSize * ((double) this.random.nextFloat() + 0.328D);
                    double angle = (double) this.random.nextFloat() * 2.0D * 3.14159D;
                    int leafX = MathHelper.floor(radialDistance * Math.sin(angle) + (double) this.basePosition[0] + offset);
                    int leafZ = MathHelper.floor(radialDistance * Math.cos(angle) + (double) this.basePosition[2] + offset);
                    int[] leafNode = new int[] { leafX, canopyY, leafZ};
                    int[] leafTop = new int[] { leafX, canopyY + this.leafDistanceLimit, leafZ};

                    if (this.checkLineClear(leafNode, leafTop) == -1) {
                        int[] branchBase = new int[] { this.basePosition[0], this.basePosition[1], this.basePosition[2]};
                        double horizontalDistance = Math.sqrt(Math.pow((double) Math.abs(this.basePosition[0] - leafNode[0]), 2.0D)
                                + Math.pow((double) Math.abs(this.basePosition[2] - leafNode[2]), 2.0D));
                        double branchDrop = horizontalDistance * this.branchSlope;

                        if ((double) leafNode[1] - branchDrop > (double) trunkTopY) {
                            branchBase[1] = trunkTopY;
                        } else {
                            branchBase[1] = (int) ((double) leafNode[1] - branchDrop);
                        }

                        if (this.checkLineClear(branchBase, leafNode) == -1) {
                            nodes[generatedNodes][0] = leafX;
                            nodes[generatedNodes][1] = canopyY;
                            nodes[generatedNodes][2] = leafZ;
                            nodes[generatedNodes][3] = branchBase[1];
                            ++generatedNodes;
                        }
                    }
                }

                --canopyY;
                --layer;
            }
        }

        this.leafNodes = new int[generatedNodes][4];
        System.arraycopy(nodes, 0, this.leafNodes, 0, generatedNodes);
    }

    public void generateLeafDisc(int i, int j, int k, float radius, byte axis, int blockId) {
        int i1 = (int) ((double) radius + 0.618D);
        byte axis1 = AXIS_LOOKUP[axis];
        byte axis2 = AXIS_LOOKUP[axis + 3];
        int[] center = new int[] { i, j, k};
        int[] pos = new int[] { 0, 0, 0};
        int j1 = -i1;

        for (pos[axis] = center[axis]; j1 <= i1; ++j1) {
            pos[axis1] = center[axis1] + j1;

            for (int k1 = -i1; k1 <= i1; ++k1) {
                double d0 = Math.sqrt(Math.pow((double) Math.abs(j1) + 0.5D, 2.0D)
                        + Math.pow((double) Math.abs(k1) + 0.5D, 2.0D));

                if (d0 <= (double) radius) {
                    pos[axis2] = center[axis2] + k1;
                    int existing = this.world.getTypeId(pos[0], pos[1], pos[2]);

                    if (existing == 0 || existing == 18) {
                        this.world.setRawTypeId(pos[0], pos[1], pos[2], blockId);
                    }
                }
            }
        }
    }

    public float computeLeafNodeLayerSize(int level) {
        if ((double) level < (double) ((float) this.heightLimit) * 0.3D) {
            return -1.618F;
        }

        float halfHeight = (float) this.heightLimit / 2.0F;
        float y = (float) this.heightLimit / 2.0F - (float) level;
        float radius;

        if (y == 0.0F) {
            radius = halfHeight;
        } else if (Math.abs(y) >= halfHeight) {
            radius = 0.0F;
        } else {
            radius = (float) Math.sqrt(Math.pow((double) Math.abs(halfHeight), 2.0D) - Math.pow((double) Math.abs(y), 2.0D));
        }

        return radius * 0.5F;
    }

    public float computeLeafClusterRadius(int i) {
        return i >= 0 && i < this.leafDistanceLimit ? (i != 0 && i != this.leafDistanceLimit - 1 ? 3.0F : 2.0F) : -1.0F;
    }

    public void generateLeafCluster(int i, int j, int k) {
        for (int y = j; y < j + this.leafDistanceLimit; ++y) {
            float radius = this.computeLeafClusterRadius(y - j);
            this.generateLeafDisc(i, y, k, radius, (byte) 1, 18);
        }
    }

    public void drawLine(int[] from, int[] to, int blockId) {
        int[] delta = new int[] { 0, 0, 0};
        byte primaryAxis = 0;

        for (byte axis = 0; axis < 3; ++axis) {
            delta[axis] = to[axis] - from[axis];
            if (Math.abs(delta[axis]) > Math.abs(delta[primaryAxis])) {
                primaryAxis = axis;
            }
        }

        if (delta[primaryAxis] != 0) {
            byte axis1 = AXIS_LOOKUP[primaryAxis];
            byte axis2 = AXIS_LOOKUP[primaryAxis + 3];
            byte direction = delta[primaryAxis] > 0 ? (byte) 1 : (byte) -1;
            double slope1 = (double) delta[axis1] / (double) delta[primaryAxis];
            double slope2 = (double) delta[axis2] / (double) delta[primaryAxis];
            int[] pos = new int[] { 0, 0, 0};

            for (int step = 0, end = delta[primaryAxis] + direction; step != end; step += direction) {
                pos[primaryAxis] = MathHelper.floor((double) (from[primaryAxis] + step) + 0.5D);
                pos[axis1] = MathHelper.floor((double) from[axis1] + (double) step * slope1 + 0.5D);
                pos[axis2] = MathHelper.floor((double) from[axis2] + (double) step * slope2 + 0.5D);
                this.world.setRawTypeId(pos[0], pos[1], pos[2], blockId);
            }
        }
    }

    public void generateLeafClusters() {
        for (int[] leafNode : this.leafNodes) {
            this.generateLeafCluster(leafNode[0], leafNode[1], leafNode[2]);
        }
    }

    public boolean shouldGenerateLeafNodeBase(int i) {
        return (double) i >= (double) this.heightLimit * 0.2D;
    }

    public void generateTrunk() {
        int i = this.basePosition[0];
        int j = this.basePosition[1];
        int k = this.basePosition[1] + this.height;
        int l = this.basePosition[2];
        int[] from = new int[] { i, j, l};
        int[] to = new int[] { i, k, l};

        this.drawLine(from, to, 17);
        if (this.trunkSize == 2) {
            ++from[0];
            ++to[0];
            this.drawLine(from, to, 17);
            ++from[2];
            ++to[2];
            this.drawLine(from, to, 17);
            --from[0];
            --to[0];
            this.drawLine(from, to, 17);
        }
    }

    public void generateLeafNodeBases() {
        int[] trunkBase = new int[] { this.basePosition[0], this.basePosition[1], this.basePosition[2]};

        for (int[] leafNode : this.leafNodes) {
            int[] leafPos = new int[] { leafNode[0], leafNode[1], leafNode[2]};
            trunkBase[1] = leafNode[3];
            int offset = trunkBase[1] - this.basePosition[1];

            if (this.shouldGenerateLeafNodeBase(offset)) {
                this.drawLine(trunkBase, leafPos, 17);
            }
        }
    }

    public int checkLineClear(int[] from, int[] to) {
        int[] delta = new int[] { 0, 0, 0};
        byte primaryAxis = 0;

        for (byte axis = 0; axis < 3; ++axis) {
            delta[axis] = to[axis] - from[axis];
            if (Math.abs(delta[axis]) > Math.abs(delta[primaryAxis])) {
                primaryAxis = axis;
            }
        }

        if (delta[primaryAxis] == 0) {
            return -1;
        }

        byte axis1 = AXIS_LOOKUP[primaryAxis];
        byte axis2 = AXIS_LOOKUP[primaryAxis + 3];
        byte direction = delta[primaryAxis] > 0 ? (byte) 1 : (byte) -1;
        double slope1 = (double) delta[axis1] / (double) delta[primaryAxis];
        double slope2 = (double) delta[axis2] / (double) delta[primaryAxis];
        int[] pos = new int[] { 0, 0, 0};
        int step = 0;
        int end;

        for (end = delta[primaryAxis] + direction; step != end; step += direction) {
            pos[primaryAxis] = from[primaryAxis] + step;
            pos[axis1] = MathHelper.floor((double) from[axis1] + (double) step * slope1);
            pos[axis2] = MathHelper.floor((double) from[axis2] + (double) step * slope2);
            int blockId = this.world.getTypeId(pos[0], pos[1], pos[2]);

            if (blockId != 0 && blockId != 18) {
                break;
            }
        }

        return step == end ? -1 : Math.abs(step);
    }

    public boolean validateLocation() {
        int[] trunkBase = new int[] { this.basePosition[0], this.basePosition[1], this.basePosition[2]};
        int[] trunkTop = new int[] { this.basePosition[0], this.basePosition[1] + this.heightLimit - 1, this.basePosition[2]};
        int groundBlockId = this.world.getTypeId(this.basePosition[0], this.basePosition[1] - 1, this.basePosition[2]);

        if (groundBlockId != 2 && groundBlockId != 3) {
            return false;
        }

        int clearHeight = this.checkLineClear(trunkBase, trunkTop);
        if (clearHeight == -1) {
            return true;
        }

        if (clearHeight < 6) {
            return false;
        }

        this.heightLimit = clearHeight;
        return true;
    }

    public int getHeightLimit() {
        return this.heightLimit;
    }

    public void setHeightLimit(int heightLimit) {
        this.heightLimit = heightLimit;
    }

    public int getHeight() {
        return this.height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public double getHeightAttenuation() {
        return this.heightAttenuation;
    }

    public void setHeightAttenuation(double heightAttenuation) {
        this.heightAttenuation = heightAttenuation;
    }

    public double getBranchDensity() {
        return this.branchDensity;
    }

    public void setBranchDensity(double branchDensity) {
        this.branchDensity = branchDensity;
    }

    public double getBranchSlope() {
        return this.branchSlope;
    }

    public void setBranchSlope(double branchSlope) {
        this.branchSlope = branchSlope;
    }

    public double getScaleWidth() {
        return this.scaleWidth;
    }

    public void setScaleWidth(double scaleWidth) {
        this.scaleWidth = scaleWidth;
    }

    public double getLeafDensity() {
        return this.leafDensity;
    }

    public void setLeafDensity(double leafDensity) {
        this.leafDensity = leafDensity;
    }

    public int getTrunkSize() {
        return this.trunkSize;
    }

    public void setTrunkSize(int trunkSize) {
        this.trunkSize = trunkSize;
    }

    public int getHeightLimitLimit() {
        return this.heightLimitLimit;
    }

    public void setHeightLimitLimit(int heightLimitLimit) {
        this.heightLimitLimit = heightLimitLimit;
    }

    public int getLeafDistanceLimit() {
        return this.leafDistanceLimit;
    }

    public void setLeafDistanceLimit(int leafDistanceLimit) {
        this.leafDistanceLimit = leafDistanceLimit;
    }

    public int[][] getLeafNodes() {
        return this.leafNodes;
    }

    public void setLeafNodes(int[][] leafNodes) {
        this.leafNodes = leafNodes;
    }

    public int[] getBasePosition() {
        return this.basePosition;
    }
}
