package com.github.jowashere.blackclover.init;

import com.github.jowashere.blackclover.Main;
import com.github.jowashere.blackclover.effects.ExperienceMultiplierEffect;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Potion;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class PotionInit
{
    public static final DeferredRegister<Effect> EFFECTS = DeferredRegister.create(ForgeRegistries.POTIONS, Main.MODID);
    public static final DeferredRegister<Potion> POTIONS = DeferredRegister.create(ForgeRegistries.POTION_TYPES, Main.MODID);

    public static final RegistryObject<Effect> MULTIPLIER_EFFECT = EFFECTS.register("multiplier", ExperienceMultiplierEffect::new);

    public static final RegistryObject<Potion> MULTIPLIER_POTION = POTIONS.register("multiplier",
            () -> new Potion(new EffectInstance(MULTIPLIER_EFFECT.get(), 1200, 0)));
}
