package com.lypaka.catalystterapokemon.Helpers;

import com.lypaka.lypakautils.FancyText;
import com.pixelmonmod.pixelmon.api.pokemon.ribbon.MutableRibbonData;
import com.pixelmonmod.pixelmon.api.pokemon.ribbon.Ribbon;
import com.pixelmonmod.pixelmon.api.pokemon.ribbon.RibbonRegistry;

import java.time.LocalDateTime;

public class MiscHelpers {

    public static MutableRibbonData ribbonData = new MutableRibbonData();
    public static Ribbon teraRibbon;

    public static void setRibbonData() {

        ribbonData.setPrefix(FancyText.getFormattedText("Tera "));

    }

    public static void loadRibbon() {

        teraRibbon = new Ribbon(RibbonRegistry.SPECIAL.getKey(), LocalDateTime.now().getDayOfYear(), FancyText.getFormattedText(""), ribbonData);

    }

}
