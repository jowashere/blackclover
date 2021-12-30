package com.github.jowashere.blackclover.init;

import com.github.jowashere.blackclover.Main;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.RangedAttribute;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class ModAttributes {

    public static final DeferredRegister<Attribute> ATTRIBUTES = DeferredRegister.create(ForgeRegistries.ATTRIBUTES, Main.MODID);
    public List<Supplier<Attribute>> attributes = new ArrayList<>();

    public static final RegistryObject<Attribute> FALL_RESISTANCE = ATTRIBUTES.register("fall_resistance" , () -> new RangedAttribute("fallResistance", 0D, -256D, 256D));
    public static final RegistryObject<Attribute> JUMP_HEIGHT = ATTRIBUTES.register("jump_height", () -> (new RangedAttribute("jumpHeight", 1D, -256D, 256D)).setSyncable(true));
    public static final RegistryObject<Attribute> REGEN_RATE = ATTRIBUTES.register("regen_rate", () -> (new RangedAttribute("regenRate", 1D, 0D, 32D)).setSyncable(true));
    public static final RegistryObject<Attribute> STEP_HEIGHT = ATTRIBUTES.register("step_height", () -> (new RangedAttribute("stepHeight", 0.5D, 0D, 20D)).setSyncable(true));
    public static final RegistryObject<Attribute> DAMAGE_REDUCTION = ATTRIBUTES.register("damage_reduction", () -> (new RangedAttribute("damageReduction", 0.0D, -1D, 1D)).setSyncable(true));
    public static final RegistryObject<Attribute> SPECIAL_DAMAGE_REDUCTION = ATTRIBUTES.register("special_damage_reduction", () -> (new RangedAttribute("specialDamageReduction", 0.0D, -1D, 1D)).setSyncable(true));
    public static final RegistryObject<Attribute> ATTACK_RANGE = ATTRIBUTES.register("attack_range", () -> (new RangedAttribute("attackRange", 0.0D, -1024D, 1024D)).setSyncable(true));
    public static final RegistryObject<Attribute> MULTIPLIER = ATTRIBUTES.register("multiplier_experience", () -> new RangedAttribute("multiplier", 1.0D, 1.0D, 5.0D).setSyncable(true));



}
