package com.lypaka.catalystterapokemon.Listeners;

import com.lypaka.catalystterapokemon.Helpers.BattleHelpers;
import com.lypaka.catalystterapokemon.TeraBattle;
import com.pixelmonmod.pixelmon.api.events.battles.BattleEndEvent;
import com.pixelmonmod.pixelmon.api.events.battles.ForceEndBattleEvent;
import com.pixelmonmod.pixelmon.battles.controller.BattleController;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class BattleEndListener {

    @SubscribeEvent
    public void onBattleForceEnd (ForceEndBattleEvent event) {

        BattleController bc = event.bc;
        TeraBattle teraBattle = BattleHelpers.getTeraBattleFromBattleController(bc);
        if (teraBattle == null) return;

        BattleHelpers.teraBattles.removeIf(entry -> entry.getUUID().toString().equalsIgnoreCase(teraBattle.getUUID().toString()));

    }

    @SubscribeEvent
    public void onBattleEnd (BattleEndEvent event) {

        BattleController bc = event.getBattleController();
        TeraBattle teraBattle = BattleHelpers.getTeraBattleFromBattleController(bc);
        if (teraBattle == null) return;

        BattleHelpers.teraBattles.removeIf(entry -> entry.getUUID().toString().equalsIgnoreCase(teraBattle.getUUID().toString()));

    }

}
