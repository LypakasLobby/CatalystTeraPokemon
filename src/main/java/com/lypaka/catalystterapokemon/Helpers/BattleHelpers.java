package com.lypaka.catalystterapokemon.Helpers;

import com.lypaka.catalystterapokemon.TeraBattle;
import com.pixelmonmod.pixelmon.battles.controller.BattleController;
import com.pixelmonmod.pixelmon.battles.controller.participants.BattleParticipant;
import com.pixelmonmod.pixelmon.battles.controller.participants.PlayerParticipant;
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

}
