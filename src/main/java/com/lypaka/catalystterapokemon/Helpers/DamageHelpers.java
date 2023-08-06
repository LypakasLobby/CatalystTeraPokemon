package com.lypaka.catalystterapokemon.Helpers;

import com.pixelmonmod.pixelmon.api.pokemon.Element;
import com.pixelmonmod.pixelmon.battles.attacks.Attack;

import java.util.*;

// I'm not particularly fond of what follows, but I'm also too lazy to go looking through Pixelmon's code to see how they do this...so "manual Samuel" time!
public class DamageHelpers {

    private static final List<String> powderMoves = Arrays.asList("Cotton Spore", "CottonSpore", "MagicPowder", "Magic Powder", "PoisonPowder", "Poison Powder", "Powder", "Rage Powder", "RagePowder", "Sleep Powder", "SleepPowder", "Spore", "StunSpore", "Stun Spore");
    public static Map<String, List<Element>> resistedTypes = new HashMap<>(); // Moves that would be not very effective against the Tera type
    public static Map<String, List<Element>> susceptibleTypes = new HashMap<>(); // Moves that would be super effective against the Tera type
    public static Map<String, List<Element>> immuneTypes = new HashMap<>(); // Moves that would just not hit the Tera type due to now being immune (Normal -> Ghost)

    public static boolean dealsSuperEffectiveDamage (String teraType, Attack attack) {

        boolean superEffective = false;
        List<Element> susceptible = susceptibleTypes.get(teraType);
        Element attackType = attack.getActualType();

        if (susceptible.contains(attackType)) superEffective = true;

        if (teraType.equalsIgnoreCase("Water") && attack.getActualMove().getAttackName().equalsIgnoreCase("Freeze Dry") ||
            teraType.equalsIgnoreCase("Water") && attack.getActualMove().getAttackName().equalsIgnoreCase("FreezeDry")) superEffective = true;

        if (attack.getActualMove().getAttackName().equalsIgnoreCase("Flying Press") || attack.getActualMove().getAttackName().equalsIgnoreCase("FlyingPress")) {

            if (teraType.equalsIgnoreCase("Normal") || teraType.equalsIgnoreCase("Grass") || teraType.equalsIgnoreCase("Ice") || teraType.equalsIgnoreCase("Fighting") || teraType.equalsIgnoreCase("Dark")) superEffective = true;

        }

        return superEffective;

    }

    public static boolean isImmuneToAttack (String teraType, String heldItem, Attack attack) {

        boolean immune = false;
        Element attackType = attack.getType();
        List<Element> types = immuneTypes.get(teraType);
        if (types.isEmpty()) return false;

        if (types.contains(attackType)) immune = true;

        if (heldItem.equalsIgnoreCase("pixelmon:air_balloon")) {

            if (attackType == Element.GROUND) return true;

        } else if (heldItem.equalsIgnoreCase("pixelmon:ring_target")) {

            if (teraType.equalsIgnoreCase("Ghost") && attackType == Element.NORMAL) return false;
            if (teraType.equalsIgnoreCase("Ghost") && attackType == Element.FIGHTING) return false;
            if (teraType.equalsIgnoreCase("Normal") && attackType == Element.GHOST) return false;
            if (teraType.equalsIgnoreCase("Flying") && attackType == Element.GROUND) return false;
            if (teraType.equalsIgnoreCase("Dark") && attackType == Element.PSYCHIC) return false;
            if (teraType.equalsIgnoreCase("Fairy") && attackType == Element.DRAGON) return false;
            if (teraType.equalsIgnoreCase("Steel") && attackType == Element.POISON) return false;

        }

        if (teraType.equalsIgnoreCase("Grass")) {

            for (String m : powderMoves) {

                if (attack.getActualMove().getAttackName().equalsIgnoreCase(m)) {

                    immune = true;
                    break;

                }

            }

        }

        return immune;

    }

