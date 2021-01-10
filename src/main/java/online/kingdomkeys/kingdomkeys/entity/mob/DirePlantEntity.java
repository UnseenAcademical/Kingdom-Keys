package online.kingdomkeys.kingdomkeys.entity.mob;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.HurtByTargetGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.FMLPlayMessages;
import online.kingdomkeys.kingdomkeys.capability.ModCapabilities;
import online.kingdomkeys.kingdomkeys.entity.EntityHelper;
import online.kingdomkeys.kingdomkeys.entity.ModEntities;
import online.kingdomkeys.kingdomkeys.entity.SeedBulletEntity;

//TODO fix seed bullet
public class DirePlantEntity extends MonsterEntity implements IKHMob {

    public DirePlantEntity(EntityType<? extends MonsterEntity> type, World worldIn) {
        super(type, worldIn);
    }

    public DirePlantEntity(FMLPlayMessages.SpawnEntity spawnEntity, World world) {
        super(ModEntities.TYPE_DIRE_PLANT.get(), world);
    }
    
    @Override
    public boolean canSpawn(IWorld worldIn, SpawnReason spawnReasonIn) {
    	return ModCapabilities.getWorld((World)worldIn).getHeartlessSpawnLevel() > 0;
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new MeleeAttackGoal(this, 1.0D, false));
        this.goalSelector.addGoal(0, new SeedGoal(this));
        this.goalSelector.addGoal(1, new LookRandomlyGoal(this));
        this.targetSelector.addGoal(0, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true));
    }

    public static AttributeModifierMap.MutableAttribute registerAttributes() {
        return MobEntity.registerAttributes()
                .createMutableAttribute(Attributes.FOLLOW_RANGE, 35.0D)
                .createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.0D)
                .createMutableAttribute(Attributes.MAX_HEALTH, 40.0D)
                .createMutableAttribute(Attributes.KNOCKBACK_RESISTANCE, 1000.0D)
                .createMutableAttribute(Attributes.ATTACK_DAMAGE, 2.0D)
				.createMutableAttribute(Attributes.ATTACK_KNOCKBACK, 1.0D)
                ;
    }

    @Override
    public int getMaxSpawnedInChunk() {
        return 4;
    }

    @Override
    protected void registerData() {
        super.registerData();
        this.dataManager.register(EntityHelper.STATE, 0);
    }

    @Override
    public EntityHelper.MobType getMobType() {
        return EntityHelper.MobType.HEARTLESS_EMBLEM;
    }

    class SeedGoal extends Goal {
        private DirePlantEntity theEntity;
        private boolean canUseAttack = true;
        private int attackTimer = 30;

        public SeedGoal(DirePlantEntity e) {
            this.theEntity = e;
        }

        @Override
        public boolean shouldExecute() {
            if(theEntity.getAttackTarget() != null) {
                if(!canUseAttack) {
                    if(attackTimer > 0) {
                        attackTimer--;
                        return false;
                    }
                    else return true;
                }
                else return true;
            }
            else return false;
        }

        @Override
        public boolean shouldContinueExecuting() {
            boolean flag = canUseAttack;

            return flag;
        }

        @Override
        public void startExecuting() {
            canUseAttack = true;
            attackTimer = 30;
            EntityHelper.setState(theEntity, 0);
        }

        @Override
        public void tick() {
            if(theEntity.getAttackTarget() != null && canUseAttack	) {
                EntityHelper.setState(theEntity, 1);
                LivingEntity target = this.theEntity.getAttackTarget();

                this.theEntity.getLookController().setLookPositionWithEntity(target, 30F, 30F);
                double d1 = this.theEntity.getAttackTarget().getPosX() - this.theEntity.getPosX();
                double d2 = this.theEntity.getAttackTarget().getPosY() - this.theEntity.getPosY();//getBoundingBox().minY + (double)(this.theEntity.getAttackTarget().getHeight() / 2.0F) - (this.theEntity.getPosY() + (double)(this.theEntity.getHeight() / 2.0F));
                double d3 = this.theEntity.getAttackTarget().getPosZ() - this.theEntity.getPosZ();
                System.out.println("attack");
                if(world.rand.nextInt(100) + 1 > EntityHelper.percentage(25, 100)) {

                    SeedBulletEntity seed = new SeedBulletEntity(this.theEntity, this.theEntity.world);
                    seed.shoot(d1, d2, d3, 1.2F, 0);
                    seed.setPosition(seed.getPosX(), this.theEntity.getPosY() + (double)(this.theEntity.getHeight() / 2.0F) + 0.3D, seed.getPosZ());
                    this.theEntity.world.addEntity(seed);
                }
                else {
                    SeedBulletEntity seed = new SeedBulletEntity(this.theEntity, this.theEntity.world);
                    seed.shoot(d1, d2, d3, 1.2F, 0);
                    seed.setPosition(seed.getPosX(), this.theEntity.getPosY() + (double)(this.theEntity.getHeight() / 2.0F) + 0.3D, seed.getPosZ());
                    this.theEntity.world.addEntity(seed);

                    seed = new SeedBulletEntity(this.theEntity, this.theEntity.world);
                    seed.shoot(d1, d2, d3, 0.7F, 0);
                    seed.setPosition(seed.getPosX(), this.theEntity.getPosY() + (double)(this.theEntity.getHeight() / 2.0F) + 0.3D, seed.getPosZ());
                    this.theEntity.world.addEntity(seed);

                    seed = new SeedBulletEntity(this.theEntity, this.theEntity.world);
                    seed.shoot(d1, d2, d3, 0.5F, 0);
                    seed.setPosition(seed.getPosX(), this.theEntity.getPosY() + (double)(this.theEntity.getHeight() / 2.0F) + 0.3D, seed.getPosZ());
                    this.theEntity.world.addEntity(seed);
                }

                canUseAttack = false;
            }
        }

    }
}
