package com.github.jowashere.blackclover.mixin;

import com.github.jowashere.blackclover.capabilities.player.IPlayerHandler;
import com.github.jowashere.blackclover.capabilities.player.PlayerCapability;
import com.github.jowashere.blackclover.capabilities.player.PlayerProvider;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraftforge.common.util.LazyOptional;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public class MixinMinecraft {

    @Inject(at = @At("HEAD"), method = "handleKeybinds", cancellable = true)
    protected void handleKeybinds(CallbackInfo cir) {

        Minecraft minecraft = (Minecraft) ((Object)this);

        ClientPlayerEntity player = minecraft.player;
        LazyOptional<IPlayerHandler> player_cap = player.getCapability(PlayerProvider.CAPABILITY_PLAYER, null);
        IPlayerHandler playerc = player_cap.orElse(new PlayerCapability());

        if(playerc.returnSpellModeToggle()) {
            for(int i = 0; i < 9; ++i) {
                if (minecraft.options.keyHotbarSlots[i].consumeClick()) {
                    if (!minecraft.player.isSpectator()) {
                        return;
                    }
                }
            }
        }
    }
}
