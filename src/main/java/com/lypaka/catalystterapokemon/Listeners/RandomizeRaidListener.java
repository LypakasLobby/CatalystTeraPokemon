package com.lypaka.catalystterapokemon.Listeners;

import com.lypaka.catalystterapokemon.Helpers.NBTHelpers;
import com.pixelmonmod.pixelmon.api.events.raids.RandomizeRaidEvent;
import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.pixelmonmod.pixelmon.api.pokemon.PokemonBuilder;
import com.pixelmonmod.pixelmon.api.util.helpers.RandomHelper;
import com.pixelmonmod.pixelmon.battles.raids.RaidData;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class RandomizeRaidListener {

    /**
     * Randomizes Pokemon correctly but clicking start (or any of the buttons for that matter) do absolutely nothing
     */

    @SubscribeEvent
    public void onRaidRandomize (RandomizeRaidEvent.ChooseSpecies event) {

        Pokemon pokemon;
        if (RandomHelper.getRandomChance(50)) {

            pokemon = PokemonBuilder.builder().species("Caterpie").build();

        } else {

            pokemon = PokemonBuilder.builder().species("Weedle").build();

        }
        NBTHelpers.setTeraType(pokemon, "Electric");
        NBTHelpers.setTerastallized(pokemon, true);
        event.setRaid(new RaidData(pokemon.getOrCreatePixelmon().getEntityId(), 3, pokemon.getSpecies(), pokemon.getForm()));

    }

}
