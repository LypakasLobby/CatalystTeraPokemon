package com.lypaka.catalystterapokemon.Listeners;

import com.lypaka.catalystterapokemon.CatalystTeraPokemon;
import com.lypaka.catalystterapokemon.Helpers.DamageHelpers;
import com.lypaka.catalystterapokemon.Helpers.MiscHelpers;
import com.lypaka.catalystterapokemon.TeraItems.TeraItemUtils;
import com.pixelmonmod.pixelmon.Pixelmon;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.server.FMLServerStartedEvent;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;

@Mod.EventBusSubscriber(modid = CatalystTeraPokemon.MOD_ID)
public class ServerStartedListener {

    @SubscribeEvent
    public static void onServerStarted (FMLServerStartedEvent event) throws ObjectMappingException {

       // MinecraftForge.EVENT_BUS.register(new InteractionListener());
        MinecraftForge.EVENT_BUS.register(new JoinListener());

        Pixelmon.EVENT_BUS.register(new BattleEndListener());
        Pixelmon.EVENT_BUS.register(new BattleStartListener());
        Pixelmon.EVENT_BUS.register(new DamageListeners());
        Pixelmon.EVENT_BUS.register(new FaintListener());
        Pixelmon.EVENT_BUS.register(new KillListener());
        Pixelmon.EVENT_BUS.register(new PixelmonUpdateListener());
        Pixelmon.EVENT_BUS.register(new RaidListeners());
        Pixelmon.EVENT_BUS.register(new RetrieveListener());

        MiscHelpers.setRibbonData();
        MiscHelpers.loadRibbon();
        DamageHelpers.loadTypeEffectivenessMaps();
        TeraItemUtils.loadShards();

    }

}
