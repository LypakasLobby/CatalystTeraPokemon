package com.lypaka.catalystterapokemon.GUIs;

import ca.landonjw.gooeylibs2.api.UIManager;
import ca.landonjw.gooeylibs2.api.button.Button;
import ca.landonjw.gooeylibs2.api.button.GooeyButton;
import ca.landonjw.gooeylibs2.api.page.GooeyPage;
import ca.landonjw.gooeylibs2.api.template.types.ChestTemplate;
import com.lypaka.catalystterapokemon.ConfigGetters;
import com.lypaka.catalystterapokemon.Helpers.NBTHelpers;
import com.lypaka.lypakautils.FancyText;
import com.lypaka.lypakautils.MiscHandlers.ItemStackBuilder;
import com.lypaka.lypakautils.MiscHandlers.LogicalPixelmonMoneyHandler;
import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.pixelmonmod.pixelmon.api.storage.PlayerPartyStorage;
import com.pixelmonmod.pixelmon.api.storage.StorageProxy;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.StringNBT;
import net.minecraft.util.text.ITextComponent;

public class PartyMenu {

    public static void openPartyMenu (ServerPlayerEntity player) {

        ChestTemplate template = ChestTemplate.builder(1).build();
        GooeyPage page = GooeyPage.builder()
                .template(template)
                .title(FancyText.getFormattedText("&dParty Menu"))
                .build();

        page.getTemplate().getSlot(0).setButton(
                GooeyButton.builder()
                        .display(
                                ItemStackBuilder.buildFromStringID("minecraft:red_dye").setDisplayName(FancyText.getFormattedText("&dShards Menu"))
                        )
                        .onClick(() -> {

                            ShardMenu.openShardMenu(player);

                        })
                        .build()
        );

        int[] partySlots = new int[]{3, 4, 5, 6, 7, 8};
        PlayerPartyStorage party = StorageProxy.getParty(player);
        for (int i = 0; i < 6; i++) {

            int slot = partySlots[i];
            Button button;
            Pokemon pokemon = party.get(i);
            ItemStack item;
            if (pokemon != null) {

                String id = pokemon.getSprite().toString();
                item = ItemStackBuilder.buildFromStringID(id);
                item.setDisplayName(FancyText.getFormattedText("&e" + pokemon.getSpecies().getName()));
                ListNBT lore = new ListNBT();
                lore.add(StringNBT.valueOf(ITextComponent.Serializer.toJson(FancyText.getFormattedText("&7Reset Cost: &a" + ConfigGetters.resetCost))));
                lore.add(StringNBT.valueOf(ITextComponent.Serializer.toJson(FancyText.getFormattedText("&7Charge Cost: &3" + ConfigGetters.cost))));
                lore.add(StringNBT.valueOf(ITextComponent.Serializer.toJson(FancyText.getFormattedText("&7Total Type Changes: &b" + NBTHelpers.getTeraCount(pokemon) + "&7/&b" + ConfigGetters.maxTypeChanges))));
                lore.add(StringNBT.valueOf(ITextComponent.Serializer.toJson(FancyText.getFormattedText("&7Current Tera Type: &c" + NBTHelpers.getTeraType(pokemon)))));
                lore.add(StringNBT.valueOf(ITextComponent.Serializer.toJson(FancyText.getFormattedText(""))));
                lore.add(StringNBT.valueOf(ITextComponent.Serializer.toJson(FancyText.getFormattedText("&7Click &6Here &7to &cReset Tera Type"))));
                item.getOrCreateChildTag("display").put("Lore", lore);

                button = GooeyButton.builder()
                        .display(item)
                        .onClick(() -> {

                            tryResetTeraChanges(player, pokemon);

                        })
                        .build();

            } else {

                String id = "minecraft:barrier";
                item = ItemStackBuilder.buildFromStringID(id);
                item.setDisplayName(FancyText.getFormattedText("&cNothing in this slot!"));

                button = GooeyButton.builder().display(item).build();

            }

            page.getTemplate().getSlot(slot).setButton(button);

            ItemStack purple = ItemStackBuilder.buildFromStringID("minecraft:purple_stained_glass_pane");
            purple.setDisplayName(FancyText.getFormattedText(""));
            page.getTemplate().getSlot(1).setButton(GooeyButton.builder().display(purple).build());
            page.getTemplate().getSlot(3).setButton(GooeyButton.builder().display(purple).build());

            ItemStack pink = ItemStackBuilder.buildFromStringID("minecraft:pink_stained_glass_pane");
            pink.setDisplayName(FancyText.getFormattedText(""));
            page.getTemplate().getSlot(2).setButton(GooeyButton.builder().display(pink).build());

            UIManager.openUIForcefully(player, page);

        }

    }

    private static void tryResetTeraChanges (ServerPlayerEntity player, Pokemon pokemon) {

        int cost = ConfigGetters.resetCost;
        int balance = (int) LogicalPixelmonMoneyHandler.getBalance(player.getUniqueID());
        if (balance >= cost) {

            LogicalPixelmonMoneyHandler.remove(player.getUniqueID(), cost);
            NBTHelpers.clearTeraCount(pokemon);
            player.sendMessage(FancyText.getFormattedText("&eSuccessfully reset Tera Type changes for " + pokemon.getSpecies().getName() + "!"), player.getUniqueID());
            PartyMenu.openPartyMenu(player);

        } else {

            player.sendMessage(FancyText.getFormattedText("&cYou don't have enough money to pay the reset fee!"), player.getUniqueID());

        }

    }

}
