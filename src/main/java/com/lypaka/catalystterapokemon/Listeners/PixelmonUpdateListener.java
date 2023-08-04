package com.lypaka.catalystterapokemon.Listeners;

import com.lypaka.catalystterapokemon.Helpers.MiscHelpers;
import com.lypaka.catalystterapokemon.Helpers.NBTHelper;
import com.lypaka.catalystterapokemon.Helpers.ParticleHelpers;
import com.pixelmonmod.pixelmon.api.events.PixelmonUpdateEvent;
import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.pixelmonmod.pixelmon.api.pokemon.ribbon.Ribbon;
import com.pixelmonmod.pixelmon.api.pokemon.ribbon.RibbonRegistry;
import com.pixelmonmod.pixelmon.api.registries.PixelmonSpecies;
import com.pixelmonmod.pixelmon.api.util.helpers.RandomHelper;
import com.pixelmonmod.pixelmon.entities.pixelmon.PixelmonEntity;
import com.pixelmonmod.pixelmon.enums.EnumGrowth;
import net.minecraft.particles.RedstoneParticleData;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.Random;

public class PixelmonUpdateListener {

    @SubscribeEvent
    public void onUpdate (PixelmonUpdateEvent event) {

        PixelmonEntity pixelmon = event.pokemon;
        Pokemon pokemon = pixelmon.getPokemon();
        World world = pixelmon.world;
        Random rand = RandomHelper.getRandom();
        
        if (NBTHelper.isTerastallized(pokemon)) {

            if (world instanceof ServerWorld) {

                ServerWorld serverWorld = (ServerWorld) world;
                // Borrowing Gastly particle physics from Clover who borrowed it from Pixelmon to create Tera particle effects
                float var2 = 0.6F;
                float var4 = rand.nextFloat() * 3.1415927F * 2.0F;
                float var42 = rand.nextFloat() * 3.1415927F * 2.0F;
                float var5 = rand.nextFloat() + 0.5F;
                float var52 = rand.nextFloat() + 0.5F;
                float var6 = MathHelper.sin(var4) * var2 * 0.7F * var5;
                float var62 = MathHelper.sin(var42) * var2 * 0.7F * var52;
                float var7 = MathHelper.cos(var4) * var2 * 0.5F * var5;
                float var72 = MathHelper.cos(var42) * var2 * 0.5F * var52;
                float var8 = rand.nextFloat() * var2 * 1.4F - 0.2F;
                float var82 = rand.nextFloat() * var2 * 1.2F;
                double gravity = -0.5;
                RedstoneParticleData data = ParticleHelpers.getParticleColorForTera(pokemon);
                if (data == null) return;

                pokemon.addRibbon(MiscHelpers.teraRibbon, true);
//                pokemon.setDisplayedRibbon(Ribbon.builder().type(RibbonRegistry.SPECIAL.getValueUnsafe()).ribbonData(MiscHelpers.ribbonData).build());

                int i;
                // I like being able to read my lines, thank you
                if (pokemon.getGrowth() == EnumGrowth.Enormous) {
                    
                    for (i = 0; i < 1; i++) {
                        
                        serverWorld.spawnParticle(data, pixelmon.getPosX() + (double) var6 + 0.20000000298023224, pixelmon.getPosY() + 0.5 + (double) var8,
                                pixelmon.getPosZ() + (double) var7 + 0.4000000059604645, 1, 0, 0, 0, gravity);
                        
                    }

                    for (i = 0; i < 1; i++) {

                        serverWorld.spawnParticle(data, pixelmon.getPosX() + (double) var62 + 0.20000000298023224, pixelmon.getPosY() + 0.5 + (double) var82,
                                pixelmon.getPosZ() + (double) var72 - 0.6000000238418579, 1, 0, 0, 0, gravity);

                    }

                } else if (pokemon.getGrowth() == EnumGrowth.Huge) {

                    for (i = 0; i < 1; i++) {

                        serverWorld.spawnParticle(data, pixelmon.getPosX() + (double) var6 + 0.20000000298023224, pixelmon.getPosY() + 0.5 + (double) var8,
                                pixelmon.getPosZ() + (double) var7 + 0.4000000059604645, 1, 0, 0, 0, gravity);

                    }

                    for (i = 0; i < 1; i++) {

                        serverWorld.spawnParticle(data, pixelmon.getPosX() + (double) var62 + 0.20000000298023224, pixelmon.getPosY() + 0.5 + (double) var82,
                                pixelmon.getPosZ() + (double) var72 - 0.6000000238418579, 1, 0, 0, 0, gravity);

                    }

                } else if (pokemon.getGrowth() == EnumGrowth.Pygmy) {

                    for (i = 0; i < 1; i++) {

                        serverWorld.spawnParticle(data, pixelmon.getPosX() + (double) var6 + 0.20000000298023224, pixelmon.getPosY() + 0.5 + (double) var8,
                                pixelmon.getPosZ() + (double) var7 + 0.4000000059604645, 1, 0, 0, 0, gravity);

                    }

                } else {

                    for (i = 0; i < 1; i++) {

                        serverWorld.spawnParticle(data, pixelmon.getPosX() + (double) var6 + 0.20000000298023224, pixelmon.getPosY() + 0.5 + (double) var8,
                                pixelmon.getPosZ() + (double) var7 + 0.4000000059604645, 1, 0, 0, 0, gravity);

                    }

                    for (i = 0; i < 1; i++) {

                        serverWorld.spawnParticle(data, pixelmon.getPosX() + (double) var62 + 0.20000000298023224, pixelmon.getPosY() + 0.5 + (double) var82,
                                pixelmon.getPosZ() + (double) var72 - 0.6000000238418579, 1, 0, 0, 0, gravity);

                    }

                }

            }
            
        }

    }

}
