package com.lypaka.catalystterapokemon.API;

import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.eventbus.api.Cancelable;
import net.minecraftforge.eventbus.api.Event;

/**
 * Called when the server tries to set a Tera Type on a Pokemon that was just created (i.e. from spawning/crates/commands/etc)
 */
@Cancelable
public class SetTeraTypeEvent extends Event {

    private final ServerPlayerEntity player;
    private final Pokemon pokemon;
    private String teraType;
    private final String cause; // used from the PokemonReceivedEvent

    public SetTeraTypeEvent (ServerPlayerEntity player, Pokemon pokemon, String teraType, String cause) {

        this.player = player;
        this.pokemon = pokemon;
        this.teraType = teraType;
        this.cause = cause;

    }

    public ServerPlayerEntity getPlayer() {

        return this.player;

    }

    public Pokemon getPokemon() {

        return this.pokemon;

    }

    public String getTeraType() {

        return this.teraType;

    }

    public void setTeraType (String type) {

        this.teraType = type;

    }

    public String getCause() {

        return this.cause;

    }

}
