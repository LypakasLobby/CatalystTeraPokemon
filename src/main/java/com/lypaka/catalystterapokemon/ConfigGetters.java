package com.lypaka.catalystterapokemon;

import com.google.common.reflect.TypeToken;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;

import java.util.List;
import java.util.Map;

public class ConfigGetters {

    public static boolean onlyAllowOTUUID;
    public static boolean allowParticles;
    public static boolean allowOutsideTera;
    public static String costEquation;
    public static String orbID;
    public static List<String> orbLore;
    public static String orbDisplayName;
    public static int maxCharges;
    public static int resetCost;
    public static Map<String, List<String>> teraBlacklist;
    public static Map<String, String> particleColors;

    public static void load() throws ObjectMappingException {

        onlyAllowOTUUID = CatalystTeraPokemon.configManager.getConfigNode(0, "Allow-Only-OT-UUID").getBoolean();
        allowParticles = CatalystTeraPokemon.configManager.getConfigNode(0, "Allow-Particles").getBoolean();
        allowOutsideTera = CatalystTeraPokemon.configManager.getConfigNode(0, "Allow-Outside-Tera").getBoolean();
        costEquation = CatalystTeraPokemon.configManager.getConfigNode(0, "Cost-Equation").getString();
        orbID = CatalystTeraPokemon.configManager.getConfigNode(0, "Item", "ID").getString();
        orbLore = CatalystTeraPokemon.configManager.getConfigNode(0, "Item", "Lore").getList(TypeToken.of(String.class));
        orbDisplayName = CatalystTeraPokemon.configManager.getConfigNode(0, "Item", "Name").getString();
        maxCharges = CatalystTeraPokemon.configManager.getConfigNode(0, "Max-Charges").getInt();
        resetCost = CatalystTeraPokemon.configManager.getConfigNode(0, "Reset-Cost").getInt();
        teraBlacklist = CatalystTeraPokemon.configManager.getConfigNode(0, "Tera-Blacklist").getValue(new TypeToken<Map<String, List<String>>>() {});
        particleColors = CatalystTeraPokemon.configManager.getConfigNode(0, "Tera-Colors").getValue(new TypeToken<Map<String, String>>() {});

    }

}
