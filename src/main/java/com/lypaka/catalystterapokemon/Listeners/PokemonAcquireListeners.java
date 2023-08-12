package com.lypaka.catalystterapokemon.Listeners;

import com.lypaka.catalystterapokemon.API.SetTeraTypeEvent;
import com.lypaka.catalystterapokemon.ConfigGetters;
import com.lypaka.catalystterapokemon.Helpers.NBTHelpers;
import com.pixelmonmod.pixelmon.api.events.PokemonReceivedEvent;
import com.pixelmonmod.pixelmon.api.events.spawning.SpawnEvent;
import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.pixelmonmod.pixelmon.entities.pixelmon.PixelmonEntity;
import com.pixelmonmod.pixelmon.spawning.PlayerTrackingSpawner;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class PokemonAcquireListeners {

    @SubscribeEvent
    public void onReceive (PokemonReceivedEvent event) {

        if (ConfigGetters.spawnOnlyTera) return;
        ServerPlayerEntity player = event.getPlayer();
        Pokemon pokemon = event.getPokemon();
        if (!NBTHelpers.getTeraType(pokemon).equalsIgnoreCase("")) {

            String randomType = NBTHelpers.getRandomTeraType(pokemon);
            SetTeraTypeEvent setEvent = new SetTeraTypeEvent(player, pokemon, randomType, event.getCause());
            MinecraftForge.EVENT_BUS.post(setEvent);
            if (!setEvent.isCanceled()) {

                NBTHelpers.setTeraType(pokemon, setEvent.getTeraType());

            }

        }

    }

    @SubscribeEvent
    public void onSpawn (SpawnEvent event) {

        if (event.action.getOrCreateEntity() instanceof PixelmonEntity) {

            ServerPlayerEntity player = null;
            if (event.spawner instanceof PlayerTrackingSpawner) {

                player = ((PlayerTrackingSpawner) event.spawner).getTrackedPlayer();

            }
            PixelmonEntity pixelmon = (PixelmonEntity) event.action.getOrCreateEntity();
            Pokemon pokemon = pixelmon.getPokemon();
            if (!NBTHelpers.getTeraType(pokemon).equalsIgnoreCase("")) {

                String randomType = NBTHelpers.getRandomTeraType(pokemon);
                SetTeraTypeEvent setEvent = new SetTeraTypeEvent(player, pokemon, randomType, "Spawn");
                MinecraftForge.EVENT_BUS.post(setEvent);
                if (!setEvent.isCanceled()) {

                    NBTHelpers.setTeraType(pokemon, setEvent.getTeraType());

                }

            }

        }

    }

}
