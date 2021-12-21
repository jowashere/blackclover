package com.github.jowashere.blackclover.entities.goals.spells.lightning;

import com.github.jowashere.blackclover.api.internal.AbstractSpell;
import com.github.jowashere.blackclover.entities.mobs.BCEntity;
import com.github.jowashere.blackclover.networking.NetworkLoader;
import com.github.jowashere.blackclover.networking.packets.spells.PacketSpellNBTSync;
import com.github.jowashere.blackclover.spells.lightning.ThunderGodGloves;
import com.github.jowashere.blackclover.util.helpers.BCMHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraftforge.fml.network.PacketDistributor;

import java.util.UUID;

public class ThunderGodGlovesGoal extends Goal
{
    private BCEntity entity;
    private final AbstractSpell spell = ThunderGodGloves.INSTANCE;

    private static final AttributeModifier HAND_SPEED = new AttributeModifier(UUID.fromString("8f4c4640-64a4-46dd-bfee-c40195ef23f3"), "Thunder Gloves Hand Modifier", 3, AttributeModifier.Operation.ADDITION);

    public ThunderGodGlovesGoal(BCEntity entity)
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
            if(!this.entity.getAttribute(Attributes.ATTACK_DAMAGE).hasModifier(getThunderStrengthModifier(this.entity)))
                this.entity.getAttribute(Attributes.ATTACK_DAMAGE).addTransientModifier(getThunderStrengthModifier(this.entity));
        }else {
            if(this.entity.getAttribute(Attributes.ATTACK_DAMAGE).hasModifier(getThunderStrengthModifier(this.entity)))
                this.entity.getAttribute(Attributes.ATTACK_DAMAGE).removeModifier(getThunderStrengthModifier(this.entity));
        }

    }

    private static AttributeModifier getThunderStrengthModifier(LivingEntity caster) {

        int magicLevel = BCMHelper.getMagicLevel(caster);

        return new AttributeModifier(UUID.fromString("f34729f7-aed7-4ae7-a11e-ce78f454fba5"), "Thunder Gloves Strength Modifier",
                2 + ((float)magicLevel/15), AttributeModifier.Operation.ADDITION);

    }
}
