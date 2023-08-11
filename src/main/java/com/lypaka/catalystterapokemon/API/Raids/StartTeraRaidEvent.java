package com.lypaka.catalystterapokemon.API.Raids;

import com.lypaka.catalystterapokemon.Raids.TeraRaid;
import com.lypaka.catalystterapokemon.Raids.TeraRaidPokemon;
import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.pixelmonmod.pixelmon.battles.controller.participants.BattleParticipant;
import net.minecraftforge.eventbus.api.Cancelable;
import net.minecraftforge.eventbus.api.Event;

import java.util.List;

@Cancelable
public class StartTeraRaidEvent extends Event {

    private final TeraRaid raid;
    private TeraRaidPokemon raidPokemon;
    private final List<BattleParticipant> battleParticipants;
    private Pokemon pokemon;

    public StartTeraRaidEvent (TeraRaid raid, TeraRaidPokemon raidPokemon, List<BattleParticipant> battleParticipants, Pokemon pokemon) {

        this.raid = raid;
        this.raidPokemon = raidPokemon;
        this.battleParticipants = battleParticipants;

    }

    public TeraRaid getRaid() {

        return this.raid;

    }

    public TeraRaidPokemon getRaidPokemon() {

        return this.raidPokemon;

    }

    public void setRaidPokemon (TeraRaidPokemon raidPokemon) {

        this.raidPokemon = raidPokemon;

    }

    public List<BattleParticipant> getBattleParticipants() {

        return this.battleParticipants;

    }

    public Pokemon getPokemon() {

        return this.pokemon;

    }

    public void setPokemon (Pokemon pokemon) {

        this.pokemon = pokemon;

    }

}
