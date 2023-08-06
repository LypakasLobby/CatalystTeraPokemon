package com.lypaka.catalystterapokemon;

import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.pixelmonmod.pixelmon.battles.controller.BattleController;

import java.util.List;

public class TeraBattle {

    private final BattleController bc;
    private List<Pokemon> toTera;

    public TeraBattle (BattleController bc, List<Pokemon> toTera) {

        this.bc = bc;
        this.toTera = toTera;

    }

    public BattleController getBattleController() {

        return this.bc;

    }

    public List<Pokemon> getPokemonToTera() {

        return this.toTera;

    }

    public void setPokemonToTera (List<Pokemon> toTera) {

        this.toTera = toTera;

    }

}
