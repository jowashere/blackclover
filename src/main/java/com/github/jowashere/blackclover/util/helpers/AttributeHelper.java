package com.github.jowashere.blackclover.util.helpers;

import com.github.jowashere.blackclover.api.BCMRegistry;
import com.github.jowashere.blackclover.api.internal.BCMAttribute;
import com.github.jowashere.blackclover.client.gui.player.spells.AbstractSpellScreen;
import com.github.jowashere.blackclover.client.gui.player.spells.LightningSpellsScreen;
import com.github.jowashere.blackclover.client.gui.player.spells.WindSpellsScreen;
import com.github.jowashere.blackclover.init.AttributeInit;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Random;

public class AttributeHelper {

    private static Random rand = new Random();

    public static BCMAttribute getAttributeFromString(String attribute) {
        for (BCMAttribute bcmAttribute : BCMRegistry.ATTRIBUTES.getValues()) {
            if (bcmAttribute.getString().equalsIgnoreCase(attribute)) {
                return bcmAttribute;
            }
        }
        return null;
    }

    @OnlyIn(Dist.CLIENT)
    public static AbstractSpellScreen getAttributeSpellScreen(BCMAttribute attribute) {
        if(attribute == AttributeInit.WIND){
            return new WindSpellsScreen();
        }else if(attribute == AttributeInit.LIGHTNING){
            return new LightningSpellsScreen();
        }
        return null;
    }

}
