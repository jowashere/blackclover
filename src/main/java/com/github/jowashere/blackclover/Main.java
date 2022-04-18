package com.github.jowashere.blackclover;

import com.github.jowashere.blackclover.api.BCMRegistry;
import com.github.jowashere.blackclover.api.IBCMPlugin;
import com.github.jowashere.blackclover.capabilities.CapabilityHandler;
import com.github.jowashere.blackclover.capabilities.player.IPlayerHandler;
import com.github.jowashere.blackclover.capabilities.player.PlayerCapability;
import com.github.jowashere.blackclover.client.gui.overlay.ManaBar;
import com.github.jowashere.blackclover.client.gui.overlay.Notifications;
import com.github.jowashere.blackclover.client.gui.overlay.Quest;
import com.github.jowashere.blackclover.client.gui.overlay.SpellMode;
import com.github.jowashere.blackclover.client.handler.ClientHandler;
import com.github.jowashere.blackclover.entities.mobs.hostile.BanditEntity;
import com.github.jowashere.blackclover.entities.mobs.hostile.VolcanoMonsterEntity;
import com.github.jowashere.blackclover.entities.mobs.quester.GrimoireMagicianEntity;
import com.github.jowashere.blackclover.events.GrimoireTextures;
import com.github.jowashere.blackclover.init.*;
import com.github.jowashere.blackclover.networking.NetworkLoader;
import com.github.jowashere.blackclover.particles.StartupClientParticle;
import com.github.jowashere.blackclover.particles.StartupCommonParticle;
import com.github.jowashere.blackclover.util.helpers.KeyboardHelper;
import com.github.jowashere.blackclover.util.helpers.RaceHelper;
import com.github.jowashere.blackclover.world.biome.ModBiomes;
import com.github.jowashere.blackclover.world.gen.ModBiomeGeneration;
import com.github.jowashere.blackclover.world.structure.configured.ConfiguredStructures;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.entity.ai.attributes.GlobalEntityTypeAttributes;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DeferredWorkQueue;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(Main.MODID)
public class Main
{
    public static final String MODID = "blackclover";
    private static final Logger LOGGER = LogManager.getLogger();

    public Main() {

        final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        MinecraftForge.EVENT_BUS.register(new CapabilityHandler());
        MinecraftForge.EVENT_BUS.register(new KeyboardHelper());
        BCMRegistry.registerPlugin(new MainPlugin());
        NetworkLoader.registerMessages();

        GrimoireTextures.AddTextures();

        MinecraftForge.EVENT_BUS.register(this);

        BlocksInit.BLOCKS.register(modEventBus);
        ItemInit.ITEMS.register(modEventBus);
        ModAttributes.ATTRIBUTES.register(modEventBus);
        EffectInit.EFFECT.register(modEventBus);
        PotionInit.POTIONS.register(modEventBus);
        PotionInit.EFFECTS.register(modEventBus);
        EntityInit.ENTITIES.register(modEventBus);
        EntityInit.SPAWN_EGGS.register(modEventBus);
        StructuresInit.DEFERRED_REGISTRY_STRUCTURE.register(modEventBus);
        ModBiomes.register(modEventBus);

        modEventBus.addListener(this::onCommonSetup);
        modEventBus.addListener(this::onClientSetup);
        modEventBus.addListener(this::onParticleSetup);
    }


    private void onCommonSetup(final FMLCommonSetupEvent event)
    {
        ModBiomeGeneration.generateBiomes();
        CapabilityManager.INSTANCE.register(IPlayerHandler.class, new PlayerCapability.Storage(), PlayerCapability::new);

        PotionInit.addPotionRecipes();

        event.enqueueWork(() ->
        {
            StructuresInit.setupStructures();
            ConfiguredStructures.registerConfiguredStructures();
        });

        for (IBCMPlugin plugin : BCMRegistry.PLUGINS) {
            System.out.println("Black Clover Mod Plugin, id: " + plugin.getPluginId() + ". Has been registered.");
            plugin.RegisterNewSpells(BCMRegistry.SPELLS);
            plugin.RegisterNewAttributes(BCMRegistry.ATTRIBUTES);
            plugin.RegisterNewModes(BCMRegistry.MODES);
            plugin.RegisterNewRaces(BCMRegistry.RACES);
        }

        RaceHelper.create();

        DeferredWorkQueue.runLater(() ->
        {
            GlobalEntityTypeAttributes.put(EntityInit.GRIMOIRE_MAGICIAN.get(), GrimoireMagicianEntity.setCustomAttributes().build());
            GlobalEntityTypeAttributes.put(EntityInit.BANDIT.get(), BanditEntity.setCustomAttributes().build());
            GlobalEntityTypeAttributes.put(EntityInit.VOLCANO_MONSTER.get(), VolcanoMonsterEntity.setCustomAttributes().build());

        });

    }

    private void onClientSetup(final FMLClientSetupEvent event) {

        RenderTypeLookup.setRenderLayer(BlocksInit.MOGURO_LEAF.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BlocksInit.MOGURO_SAPLING.get(), RenderType.cutout());

        ClientHandler.OnSetup();

        MinecraftForge.EVENT_BUS.register(new Quest());
        MinecraftForge.EVENT_BUS.register(new Notifications());
        MinecraftForge.EVENT_BUS.register(new SpellMode());
        MinecraftForge.EVENT_BUS.register(new ManaBar());
        KeybindInit.register();
    }

    private void onParticleSetup(final ParticleFactoryRegisterEvent event)
    {
        MinecraftForge.EVENT_BUS.register(StartupCommonParticle.class);
        MinecraftForge.EVENT_BUS.register(StartupClientParticle.class);
    }
}
