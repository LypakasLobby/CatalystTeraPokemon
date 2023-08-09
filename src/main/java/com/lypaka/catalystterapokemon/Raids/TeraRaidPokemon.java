package com.lypaka.catalystterapokemon.Raids;

import java.util.Map;

public class TeraRaidPokemon {

    private final double chance;
    private final String palette;
    private final RaidRewards rewards;
    private final float scale;
    private final int starLevel;
    private final Map<String, Double> teraTypeChances;

    public TeraRaidPokemon (double chance, String palette, RaidRewards rewards, float scale, int starLevel, Map<String, Double> teraTypeChances) {

        this.chance = chance;
        this.palette = palette;
        this.rewards = rewards;
        this.scale = scale;
        this.starLevel = starLevel;
        this.teraTypeChances = teraTypeChances;

    }

    public double getChance() {

        return this.chance;

    }

    public String getPalette() {

        return this.palette;

    }

    public RaidRewards getRewards() {

        return this.rewards;

    }

    public float getScale() {

        return this.scale;

    }

    public int getStarLevel() {

        return this.starLevel;

    }

    public Map<String, Double> getTeraTypeChances() {

        return this.teraTypeChances;

    }

}
