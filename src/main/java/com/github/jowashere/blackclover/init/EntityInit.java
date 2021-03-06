package com.github.jowashere.blackclover.init;

import com.github.jowashere.blackclover.Main;
import com.github.jowashere.blackclover.entities.mobs.hostile.BanditEntity;
import com.github.jowashere.blackclover.entities.mobs.hostile.VolcanoMonsterEntity;
import com.github.jowashere.blackclover.entities.mobs.quester.GrimoireMagicianEntity;
import com.github.jowashere.blackclover.entities.spells.antimagic.BlackSlashEntity;
import com.github.jowashere.blackclover.entities.spells.darkness.AvidyaSlashEntity;
import com.github.jowashere.blackclover.entities.spells.darkness.BlackHoleEntity;
import com.github.jowashere.blackclover.entities.spells.light.LightSwordOJEntity;
import com.github.jowashere.blackclover.entities.spells.lightning.ThunderOrbEntity;
import com.github.jowashere.blackclover.entities.spells.slash.DeathScytheEntity;
import com.github.jowashere.blackclover.entities.spells.sword.OriginFlashEntity;
import com.github.jowashere.blackclover.entities.spells.water.*;
import com.github.jowashere.blackclover.entities.spells.wind.WindBladeEntity;
import com.github.jowashere.blackclover.entities.spells.wind.WindCrescentEntity;
import com.github.jowashere.blackclover.entities.spells.wind.WindHawkEntity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.passive.WaterMobEntity;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.gen.Heightmap;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.List;

public class EntityInit {

