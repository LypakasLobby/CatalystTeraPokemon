package com.lypaka.catalystterapokemon.Raids;

import java.util.List;

public class RaidRewardCommandSet {

    private final double chance;
    private final List<String> commands;

    public RaidRewardCommandSet (double chance, List<String> commands) {

        this.chance = chance;
        this.commands = commands;

    }

    public double getChance() {

        return this.chance;

    }

    public List<String> getCommands() {

        return this.commands;

    }

}
