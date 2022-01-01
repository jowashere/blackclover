package com.github.jowashere.blackclover;

import com.electronwill.nightconfig.core.CommentedConfig;
import com.github.jowashere.blackclover.api.BCMRegistry;
import com.github.jowashere.blackclover.api.IBCMPlugin;
import com.github.jowashere.blackclover.api.curios.CuriosApi;
import com.github.jowashere.blackclover.api.curios.SlotTypeMessage;
import com.github.jowashere.blackclover.api.curios.SlotTypePreset;
import com.github.jowashere.blackclover.capabilities.CapabilityHandler;
import com.github.jowashere.blackclover.capabilities.player.IPlayerHandler;
import com.github.jowashere.blackclover.capabilities.player.PlayerCapability;
import com.github.jowashere.blackclover.client.curios.*;
import com.github.jowashere.blackclover.client.curios.gui.curios.CuriosScreen;
import com.github.jowashere.blackclover.client.curios.gui.curios.GuiEventHandler;
import com.github.jowashere.blackclover.client.curios.gui.overlay.ManaBar;
import com.github.jowashere.blackclover.client.curios.gui.overlay.Notifications;
import com.github.jowashere.blackclover.client.curios.gui.overlay.SpellMode;
import com.github.jowashere.blackclover.client.curios.render.CuriosLayer;
import com.github.jowashere.blackclover.client.handler.ClientHandler;
import com.github.jowashere.blackclover.common.curios.CuriosConfig;
import com.github.jowashere.blackclover.common.curios.CuriosHelper;
import com.github.jowashere.blackclover.common.curios.CuriosRegistry;
import com.github.jowashere.blackclover.common.curios.capability.CurioInventoryCapability;
import com.github.jowashere.blackclover.common.curios.capability.CurioItemCapability;
import com.github.jowashere.blackclover.common.curios.event.CuriosEventHandler;
import com.github.jowashere.blackclover.common.curios.server.SlotHelper;
import com.github.jowashere.blackclover.common.curios.server.command.CurioArgumentType;
import com.github.jowashere.blackclover.common.curios.server.command.CuriosCommand;
import com.github.jowashere.blackclover.common.curios.slottype.SlotTypeManager;
import com.github.jowashere.blackclover.common.curios.triggers.EquipCurioTrigger;
import com.github.jowashere.blackclover.entities.mobs.quester.GrimoireMagicianEntity;
import com.github.jowashere.blackclover.entities.mobs.hostile.BanditEntity;
import com.github.jowashere.blackclover.events.GrimoireTextures;
import com.github.jowashere.blackclover.init.*;
import com.github.jowashere.blackclover.networking.NetworkLoader;
import com.github.jowashere.blackclover.util.helpers.KeyboardHelper;
import com.github.jowashere.blackclover.util.helpers.RaceHelper;
import com.github.jowashere.blackclover.world.structure.configured.ConfiguredStructures;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.renderer.entity.PlayerRenderer;
import net.minecraft.client.resources.ReloadListener;
import net.minecraft.command.arguments.ArgumentSerializer;
import net.minecraft.command.arguments.ArgumentTypes;
import net.minecraft.entity.ai.attributes.GlobalEntityTypeAttributes;
import net.minecraft.profiler.IProfiler;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DeferredWorkQueue;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.*;
import net.minecraftforge.fml.event.server.FMLServerAboutToStartEvent;
import net.minecraftforge.fml.event.server.FMLServerStoppedEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

@Mod(Main.MODID)
public class Main
{
    public static final String MODID = "blackclover";
    public static final Logger LOGGER = LogManager.getLogger();

    private static final boolean DEBUG = false;


    public Main() {
        //CURIOS STUFF
        DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> CuriosClientMod::init);

