package com.github.jowashere.blackclover.init.spells;

import com.github.jowashere.blackclover.api.BCMRegistry;
import com.github.jowashere.blackclover.api.IBCMPlugin;
import com.github.jowashere.blackclover.api.internal.BCMSpell;
import com.github.jowashere.blackclover.capabilities.player.IPlayerHandler;
import com.github.jowashere.blackclover.capabilities.player.PlayerCapability;
import com.github.jowashere.blackclover.capabilities.player.PlayerProvider;
import com.github.jowashere.blackclover.entities.spells.slash.DeathScytheEntity;
import com.github.jowashere.blackclover.util.helpers.BCMHelper;
import com.github.jowashere.blackclover.util.helpers.SpellHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.item.ArmorStandEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.util.LazyOptional;

import java.util.List;
import java.util.UUID;

public class SlashMagicSpells {

    public static BCMSpell DEATH_SCYTHE = new BCMSpell(null, "death_scythe", BCMSpell.Type.SLASH_MAGIC, 30F, 60, false, 64, 16, false, (playerIn, modifier0, modifier1, playerCapability, manaIn) -> {

        if (!playerIn.level.isClientSide) {
            DeathScytheEntity entity = new DeathScytheEntity(playerIn.level, playerIn, manaIn);
            entity.shootFromRotation(playerIn, playerIn.xRot, playerIn.yRot, 0.0F, 1.8F, 1.0F);
            playerIn.level.addFreshEntity(entity);
            playerIn.swing(Hand.MAIN_HAND, true);
        }
    }).setExtraSpellChecks((playerIn -> {
        boolean slashBlades = playerIn.getPersistentData().getBoolean("blackclover_slash_blades");
        return slashBlades;
    })).setCheckFailMsg("Slash Blades need to be on.");
    public static BCMSpell LUNATIC_SLASH = new BCMSpell(null, "lunatic_slash", BCMSpell.Type.SLASH_MAGIC, 70F, 500, false, 64, 32, false, (playerIn, modifier0, modifier1, playerCapability, manaIn) -> {

        LazyOptional<IPlayerHandler> playerInCap = playerIn.getCapability(PlayerProvider.CAPABILITY_PLAYER, null);
        IPlayerHandler player_cap = playerInCap.orElse(new PlayerCapability());

        RayTraceResult mop = BCMHelper.RayTraceBlocksAndEntities(playerIn, 7);

        List<LivingEntity> entities = BCMHelper.GetEntitiesNear(new BlockPos(mop.getLocation()), playerIn.level, 7, LivingEntity.class);
        if(entities.contains(playerIn))
            entities.remove(playerIn);

        entities.forEach(entity -> {
            entity.hurt(DamageSource.playerAttack(playerIn), SpellHelper.spellDamageCalcP(playerIn, 2, 3));
            ((ServerWorld) playerIn.level).sendParticles(ParticleTypes.SWEEP_ATTACK, entity.getX(), entity.getY(),
                    entity.getZ(), (int) 10, 3, 3, 3, 0.1);
            (playerIn.level).playSound((PlayerEntity) null, entity.getX(), entity.getY(), entity.getZ(), SoundEvents.PLAYER_ATTACK_SWEEP, playerIn.getSoundSource(), (float) 1, (float) 1);

            BCMHelper.waitThen(playerIn.level, 11, () -> {
                entity.hurt(DamageSource.playerAttack(playerIn), SpellHelper.spellDamageCalcP(playerIn, 2, 3));
                ((ServerWorld) playerIn.level).sendParticles(ParticleTypes.SWEEP_ATTACK, entity.getX(), entity.getY(),
                        entity.getZ(), (int) 10, 3, 3, 3, 0.1);
                (playerIn.level).playSound((PlayerEntity) null, entity.getX(), entity.getY(), entity.getZ(), SoundEvents.PLAYER_ATTACK_SWEEP, playerIn.getSoundSource(), (float) 1, (float) 1);
            });

            BCMHelper.waitThen(playerIn.level, 22, () -> {
                entity.hurt(DamageSource.playerAttack(playerIn), SpellHelper.spellDamageCalcP(playerIn, 2, 3));
                ((ServerWorld) playerIn.level).sendParticles(ParticleTypes.SWEEP_ATTACK, entity.getX(), entity.getY(),
                        entity.getZ(), (int) 10, 3, 3, 3, 0.1);
                (playerIn.level).playSound((PlayerEntity) null, entity.getX(), entity.getY(), entity.getZ(), SoundEvents.PLAYER_ATTACK_SWEEP, playerIn.getSoundSource(), (float) 1, (float) 1);
            });

        });

    }).setExtraSpellChecks((playerIn -> {
        boolean slashBlades = playerIn.getPersistentData().getBoolean("blackclover_slash_blades");
        return slashBlades;
    })).setCheckFailMsg("Slash Blades need to be on.");
    public static BCMSpell SLASH_BLADES = new BCMSpell(null, "slash_blades", BCMSpell.Type.SLASH_MAGIC, 0.6F, 50, true, 64, 0, true, (playerIn, modifier0, modifier1, playerCapability, manaIn) -> {

        LazyOptional<IPlayerHandler> playerInCap = playerIn.getCapability(PlayerProvider.CAPABILITY_PLAYER, null);
        IPlayerHandler player_cap = playerInCap.orElse(new PlayerCapability());

        if(playerIn.getItemInHand(Hand.MAIN_HAND).isEmpty()){
            if(!playerIn.getAttribute(Attributes.ATTACK_DAMAGE).hasModifier(getStrengthModifier(playerIn)))
                playerIn.getAttribute(Attributes.ATTACK_DAMAGE).addTransientModifier(getStrengthModifier(playerIn));
        }else {
            if(playerIn.getAttribute(Attributes.ATTACK_DAMAGE).hasModifier(getStrengthModifier(playerIn)))
                playerIn.getAttribute(Attributes.ATTACK_DAMAGE).removeModifier(getStrengthModifier(playerIn));
        }
    }).addStartEventListener((playerIn, manaIn) -> {
        playerIn.getPersistentData().putBoolean("slash_damage", true);
    }).addCancelEventListener(playerIn -> {
        if(playerIn.getAttribute(Attributes.ATTACK_DAMAGE).hasModifier(getStrengthModifier(playerIn)))
            playerIn.getAttribute(Attributes.ATTACK_DAMAGE).removeModifier(getStrengthModifier(playerIn));
    }).addAttackEventListener(((attacker, target) -> {

        if(attacker.getPersistentData().getBoolean("slash_damage")){
            if (attacker.getItemInHand(Hand.MAIN_HAND).isEmpty()) {
                LazyOptional<IPlayerHandler> playerInCap = attacker.getCapability(PlayerProvider.CAPABILITY_PLAYER, null);
                IPlayerHandler player_cap = playerInCap.orElse(new PlayerCapability());

                float f = (float) attacker.getAttributeValue(Attributes.ATTACK_DAMAGE);
                int level = ((player_cap.ReturnMagicLevel() / 100) * 15);
                float ratio = 1.0F - 1.0F / (float) (level + 1);
                float f3 = 1.0F + ratio * f;

                attacker.getPersistentData().putBoolean("slash_damage", false);
                for (LivingEntity livingentity : attacker.level.getEntitiesOfClass(LivingEntity.class, target.getBoundingBox().inflate(1.0D, 0.25D, 1.0D))) {
                    if (livingentity != attacker && livingentity != target && !attacker.isAlliedTo(livingentity) && (!(livingentity instanceof ArmorStandEntity) || !((ArmorStandEntity) livingentity).isMarker()) && attacker.distanceToSqr(livingentity) < 9.0D) {
                        livingentity.knockback(0.4F, (double) MathHelper.sin(attacker.yRot * ((float) Math.PI / 180F)), (double) (-MathHelper.cos(attacker.yRot * ((float) Math.PI / 180F))));
                        livingentity.hurt(DamageSource.playerAttack(attacker), f3);
                    }
                }
                attacker.level.playSound((PlayerEntity) null, attacker.getX(), attacker.getY(), attacker.getZ(), SoundEvents.PLAYER_ATTACK_SWEEP, attacker.getSoundSource(), 1.0F, 1.0F);
                attacker.sweepAttack();
                attacker.getPersistentData().putBoolean("slash_damage", true);
            }
        }
    }));

    public static void registerSpells(BCMRegistry.SpellRegistry spellRegistry, IBCMPlugin pluginIn) {

        spellRegistry.register(SLASH_BLADES.setPlugin(pluginIn));
        spellRegistry.register(DEATH_SCYTHE.setPlugin(pluginIn));
        spellRegistry.register(LUNATIC_SLASH.setPlugin(pluginIn));

    }

    private static AttributeModifier getStrengthModifier(PlayerEntity playerEntity) {
        LazyOptional<IPlayerHandler> playerInCap = playerEntity.getCapability(PlayerProvider.CAPABILITY_PLAYER, null);
        IPlayerHandler player_cap = playerInCap.orElse(new PlayerCapability());
        return new AttributeModifier(UUID.fromString("d8a6b472-db0a-48df-9011-303810669e5b"), "Slash Blades Damage Modifier",
                5 + ((float)player_cap.ReturnMagicLevel()/9), AttributeModifier.Operation.ADDITION);

    }

}
