package com.lypaka.catalystterapokemon.Helpers;

import com.pixelmonmod.pixelmon.api.pokemon.Element;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// I'm not particularly fond of what follows, but I'm also too lazy to go looking through Pixelmon's code to see how they do this...so "manual Samuel" time!
public class DamageHelpers {

    public static Map<String, List<Element>> resistedTypes = new HashMap<>(); // Moves that would be not very effective against the Tera type
    public static Map<String, List<Element>> susceptibleTypes = new HashMap<>(); // Moves that would be super effective against the Tera type
    public static Map<String, List<Element>> immuneTypes = new HashMap<>(); // Moves that would just not hit the Tera type due to now being immune (Normal -> Ghost)

    public static void loadTypeEffectivenessMaps() {

        for (Element teraType : Element.getAllTypes()) {

            if (teraType != Element.MYSTERY) {

                List<Element> resisted = new ArrayList<>();
                List<Element> susceptible = new ArrayList<>();
                switch (teraType.getName().toLowerCase()) {

                    case "normal":
                        resistedTypes.put(teraType.getName(), resisted);
                        susceptibleTypes.put(teraType.getName(), susceptible);
                        break;

                    case "fire":
                        resisted.add(Element.FIRE);
                        resisted.add(Element.ICE);
                        resisted.add(Element.STEEL);
                        resisted.add(Element.FAIRY);
                        resistedTypes.put(teraType.getName(), resisted);

                        susceptible.add(Element.WATER);
                        susceptible.add(Element.GROUND);
                        susceptible.add(Element.ROCK);
                        susceptibleTypes.put(teraType.getName(), susceptible);
                        break;

                    case "water":
                        resisted.add(Element.FIRE);
                        resisted.add(Element.WATER);
                        resisted.add(Element.ICE);
                        resisted.add(Element.STEEL);
                        resistedTypes.put(teraType.getName(), resisted);

                        susceptible.add(Element.GRASS);
                        susceptible.add(Element.ELECTRIC);
                        susceptibleTypes.put(teraType.getName(), susceptible);
                        break;

                    case "grass":
                        resisted.add(Element.WATER);
                        resisted.add(Element.GRASS);
                        resisted.add(Element.ELECTRIC);
                        resisted.add(Element.GROUND);
                        resistedTypes.put(teraType.getName(), resisted);

                        susceptible.add(Element.FIRE);
                        susceptible.add(Element.ICE);
                        susceptible.add(Element.POISON);
                        susceptible.add(Element.FLYING);
                        susceptible.add(Element.BUG);
                        susceptibleTypes.put(teraType.getName(), susceptible);
                        break;

                    case "electric":
                        resisted.add(Element.ELECTRIC);
                        resisted.add(Element.FLYING);
                        resistedTypes.put(teraType.getName(), resisted);

                        susceptible.add(Element.GROUND);
                        break;

                    case "ice":
                        resisted.add(Element.ICE);
                        resistedTypes.put(teraType.getName(), resisted);
                        break;

                    case "fighting":
                        resisted.add(Element.BUG);
                        resisted.add(Element.ROCK);
                        resistedTypes.put(teraType.getName(), resisted);
                        break;

                    case "poison":
                        resisted.add(Element.GRASS);
                        resisted.add(Element.FIGHTING);
                        resisted.add(Element.POISON);
                        resisted.add(Element.BUG);
                        resisted.add(Element.FAIRY);
                        resistedTypes.put(teraType.getName(), resisted);
                        break;

                    case "ground":
                        resisted.add(Element.POISON);
                        resisted.add(Element.ROCK);
                        resistedTypes.put(teraType.getName(), resisted);
                        break;

                    case "flying":
                        resisted.add(Element.GRASS);
                        resisted.add(Element.FIGHTING);
                        resisted.add(Element.BUG);
                        resistedTypes.put(teraType.getName(), resisted);
                        break;

                    case "psychic":
                        resisted.add(Element.FIGHTING);
                        resisted.add(Element.PSYCHIC);
                        resistedTypes.put(teraType.getName(), resisted);
                        break;

                    case "bug":
                        resisted.add(Element.GRASS);
                        resisted.add(Element.FIGHTING);
                        resisted.add(Element.GROUND);
                        resistedTypes.put(teraType.getName(), resisted);
                        break;

                    case "rock":
                        resisted.add(Element.NORMAL);
                        resisted.add(Element.FIRE);
                        resisted.add(Element.POISON);
                        resisted.add(Element.FLYING);
                        resistedTypes.put(teraType.getName(), resisted);
                        break;

                    case "ghost":
                        resisted.add(Element.POISON);
                        resisted.add(Element.BUG);
                        resistedTypes.put(teraType.getName(), resisted);
                        break;

                    case "dragon":
                        resisted.add(Element.FIRE);
                        resisted.add(Element.WATER);
                        resisted.add(Element.GRASS);
                        resisted.add(Element.ELECTRIC);
                        resistedTypes.put(teraType.getName(), resisted);
                        break;

                    case "dark":
                        resisted.add(Element.GHOST);
                        resisted.add(Element.DARK);
                        resistedTypes.put(teraType.getName(), resisted);
                        break;

                    case "steel":
                        resisted.add(Element.NORMAL);
                        resisted.add(Element.GRASS);
                        resisted.add(Element.ICE);
                        resisted.add(Element.FLYING);
                        resisted.add(Element.PSYCHIC);
                        resisted.add(Element.BUG);
                        resisted.add(Element.ROCK);
                        resisted.add(Element.DRAGON);
                        resisted.add(Element.STEEL);
                        resisted.add(Element.FAIRY);
                        resistedTypes.put(teraType.getName(), resisted);
                        break;

                    case "fairy":
                        resisted.add(Element.FIGHTING);
                        resisted.add(Element.BUG);
                        resisted.add(Element.DARK);
                        resistedTypes.put(teraType.getName(), resisted);
                        break;

                }

            }

        }

    }

}
