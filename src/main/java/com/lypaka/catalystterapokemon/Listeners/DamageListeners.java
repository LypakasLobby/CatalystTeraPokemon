package com.lypaka.catalystterapokemon.Listeners;

import com.lypaka.catalystterapokemon.Helpers.DamageHelpers;
import com.lypaka.catalystterapokemon.Helpers.NBTHelpers;
import com.pixelmonmod.pixelmon.api.events.battles.AttackEvent;
import com.pixelmonmod.pixelmon.api.pokemon.Element;
import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.pixelmonmod.pixelmon.battles.attacks.Attack;
import com.pixelmonmod.pixelmon.battles.attacks.Effectiveness;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class DamageListeners {

    @SubscribeEvent
    public void onTypeEffectiveness (AttackEvent.TypeEffectiveness event) {

        Pokemon target = event.target.pokemon;
        Attack attack = event.user.attack;

        if (NBTHelpers.isTerastallized(target)) {

            String teraType = NBTHelpers.getTeraType(target);
            String heldItem = target.getHeldItem().getItem().getRegistryName().toString();
            if (DamageHelpers.isImmuneToAttack(teraType, heldItem, attack)) {

                event.setEffectiveness(Effectiveness.None);

            } else if (DamageHelpers.dealsSuperEffectiveDamage(teraType, attack)) {

                event.setEffectiveness(Effectiveness.Super);

            } else if (DamageHelpers.dealsNotVeryEffectiveDamage(teraType, attack)) {

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

            Element attackType = event.user.attack.getActualType();
            if (attackType.getName().equalsIgnoreCase(NBTHelpers.getTeraType(pokemon))) {

                event.setStabbing(true);
                event.stabMultiplier = 2.0;

            }

        }

    }

}
