package com.github.jowashere.blackclover.client.handler;

import com.github.jowashere.blackclover.client.renderer.layers.GrimoireLayer;
import com.github.jowashere.blackclover.client.renderer.layers.TGLayer;
import com.github.jowashere.blackclover.client.renderer.projectiles.spells.lightning.ThunderOrbRenderer;
import com.github.jowashere.blackclover.client.renderer.projectiles.spells.wind.WindBladeRenderer;
import com.github.jowashere.blackclover.client.renderer.projectiles.spells.wind.WindCrescentRenderer;
import com.github.jowashere.blackclover.client.renderer.summons.WindHawkRenderer;
import com.github.jowashere.blackclover.init.EntityInit;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.client.renderer.entity.PlayerRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.PlayerModel;
import net.minecraft.entity.EntityType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

import java.util.List;
import java.util.Map;

@OnlyIn(Dist.CLIENT)
public class ClientHandler {

    public static void onsetup(){

        //Spells
        RenderingRegistry.registerEntityRenderingHandler(EntityInit.WIND_BLADE.get(), new WindBladeRenderer.Factory());
        RenderingRegistry.registerEntityRenderingHandler(EntityInit.WIND_CRESCENT.get(), new WindCrescentRenderer.Factory());

        RenderingRegistry.registerEntityRenderingHandler(EntityInit.THUNDER_ORB.get(), new ThunderOrbRenderer.Factory());

        RenderingRegistry.registerEntityRenderingHandler(EntityInit.WIND_HAWK.get(), WindHawkRenderer::new);

        Map<String, PlayerRenderer> playerSkinMap = Minecraft.getInstance().getEntityRenderDispatcher().getSkinMap();
        ClientHandler.addPlayerLayers(playerSkinMap.get("default"));
        ClientHandler.addPlayerLayers(playerSkinMap.get("slim"));

    }

    public static void addPlayerLayers(PlayerRenderer renderer)
    {
        List<LayerRenderer<AbstractClientPlayerEntity, PlayerModel<AbstractClientPlayerEntity>>> layers = ObfuscationReflectionHelper.getPrivateValue(LivingRenderer.class, renderer, "field_177097_h");
        if(layers != null)
        {
            layers.add(new GrimoireLayer<>(renderer));
            layers.add(new TGLayer<>(renderer));
        }
    }

}
