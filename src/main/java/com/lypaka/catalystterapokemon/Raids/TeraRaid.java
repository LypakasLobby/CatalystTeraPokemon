package com.lypaka.catalystterapokemon.Raids;

import java.util.List;

public class TeraRaid {

    private final int defaultStarLevel;
    private final List<String> denLocations;
    private final List<TeraRaidPokemon> raidPokemon;

    public TeraRaid (int defaultStarLevel, List<String> denLocations, List<TeraRaidPokemon> raidPokemon) {

        this.defaultStarLevel = defaultStarLevel;
        this.denLocations = denLocations;
        this.raidPokemon = raidPokemon;

    }

    public int getDefaultStarLevel() {

        return this.defaultStarLevel;

    }

    public List<String> getDenLocations() {

        return this.denLocations;

    }

    public List<TeraRaidPokemon> getRaidPokemon() {

        return this.raidPokemon;

    }

}
