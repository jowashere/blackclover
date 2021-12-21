package com.github.jowashere.blackclover.entities.goals.spells.lightning;

import com.github.jowashere.blackclover.api.internal.AbstractSpell;
import com.github.jowashere.blackclover.capabilities.player.IPlayerHandler;
import com.github.jowashere.blackclover.capabilities.player.PlayerCapability;
import com.github.jowashere.blackclover.capabilities.player.PlayerProvider;
import com.github.jowashere.blackclover.entities.mobs.BCEntity;
import com.github.jowashere.blackclover.init.ModAttributes;
import com.github.jowashere.blackclover.networking.NetworkLoader;
import com.github.jowashere.blackclover.networking.packets.spells.PacketSpellNBTSync;
import com.github.jowashere.blackclover.spells.lightning.ThunderGodBoots;
import com.github.jowashere.blackclover.util.helpers.BCMHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fml.network.PacketDistributor;

import java.util.UUID;

public class ThunderGodBootsGoal extends Goal
{
    private BCEntity entity;
    private final AbstractSpell spell = ThunderGodBoots.INSTANCE;

    private static final AttributeModifier STEP_HEIGHT = new AttributeModifier(UUID.fromString("ad388521-c053-4a67-a0d9-ff57379a2c68"), "Step Height Multiplier", 1, AttributeModifier.Operation.ADDITION);

    public ThunderGodBootsGoal(BCEntity entity)
    {
        this.entity = entity;
        this.entity.addThreat(12);
    }

    @Override
    public boolean canUse()
    {
        boolean hasTarget = this.entity.getTarget() != null;

        this.toggle(hasTarget);
        return hasTarget;
    }

    public void toggle(boolean toggle)
    {
        String nbtName = spell.getCorrelatedPlugin().getPluginId() + "_" + spell.getName();
        this.entity.getPersistentData().putBoolean(nbtName, toggle);
        NetworkLoader.INSTANCE.send(PacketDistributor.ALL.noArg(), new PacketSpellNBTSync(this.entity.getId(), nbtName, toggle));

        if(toggle) {
            if(!this.entity.getAttribute(Attributes.MOVEMENT_SPEED).hasModifier(getThunderSpeedModifier(this.entity)))
                this.entity.getAttribute(Attributes.MOVEMENT_SPEED).addTransientModifier(getThunderSpeedModifier(this.entity));

            if(!this.entity.getAttribute(ModAttributes.JUMP_HEIGHT.get()).hasModifier(getThunderJumpModifier(this.entity)))
                this.entity.getAttribute(ModAttributes.JUMP_HEIGHT.get()).addTransientModifier(getThunderJumpModifier(this.entity));

            if(!this.entity.getAttribute(ModAttributes.STEP_HEIGHT.get()).hasModifier(STEP_HEIGHT))
                this.entity.getAttribute(ModAttributes.STEP_HEIGHT.get()).addTransientModifier(STEP_HEIGHT);

            this.entity.fallDistance = 0;

        } else {
            if(this.entity.getAttribute(Attributes.MOVEMENT_SPEED).hasModifier(getThunderSpeedModifier(this.entity)))
                this.entity.getAttribute(Attributes.MOVEMENT_SPEED).removeModifier(getThunderSpeedModifier(this.entity));

            if(this.entity.getAttribute(ModAttributes.JUMP_HEIGHT.get()).hasModifier(getThunderJumpModifier(this.entity)))
                this.entity.getAttribute(ModAttributes.JUMP_HEIGHT.get()).removeModifier(getThunderJumpModifier(this.entity));

            if(this.entity.getAttribute(ModAttributes.STEP_HEIGHT.get()).hasModifier(STEP_HEIGHT))
                this.entity.getAttribute(ModAttributes.STEP_HEIGHT.get()).removeModifier(STEP_HEIGHT);
        }
    }

    private static AttributeModifier getThunderSpeedModifier(LivingEntity caster) {

        int magicLevel = BCMHelper.getMagicLevel(caster);

        LazyOptional<IPlayerHandler> playerInCap = caster.getCapability(PlayerProvider.CAPABILITY_PLAYER, null);
        IPlayerHandler player_cap = playerInCap.orElse(new PlayerCapability());
        return new AttributeModifier(UUID.fromString("c6d81aa4-76af-4040-b7c2-ebe3c6616af1"), "Thunder Boots Speed Modifier",
                0.055 * magicLevel, AttributeModifier.Operation.MULTIPLY_BASE);
    }

    private static AttributeModifier getThunderJumpModifier(LivingEntity caster) {

        int magicLevel = BCMHelper.getMagicLevel(caster);

        return new AttributeModifier(UUID.fromString("f34729f7-aed7-4ae7-a11e-ce78f454fba5"), "Thunder Boots Jump Modifier",
                1.5 + (((float)magicLevel/100) * 3), AttributeModifier.Operation.ADDITION);

    }
}
