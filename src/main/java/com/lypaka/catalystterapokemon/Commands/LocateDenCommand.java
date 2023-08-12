package com.lypaka.catalystterapokemon.Commands;

import com.lypaka.catalystterapokemon.Listeners.InteractionListener;
import com.lypaka.lypakautils.FancyText;
import com.lypaka.lypakautils.MiscHandlers.PermissionHandler;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.entity.player.ServerPlayerEntity;

import java.util.UUID;

public class LocateDenCommand {

    public LocateDenCommand (CommandDispatcher<CommandSource> dispatcher) {

        for (String a : CatalystTeraPokemonCommand.ALIASES) {

            dispatcher.register(
                    Commands.literal(a)
                            .then(
                                    Commands.literal("locateden")
                                            .executes(c -> {

                                                if (c.getSource().getEntity() instanceof ServerPlayerEntity) {

                                                    ServerPlayerEntity player = (ServerPlayerEntity) c.getSource().getEntity();
                                                    if (!PermissionHandler.hasPermission(player, "catalystterapokemon.command.admin")) {

                                                        player.sendMessage(FancyText.getFormattedText("&cYou don't have permission to use this command!"), player.getUniqueID());
                                                        return 1;

                                                    }

                                                    UUID uuid = player.getUniqueID();
                                                    if (!InteractionListener.locatorList.contains(uuid)) {

                                                        InteractionListener.locatorList.add(uuid);
                                                        player.sendMessage(FancyText.getFormattedText("&ePlease right click on a den you wish to know the location of."), uuid);

                                                    } else {

                                                        player.sendMessage(FancyText.getFormattedText("&ePlease right click on a den you wish to know the location of."), uuid);

                                                    }

                                                }

                                                return 1;

                                            })
                            )
            );

        }

    }

}
