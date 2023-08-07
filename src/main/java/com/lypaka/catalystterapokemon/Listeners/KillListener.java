package com.lypaka.catalystterapokemon.Listeners;

import com.lypaka.catalystterapokemon.API.OrbChargeEvent;
import com.lypaka.catalystterapokemon.ConfigGetters;
import com.pixelmonmod.pixelmon.api.events.BeatWildPixelmonEvent;
import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class KillListener {

    @SubscribeEvent
    public void onPokemonKill (BeatWildPixelmonEvent event) {

        ServerPlayerEntity player = event.player;
        int count = 0;
        if (ConfigGetters.playerCharges.containsKey(player.getUniqueID().toString())) count = ConfigGetters.playerCharges.get(player.getUniqueID().toString());

        Pokemon pokemon = event.wpp.getFaintedPokemon().pokemon;
        int updated = count + getModifier(pokemon);
        OrbChargeEvent orbChargeEvent = new OrbChargeEvent(player, updated);
        MinecraftForge.EVENT_BUS.post(orbChargeEvent);
        if (!orbChargeEvent.isCanceled()) {

            ConfigGetters.playerCharges.put(player.getUniqueID().toString(), orbChargeEvent.getCount());

        }

    }

    private static int getModifier (Pokemon pokemon) {

        // Pokemon has no pre-evolutions and can evolve, Pokemon is baby-stage
        if (pokemon.getForm().getPreEvolutions().size() == 0 && pokemon.getForm().getEvolutions().size() != 0) {

            return 1;

        }

        // Pokemon has pre-evolutions and can evolve, Pokemon is middle-stage
        if (pokemon.getForm().getPreEvolutions().size() != 0 && pokemon.getForm().getEvolutions().size() != 0) {

            return 2;

        }

        // Pokemon has pre-evolutions and can not evolve, Pokemon is final-stage
        if (pokemon.getForm().getPreEvolutions().size() != 0 && pokemon.getForm().getEvolutions().size() == 0) {

            return 3;

        }

        // Pokemon has no pre-evolutions and can not evolve, Pokemon is single-stage
        if (pokemon.getForm().getPreEvolutions().size() == 0 && pokemon.getForm().getEvolutions().size() == 0) {

            return 2;

        }

        return 1;

    }

}
