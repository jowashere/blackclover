package com.github.jowashere.blackclover.init.spells;

import com.github.jowashere.blackclover.api.BCMRegistry;
import com.github.jowashere.blackclover.api.IBCMPlugin;
import com.github.jowashere.blackclover.api.internal.BCMSpell;
import com.github.jowashere.blackclover.capabilities.player.IPlayerHandler;
import com.github.jowashere.blackclover.capabilities.player.PlayerCapability;
import com.github.jowashere.blackclover.capabilities.player.PlayerProvider;
import com.github.jowashere.blackclover.entities.spells.lightning.ThunderOrbEntity;
import com.github.jowashere.blackclover.init.ModAttributes;
import com.github.jowashere.blackclover.networking.NetworkLoader;
import com.github.jowashere.blackclover.networking.packets.spells.PacketIntSpellNBTSync;
import com.github.jowashere.blackclover.util.helpers.BCMHelper;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fml.network.PacketDistributor;

import java.util.UUID;

public class LightningSpells {

    private static final AttributeModifier STEP_HEIGHT = new AttributeModifier(UUID.fromString("ad388521-c053-4a67-a0d9-ff57379a2c68"), "Step Height Multiplier", 1, AttributeModifier.Operation.ADDITION);

    public static void registerSpells(BCMRegistry.SpellRegistry spellRegistry, IBCMPlugin pluginIn) {
        spellRegistry.register(new BCMSpell(pluginIn, "tg_gloves", BCMSpell.Type.LIGHTNING_MAGIC, 0.25F, 40, false, 32, 0, true, (playerIn, modifier0, modifier1, playerCapability, manaIn) -> {

            LazyOptional<IPlayerHandler> playerInCap = playerIn.getCapability(PlayerProvider.CAPABILITY_PLAYER, null);
            IPlayerHandler player_cap = playerInCap.orElse(new PlayerCapability());


        }).addStartEventListener((playerIn, manaIn) -> {
            if(!playerIn.getAttribute(Attributes.ATTACK_DAMAGE).hasModifier(getThunderStrengthModifier(playerIn)))
                playerIn.getAttribute(Attributes.ATTACK_DAMAGE).addTransientModifier(getThunderStrengthModifier(playerIn));

            if(!playerIn.getAttribute(Attributes.ATTACK_SPEED).hasModifier(getThunderHandSpeedModifier(playerIn)))
                playerIn.getAttribute(Attributes.ATTACK_SPEED).addTransientModifier(getThunderHandSpeedModifier(playerIn));
        }).addCancelEventListener(playerIn -> {
            if(playerIn.getAttribute(Attributes.ATTACK_DAMAGE).hasModifier(getThunderStrengthModifier(playerIn)))
                playerIn.getAttribute(Attributes.ATTACK_DAMAGE).removeModifier(getThunderStrengthModifier(playerIn));

            if(playerIn.getAttribute(Attributes.ATTACK_SPEED).hasModifier(getThunderHandSpeedModifier(playerIn)))
                playerIn.getAttribute(Attributes.ATTACK_SPEED).removeModifier(getThunderHandSpeedModifier(playerIn));

        }).setUnlockLevel(5));

        spellRegistry.register(new BCMSpell(pluginIn, "tg_boots", BCMSpell.Type.LIGHTNING_MAGIC, 0.25F, 40, true, 32, 16, true, (playerIn, modifier0, modifier1, playerCapability, manaIn) -> {

            LazyOptional<IPlayerHandler> playerInCap = playerIn.getCapability(PlayerProvider.CAPABILITY_PLAYER, null);
            IPlayerHandler player_cap = playerInCap.orElse(new PlayerCapability());

            playerIn.fallDistance = 0;

        }).addStartEventListener((playerIn, manaIn) -> {
            if(!playerIn.getAttribute(Attributes.MOVEMENT_SPEED).hasModifier(getThunderSpeedModifier(playerIn)))
                playerIn.getAttribute(Attributes.MOVEMENT_SPEED).addTransientModifier(getThunderSpeedModifier(playerIn));

            if(!playerIn.getAttribute(ModAttributes.JUMP_HEIGHT.get()).hasModifier(getThunderJumpModifier(playerIn)))
                playerIn.getAttribute(ModAttributes.JUMP_HEIGHT.get()).addTransientModifier(getThunderJumpModifier(playerIn));

            if(!playerIn.getAttribute(ModAttributes.STEP_HEIGHT.get()).hasModifier(STEP_HEIGHT))
                playerIn.getAttribute(ModAttributes.STEP_HEIGHT.get()).addTransientModifier(STEP_HEIGHT);

        }).addCancelEventListener(playerIn -> {
            if(playerIn.getAttribute(Attributes.MOVEMENT_SPEED).hasModifier(getThunderSpeedModifier(playerIn)))
                playerIn.getAttribute(Attributes.MOVEMENT_SPEED).removeModifier(getThunderSpeedModifier(playerIn));

            if(playerIn.getAttribute(ModAttributes.JUMP_HEIGHT.get()).hasModifier(getThunderJumpModifier(playerIn)))
                playerIn.getAttribute(ModAttributes.JUMP_HEIGHT.get()).removeModifier(getThunderJumpModifier(playerIn));

            if(playerIn.getAttribute(ModAttributes.STEP_HEIGHT.get()).hasModifier(STEP_HEIGHT))
                playerIn.getAttribute(ModAttributes.STEP_HEIGHT.get()).removeModifier(STEP_HEIGHT);

        }).setUnlockLevel(1));

        spellRegistry.register(new BCMSpell(pluginIn, "thunder_orb", BCMSpell.Type.LIGHTNING_MAGIC, 20F, 60, false, 32, 32, false, ((playerIn, modifier0, modifier1, playerCapability, manaIn) -> {

            LazyOptional<IPlayerHandler> playerInCap = playerIn.getCapability(PlayerProvider.CAPABILITY_PLAYER, null);
            IPlayerHandler player_cap = playerInCap.orElse(new PlayerCapability());

            if (!playerIn.level.isClientSide) {
                ThunderOrbEntity entity = new ThunderOrbEntity(playerIn.level, playerIn, manaIn);
                entity.shootFromRotation(playerIn, playerIn.xRot, playerIn.yRot, 0.0F, 2.5F, 1F);
                playerIn.level.addFreshEntity(entity);
                playerIn.swing(Hand.MAIN_HAND, true);
            }
        })).setExtraSpellChecks((playerIn) -> playerIn.getPersistentData().getBoolean("blackclover_tg_gloves")).setCheckFailMsg("Thunder God Gloves need to be on.")
                .setUnlockLevel(10));

        spellRegistry.register(new BCMSpell(pluginIn, "thunder_fiend", BCMSpell.Type.LIGHTNING_MAGIC, 20F, 60, false, 32, 48, false, ((playerIn, modifier0, modifier1, playerCapability, manaIn) -> {

            LazyOptional<IPlayerHandler> playerInCap = playerIn.getCapability(PlayerProvider.CAPABILITY_PLAYER, null);
            IPlayerHandler player_cap = playerInCap.orElse(new PlayerCapability());

            if (!playerIn.level.isClientSide) {
                Vector3d speed = BCMHelper.Propulsion(playerIn, 3, 3);
                playerIn.setDeltaMovement(speed.x, 0.3, speed.z);
                playerIn.hurtMarked = true;
                playerIn.hasImpulse = true;
                playerIn.swing(Hand.MAIN_HAND, true);

                String nbtName = "thunder_fiend_dmg";
                playerIn.getPersistentData().putInt(nbtName, 8);
                NetworkLoader.INSTANCE.send(PacketDistributor.ALL.noArg(), new PacketIntSpellNBTSync(playerIn.getId(), nbtName, 6));

            }
        })).setExtraSpellChecks((playerIn) -> playerIn.getPersistentData().getBoolean("blackclover_tg_boots")).setCheckFailMsg("Thunder God Boots need to be on.")
        .setUnlockLevel(20));

    }

