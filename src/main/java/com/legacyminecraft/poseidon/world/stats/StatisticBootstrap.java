package com.legacyminecraft.poseidon.world.stats;

import net.minecraft.server.CounterStatistic;
import net.minecraft.server.Statistic;

/**
 * Canonical bootstrap for legacy core (non-item/non-block) statistics.
 */
public final class StatisticBootstrap {
    private static final StatisticBootstrap INSTANCE = new StatisticBootstrap();
    private final StatisticTranslationBehaviour statisticTranslationBehaviour = StatisticTranslationBehaviour.getInstance();

    private StatisticBootstrap() {
    }

    public static StatisticBootstrap getInstance() {
        return INSTANCE;
    }

    public StatisticSet bootstrapCoreStatistics() {
        Statistic startGame = (new CounterStatistic(1000, statisticTranslationBehaviour.translate("stat.startGame"))).e().d();
        Statistic createWorld = (new CounterStatistic(1001, statisticTranslationBehaviour.translate("stat.createWorld"))).e().d();
        Statistic loadWorld = (new CounterStatistic(1002, statisticTranslationBehaviour.translate("stat.loadWorld"))).e().d();
        Statistic joinMultiplayer = (new CounterStatistic(1003, statisticTranslationBehaviour.translate("stat.joinMultiplayer"))).e().d();
        Statistic leaveGame = (new CounterStatistic(1004, statisticTranslationBehaviour.translate("stat.leaveGame"))).e().d();
        Statistic playOneMinute = (new CounterStatistic(1100, statisticTranslationBehaviour.translate("stat.playOneMinute"), Statistic.j)).e().d();
        Statistic walkOneCm = (new CounterStatistic(2000, statisticTranslationBehaviour.translate("stat.walkOneCm"), Statistic.k)).e().d();
        Statistic swimOneCm = (new CounterStatistic(2001, statisticTranslationBehaviour.translate("stat.swimOneCm"), Statistic.k)).e().d();
        Statistic fallOneCm = (new CounterStatistic(2002, statisticTranslationBehaviour.translate("stat.fallOneCm"), Statistic.k)).e().d();
        Statistic climbOneCm = (new CounterStatistic(2003, statisticTranslationBehaviour.translate("stat.climbOneCm"), Statistic.k)).e().d();
        Statistic flyOneCm = (new CounterStatistic(2004, statisticTranslationBehaviour.translate("stat.flyOneCm"), Statistic.k)).e().d();
        Statistic diveOneCm = (new CounterStatistic(2005, statisticTranslationBehaviour.translate("stat.diveOneCm"), Statistic.k)).e().d();
        Statistic minecartOneCm = (new CounterStatistic(2006, statisticTranslationBehaviour.translate("stat.minecartOneCm"), Statistic.k)).e().d();
        Statistic boatOneCm = (new CounterStatistic(2007, statisticTranslationBehaviour.translate("stat.boatOneCm"), Statistic.k)).e().d();
        Statistic pigOneCm = (new CounterStatistic(2008, statisticTranslationBehaviour.translate("stat.pigOneCm"), Statistic.k)).e().d();
        Statistic jump = (new CounterStatistic(2010, statisticTranslationBehaviour.translate("stat.jump"))).e().d();
        Statistic drop = (new CounterStatistic(2011, statisticTranslationBehaviour.translate("stat.drop"))).e().d();
        Statistic damageDealt = (new CounterStatistic(2020, statisticTranslationBehaviour.translate("stat.damageDealt"))).d();
        Statistic damageTaken = (new CounterStatistic(2021, statisticTranslationBehaviour.translate("stat.damageTaken"))).d();
        Statistic deaths = (new CounterStatistic(2022, statisticTranslationBehaviour.translate("stat.deaths"))).d();
        Statistic mobKills = (new CounterStatistic(2023, statisticTranslationBehaviour.translate("stat.mobKills"))).d();
        Statistic playerKills = (new CounterStatistic(2024, statisticTranslationBehaviour.translate("stat.playerKills"))).d();
        Statistic fishCaught = (new CounterStatistic(2025, statisticTranslationBehaviour.translate("stat.fishCaught"))).d();

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
