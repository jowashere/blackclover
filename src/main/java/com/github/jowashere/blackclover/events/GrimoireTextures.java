package com.github.jowashere.blackclover.events;

import com.github.jowashere.blackclover.api.BCMRegistry;
import com.github.jowashere.blackclover.api.internal.BCMRace;

import java.util.ArrayList;

public class GrimoireTextures {

    public static ArrayList<String> WindGrimoires = new ArrayList<>();
    public static ArrayList<String> LightningGrimoires = new ArrayList<>();

    public static void AddTextures() {

        WindGrimoires.add("textures/entities/layers/grimoires/yuno_grimoire.png");
        LightningGrimoires.add("textures/entities/layers/grimoires/luck_grimoire.png");

    }
}
