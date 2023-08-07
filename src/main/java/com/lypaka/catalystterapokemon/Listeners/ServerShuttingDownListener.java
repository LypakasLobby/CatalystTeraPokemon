package com.lypaka.catalystterapokemon.Listeners;

import com.lypaka.catalystterapokemon.CatalystTeraPokemon;
import com.lypaka.catalystterapokemon.ConfigGetters;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.server.FMLServerStoppingEvent;

@Mod.EventBusSubscriber(modid = CatalystTeraPokemon.MOD_ID)
public class ServerShuttingDownListener {

    @SubscribeEvent
    public static void onServerShuttingDown (FMLServerStoppingEvent event) {

        CatalystTeraPokemon.configManager.getConfigNode(2, "Charges").setValue(ConfigGetters.playerCharges);
        CatalystTeraPokemon.configManager.save();

    }

}
