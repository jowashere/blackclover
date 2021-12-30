package com.github.jowashere.blackclover.spells.slash;

import com.github.jowashere.blackclover.api.internal.AbstractSpell;
import com.github.jowashere.blackclover.init.AttributeInit;
import com.github.jowashere.blackclover.networking.NetworkLoader;
import com.github.jowashere.blackclover.networking.packets.spells.PacketIntSpellNBTSync;
import com.github.jowashere.blackclover.util.helpers.BCMHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraftforge.fml.network.PacketDistributor;

public class ForwardThrust extends AbstractSpell
{
    public static final AbstractSpell INSTANCE = new ForwardThrust();


    public ForwardThrust()
    {
        super("foward_thrust", AttributeInit.SLASH);

        this.setManaCost(25F);
        this.setCooldown(80);
        this.setUnlockLevel(15);
        this.setUV(32, 48);

        this.action = this::action;
        this.extraCheck = this::extraCheck;
        this.setCheckFailMsg("Slash Blades need to be on.");
    }

    private void action(LivingEntity caster, float manaIn)
    {
        if (!caster.level.isClientSide)
        {
            int magicLevel = BCMHelper.getMagicLevel(caster);
            Vector3d speed = BCMHelper.Propulsion(caster, magicLevel , 3 );
            caster.setDeltaMovement(speed.x, 0.7, speed.z);
            caster.hurtMarked = true;
            caster.hasImpulse = true;
            caster.swing(Hand.MAIN_HAND, true);

            String nbtName = "forward_thrust_dmg";
            caster.getPersistentData().putInt(nbtName, 8 + (magicLevel/2));
            NetworkLoader.INSTANCE.send(PacketDistributor.ALL.noArg(), new PacketIntSpellNBTSync(caster.getId(), nbtName, 6));
        }
    }

    private boolean extraCheck(PlayerEntity caster){
        return caster.getPersistentData().getBoolean("blackclover_slash_blades");
    }
}
