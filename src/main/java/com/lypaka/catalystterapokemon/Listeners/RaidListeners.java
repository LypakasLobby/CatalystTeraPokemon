package com.lypaka.catalystterapokemon.Listeners;

import com.lypaka.catalystterapokemon.Helpers.NBTHelpers;
import com.pixelmonmod.pixelmon.api.events.battles.BattleTickEvent;
import com.pixelmonmod.pixelmon.api.events.raids.RandomizeRaidEvent;
import com.pixelmonmod.pixelmon.api.events.raids.StartRaidEvent;
import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.pixelmonmod.pixelmon.api.pokemon.PokemonBuilder;
import com.pixelmonmod.pixelmon.api.util.helpers.RandomHelper;
import com.pixelmonmod.pixelmon.battles.controller.participants.BattleParticipant;
import com.pixelmonmod.pixelmon.battles.controller.participants.PixelmonWrapper;
import com.pixelmonmod.pixelmon.battles.controller.participants.RaidPixelmonParticipant;
import com.pixelmonmod.pixelmon.battles.raids.RaidData;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class RaidListeners {

    @SubscribeEvent
    public void onRaidRandomize (RandomizeRaidEvent.ChooseSpecies event) {

        Pokemon pokemon;
        if (RandomHelper.getRandomChance(50)) {

            pokemon = PokemonBuilder.builder().species("Snorlax").build();

        } else {

            pokemon = PokemonBuilder.builder().species("Munchlax").build();

        }

        event.setRaid(new RaidData(event.den.getEntityId(), 5, pokemon.getSpecies(), pokemon.getForm()));

    }

    @SubscribeEvent // this is absolutely necessary for the Pokemon to be Tera'd
    public void onRaidStart (StartRaidEvent event) {

        Pokemon pokemon = event.getRaidPixelmon().getPokemon();
        NBTHelpers.setTeraType(pokemon, "Poison");
        NBTHelpers.setTerastallized(pokemon, true);

    }

    @SubscribeEvent
    public void onBattleTick (BattleTickEvent event) {

        if (event.getBattleController().isRaid()) {

            for (BattleParticipant bp : event.getBattleController().participants) {

                if (bp instanceof RaidPixelmonParticipant) {

                    RaidPixelmonParticipant rpp = (RaidPixelmonParticipant) bp;
                    rpp.getGovernor().getSettings().moveset.removeIf(a -> {

                        try {

                            if (a != null) {

                                return a.isMax;

                            }

                        } catch (NullPointerException er) {

                            return true;

                        }


                        return false;

                    });
                    PixelmonWrapper wrapper = rpp.getWrapper();
                    Pokemon pokemon = wrapper.pokemon;
                    if (NBTHelpers.isTerastallized(pokemon)) {

                        if (wrapper.isDynamax > 0) {

                            wrapper.isDynamax = 0;
                            wrapper.dynamaxTurns = 0;
                            wrapper.dynamaxAnimationTicks = 0;
                            wrapper.entity.setDynamaxScale(0);
                            wrapper.dynamax(true, wrapper.getHealthPercent());
                            wrapper.entity.setPixelmonScale(2.0f);
                            event.getBattleController().updateFormChange(wrapper);
                            break;

                        }

                    }

                }

            }

        }

    }

}
