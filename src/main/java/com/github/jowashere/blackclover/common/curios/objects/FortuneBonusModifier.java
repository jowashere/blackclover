package com.github.jowashere.blackclover.common.curios.objects;

import com.github.jowashere.blackclover.api.curios.CuriosApi;
import com.github.jowashere.blackclover.api.curios.type.capability.ICuriosItemHandler;
import com.google.gson.JsonObject;
import net.minecraft.block.BlockState;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootParameterSets;
import net.minecraft.loot.LootParameters;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.common.loot.LootModifier;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Map;

public class FortuneBonusModifier extends LootModifier {

  protected FortuneBonusModifier(ILootCondition[] conditions) {
    super(conditions);
  }

  @Nonnull
  @Override
  protected List<ItemStack> doApply(List<ItemStack> generatedLoot, LootContext context) {
    ItemStack tool = context.getParamOrNull(LootParameters.TOOL);

    if (tool == null || tool.hasTag() && tool.getTag() != null &&
        tool.getTag().getBoolean("HasCuriosFortuneBonus")) {
      return generatedLoot;
    }
    Entity entity = context.getParamOrNull(LootParameters.THIS_ENTITY);
    BlockState blockState = context.getParamOrNull(LootParameters.BLOCK_STATE);

    if (blockState == null || !(entity instanceof LivingEntity)) {
      return generatedLoot;
    }
    LivingEntity player = (LivingEntity) entity;
    int totalFortuneBonus = CuriosApi.getCuriosHelper().getCuriosHandler(player)
        .map(ICuriosItemHandler::getFortuneBonus).orElse(0);

    if (totalFortuneBonus <= 0) {
      return generatedLoot;
    }
    ItemStack fakeTool = tool.isEmpty() ? new ItemStack(Items.BARRIER) : tool.copy();
    fakeTool.getOrCreateTag().putBoolean("HasCuriosFortuneBonus", true);

    Map<Enchantment, Integer> enchantments = EnchantmentHelper.getEnchantments(fakeTool);
    enchantments.put(Enchantments.BLOCK_FORTUNE,
        EnchantmentHelper.getItemEnchantmentLevel(Enchantments.BLOCK_FORTUNE, fakeTool) + totalFortuneBonus);
    EnchantmentHelper.setEnchantments(enchantments, fakeTool);
    LootContext.Builder builder = new LootContext.Builder(context);
    builder.withParameter(LootParameters.TOOL, fakeTool);
    LootContext newContext = builder.create(LootParameterSets.BLOCK);
    LootTable lootTable = context.getLevel().getServer().getLootTables()
        .get(blockState.getBlock().getLootTable());
    return lootTable.getRandomItems(newContext);
  }

  public static class Serializer extends GlobalLootModifierSerializer<FortuneBonusModifier> {

    @Override
    public FortuneBonusModifier read(ResourceLocation location, JsonObject object,
                                     ILootCondition[] conditions) {
      return new FortuneBonusModifier(conditions);
    }

    @Override
    public JsonObject write(FortuneBonusModifier instance) {
      return this.makeConditions(instance.conditions);
    }
  }
}
