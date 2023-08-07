package com.lypaka.catalystterapokemon.Helpers;

import com.lypaka.catalystterapokemon.TeraBattle;
import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.pixelmonmod.pixelmon.battles.controller.BattleController;
import com.pixelmonmod.pixelmon.battles.controller.participants.BattleParticipant;
import com.pixelmonmod.pixelmon.battles.controller.participants.PlayerParticipant;
import com.pixelmonmod.pixelmon.items.heldItems.MegaStoneItem;
import com.pixelmonmod.pixelmon.items.heldItems.ZCrystalItem;
import net.minecraft.entity.player.ServerPlayerEntity;

import java.util.*;

public class BattleHelpers {

    public static List<TeraBattle> teraBattles = new ArrayList<>();
    public static Map<BattleController, UUID> battleMap = new HashMap<>(); // used to clear Tera Battle object on battle end

    public static TeraBattle getTeraBattleFromBattleController (BattleController bc) {

        UUID battleUUID = battleMap.getOrDefault(bc, null);
        if (battleUUID == null) return null;
        for (TeraBattle tb : teraBattles) {

            UUID uuid = tb.getUUID();
            if (uuid.toString().equalsIgnoreCase(battleUUID.toString())) {

                return tb;

            }

        }

        return null;

    }

    public static TeraBattle getTeraBattleFromPlayer (ServerPlayerEntity player) {

        for (TeraBattle tb : teraBattles) {

            BattleController bc = tb.getBattleController();
            List<BattleParticipant> participants = bc.participants;
            for (BattleParticipant bp : participants) {

                if (bp instanceof PlayerParticipant) {

                    PlayerParticipant pp = (PlayerParticipant) bp;
                    ServerPlayerEntity playerP = pp.player;
                    if (player.getUniqueID().toString().equalsIgnoreCase(playerP.getUniqueID().toString())) return tb;

                }

            }

        }

        return null;

    }

    public static boolean canPokemonTera (Pokemon pokemon) {

        if (pokemon.getHeldItemAsItemHeld() instanceof MegaStoneItem) return false;
        if (pokemon.getHeldItemAsItemHeld() instanceof ZCrystalItem) return false;

        for (int i = 0; i < 4; i++) {

            String move = pokemon.getMoveset().attacks[i].getActualMove().getAttackName();
            if (move.contains("Max")) return false; // Pokemon is Dynamaxed/GMaxed
            if (move.equalsIgnoreCase("Dragon Ascent") || move.equalsIgnoreCase("DragonAscent")) {

                if (pokemon.getSpecies().getName().equalsIgnoreCase("Rayquaza")) {

                    return false;

                }

            }

        }

        return true;

    }

}
