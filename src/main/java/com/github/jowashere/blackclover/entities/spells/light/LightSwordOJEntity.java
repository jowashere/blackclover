package com.github.jowashere.blackclover.entities.spells.light;

import com.github.jowashere.blackclover.api.internal.entities.spells.AbstractLightProjectileEntity;
import com.github.jowashere.blackclover.init.EntityInit;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.network.IPacket;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class LightSwordOJEntity extends AbstractLightProjectileEntity {

    public LightSwordOJEntity(EntityType<? extends LightSwordOJEntity> type, World worldIn) {
        super(type, worldIn);
    }

    public LightSwordOJEntity(World worldIn, LivingEntity throwerIn, float manaIn) {
        super(EntityInit.LIGHT_SWORD_OJ.get(), throwerIn, worldIn, manaIn);
        this.setDamageTier(2);
        this.setBaseDamage(4);
        this.removeOnGround = false;
    }

    @Override
    protected float getGravity() {
        return 0F;
    }

    @Override
    public String getAffiliatedSpellName() {
        return "light_sword_oj";
    }

    @Override
    public IPacket<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    protected Item getDefaultItem() {
        return null;
    }
}