        final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        //CURIOS STUFF
        modEventBus.addListener(this::setup);
        modEventBus.addListener(this::config);
        modEventBus.addListener(this::enqueue);
        modEventBus.addListener(this::process);
        MinecraftForge.EVENT_BUS.addListener(this::serverAboutToStart);
        MinecraftForge.EVENT_BUS.addListener(this::serverStopped);
        MinecraftForge.EVENT_BUS.addListener(this::registerCommands);
        MinecraftForge.EVENT_BUS.addListener(this::reload);
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, CuriosClientConfig.CLIENT_SPEC);
        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, CuriosConfig.SERVER_SPEC);


        MinecraftForge.EVENT_BUS.register(new CapabilityHandler());
        MinecraftForge.EVENT_BUS.register(new KeyboardHelper());
        BCMRegistry.registerPlugin(new MainPlugin());

        GrimoireTextures.AddTextures();

        MinecraftForge.EVENT_BUS.register(this);

        ItemInit.ITEMS.register(modEventBus);
        ModAttributes.ATTRIBUTES.register(modEventBus);
        EffectInit.EFFECT.register(modEventBus);
        EntityInit.ENTITIES.register(modEventBus);
        EntityInit.SPAWN_EGGS.register(modEventBus);
        StructuresInit.DEFERRED_REGISTRY_STRUCTURE.register(modEventBus);



        modEventBus.addListener(this::onCommonSetup);
        modEventBus.addListener(this::onClientSetup);
    }

    private void setup(FMLCommonSetupEvent event)
    {
        CuriosApi.setCuriosHelper(new CuriosHelper());
        CurioInventoryCapability.register();
        CurioItemCapability.register();
        MinecraftForge.EVENT_BUS.register(new CuriosEventHandler());
        NetworkLoader.registerMessages();
        //NetworkHandler.register();
        ArgumentTypes.register("curios:slot_type", CurioArgumentType.class,
                new ArgumentSerializer<>(CurioArgumentType::slot));
        CriteriaTriggers.register(EquipCurioTrigger.INSTANCE);
    }

    //Here registering new stuff for curios
    private void enqueue(InterModEnqueueEvent evt)
    {
        InterModComms.sendTo(Main.MODID, SlotTypeMessage.REGISTER_TYPE, () -> SlotTypePreset.HEAD.getMessageBuilder().build());

        if (DEBUG) {
            InterModComms.sendTo(MODID, SlotTypeMessage.REGISTER_TYPE,
                    () -> Arrays.stream(SlotTypePreset.values())
                            .map(preset -> preset.getMessageBuilder().build()).collect(Collectors.toList()));
        }
    }
    private void process(InterModProcessEvent evt) {
        SlotTypeManager.buildImcSlotTypes(evt.getIMCStream(SlotTypeMessage.REGISTER_TYPE::equals),
                evt.getIMCStream(SlotTypeMessage.MODIFY_TYPE::equals));
    }
    private void serverAboutToStart(FMLServerAboutToStartEvent evt) {
        CuriosApi.setSlotHelper(new SlotHelper());
        SlotTypeManager.buildSlotTypes();
    }
    private void serverStopped(FMLServerStoppedEvent evt) {
        CuriosApi.setSlotHelper(null);
    }
    private void registerCommands(RegisterCommandsEvent evt) {
        CuriosCommand.register(evt.getDispatcher());
    }
    private void reload(final AddReloadListenerEvent evt) {
        evt.addListener(new ReloadListener<Void>() {
            @Nonnull
            @Override
            protected Void prepare(@Nonnull IResourceManager resourceManagerIn,
                                   @Nonnull IProfiler profilerIn) {
                return null;
            }

            @Override
            protected void apply(@Nonnull Void objectIn, @Nonnull IResourceManager resourceManagerIn,
                                 @Nonnull IProfiler profilerIn) {
                CuriosEventHandler.dirtyTags = true;
            }
        });
    }
    private void config(final ModConfig.Loading evt) {

        if (evt.getConfig().getModId().equals(MODID)) {

            if (evt.getConfig().getType() == ModConfig.Type.SERVER) {
                ForgeConfigSpec spec = evt.getConfig().getSpec();
                CommentedConfig commentedConfig = evt.getConfig().getConfigData();

                if (spec == CuriosConfig.SERVER_SPEC) {
                    CuriosConfig.transformCurios(commentedConfig);
                    SlotTypeManager.buildConfigSlotTypes();
                }
            }
        }
    }

    private void onCommonSetup(final FMLCommonSetupEvent event)
    {
        CapabilityManager.INSTANCE.register(IPlayerHandler.class, new PlayerCapability.Storage(), PlayerCapability::new);

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
        });

    }

    private void onClientSetup(final FMLClientSetupEvent event) {

        ClientHandler.OnSetup();
        MinecraftForge.EVENT_BUS.register(new Notifications());
        MinecraftForge.EVENT_BUS.register(new SpellMode());
        MinecraftForge.EVENT_BUS.register(new ManaBar());
        KeybindInit.register();
    }

    @Mod.EventBusSubscriber(modid = MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientProxy {

        @SubscribeEvent
        public static void stitchTextures(TextureStitchEvent.Pre evt) {
            CuriosClientMod.stitch(evt);
            evt.addSprite(new ResourceLocation(Main.MODID, "item/crown"));
        }

        @SubscribeEvent
        public static void setupClient(FMLClientSetupEvent evt) {
            CuriosApi.setIconHelper(new IconHelper());
            MinecraftForge.EVENT_BUS.register(new ClientEventHandler());
            MinecraftForge.EVENT_BUS.register(new GuiEventHandler());
            ScreenManager.register(CuriosRegistry.CONTAINER_TYPE, CuriosScreen::new);
            KeyRegistry.registerKeys();
        }

        @SubscribeEvent
        public static void postSetupClient(FMLLoadCompleteEvent evt) {
            Map<String, PlayerRenderer> skinMap = Minecraft.getInstance().getEntityRenderDispatcher().getSkinMap();

            for (PlayerRenderer render : skinMap.values()) {
                render.addLayer(new CuriosLayer<>(render));
            }
        }
    }
}
