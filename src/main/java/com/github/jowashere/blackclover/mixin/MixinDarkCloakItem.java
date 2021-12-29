package com.github.jowashere.blackclover.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Item.class)
public abstract class MixinDarkCloakItem {

    @Shadow @Final private int maxStackSize;

    @Inject(at = @At("HEAD"), method = "inventoryTick", cancellable = true)
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected, CallbackInfo ci) {

        Item item = (Item) ((Object)this);

        if(!stack.isStackable()){
            if(item instanceof SwordItem && maxStackSize == 1) {
                if(!selected || !entity.getPersistentData().getBoolean("blackclover_dark_cloaked_blade")){
                    if(item instanceof SwordItem && maxStackSize == 1) {
                        if(stack.hasTag()){
                            if (stack.getTag().contains("dark_cloak")) {
                                stack.getTag().remove("dark_cloak");
                            }
                        }
                    }
                }
            }
        }

    }

    @Inject(at = @At("HEAD"), method = "isFoil", cancellable = true)
    public void isFoil(ItemStack pStack, CallbackInfoReturnable<Boolean> cir) {
        if(!pStack.isStackable()){
            if(pStack.getItem() instanceof SwordItem && pStack.getMaxStackSize() == 1) {
                if(pStack.hasTag()){
                    cir.setReturnValue(((pStack.getTag().contains("dark_cloak")) && pStack.getItem() instanceof SwordItem ) || pStack.isEnchanted());
                    return;
                }
            }
        }
    }
}
