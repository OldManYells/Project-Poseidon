package com.legacyminecraft.poseidon.world.stats;


/**
 * Canonical bootstrap for legacy core (non-item/non-block) statistics.
 */
public final class StatisticBootstrap {
    private static final StatisticBootstrap INSTANCE = new StatisticBootstrap();
    private final StatisticTranslationBehaviour statisticTranslationBehaviour = StatisticTranslationBehaviour.getInstance();
    private final CounterStatisticFactoryBehaviour counterStatisticFactoryBehaviour = CounterStatisticFactoryBehaviour.getInstance();

    private StatisticBootstrap() {
    }

    public static StatisticBootstrap getInstance() {
        return INSTANCE;
    }

    public StatisticSet bootstrapCoreStatistics() {
        Statistic startGame = counterStatisticFactoryBehaviour.createCore(1000, statisticTranslationBehaviour.translate("stat.startGame"));
        Statistic createWorld = counterStatisticFactoryBehaviour.createCore(1001, statisticTranslationBehaviour.translate("stat.createWorld"));
        Statistic loadWorld = counterStatisticFactoryBehaviour.createCore(1002, statisticTranslationBehaviour.translate("stat.loadWorld"));
        Statistic joinMultiplayer = counterStatisticFactoryBehaviour.createCore(1003, statisticTranslationBehaviour.translate("stat.joinMultiplayer"));
        Statistic leaveGame = counterStatisticFactoryBehaviour.createCore(1004, statisticTranslationBehaviour.translate("stat.leaveGame"));
        Statistic playOneMinute = counterStatisticFactoryBehaviour.createCore(1100, statisticTranslationBehaviour.translate("stat.playOneMinute"), Statistic.j);
        Statistic walkOneCm = counterStatisticFactoryBehaviour.createCore(2000, statisticTranslationBehaviour.translate("stat.walkOneCm"), Statistic.k);
        Statistic swimOneCm = counterStatisticFactoryBehaviour.createCore(2001, statisticTranslationBehaviour.translate("stat.swimOneCm"), Statistic.k);
        Statistic fallOneCm = counterStatisticFactoryBehaviour.createCore(2002, statisticTranslationBehaviour.translate("stat.fallOneCm"), Statistic.k);
        Statistic climbOneCm = counterStatisticFactoryBehaviour.createCore(2003, statisticTranslationBehaviour.translate("stat.climbOneCm"), Statistic.k);
        Statistic flyOneCm = counterStatisticFactoryBehaviour.createCore(2004, statisticTranslationBehaviour.translate("stat.flyOneCm"), Statistic.k);
        Statistic diveOneCm = counterStatisticFactoryBehaviour.createCore(2005, statisticTranslationBehaviour.translate("stat.diveOneCm"), Statistic.k);
        Statistic minecartOneCm = counterStatisticFactoryBehaviour.createCore(2006, statisticTranslationBehaviour.translate("stat.minecartOneCm"), Statistic.k);
        Statistic boatOneCm = counterStatisticFactoryBehaviour.createCore(2007, statisticTranslationBehaviour.translate("stat.boatOneCm"), Statistic.k);
        Statistic pigOneCm = counterStatisticFactoryBehaviour.createCore(2008, statisticTranslationBehaviour.translate("stat.pigOneCm"), Statistic.k);
        Statistic jump = counterStatisticFactoryBehaviour.createCore(2010, statisticTranslationBehaviour.translate("stat.jump"));
        Statistic drop = counterStatisticFactoryBehaviour.createCore(2011, statisticTranslationBehaviour.translate("stat.drop"));
        Statistic damageDealt = counterStatisticFactoryBehaviour.createCounterOnly(2020, statisticTranslationBehaviour.translate("stat.damageDealt"));
        Statistic damageTaken = counterStatisticFactoryBehaviour.createCounterOnly(2021, statisticTranslationBehaviour.translate("stat.damageTaken"));
        Statistic deaths = counterStatisticFactoryBehaviour.createCounterOnly(2022, statisticTranslationBehaviour.translate("stat.deaths"));
        Statistic mobKills = counterStatisticFactoryBehaviour.createCounterOnly(2023, statisticTranslationBehaviour.translate("stat.mobKills"));
        Statistic playerKills = counterStatisticFactoryBehaviour.createCounterOnly(2024, statisticTranslationBehaviour.translate("stat.playerKills"));
        Statistic fishCaught = counterStatisticFactoryBehaviour.createCounterOnly(2025, statisticTranslationBehaviour.translate("stat.fishCaught"));

        return new StatisticSet(
                startGame,
                createWorld,
                loadWorld,
                joinMultiplayer,
                leaveGame,
                playOneMinute,
                walkOneCm,
                swimOneCm,
                fallOneCm,
                climbOneCm,
                flyOneCm,
                diveOneCm,
                minecartOneCm,
                boatOneCm,
                pigOneCm,
                jump,
                drop,
                damageDealt,
                damageTaken,
                deaths,
                mobKills,
                playerKills,
                fishCaught
        );
    }

