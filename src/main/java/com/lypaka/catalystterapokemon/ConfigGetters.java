package com.lypaka.catalystterapokemon;

import com.google.common.reflect.TypeToken;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ConfigGetters {

    public static boolean onlyAllowOTUUID;
    public static boolean allowParticles;
    public static boolean allowPlayerVSPlayerTera;
    public static boolean allowOutsideTera;
    public static int cost;
    public static int maxCharges;
    public static int maxTypeChanges;
    public static List<String> npcBlacklist;
    public static List<String> teraRaids;
    public static int resetCost;
    public static boolean starterCanHaveTera;
    public static boolean spawnOnlyTera;
    public static Map<String, List<String>> teraBlacklist;
    public static double teraChance;
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

        boolean save = false;
        onlyAllowOTUUID = CatalystTeraPokemon.configManager.getConfigNode(0, "Allow-Only-OT-UUID").getBoolean();
        allowParticles = CatalystTeraPokemon.configManager.getConfigNode(0, "Allow-Particles").getBoolean();
        if (CatalystTeraPokemon.configManager.getConfigNode(0, "Allow-Player-VS-Player-Tera").isVirtual()) {

            allowPlayerVSPlayerTera = true;
            CatalystTeraPokemon.configManager.getConfigNode(0, "Allow-Player-VS-Player-Tera").setComment("If false, will not allow players to use Tera in battles against other players");
            CatalystTeraPokemon.configManager.getConfigNode(0, "Allow-Player-VS-Player-Tera").setValue(true);
            save = true;

        } else {

            allowPlayerVSPlayerTera = CatalystTeraPokemon.configManager.getConfigNode(0, "Allow-Player-VS-Player-Tera").getBoolean();

        }
        allowOutsideTera = CatalystTeraPokemon.configManager.getConfigNode(0, "Allow-Outside-Tera").getBoolean();
        cost = CatalystTeraPokemon.configManager.getConfigNode(0, "Cost").getInt();
        maxCharges = CatalystTeraPokemon.configManager.getConfigNode(0, "Max-Charges").getInt();
        maxTypeChanges = CatalystTeraPokemon.configManager.getConfigNode(0, "Max-Type-Changes").getInt();
        if (CatalystTeraPokemon.configManager.getConfigNode(0, "NPC-Blacklist").isVirtual()) {

            npcBlacklist = new ArrayList<>();
            CatalystTeraPokemon.configManager.getConfigNode(0, "NPC-Blacklist").setValue(npcBlacklist);
            CatalystTeraPokemon.configManager.getConfigNode(0, "NPC-Blacklist").setComment("NPCs players can't use Tera against, in \"world,x,y,z\" format");
            if (!save) save = true;

        } else {

            npcBlacklist = CatalystTeraPokemon.configManager.getConfigNode(0, "NPC-Blacklist").getList(TypeToken.of(String.class));

        }
        teraRaids = CatalystTeraPokemon.configManager.getConfigNode(0, "Raids").getList(TypeToken.of(String.class));
        resetCost = CatalystTeraPokemon.configManager.getConfigNode(0, "Reset-Cost").getInt();
        starterCanHaveTera = CatalystTeraPokemon.configManager.getConfigNode(0, "Starter-Can-Have-Tera").getBoolean();
        spawnOnlyTera = CatalystTeraPokemon.configManager.getConfigNode(0, "Spawn-Only").getBoolean();
        teraBlacklist = CatalystTeraPokemon.configManager.getConfigNode(0, "Tera-Blacklist").getValue(new TypeToken<Map<String, List<String>>>() {});
        teraChance = CatalystTeraPokemon.configManager.getConfigNode(0, "Tera-Chance").getDouble();
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
        if (save) {

            CatalystTeraPokemon.configManager.save();

        }

    }

}
