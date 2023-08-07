package com.lypaka.catalystterapokemon.Listeners;

import com.lypaka.catalystterapokemon.Helpers.NBTHelpers;
import com.pixelmonmod.pixelmon.api.events.PixelmonFaintEvent;
import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class FaintListener {

    @SubscribeEvent
    public void onFaint (PixelmonFaintEvent event) {

        Pokemon pokemon = event.getPokemon();
        if (NBTHelpers.isTerastallized(pokemon)) {

            NBTHelpers.setTerastallized(pokemon, false);

        }

    }

}
