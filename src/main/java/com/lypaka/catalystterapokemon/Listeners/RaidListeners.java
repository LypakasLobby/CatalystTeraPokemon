package com.lypaka.catalystterapokemon.Listeners;

import com.lypaka.catalystterapokemon.API.Raids.EndTeraRaidEvent;
import com.lypaka.catalystterapokemon.API.Raids.StartTeraRaidEvent;
import com.lypaka.catalystterapokemon.CatalystTeraPokemon;
import com.lypaka.catalystterapokemon.Helpers.NBTHelpers;
import com.lypaka.catalystterapokemon.Raids.RaidRegistry;
import com.lypaka.catalystterapokemon.Raids.TeraRaid;
import com.lypaka.catalystterapokemon.Raids.TeraRaidPokemon;
import com.lypaka.lypakautils.WorldStuff.WorldMap;
import com.pixelmonmod.pixelmon.api.events.battles.BattleTickEvent;
import com.pixelmonmod.pixelmon.api.events.raids.EndRaidEvent;
import com.pixelmonmod.pixelmon.api.events.raids.RandomizeRaidEvent;
import com.pixelmonmod.pixelmon.api.events.raids.StartRaidEvent;
import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.pixelmonmod.pixelmon.api.pokemon.PokemonBuilder;
import com.pixelmonmod.pixelmon.api.util.helpers.RandomHelper;
import com.pixelmonmod.pixelmon.battles.controller.participants.BattleParticipant;
import com.pixelmonmod.pixelmon.battles.controller.participants.PixelmonWrapper;
import com.pixelmonmod.pixelmon.battles.controller.participants.RaidPixelmonParticipant;
import com.pixelmonmod.pixelmon.battles.raids.RaidData;
import com.pixelmonmod.pixelmon.entities.DenEntity;
import net.minecraft.world.storage.ServerWorldInfo;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.*;

public class RaidListeners {

    public static Map<UUID, TeraRaid> raidMap = new HashMap<>();
    public static Map<UUID, TeraRaidPokemon> raidPokemonMap = new HashMap<>();
    public static Map<UUID, Pokemon> pokemonMap = new HashMap<>();
    public static Map<RaidPixelmonParticipant, TeraRaidPokemon> pokemonToPokemonMap = new HashMap<>();

    @SubscribeEvent
    public void onRaidRandomize (RandomizeRaidEvent.ChooseSpecies event) {

        DenEntity den = event.den;
        String worldName = WorldMap.getWorldName(den.world);
        String location = worldName + "," + den.getPosition().getX() + "," + den.getPosition().getY() + "," + den.getPosition().getZ();
        List<TeraRaid> possibleRaids = new ArrayList<>();
        for (Map.Entry<String, TeraRaid> entry : RaidRegistry.raidMap.entrySet()) {

            TeraRaid raid = entry.getValue();
            List<String> denLocations = raid.getDenLocations();
            if (denLocations.contains(location)) {

                if (!possibleRaids.contains(raid)) {

                    possibleRaids.add(raid);

                }

            }

        }

        if (possibleRaids.size() == 0) return;

        TeraRaid selectedRaid = RandomHelper.getRandomElementFromList(possibleRaids);
        List<TeraRaidPokemon> pokemonList = selectedRaid.getRaidPokemon();
        Map<Double, UUID> map1 = new HashMap<>();
        Map<UUID, TeraRaidPokemon> map2 = new HashMap<>();
        for (TeraRaidPokemon p : pokemonList) {

            UUID uuid = UUID.randomUUID();
            map1.put(p.getChance(), uuid);
            map2.put(uuid, p);

        }

        List<Double> chances = new ArrayList<>(map1.keySet());
        Collections.sort(chances);

        TeraRaidPokemon selectedPokemon = null;
        for (int i = 0; i < chances.size(); i++) {

            double c = chances.get(i);
            if (RandomHelper.getRandomChance(c)) {

                UUID uuid = map1.get(c);
                selectedPokemon = map2.get(uuid);
                break;

            }

        }

        if (selectedPokemon == null) return;

        String species = selectedPokemon.getSpecies();
        String form = selectedPokemon.getForm();
        String palette = selectedPokemon.getPalette();

        Pokemon pokemon = PokemonBuilder.builder().species(species).build();
        if (!form.equalsIgnoreCase("default")) {

            pokemon.setForm(form);

        }
        if (!palette.equalsIgnoreCase("default")) {

            pokemon.setPalette(palette);

        }

        event.setRaid(new RaidData(den.getEntityId(), selectedPokemon.getStarLevel(), pokemon.getSpecies(), pokemon.getForm()));
        raidMap.put(den.getUniqueID(), selectedRaid);
        raidPokemonMap.put(den.getUniqueID(), selectedPokemon);
        pokemonMap.put(den.getUniqueID(), pokemon);

    }

