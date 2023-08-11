package com.lypaka.catalystterapokemon.Raids;

import java.util.Map;

public class TeraRaidPokemon {

    private final double chance;
    private final String form;
    private final String palette;
    private final String species;
    private final RaidRewards rewards;
    private final float scale;
    private final int starLevel;
    private final Map<String, Double> teraTypeChances;

    public TeraRaidPokemon (double chance, String form, String palette, String species, RaidRewards rewards, float scale, int starLevel, Map<String, Double> teraTypeChances) {

        this.chance = chance;
        this.form = form;
        this.palette = palette;
        this.species = species;
        this.rewards = rewards;
        this.scale = scale;
        this.starLevel = starLevel;
        this.teraTypeChances = teraTypeChances;

    }

    public double getChance() {

        return this.chance;

    }

    public String getForm() {

        return this.form;

    }

    public String getPalette() {

        return this.palette;

    }

    public String getSpecies() {

        return this.species;

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
