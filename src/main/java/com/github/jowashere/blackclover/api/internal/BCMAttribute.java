package com.github.jowashere.blackclover.api.internal;

import com.github.jowashere.blackclover.Main;
import com.github.jowashere.blackclover.events.spells.AbstractAddSpells;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;

public class BCMAttribute {

    private static final ResourceLocation SYMBOL_LOCATION = new ResourceLocation(Main.MODID, "textures/gui/symbols.png");

    private final String attributeName;
    private final int weight;

    private AbstractAddSpells spellAdder;

    private ArrayList<String> grimoireTextures;

    private String message = "";
    private String owner = "";
    private int colour = 0;
    private int u;
    private int v;

    public BCMAttribute(String attributeName, int weight, boolean hasNPC) {
        this.attributeName = attributeName;
        this.weight = weight;
    }

    public BCMAttribute(String attributeName, int weight, boolean hasNPC, int u, int v) {
        this(attributeName, weight, hasNPC);
        this.u = u;
        this.v = v;
    }

    public BCMAttribute(String attributeName, int weight, boolean hasNPC, int u, int v, ResourceLocation resourceLocation) {
        this(attributeName, weight, hasNPC);
        this.u = u;
        this.v = v;
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

    public BCMAttribute setAttributeUser(String owner) {
        this.owner = owner;
        return this;
    }

    public BCMAttribute setSpellAdder(AbstractAddSpells spellAdder){
        this.spellAdder = spellAdder;
        return this;
    }

    public AbstractAddSpells getSpellAdder(){
        if(spellAdder instanceof AbstractAddSpells){
            return this.spellAdder;
        }
        return null;
    }

    public String getAttributeUser(){
        return this.owner;
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
