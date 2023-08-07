package com.lypaka.catalystterapokemon.API;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.eventbus.api.Cancelable;
import net.minecraftforge.eventbus.api.Event;

@Cancelable
public class OrbChargeEvent extends Event {

    private final ServerPlayerEntity player;
    private int count;

    public OrbChargeEvent (ServerPlayerEntity player, int count) {

        this.player = player;
        this.count = count;

    }

    public ServerPlayerEntity getPlayer() {

        return this.player;

    }

    public int getCount() {

        return this.count;

    }

    public void setCount (int count) {

        this.count = count;

    }

}
