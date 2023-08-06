package com.lypaka.catalystterapokemon;

import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.pixelmonmod.pixelmon.battles.controller.BattleController;

import java.util.List;
import java.util.UUID;

public class TeraBattle {

    private final BattleController bc;
    private final UUID uuid;
    private List<Pokemon> toTera;

    public TeraBattle (BattleController bc, UUID uuid, List<Pokemon> toTera) {

        this.bc = bc;
        this.uuid = uuid;
        this.toTera = toTera;

    }

    public BattleController getBattleController() {

        return this.bc;

    }

    public UUID getUUID() {

        return this.uuid;

    }

    public List<Pokemon> getPokemonToTera() {

        return this.toTera;

    }

    public void setPokemonToTera (List<Pokemon> toTera) {

        this.toTera = toTera;

    }

}
