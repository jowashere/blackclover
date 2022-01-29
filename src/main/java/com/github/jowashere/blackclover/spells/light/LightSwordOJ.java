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
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.awt.*;

import static com.ibm.icu.text.PluralRules.Operand.j;

public class LightSwordOJ extends AbstractSpell {

    public LightSwordOJ() {
        super("light_sword_oj", AttributeInit.LIGHT);

        this.setManaCost(25F);
        this.setCooldown(50);
        this.setUnlockLevel(5);
        this.setUV(80, 16);

        this.action = this::action;
    }

    private void action(LivingEntity caster, float manaIn) {
        if (!caster.level.isClientSide) {
            LightSwordOJEntity entity = new LightSwordOJEntity(caster.level, caster, manaIn);
            entity.shootFromRotation(caster, caster.xRot, caster.yRot, 0.0F, 3.6F, 1F);
            caster.level.addFreshEntity(entity);
            caster.swing(Hand.MAIN_HAND, true);

            LightParticleData lightParticleData = new LightParticleData(new Color(255, 255, 255),  0.5);
            IPacket<?> ipacket = new SSpawnParticlePacket(lightParticleData, true, caster.getX() + Math.random() / 2, caster.getY() + 1 + Math.random() , caster.getZ() + Math.random() / 2, 0, 0, 0, 0, 1);
            IPacket<?> ipacket2 = new SSpawnParticlePacket(lightParticleData, true, caster.getX() + Math.random() / 2, caster.getY() + 1 + Math.random() , caster.getZ() + Math.random() / 2, 0, 0, 0, 0, 1);
            IPacket<?> ipacket3 = new SSpawnParticlePacket(lightParticleData, true, caster.getX() + Math.random() / 2, caster.getY() + 1 + Math.random() , caster.getZ() + Math.random() / 2, 0, 0, 0, 0, 1);
            IPacket<?> ipacket4 = new SSpawnParticlePacket(lightParticleData, true, caster.getX() + Math.random() / 2, caster.getY() + 1 + Math.random() , caster.getZ() + Math.random() / 2, 0, 0, 0, 0, 1);
            IPacket<?> ipacket5 = new SSpawnParticlePacket(lightParticleData, true, caster.getX() + Math.random() / 2, caster.getY() + 1 + Math.random() , caster.getZ() + Math.random() / 2, 0, 0, 0, 0, 1);
            IPacket<?> ipacket6 = new SSpawnParticlePacket(lightParticleData, true, caster.getX() + Math.random() / 2, caster.getY()+ 1 + Math.random() , caster.getZ() + Math.random() / 2, 0, 0, 0, 0, 1);
            IPacket<?> ipacket7 = new SSpawnParticlePacket(lightParticleData, true, caster.getX() + Math.random() / 2, caster.getY() + 1 + Math.random() , caster.getZ() + Math.random() / 2, 0, 0, 0, 0, 1);

            for (int j = 0; j < caster.level.players().size(); ++j)
            {
                ServerPlayerEntity player = (ServerPlayerEntity) caster.level.players().get(j);
                BlockPos blockpos = new BlockPos(player.getX(), player.getY(), player.getZ());
                if (blockpos.closerThan(new Vector3d(caster.getX(), caster.getY(), caster.getZ()), 512))
                {
                    player.connection.send(ipacket);
                    player.connection.send(ipacket2);
                    player.connection.send(ipacket3);
                    player.connection.send(ipacket4);
                    player.connection.send(ipacket5);
                    player.connection.send(ipacket6);
                    player.connection.send(ipacket7);


                }
            }
        }
    }
}
