package com.lypaka.catalystterapokemon.Helpers;

import com.lypaka.catalystterapokemon.ConfigGetters;
import com.lypaka.lypakautils.FancyText;
import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.pixelmonmod.pixelmon.api.pokemon.ribbon.MutableRibbonData;
import com.pixelmonmod.pixelmon.api.pokemon.ribbon.Ribbon;
import com.pixelmonmod.pixelmon.api.pokemon.ribbon.RibbonRegistry;
import net.minecraft.particles.RedstoneParticleData;

public class ParticleHelpers {

    public static RedstoneParticleData getParticleColorForTera (Pokemon pokemon) {

        String teraType = NBTHelper.getTeraType(pokemon);
        RedstoneParticleData data = null;
        if (ConfigGetters.particleColors.containsKey(teraType)) {

            String[] rgb = ConfigGetters.particleColors.get(teraType).split(", ");
            float r = Float.parseFloat(rgb[0]) / 1000;
            float g = Float.parseFloat(rgb[1]) / 1000;
            float b = Float.parseFloat(rgb[2]) / 1000;
            data = new RedstoneParticleData(r, g, b, 1.0f);
            MutableRibbonData ribbonData = new MutableRibbonData();
            ribbonData.setPrefix(FancyText.getFormattedText("Tera "));
            pokemon.setDisplayedRibbon(Ribbon.builder().type(RibbonRegistry.SPECIAL.getValueUnsafe()).ribbonData(ribbonData).build());

        }

        return data;

    }

}
