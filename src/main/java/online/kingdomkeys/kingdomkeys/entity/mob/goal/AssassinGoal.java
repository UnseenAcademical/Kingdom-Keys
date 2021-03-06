package online.kingdomkeys.kingdomkeys.entity.mob.goal;

import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.TargetGoal;
import net.minecraft.world.Explosion;
import online.kingdomkeys.kingdomkeys.entity.EntityHelper;

public class AssassinGoal extends TargetGoal {
	// 2 - is Exploding ; 1 - in Shadow ; 0 - in Overworld

	private final int MAX_DISTANCE_FOR_AI = 100, TIME_BEFORE_NEXT_ATTACK = 70, TIME_TO_GO_UNDERGROUND = 120, TIME_UNDERGROUND = 30;
	private int undergroundTicks = 70, ticksUntilNextAttack, ticksToLowHealth = 70, ticksToExplode = 30;
	private boolean canUseNextAttack = true;

	public AssassinGoal(CreatureEntity creature) {
		super(creature, true);
		ticksUntilNextAttack = TIME_BEFORE_NEXT_ATTACK;
	}

	@Override
	public boolean shouldContinueExecuting() {
		if (this.goalOwner.getAttackTarget() != null) {
			if(goalOwner.getHealth() <= goalOwner.getMaxHealth() / 4) { //If the assassin is at 25% hp or less
				if(isExploding()) {
					ticksToExplode--;
					if(ticksToExplode <= 0) {
						explode();
					}
				} else {
					ticksToLowHealth--;
					if(ticksToLowHealth <= 0) {
						EntityHelper.setState(this.goalOwner, 2);
	                    this.goalOwner.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.0D);
	                    this.goalOwner.setInvulnerable(true);
					}
				}
				return true;
			}
			
			
			if (isUnderground()) {
				this.goalOwner.setInvulnerable(true);

				canUseNextAttack = false;
				if(this.goalOwner.getDistance(this.goalOwner.getAttackTarget()) < 5) {
					this.goalOwner.attackEntityAsMob(this.goalOwner.getAttackTarget());
				} else {
					EntityHelper.setState(this.goalOwner, 0);
					this.goalOwner.setInvulnerable(false);
					undergroundTicks = TIME_TO_GO_UNDERGROUND;
					canUseNextAttack = true;
				}
				
				undergroundTicks++;
				if (undergroundTicks >= TIME_UNDERGROUND) { //Go to the surface
					EntityHelper.setState(this.goalOwner, 0);
					this.goalOwner.setInvulnerable(false);

					canUseNextAttack = true;
				}
			}
			
			if(this.goalOwner.getDistance(this.goalOwner.getAttackTarget()) < 5) { //If target is in range
				if (this.goalOwner.isOnGround()) {
					if (!isUnderground()) {
						undergroundTicks--;
						if (undergroundTicks <= 0) {
							EntityHelper.setState(this.goalOwner, 1);
							canUseNextAttack = false;
						}
					} else {
	
					}
				}
	
				if (!canUseNextAttack) {
					ticksUntilNextAttack--;
					if (ticksUntilNextAttack <= 0) {
						canUseNextAttack = true;
						ticksUntilNextAttack = TIME_BEFORE_NEXT_ATTACK;
					}
				}
			}

			return true;
		}
		EntityHelper.setState(this.goalOwner, 0);
		this.goalOwner.setInvulnerable(false);
		return false;
	}

	private void explode() {
        goalOwner.world.createExplosion(goalOwner, goalOwner.getPosX(), goalOwner.getPosY(), goalOwner.getPosZ(), 6, false, Explosion.Mode.NONE);
        goalOwner.remove();
	}

	@Override
	public void startExecuting() {
		EntityHelper.setState(this.goalOwner, 0);
		this.goalOwner.setInvulnerable(false);
	}

	private boolean isUnderground() {
		return EntityHelper.getState(this.goalOwner) == 1;
	}
	
	private boolean isExploding() {
		return EntityHelper.getState(this.goalOwner) == 2;
	}

	@Override
	public boolean shouldExecute() {
		return this.goalOwner.getAttackTarget() != null && this.goalOwner.getDistanceSq(this.goalOwner.getAttackTarget()) < MAX_DISTANCE_FOR_AI;
	}

}