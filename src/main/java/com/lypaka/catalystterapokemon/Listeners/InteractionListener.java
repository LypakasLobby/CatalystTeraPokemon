package com.lypaka.catalystterapokemon.Listeners;

import com.lypaka.catalystterapokemon.API.TeraShardEvent;
import com.lypaka.catalystterapokemon.ConfigGetters;
import com.lypaka.catalystterapokemon.Helpers.NBTHelpers;
import com.lypaka.catalystterapokemon.TeraItems.TeraItemUtils;
import com.lypaka.catalystterapokemon.TeraItems.TeraShard;
import com.lypaka.lypakautils.FancyText;
import com.pixelmonmod.pixelmon.api.events.raids.DenEvent;
import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.pixelmonmod.pixelmon.entities.pixelmon.PixelmonEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.world.storage.ServerWorldInfo;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class InteractionListener {

    public static List<UUID> locatorList = new ArrayList<>();

    @SubscribeEvent
    public void onRaidClick (DenEvent.Interact event) {

        if (event.wasRightClick()) {

            UUID uuid = event.getPlayer().getUniqueID();
            locatorList.removeIf(e -> {

                if (e.toString().equalsIgnoreCase(uuid.toString())) {

                    event.setCanceled(true);
                    String world = ((ServerWorldInfo) event.getDen().world.getWorldInfo()).getWorldName();
                    int x = event.getDen().getPosition().getX();
                    int y = event.getDen().getPosition().getY();
                    int z = event.getDen().getPosition().getZ();

                    String location = world + "," + x + "," + y + "," + z;
                    event.getPlayer().sendMessage(FancyText.getFormattedText("&eDen located at: &a" + location), uuid);
                    return true;

                }

                return false;

            });

        }

    }

    @SubscribeEvent
    public void onPokemonClick (PlayerInteractEvent.EntityInteractSpecific event) {

        if (event.getSide() == LogicalSide.CLIENT) return;
        if (event.getHand() == Hand.OFF_HAND) return;
        ServerPlayerEntity player = (ServerPlayerEntity) event.getPlayer();
        TeraShard shard = TeraItemUtils.getFromPlayerItem(player.getHeldItem(Hand.MAIN_HAND));
        if (shard == null) return;

        event.setCanceled(true); // this cancels the event in the event the shard item has another function
        if (event.getTarget() instanceof PixelmonEntity) {

            PixelmonEntity pixelmon = (PixelmonEntity) event.getTarget();
            Pokemon pokemon = pixelmon.getPokemon();

            if (ConfigGetters.onlyAllowOTUUID) {

                UUID otUUID = pokemon.getOwnerPlayerUUID();
                if (!otUUID.toString().equalsIgnoreCase(player.getUniqueID().toString())) {

                    player.sendMessage(FancyText.getFormattedText("&cOnly the original Trainer can change this Pokemon's Tera Type!"), player.getUniqueID());
                    return;

                }

            }

            int currentChanges = NBTHelpers.getTeraCount(pokemon);
            if (currentChanges >= ConfigGetters.maxTypeChanges) {

                player.sendMessage(FancyText.getFormattedText("&cYou've exceeded max Tera Type changes on this Pokemon!"), player.getUniqueID());
                player.sendMessage(FancyText.getFormattedText("&cTo reset the Tera Type changes, please visit the reset menu at \"/tera menu\"!"), player.getUniqueID());
                return;

            }

            String type = shard.getType();
            if (ConfigGetters.teraBlacklist.containsKey(pokemon.getSpecies().getName())) {

                List<String> blacklist = ConfigGetters.teraBlacklist.get(pokemon.getSpecies().getName());
                if (blacklist.contains(type)) {

                    player.sendMessage(FancyText.getFormattedText("&cThis Pokemon cannot have this Tera Type!"), player.getUniqueID());
                    return;

                }

            }

            TeraShardEvent shardEvent = new TeraShardEvent(player, pokemon, shard);
            MinecraftForge.EVENT_BUS.post(shardEvent);
            if (!shardEvent.isCanceled()) {

                player.getHeldItem(Hand.MAIN_HAND).setCount(player.getHeldItem(Hand.MAIN_HAND).getCount() - 1);
                NBTHelpers.setTeraType(pokemon, type, true);
                player.sendMessage(FancyText.getFormattedText("&eSuccessfully set " + pokemon.getSpecies().getName() + "'s Tera Type to " + type + "!"), player.getUniqueID());

            }

        }

    }

}
