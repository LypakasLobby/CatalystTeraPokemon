package com.lypaka.catalystterapokemon.Raids;

import java.util.Map;

public class RaidRewards {

    private final int max;
    private final int min;
    private final Map<String, RaidRewardCommandSet> commandSets;
    private final Map<String, Double> items;

    public RaidRewards (int max, int min, Map<String, RaidRewardCommandSet> commandSets, Map<String, Double> items) {

        this.max = max;
        this.min = min;
        this.commandSets = commandSets;
        this.items = items;

    }

    public int getMax() {

        return this.max;

    }

    public int getMin() {

        return this.min;

    }

    public Map<String, RaidRewardCommandSet> getCommandSets() {

        return this.commandSets;

    }

    public Map<String, Double> getItems() {

        return this.items;

    }

}
