package com.github.jowashere.blackclover.events;

import java.util.ArrayList;

public class GrimoireTextures {

    public static ArrayList<String> WindGrimoires = new ArrayList<>();
    public static ArrayList<String> LightningGrimoires = new ArrayList<>();
    public static ArrayList<String> DarknessGrimoires = new ArrayList<>();
    public static ArrayList<String> AntiMagicGrimoires = new ArrayList<>();
    public static ArrayList<String> SwordGrimoires = new ArrayList<>();
    public static ArrayList<String> SlashGrimoires = new ArrayList<>();
    public static ArrayList<String> LightGrimoires = new ArrayList<>();

    public static void AddTextures() {

        WindGrimoires.add("textures/entities/layers/grimoires/yuno_grimoire.png");
        WindGrimoires.add("textures/entities/layers/grimoires/george_grimoire.png");

        LightningGrimoires.add("textures/entities/layers/grimoires/luck_grimoire.png");

        DarknessGrimoires.add("textures/entities/layers/grimoires/yami_grimoire.png");

        AntiMagicGrimoires.add("textures/entities/layers/grimoires/asta_grimoire.png");

        SwordGrimoires.add("textures/entities/layers/grimoires/licht_grimoire.png");

        LightGrimoires.add("textures/entities/layers/grimoires/patolli_grimoire.png");

        SlashGrimoires.add("textures/entities/layers/grimoires/jack_grimoire.png");
    }
}
