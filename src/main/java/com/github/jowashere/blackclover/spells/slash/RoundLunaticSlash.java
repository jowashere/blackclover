package com.github.jowashere.blackclover.spells.slash;

import com.github.jowashere.blackclover.api.internal.AbstractSpell;
import com.github.jowashere.blackclover.init.AttributeInit;
import com.github.jowashere.blackclover.util.helpers.BCMHelper;
import com.github.jowashere.blackclover.util.helpers.SpellHelper;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.dispenser.IPosition;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import java.util.List;
import java.util.Optional;

public class RoundLunaticSlash extends AbstractSpell
{
    public static final AbstractSpell INSTANCE = new RoundLunaticSlash();

    public RoundLunaticSlash()
    {
        super("round_lunatic_slash", AttributeInit.SLASH);

        this.setManaCost(300F);
        this.setCooldown(20);
        this.setUnlockLevel(30);
        this.setUV(64, 32);

        this.action = this::action;
        this.extraCheck = this::extraCheck;
        this.setCheckFailMsg("Slash Blades need to be on.");
    }

    //Checks entities in a radius
    //Damages them + particles if they are
    private void action(LivingEntity caster, float manaIn)
    {
        if (!caster.level.isClientSide)
        {
            int magicLevel = BCMHelper.getMagicLevel(caster);

            World world = caster.level;
            BlockPos casterPos = caster.getEntity().blockPosition();

            //TODO make it so blocks get destroyed
            //for (int x = casterPos.getX(); x <= casterPos.getX() + 3 + (BCMHelper.getMagicLevel(caster) / 4); x++)
            //{
              //  for (int z = casterPos.getZ(); z <= casterPos.getZ() + 3 + (BCMHelper.getMagicLevel(caster) / 4); z++)
                //{
                  //  BlockPos pos = new BlockPos(z, casterPos.getY(), casterPos.getZ());
                    //world.setBlock(pos, Blocks.AIR.defaultBlockState(), 2);
            //    }
            //}



            //Checks radius and damages enemies
            List<Entity> entities = BCMHelper.GetEntitiesNear(caster.blockPosition(), caster.level, (3 + (int) (BCMHelper.getMagicLevel(caster) / 4)));
            if (entities.contains(caster))
            {
                entities.remove(caster);

                entities.forEach(entity -> {
                    BCMHelper.doSpellDamage(caster, (LivingEntity) entity, SpellHelper.spellDamageCalc(magicLevel, 2, 3));

                    ((ServerWorld) caster.level).sendParticles(ParticleTypes.SWEEP_ATTACK, entity.getX(), entity.getY(),
                            entity.getZ(), (int) 10, 3, 3, 3, 0.1);
                });
            }

        }
    }


    private boolean extraCheck(PlayerEntity caster){
        return caster.getPersistentData().getBoolean("blackclover_slash_blades");
    }}
