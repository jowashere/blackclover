package com.github.jowashere.blackclover.init.spells;

import com.github.jowashere.blackclover.api.BCMRegistry;
import com.github.jowashere.blackclover.api.IBCMPlugin;
import com.github.jowashere.blackclover.api.internal.BCMSpell;
import com.github.jowashere.blackclover.capabilities.player.IPlayerHandler;
import com.github.jowashere.blackclover.capabilities.player.PlayerCapability;
import com.github.jowashere.blackclover.capabilities.player.PlayerProvider;
import com.github.jowashere.blackclover.entities.spells.light.LightSwordOJEntity;
import com.github.jowashere.blackclover.init.ItemInit;
import com.github.jowashere.blackclover.util.helpers.BCMHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.util.LazyOptional;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class LightMagicSpells {

    private static IBCMPlugin plugin;

    public static BCMSpell LIGHT_SWORD = new BCMSpell(plugin, "light_sword", BCMSpell.Type.LIGHT_MAGIC, 0.4F, 30, true, 80, 32, true, (playerIn, modifier0, modifier1, playerCapability, manaIn) -> {

        LazyOptional<IPlayerHandler> playerInCap = playerIn.getCapability(PlayerProvider.CAPABILITY_PLAYER, null);
        IPlayerHandler player_cap = playerInCap.orElse(new PlayerCapability());

    }).addStartEventListener((playerIn, manaIn) -> {
        BCMHelper.GiveItem(playerIn, new ItemStack(ItemInit.LIGHT_SWORD.get()));
    }).setUnlockLevel(1);

    public static BCMSpell LIGHT_SWORD_OJ = new BCMSpell(plugin, "light_sword_oj", BCMSpell.Type.LIGHT_MAGIC, 30F, 50, false, 80, 16, false, (playerIn, modifier0, modifier1, playerCapability, manaIn) -> {

        LazyOptional<IPlayerHandler> playerInCap = playerIn.getCapability(PlayerProvider.CAPABILITY_PLAYER, null);
        IPlayerHandler player_cap = playerInCap.orElse(new PlayerCapability());

        if (!playerIn.level.isClientSide) {
            LightSwordOJEntity entity = new LightSwordOJEntity(playerIn.level, playerIn, manaIn);
            entity.shootFromRotation(playerIn, playerIn.xRot, playerIn.yRot, 0.0F, 3.6F, 1F);
            playerIn.level.addFreshEntity(entity);
            playerIn.swing(Hand.MAIN_HAND, true);
        }

    }).setUnlockLevel(5);

    public static BCMSpell LIGHT_SWORDS_OJ = new BCMSpell(plugin, "light_swords_oj", BCMSpell.Type.LIGHT_MAGIC, 80F, 100, false, 80, 16, false, (playerIn, modifier0, modifier1, playerCapability, manaIn) -> {

        LazyOptional<IPlayerHandler> playerInCap = playerIn.getCapability(PlayerProvider.CAPABILITY_PLAYER, null);
        IPlayerHandler player_cap = playerInCap.orElse(new PlayerCapability());

        if (!playerIn.level.isClientSide) {
            for(int i = 0; i < 15; i++) {
                LightSwordOJEntity entity = new LightSwordOJEntity(playerIn.level, playerIn, manaIn);

                entity.shoot((float) (playerIn.getLookAngle().x + (Math.random() * 0.45) - 0.275), (float) (playerIn.getLookAngle().y + (Math.random() * 0.4) - 0.25), (float) (playerIn.getLookAngle().z + (Math.random() * 0.45) - 0.275), 3.0F, 0);
                playerIn.level.addFreshEntity(entity);
            }
        }

    }).setUnlockLevel(10);

    public static BCMSpell LIGHT_MOVEMENT = new BCMSpell(plugin, "light_movement", BCMSpell.Type.LIGHT_MAGIC, 50F, 90, false, 80, 0, false, (playerIn, modifier0, modifier1, playerCapability, manaIn) -> {

        LazyOptional<IPlayerHandler> playerInCap = playerIn.getCapability(PlayerProvider.CAPABILITY_PLAYER, null);
        IPlayerHandler player_cap = playerInCap.orElse(new PlayerCapability());

        if (!playerIn.level.isClientSide) {
            BlockPos pos = BCMHelper.RayTraceBlockSafe(playerIn, 8.5F);

            ((ServerWorld) playerIn.level).sendParticles(ParticleTypes.END_ROD, playerIn.getX(), playerIn.getY(), playerIn.getZ(), 10, 0, 1, 0, 0.1);
            playerIn.teleportTo(pos.getX(), pos.getY(), pos.getZ());
            if (playerIn instanceof ServerPlayerEntity) {
                ((ServerPlayerEntity) playerIn).connection.teleport(pos.getX(), pos.getY(), pos.getZ(), playerIn.yRot,
                        playerIn.xRot, Collections.emptySet());
            }
            ((ServerWorld) playerIn.level).sendParticles(ParticleTypes.END_ROD, playerIn.getX(), playerIn.getY(), playerIn.getZ(), 10, 0, 1, 0, 0.1);
        }

    }).setUnlockLevel(5);

    public static BCMSpell ARROWS_OF_JUDGEMENT = new BCMSpell(plugin, "arrows_of_judgement", BCMSpell.Type.LIGHT_MAGIC, 100F, 300, false, 80, 80, false, (playerIn, modifier0, modifier1, playerCapability, manaIn) -> {

        LazyOptional<IPlayerHandler> playerInCap = playerIn.getCapability(PlayerProvider.CAPABILITY_PLAYER, null);
        IPlayerHandler player_cap = playerInCap.orElse(new PlayerCapability());

        int maxArrowCount = 5 + ((player_cap.ReturnMagicLevel()/100)*30);

        if (!playerIn.level.isClientSide) {

            List<LivingEntity> entities = BCMHelper.GetEntitiesNear(playerIn.blockPosition(), playerIn.level, 15, LivingEntity.class);
            entities.remove(playerIn);

            AtomicInteger arrowCount = new AtomicInteger();

            entities.forEach(entity -> {
                if(arrowCount.get() < maxArrowCount){
                    LightSwordOJEntity lightSword = new LightSwordOJEntity(playerIn.level, playerIn, manaIn);
                    lightSword.setPos(entity.getX(), entity.getY() + 5, entity.getZ());
                    lightSword.shoot(0, -180, 0, 2F, 0);
                    playerIn.level.addFreshEntity(lightSword);
                    arrowCount.getAndIncrement();
                }
            });
        }

    }).setUnlockLevel(40);

    public static BCMSpell LIGHT_HEALING = new BCMSpell(plugin, "light_healing", BCMSpell.Type.LIGHT_MAGIC, 0.95F, 120, false, 80, 64, true, (playerIn, modifier0, modifier1, playerCapability, manaIn) -> {

        LazyOptional<IPlayerHandler> playerInCap = playerIn.getCapability(PlayerProvider.CAPABILITY_PLAYER, null);
        IPlayerHandler player_cap = playerInCap.orElse(new PlayerCapability());

        if (!playerIn.level.isClientSide) {
            playerIn.addEffect(new EffectInstance(Effects.REGENERATION, 5, Math.max(1, player_cap.ReturnMagicLevel()/2), false, false, false));
            ((ServerWorld) playerIn.level).sendParticles(ParticleTypes.END_ROD, playerIn.getX(), playerIn.getY(), playerIn.getZ(), 2, 0, 1, 0, 0.1);
        }

    }).setToggleTimer(60).setUnlockLevel(20);

    public static void registerSpells(BCMRegistry.SpellRegistry spellRegistry, IBCMPlugin pluginIn) {

        spellRegistry.register(LIGHT_SWORD.setPlugin(pluginIn));
        spellRegistry.register(LIGHT_MOVEMENT.setPlugin(pluginIn));
        spellRegistry.register(LIGHT_SWORD_OJ.setPlugin(pluginIn));
        spellRegistry.register(LIGHT_SWORDS_OJ.setPlugin(pluginIn));
        spellRegistry.register(LIGHT_HEALING.setPlugin(pluginIn));
        spellRegistry.register(ARROWS_OF_JUDGEMENT.setPlugin(pluginIn));

    }
}
