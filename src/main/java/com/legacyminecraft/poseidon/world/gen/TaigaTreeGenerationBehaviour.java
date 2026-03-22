package com.legacyminecraft.poseidon.world.gen;

import net.minecraft.server.Block;
import org.bukkit.BlockChangeDelegate;

import java.util.Random;

public final class TaigaTreeGenerationBehaviour {
    private static final TaigaTreeGenerationBehaviour INSTANCE = new TaigaTreeGenerationBehaviour();

    private TaigaTreeGenerationBehaviour() {
    }

    public static TaigaTreeGenerationBehaviour getInstance() {
        return INSTANCE;
    }

    public boolean generateTaiga1(BlockChangeDelegate world, Random random, int i, int j, int k) {
        int l = random.nextInt(5) + 7;
        int i1 = l - random.nextInt(2) - 3;
        int j1 = l - i1;
        int k1 = 1 + random.nextInt(j1 + 1);
        boolean flag = true;

        if (j >= 1 && j + l + 1 <= 128) {
            int l1;
            int i2;
            int j2;
            int k2;
            int l2;

            for (l1 = j; l1 <= j + 1 + l && flag; ++l1) {
                if (l1 - j < i1) {
                    l2 = 0;
                } else {
                    l2 = k1;
                }

                for (i2 = i - l2; i2 <= i + l2 && flag; ++i2) {
                    for (j2 = k - l2; j2 <= k + l2 && flag; ++j2) {
                        if (l1 >= 0 && l1 < 128) {
                            k2 = world.getTypeId(i2, l1, j2);
                            if (k2 != 0 && k2 != Block.LEAVES.id) {
                                flag = false;
                            }
                        } else {
                            flag = false;
                        }
                    }
                }
            }

            if (!flag) {
                return false;
            } else {
                l1 = world.getTypeId(i, j - 1, k);
                if ((l1 == Block.GRASS.id || l1 == Block.DIRT.id) && j < 128 - l - 1) {
                    world.setRawTypeId(i, j - 1, k, Block.DIRT.id);
                    l2 = 0;

                    for (i2 = j + l; i2 >= j + i1; --i2) {
                        for (j2 = i - l2; j2 <= i + l2; ++j2) {
                            k2 = j2 - i;

                            for (int i3 = k - l2; i3 <= k + l2; ++i3) {
                                int j3 = i3 - k;

                                if ((Math.abs(k2) != l2 || Math.abs(j3) != l2 || l2 <= 0) && !Block.o[world.getTypeId(j2, i2, i3)] && !Block.leafDecayBlacklist.contains(world.getTypeId(l1, i2, k2))) {
                                    world.setRawTypeIdAndData(j2, i2, i3, Block.LEAVES.id, 1);
                                }
                            }
                        }

                        if (l2 >= 1 && i2 == j + i1 + 1) {
                            --l2;
                        } else if (l2 < k1) {
                            ++l2;
                        }
                    }

                    for (i2 = 0; i2 < l - 1; ++i2) {
                        j2 = world.getTypeId(i, j + i2, k);
                        if (j2 == 0 || j2 == Block.LEAVES.id) {
                            world.setRawTypeIdAndData(i, j + i2, k, Block.LOG.id, 1);
                        }
                    }

                    return true;
                } else {
                    return false;
                }
            }
        } else {
            return false;
        }
    }

    public boolean generateTaiga2(BlockChangeDelegate world, Random random, int i, int j, int k) {
        int l = random.nextInt(4) + 6;
        int i1 = 1 + random.nextInt(2);
        int j1 = l - i1;
        int k1 = 2 + random.nextInt(2);
        boolean flag = true;

        if (j >= 1 && j + l + 1 <= 128) {
            int l1;
            int i2;
            int j2;
            int k2;

            for (l1 = j; l1 <= j + 1 + l && flag; ++l1) {
                if (l1 - j < i1) {
                    k2 = 0;
                } else {
                    k2 = k1;
                }

                for (i2 = i - k2; i2 <= i + k2 && flag; ++i2) {
                    for (int l2 = k - k2; l2 <= k + k2 && flag; ++l2) {
                        if (l1 >= 0 && l1 < 128) {
                            j2 = world.getTypeId(i2, l1, l2);
                            if (j2 != 0 && j2 != Block.LEAVES.id) {
                                flag = false;
                            }
                        } else {
                            flag = false;
                        }
                    }
                }
            }

            if (!flag) {
                return false;
            } else {
                l1 = world.getTypeId(i, j - 1, k);
                if ((l1 == Block.GRASS.id || l1 == Block.DIRT.id) && j < 128 - l - 1) {
                    world.setRawTypeId(i, j - 1, k, Block.DIRT.id);
                    k2 = random.nextInt(2);
                    i2 = 1;
                    byte b0 = 0;

                    int i3;
                    int j3;

                    for (j2 = 0; j2 <= j1; ++j2) {
                        j3 = j + l - j2;

                        for (i3 = i - k2; i3 <= i + k2; ++i3) {
                            int k3 = i3 - i;

                            for (int l3 = k - k2; l3 <= k + k2; ++l3) {
                                int i4 = l3 - k;

                                if ((Math.abs(k3) != k2 || Math.abs(i4) != k2 || k2 <= 0) && !Block.o[world.getTypeId(i3, j3, l3)] && !Block.leafDecayBlacklist.contains(world.getTypeId(l1, i2, k2))) {
                                    world.setRawTypeIdAndData(i3, j3, l3, Block.LEAVES.id, 1);
                                }
                            }
                        }

                        if (k2 >= i2) {
                            k2 = b0;
                            b0 = 1;
                            ++i2;
                            if (i2 > k1) {
                                i2 = k1;
                            }
                        } else {
                            ++k2;
                        }
                    }

                    j2 = random.nextInt(3);

                    for (j3 = 0; j3 < l - j2; ++j3) {
                        i3 = world.getTypeId(i, j + j3, k);
                        if (i3 == 0 || i3 == Block.LEAVES.id) {
                            world.setRawTypeIdAndData(i, j + j3, k, Block.LOG.id, 1);
                        }
                    }

                    return true;
                } else {
                    return false;
                }
            }
        } else {
            return false;
        }
    }
}
