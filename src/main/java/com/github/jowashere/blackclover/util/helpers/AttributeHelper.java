package com.github.jowashere.blackclover.util.helpers;

import com.github.jowashere.blackclover.api.BCMRegistry;
import com.github.jowashere.blackclover.api.internal.BCMAttribute;

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

}
