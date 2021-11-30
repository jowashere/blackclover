package com.github.jowashere.blackclover.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Item.class)
public abstract class MixinItem {

    @Shadow public abstract void inventoryTick(ItemStack pStack, World pLevel, Entity pEntity, int pItemSlot, boolean pIsSelected);

    @Inject(at = @At("HEAD"), method = "inventoryTick", cancellable = true)
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected, CallbackInfo ci) {

        if(!selected || !entity.getPersistentData().getBoolean("blackclover_dark_cloaked_blade")){
            if (stack.getOrCreateTag().getInt("dark_cloak") != 0) {
                stack.getOrCreateTag().putInt("dark_cloak", 0);
            }
        }
    }

    @Inject(at = @At("HEAD"), method = "onEntitySwing", cancellable = true)
    public void onEntitySwing(ItemStack pStack, CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue((pStack.getOrCreateTag().getInt("dark_cloak") > 0) || pStack.isEnchanted());
        return;
    }

    @Inject(at = @At("HEAD"), method = "isFoil", cancellable = true)
    public void isFoil(ItemStack pStack, CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue((pStack.getOrCreateTag().getInt("dark_cloak") > 0) || pStack.isEnchanted());
        return;
    }
}
