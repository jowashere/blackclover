package com.github.jowashere.blackclover;

import com.github.jowashere.blackclover.api.BCMRegistry;
import com.github.jowashere.blackclover.api.IBCMPlugin;
import com.github.jowashere.blackclover.capabilities.CapabilityHandler;
import com.github.jowashere.blackclover.capabilities.player.IPlayerHandler;
import com.github.jowashere.blackclover.capabilities.player.PlayerCapability;
import com.github.jowashere.blackclover.client.gui.overlay.ManaBar;
import com.github.jowashere.blackclover.client.gui.overlay.Notifications;
import com.github.jowashere.blackclover.client.gui.overlay.SpellMode;
import com.github.jowashere.blackclover.client.handler.ClientHandler;
import com.github.jowashere.blackclover.entities.mobs.questers.GrimoireMagicianEntity;
import com.github.jowashere.blackclover.events.GrimoireTextures;
import com.github.jowashere.blackclover.init.*;
import com.github.jowashere.blackclover.networking.NetworkLoader;
import com.github.jowashere.blackclover.util.helpers.KeyboardHelper;
import com.github.jowashere.blackclover.util.helpers.RaceHelper;
import net.minecraft.entity.ai.attributes.GlobalEntityTypeAttributes;
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

        ItemInit.ITEMS.register(modEventBus);
        ModAttributes.ATTRIBUTES.register(modEventBus);
        EffectInit.EFFECT.register(modEventBus);
        EntityInit.ENTITIES.register(modEventBus);

        modEventBus.addListener(this::onCommonSetup);
        modEventBus.addListener(this::onClientSetup);
    }

    private void onCommonSetup(final FMLCommonSetupEvent event)
    {
        CapabilityManager.INSTANCE.register(IPlayerHandler.class, new PlayerCapability.Storage(), PlayerCapability::new);

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
        });

    }

    private void onClientSetup(final FMLClientSetupEvent event) {

        ClientHandler.OnSetup();
        MinecraftForge.EVENT_BUS.register(new Notifications());
        MinecraftForge.EVENT_BUS.register(new SpellMode());
        MinecraftForge.EVENT_BUS.register(new ManaBar());
        KeybindInit.register();


    }
}
