package com.lypaka.catalystterapokemon.Helpers;

import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import net.minecraft.nbt.CompoundNBT;

public class NBTHelper {

    public static void addTeraCount (Pokemon pokemon) {

        CompoundNBT tags = pokemon.getPersistentData();
        int teraCount = tags.contains("TeraCount") ? tags.getInt("TeraCount") : 0;
        int updated = teraCount + 1;
        pokemon.getPersistentData().putInt("TeraCount", updated);

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

    }

    public static boolean isTerastallized (Pokemon pokemon) {

        return pokemon.getPersistentData().contains("Terastallized") && pokemon.getPersistentData().getBoolean("Terastallized");

    }

}
