package com.lypaka.catalystterapokemon.TeraItems;

import com.google.common.reflect.TypeToken;
import com.lypaka.catalystterapokemon.CatalystTeraPokemon;
import com.lypaka.catalystterapokemon.ConfigGetters;
import net.minecraft.item.ItemStack;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TeraItemUtils {

    public static List<TeraShard> teraShards;

    public static void loadShards() throws ObjectMappingException {

        teraShards = new ArrayList<>();
        for (Map.Entry<String, Map<String, String>> entry : ConfigGetters.shardItems.entrySet()) {

            String type = entry.getKey();
            String id = CatalystTeraPokemon.configManager.getConfigNode(1, "Shards", type, "ID").getString();
            String displayName = CatalystTeraPokemon.configManager.getConfigNode(1, "Shards", type, "Display-Name").getString();
            List<String> loreGUI = CatalystTeraPokemon.configManager.getConfigNode(1, "Shards", type, "Lore-GUI").getList(TypeToken.of(String.class));
            List<String> loreItem = CatalystTeraPokemon.configManager.getConfigNode(1, "Shards", type, "Lore-Item").getList(TypeToken.of(String.class));

            TeraShard shard = new TeraShard(type, id, displayName, loreGUI, loreItem);
            teraShards.add(shard);

        }

    }

    public static TeraShard getFromPlayerItem (ItemStack item) {

        TeraShard shard = null;
        String id = item.getItem().getRegistryName().toString();
        String displayName = item.getDisplayName().getUnformattedComponentText();

        for (TeraShard teraShard : TeraItemUtils.teraShards) {

            String shardID = teraShard.getID();
            String shardDisplayName = teraShard.getDisplayName();

            if (id.equalsIgnoreCase(shardID) && displayName.equalsIgnoreCase(shardDisplayName)) {

                shard = teraShard;
                break;

            }

        }

        return shard;

    }

    public static TeraShard getFromType (String type) {

        TeraShard shard = null;
        for (TeraShard teraShard : TeraItemUtils.teraShards) {

            if (teraShard.getType().equalsIgnoreCase(type)) {

                shard = teraShard;
                break;

            }

        }

        return shard;

    }

}
