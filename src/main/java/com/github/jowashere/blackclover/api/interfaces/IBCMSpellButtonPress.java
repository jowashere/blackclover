package com.github.jowashere.blackclover.api.interfaces;

import com.github.jowashere.blackclover.capabilities.player.IPlayerHandler;
import com.github.jowashere.blackclover.capabilities.player.PlayerCapability;
import com.github.jowashere.blackclover.capabilities.player.PlayerProvider;
import com.github.jowashere.blackclover.client.gui.widgets.spells.GuiButtonSpell;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraftforge.common.util.LazyOptional;

public interface IBCMSpellButtonPress extends Button.IPressable {

    void onPress(GuiButtonSpell buttonSpell, IPlayerHandler playerCapability);

    @Override
    default void onPress(Button p_onPress_1_) {
        if (p_onPress_1_ instanceof GuiButtonSpell) {
            ClientPlayerEntity player = Minecraft.getInstance().player;
            LazyOptional<IPlayerHandler> player_cap = player.getCapability(PlayerProvider.CAPABILITY_PLAYER, null);
            IPlayerHandler playerc = player_cap.orElse(new PlayerCapability());

            onPress((GuiButtonSpell) p_onPress_1_, playerc);
        }
    }

}
