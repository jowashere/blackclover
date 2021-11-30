package com.github.jowashere.blackclover.mixin;

import com.github.jowashere.blackclover.util.helpers.EntityAttributeHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(GameRenderer.class)
public class GameRendererMixin {
    @Shadow
    @Final
    private Minecraft minecraft;

    @ModifyConstant(method = "pick(F)V", require = 1, allow = 1, constant = @Constant(doubleValue = 6.0))
    private double getActualAttackRangeInCreative(final double attackRange)
    {
        if (this.minecraft.player != null)
            return EntityAttributeHelper.getSquaredAttackRangeDistance(this.minecraft.player, attackRange);
        return attackRange;
    }

    @ModifyVariable(method = "pick(F)V", at = @At("STORE"), ordinal = 1)
    private double getActualAttackRangeInSurvival0(double attackRange)
    {
        if (this.minecraft.player != null)
            return EntityAttributeHelper.getSquaredAttackRangeDistance(this.minecraft.player, attackRange);
        return attackRange;
    }

    @ModifyConstant(method = "pick(F)V", constant = @Constant(doubleValue = 9.0))
    private double getActualAttackRangeInSurvival1(final double attackRange)
    {
        if (this.minecraft.player != null)
            return EntityAttributeHelper.getSquaredAttackRangeDistance(this.minecraft.player, attackRange);
        return attackRange;
    }
}
