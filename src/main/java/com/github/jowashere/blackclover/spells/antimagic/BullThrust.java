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

public class BullThrust extends AbstractSpell {

    public BullThrust(IBCMPlugin plugin) {
        super(plugin, "bull_thrust", AttributeInit.ANTI_MAGIC);

        this.setManaCost(15F);
        this.setCooldown(100);
        this.setUnlockLevel(5);
        this.setUV(0, 0);

        this.action = this::action;
        this.extraCheck = this::extraCheck;
        this.setCheckFailMsg("Demon Dweller/Slayer Sword needs to be in hand!");

    }

    private void action(LivingEntity caster, float manaIn) {
        if (!caster.level.isClientSide) {
            Vector3d speed = BCMHelper.Propulsion(caster, 2.5, 2.5);
            caster.setDeltaMovement(speed.x, 0.3, speed.z);
            caster.hurtMarked = true;
            caster.hasImpulse = true;
            caster.swing(Hand.MAIN_HAND, true);

            String nbtName = "bull_thrust_dmg";
            caster.getPersistentData().putInt(nbtName, 6);
            NetworkLoader.INSTANCE.send(PacketDistributor.ALL.noArg(), new PacketIntSpellNBTSync(caster.getId(), nbtName, 6));
        }
    }

    private boolean extraCheck(PlayerEntity caster){
        ItemStack mainItem = caster.getItemInHand(Hand.MAIN_HAND);
        return (mainItem.getItem().equals(ItemInit.DEMON_SLAYER.get()) || mainItem.getItem().equals(ItemInit.DEMON_DWELLER.get())) && mainItem.getOrCreateTag().getBoolean("antimagic");
    }
}
