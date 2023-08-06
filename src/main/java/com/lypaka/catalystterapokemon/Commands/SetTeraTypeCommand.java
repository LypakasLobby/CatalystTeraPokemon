package com.lypaka.catalystterapokemon.Commands;

import com.lypaka.catalystterapokemon.ConfigGetters;
import com.lypaka.catalystterapokemon.Helpers.NBTHelpers;
import com.lypaka.lypakautils.FancyText;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.pixelmonmod.pixelmon.api.storage.PlayerPartyStorage;
import com.pixelmonmod.pixelmon.api.storage.StorageProxy;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.ISuggestionProvider;
import net.minecraft.entity.player.ServerPlayerEntity;

public class SetTeraTypeCommand {

    private static SuggestionProvider<CommandSource> TYPES = (context, builder) -> ISuggestionProvider.suggest(ConfigGetters.particleColors.keySet(), builder);

    public SetTeraTypeCommand (CommandDispatcher<CommandSource> dispatcher) {

        for (String a : CatalystTeraPokemonCommand.ALIASES) {

            dispatcher.register(
                    Commands.literal(a)
                            .then(
                                    Commands.literal("set")
                                            .then(
                                                    Commands.argument("slot", IntegerArgumentType.integer(1, 6))
                                                            .then(
                                                                    Commands.argument("type", StringArgumentType.string())
                                                                            .suggests(TYPES)
                                                                            .executes(c -> {

                                                                                if (c.getSource().getEntity() instanceof ServerPlayerEntity) {

                                                                                    ServerPlayerEntity player = (ServerPlayerEntity) c.getSource().getEntity();
                                                                                    int slot = IntegerArgumentType.getInteger(c, "slot") - 1;
                                                                                    String type = StringArgumentType.getString(c, "type");
                                                                                    PlayerPartyStorage storage = StorageProxy.getParty(player);
                                                                                    Pokemon pokemon = storage.get(slot);
                                                                                    if (pokemon == null) {

                                                                                        player.sendMessage(FancyText.getFormattedText("&cNo Pokemon in this slot!"), player.getUniqueID());
                                                                                        return 0;

                                                                                    } else {

                                                                                        NBTHelpers.setTeraType(pokemon, type);
                                                                                        player.sendMessage(FancyText.getFormattedText("&aSuccessfully set " + pokemon.getSpecies().getName() + "'s Tera Type to " + type + "!"), player.getUniqueID());
                                                                                        return 1;

                                                                                    }

                                                                                } else {

                                                                                    c.getSource().sendErrorMessage(FancyText.getFormattedText("&cThis command can only be executed by players!"));
                                                                                    return 0;

                                                                                }

                                                                            })
                                                            )
                                            )
                            )
            );

        }

    }

}
