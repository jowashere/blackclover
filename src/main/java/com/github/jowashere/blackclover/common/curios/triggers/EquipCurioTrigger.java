package com.github.jowashere.blackclover.common.curios.triggers;

import com.github.jowashere.blackclover.Main;
import com.google.gson.JsonObject;
import net.minecraft.advancements.criterion.*;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.ConditionArrayParser;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nonnull;

/**
 * This should be triggered whenever player successfully equips any item in their curios slot. In
 * theory, the item may not necessarily be valid for slot or have ICurio capability attached to it
 * at all, but that is mostly unimportant under normal circumstances.
 * <p>
 * Current implementation allows to perform item and location tests in criteria.
 */

public class EquipCurioTrigger extends AbstractCriterionTrigger<EquipCurioTrigger.Instance> {

  public static final ResourceLocation ID = new ResourceLocation(Main.MODID, "equip_curio");
  public static final EquipCurioTrigger INSTANCE = new EquipCurioTrigger();

  private EquipCurioTrigger() {
  }

  @Nonnull
  @Override
  public ResourceLocation getId() {
    return ID;
  }

  @Nonnull
  @Override
  public Instance createInstance(@Nonnull JsonObject json,
                                                       @Nonnull
                                                           EntityPredicate.AndPredicate playerPred,
                                                       @Nonnull ConditionArrayParser conditions) {
    return new Instance(playerPred, ItemPredicate.fromJson(json.get("item")),
        LocationPredicate.fromJson(json.get("location")));
  }

  public void trigger(ServerPlayerEntity player, ItemStack stack, ServerWorld world, double x,
                      double y, double z) {
    this.trigger(player, instance -> instance.test(stack, world, x, y, z));
  }

  static class Instance extends CriterionInstance {

    private final ItemPredicate item;
    private final LocationPredicate location;

    Instance(EntityPredicate.AndPredicate playerPred, ItemPredicate count,
             LocationPredicate indexPos) {
      super(ID, playerPred);
      this.item = count;
      this.location = indexPos;
    }

    @Nonnull
    @Override
    public ResourceLocation getCriterion() {
      return ID;
    }

    boolean test(ItemStack stack, ServerWorld world, double x, double y, double z) {
      return this.item.matches(stack) && this.location.matches(world, x, y, z);
    }
  }
}