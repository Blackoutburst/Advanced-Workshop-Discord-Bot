package com.blackoutburst.wsbot.utils;

import java.util.List;

public class Player {

    private String uuid;
    private String name;
    private int gameCount;
    private int roundCount;
    private List<MapStats> maps;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public int getGameCount() {
        return gameCount;
    }

    public int getRoundCount() {
        return roundCount;
    }

    public List<MapStats> getMaps() {
        return maps;
    }

    public static class MapStats {
        private String name;
        private int gameCount;
        private int roundCount;
        private double time;
        private int m1crafts;
        private int s90crafts;
        private int m2crafts;
        private int m5crafts;
        private double allCrafts;
        private List<Craft> crafts;

        public String getName() {
            return name;
        }

        public int getGameCount() {
            return gameCount;
        }

        public int getRoundCount() {
            return roundCount;
        }

        public double getTime() {
            return time;
        }

        public int getM1crafts() {
            return m1crafts;
        }

        public int getS90crafts() {
            return s90crafts;
        }

        public int getM2crafts() {
            return m2crafts;
        }

        public int getM5crafts() {
            return m5crafts;
        }

        public double getAllCrafts() {
            return allCrafts;
        }

        public List<Craft> getCrafts() {
            return crafts;
        }
    }

    public static class Craft {
        private String name;
        private double time;

        public String getName() {
            return name;
        }

        public double getTime() {
            return time;
        }
    }

}
