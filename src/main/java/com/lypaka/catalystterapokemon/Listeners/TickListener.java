package com.lypaka.catalystterapokemon.Listeners;

import com.lypaka.catalystterapokemon.CatalystTeraPokemon;
import com.lypaka.catalystterapokemon.Helpers.BattleHelpers;
import com.lypaka.catalystterapokemon.Helpers.NBTHelpers;
import com.lypaka.catalystterapokemon.TeraBattle;
import com.lypaka.lypakautils.FancyText;
import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.pixelmonmod.pixelmon.battles.controller.BattleController;
import com.pixelmonmod.pixelmon.battles.controller.BattleStage;
import com.pixelmonmod.pixelmon.battles.controller.participants.BattleParticipant;
import com.pixelmonmod.pixelmon.battles.controller.participants.PlayerParticipant;
import com.pixelmonmod.pixelmon.battles.controller.participants.Spectator;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

/**
 * This is disgusting
 */
@Mod.EventBusSubscriber(modid = CatalystTeraPokemon.MOD_ID)
public class TickListener {

    @SubscribeEvent
    public static void onTick (TickEvent.ServerTickEvent event) {

        if (BattleHelpers.teraBattles.isEmpty()) return;

        for (TeraBattle tb : BattleHelpers.teraBattles) {

            BattleController bc = tb.getBattleController();
            if (bc.getStage() == BattleStage.DOACTION) {

                List<Pokemon> toTera = tb.getPokemonToTera();
                for (Pokemon p : toTera) {

                    if (!NBTHelpers.isTerastallized(p)) {

                        String teraType = NBTHelpers.getTeraType(p);
                        NBTHelpers.setTerastallized(p, true);
                        List<BattleParticipant> participants = bc.participants;
                        for (BattleParticipant bp : participants) {

                            if (bp instanceof PlayerParticipant) {

                                PlayerParticipant pp = (PlayerParticipant) bp;
                                ServerPlayerEntity player = pp.player;
                                player.sendMessage(FancyText.getFormattedText("&a" + p.getSpecies().getName() + " has Terastallized into the " + teraType + " Tera Type!"), player.getUniqueID());

                            }

                        }
                        for (Spectator spectator : bc.spectators) {

                            spectator.getEntity().sendMessage(FancyText.getFormattedText("&a" + p.getSpecies().getName() + " has Terastallized into the " + teraType + " Tera Type!"), spectator.getEntity().getUniqueID());

                        }

                    }

                }

            }

        }

    }

}
