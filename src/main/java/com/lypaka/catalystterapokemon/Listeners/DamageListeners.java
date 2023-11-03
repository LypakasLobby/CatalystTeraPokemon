package com.lypaka.catalystterapokemon.Listeners;

import com.lypaka.catalystterapokemon.Helpers.DamageHelpers;
import com.lypaka.catalystterapokemon.Helpers.NBTHelpers;
import com.pixelmonmod.pixelmon.api.events.battles.AttackEvent;
import com.pixelmonmod.pixelmon.api.pokemon.Element;
import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.pixelmonmod.pixelmon.battles.attacks.Attack;
import com.pixelmonmod.pixelmon.battles.attacks.Effectiveness;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.List;

public class DamageListeners {

    @SubscribeEvent
    public void onTypeEffectiveness (AttackEvent.TypeEffectiveness event) {

        Pokemon target = event.target.pokemon;
        Pokemon user = event.user.pokemon;
        Attack attack = event.user.attack;

        if (NBTHelpers.isTerastallized(target)) {

            String teraType = NBTHelpers.getTeraType(target);
            String heldItem = target.getHeldItem().getItem().getRegistryName().toString();
            if (DamageHelpers.isImmuneToAttack(teraType, heldItem, attack)) {

                event.setEffectiveness(Effectiveness.None);

            } else if (DamageHelpers.dealsSuperEffectiveDamage(teraType, attack, target, user)) {

                event.setEffectiveness(Effectiveness.Super);

            } else if (DamageHelpers.dealsNotVeryEffectiveDamage(teraType, attack, target, user)) {

                event.setEffectiveness(Effectiveness.Not);

            } else {

                event.setEffectiveness(Effectiveness.Normal);

            }

        }

    }

    @SubscribeEvent
    public void onSTAB (AttackEvent.Stab event) {

        Pokemon pokemon = event.user.pokemon;
        if (NBTHelpers.isTerastallized(pokemon)) {

            String teraType = NBTHelpers.getTeraType(pokemon);
            List<Element> originalTypes = pokemon.getForm().getTypes();
            Element attackType = event.user.attack.getActualType();
            if (attackType.getName().equalsIgnoreCase(teraType)) {

                double stab = 1.5;
                event.setStabbing(true);
                for (Element ele : originalTypes) {

                    if (ele.getName().equalsIgnoreCase(teraType)) {

                        stab = 2.0;
                        break;

                    }

                }
                if (pokemon.getAbility().getName().equalsIgnoreCase("Adaptability")) {

                    stab = 2.25;

                }
                event.stabMultiplier = stab;

            }

        }

    }

}