    public static final DeferredRegister<Item> SPAWN_EGGS = DeferredRegister.create(ForgeRegistries.ITEMS, Main.MODID);

    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES, Main.MODID);

    //Spells
    public static final RegistryObject<EntityType<WaterBallEntity>> WATER_BALL = ENTITIES.register("water_ball", () -> EntityType.Builder.<WaterBallEntity>of(WaterBallEntity::new, EntityClassification.MISC).setShouldReceiveVelocityUpdates(true).setUpdateInterval(1).setTrackingRange(128).sized(0.4F, 0.2F).build(Main.MODID + ":water_ball"));
    public static final RegistryObject<EntityType<WaterSpearEntity>> WATER_SPEAR = ENTITIES.register("water_spear", () -> EntityType.Builder.<WaterSpearEntity>of(WaterSpearEntity::new, EntityClassification.MISC).setShouldReceiveVelocityUpdates(true).setUpdateInterval(1).setTrackingRange(128).sized(0.6F, 0.4F).build(Main.MODID + ":water_spear"));
    public static final RegistryObject<EntityType<WaterDragonEntity>> WATER_DRAGON = ENTITIES.register("water_dragon", () -> EntityType.Builder.<WaterDragonEntity>of(WaterDragonEntity::new, EntityClassification.MISC).setShouldReceiveVelocityUpdates(true).setUpdateInterval(1).setTrackingRange(128).sized(1, 1).build(Main.MODID + ":water_dragon"));
    public static final RegistryObject<EntityType<WaterShieldEntity>> WATER_SHIELD = ENTITIES.register("water_shield", () -> EntityType.Builder.<WaterShieldEntity>of(WaterShieldEntity::new, EntityClassification.MISC).setShouldReceiveVelocityUpdates(true).setUpdateInterval(1).setTrackingRange(128).sized(1, 1).build(Main.MODID + ":water_shield"));
    public static final RegistryObject<EntityType<WaterDomeEntity>> WATER_DOME = ENTITIES.register("water_dome", () -> EntityType.Builder.<WaterDomeEntity>of(WaterDomeEntity::new, EntityClassification.MISC).setShouldReceiveVelocityUpdates(true).setUpdateInterval(1).setTrackingRange(128).sized(1, 1).build(Main.MODID + ":water_dome"));
    public static final RegistryObject<EntityType<PointBlankDragonEntity>> POINT_BLANK_DRAGON = ENTITIES.register("point_blank_dragon", () -> EntityType.Builder.<PointBlankDragonEntity>of(PointBlankDragonEntity::new, EntityClassification.MISC).setShouldReceiveVelocityUpdates(true).setUpdateInterval(1).setTrackingRange(128).sized(1, 1).build(Main.MODID + ":point_blank_dragon"));





    public static final RegistryObject<EntityType<WindBladeEntity>> WIND_BLADE = ENTITIES.register("wind_blade", () -> EntityType.Builder.<WindBladeEntity>of(WindBladeEntity::new, EntityClassification.MISC).setShouldReceiveVelocityUpdates(true).setUpdateInterval(1).setTrackingRange(128).sized(0.4F, 0.2F).build(Main.MODID + ":wind_blade"));
    public static final RegistryObject<EntityType<WindCrescentEntity>> WIND_CRESCENT = ENTITIES.register("wind_crescent", () -> EntityType.Builder.<WindCrescentEntity>of(WindCrescentEntity::new, EntityClassification.MISC).setShouldReceiveVelocityUpdates(true).setUpdateInterval(1).setTrackingRange(128).sized(3.8F, 0.2F).build(Main.MODID + ":wind_crescent"));
    public static final RegistryObject<EntityType<WindHawkEntity>> WIND_HAWK = ENTITIES.register("wind_hawk", () -> EntityType.Builder.<WindHawkEntity>of(WindHawkEntity::new, EntityClassification.MISC).setShouldReceiveVelocityUpdates(true).setUpdateInterval(1).setTrackingRange(128).sized(1.0F, 1.0F).build(Main.MODID + ":wind_hawk"));

    public static final RegistryObject<EntityType<ThunderOrbEntity>> THUNDER_ORB = ENTITIES.register("thunder_orb", () -> EntityType.Builder.<ThunderOrbEntity>of(ThunderOrbEntity::new, EntityClassification.MISC).setShouldReceiveVelocityUpdates(true).setUpdateInterval(1).setTrackingRange(128).sized(0.6F, 0.6F).build(Main.MODID + ":thunder_orb"));

    public static final RegistryObject<EntityType<LightSwordOJEntity>> LIGHT_SWORD_OJ = ENTITIES.register("light_sword_oj", () -> EntityType.Builder.<LightSwordOJEntity>of(LightSwordOJEntity::new, EntityClassification.MISC).setShouldReceiveVelocityUpdates(true).setUpdateInterval(1).setTrackingRange(128).sized(0.6F, 0.6F).build(Main.MODID + ":light_sword_oj"));

    public static final RegistryObject<EntityType<AvidyaSlashEntity>> AVIDYA_SLASH = ENTITIES.register("avidya_slash", () -> EntityType.Builder.<AvidyaSlashEntity>of(AvidyaSlashEntity::new, EntityClassification.MISC).setShouldReceiveVelocityUpdates(true).setUpdateInterval(1).setTrackingRange(128).sized(3.8F, 0.2F).build(Main.MODID + ":avidya_slash"));
    public static final RegistryObject<EntityType<BlackHoleEntity>> BLACK_HOLE = ENTITIES.register("black_hole", () -> EntityType.Builder.<BlackHoleEntity>of(BlackHoleEntity::new, EntityClassification.MISC).setShouldReceiveVelocityUpdates(true).setUpdateInterval(1).setTrackingRange(128).sized(1F, 1F).build(Main.MODID + ":black_hole"));

    public static final RegistryObject<EntityType<DeathScytheEntity>> DEATH_SCYTHE = ENTITIES.register("death_scythe", () -> EntityType.Builder.<DeathScytheEntity>of(DeathScytheEntity::new, EntityClassification.MISC).setShouldReceiveVelocityUpdates(true).setUpdateInterval(1).setTrackingRange(128).sized(0.2F, 1F).build(Main.MODID + ":death_scythe"));

    public static final RegistryObject<EntityType<OriginFlashEntity>> ORIGIN_FLASH = ENTITIES.register("origin_flash", () -> EntityType.Builder.<OriginFlashEntity>of(OriginFlashEntity::new, EntityClassification.MISC).setShouldReceiveVelocityUpdates(true).setUpdateInterval(1).setTrackingRange(128).sized(3.8F, 0.2F).build(Main.MODID + ":origin_flash"));

    public static final RegistryObject<EntityType<BlackSlashEntity>> BLACK_SLASH = ENTITIES.register("black_slash", () -> EntityType.Builder.<BlackSlashEntity>of(BlackSlashEntity::new, EntityClassification.MISC).setShouldReceiveVelocityUpdates(true).setUpdateInterval(1).setTrackingRange(128).sized(3.8F, 0.2F).build(Main.MODID + ":black_slash"));

    public static final RegistryObject<EntityType<GrimoireMagicianEntity>> GRIMOIRE_MAGICIAN = ENTITIES
            .register("grimoire_magician",
                    () -> EntityType.Builder.of(GrimoireMagicianEntity::new, EntityClassification.AMBIENT)
                            .sized(1f, 2f)
                            .build(Main.MODID + ":grimoire_magician"));

    public static final RegistryObject<EntityType<BanditEntity>> BANDIT = ENTITIES
            .register("bandit",
                    () -> EntityType.Builder.of(BanditEntity::new, EntityClassification.CREATURE)
                            .sized(1f, 2f)
                            .build(Main.MODID + ":bandit"));

    public static final RegistryObject<EntityType<VolcanoMonsterEntity>> VOLCANO_MONSTER = ENTITIES
            .register("volcano_monster",
                    () -> EntityType.Builder.of(VolcanoMonsterEntity::new, EntityClassification.CREATURE)
                            .sized(2f, 4f)
                            .fireImmune()
                            .build(Main.MODID + ":volcano_monster"));



    public static final List<EntityType<?>> ALL = new ArrayList<>();
    public static void registerEntities(RegistryEvent.Register<EntityType<?>> evt) {
        evt.getRegistry().registerAll(ALL.toArray(new EntityType<?>[0]));

        EntitySpawnPlacementRegistry.register(EntityInit.VOLCANO_MONSTER.get(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, MobEntity::checkMobSpawnRules);
    }
}
