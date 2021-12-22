package com.github.jowashere.blackclover.util.helpers;

import com.github.jowashere.blackclover.api.BCMRegistry;
import com.github.jowashere.blackclover.api.internal.BCMAttribute;
import com.github.jowashere.blackclover.init.AttributeInit;

import java.util.ArrayList;
import java.util.Random;

public class AttributeHelper {

    private static Random rand = new Random();

    public static BCMAttribute getAttributeFromString(String attribute) {
        for (BCMAttribute bcmAttribute : BCMRegistry.ATTRIBUTES.getValues()) {
            if (bcmAttribute.getString().equalsIgnoreCase(attribute)) {
                return bcmAttribute;
            }
        }
        return AttributeInit.NULL;
    }

    public static BCMAttribute getRandomAttribute(boolean onlyNPC) {

        ArrayList<BCMAttribute> attributes = new ArrayList<>();
        for (BCMAttribute attribute : BCMRegistry.ATTRIBUTES.getValues()) {

            if(!onlyNPC){
                if (attribute != AttributeInit.NULL) {
                    attributes.add(attribute);
                }
            }else {
                if (attribute != AttributeInit.NULL && attribute.hasNPC()) {
                    attributes.add(attribute);
                }
            }
        }

        double totalWeight = 0.0D;
        for (BCMAttribute attribute : attributes) {
            totalWeight += attribute.getWeight();
        }

        int randomIndex = -1;
        double random = Math.random() * totalWeight;
        for (int i = 0; i < attributes.size(); ++i) {
            random -= attributes.get(i).getWeight();
            if (random <= 0.0D) {
                randomIndex = i;
                break;
            }
        }

        BCMAttribute randomAttribute = attributes.get(randomIndex);

        return randomAttribute;
    }


}
