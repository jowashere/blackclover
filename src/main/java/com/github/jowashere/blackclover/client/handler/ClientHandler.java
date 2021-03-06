package com.github.jowashere.blackclover.client.handler;

import com.github.jowashere.blackclover.Main;
import com.github.jowashere.blackclover.client.renderer.entities.hostile.VolcanoMonsterRenderer;
import com.github.jowashere.blackclover.client.renderer.handler.HumanoidRenderer;
import com.github.jowashere.blackclover.client.renderer.item.FullBrightItem;
import com.github.jowashere.blackclover.client.renderer.layers.*;
import com.github.jowashere.blackclover.client.renderer.models.HumanoidModel;
import com.github.jowashere.blackclover.client.renderer.spells.others.BlackHoleRenderer;
import com.github.jowashere.blackclover.client.renderer.spells.others.WaterDomeRenderer;
import com.github.jowashere.blackclover.client.renderer.spells.others.WaterShieldRenderer;
import com.github.jowashere.blackclover.client.renderer.spells.projectiles.antimagic.BlackSlashRenderer;
import com.github.jowashere.blackclover.client.renderer.spells.projectiles.darkness.AvidyaSlashRenderer;
import com.github.jowashere.blackclover.client.renderer.spells.projectiles.light.LightSwordOJRenderer;
import com.github.jowashere.blackclover.client.renderer.spells.projectiles.lightning.ThunderOrbRenderer;
import com.github.jowashere.blackclover.client.renderer.spells.projectiles.slash.DeathScytheRenderer;
import com.github.jowashere.blackclover.client.renderer.spells.projectiles.sword.OriginFlashRenderer;
import com.github.jowashere.blackclover.client.renderer.spells.projectiles.water.PointBlankWaterDragonRenderer;
import com.github.jowashere.blackclover.client.renderer.spells.projectiles.water.WaterBallRenderer;
import com.github.jowashere.blackclover.client.renderer.spells.projectiles.water.WaterDragonRender;
import com.github.jowashere.blackclover.client.renderer.spells.projectiles.water.WaterSpearRenderer;
import com.github.jowashere.blackclover.client.renderer.spells.projectiles.wind.WindBladeRenderer;
import com.github.jowashere.blackclover.client.renderer.spells.projectiles.wind.WindCrescentRenderer;
import com.github.jowashere.blackclover.client.renderer.spells.summons.WindHawkRenderer;
import com.github.jowashere.blackclover.init.EntityInit;
import com.github.jowashere.blackclover.init.ItemInit;
import com.github.jowashere.blackclover.items.weapons.WeaponProperties;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.client.renderer.entity.PlayerRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.PlayerModel;
import net.minecraft.client.renderer.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemModelsProperties;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.UnaryOperator;

@OnlyIn(Dist.CLIENT)
public class ClientHandler {

