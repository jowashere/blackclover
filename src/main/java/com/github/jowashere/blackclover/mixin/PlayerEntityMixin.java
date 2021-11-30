package com.github.jowashere.blackclover.mixin;

import com.github.jowashere.blackclover.util.helpers.AttributeHelper;
import com.github.jowashere.blackclover.util.helpers.EntityAttributeHelper;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity {

    public PlayerEntityMixin(final EntityType<? extends LivingEntity> type, final World world)
    {
        super(type, world);
    }

    @ModifyConstant(method = "attack(Lnet/minecraft/entity/Entity;)V", constant = @Constant(doubleValue = 9.0))
    private double getActualAttackRange(final double attackRange)
    {
        return EntityAttributeHelper.getSquaredAttackRangeDistance(this, attackRange);
    }

}
