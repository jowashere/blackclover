package com.github.jowashere.blackclover.client.handler;

import com.github.jowashere.blackclover.client.renderer.handler.GrimoireMagicianRenderer;
import com.github.jowashere.blackclover.client.renderer.layers.*;
import com.github.jowashere.blackclover.client.renderer.spells.others.BlackHoleRenderer;
import com.github.jowashere.blackclover.client.renderer.spells.projectiles.antimagic.BlackSlashRenderer;
import com.github.jowashere.blackclover.client.renderer.spells.projectiles.darkness.AvidyaSlashRenderer;
import com.github.jowashere.blackclover.client.renderer.spells.projectiles.lightning.ThunderOrbRenderer;
import com.github.jowashere.blackclover.client.renderer.spells.projectiles.slash.DeathScytheRenderer;
import com.github.jowashere.blackclover.client.renderer.spells.projectiles.wind.WindBladeRenderer;
import com.github.jowashere.blackclover.client.renderer.spells.projectiles.wind.WindCrescentRenderer;
import com.github.jowashere.blackclover.client.renderer.spells.summons.WindHawkRenderer;
import com.github.jowashere.blackclover.init.EntityInit;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.client.renderer.entity.PlayerRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.PlayerModel;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

import java.util.List;
import java.util.Map;

@OnlyIn(Dist.CLIENT)
public class ClientHandler {

    public static void OnSetup(){

        //Spells
        RenderingRegistry.registerEntityRenderingHandler(EntityInit.WIND_BLADE.get(), new WindBladeRenderer.Factory());
        RenderingRegistry.registerEntityRenderingHandler(EntityInit.WIND_CRESCENT.get(), new WindCrescentRenderer.Factory());

        RenderingRegistry.registerEntityRenderingHandler(EntityInit.THUNDER_ORB.get(), new ThunderOrbRenderer.Factory());

        RenderingRegistry.registerEntityRenderingHandler(EntityInit.AVIDYA_SLASH.get(), new AvidyaSlashRenderer.Factory());
        RenderingRegistry.registerEntityRenderingHandler(EntityInit.BLACK_HOLE.get(), new BlackHoleRenderer.Factory());

        RenderingRegistry.registerEntityRenderingHandler(EntityInit.BLACK_SLASH.get(), new BlackSlashRenderer.Factory());

        RenderingRegistry.registerEntityRenderingHandler(EntityInit.WIND_HAWK.get(), WindHawkRenderer::new);

        RenderingRegistry.registerEntityRenderingHandler(EntityInit.DEATH_SCYTHE.get(), new DeathScytheRenderer.Factory());

        RenderingRegistry.registerEntityRenderingHandler(EntityInit.GRIMOIRE_MAGICIAN.get(), GrimoireMagicianRenderer::new);

        Map<String, PlayerRenderer> playerSkinMap = Minecraft.getInstance().getEntityRenderDispatcher().getSkinMap();
        ClientHandler.addPlayerLayers(playerSkinMap.get("default"));
        ClientHandler.addPlayerLayers(playerSkinMap.get("slim"));

    }

    @OnlyIn(Dist.CLIENT)
    public static void addPlayerLayers(PlayerRenderer renderer)
    {
        List<LayerRenderer<AbstractClientPlayerEntity, PlayerModel<AbstractClientPlayerEntity>>> layers = ObfuscationReflectionHelper.getPrivateValue(LivingRenderer.class, renderer, "field_177097_h");
        if(layers != null)
        {
            layers.add(new GrimoireLayer<>(renderer));
            layers.add(new TGLayer<>(renderer));
            layers.add(new BlackModeLayer<>(renderer));
            layers.add(new BlackCocoonLayer<>(renderer));
            layers.add(new SlashBladesLayer<>(renderer));

        }
    }

}
