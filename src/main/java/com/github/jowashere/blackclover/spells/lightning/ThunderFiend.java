package com.github.jowashere.blackclover.spells.lightning;

import com.github.jowashere.blackclover.api.IBCMPlugin;
import com.github.jowashere.blackclover.api.internal.AbstractSpell;
import com.github.jowashere.blackclover.init.AttributeInit;
import com.github.jowashere.blackclover.networking.NetworkLoader;
import com.github.jowashere.blackclover.networking.packets.spells.PacketIntSpellNBTSync;
import com.github.jowashere.blackclover.spells.wind.ToweringTornado;
import com.github.jowashere.blackclover.util.helpers.BCMHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraftforge.fml.network.PacketDistributor;

public class ThunderFiend extends AbstractSpell {

    public static final AbstractSpell INSTANCE = new ThunderFiend(null);

    public ThunderFiend(IBCMPlugin plugin) {


        super(plugin, "thunder_fiend", AttributeInit.LIGHTNING);

        this.setManaCost(20F);
        this.setCooldown(60);
        this.setUnlockLevel(20);
        this.setUV(32, 48);

        this.action = this::action;
        this.extraCheck = this::extraCheck;
        this.setCheckFailMsg("Thunder God Boots need to be on.");

    }

    private void action(LivingEntity caster, float manaIn) {
        if (!caster.level.isClientSide) {
            Vector3d speed = BCMHelper.Propulsion(caster, 3, 3);
            caster.setDeltaMovement(speed.x, 0.3, speed.z);
            caster.hurtMarked = true;
            caster.hasImpulse = true;
            caster.swing(Hand.MAIN_HAND, true);

            String nbtName = "thunder_fiend_dmg";
            caster.getPersistentData().putInt(nbtName, 8);
            NetworkLoader.INSTANCE.send(PacketDistributor.ALL.noArg(), new PacketIntSpellNBTSync(caster.getId(), nbtName, 6));
        }
    }

    private boolean extraCheck(PlayerEntity caster){
        return caster.getPersistentData().getBoolean("blackclover_tg_boots");
    }
}
