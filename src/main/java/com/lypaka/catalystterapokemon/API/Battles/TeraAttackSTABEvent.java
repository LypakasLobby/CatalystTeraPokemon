package com.lypaka.catalystterapokemon.API.Battles;

import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.pixelmonmod.pixelmon.battles.controller.participants.BattleParticipant;
import net.minecraftforge.eventbus.api.Cancelable;
import net.minecraftforge.eventbus.api.Event;

/**
 * Called messing with the STAB multiplier. Canceling the event will cancel all modifications
 */

@Cancelable
public class TeraAttackSTABEvent extends Event {

    private final BattleParticipant userOwner;
    private final BattleParticipant victimOwner;
    private final Pokemon userPokemon;
    private final Pokemon victimPokemon;

    public TeraAttackSTABEvent (BattleParticipant userOwner, BattleParticipant victimOwner, Pokemon userPokemon, Pokemon victimPokemon) {

        this.userOwner = userOwner;
        this.victimOwner = victimOwner;
        this.userPokemon = userPokemon;
        this.victimPokemon = victimPokemon;

    }

    public BattleParticipant getUserOwner() {

        return this.userOwner;

    }

    public BattleParticipant getVictimOwner() {

        return this.victimOwner;

    }

    public Pokemon getUserPokemon() {

        return this.userPokemon;

    }

    public Pokemon getVictimPokemon() {

        return this.victimPokemon;

    }

}
