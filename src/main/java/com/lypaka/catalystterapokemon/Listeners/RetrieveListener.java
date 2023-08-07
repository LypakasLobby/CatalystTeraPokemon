package com.lypaka.catalystterapokemon.Listeners;

import com.lypaka.catalystterapokemon.Helpers.NBTHelpers;
import com.pixelmonmod.pixelmon.api.events.PokemonRetrievedEvent;
import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class RetrieveListener {

    @SubscribeEvent
    public void onRetrieve (PokemonRetrievedEvent event) {

        Pokemon pokemon = event.getPokemon();
        if (NBTHelpers.isTerastallized(pokemon)) {

            NBTHelpers.setTerastallized(pokemon, false);

        }

    }

}
