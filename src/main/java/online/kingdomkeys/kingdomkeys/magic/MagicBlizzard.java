package online.kingdomkeys.kingdomkeys.magic;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ThrowableEntity;
import net.minecraft.util.Hand;
import online.kingdomkeys.kingdomkeys.entity.magic.BlizzardEntity;
import online.kingdomkeys.kingdomkeys.entity.magic.FireEntity;

public class MagicBlizzard extends Magic {

	public MagicBlizzard(String registryName, int cost, int order) {
		super(registryName, cost, false, order);
		this.name = registryName;
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onUse(PlayerEntity player, PlayerEntity caster) {
		ThrowableEntity shot = new BlizzardEntity(player.world, player);
		player.world.addEntity(shot);
		shot.func_234612_a_(player, player.rotationPitch, player.rotationYaw, 0, 1F, 0);
		player.swingArm(Hand.MAIN_HAND);
	}

}
