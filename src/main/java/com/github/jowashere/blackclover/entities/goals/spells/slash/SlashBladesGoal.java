package com.github.jowashere.blackclover.entities.goals.spells.slash;

import com.github.jowashere.blackclover.api.internal.AbstractSpell;
import com.github.jowashere.blackclover.entities.mobs.BCEntity;
import com.github.jowashere.blackclover.networking.NetworkLoader;
import com.github.jowashere.blackclover.networking.packets.spells.PacketSpellNBTSync;
import com.github.jowashere.blackclover.spells.slash.SlashBlades;
import com.github.jowashere.blackclover.util.helpers.BCMHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraftforge.fml.network.PacketDistributor;

import java.util.UUID;

public class SlashBladesGoal extends Goal
{
    private BCEntity entity;
    private final AbstractSpell spell = SlashBlades.INSTANCE;

    public SlashBladesGoal(BCEntity entity)
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
            if(!this.entity.getAttribute(Attributes.ATTACK_DAMAGE).hasModifier(getStrengthModifier(this.entity)))
                this.entity.getAttribute(Attributes.ATTACK_DAMAGE).addTransientModifier(getStrengthModifier(this.entity));
        }else {
            if(this.entity.getAttribute(Attributes.ATTACK_DAMAGE).hasModifier(getStrengthModifier(this.entity)))
                this.entity.getAttribute(Attributes.ATTACK_DAMAGE).removeModifier(getStrengthModifier(this.entity));
        }

    }

    private static AttributeModifier getStrengthModifier(LivingEntity caster) {

        int magicLevel = BCMHelper.getMagicLevel(caster);

        return new AttributeModifier(UUID.fromString("d8a6b472-db0a-48df-9011-303810669e5b"), "Slash Blades Damage Modifier",
                3 + ((float)magicLevel/11), AttributeModifier.Operation.ADDITION);

    }
}