    public static final class StatisticSet {
        private final Statistic startGame;
        private final Statistic createWorld;
        private final Statistic loadWorld;
        private final Statistic joinMultiplayer;
        private final Statistic leaveGame;
        private final Statistic playOneMinute;
        private final Statistic walkOneCm;
        private final Statistic swimOneCm;
        private final Statistic fallOneCm;
        private final Statistic climbOneCm;
        private final Statistic flyOneCm;
        private final Statistic diveOneCm;
        private final Statistic minecartOneCm;
        private final Statistic boatOneCm;
        private final Statistic pigOneCm;
        private final Statistic jump;
        private final Statistic drop;
        private final Statistic damageDealt;
        private final Statistic damageTaken;
        private final Statistic deaths;
        private final Statistic mobKills;
        private final Statistic playerKills;
        private final Statistic fishCaught;

        private StatisticSet(
                Statistic startGame,
                Statistic createWorld,
                Statistic loadWorld,
                Statistic joinMultiplayer,
                Statistic leaveGame,
                Statistic playOneMinute,
                Statistic walkOneCm,
                Statistic swimOneCm,
                Statistic fallOneCm,
                Statistic climbOneCm,
                Statistic flyOneCm,
                Statistic diveOneCm,
                Statistic minecartOneCm,
                Statistic boatOneCm,
                Statistic pigOneCm,
                Statistic jump,
                Statistic drop,
                Statistic damageDealt,
                Statistic damageTaken,
                Statistic deaths,
                Statistic mobKills,
                Statistic playerKills,
                Statistic fishCaught
        ) {
            this.startGame = startGame;
            this.createWorld = createWorld;
            this.loadWorld = loadWorld;
            this.joinMultiplayer = joinMultiplayer;
            this.leaveGame = leaveGame;
            this.playOneMinute = playOneMinute;
            this.walkOneCm = walkOneCm;
            this.swimOneCm = swimOneCm;
            this.fallOneCm = fallOneCm;
            this.climbOneCm = climbOneCm;
            this.flyOneCm = flyOneCm;
            this.diveOneCm = diveOneCm;
            this.minecartOneCm = minecartOneCm;
            this.boatOneCm = boatOneCm;
            this.pigOneCm = pigOneCm;
            this.jump = jump;
            this.drop = drop;
            this.damageDealt = damageDealt;
            this.damageTaken = damageTaken;
            this.deaths = deaths;
            this.mobKills = mobKills;
            this.playerKills = playerKills;
            this.fishCaught = fishCaught;
        }

        public Statistic getStartGame() {
            return startGame;
        }

        public Statistic getCreateWorld() {
            return createWorld;
        }

        public Statistic getLoadWorld() {
            return loadWorld;
        }

        public Statistic getJoinMultiplayer() {
            return joinMultiplayer;
        }

        public Statistic getLeaveGame() {
            return leaveGame;
        }

        public Statistic getPlayOneMinute() {
            return playOneMinute;
        }

        public Statistic getWalkOneCm() {
            return walkOneCm;
        }

        public Statistic getSwimOneCm() {
            return swimOneCm;
        }

        public Statistic getFallOneCm() {
            return fallOneCm;
        }

        public Statistic getClimbOneCm() {
            return climbOneCm;
        }

        public Statistic getFlyOneCm() {
            return flyOneCm;
        }

        public Statistic getDiveOneCm() {
            return diveOneCm;
        }

        public Statistic getMinecartOneCm() {
            return minecartOneCm;
        }

        public Statistic getBoatOneCm() {
            return boatOneCm;
        }

        public Statistic getPigOneCm() {
            return pigOneCm;
        }

        public Statistic getJump() {
            return jump;
        }

        public Statistic getDrop() {
            return drop;
        }

        public Statistic getDamageDealt() {
            return damageDealt;
        }

        public Statistic getDamageTaken() {
            return damageTaken;
        }

        public Statistic getDeaths() {
            return deaths;
        }

        public Statistic getMobKills() {
            return mobKills;
        }

        public Statistic getPlayerKills() {
            return playerKills;
        }

        public Statistic getFishCaught() {
            return fishCaught;
        }
    }
}
