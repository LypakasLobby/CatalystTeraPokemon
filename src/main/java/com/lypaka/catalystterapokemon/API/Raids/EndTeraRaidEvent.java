package com.lypaka.catalystterapokemon.API.Raids;

import com.lypaka.catalystterapokemon.Raids.TeraRaid;
import com.lypaka.catalystterapokemon.Raids.TeraRaidPokemon;
import com.pixelmonmod.pixelmon.battles.controller.participants.BattleParticipant;
import net.minecraftforge.eventbus.api.Event;

import java.util.ArrayList;

public class EndTeraRaidEvent extends Event {

    private final TeraRaid raid;
    private final TeraRaidPokemon pokemon;
    private final ArrayList<BattleParticipant> battleParticipants;

    public EndTeraRaidEvent (TeraRaid raid, TeraRaidPokemon pokemon, ArrayList<BattleParticipant> battleParticipants) {

        this.raid = raid;
        this.pokemon = pokemon;
        this.battleParticipants = battleParticipants;

    }

    public TeraRaid getRaid() {

        return this.raid;

    }

    public TeraRaidPokemon getPokemon() {

        return this.pokemon;

    }

    public ArrayList<BattleParticipant> getBattleParticipants() {

        return this.battleParticipants;

    }

}
