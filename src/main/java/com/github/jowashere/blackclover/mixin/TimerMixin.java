package com.github.jowashere.blackclover.mixin;

import net.minecraft.util.Timer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Timer.class)
public interface TimerMixin {

    @Accessor("msPerTick")
    @Final
    public float getMsPerTick();

}
