package com.github.jowashere.blackclover.init;

import com.github.jowashere.blackclover.Main;
import com.github.jowashere.blackclover.effects.ExperienceMultiplierEffect;
import com.github.jowashere.blackclover.effects.ManaRegenEffect;
import com.github.jowashere.blackclover.effects.ManaZoneEffect;
import com.github.jowashere.blackclover.items.misc.ManaRegenPotion;
import com.github.jowashere.blackclover.items.misc.MoguroLeafJuice;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.potion.*;
import net.minecraft.util.text.KeybindTextComponent;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;
import net.minecraftforge.common.brewing.IBrewingRecipe;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class PotionInit
{
    public static final DeferredRegister<Effect> EFFECTS = DeferredRegister.create(ForgeRegistries.POTIONS, Main.MODID);
    public static final DeferredRegister<Potion> POTIONS = DeferredRegister.create(ForgeRegistries.POTION_TYPES, Main.MODID);

    public static final RegistryObject<Effect> MANA_PRESSURE = EFFECTS.register("mana_pressure", ManaZoneEffect::new);
    public static final RegistryObject<Effect> MULTIPLIER_EFFECT = EFFECTS.register("multiplier", ExperienceMultiplierEffect::new);

    public static final RegistryObject<Effect> MANA_REGEN = EFFECTS.register("mana_regen_effect", ManaRegenEffect::new);
    public static final RegistryObject<Potion> MANA_REGEN_POTION = POTIONS.register("mana_regen_potion",
            () -> new Potion(new EffectInstance(MANA_REGEN.get(), 1200, 0)));

    public static final RegistryObject<Potion> MULTIPLIER_POTION = POTIONS.register("multiplier",
            () -> new Potion(new EffectInstance(MULTIPLIER_EFFECT.get(), 1200, 0)));

    public static void addPotionRecipes()
    {
    BrewingRecipeRegistry.addRecipe(new BetterBrewingRecipe(Potions.WATER, ItemInit.MOGURO_LEAF.get(), new ItemStack(ItemInit.MANA_REGEN_POTION.get())));
}
    private static class BetterBrewingRecipe implements IBrewingRecipe {
        private final Potion bottleInput;
        private final Item itemInput;
        private final ItemStack output;

        public BetterBrewingRecipe(Potion bottleInputIn, Item itemInputIn, ItemStack outputIn){
            this.bottleInput = bottleInputIn;
            this.itemInput = itemInputIn;
            this.output = outputIn;
        }

        // checks the item where the water bottle would go
        @Override
        public boolean isInput(ItemStack input) {
            return PotionUtils.getPotion(input).equals(this.bottleInput);
        }

        // checks the item where the nether wort would go
        @Override
        public boolean isIngredient(ItemStack ingredient) {
            return ingredient.getItem().equals(this.itemInput);
        }

        // gets the output potion. Very important to call copy because ItemStacks are mutable
        @Override
        public ItemStack getOutput(ItemStack input, ItemStack ingredient) {
            if (isInput(input) && isIngredient(ingredient)){
                return this.output.copy();
            } else {
                return ItemStack.EMPTY;
            }
        }
    }
}
