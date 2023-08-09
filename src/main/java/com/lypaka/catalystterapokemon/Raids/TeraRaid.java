package com.lypaka.catalystterapokemon.Raids;

import com.pixelmonmod.pixelmon.battles.controller.participants.BattleParticipant;
import com.pixelmonmod.pixelmon.battles.controller.participants.RaidPixelmonParticipant;
import com.pixelmonmod.pixelmon.entities.DenEntity;

import java.util.ArrayList;

public class TeraRaid {

    private RaidPixelmonParticipant rpp;
    private final DenEntity den;
    private final ArrayList<BattleParticipant> battleParticipants;
    private final int stars;

    public TeraRaid (RaidPixelmonParticipant rpp, DenEntity den, ArrayList<BattleParticipant> battleParticipants, int stars) {

        this.rpp = rpp;
        this.den = den;
        this.battleParticipants = battleParticipants;
        this.stars = stars;

    }

}
