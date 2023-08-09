package com.lypaka.catalystterapokemon.Listeners;

import com.lypaka.catalystterapokemon.Helpers.NBTHelpers;
import com.pixelmonmod.pixelmon.api.events.battles.BattleStartedEvent;
import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.pixelmonmod.pixelmon.api.storage.PlayerPartyStorage;
import com.pixelmonmod.pixelmon.api.storage.StorageProxy;
import com.pixelmonmod.pixelmon.battles.controller.BattleController;
import com.pixelmonmod.pixelmon.battles.controller.participants.BattleParticipant;
import com.pixelmonmod.pixelmon.battles.controller.participants.PlayerParticipant;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class BattleStartListener {

    @SubscribeEvent
    public void onBattleStart (BattleStartedEvent event) {

        BattleController bc = event.getBattleController();
        for (BattleParticipant bp : bc.participants) {

            if (bp instanceof PlayerParticipant) {

                PlayerParticipant pp = (PlayerParticipant) bp;
                ServerPlayerEntity player = pp.player;
                PlayerPartyStorage storage = StorageProxy.getParty(player);
                for (Pokemon p : storage.getTeam()) {

                    if (NBTHelpers.isTerastallized(p)) {

                        NBTHelpers.setTerastallized(p, false);

                    }

                }

            }

        }

    }

}
