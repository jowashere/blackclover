package com.github.jowashere.blackclover.entities.goals.spells.lightning;

import com.github.jowashere.blackclover.api.Beapi;
import com.github.jowashere.blackclover.entities.goals.other.CooldownGoal;
import com.github.jowashere.blackclover.entities.mobs.BCEntity;
import com.github.jowashere.blackclover.init.AttributeInit;
import com.github.jowashere.blackclover.networking.NetworkLoader;
import com.github.jowashere.blackclover.networking.packets.spells.PacketIntSpellNBTSync;
import com.github.jowashere.blackclover.spells.SpellRegistry;
import com.github.jowashere.blackclover.util.helpers.BCMHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraftforge.fml.network.PacketDistributor;

public class ThunderFiendGoal extends CooldownGoal
{
    private BCEntity entity;

    public ThunderFiendGoal(BCEntity entity)
    {
        super(entity, SpellRegistry.THUNDER_FIEND);
        this.entity = entity;
        this.entity.addThreat(12);
    }

    @Override
    public boolean canUse()
    {
        if(!super.canUse())
            return false;

        if (this.entity.getTarget() == null)
            return false;

        if (!this.entity.getAttribute().equals(AttributeInit.LIGHTNING))
            return false;

        if(!this.entity.canSee(this.entity.getTarget()))
            return false;

        if(this.entity.getCurrentGoal() != null)
            return false;

        if (Beapi.randomWithRange(1, 10) <= 5)
            return false;

        this.execute(entity);
        return true;
    }

    @Override
    public void onGoalEnd()
    {
        super.onGoalEnd();
        this.entity.setCurrentGoal(null);
        this.entity.setPreviousGoal(this);
    }

    public void execute(LivingEntity caster)
    {
        super.execute();

        Vector3d speed = BCMHelper.Propulsion(caster, 3, 3);
        caster.setDeltaMovement(speed.x, 0.3, speed.z);
        caster.hurtMarked = true;
        caster.hasImpulse = true;
        caster.swing(Hand.MAIN_HAND, true);

        String nbtName = "thunder_fiend_dmg";
        caster.getPersistentData().putInt(nbtName, 8);
        NetworkLoader.INSTANCE.send(PacketDistributor.ALL.noArg(), new PacketIntSpellNBTSync(caster.getId(), nbtName, 6));

        this.entity.setCurrentGoal(this);
        entity.applySpellCD(spell);
    }
}
