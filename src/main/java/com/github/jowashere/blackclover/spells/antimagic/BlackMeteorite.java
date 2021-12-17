package com.github.jowashere.blackclover.spells.antimagic;

import com.github.jowashere.blackclover.api.IBCMPlugin;
import com.github.jowashere.blackclover.api.internal.AbstractSpell;
import com.github.jowashere.blackclover.init.AttributeInit;
import com.github.jowashere.blackclover.init.ItemInit;
import com.github.jowashere.blackclover.networking.NetworkLoader;
import com.github.jowashere.blackclover.networking.packets.spells.PacketIntSpellNBTSync;
import com.github.jowashere.blackclover.util.helpers.BCMHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraftforge.fml.network.PacketDistributor;

public class BlackMeteorite extends AbstractSpell {

    public BlackMeteorite(IBCMPlugin plugin) {
        super(plugin, "black_meteorite", AttributeInit.ANTI_MAGIC);

        this.setSkillSpell(true);
        this.setManaCost(85F);
        this.setCooldown(700);
        this.setUnlockLevel(35);
        this.setUV(0, 64);

        this.action = this::action;
        this.extraCheck = this::extraCheck;
        this.setCheckFailMsg("Need to be in Black Mode and holding Demon Dweller/Slayer Sword!");

    }

    private void action(LivingEntity caster, float manaIn) {
        if (!caster.level.isClientSide) {
            Vector3d speed = BCMHelper.Propulsion(caster, 7, 7, 7);
            caster.setDeltaMovement(speed.x, speed.y, speed.z);
            caster.hurtMarked = true;
            caster.hasImpulse = true;
            caster.swing(Hand.MAIN_HAND, true);

            String nbtName = "black_meteorite_dmg";
            caster.getPersistentData().putInt(nbtName, 10);
            NetworkLoader.INSTANCE.send(PacketDistributor.ALL.noArg(), new PacketIntSpellNBTSync(caster.getId(), nbtName, 10));

            if(caster instanceof PlayerEntity)
                ((PlayerEntity)caster).startFallFlying();
        }
    }

    private boolean extraCheck(PlayerEntity caster){
        ItemStack mainItem = caster.getItemInHand(Hand.MAIN_HAND);
        return (mainItem.getItem().equals(ItemInit.DEMON_SLAYER.get()) || mainItem.getItem().equals(ItemInit.DEMON_DWELLER.get())) && mainItem.getOrCreateTag().getBoolean("antimagic") && caster.getPersistentData().getBoolean("blackclover_black_mode");
    }
}