    public static void loadTypeEffectivenessMaps() {

        for (Element teraType : Element.getAllTypes()) {

            if (teraType != Element.MYSTERY) {

                List<Element> resisted = new ArrayList<>();
                List<Element> susceptible = new ArrayList<>();
                List<Element> immune = new ArrayList<>();
                switch (teraType.getName().toLowerCase()) {

                    case "normal":
                        resistedTypes.put(teraType.getName(), resisted);
                        susceptibleTypes.put(teraType.getName(), susceptible);

                        immune.add(Element.GHOST);
                        immuneTypes.put(teraType.getName(), immune);
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

                        immuneTypes.put(teraType.getName(), immune);
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

                        immuneTypes.put(teraType.getName(), immune);
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

                        immuneTypes.put(teraType.getName(), immune);
                        break;

                    case "electric":
                        resisted.add(Element.ELECTRIC);
                        resisted.add(Element.FLYING);
                        resistedTypes.put(teraType.getName(), resisted);

                        susceptible.add(Element.GROUND);
                        susceptibleTypes.put(teraType.getName(), susceptible);

                        immuneTypes.put(teraType.getName(), immune);
                        break;

                    case "ice":
                        resisted.add(Element.ICE);
                        resistedTypes.put(teraType.getName(), resisted);

                        susceptible.add(Element.FIRE);
                        susceptible.add(Element.FIGHTING);
                        susceptible.add(Element.ROCK);
                        susceptible.add(Element.STEEL);
                        susceptibleTypes.put(teraType.getName(), susceptible);

                        immuneTypes.put(teraType.getName(), immune);
                        break;

                    case "fighting":
                        resisted.add(Element.BUG);
                        resisted.add(Element.ROCK);
                        resistedTypes.put(teraType.getName(), resisted);

                        susceptible.add(Element.FLYING);
                        susceptible.add(Element.PSYCHIC);
                        susceptible.add(Element.FAIRY);
                        susceptibleTypes.put(teraType.getName(), susceptible);

                        immuneTypes.put(teraType.getName(), immune);
                        break;

                    case "poison":
                        resisted.add(Element.GRASS);
                        resisted.add(Element.FIGHTING);
                        resisted.add(Element.POISON);
                        resisted.add(Element.BUG);
                        resisted.add(Element.FAIRY);
                        resistedTypes.put(teraType.getName(), resisted);

                        susceptible.add(Element.GROUND);
                        susceptible.add(Element.PSYCHIC);
                        susceptibleTypes.put(teraType.getName(), susceptible);

                        immuneTypes.put(teraType.getName(), immune);
                        break;

                    case "ground":
                        resisted.add(Element.POISON);
                        resisted.add(Element.ROCK);
                        resistedTypes.put(teraType.getName(), resisted);

                        susceptible.add(Element.WATER);
                        susceptible.add(Element.GRASS);
                        susceptible.add(Element.ICE);
                        susceptibleTypes.put(teraType.getName(), susceptible);

                        immune.add(Element.ELECTRIC);
                        immuneTypes.put(teraType.getName(), immune);
                        break;

                    case "flying":
                        resisted.add(Element.GRASS);
                        resisted.add(Element.FIGHTING);
                        resisted.add(Element.BUG);
                        resistedTypes.put(teraType.getName(), resisted);

                        susceptible.add(Element.ELECTRIC);
                        susceptible.add(Element.ICE);
                        susceptible.add(Element.ROCK);
                        susceptibleTypes.put(teraType.getName(), susceptible);

                        immune.add(Element.GROUND);
                        immuneTypes.put(teraType.getName(), immune);
                        break;

                    case "psychic":
                        resisted.add(Element.FIGHTING);
                        resisted.add(Element.PSYCHIC);
                        resistedTypes.put(teraType.getName(), resisted);

                        susceptible.add(Element.BUG);
                        susceptible.add(Element.GHOST);
                        susceptible.add(Element.DARK);
                        susceptibleTypes.put(teraType.getName(), susceptible);

                        immuneTypes.put(teraType.getName(), immune);
                        break;

                    case "bug":
                        resisted.add(Element.GRASS);
                        resisted.add(Element.FIGHTING);
                        resisted.add(Element.GROUND);
                        resistedTypes.put(teraType.getName(), resisted);

                        susceptible.add(Element.FIRE);
                        susceptible.add(Element.FLYING);
                        susceptible.add(Element.ROCK);
                        susceptibleTypes.put(teraType.getName(), susceptible);

                        immuneTypes.put(teraType.getName(), immune);
                        break;

                    case "rock":
                        resisted.add(Element.NORMAL);
                        resisted.add(Element.FIRE);
                        resisted.add(Element.POISON);
                        resisted.add(Element.FLYING);
                        resistedTypes.put(teraType.getName(), resisted);

                        susceptible.add(Element.WATER);
                        susceptible.add(Element.GRASS);
                        susceptible.add(Element.FIGHTING);
                        susceptible.add(Element.GROUND);
                        susceptible.add(Element.STEEL);
                        susceptibleTypes.put(teraType.getName(), susceptible);

                        immuneTypes.put(teraType.getName(), immune);
                        break;

                    case "ghost":
                        resisted.add(Element.POISON);
                        resisted.add(Element.BUG);
                        resistedTypes.put(teraType.getName(), resisted);

                        susceptible.add(Element.GHOST);
                        susceptible.add(Element.DARK);
                        susceptibleTypes.put(teraType.getName(), susceptible);

                        immune.add(Element.NORMAL);
                        immuneTypes.put(teraType.getName(), immune);
                        break;

                    case "dragon":
                        resisted.add(Element.FIRE);
                        resisted.add(Element.WATER);
                        resisted.add(Element.GRASS);
                        resisted.add(Element.ELECTRIC);
                        resistedTypes.put(teraType.getName(), resisted);

                        susceptible.add(Element.ICE);
                        susceptible.add(Element.DRAGON);
                        susceptible.add(Element.FAIRY);
                        susceptibleTypes.put(teraType.getName(), susceptible);

                        immuneTypes.put(teraType.getName(), immune);
                        break;

                    case "dark":
                        resisted.add(Element.GHOST);
                        resisted.add(Element.DARK);
                        resistedTypes.put(teraType.getName(), resisted);

                        susceptible.add(Element.FIGHTING);
                        susceptible.add(Element.BUG);
                        susceptible.add(Element.FAIRY);
                        susceptibleTypes.put(teraType.getName(), susceptible);

                        immuneTypes.put(teraType.getName(), immune);
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

                        susceptible.add(Element.FIRE);
                        susceptible.add(Element.FIGHTING);
                        susceptible.add(Element.GROUND);
                        susceptibleTypes.put(teraType.getName(), susceptible);

                        immune.add(Element.POISON);
                        immuneTypes.put(teraType.getName(), immune);
                        break;

                    case "fairy":
                        resisted.add(Element.FIGHTING);
                        resisted.add(Element.BUG);
                        resisted.add(Element.DARK);
                        resistedTypes.put(teraType.getName(), resisted);

                        susceptible.add(Element.POISON);
                        susceptible.add(Element.STEEL);
                        susceptibleTypes.put(teraType.getName(), susceptible);

                        immune.add(Element.DRAGON);
                        immuneTypes.put(teraType.getName(), immune);
                        break;

                }

            }

        }

    }

}
