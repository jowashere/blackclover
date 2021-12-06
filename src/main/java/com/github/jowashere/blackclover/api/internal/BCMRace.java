package com.github.jowashere.blackclover.api.internal;

import com.github.jowashere.blackclover.Main;
import net.minecraft.util.ResourceLocation;

public class BCMRace {

    private static final ResourceLocation SYMBOL_LOCATION = new ResourceLocation(Main.MODID, "textures/gui/symbols.png");

    private final String race;
    private final int weight;

    private String message = "";
    private float startingMana = 300F;
    private int u;
    private int v;
    private ResourceLocation resourceLocation;
    private boolean hasSymbol;
    private boolean hasNPC;

    private float manaMultiplier = 1;

    private boolean hasDedicatedTab;


    public BCMRace(String race, int weight, boolean hasNPC) {
        this.race = race;
        this.weight = weight;
        this.hasNPC = hasNPC;
    }

    public BCMRace(String clan, int weight, boolean hasNPC, int u, int v) {
        this(clan, weight, hasNPC);
        this.u = u;
        this.v = v;
        this.resourceLocation = SYMBOL_LOCATION;
        this.hasSymbol = true;
    }

    public BCMRace(String clan, int weight, boolean hasNPC, int u, int v, ResourceLocation resourceLocation) {
        this(clan, weight, hasNPC);
        this.u = u;
        this.v = v;
        this.resourceLocation = resourceLocation;
        this.hasSymbol = true;
    }

    public int getWeight() {
        return weight;
    }

    public String getString() {
        return this.race;
    }

    public BCMRace setRaceMessage(String message) {
        this.message = message;
        return this;
    }

    public BCMRace setStartingMana(float amount) {
        this.startingMana = amount;
        return this;
    }


    public String getRaceMessage() {
        return this.message;
    }

    public float getStartingMana() {
        return this.startingMana;
    }

    public ResourceLocation getResourceLocation() {
        return resourceLocation;
    }

    public int getU() {
        return u;
    }

    public int getV() {
        return v;
    }

    public boolean hasSymbol() {
        return hasSymbol;
    }

    public boolean hasNPC() {
        return hasNPC;
    }

    public BCMRace setManaMultiplier(float multiplier) {
        this.manaMultiplier = multiplier;
        return this;
    }

    public float getManaMultiplier() {
        return this.manaMultiplier;
    }

}
