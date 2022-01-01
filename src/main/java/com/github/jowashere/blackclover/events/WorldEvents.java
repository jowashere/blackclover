package com.github.jowashere.blackclover.events;

import com.github.jowashere.blackclover.Main;
import com.github.jowashere.blackclover.capabilities.player.IPlayerHandler;
import com.github.jowashere.blackclover.capabilities.player.PlayerCapability;
import com.github.jowashere.blackclover.capabilities.player.PlayerProvider;
import com.github.jowashere.blackclover.init.PotionInit;
import com.github.jowashere.blackclover.world.biome.ModBiomes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;


public class WorldEvents
{
    @Mod.EventBusSubscriber(modid = Main.MODID)
    public static class biomeStuff
    {
        @SubscribeEvent
        public static void enteringManaZone(LivingEvent.LivingUpdateEvent event)
        {
            if (!(event.getEntityLiving() instanceof PlayerEntity))
                return;

            PlayerEntity player = (PlayerEntity) event.getEntityLiving();
            World world = player.level;

            if(world.isClientSide)
                return;

            LazyOptional<IPlayerHandler> playerc = player.getCapability(PlayerProvider.CAPABILITY_PLAYER, null);
            IPlayerHandler player_cap = playerc.orElse(new PlayerCapability());
            int magiclevel = player_cap.getMagicLevel();
            BlockPos pos = player.getEntity().blockPosition();
            ResourceLocation biome = world.registryAccess().registryOrThrow(Registry.BIOME_REGISTRY).getKey(world.getBiome(pos));


            if (biome.equals(ModBiomes.GRAND_MAGIC_ZONE_VOLCANO.getId()))
            {
                if (magiclevel < 30 || !player_cap.ReturnManaSkinToggled())
                {
                    if (player.getHealth() > 1.0F)
                    {
                        player.hurt(DamageSource.MAGIC, 1.0F);
                    }
                }
            }
        }
    }
}