    private static AttributeModifier getThunderHandSpeedModifier(PlayerEntity playerEntity) {
        LazyOptional<IPlayerHandler> playerInCap = playerEntity.getCapability(PlayerProvider.CAPABILITY_PLAYER, null);
        IPlayerHandler player_cap = playerInCap.orElse(new PlayerCapability());
        return new AttributeModifier(UUID.fromString("8f4c4640-64a4-46dd-bfee-c40195ef23f3"), "Thunder Gloves Hand Modifier"
                , 3, AttributeModifier.Operation.ADDITION);
    }

    private static AttributeModifier getThunderSpeedModifier(PlayerEntity playerEntity) {
        LazyOptional<IPlayerHandler> playerInCap = playerEntity.getCapability(PlayerProvider.CAPABILITY_PLAYER, null);
        IPlayerHandler player_cap = playerInCap.orElse(new PlayerCapability());
        return new AttributeModifier(UUID.fromString("c6d81aa4-76af-4040-b7c2-ebe3c6616af1"), "Thunder Boots Speed Modifier",
                0.055 * player_cap.ReturnMagicLevel(), AttributeModifier.Operation.MULTIPLY_BASE);
    }

    private static AttributeModifier getThunderStrengthModifier(PlayerEntity playerEntity) {
        LazyOptional<IPlayerHandler> playerInCap = playerEntity.getCapability(PlayerProvider.CAPABILITY_PLAYER, null);
        IPlayerHandler player_cap = playerInCap.orElse(new PlayerCapability());
        return new AttributeModifier(UUID.fromString("f34729f7-aed7-4ae7-a11e-ce78f454fba5"), "Thunder Gloves Strength Modifier",
                4 + ((float)player_cap.ReturnMagicLevel()/10), AttributeModifier.Operation.ADDITION);

    }

    private static AttributeModifier getThunderJumpModifier(PlayerEntity playerEntity) {
        LazyOptional<IPlayerHandler> playerInCap = playerEntity.getCapability(PlayerProvider.CAPABILITY_PLAYER, null);
        IPlayerHandler player_cap = playerInCap.orElse(new PlayerCapability());
        return new AttributeModifier(UUID.fromString("f34729f7-aed7-4ae7-a11e-ce78f454fba5"), "Thunder Boots Jump Modifier",
                1.5 + (((float)player_cap.ReturnMagicLevel()/100) * 3), AttributeModifier.Operation.ADDITION);

    }

}
