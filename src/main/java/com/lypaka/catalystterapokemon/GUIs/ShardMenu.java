package com.lypaka.catalystterapokemon.GUIs;

import ca.landonjw.gooeylibs2.api.UIManager;
import ca.landonjw.gooeylibs2.api.button.Button;
import ca.landonjw.gooeylibs2.api.button.GooeyButton;
import ca.landonjw.gooeylibs2.api.page.GooeyPage;
import ca.landonjw.gooeylibs2.api.template.types.ChestTemplate;
import com.lypaka.catalystterapokemon.ConfigGetters;
import com.lypaka.catalystterapokemon.TeraItems.TeraItemUtils;
import com.lypaka.catalystterapokemon.TeraItems.TeraShard;
import com.lypaka.lypakautils.FancyText;
import com.lypaka.lypakautils.MiscHandlers.ItemStackBuilder;
import com.lypaka.lypakautils.MiscHandlers.LogicalPixelmonMoneyHandler;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.StringNBT;
import net.minecraft.util.text.ITextComponent;

import java.util.List;

public class ShardMenu {

    public static void openShardMenu (ServerPlayerEntity player) {

        ChestTemplate template = ChestTemplate.builder(3).build();
        GooeyPage page = GooeyPage.builder()
                .template(template)
                .title(FancyText.getFormattedText("&aTera Shards Menu"))
                .build();

        String[] types = new String[]{"Bug", "Dark", "Dragon", "Electric", "Fairy", "Fighting", "Fire", "Flying", "Ghost", "Grass", "Ground", "Ice", "Normal", "Poison", "Psychic", "Rock", "Steel", "Water"};
        for (int i = 0; i < types.length; i++) {

            int slot = i + 9;
            String type = types[i];
            TeraShard shard = TeraItemUtils.getFromType(type);
            ItemStack item = ItemStackBuilder.buildFromStringID(shard.getID());
            item.setDisplayName(FancyText.getFormattedText(shard.getDisplayName()));
            List<String> stringLore = shard.getGUILore();
            ListNBT lore = new ListNBT();

            for (String value : stringLore) {

                lore.add(StringNBT.valueOf(ITextComponent.Serializer.toJson(FancyText.getFormattedText(value.replace("%amountCharge%", String.valueOf(ConfigGetters.cost))))));

            }

            item.getOrCreateChildTag("display").put("Lore", lore);
            page.getTemplate().getSlot(slot).setButton(getButton(shard, item, player));

        }

        ItemStack partyMenu = ItemStackBuilder.buildFromStringID(ConfigGetters.partyID);
        partyMenu.setDisplayName(FancyText.getFormattedText(ConfigGetters.partyDisplayName));
        ListNBT partyLore = new ListNBT();
        for (String s : ConfigGetters.partyLore) {

            partyLore.add(StringNBT.valueOf(ITextComponent.Serializer.toJson(FancyText.getFormattedText(s))));

        }
        partyMenu.getOrCreateChildTag("display").put("Lore", partyLore);
        page.getTemplate().getSlot(3).setButton(
                GooeyButton.builder()
                        .display(partyMenu)
                        .onClick(() -> {

                            PartyMenu.openPartyMenu(player);

                        })
                        .build()
        );

        ItemStack orb = ItemStackBuilder.buildFromStringID(ConfigGetters.orbID);
        orb.setDisplayName(FancyText.getFormattedText(ConfigGetters.orbDisplayName));
        ListNBT orbLore = new ListNBT();
        int charges = ConfigGetters.playerCharges.getOrDefault(player.getUniqueID().toString(), 0);
        for (String s : ConfigGetters.orbLore) {

            orbLore.add(StringNBT.valueOf(ITextComponent.Serializer.toJson(FancyText.getFormattedText(s
                    .replace("%currentCharge%", String.valueOf(charges))
                    .replace("%maxCharge%", String.valueOf(ConfigGetters.maxCharges))
            ))));

        }
        orb.getOrCreateChildTag("display").put("Lore", orbLore);
        page.getTemplate().getSlot(5).setButton(GooeyButton.builder().display(orb).build());

        // slots 0, 1, 2, 3, 5, 6, 7, 8 for glass
        // 0, 2, 6, 8 purple
        // 1, 3, 5, 7 pink
        int[] purpleSlots = new int[]{0, 2, 6, 8};
        int[] pinkSlots = new int[]{1, 4, 7};

        for (int i : purpleSlots) {

            ItemStack glass = ItemStackBuilder.buildFromStringID("minecraft:purple_stained_glass_pane");
            glass.setDisplayName(FancyText.getFormattedText(""));
            page.getTemplate().getSlot(i).setButton(GooeyButton.builder().display(glass).build());

        }
        for (int i : pinkSlots) {

            ItemStack glass = ItemStackBuilder.buildFromStringID("minecraft:pink_stained_glass_pane");
            glass.setDisplayName(FancyText.getFormattedText(""));
            page.getTemplate().getSlot(i).setButton(GooeyButton.builder().display(glass).build());

        }

        UIManager.openUIForcefully(player, page);

    }

    private static Button getButton (TeraShard shard, ItemStack item, ServerPlayerEntity player) {

        int charges = ConfigGetters.playerCharges.getOrDefault(player.getUniqueID().toString(), 0);
        Button button;

        if (charges >= ConfigGetters.cost) {

            int updatedCharges = charges - ConfigGetters.cost;
            List<String> newLore = shard.getItemLore();
            ListNBT lore = new ListNBT();
            for (String s : newLore) {

                lore.add(StringNBT.valueOf(ITextComponent.Serializer.toJson(FancyText.getFormattedText(s))));

            }
            ItemStack toGive = ItemStackBuilder.buildFromStringID(shard.getID());
            toGive.setDisplayName(FancyText.getFormattedText(shard.getDisplayName()));
            toGive.getOrCreateChildTag("display").put("Lore", lore);
            toGive.setCount(1);
            button = GooeyButton.builder()
                    .display(item)
                    .onClick(action -> {

                        player.closeContainer();
                        LogicalPixelmonMoneyHandler.remove(player.getUniqueID(), ConfigGetters.cost);
                        player.addItemStackToInventory(toGive);
                        ConfigGetters.playerCharges.put(player.getUniqueID().toString(), updatedCharges);
                        player.sendMessage(FancyText.getFormattedText("&eYou've created 1 " + shard.getType() + " Tera Shard!"), player.getUniqueID());

                    })
                    .build();

        } else {

            button = GooeyButton.builder()
                    .display(item)
                    .onClick(action -> {

                        player.sendMessage(FancyText.getFormattedText("&cYou don't have enough charges in your Tera Orb to create a shard!"), player.getUniqueID());

                    })
                    .build();

        }

        return button;

    }

}
