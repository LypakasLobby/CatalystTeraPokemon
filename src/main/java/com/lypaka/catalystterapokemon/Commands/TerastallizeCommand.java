package com.lypaka.catalystterapokemon.Commands;

import com.lypaka.catalystterapokemon.API.TerastallizeEvent;
import com.lypaka.catalystterapokemon.ConfigGetters;
import com.lypaka.catalystterapokemon.Helpers.BattleHelpers;
import com.lypaka.catalystterapokemon.Helpers.NBTHelpers;
import com.lypaka.catalystterapokemon.TeraBattle;
import com.lypaka.lypakautils.FancyText;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.pixelmonmod.pixelmon.api.storage.PlayerPartyStorage;
import com.pixelmonmod.pixelmon.api.storage.StorageProxy;
import com.pixelmonmod.pixelmon.battles.BattleRegistry;
import com.pixelmonmod.pixelmon.battles.controller.BattleController;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.ISuggestionProvider;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.common.MinecraftForge;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class TerastallizeCommand {

    private static SuggestionProvider<CommandSource> SLOTS = (context, builder) -> ISuggestionProvider.suggest(Arrays.asList("1", "2", "3", "4", "5", "6"), builder);

    public TerastallizeCommand (CommandDispatcher<CommandSource> dispatcher) {

        for (String a : CatalystTeraPokemonCommand.ALIASES) {

            dispatcher.register(
                    Commands.literal(a)
                            .then(
                                    Commands.literal("terastallize")
                                            .then(
                                                    Commands.argument("slot", IntegerArgumentType.integer(1, 6))
                                                            .suggests(SLOTS)
                                                            .executes(c -> {

                                                                if (c.getSource().getEntity() instanceof ServerPlayerEntity) {

                                                                    ServerPlayerEntity player = (ServerPlayerEntity) c.getSource().getEntity();
                                                                    int slot = IntegerArgumentType.getInteger(c, "slot") - 1;
                                                                    PlayerPartyStorage storage = StorageProxy.getParty(player);
                                                                    Pokemon pokemon = storage.get(slot);
                                                                    if (pokemon == null) {

                                                                        player.sendMessage(FancyText.getFormattedText("&cNothing in that party slot!"), player.getUniqueID());
                                                                        return 0;

                                                                    } else if (NBTHelpers.isTerastallized(pokemon)) {

                                                                        player.sendMessage(FancyText.getFormattedText("&cThis Pokemon is already Terastallized!"), player.getUniqueID());
                                                                        return 0;

                                                                    } else {

                                                                        if (!BattleHelpers.canPokemonTera(pokemon)) {

                                                                            player.sendMessage(FancyText.getFormattedText("&cThis Pokemon is currently incapable of Terastallizing due to one of the following reasons:"), player.getUniqueID());
                                                                            player.sendMessage(FancyText.getFormattedText("&eHolding Mega Stone/Z-Crystal, is Rayquaza with Dragon Ascent, or is Dynamaxed/GMax"), player.getUniqueID());
                                                                            return 0;

                                                                        }
                                                                        String teraType = NBTHelpers.getTeraType(pokemon);
                                                                        if (teraType.equalsIgnoreCase("")) {

                                                                            player.sendMessage(FancyText.getFormattedText("&cThis Pokemon does not have a Tera Type!"), player.getUniqueID());
                                                                            return 0;

                                                                        } else {

                                                                            TerastallizeEvent teraEvent = new TerastallizeEvent(player, pokemon);
                                                                            MinecraftForge.EVENT_BUS.post(teraEvent);
                                                                            if (!teraEvent.isCanceled()) {

                                                                                if (BattleRegistry.getBattle(player) != null) {

                                                                                    BattleController bc = BattleRegistry.getBattle(player);
                                                                                    TeraBattle teraBattle = BattleHelpers.getTeraBattleFromPlayer(player);
                                                                                    if (teraBattle == null) {

                                                                                        List<Pokemon> toTera = new ArrayList<>();
                                                                                        List<Pokemon> alreadyTera = new ArrayList<>();
                                                                                        UUID uuid = UUID.randomUUID();
                                                                                        BattleHelpers.battleMap.put(bc, uuid);
                                                                                        toTera.add(pokemon);
                                                                                        teraBattle = new TeraBattle(bc, uuid, toTera, alreadyTera);
                                                                                        BattleHelpers.teraBattles.add(teraBattle);

                                                                                    } else {

                                                                                        List<Pokemon> toTera = teraBattle.getPokemonToTera();
                                                                                        for (Pokemon p : toTera) {

                                                                                            if (p.getOwnerPlayerUUID().toString().equalsIgnoreCase(player.getUniqueID().toString())) {

                                                                                                player.sendMessage(FancyText.getFormattedText("&cYou've already Tera'd this battle!"), player.getUniqueID());
                                                                                                return 1;

                                                                                            }

                                                                                        }
                                                                                        List<Pokemon> alreadyTera = teraBattle.getAlreadyTera();
                                                                                        for (Pokemon p : alreadyTera) {

                                                                                            if (p.getOwnerPlayerUUID().toString().equalsIgnoreCase(player.getUniqueID().toString())) {

                                                                                                player.sendMessage(FancyText.getFormattedText("&cYou've already Tera'd this battle!"), player.getUniqueID());
                                                                                                return 1;

                                                                                            }

                                                                                        }
                                                                                        toTera.add(pokemon);

                                                                                    }

                                                                                    return 1;

                                                                                } else if (ConfigGetters.allowOutsideTera) {

                                                                                    NBTHelpers.setTerastallized(pokemon, true);
                                                                                    player.sendMessage(FancyText.getFormattedText("&a" + pokemon.getSpecies().getName() + " has Terastallized into the " + teraType + " Tera Type!"), player.getUniqueID());

                                                                                } else {

                                                                                    player.sendMessage(FancyText.getFormattedText("&cYou cannot do that outside of battle!"), player.getUniqueID());

                                                                                }

                                                                            }

                                                                        }

                                                                    }

                                                                } else {

                                                                    c.getSource().sendErrorMessage(FancyText.getFormattedText("&cThis command can only be executed by players!"));
                                                                    return 0;

                                                                }

                                                                return 0;

                                                            })
                                            )
                            )
            );

        }

    }

}
