package com.lypaka.catalystterapokemon.Raids;

import com.google.common.reflect.TypeToken;
import com.lypaka.catalystterapokemon.CatalystTeraPokemon;
import com.lypaka.catalystterapokemon.ConfigGetters;
import com.lypaka.lypakautils.ConfigurationLoaders.BasicConfigManager;
import com.lypaka.lypakautils.ConfigurationLoaders.ComplexConfigManager;
import com.lypaka.lypakautils.ConfigurationLoaders.ConfigUtils;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RaidRegistry {

    public static Map<String, TeraRaid> raidMap;

    public static void loadRaids() throws IOException, ObjectMappingException {

        raidMap = new HashMap<>();
        CatalystTeraPokemon.logger.info("Loading raid data...");
        String[] files = new String[]{"settings.conf"};
        for (String raidID : ConfigGetters.teraRaids) {

            Path dir = ConfigUtils.checkDir(Paths.get("./config/catalystterapokemon/raids/" + raidID));
            BasicConfigManager bcm = new BasicConfigManager(files, dir, CatalystTeraPokemon.class, CatalystTeraPokemon.MOD_NAME, CatalystTeraPokemon.MOD_ID, CatalystTeraPokemon.logger);
            bcm.init();

            int defaultStarLevel = bcm.getConfigNode(0, "Default-Star-Level").getInt();
            List<String> denLocations = bcm.getConfigNode(0, "Locations").getList(TypeToken.of(String.class));
            List<String> raidPokemonList = bcm.getConfigNode(0, "Pokemon").getList(TypeToken.of(String.class));
            List<TeraRaidPokemon> teraPokemon = new ArrayList<>();

            ComplexConfigManager ccm = new ComplexConfigManager(raidPokemonList, "pokemon", "raidPokemonTemplate.conf", dir, CatalystTeraPokemon.class, CatalystTeraPokemon.MOD_NAME, CatalystTeraPokemon.MOD_ID, CatalystTeraPokemon.logger);
            ccm.init();

            for (int i = 0; i < raidPokemonList.size(); i++) {

                double pokemonChance = ccm.getConfigNode(i, "Chance").getDouble();
                String palette = ccm.getConfigNode(i, "Palette").getString();

                int rewardMax = ccm.getConfigNode(i, "Rewards", "Amount-Max").getInt();
                int rewardMin = ccm.getConfigNode(i, "Rewards", "Amount-Min").getInt();

                Map<String, RaidRewardCommandSet> commandMap = new HashMap<>();
                Map<String, Map<String, String>> configRewardMap = ccm.getConfigNode(i, "Rewards", "Prizes", "Commands").getValue(new TypeToken<Map<String, Map<String, String>>>() {});
                for (Map.Entry<String, Map<String, String>> entry : configRewardMap.entrySet()) {

                    String setID = entry.getKey();
                    double commandSetChance = ccm.getConfigNode(i, "Rewards", "Prizes", "Commands", setID, "Chance").getDouble();
                    List<String> commands = ccm.getConfigNode(i, "Rewards", "Prizes", setID, "Executed").getList(TypeToken.of(String.class));

                    RaidRewardCommandSet raidRewardCommandSet = new RaidRewardCommandSet(commandSetChance, commands);
                    commandMap.put(setID, raidRewardCommandSet);

                }

                Map<String, Double> itemMap = ccm.getConfigNode(i, "Rewards", "Prizes", "Items").getValue(new TypeToken<Map<String, Double>>() {});

                RaidRewards raidRewards = new RaidRewards(rewardMax, rewardMin, commandMap, itemMap);
                float scale = ccm.getConfigNode(i, "Scale").getFloat();
                int starLevel = defaultStarLevel;
                if (!ccm.getConfigNode(i, "Star-Level").isVirtual()) {

                    starLevel = ccm.getConfigNode(i, "Star-Level").getInt();

                }
                Map<String, Double> teraTypeChances = ccm.getConfigNode(i, "Tera-Type").getValue(new TypeToken<Map<String, Double>>() {});

                TeraRaidPokemon teraRaidPokemon = new TeraRaidPokemon(pokemonChance, palette, raidRewards, scale, starLevel, teraTypeChances);
                teraPokemon.add(teraRaidPokemon);

            }

            TeraRaid teraRaid = new TeraRaid(defaultStarLevel, denLocations, teraPokemon);
            raidMap.put(raidID, teraRaid);
            CatalystTeraPokemon.logger.info("Successfully loaded raid: " + raidID);

        }

        CatalystTeraPokemon.logger.info("Finished loading all raids' data!");

    }

}
