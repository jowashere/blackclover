package com.github.jowashere.blackclover.mixin;

import com.github.jowashere.blackclover.util.helpers.EntityAttributeHelper;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.play.IServerPlayNetHandler;
import net.minecraft.network.play.ServerPlayNetHandler;
import net.minecraft.network.play.client.CUseEntityPacket;
import net.minecraft.server.MinecraftServer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(ServerPlayNetHandler.class)
public abstract class ServerPlayNetHandlerMixin implements IServerPlayNetHandler{

    @Shadow
    public ServerPlayerEntity player;

    @Shadow
    @Final
    public MinecraftServer server;

    @ModifyConstant(
            method = "handleInteract(Lnet/minecraft/network/play/client/CUseEntityPacket;)V",
            constant = @Constant(doubleValue = 36.0)
    )
    public double getActualAttackRange(double attackRange, CUseEntityPacket packet)
    {
        if (packet.getAction() == CUseEntityPacket.Action.ATTACK)
            return EntityAttributeHelper.getSquaredAttackRangeDistance(this.player, attackRange);

        return EntityAttributeHelper.getSquaredAttackRangeDistance(this.player, attackRange);
    }

}
