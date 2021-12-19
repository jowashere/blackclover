package com.github.jowashere.blackclover.spells.lightning;

import com.github.jowashere.blackclover.api.IBCMPlugin;
import com.github.jowashere.blackclover.api.internal.AbstractSpell;
import com.github.jowashere.blackclover.entities.spells.lightning.ThunderOrbEntity;
import com.github.jowashere.blackclover.init.AttributeInit;
import com.github.jowashere.blackclover.spells.wind.ToweringTornado;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;

public class ThunderCrumblingOrb extends AbstractSpell {

    public static final AbstractSpell INSTANCE = new ThunderCrumblingOrb(null);

    public ThunderCrumblingOrb(IBCMPlugin plugin) {
        super(plugin, "thunder_orb", AttributeInit.LIGHTNING);

        this.setManaCost(20F);
        this.setCooldown(60);
        this.setUnlockLevel(10);
        this.setUV(32, 32);

        this.action = this::action;
        this.extraCheck = this::extraCheck;
        this.setCheckFailMsg("Thunder God Gloves need to be on.");

    }

    private void action(LivingEntity caster, float manaIn) {
        if (!caster.level.isClientSide) {
            ThunderOrbEntity entity = new ThunderOrbEntity(caster.level, caster, manaIn);
            entity.shootFromRotation(caster, caster.xRot, caster.yRot, 0.0F, 2.5F, 1F);
            caster.level.addFreshEntity(entity);
            caster.swing(Hand.MAIN_HAND, true);
        }
    }

    private boolean extraCheck(PlayerEntity caster){
        return caster.getPersistentData().getBoolean("blackclover_tg_gloves");
    }
}