    public static void OnSetup(){

        //Spells
        RenderingRegistry.registerEntityRenderingHandler(EntityInit.WATER_BALL.get(), new WaterBallRenderer.Factory());
        RenderingRegistry.registerEntityRenderingHandler(EntityInit.WATER_SPEAR.get(), new WaterSpearRenderer.Factory());
        RenderingRegistry.registerEntityRenderingHandler(EntityInit.WATER_DRAGON.get(), new WaterDragonRender.Factory());
        RenderingRegistry.registerEntityRenderingHandler(EntityInit.WATER_SHIELD.get(), new WaterShieldRenderer.Factory());
        RenderingRegistry.registerEntityRenderingHandler(EntityInit.WATER_DOME.get(), new WaterDomeRenderer.Factory());
        RenderingRegistry.registerEntityRenderingHandler(EntityInit.POINT_BLANK_DRAGON.get(), new PointBlankWaterDragonRenderer.Factory());

        RenderingRegistry.registerEntityRenderingHandler(EntityInit.WIND_BLADE.get(), new WindBladeRenderer.Factory());
        RenderingRegistry.registerEntityRenderingHandler(EntityInit.WIND_CRESCENT.get(), new WindCrescentRenderer.Factory());
        RenderingRegistry.registerEntityRenderingHandler(EntityInit.WIND_HAWK.get(), WindHawkRenderer::new);

        RenderingRegistry.registerEntityRenderingHandler(EntityInit.THUNDER_ORB.get(), new ThunderOrbRenderer.Factory());

        RenderingRegistry.registerEntityRenderingHandler(EntityInit.AVIDYA_SLASH.get(), new AvidyaSlashRenderer.Factory());
        RenderingRegistry.registerEntityRenderingHandler(EntityInit.BLACK_HOLE.get(), new BlackHoleRenderer.Factory());

        RenderingRegistry.registerEntityRenderingHandler(EntityInit.BLACK_SLASH.get(), new BlackSlashRenderer.Factory());

        RenderingRegistry.registerEntityRenderingHandler(EntityInit.DEATH_SCYTHE.get(), new DeathScytheRenderer.Factory());

        RenderingRegistry.registerEntityRenderingHandler(EntityInit.LIGHT_SWORD_OJ.get(), new LightSwordOJRenderer.Factory());

        RenderingRegistry.registerEntityRenderingHandler(EntityInit.ORIGIN_FLASH.get(), new OriginFlashRenderer.Factory());

        //Mobs
        RenderingRegistry.registerEntityRenderingHandler(EntityInit.GRIMOIRE_MAGICIAN.get(), new HumanoidRenderer.Factory(new HumanoidModel(), 1F));

        //Hostile mobs
        RenderingRegistry.registerEntityRenderingHandler(EntityInit.BANDIT.get(), new HumanoidRenderer.Factory(new HumanoidModel(), 1F));
        RenderingRegistry.registerEntityRenderingHandler(EntityInit.VOLCANO_MONSTER.get(), new VolcanoMonsterRenderer.Factory());

        Map<String, PlayerRenderer> playerSkinMap = Minecraft.getInstance().getEntityRenderDispatcher().getSkinMap();
        ClientHandler.addPlayerLayers(playerSkinMap.get("default"));
        ClientHandler.addPlayerLayers(playerSkinMap.get("slim"));

        ClientHandler.addItemModelProperties();

    }

    @Mod.EventBusSubscriber(modid = Main.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ModBusEvents {

        @SubscribeEvent
        public static void modelBake(ModelBakeEvent event) {

            fullbrightItem(event, ItemInit.LIGHT_SWORD);
        }
    }

    @OnlyIn(Dist.CLIENT)
    private static void addItemModelProperties(){
        ItemModelsProperties.register(ItemInit.YAMIS_KATANA.get(), new ResourceLocation("antimagic"), WeaponProperties.antiMagicProperty);
        ItemModelsProperties.register(ItemInit.YAMIS_KATANA.get(), new ResourceLocation("blackmode"), WeaponProperties.blackMode);

        ItemModelsProperties.register(ItemInit.DEMON_DWELLER.get(), new ResourceLocation("antimagic"), WeaponProperties.antiMagicProperty);
        ItemModelsProperties.register(ItemInit.DEMON_DWELLER.get(), new ResourceLocation("blackmode"), WeaponProperties.blackMode);

        ItemModelsProperties.register(ItemInit.DEMON_DESTROYER.get(), new ResourceLocation("antimagic"), WeaponProperties.antiMagicProperty);
        ItemModelsProperties.register(ItemInit.DEMON_DESTROYER.get(), new ResourceLocation("blackmode"), WeaponProperties.blackMode);

        ItemModelsProperties.register(ItemInit.DEMON_SLAYER.get(), new ResourceLocation("antimagic"), WeaponProperties.antiMagicProperty);
        ItemModelsProperties.register(ItemInit.DEMON_SLAYER.get(), new ResourceLocation("blackmode"), WeaponProperties.blackMode);
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

    private static void fullbrightItem(ModelBakeEvent event, RegistryObject<Item> item) {
        fullbrightItem(event, item, f -> f);
    }

    private static void fullbrightItem(ModelBakeEvent event, RegistryObject<Item> item, UnaryOperator<FullBrightItem> process) {
        fullbright(event, Objects.requireNonNull(item.getId()), "inventory", process);
    }

    private static void fullbright(ModelBakeEvent event, ResourceLocation rl, String state, UnaryOperator<FullBrightItem> process) {
        ModelResourceLocation mrl = new ModelResourceLocation(rl, state);
        event.getModelRegistry().put(mrl, process.apply(new FullBrightItem(event.getModelRegistry().get(mrl))));
    }

}
