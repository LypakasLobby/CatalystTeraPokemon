package com.lypaka.catalystterapokemon.Commands;

import com.lypaka.catalystterapokemon.Helpers.NBTHelper;
import com.lypaka.lypakautils.FancyText;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.pixelmonmod.pixelmon.api.storage.PlayerPartyStorage;
import com.pixelmonmod.pixelmon.api.storage.StorageProxy;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.entity.player.ServerPlayerEntity;

public class TerastallizeCommand {

    public TerastallizeCommand (CommandDispatcher<CommandSource> dispatcher) {

        for (String a : CatalystTeraPokemonCommand.ALIASES) {

            dispatcher.register(
                    Commands.literal(a)
                            .then(
                                    Commands.literal("terastallize")
                                            .then(
                                                    Commands.argument("slot", IntegerArgumentType.integer(1, 6))
                                                            .executes(c -> {

                                                                if (c.getSource().getEntity() instanceof ServerPlayerEntity) {

                                                                    ServerPlayerEntity player = (ServerPlayerEntity) c.getSource().getEntity();
                                                                    int slot = IntegerArgumentType.getInteger(c, "slot") - 1;
                                                                    PlayerPartyStorage storage = StorageProxy.getParty(player);
                                                                    Pokemon pokemon = storage.get(slot);
                                                                    if (pokemon == null) {

                                                                        player.sendMessage(FancyText.getFormattedText("&cNothing in that party slot!"), player.getUniqueID());
                                                                        return 0;

                                                                    } else {

                                                                        String teraType = NBTHelper.getTeraType(pokemon);
                                                                        if (teraType.equalsIgnoreCase("")) {

                                                                            player.sendMessage(FancyText.getFormattedText("&cThis Pokemon does not have a Tera Type!"), player.getUniqueID());
                                                                            return 0;

                                                                        } else {

                                                                            NBTHelper.setTerastallized(pokemon, true);
                                                                            player.sendMessage(FancyText.getFormattedText("&a" + pokemon.getSpecies().getName() + " has Terastallized into the " + teraType + " Tera Type!"), player.getUniqueID());
                                                                            return 1;

                                                                        }

                                                                    }

                                                                } else {

                                                                    c.getSource().sendErrorMessage(FancyText.getFormattedText("&cThis command can only be executed by players!"));
                                                                    return 0;

                                                                }

                                                            })
                                            )
                            )
            );

        }

    }

}
