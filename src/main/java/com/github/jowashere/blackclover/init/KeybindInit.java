package com.github.jowashere.blackclover.init;

import com.github.jowashere.blackclover.Main;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;

import static org.lwjgl.glfw.GLFW.*;

public class KeybindInit {

    private static final String CATEGORY = "key.categories." + Main.MODID;
    public static KeyBinding MANA_SKIN;
    public static KeyBinding REINFORCEMENT;
    public static KeyBinding SPELL_MODE;
    public static KeyBinding MAGIC_MENU;
    public static KeyBinding KEYBIND1;
    public static KeyBinding KEYBIND2;
    public static KeyBinding KEYBIND3;
    public static KeyBinding KEYBIND4;
    public static KeyBinding KEYBIND5;
    public static KeyBinding KEYBIND6;
    public static KeyBinding KEYBIND7;
    public static KeyBinding KEYBIND8;
    public static KeyBinding KEYBIND9;


    public static void register()
    {
        MANA_SKIN = new KeyBinding("key." + Main.MODID + ".manaskin", GLFW_KEY_X, CATEGORY);
        REINFORCEMENT = new KeyBinding("key." + Main.MODID + ".reinforcement", GLFW_KEY_C, CATEGORY);
        SPELL_MODE = new KeyBinding("key." + Main.MODID + ".spellmode", GLFW_KEY_G, CATEGORY);
        MAGIC_MENU = new KeyBinding("key." + Main.MODID + ".magicmenu", GLFW_KEY_M, CATEGORY);

        KEYBIND1 = new KeyBinding("key." + Main.MODID + ".keybind1", GLFW_KEY_1, CATEGORY);
        ClientRegistry.registerKeyBinding(KEYBIND1);
        KEYBIND2 = new KeyBinding("key." + Main.MODID + ".keybind2", GLFW_KEY_2, CATEGORY);
        ClientRegistry.registerKeyBinding(KEYBIND2);
        KEYBIND3 = new KeyBinding("key." + Main.MODID + ".keybind3", GLFW_KEY_3, CATEGORY);
        ClientRegistry.registerKeyBinding(KEYBIND3);
        KEYBIND4 = new KeyBinding("key." + Main.MODID + ".keybind4", GLFW_KEY_4, CATEGORY);
        ClientRegistry.registerKeyBinding(KEYBIND4);
        KEYBIND5 = new KeyBinding("key." + Main.MODID + ".keybind5", GLFW_KEY_5, CATEGORY);
        ClientRegistry.registerKeyBinding(KEYBIND5);
        KEYBIND6 = new KeyBinding("key." + Main.MODID + ".keybind6", GLFW_KEY_6, CATEGORY);
        ClientRegistry.registerKeyBinding(KEYBIND6);
        KEYBIND7 = new KeyBinding("key." + Main.MODID + ".keybind7", GLFW_KEY_7, CATEGORY);
        ClientRegistry.registerKeyBinding(KEYBIND7);
        KEYBIND8 = new KeyBinding("key." + Main.MODID + ".keybind8", GLFW_KEY_8, CATEGORY);
        ClientRegistry.registerKeyBinding(KEYBIND8);
        KEYBIND9 = new KeyBinding("key." + Main.MODID + ".keybind9", GLFW_KEY_9, CATEGORY);
        ClientRegistry.registerKeyBinding(KEYBIND9);

        ClientRegistry.registerKeyBinding(MANA_SKIN);
        ClientRegistry.registerKeyBinding(REINFORCEMENT);
        ClientRegistry.registerKeyBinding(SPELL_MODE);
        ClientRegistry.registerKeyBinding(MAGIC_MENU);
    }

}
