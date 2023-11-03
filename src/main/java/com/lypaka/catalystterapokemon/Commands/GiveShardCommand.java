package com.lypaka.catalystterapokemon.Commands;

import com.lypaka.catalystterapokemon.TeraItems.TeraItemUtils;
import com.lypaka.catalystterapokemon.TeraItems.TeraShard;
import com.lypaka.lypakautils.FancyText;
import com.lypaka.lypakautils.MiscHandlers.ItemStackBuilder;
import com.lypaka.lypakautils.MiscHandlers.PermissionHandler;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.ISuggestionProvider;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.StringNBT;
import net.minecraft.util.text.ITextComponent;

import java.util.Arrays;

public class GiveShardCommand {

    private static SuggestionProvider<CommandSource> SHARDS = (context, builder) -> ISuggestionProvider.suggest(
            Arrays.asList("Bug", "Dark", "Dragon", "Electric", "Fairy", "Fighting", "Fire", "Flying", "Ghost", "Grass", "Ground", "Ice", "Normal", "Poison", "Psychic", "Rock", "Water", "Steel")
            , builder);

    public GiveShardCommand (CommandDispatcher<CommandSource> dispatcher) {

        for (String a : CatalystTeraPokemonCommand.ALIASES) {

            dispatcher.register(
                    Commands.literal(a)
                            .then(
                                    Commands.literal("giveshard")
                                            .then(
                                                    Commands.argument("player", EntityArgument.player())
                                                            .then(
                                                                    Commands.argument("shard", StringArgumentType.word())
                                                                            .suggests(SHARDS)
                                                                            .then(
                                                                                    Commands.argument("amount", IntegerArgumentType.integer(1, 64))
                                                                                            .executes(c -> {

                                                                                                if (c.getSource().getEntity() instanceof ServerPlayerEntity) {

                                                                                                    ServerPlayerEntity player = (ServerPlayerEntity) c.getSource().getEntity();
                                                                                                   /* if (!PermissionHandler.hasPermission(player, "catalystterapokemon.command.admin")) {

                                                                                                        player.sendMessage(FancyText.getFormattedText("&cYou don't have permission to use this command!"), player.getUniqueID());
                                                                                                        return 0;

                                                                                                    }*/

                                                                                                }

                                                                                                TeraShard shard = TeraItemUtils.getFromType(StringArgumentType.getString(c, "shard"));
                                                                                                if (shard == null) {

                                                                                                    c.getSource().sendErrorMessage(FancyText.getFormattedText("&cInvalid shard type!"));
                                                                                                    return 0;

                                                                                                }

                                                                                                ItemStack shardItem = ItemStackBuilder.buildFromStringID(shard.getID());
                                                                                                int amount = IntegerArgumentType.getInteger(c, "amount");
                                                                                                shardItem.setCount(amount);
                                                                                                shardItem.setDisplayName(FancyText.getFormattedText(shard.getDisplayName()));
                                                                                                ListNBT lore = new ListNBT();
                                                                                                for (String s : shard.getItemLore()) {

                                                                                                    lore.add(StringNBT.valueOf(ITextComponent.Serializer.toJson(FancyText.getFormattedText(s))));

                                                                                                }
                                                                                                shardItem.getOrCreateChildTag("display").put("Lore", lore);
                                                                                                ServerPlayerEntity target = EntityArgument.getPlayer(c, "player");
                                                                                                target.addItemStackToInventory(shardItem);
                                                                                                c.getSource().sendFeedback(FancyText.getFormattedText("&eSuccessfully gave " + target.getName().getString() + " " + amount + " " + shard.getType() + " Tera Shard(s)!"), true);
                                                                                                target.sendMessage(FancyText.getFormattedText("&eYou've received " + amount + " " + shard.getType() + " Tera Shard(s)!"), target.getUniqueID());
                                                                                                return 1;

                                                                                            })
                                                                            )
                                                                            .executes(c -> {

                                                                                if (c.getSource().getEntity() instanceof ServerPlayerEntity) {

                                                                                    ServerPlayerEntity player = (ServerPlayerEntity) c.getSource().getEntity();
                                                                                    /*if (!PermissionHandler.hasPermission(player, "catalystterapokemon.command.admin")) {

                                                                                        player.sendMessage(FancyText.getFormattedText("&cYou don't have permission to use this command!"), player.getUniqueID());
                                                                                        return 0;

                                                                                    }*/

                                                                                }

                                                                                TeraShard shard = TeraItemUtils.getFromType(StringArgumentType.getString(c, "shard"));
                                                                                if (shard == null) {

                                                                                    c.getSource().sendErrorMessage(FancyText.getFormattedText("&cInvalid shard type!"));
                                                                                    return 0;

                                                                                }

                                                                                ItemStack shardItem = ItemStackBuilder.buildFromStringID(shard.getID());
                                                                                int amount = 1;
                                                                                shardItem.setCount(amount);
                                                                                shardItem.setDisplayName(FancyText.getFormattedText(shard.getDisplayName()));
                                                                                ListNBT lore = new ListNBT();
                                                                                for (String s : shard.getItemLore()) {

                                                                                    lore.add(StringNBT.valueOf(ITextComponent.Serializer.toJson(FancyText.getFormattedText(s))));

                                                                                }
                                                                                shardItem.getOrCreateChildTag("display").put("Lore", lore);
                                                                                ServerPlayerEntity target = EntityArgument.getPlayer(c, "player");
                                                                                target.addItemStackToInventory(shardItem);
                                                                                c.getSource().sendFeedback(FancyText.getFormattedText("&eSuccessfully gave " + target.getName().getString() + " " + amount + " " + shard.getType() + " Tera Shard(s)!"), true);
                                                                                target.sendMessage(FancyText.getFormattedText("&eYou've received " + amount + " " + shard.getType() + " Tera Shard(s)!"), target.getUniqueID());
                                                                                return 1;

                                                                            })
                                                            )
                                            )
                            )
            );

        }

    }

}
