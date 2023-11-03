package com.lypaka.catalystterapokemon.Commands;

import com.lypaka.catalystterapokemon.ConfigGetters;
import com.lypaka.catalystterapokemon.Helpers.NBTHelpers;
import com.lypaka.lypakautils.FancyText;
import com.lypaka.lypakautils.MiscHandlers.PermissionHandler;
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

import java.util.Arrays;

public class SetTeraTypeCommand {

    private static SuggestionProvider<CommandSource> TYPES = (context, builder) -> ISuggestionProvider.suggest(ConfigGetters.particleColors.keySet(), builder);
    private static SuggestionProvider<CommandSource> SLOTS = (context, builder) -> ISuggestionProvider.suggest(Arrays.asList("1", "2", "3", "4", "5", "6"), builder);

    public SetTeraTypeCommand (CommandDispatcher<CommandSource> dispatcher) {

        for (String a : CatalystTeraPokemonCommand.ALIASES) {

            dispatcher.register(
                    Commands.literal(a)
                            .then(
                                    Commands.literal("set")
                                            .then(
                                                    Commands.argument("slot", IntegerArgumentType.integer(1, 6))
                                                            .suggests(SLOTS)
                                                            .then(
                                                                    Commands.argument("type", StringArgumentType.string())
                                                                            .suggests(TYPES)
                                                                            .executes(c -> {

                                                                                if (c.getSource().getEntity() instanceof ServerPlayerEntity) {

                                                                                    ServerPlayerEntity player = (ServerPlayerEntity) c.getSource().getEntity();
                                                                                    if (!PermissionHandler.hasPermission(player, "catalystterapokemon.command.admin")) {

                                                                                        player.sendMessage(FancyText.getFormattedText("&cYou don't have permission to use this command!"), player.getUniqueID());
                                                                                        return 1;

                                                                                    }
                                                                                    int slot = IntegerArgumentType.getInteger(c, "slot") - 1;
                                                                                    String type = NBTHelpers.getProperName(StringArgumentType.getString(c, "type"));
                                                                                    PlayerPartyStorage storage = StorageProxy.getParty(player);
                                                                                    Pokemon pokemon = storage.get(slot);
                                                                                    if (pokemon == null) {

                                                                                        player.sendMessage(FancyText.getFormattedText("&cNo Pokemon in this slot!"), player.getUniqueID());
                                                                                        return 0;

                                                                                    } else {

                                                                                        int currentChanges = NBTHelpers.getTeraCount(pokemon);
                                                                                        if (currentChanges < ConfigGetters.maxTypeChanges) {

                                                                                            NBTHelpers.setTeraType(pokemon, type);
                                                                                            player.sendMessage(FancyText.getFormattedText("&aSuccessfully set " + pokemon.getSpecies().getName() + "'s Tera Type to " + type + "!"), player.getUniqueID());
                                                                                            return 1;

                                                                                        } else {

                                                                                            player.sendMessage(FancyText.getFormattedText("&cYou've exceeded max Tera Type changes on this Pokemon!"), player.getUniqueID());
                                                                                            player.sendMessage(FancyText.getFormattedText("&cTo reset the Tera Type changes, please visit the reset menu at \"/tera menu\"!"), player.getUniqueID());
                                                                                            return 0;

                                                                                        }

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
