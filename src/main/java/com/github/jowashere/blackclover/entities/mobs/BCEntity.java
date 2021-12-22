package com.github.jowashere.blackclover.entities.mobs;

import com.github.jowashere.blackclover.api.Beapi;
import com.github.jowashere.blackclover.api.internal.AbstractSpell;
import com.github.jowashere.blackclover.api.internal.BCMAttribute;
import com.github.jowashere.blackclover.capabilities.player.IPlayerHandler;
import com.github.jowashere.blackclover.capabilities.player.PlayerProvider;
import com.github.jowashere.blackclover.entities.AiSpellEntry;
import com.github.jowashere.blackclover.init.AttributeInit;
import com.github.jowashere.blackclover.init.ModAttributes;
import com.github.jowashere.blackclover.networking.NetworkLoader;
import com.github.jowashere.blackclover.networking.packets.PacketMagicExpSync;
import com.github.jowashere.blackclover.networking.packets.entity.PacketSyncBCEntityTarget;
import com.github.jowashere.blackclover.util.helpers.AttributeHelper;
import com.github.jowashere.blackclover.util.helpers.BCMHelper;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.*;
import net.minecraftforge.common.IExtensibleEnum;
import net.minecraftforge.fml.network.PacketDistributor;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public abstract class BCEntity extends CreatureEntity implements IDynamicRenderer {
    protected String[] textures;
    protected boolean needsEntityDataUpdate;
    protected float magicXPDrop = 1;

    private static final DataParameter<String> TEXTURE = EntityDataManager.defineId(BCEntity.class, DataSerializers.STRING);
    private static final DataParameter<String> GRIMOIRE_TEXTURE = EntityDataManager.defineId(BCEntity.class, DataSerializers.STRING);
    private static final DataParameter<String> ATTRIBUTE_NAME = EntityDataManager.defineId(BCEntity.class, DataSerializers.STRING);
    private static final DataParameter<Integer> ANIMATION = EntityDataManager.defineId(BCEntity.class, DataSerializers.INT);
    private static final DataParameter<Integer> MAGIC_LEVEL = EntityDataManager.defineId(BCEntity.class, DataSerializers.INT);

    private int yul;
    protected int threat = 2, maxML = 100, minML = 0;
    private Goal currentGoal, previousGoal;
    protected List<AiSpellEntry> SPELL_POOL = new ArrayList<AiSpellEntry>();

    public BCEntity(EntityType type, World world)
    {
        this(type, world, null);
    }

    public BCEntity(EntityType type, World world, String[] textures)
    {
        super(type, world);
        this.xpReward = this.threat;
        this.textures = textures;
        this.chooseTexture();
    }

    public static AttributeModifierMap.MutableAttribute createAttributes()
    {
        return MobEntity.createMobAttributes().add(Attributes.ATTACK_DAMAGE);
    }

    @Override
    protected void defineSynchedData()
    {
        super.defineSynchedData();
        this.getEntityData().define(TEXTURE, "");
        this.getEntityData().define(GRIMOIRE_TEXTURE, "");
        this.getEntityData().define(ATTRIBUTE_NAME, "null");
        this.getEntityData().define(MAGIC_LEVEL, 1);
        this.getEntityData().define(ANIMATION, 0);
    }

    @Override
    public void addAdditionalSaveData(CompoundNBT nbt)
    {
        super.addAdditionalSaveData(nbt);

        nbt.putInt("magic_level", this.getMagicLevel());
        nbt.putInt("yul", this.yul);
        nbt.putInt("threat", this.threat);
        nbt.putInt("animation", this.getAnimation());

        nbt.putString("attribute_name", this.getAttribute().getString());
        nbt.putString("grimoire_texture", this.getGrimoireTexture());
        nbt.putString("texture", this.getMobTexture());
    }

    @Override
    public void readAdditionalSaveData(CompoundNBT nbt)
    {
        super.readAdditionalSaveData(nbt);

        this.setMagicLevel(nbt.getInt("magic_level"));
        this.yul = nbt.getInt("yul");
        this.threat = nbt.getInt("threat");
        this.setAnimation(nbt.getInt("animation"));

        this.setAttribute(AttributeHelper.getAttributeFromString(nbt.getString("attribute_name")));
        this.setGrimoireTexture(nbt.getString("grimoire_texture"));
        this.setTexture(nbt.getString("texture"));
    }

    @Override
    public String getDefaultTexture()
    {
        if (this.textures != null)
            return this.textures[0];
        return "null";
    }

    @Override
    @Nullable
    public ILivingEntityData finalizeSpawn(IServerWorld world, DifficultyInstance difficulty, SpawnReason reason, @Nullable ILivingEntityData spawnData, @Nullable CompoundNBT dataTag)
    {
        spawnData = super.finalizeSpawn(world, difficulty, reason, spawnData, dataTag);

        this.setLeftHanded(false);

        if (this.textures != null && this.textures.length > 0)
        {
            int id = this.random.nextInt(this.textures.length);
            this.setTexture(this.textures[id]);
        } else if (BCMHelper.isNullOrEmpty(this.getMobTexture()))
            this.setTexture(this.getDefaultTexture());

        this.setMagicLevel((int) Beapi.randomWithRange(minML, maxML));

        return spawnData;
    }

    public void generateGrimoireTextures() {
        if (this.getAttribute() != null && this.getAttribute() != AttributeInit.NULL) {

            List<String> grimoireTextures = this.getAttribute().getGrimoireTextures();

            int id = this.random.nextInt(grimoireTextures.size());
            this.setGrimoireTexture(grimoireTextures.get(id));
        }
    }

    @Override
    protected void dropCustomDeathLoot(DamageSource source, int looting, boolean recentlyHitIn)
    {
    }

    @Override
    public void die(DamageSource cause) {
        super.die(cause);

        if (cause.getEntity() instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) cause.getEntity();
            IPlayerHandler playercap = player.getCapability(PlayerProvider.CAPABILITY_PLAYER).orElseThrow(() -> new RuntimeException("CAPABILITY_PLAYER NOT FOUND!"));

            playercap.addMagicExp(this.magicXPDrop);
            NetworkLoader.INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) player), new PacketMagicExpSync(playercap.returnMagicExp(), player.getId()));

        }

    }

    @Override
    protected void tickDeath() {
        super.tickDeath();
    }

    @SuppressWarnings("deprecation")
    @Override
    public float getWalkTargetValue(BlockPos pos, IWorldReader world)
    {
        Block block = world.getBlockState(pos.below()).getBlock();
        boolean flag1 = !world.getBlockState(pos).isAir();
        boolean flag2 = pos.getY() < 150;
        boolean flag3 = block.getBlock() == Blocks.GRASS_BLOCK || block.getBlock() == Blocks.SAND || block.getBlock() == Blocks.STONE;
        float weight = (64 / pos.getY()) * 10;
        return flag1 && flag2 && flag3 ? weight : 0;
    }

    @Override
    public boolean checkSpawnRules(IWorld world, SpawnReason reason)
    {
        return this.getWalkTargetValue(new BlockPos(this.position()), world) > 0.0F;
    }

    @Override
    public void checkDespawn()
    {
        if (this.hasCustomName())
            return;

        super.checkDespawn();
    }

    @Override
    public String getMobTexture()
    {
        return this.getEntityData().get(TEXTURE);
    }

    protected void setTexture(String id)
    {
        this.getEntityData().set(TEXTURE, id);
    }

    protected void chooseTexture()
    {
        if(!this.level.isClientSide){
            if (this.textures != null && this.textures.length > 0)
            {
                int id = this.random.nextInt(this.textures.length);
                this.setTexture(this.textures[id]);
            }
        }
    }

    protected void chooseGrimoireTexture()
    {
        if(!this.level.isClientSide){
            if (this.textures != null && this.textures.length > 0)
            {
                int id = this.random.nextInt(this.textures.length);
                this.setTexture(this.textures[id]);
            }
        }
    }

    public int getMagicLevel()
    {
        return this.entityData.get(MAGIC_LEVEL);
    }

    public void setMagicLevel(int value)
    {
        this.entityData.set(MAGIC_LEVEL, value);
    }

    public int getYul()
    {
        return this.yul;
    }

    public void setYul(double value)
    {
        this.yul = (int) Math.floor(value);
    }

    public int getAnimation()
    {
        return this.entityData.get(ANIMATION);
    }

    public void setAnimation(int value)
    {
        this.entityData.set(ANIMATION, value);
    }

    public Goal getCurrentGoal()
    {
        return this.currentGoal;
    }

    public Goal getPreviousGoal()
    {
        return this.previousGoal;
    }

    public void setCurrentGoal(Goal goal)
    {
        this.currentGoal = goal;
    }

    public void setPreviousGoal(Goal goal)
    {
        this.previousGoal = goal;
    }

    public void addThreat(int threat)
    {
        this.threat += threat;
    }

    public int getThreat()
    {
        return this.threat;
    }

    public void setThreat(int threat)
    {
        this.threat = threat;
    }

    public void queueEntityDataUpdate()
    {
        this.needsEntityDataUpdate = true;
    }

    @Override
    public void tick()
    {
        super.tick();

        if (!this.level.isClientSide)
        {
            if (this.needsEntityDataUpdate)
            {
                /*IEntityStats props = EntityStatsCapability.get(this);
                WyNetwork.sendToAllTrackingAndSelf(new SSyncEntityStatsPacket(this.getId(), props), this);*/

                this.needsEntityDataUpdate = false;
            }

            int targetID = this.getTarget() != null ? this.getTarget().getId() : 0;
            NetworkLoader.INSTANCE.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> this), new PacketSyncBCEntityTarget(this.getId(), targetID));

        }
    }

    public static enum Animation implements IExtensibleEnum
    {
        NONE(0), FLINTLOCK_POINTING(1), CLEAVE_ATTACK(2), SHOCKWAVE(3), BLOCK(4);

        private int id;

        private Animation(int id)
        {
            this.id = id;
        }

        public int getId()
        {
            return this.id;
        }

        public static Animation create(String name, int id)
        {
            throw new IllegalStateException("Enum not extended");
        }
    }

    public String getGrimoireTexture() {
        return this.entityData.get(GRIMOIRE_TEXTURE);
    }

    public void setGrimoireTexture(String grimoireTexLoc) {
        this.entityData.set(GRIMOIRE_TEXTURE, grimoireTexLoc);
    }

    public BCMAttribute getAttribute(){
        return AttributeHelper.getAttributeFromString(this.entityData.get(ATTRIBUTE_NAME));
    }

    public void setAttribute(BCMAttribute attribute){
        this.entityData.set(ATTRIBUTE_NAME, attribute.getString());
    }

    public void applySpellCD(AbstractSpell spell){
        String nbtName = spell.getCorrelatedPlugin().getPluginId() + "_" + spell.getName() + "_cd";
        this.getPersistentData().putInt(nbtName, spell.getCooldown());
    }

    protected static AttributeModifierMap.MutableAttribute addModAttributes (AttributeModifierMap.MutableAttribute attributes) {
        return attributes
                .add(ModAttributes.DAMAGE_REDUCTION.get())
                .add(ModAttributes.FALL_RESISTANCE.get())
                .add(ModAttributes.JUMP_HEIGHT.get())
                .add(ModAttributes.REGEN_RATE.get())
                .add(ModAttributes.SPECIAL_DAMAGE_REDUCTION.get())
                .add(ModAttributes.ATTACK_RANGE.get())
                .add(ModAttributes.STEP_HEIGHT.get());
    }

    public float getMagicExpDrop() {
        return magicXPDrop;
    }

}
