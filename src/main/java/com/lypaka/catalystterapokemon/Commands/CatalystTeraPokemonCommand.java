package com.lypaka.catalystterapokemon.Commands;

import com.lypaka.catalystterapokemon.CatalystTeraPokemon;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.server.command.ConfigCommand;

import java.util.Arrays;
import java.util.List;

@Mod.EventBusSubscriber(modid = CatalystTeraPokemon.MOD_ID)
public class CatalystTeraPokemonCommand {

    public static List<String> ALIASES = Arrays.asList("catalystterapokemon", "ctp", "tera", "terapokemon");

    @SubscribeEvent
    public static void onCommandRegistration (RegisterCommandsEvent event) {

        new GiveShardCommand(event.getDispatcher());
        new LocateDenCommand(event.getDispatcher());
        new MenuCommand(event.getDispatcher());
        new ReloadCommand(event.getDispatcher());
        new ResetCommand(event.getDispatcher());
        new SetTeraTypeCommand(event.getDispatcher());
        new TerastallizeCommand(event.getDispatcher());

        ConfigCommand.register(event.getDispatcher());

    }

}
