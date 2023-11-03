package com.lypaka.catalystterapokemon.API.Battles;

import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.pixelmonmod.pixelmon.battles.attacks.Effectiveness;
import com.pixelmonmod.pixelmon.battles.controller.participants.BattleParticipant;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.eventbus.api.Cancelable;
import net.minecraftforge.eventbus.api.Event;

/**
 *
 * Called when an Attack is used in battle
 * Canceling the event will cancel all checks and the Attack will proceed without Tera Typings being considered
 *
 */
@Cancelable
public class TeraAttackEffectivenessEvent extends Event {

    private final BattleParticipant userOwner;
    private final BattleParticipant victimOwner;
    private final Pokemon userPokemon;
    private final Pokemon victimPokemon;
    private Effectiveness effectiveness;

    public TeraAttackEffectivenessEvent (BattleParticipant userOwner, BattleParticipant victimOwner, Pokemon userPokemon, Pokemon victimPokemon, Effectiveness effectiveness) {

        this.userOwner = userOwner;
        this.victimOwner = victimOwner;
        this.userPokemon = userPokemon;
        this.victimPokemon = victimPokemon;
        this.effectiveness = effectiveness;

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

    public Effectiveness getEffectiveness() {

        return this.effectiveness;

    }

    public void setEffectiveness (Effectiveness effectiveness) {

        this.effectiveness = effectiveness;

    }

}
