package com.github.jowashere.blackclover.init.spells;

import com.github.jowashere.blackclover.api.BCMRegistry;
import com.github.jowashere.blackclover.api.IBCMPlugin;
import com.github.jowashere.blackclover.api.internal.BCMSpell;
import com.github.jowashere.blackclover.capabilities.player.IPlayerHandler;
import com.github.jowashere.blackclover.capabilities.player.PlayerCapability;
import com.github.jowashere.blackclover.capabilities.player.PlayerProvider;
import com.github.jowashere.blackclover.entities.projectiles.spells.lightning.ThunderOrbEntity;
import com.github.jowashere.blackclover.entities.projectiles.spells.wind.WindBladeEntity;
import com.github.jowashere.blackclover.entities.projectiles.spells.wind.WindCrescentEntity;
import com.github.jowashere.blackclover.entities.summons.WindHawkEntity;
import com.github.jowashere.blackclover.init.EffectInit;
import com.github.jowashere.blackclover.init.EntityInit;
import net.minecraft.entity.*;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.util.LazyOptional;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class LightningSpells {

    public static void registerLightningSpells(BCMRegistry.SpellRegistry spellRegistry, IBCMPlugin pluginIn) {
        spellRegistry.register(new BCMSpell(pluginIn, "tg_gloves", BCMSpell.Type.LIGHTNING_MAGIC, 0.25F, 40, false, 32, 0, true, (playerIn, modifier0, modifier1, playerCapability) -> {

            LazyOptional<IPlayerHandler> playerInCap = playerIn.getCapability(PlayerProvider.CAPABILITY_PLAYER, null);
            IPlayerHandler player_cap = playerInCap.orElse(new PlayerCapability());

            if (!playerIn.level.isClientSide) {
                playerIn.addEffect(new EffectInstance(Effects.DAMAGE_BOOST, 55,  Integer.min( (int) (player_cap.returnMagicLevel() * 0.75), 26), false, false, false));
                playerIn.addEffect(new EffectInstance(Effects.DIG_SPEED, 55, Integer.min((int) (player_cap.returnMagicLevel() * 0.75), 20), false, false, false));

            }
        }));
        spellRegistry.register(new BCMSpell(pluginIn, "tg_boots", BCMSpell.Type.LIGHTNING_MAGIC, 0.25F, 40, false, 32, 16, true, (playerIn, modifier0, modifier1, playerCapability) -> {

            LazyOptional<IPlayerHandler> playerInCap = playerIn.getCapability(PlayerProvider.CAPABILITY_PLAYER, null);
            IPlayerHandler player_cap = playerInCap.orElse(new PlayerCapability());

            if (!playerIn.level.isClientSide) {
                if(playerIn.isSprinting())
                    playerIn.addEffect(new EffectInstance(Effects.MOVEMENT_SPEED, 55,  Integer.min( (int) (player_cap.returnMagicLevel() * 0.65), 20), false, false, false));
                if(!playerIn.isOnGround())
                    playerIn.addEffect(new EffectInstance(Effects.JUMP, 55, Integer.min((int) (player_cap.returnMagicLevel() * 0.65), 8), false, false, false));

            }
        }));
        spellRegistry.register(new BCMSpell(pluginIn, "thunder_orb", BCMSpell.Type.LIGHTNING_MAGIC, 0.5F, 60, false, 32, 16, true, ((playerIn, modifier0, modifier1, playerCapability) -> {

            LazyOptional<IPlayerHandler> playerInCap = playerIn.getCapability(PlayerProvider.CAPABILITY_PLAYER, null);
            IPlayerHandler player_cap = playerInCap.orElse(new PlayerCapability());

            if (!playerIn.level.isClientSide) {
                ThunderOrbEntity entity = new ThunderOrbEntity(playerIn.level, playerIn);
                entity.shootFromRotation(playerIn, playerIn.xRot, playerIn.yRot, 0.0F, 1.3F, 5.0F);
                playerIn.level.addFreshEntity(entity);
                playerIn.swing(Hand.MAIN_HAND, true);
            }
        })).setExtraSpellChecks((playerIn, modifier0, modifier1) -> playerIn.getPersistentData().getBoolean("blackclover_tg_gloves")));
    }

}
