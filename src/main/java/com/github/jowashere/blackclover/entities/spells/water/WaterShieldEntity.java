package com.github.jowashere.blackclover.entities.spells.water;

import com.github.jowashere.blackclover.api.internal.entities.spells.AbstractAntiMagicProjectileEntity;
import com.github.jowashere.blackclover.api.internal.entities.spells.AbstractSpellProjectileEntity;
import com.github.jowashere.blackclover.init.EntityInit;
import com.github.jowashere.blackclover.util.helpers.BCMHelper;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MoverType;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.NetworkHooks;

import java.util.List;
import java.util.UUID;

public class WaterShieldEntity extends Entity {

    private LivingEntity owner;
    private UUID ownerUUID;
    private float manaIn;
    private final double speedLimit = 1;
    private final double pullSpeed = 0.7;

    public WaterShieldEntity(EntityType<? extends WaterShieldEntity> type, World worldIn)
    {
        super(type, worldIn);
    }

    public WaterShieldEntity(World worldIn, LivingEntity owner, float manaIn) {
        super(EntityInit.WATER_SHIELD.get(), worldIn);

        this.ownerUUID = owner.getUUID();
        this.manaIn = manaIn;

    }


    public void setOwner(UUID uuid)
    {
        this.ownerUUID = uuid;
        this.owner = (LivingEntity) ((ServerWorld) this.level).getEntity(this.ownerUUID);
    }

    @Override
    public boolean isColliding(BlockPos pPos, BlockState pState) {
        return super.isColliding(pPos, pState);
    }

    @Override
    public void tick()
    {
        super.tick();

        if(this.level.isClientSide)
            return;

        if (this.ownerUUID == null)
        {
            this.remove();
            return;
        }

        if(this.owner == null)
        {
            this.owner = (LivingEntity) ((ServerWorld) this.level).getEntity(this.ownerUUID);
            return;
        }

        if(Math.round(this.getY()) > Math.round(owner.getY() + 3))
            this.move(MoverType.SELF, new Vector3d(0, -0.1, 0));
        else if (Math.round(this.getY()) < Math.round(owner.getY() + 3))
            this.move(MoverType.SELF, new Vector3d(0, 0.1, 0));


        if(this.owner.getHealth() <= 0)
        {
            this.remove();
        }

        Vector3d lookingAngle = this.owner.getLookAngle();

        Vector3d lookAngle = this.owner.getLookAngle();

        Vector3d shieldAnglePos = new Vector3d(this.owner.getX() - lookAngle.x, this.owner.getY(), this.owner.getZ() - lookAngle.z);



        double posX = this.getX() - this.owner.getX() - 2 * lookingAngle.x;
        double posZ = this.getZ() - this.owner.getZ() - 2 * lookingAngle.z;

        if(posX > 0 && posX > this.speedLimit)
            posX = this.speedLimit;
        else if(posX < 0 && posX < -this.speedLimit)
            posX = -this.speedLimit;

        if(posZ > 0 && posZ > this.speedLimit)
            posZ = this.speedLimit;
        else if(posZ < 0 && posZ < -this.speedLimit)
            posZ = -this.speedLimit;

        this.move(MoverType.SELF, new Vector3d(-posX, 0, -posZ));

        List<AbstractSpellProjectileEntity> spellProjectiles = BCMHelper.GetEntitiesNear(this.getOnPos(), this.level, 3, AbstractSpellProjectileEntity.class);

        spellProjectiles.forEach(spellProjectile -> {

            if(spellProjectile.getOwner() == this.owner)
                return;

            if(spellProjectile instanceof AbstractAntiMagicProjectileEntity)
                return;

            if(spellProjectile.getManaIn() > this.manaIn * 1.5)
                return;

            double spellPosX = spellProjectile.getX() - this.getX();
            double spellPosZ = spellProjectile.getZ() - this.getZ();
            double spellPosY = spellProjectile.getZ() - this.getZ();

            if(Math.round(spellProjectile.getY()) > Math.round(this.getY()))
                spellProjectile.move(MoverType.SELF, new Vector3d(0, -0.7, 0));
            else if (Math.round(spellProjectile.getY()) < Math.round(this.getY()))
                spellProjectile.move(MoverType.SELF, new Vector3d(0, 0.7, 0));

            if(spellPosX > 0 && spellPosX > this.pullSpeed)
                spellPosX = this.pullSpeed;
            else if(spellPosX < 0 && spellPosX < -this.pullSpeed)
                spellPosX = -this.pullSpeed;

            if(spellPosZ > 0 && spellPosZ > this.pullSpeed)
                spellPosZ = this.pullSpeed;
            else if(spellPosZ < 0 && spellPosZ < -this.pullSpeed)
                spellPosZ = -this.pullSpeed;

            spellProjectile.move(MoverType.SELF, new Vector3d(-spellPosX, -spellPosY, -spellPosZ));

            boolean xMatches = Math.round(spellProjectile.getX()) == Math.round(this.getX());
            boolean yMatches = Math.round(spellProjectile.getY()) == Math.round(this.getY());
            boolean zMatches = Math.round(spellProjectile.getZ()) == Math.round(this.getZ());

            if(xMatches && yMatches && zMatches){
                spellProjectile.remove();
            }

        });

    }

    @Override
    protected void defineSynchedData() {}

    @Override
    protected void addAdditionalSaveData(CompoundNBT compound)
    {
        compound.putString("OwnerUUID", this.ownerUUID.toString());
    }

    @Override
    protected void readAdditionalSaveData(CompoundNBT compound)
    {
        if (compound.contains("OwnerUUID", 8))
            this.setOwner(UUID.fromString(compound.getString("OwnerUUID")));
    }

    @Override
    public IPacket<?> getAddEntityPacket()
    {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
