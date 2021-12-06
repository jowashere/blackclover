package com.github.jowashere.blackclover.entities.mobs.questers;

import com.github.jowashere.blackclover.capabilities.player.IPlayerHandler;
import com.github.jowashere.blackclover.capabilities.player.PlayerCapability;
import com.github.jowashere.blackclover.capabilities.player.PlayerProvider;
import com.github.jowashere.blackclover.init.ModAttributes;
import com.github.jowashere.blackclover.networking.NetworkLoader;
import com.github.jowashere.blackclover.networking.packets.PacketSetGrimoire;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.HurtByTargetGoal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fml.network.PacketDistributor;

public class GrimoireMagicianEntity extends CreatureEntity //TODO make it so it actually renders
{
    public GrimoireMagicianEntity(EntityType<? extends CreatureEntity> type, World world)
    {
        super(type, world);
    }

    @Override
    protected void registerGoals()
    {
        super.registerGoals();
        this.goalSelector.addGoal(0, new MeleeAttackGoal(this, 1, true));
        this.goalSelector.addGoal(8, new LookAtGoal(this, PlayerEntity.class, 4));
        this.goalSelector.addGoal(3, new LookRandomlyGoal(this));

        this.targetSelector.addGoal(3, new HurtByTargetGoal(this));
    }

    public static AttributeModifierMap.MutableAttribute setCustomAttributes()
    {
        return MobEntity.createMobAttributes()
                .add(Attributes.ATTACK_DAMAGE, 50)
                .add(Attributes.ARMOR, 20)
                .add(Attributes.MAX_HEALTH, 300)
                .add(Attributes.FOLLOW_RANGE, 100)
                .add(Attributes.MOVEMENT_SPEED, 3)
                .add(ModAttributes.FALL_RESISTANCE.get(), 50);
    }

    @Override
    public boolean removeWhenFarAway(double d)
    {
        return false;
    }

    @Override
    protected ActionResultType mobInteract(PlayerEntity player, Hand hand)
    {
        if (hand != Hand.MAIN_HAND)
            return ActionResultType.PASS;

        if (!player.level.isClientSide)
        {
            LazyOptional<IPlayerHandler> playerc = player.getCapability(PlayerProvider.CAPABILITY_PLAYER, null);
            IPlayerHandler playerStats = playerc.orElse(new PlayerCapability());

            int magicLevel = playerStats.getMagicLevel();

            if (playerStats.returnHasGrimoire())
            {
                player.displayClientMessage(new StringTextComponent("You already got your grimoire go away!"), false);

            }

            if (magicLevel >= 5)
            {
                player.displayClientMessage(new StringTextComponent("Here is your Grimoire! You seem mature enough"), false);
                playerStats.setHasGrimoire(true);
                NetworkLoader.INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) player), new PacketSetGrimoire(true, true, player.getId()));

            }
            else
            {
                player.displayClientMessage(new StringTextComponent("Come back when you're mature enough for a grimoire!"), false);
            }

        }
        return ActionResultType.PASS;
    }
}
