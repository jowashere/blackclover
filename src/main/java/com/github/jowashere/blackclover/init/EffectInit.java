package com.github.jowashere.blackclover.init;

import com.github.jowashere.blackclover.Main;
import com.github.jowashere.blackclover.effects.MagicLevelEffect;
import net.minecraft.potion.Effect;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class EffectInit {

    public static final DeferredRegister<Effect> EFFECT = DeferredRegister.create(ForgeRegistries.POTIONS, Main.MODID);

    public static final RegistryObject<Effect> MAGIC_LEVEL = EFFECT.register("magic_lvl", MagicLevelEffect::new);

}
