package com.lypaka.catalystterapokemon;

import com.google.common.reflect.TypeToken;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;

import java.util.List;
import java.util.Map;

public class ConfigGetters {

    public static boolean onlyAllowOTUUID;
    public static boolean allowParticles;
    public static boolean allowOutsideTera;
    public static int cost;
    public static int maxCharges;
    public static int maxTypeChanges;
    public static List<String> teraRaids;
    public static int resetCost;
    public static Map<String, List<String>> teraBlacklist;
    public static Map<String, String> particleColors;

    public static String orbDisplayName;
    public static String orbID;
    public static List<String> orbLore;
    public static String partyDisplayName;
    public static String partyID;
    public static List<String> partyLore;
    public static Map<String, Map<String, String>> shardItems;

    public static Map<String, Integer> playerCharges;

    public static void load (boolean reload) throws ObjectMappingException {

        onlyAllowOTUUID = CatalystTeraPokemon.configManager.getConfigNode(0, "Allow-Only-OT-UUID").getBoolean();
        allowParticles = CatalystTeraPokemon.configManager.getConfigNode(0, "Allow-Particles").getBoolean();
        allowOutsideTera = CatalystTeraPokemon.configManager.getConfigNode(0, "Allow-Outside-Tera").getBoolean();
        cost = CatalystTeraPokemon.configManager.getConfigNode(0, "Cost").getInt();
        maxCharges = CatalystTeraPokemon.configManager.getConfigNode(0, "Max-Charges").getInt();
        maxTypeChanges = CatalystTeraPokemon.configManager.getConfigNode(0, "Max-Type-Changes").getInt();
        teraRaids = CatalystTeraPokemon.configManager.getConfigNode(0, "Raids").getList(TypeToken.of(String.class));
        resetCost = CatalystTeraPokemon.configManager.getConfigNode(0, "Reset-Cost").getInt();
        teraBlacklist = CatalystTeraPokemon.configManager.getConfigNode(0, "Tera-Blacklist").getValue(new TypeToken<Map<String, List<String>>>() {});
        particleColors = CatalystTeraPokemon.configManager.getConfigNode(0, "Tera-Colors").getValue(new TypeToken<Map<String, String>>() {});

        orbDisplayName = CatalystTeraPokemon.configManager.getConfigNode(1, "Orb", "Display-Name").getString();
        orbID = CatalystTeraPokemon.configManager.getConfigNode(1, "Orb", "ID").getString();
        orbLore = CatalystTeraPokemon.configManager.getConfigNode(1, "Orb", "Lore").getList(TypeToken.of(String.class));
        partyDisplayName = CatalystTeraPokemon.configManager.getConfigNode(1, "Party", "Display-Name").getString();
        partyID = CatalystTeraPokemon.configManager.getConfigNode(1, "Party", "ID").getString();
        partyLore = CatalystTeraPokemon.configManager.getConfigNode(1, "Party", "Lore").getList(TypeToken.of(String.class));
        shardItems = CatalystTeraPokemon.configManager.getConfigNode(1, "Shards").getValue(new TypeToken<Map<String, Map<String, String>>>() {});

        if (!reload) {

            playerCharges = CatalystTeraPokemon.configManager.getConfigNode(2, "Charges").getValue(new TypeToken<Map<String, Integer>>() {});

        }

    }

}
