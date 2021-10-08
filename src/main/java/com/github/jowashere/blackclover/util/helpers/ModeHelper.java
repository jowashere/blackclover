package com.github.jowashere.blackclover.util.helpers;

import com.github.jowashere.blackclover.api.BCMRegistry;
import com.github.jowashere.blackclover.api.internal.BCMMode;

public class ModeHelper {

    public static BCMMode getModeFromString(String mode) {
        for (BCMMode bcmMode : BCMRegistry.MODES.getValues()) {
            if (bcmMode.getName().equalsIgnoreCase(mode)) {
                return bcmMode;
            }
        }
        return null;
    }

}
