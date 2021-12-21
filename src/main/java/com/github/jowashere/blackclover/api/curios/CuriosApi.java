package com.github.jowashere.blackclover.api.curios;

import com.github.jowashere.blackclover.api.curios.type.util.ICuriosHelper;
import com.github.jowashere.blackclover.api.curios.type.util.IIconHelper;
import com.github.jowashere.blackclover.api.curios.type.util.ISlotHelper;

public class CuriosApi
{
    public static final String MODID = "curios";

    private static IIconHelper iconHelper;
    private static ISlotHelper slotHelper;
    private static ICuriosHelper curiosHelper;

    public static IIconHelper getIconHelper() {
        return iconHelper;
    }

    public static ISlotHelper getSlotHelper() {
        return slotHelper;
    }

    public static ICuriosHelper getCuriosHelper() {
        return curiosHelper;
    }

    public static void setIconHelper(IIconHelper helper) {

        if (iconHelper == null) {
            iconHelper = helper;
        }
    }

    public static void setSlotHelper(ISlotHelper helper) {
        slotHelper = helper;
    }

    public static void setCuriosHelper(ICuriosHelper helper) {

        if (curiosHelper == null) {
            curiosHelper = helper;
        }
    }
}
