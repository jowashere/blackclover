package com.github.jowashere.blackclover.util.helpers;

import com.github.jowashere.blackclover.api.BCMRegistry;
import com.github.jowashere.blackclover.api.internal.BCMRace;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RaceHelper {

    private static List<BCMRace> racesWithNPC = new ArrayList<>();
    private static Random rand = new Random();

    public static BCMRace getRaceFromString(String race) {
        for (BCMRace bcmRace : BCMRegistry.RACES.getValues()) {
            if (bcmRace.getString().equalsIgnoreCase(race)) {
                return bcmRace;
            }
        }
        return null;
    }

    public static List<BCMRace> getRacesWithNPC() {
        return racesWithNPC;
    }

    public static void create() {
        for (BCMRace race : BCMRegistry.RACES.getValues()) {
            if (race.hasNPC()) {
                racesWithNPC.add(race);
            }
        }
    }

}
