package com.lypaka.catalystterapokemon.API;

import com.lypaka.catalystterapokemon.TeraItems.TeraShard;
import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.eventbus.api.Cancelable;
import net.minecraftforge.eventbus.api.Event;

@Cancelable
public class TeraShardEvent extends Event {

    private final ServerPlayerEntity player;
    private final Pokemon pokemon;
    private final TeraShard teraShard;

    public TeraShardEvent (ServerPlayerEntity player, Pokemon pokemon, TeraShard teraShard) {

        this.player = player;
        this.pokemon = pokemon;
        this.teraShard = teraShard;

    }

    public ServerPlayerEntity getPlayer() {

        return this.player;

    }

    public Pokemon getPokemon() {

        return this.pokemon;

    }

    public TeraShard getTeraShard() {

        return this.teraShard;

    }

}
