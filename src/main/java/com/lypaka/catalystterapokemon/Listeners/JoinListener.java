package com.lypaka.catalystterapokemon.Listeners;

import com.lypaka.catalystterapokemon.Helpers.NBTHelpers;
import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.pixelmonmod.pixelmon.api.storage.PlayerPartyStorage;
import com.pixelmonmod.pixelmon.api.storage.StorageProxy;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class JoinListener {

    @SubscribeEvent
    public void onJoin (PlayerEvent.PlayerLoggedInEvent event) {

        ServerPlayerEntity player = (ServerPlayerEntity) event.getPlayer();
        PlayerPartyStorage storage = StorageProxy.getParty(player);

        for (Pokemon p : storage.getTeam()) {

            if (p != null) {

                if (NBTHelpers.isTerastallized(p)) {

                    NBTHelpers.setTerastallized(p, false);

                }

            }

        }

    }

}
