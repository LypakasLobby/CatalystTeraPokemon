package com.lypaka.catalystterapokemon.Commands;

import com.lypaka.catalystterapokemon.Helpers.NBTHelpers;
import com.lypaka.lypakautils.FancyText;
import com.lypaka.lypakautils.MiscHandlers.PermissionHandler;
import com.lypaka.lypakautils.MiscHandlers.PixelmonHelpers;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.entity.player.ServerPlayerEntity;

import java.util.List;

public class ResetCommand {

    public ResetCommand (CommandDispatcher<CommandSource> dispatcher) {

        for (String a : CatalystTeraPokemonCommand.ALIASES) {

            dispatcher.register(
                    Commands.literal(a)
                                    .then(
                                            Commands.literal("reset")
                                                    .then(
                                                            Commands.argument("player", EntityArgument.player())
                                                                    .then(
                                                                            Commands.argument("slot", IntegerArgumentType.integer(1, 6))
                                                                                    .executes(c -> {

                                                                                        if (c.getSource().getEntity() instanceof ServerPlayerEntity) {

                                                                                            ServerPlayerEntity player = (ServerPlayerEntity) c.getSource().getEntity();
                                                                                            if (!PermissionHandler.hasPermission(player, "catalystterapokemon.command.admin")) {

                                                                                                player.sendMessage(FancyText.getFormattedText("&cYou don't have permission to use this command!"), player.getUniqueID());
                                                                                                return 0;

                                                                                            }

                                                                                        }

                                                                                        ServerPlayerEntity target = EntityArgument.getPlayer(c, "player");
                                                                                        int slot = IntegerArgumentType.getInteger(c, "slot") - 1;
                                                                                        List<Pokemon> team = PixelmonHelpers.getTeam(target);
                                                                                        Pokemon pokemon = team.get(slot);
                                                                                        if (pokemon != null) {

                                                                                            NBTHelpers.clearTeraCount(pokemon);
                                                                                            NBTHelpers.clearTeraType(pokemon);
                                                                                            target.sendMessage(FancyText.getFormattedText("&aSuccessfully cleared " + pokemon.getSpecies().getName() + "'s Tera information!"), target.getUniqueID());

                                                                                        }

                                                                                        return 1;

                                                                                    })
                                                                    )
                                                    )
                                    )

            );

        }

    }

}
