package com.lypaka.catalystterapokemon.Commands;

import com.lypaka.catalystterapokemon.CatalystTeraPokemon;
import com.lypaka.catalystterapokemon.ConfigGetters;
import com.lypaka.catalystterapokemon.Helpers.DamageHelpers;
import com.lypaka.catalystterapokemon.Raids.RaidRegistry;
import com.lypaka.catalystterapokemon.TeraItems.TeraItemUtils;
import com.lypaka.lypakautils.FancyText;
import com.lypaka.lypakautils.MiscHandlers.PermissionHandler;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.entity.player.ServerPlayerEntity;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;

import java.io.IOException;

public class ReloadCommand {

    public ReloadCommand (CommandDispatcher<CommandSource> dispatcher) {

        for (String a : CatalystTeraPokemonCommand.ALIASES) {

            dispatcher.register(
                    Commands.literal(a)
                            .then(
                                    Commands.literal("reload")
                                            .executes(c -> {

                                                if (c.getSource().getEntity() instanceof ServerPlayerEntity) {

                                                    ServerPlayerEntity player = (ServerPlayerEntity) c.getSource().getEntity();
                                                    if (!PermissionHandler.hasPermission(player, "catalystterapokemon.command.admin")) {

                                                        player.sendMessage(FancyText.getFormattedText("&cYou don't have permission to use this command!"), player.getUniqueID());
                                                        return 0;

                                                    }

                                                }

                                                try {

                                                    CatalystTeraPokemon.configManager.load();
                                                    ConfigGetters.load(true);
                                                    DamageHelpers.loadTypeEffectivenessMaps();
                                                    TeraItemUtils.loadShards();
                                                    RaidRegistry.loadRaids();
                                                    c.getSource().sendFeedback(FancyText.getFormattedText("&aSuccessfully reloaded CatalystTeraPokemon configuration!"), true);
                                                    return 1;

                                                } catch (ObjectMappingException | IOException e) {

                                                    throw new RuntimeException(e);

                                                }

                                            })
                            )
            );

        }

    }

}