    @SubscribeEvent // this is absolutely necessary for the Pokemon to be Tera'd
    public void onRaidStart (StartRaidEvent event) {

        DenEntity den = event.getDen();
        if (raidPokemonMap.containsKey(den.getUniqueID())) {

            TeraRaid raid = raidMap.get(den.getUniqueID());
            List<BattleParticipant> participants = Arrays.asList(event.getAllyParticipants());
            TeraRaidPokemon raidPokemon = raidPokemonMap.get(den.getUniqueID());
            Pokemon pokemon = pokemonMap.get(den.getUniqueID());

            StartTeraRaidEvent raidEvent = new StartTeraRaidEvent(raid, raidPokemon, participants, pokemon);
            MinecraftForge.EVENT_BUS.post(raidEvent);
            if (raidEvent.isCanceled()) return;

            raidPokemon = raidEvent.getRaidPokemon();
            pokemon = raidEvent.getPokemon();
            Map<String, Double> teraTypes = raidPokemon.getTeraTypeChances();
            String selectedType = null;

            if (teraTypes.size() == 1) {

                for (Map.Entry<String, Double> entry : teraTypes.entrySet()) {

                    selectedType = entry.getKey();
                    break;

                }

            } else {

                double sum = teraTypes.values().stream().mapToDouble(c -> c).sum();
                double rng = sum * RandomHelper.getRandom().nextDouble();

                for (Map.Entry<String, Double> entry : teraTypes.entrySet()) {

                    if (Double.compare(rng, entry.getValue()) <= 0) {

                        selectedType = entry.getKey();
                        break;

                    } else {

                        rng -= entry.getValue();

                    }

                }

            }

            if (selectedType == null) {

                CatalystTeraPokemon.logger.error("Could not get Tera Type for TeraRaidPokemon: " + raidPokemon.getSpecies());
                return;

            }

            NBTHelpers.setTeraType(pokemon, selectedType, true);
            NBTHelpers.setTerastallized(pokemon, true);
            pokemonToPokemonMap.put(event.getRaidParticipant(), raidPokemon);

        }

    }

    @SubscribeEvent
    public void onBattleTick (BattleTickEvent event) {

        if (event.getBattleController().isRaid()) {

            for (BattleParticipant bp : event.getBattleController().participants) {

                if (bp instanceof RaidPixelmonParticipant) {

                    RaidPixelmonParticipant rpp = (RaidPixelmonParticipant) bp;
                    if (pokemonToPokemonMap.containsKey(rpp)) {

                        TeraRaidPokemon raidPokemon = pokemonToPokemonMap.get(rpp);
                        rpp.getGovernor().getSettings().moveset.removeIf(a -> {

                            try {

                                if (a != null) {

                                    return a.isMax;

                                }

                            } catch (NullPointerException er) {

                                return true;

                            }


                            return false;

                        });
                        PixelmonWrapper wrapper = rpp.getWrapper();
                        Pokemon pokemon = wrapper.pokemon;
                        if (NBTHelpers.isTerastallized(pokemon)) {

                            if (wrapper.isDynamax > 0) {

                                wrapper.isDynamax = 0;
                                wrapper.dynamaxTurns = 0;
                                wrapper.dynamaxAnimationTicks = 0;
                                wrapper.entity.setDynamaxScale(0);
                                wrapper.dynamax(true, wrapper.getHealthPercent());
                                wrapper.entity.setPixelmonScale(raidPokemon.getScale());
                                event.getBattleController().updateFormChange(wrapper);
                                break;

                            }

                        }

                    }

                }

            }

        }

    }

    @SubscribeEvent
    public void onRaidEnd (EndRaidEvent event) {

        UUID uuid = event.getRaid().getDenEntity(event.getRaidParticipant().getEntity().world).get().getUniqueID();
        if (raidMap.containsKey(uuid)) {

            pokemonToPokemonMap.entrySet().removeIf(e -> e.getKey() == event.getRaidParticipant());
            TeraRaid teraRaid = raidMap.get(uuid);
            raidMap.entrySet().removeIf(e -> e.getKey().toString().equalsIgnoreCase(uuid.toString()));
            TeraRaidPokemon teraPokemon = raidPokemonMap.get(uuid);
            ArrayList<BattleParticipant> participants = event.getAllyParticipants();
            raidPokemonMap.entrySet().removeIf(e -> e.getKey().toString().equalsIgnoreCase(uuid.toString()));
            pokemonMap.entrySet().removeIf(e -> e.getKey().toString().equalsIgnoreCase(uuid.toString()));

            EndTeraRaidEvent endTeraRaidEvent = new EndTeraRaidEvent(teraRaid, teraPokemon, participants);
            MinecraftForge.EVENT_BUS.post(endTeraRaidEvent);

        }

    }

}
