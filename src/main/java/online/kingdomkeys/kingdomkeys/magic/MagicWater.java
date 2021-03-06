package online.kingdomkeys.kingdomkeys.magic;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ThrowableEntity;
import net.minecraft.util.Hand;
import online.kingdomkeys.kingdomkeys.entity.magic.BlizzardEntity;
import online.kingdomkeys.kingdomkeys.entity.magic.FireEntity;
import online.kingdomkeys.kingdomkeys.entity.magic.WaterEntity;

public class MagicWater extends Magic {

	public MagicWater(String registryName, int cost, int order) {
		super(registryName, cost, false, order);
		this.name = registryName;
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onUse(PlayerEntity player, PlayerEntity caster) {
		WaterEntity shot = new WaterEntity(player.world, player);
		shot.setCaster(player.getDisplayName().getString());
		player.world.addEntity(shot);
		player.swingArm(Hand.MAIN_HAND);
	}

}
