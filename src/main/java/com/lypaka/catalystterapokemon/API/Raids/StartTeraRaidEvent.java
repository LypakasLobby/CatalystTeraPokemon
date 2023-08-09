package com.lypaka.catalystterapokemon.API.Raids;

import com.pixelmonmod.pixelmon.battles.controller.participants.BattleParticipant;
import com.pixelmonmod.pixelmon.battles.controller.participants.PlayerParticipant;
import com.pixelmonmod.pixelmon.battles.controller.participants.RaidPixelmonParticipant;
import com.pixelmonmod.pixelmon.entities.DenEntity;
import net.minecraftforge.eventbus.api.Cancelable;
import net.minecraftforge.eventbus.api.Event;

import java.util.ArrayList;

@Cancelable
public class StartTeraRaidEvent extends Event {

    private final RaidPixelmonParticipant rpp;
    private final DenEntity den;
    private final ArrayList<BattleParticipant> battleParticipants;
    private final int stars;

    public StartTeraRaidEvent (RaidPixelmonParticipant rpp, DenEntity den, ArrayList<BattleParticipant> battleParticipants, int stars) {

        this.rpp = rpp;
        this.den = den;
        this.battleParticipants = battleParticipants;
        this.stars = stars;

    }

    public RaidPixelmonParticipant getRaidPokemon() {

        return this.rpp;

    }

    public DenEntity getDen() {

        return this.den;

    }

    public ArrayList<BattleParticipant> getBattleParticipants() {

        return this.battleParticipants;

    }

    public int getStars() {

        return this.stars;

    }

}
