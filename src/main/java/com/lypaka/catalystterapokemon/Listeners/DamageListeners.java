package com.lypaka.catalystterapokemon.Listeners;

import com.lypaka.catalystterapokemon.API.Battles.TeraAttackEffectivenessEvent;
import com.lypaka.catalystterapokemon.API.Battles.TeraAttackSTABEvent;
import com.lypaka.catalystterapokemon.Helpers.DamageHelpers;
import com.lypaka.catalystterapokemon.Helpers.NBTHelpers;
import com.pixelmonmod.pixelmon.api.events.battles.AttackEvent;
import com.pixelmonmod.pixelmon.api.pokemon.Element;
import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.pixelmonmod.pixelmon.battles.attacks.Attack;
import com.pixelmonmod.pixelmon.battles.attacks.Effectiveness;
import com.pixelmonmod.pixelmon.battles.controller.participants.BattleParticipant;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.List;

public class DamageListeners {

    @SubscribeEvent
    public void onTypeEffectiveness (AttackEvent.TypeEffectiveness event) {

        Pokemon target = event.target.pokemon;
        Pokemon user = event.user.pokemon;
        Attack attack = event.user.attack;
        BattleParticipant userOwner = null;
        BattleParticipant victimOwner = null;
        List<BattleParticipant> participants = event.getBattleController().participants;
        for (BattleParticipant bp : participants) {

            if (bp.getTeamPokemon().contains(event.target)) victimOwner = bp;
            if (bp.getTeamPokemon().contains(event.user)) userOwner = bp;

            if (userOwner != null && victimOwner != null) break;

        }

        if (NBTHelpers.isTerastallized(target)) {

            String teraType = NBTHelpers.getTeraType(target);
            String heldItem = target.getHeldItem().getItem().getRegistryName().toString();
            if (DamageHelpers.isImmuneToAttack(teraType, heldItem, attack)) {

                TeraAttackEffectivenessEvent teraEvent = new TeraAttackEffectivenessEvent(userOwner, victimOwner, user, target, Effectiveness.None);
                MinecraftForge.EVENT_BUS.post(teraEvent);
                if (!teraEvent.isCanceled()) {

                    event.setEffectiveness(teraEvent.getEffectiveness());

                }

            } else if (DamageHelpers.dealsSuperEffectiveDamage(teraType, attack, target, user)) {

                TeraAttackEffectivenessEvent teraEvent = new TeraAttackEffectivenessEvent(userOwner, victimOwner, user, target, Effectiveness.Super);
                MinecraftForge.EVENT_BUS.post(teraEvent);
                if (!teraEvent.isCanceled()) {

                    event.setEffectiveness(teraEvent.getEffectiveness());

                }

            } else if (DamageHelpers.dealsNotVeryEffectiveDamage(teraType, attack, target, user)) {

                TeraAttackEffectivenessEvent teraEvent = new TeraAttackEffectivenessEvent(userOwner, victimOwner, user, target, Effectiveness.Not);
                MinecraftForge.EVENT_BUS.post(teraEvent);
                if (!teraEvent.isCanceled()) {

                    event.setEffectiveness(teraEvent.getEffectiveness());

                }

            } else {

                TeraAttackEffectivenessEvent teraEvent = new TeraAttackEffectivenessEvent(userOwner, victimOwner, user, target, Effectiveness.Normal);
                MinecraftForge.EVENT_BUS.post(teraEvent);
                if (!teraEvent.isCanceled()) {

                    event.setEffectiveness(teraEvent.getEffectiveness());

                }

            }

        }

    }

    @SubscribeEvent
    public void onSTAB (AttackEvent.Stab event) {

        Pokemon target = event.target.pokemon;
        Pokemon user = event.user.pokemon;
        BattleParticipant userOwner = null;
        BattleParticipant victimOwner = null;
        List<BattleParticipant> participants = event.getBattleController().participants;
        for (BattleParticipant bp : participants) {

            if (bp.getTeamPokemon().contains(event.target)) victimOwner = bp;
            if (bp.getTeamPokemon().contains(event.user)) userOwner = bp;

            if (userOwner != null && victimOwner != null) break;

        }
        if (NBTHelpers.isTerastallized(user)) {

            TeraAttackSTABEvent stabEvent = new TeraAttackSTABEvent(userOwner, victimOwner, user, target);
            MinecraftForge.EVENT_BUS.post(stabEvent);
            if (!stabEvent.isCanceled()) {

                String teraType = NBTHelpers.getTeraType(user);
                List<Element> originalTypes = user.getForm().getTypes();
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
                    if (user.getAbility().getName().equalsIgnoreCase("Adaptability")) {

                        stab = 2.25;

                    }
                    event.stabMultiplier = stab;

                }

            }

        }

    }

}
