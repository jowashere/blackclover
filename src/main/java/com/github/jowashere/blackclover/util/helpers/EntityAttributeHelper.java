package com.github.jowashere.blackclover.util.helpers;

import com.github.jowashere.blackclover.init.ModAttributes;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;

public class EntityAttributeHelper {
    public static double getAttackRangeDistance(final LivingEntity entity, final double baseReachDistance)
    {
        final ModifiableAttributeInstance reachDistance = entity.getAttribute(ModAttributes.ATTACK_RANGE.get());
        return (reachDistance != null) ? (baseReachDistance + reachDistance.getValue()) : baseReachDistance;
    }

    public static double getSquaredAttackRangeDistance(final LivingEntity entity, final double sqBaseReachDistance)
    {
        final double reachDistance = getAttackRangeDistance(entity, Math.sqrt(sqBaseReachDistance));
        return reachDistance * reachDistance;
    }
}
