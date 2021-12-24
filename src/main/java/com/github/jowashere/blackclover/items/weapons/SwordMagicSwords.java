package com.github.jowashere.blackclover.items.weapons;

import com.github.jowashere.blackclover.api.internal.BCMAttribute;
import com.github.jowashere.blackclover.capabilities.player.IPlayerHandler;
import com.github.jowashere.blackclover.capabilities.player.PlayerCapability;
import com.github.jowashere.blackclover.capabilities.player.PlayerProvider;
import com.github.jowashere.blackclover.init.AttributeInit;
import com.github.jowashere.blackclover.init.GenericItemTier;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;
import net.minecraftforge.common.util.LazyOptional;

public class SwordMagicSwords extends SwordItem {

    /*private IItemPropertyGetter devilUnion = (itemStack, world, livingEntity) ->
    {
        float antiMagic = 0;

        antiMagic = itemStack.getOrCreateTag().getBoolean("antimagic") ? 1 : 0;

        return antiMagic;
    };*/

    public SwordMagicSwords(Properties properties, int damage, float speed) {
        super(GenericItemTier.SWORDMAGIC, damage, speed, properties);

    }

    @Override
    public void inventoryTick(ItemStack itemStack, World world, Entity entity, int slot, boolean isSelected)
    {
        super.inventoryTick(itemStack, world, entity, slot, isSelected);
        if(entity instanceof PlayerEntity){

            PlayerEntity player = (PlayerEntity) entity;
            LazyOptional<IPlayerHandler> playerc = player.getCapability(PlayerProvider.CAPABILITY_PLAYER, null);
            IPlayerHandler player_cap = playerc.orElse(new PlayerCapability());

            if(isSelected) {

                boolean isAnti = itemStack.getOrCreateTag().getBoolean("antimagic");
                BCMAttribute attribute = player_cap.ReturnMagicAttribute();

                if(attribute.equals(AttributeInit.ANTI_MAGIC) && !isAnti){
                    itemStack.getOrCreateTag().putBoolean("antimagic", true);
                    player.level.playSound(player, player.getX(), player.getY(), player.getZ(), SoundEvents.ENCHANTMENT_TABLE_USE, SoundCategory.PLAYERS, 1, 1);
                }else if(attribute.equals(AttributeInit.SWORD) && isAnti){
                    player.level.playSound(player, player.getX(), player.getY(), player.getZ(), SoundEvents.ENCHANTMENT_TABLE_USE, SoundCategory.PLAYERS, 1, 1);
                    itemStack.getOrCreateTag().putBoolean("antimagic", false);
                }
            }

            if((!isSelected || !player_cap.ReturnMagicAttribute().equals(AttributeInit.ANTI_MAGIC)) && itemStack.getOrCreateTag().getBoolean("black_divider")){
                itemStack.getOrCreateTag().putBoolean("black_divider", false);
            }

        }
    }

    @Override
    public boolean onEntitySwing(ItemStack stack, LivingEntity entity) {
        return super.onEntitySwing(stack, entity);
    }

    @Override
    public int getEnchantmentValue()
    {
        return 14;
    }

    @Override
    public boolean isBookEnchantable(ItemStack stack, ItemStack book) {
        return true;
    }

}
