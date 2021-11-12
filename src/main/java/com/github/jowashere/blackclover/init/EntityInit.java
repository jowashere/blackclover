package com.github.jowashere.blackclover.init;

import com.github.jowashere.blackclover.Main;
import com.github.jowashere.blackclover.entities.projectiles.spells.lightning.ThunderOrbEntity;
import com.github.jowashere.blackclover.entities.projectiles.spells.wind.WindBladeEntity;
import com.github.jowashere.blackclover.entities.projectiles.spells.wind.WindCrescentEntity;
import com.github.jowashere.blackclover.entities.summons.WindHawkEntity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class EntityInit {

    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES, Main.MODID);

    //Spells
    public static final RegistryObject<EntityType<WindBladeEntity>> WIND_BLADE = ENTITIES.register("wind_blade", () -> EntityType.Builder.<WindBladeEntity>of(WindBladeEntity::new, EntityClassification.MISC).setShouldReceiveVelocityUpdates(true).setUpdateInterval(1).setTrackingRange(128).sized(0.4F,0.2F).build(Main.MODID + ":wind_blade"));
    public static final RegistryObject<EntityType<WindCrescentEntity>> WIND_CRESCENT = ENTITIES.register("wind_crescent", () -> EntityType.Builder.<WindCrescentEntity>of(WindCrescentEntity::new, EntityClassification.MISC).setShouldReceiveVelocityUpdates(true).setUpdateInterval(1).setTrackingRange(128).sized(1.5F,0.7F).build(Main.MODID + ":wind_crescent"));

    public static final RegistryObject<EntityType<ThunderOrbEntity>> THUNDER_ORB = ENTITIES.register("thunder_orb", () -> EntityType.Builder.<ThunderOrbEntity>of(ThunderOrbEntity::new, EntityClassification.MISC).setShouldReceiveVelocityUpdates(true).setUpdateInterval(1).setTrackingRange(128).sized(1.5F,0.7F).build(Main.MODID + ":thunder_orb"));

    //Summons
    public static final RegistryObject<EntityType<WindHawkEntity>> WIND_HAWK = ENTITIES.register("wind_hawk", () -> EntityType.Builder.<WindHawkEntity>of(WindHawkEntity::new, EntityClassification.MISC).setShouldReceiveVelocityUpdates(true).setUpdateInterval(1).setTrackingRange(128).sized(1.0F,1.0F).build(Main.MODID + ":wind_hawk"));

}
