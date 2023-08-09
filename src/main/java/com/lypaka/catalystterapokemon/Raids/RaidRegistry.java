package com.lypaka.catalystterapokemon.Raids;

import com.lypaka.catalystterapokemon.CatalystTeraPokemon;

import java.util.HashMap;
import java.util.Map;

public class RaidRegistry {

    public static Map<String, TeraRaid> raidMap;

    public static void loadRaids() {

        raidMap = new HashMap<>();
        CatalystTeraPokemon.logger.info("Loading raid data...");


    }

}
