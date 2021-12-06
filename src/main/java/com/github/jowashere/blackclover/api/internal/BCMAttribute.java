package com.github.jowashere.blackclover.api.internal;

import com.github.jowashere.blackclover.Main;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;

public class BCMAttribute {

    private static final ResourceLocation SYMBOL_LOCATION = new ResourceLocation(Main.MODID, "textures/gui/symbols.png");

    private final String attributeName;
    private final int weight;
    private ArrayList<String> grimoireTextures;

    private BCMSpell.Type spellType;

    private String message = "";
    private int colour = 0;
    private int u;
    private int v;

    public BCMAttribute(String attributeName, int weight, boolean hasNPC, BCMSpell.Type spellType) {
        this.attributeName = attributeName;
        this.weight = weight;
    }

    public BCMAttribute(String attributeName, int weight, boolean hasNPC, int u, int v, BCMSpell.Type spellType) {
        this(attributeName, weight, hasNPC, spellType);
        this.u = u;
        this.v = v;
        this.spellType = spellType;
    }

    public BCMAttribute(String attributeName, int weight, boolean hasNPC, int u, int v, BCMSpell.Type spellType, ResourceLocation resourceLocation) {
        this(attributeName, weight, hasNPC, spellType);
        this.u = u;
        this.v = v;
        this.spellType = spellType;
    }

    public int getWeight() {
        return weight;
    }

    public String getString() {
        return this.attributeName;
    }

    public BCMAttribute setAttributeMessage(String message) {
        this.message = message;
        return this;
    }

    public String getAttributeMessage() {
        return this.message;
    }

    public BCMSpell.Type getSpellType(){
        return this.spellType;
    }

    public int getU(){
        return this.u;
    }

    public int getV(){
        return this.v;
    }

    public BCMAttribute setGrimoireTextures(ArrayList<String> list){
        this.grimoireTextures = list;
        return this;
    }

    public ArrayList<String> getGrimoireTextures(){
        return this.grimoireTextures;
    }

    public BCMAttribute setAttributeColour(int colour) {
        this.colour = colour;
        return this;
    }

    public int getAttributeColour() {
        return this.colour;
    }

}
