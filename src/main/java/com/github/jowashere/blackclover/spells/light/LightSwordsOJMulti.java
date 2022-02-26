package com.github.jowashere.blackclover.spells.light;

import com.github.jowashere.blackclover.api.internal.AbstractSpell;
import com.github.jowashere.blackclover.entities.spells.light.LightSwordOJEntity;
import com.github.jowashere.blackclover.init.AttributeInit;
import com.github.jowashere.blackclover.particles.light.LightParticleData;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.IPacket;
import net.minecraft.network.play.server.SSpawnParticlePacket;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;

import java.awt.*;

public class LightSwordsOJMulti extends AbstractSpell {

    public LightSwordsOJMulti() {
        super("light_swords_oj", AttributeInit.LIGHT);

        this.setManaCost(75F);
        this.setCooldown(110);
        this.setUnlockLevel(10);
        this.setUV(80, 80);

        this.action = this::action;

    }

    private void action(LivingEntity caster, float manaIn) {
        if (!caster.level.isClientSide) {
            for(int i = 0; i < 15; i++) {
                LightSwordOJEntity entity = new LightSwordOJEntity(caster.level, caster, manaIn);
                entity.shootFromRotation(caster, caster.xRot, caster.yRot, 0.0F, 3.6F, 1F);
                caster.level.addFreshEntity(entity);
                caster.swing(Hand.MAIN_HAND, true);
                LightParticleData lightParticleData = new LightParticleData(new Color(255, 255, 255),  0.5);

                Vector3d vec3 = caster.getLookAngle();

                IPacket<?>[] packets = new IPacket[7];
                for (int a = 0; a < 7; ++a)
                {
                    double random = Math.random() * (0.8 - 0.1) + 0.1;
                    packets[a] = new SSpawnParticlePacket(lightParticleData, true, caster.getX() + vec3.x + Math.random(), caster.getY() + vec3.y + 1 + Math.random(), caster.getZ() + vec3.z + Math.random(), 0, 0, 0, 0, 1);
                    for (int j = 0; j < caster.level.players().size(); ++j)
                    {
                        ServerPlayerEntity player = (ServerPlayerEntity) caster.level.players().get(j);
                        BlockPos blockpos = new BlockPos(player.getX(), player.getY(), player.getZ());
                        if (blockpos.closerThan(new Vector3d(caster.getX(), caster.getY(), caster.getZ()), 512))
                        {
                            player.connection.send(packets[a]);
                        }
                    }
                }
            }
        }
    }

}
