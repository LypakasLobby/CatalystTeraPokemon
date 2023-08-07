package com.lypaka.catalystterapokemon.Helpers;

import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import net.minecraft.nbt.CompoundNBT;

public class NBTHelpers {

    public static void addTeraCount (Pokemon pokemon) {

        CompoundNBT tags = pokemon.getPersistentData();
        int teraCount = tags.contains("TeraCount") ? tags.getInt("TeraCount") : 0;
        int updated = teraCount + 1;
        pokemon.getPersistentData().putInt("TeraCount", updated);

    }

    public static int getTeraCount (Pokemon pokemon) {

        int teraCount = 0;
        if (pokemon.getPersistentData().contains("TeraCount")) teraCount = pokemon.getPersistentData().getInt("TeraCount");
        return teraCount;

    }

    public static void setTeraType (Pokemon pokemon, String type) {

        pokemon.getPersistentData().putString("TeraType", type);

    }

    public static void clearTeraCount (Pokemon pokemon) {

        if (pokemon.getPersistentData().contains("TeraCount")) {

            pokemon.getPersistentData().remove("TeraCount");

        }

    }

    public static void setTerastallized (Pokemon pokemon, boolean value) {

        pokemon.getPersistentData().putBoolean("Terastallized", value);
        if (value) {

            pokemon.addRibbon(MiscHelpers.teraRibbon, true);

        } else {

            pokemon.removeRibbon(MiscHelpers.teraRibbon);

        }

    }

    public static boolean isTerastallized (Pokemon pokemon) {

        return pokemon.getPersistentData().contains("Terastallized") && pokemon.getPersistentData().getBoolean("Terastallized");

    }

    public static String getTeraType (Pokemon pokemon) {

        String type = "";
        if (pokemon.getPersistentData().contains("TeraType")) type = pokemon.getPersistentData().getString("TeraType");
        return type;

    }

    public static String getProperName (String teraType) {

        return teraType.substring(0, 1).toUpperCase() + teraType.substring(1);

    }

}
