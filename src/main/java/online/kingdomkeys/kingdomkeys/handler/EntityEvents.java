package online.kingdomkeys.kingdomkeys.handler;

import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.boss.WitherEntity;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.dimension.DimensionType;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.event.TickEvent.PlayerTickEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.event.world.RegisterDimensionsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import online.kingdomkeys.kingdomkeys.KingdomKeys;
import online.kingdomkeys.kingdomkeys.capability.ExtendedWorldData;
import online.kingdomkeys.kingdomkeys.capability.IGlobalCapabilities;
import online.kingdomkeys.kingdomkeys.capability.IPlayerCapabilities;
import online.kingdomkeys.kingdomkeys.capability.ModCapabilities;
import online.kingdomkeys.kingdomkeys.client.sound.ModSounds;
import online.kingdomkeys.kingdomkeys.driveform.DriveForm;
import online.kingdomkeys.kingdomkeys.driveform.ModDriveForms;
import online.kingdomkeys.kingdomkeys.entity.DriveOrbEntity;
import online.kingdomkeys.kingdomkeys.entity.HPOrbEntity;
import online.kingdomkeys.kingdomkeys.entity.MPOrbEntity;
import online.kingdomkeys.kingdomkeys.entity.MunnyEntity;
import online.kingdomkeys.kingdomkeys.item.KeybladeItem;
import online.kingdomkeys.kingdomkeys.item.organization.IOrgWeapon;
import online.kingdomkeys.kingdomkeys.lib.DamageCalculation;
import online.kingdomkeys.kingdomkeys.lib.Strings;
import online.kingdomkeys.kingdomkeys.lib.Utils;
import online.kingdomkeys.kingdomkeys.network.PacketHandler;
import online.kingdomkeys.kingdomkeys.network.cts.CSSetAerialDodgeTicksPacket;
import online.kingdomkeys.kingdomkeys.network.cts.CSSetGlidingPacket;
import online.kingdomkeys.kingdomkeys.network.cts.CSSyncAllClientDataPacket;
import online.kingdomkeys.kingdomkeys.network.stc.SCRecalculateEyeHeight;
import online.kingdomkeys.kingdomkeys.network.stc.SCSyncCapabilityPacket;
import online.kingdomkeys.kingdomkeys.network.stc.SCSyncExtendedWorld;
import online.kingdomkeys.kingdomkeys.network.stc.SCSyncGlobalCapabilityPacket;
import online.kingdomkeys.kingdomkeys.world.ModDimensions;

public class EntityEvents {

	public static boolean isBoss = false;
	public static boolean isHostiles = false;

	@SubscribeEvent
	public void registerDimensions(final RegisterDimensionsEvent event) {
		if (DimensionType.byName(KingdomKeys.TTDimType) == null) {
			DimensionManager.registerDimension(KingdomKeys.TTDimType, ModDimensions.traverseTownDimension.get(), null, true);
		}
		KingdomKeys.LOGGER.info("Dimensions Registered!");
	}
	
	@SubscribeEvent
	public void onPlayerJoin(PlayerLoggedInEvent e) {
		PlayerEntity player = e.getPlayer();
		IPlayerCapabilities props = ModCapabilities.get(player);
		if(props != null) {
			if (!player.world.isRemote) { // Sync from server to client
				//System.out.println(player.world.isRemote+" : "+ModCapabilities.get(player).getAbilitiesMap().get("kingdomkeys:scan")[0]);
				//LinkedHashMap<String, int[]> map = ModCapabilities.get(player).getAbilitiesMap();
				//System.out.println(map.values());
				player.setHealth(props.getMaxHP());
				player.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(props.getMaxHP());
				
				PacketHandler.sendTo(new SCSyncCapabilityPacket(props), (ServerPlayerEntity) player);
				
				ExtendedWorldData worldData = ExtendedWorldData.get(player.world);
				PacketHandler.sendTo(new SCSyncExtendedWorld(worldData), (ServerPlayerEntity) player);
			}
			PacketHandler.syncToAllAround(player, props);
		}
	}

	@SubscribeEvent
	public void onPlayerTick(PlayerTickEvent event) {
		IPlayerCapabilities props = ModCapabilities.get(event.player);
		if (props != null) {
			//System.out.println(props.getPartiesInvited());
			if(!event.player.world.isRemote && event.player.ticksExisted == 5) {
				PacketHandler.sendTo(new SCSyncCapabilityPacket(props), (ServerPlayerEntity)event.player);
			}
			
			if (props.getActiveDriveForm().equals(Strings.Form_Anti)) {
				if (props.getFP() > 0) {
					props.setFP(props.getFP() - 0.3);
				} else {
					props.setActiveDriveForm("");
					event.player.world.playSound(event.player, event.player.getPosition(), ModSounds.unsummon.get(), SoundCategory.MASTER, 1.0f, 1.0f);
					if(!event.player.world.isRemote) {
						PacketHandler.syncToAllAround(event.player, props);
					}
				}
			} else if (!props.getActiveDriveForm().equals("")) {
				ModDriveForms.registry.getValue(new ResourceLocation(props.getActiveDriveForm())).updateDrive(event.player);
			}
		
			// MP Recharge system
			if (props.getRecharge()) {
				if (props.getMP() >= props.getMaxMP()) { //Has recharged fully
					props.setRecharge(false);
					props.setMP(props.getMaxMP());
				} else { //Still recharging
					if (event.player.ticksExisted % 5 == 0)
						props.addMP(props.getMaxMP()/50);
				}
				
				//PacketHandler.sendTo(new SCSyncCapabilityPacket(props), (ServerPlayerEntity) event.player);

			} else { // Not on recharge
				if (props.getMP() <= 0) {
					props.setRecharge(true);
					if(!event.player.world.isRemote) {
						PacketHandler.sendTo(new SCSyncCapabilityPacket(props), (ServerPlayerEntity) event.player);
					}
				}
			}
		}

		// Combat mode
		List<Entity> entities = event.player.world.getEntitiesWithinAABBExcludingEntity(event.player, event.player.getBoundingBox().grow(16.0D, 10.0D, 16.0D).offset(-8.0D, -5.0D, -8.0D));
		List<Entity> bossEntities = event.player.world.getEntitiesWithinAABBExcludingEntity(event.player, event.player.getBoundingBox().grow(150.0D, 100.0D, 150.0D).offset(-75.0D, -50.0D, -75.0D));
		if (!bossEntities.isEmpty()) {
			for (int i = 0; i < bossEntities.size(); i++) {
				if (bossEntities.get(i) instanceof EnderDragonEntity || bossEntities.get(i) instanceof WitherEntity) {
					isBoss = true;
					break;
				} else {
					isBoss = false;
				}
			}
		} else {
			isBoss = false;
		}
		if (!entities.isEmpty()) {
			for (Entity entity : entities) {
				if (entity instanceof MobEntity) {
					isHostiles = true;
					break;
				} else {
					isHostiles = false;
				}
			}
		} else {
			isHostiles = false;
		}
	}

	@SubscribeEvent
	public void onLivingUpdate(LivingUpdateEvent event) {
		IGlobalCapabilities gProps = ModCapabilities.getGlobal(event.getEntityLiving());
		IPlayerCapabilities props = null;
		PlayerEntity player = null;
		if (event.getEntityLiving() instanceof PlayerEntity) {
			player = (PlayerEntity) event.getEntityLiving();
			props = ModCapabilities.get((PlayerEntity) event.getEntityLiving());
		}

		//MinecraftForge.EVENT_BUS.post(new EntityEvent.EyeHeight(player, player.getPose(), player.getSize(player.getPose()), player.getHeight()));

		if (gProps != null) {
			// Stop
			if (gProps.getStoppedTicks() > 0) {
				gProps.subStoppedTicks(1);

				event.getEntityLiving().setMotion(0, 0, 0);
				event.getEntityLiving().velocityChanged = true;

				if (gProps.getStoppedTicks() <= 0) {
					gProps.setStoppedTicks(0); // Just in case it goes below (shouldn't happen)
					if (gProps.getDamage() > 0 && gProps.getStopCaster() != null) {						
						event.getEntityLiving().attackEntityFrom(DamageSource.causePlayerDamage(Utils.getPlayerByName(event.getEntity().world, gProps.getStopCaster())), gProps.getDamage()/3);
					}

					if (event.getEntityLiving() instanceof ServerPlayerEntity) // Packet to unfreeze client
						PacketHandler.sendTo(new SCSyncGlobalCapabilityPacket(gProps), (ServerPlayerEntity) event.getEntityLiving());
					gProps.setDamage(0);
					gProps.setStopCaster(null);
				}
			}

			// Gravity
			if (gProps.getFlatTicks() > 0) {
				gProps.subFlatTicks(1);

				event.getEntityLiving().setMotion(0, -4, 0);
				event.getEntityLiving().velocityChanged = true;

				if (gProps.getFlatTicks() <= 0) {
					gProps.setFlatTicks(0); // Just in case it goes below (shouldn't happen)
					
					
					if (event.getEntityLiving() instanceof LivingEntity) {// This should sync the state of this entity (player or mob) to all the clients around to stop render it flat
						PacketHandler.syncToAllAround(event.getEntityLiving(), gProps);
						
						if (event.getEntityLiving() instanceof ServerPlayerEntity) {
							PacketHandler.sendTo(new SCRecalculateEyeHeight(), (ServerPlayerEntity) event.getEntityLiving());
						}
					}
					
				}
			}
		}

		if (props != null) {
			// Drive Form abilities
			//if(player.world.isRemote && player.getDisplayName().getFormattedText().equals(Minecraft.getInstance().player.getDisplayName().getFormattedText())) {
				if(shouldHandleHighJump(player, props)) {
					handleHighJump(player, props);
				}
				if(props.getActiveDriveForm().equals(Strings.Form_Wisdom)) {
					//handleQuickRun(player, props);
				}
				if(props.getActiveDriveForm().equals(Strings.Form_Limit)) {
					//handleDodgeRoll(player, props);
				}
				if(props.getActiveDriveForm().equals(Strings.Form_Master) || props.getActiveDriveForm().equals("") && (props.getDriveFormsMap().containsKey(Strings.Form_Master) && props.getDriveFormLevel(Strings.Form_Master) >= 3)) {
					handleAerialDodge(player, props);
				}
				if(props.getActiveDriveForm().equals(Strings.Form_Final) || props.getActiveDriveForm().equals("") && (props.getDriveFormsMap().containsKey(Strings.Form_Final) && props.getDriveFormLevel(Strings.Form_Final) >= 3)) {
					handleGlide(player, props);
				}
			//}

			//Reflect
			if (props.getReflectTicks() > 0) {
				props.remReflectTicks(1);

				event.getEntityLiving().setMotion(0, 0, 0);
				event.getEntityLiving().velocityChanged = true;

				// Spawn particles
				float radius = 1.5F;
				double freq = 0.6;
				double X = event.getEntityLiving().getPosX();
				double Y = event.getEntityLiving().getPosY();
				double Z = event.getEntityLiving().getPosZ();

				for (double x = X - radius; x <= X + radius; x += freq) {
					for (double y = Y - radius; y <= Y + radius; y += freq) {
						for (double z = Z - radius; z <= Z + radius; z += freq) {
							if ((X - x) * (X - x) + (Y - y) * (Y - y) + (Z - z) * (Z - z) <= radius * radius) {
								event.getEntityLiving().world.addParticle(ParticleTypes.BUBBLE_POP, x, y + 1, z, 0, 0, 0);
							}
						}
					}
				}

			} else { // When it finishes
				if (props.getReflectActive()) {// If has been hit
					// SPAWN ENTITY and apply damage
					List<Entity> list = player.world.getEntitiesWithinAABBExcludingEntity(player, player.getBoundingBox().grow(8.0D, 4.0D, 8.0D).offset(-4.0D, -1.0D, -4.0D));
					if (!list.isEmpty()) {
						for (int i = 0; i < list.size(); i++) {
							Entity e = (Entity) list.get(i);
							if (e instanceof LivingEntity) {
								e.attackEntityFrom(DamageSource.causePlayerDamage(player), 10);
							}
						}
					}
					props.setReflectActive(false); // Restart reflect
				}
			}
			
			//Aero
			if (props.getAeroTicks() > 0) {
				props.remAeroTicks(1);

				if(player.ticksExisted % 5 == 0) {
				// Spawn particles
				float radius = 1F;
				double freq = 0.5;
				double X = event.getEntityLiving().getPosX();
				double Y = event.getEntityLiving().getPosY();
				double Z = event.getEntityLiving().getPosZ();

				for (double x = X - radius; x <= X + radius; x += freq) {
					for (double y = Y - radius; y <= Y + radius; y += freq) {
						for (double z = Z - radius; z <= Z + radius; z += freq) {
							if ((X - x) * (X - x) + (Y - y) * (Y - y) + (Z - z) * (Z - z) <= radius * radius) {
								event.getEntityLiving().world.addParticle(ParticleTypes.ENCHANTED_HIT, x, y + 1, z, 0, 0, 0);
							}
						}
					}
				}
				}
			} 
		}
	}
	
	private void handleHighJump(PlayerEntity player, IPlayerCapabilities props) {
		boolean j = false;
        if(player.world.isRemote) {
            j = Minecraft.getInstance().gameSettings.keyBindJump.isKeyDown();
        }

        if (j) {
            if(player.getMotion().y > 0) {
            	if(props.getActiveDriveForm().equals(Strings.Form_Final)) {
	            	player.setMotion(player.getMotion().add(0,DriveForm.FINAL_JUMP_BOOST[props.getDriveFormLevel(Strings.Form_Final)],0));
            	} else {
            		System.out.println(props.getDriveFormsMap() != null);
            		if(props.getActiveDriveForm() != null) {
            			System.out.println(props.getDriveFormLevel(Strings.Form_Valor));
            			int jumpLevel = props.getActiveDriveForm().equals("") ? props.getDriveFormLevel(Strings.Form_Valor)-2 : props.getDriveFormLevel(Strings.Form_Valor);//TODO eventually replace it with the skill
	            		player.setMotion(player.getMotion().add(0,DriveForm.VALOR_JUMP_BOOST[jumpLevel],0));
            		}
	            }
            }
        }
	}
	
	private boolean shouldHandleHighJump(PlayerEntity player, IPlayerCapabilities props) {
		if(props.getDriveFormsMap() == null)
			return false;
		if(props.getActiveDriveForm().equals(Strings.Form_Valor) || props.getActiveDriveForm().equals(Strings.Form_Final)) {
			return true;
		}
		if(props.getActiveDriveForm().equals("") && (props.getDriveFormsMap().containsKey(Strings.Form_Valor) && props.getDriveFormLevel(Strings.Form_Valor) >= 3)){
			return true;
		}
		return false;
	}

	private void handleAerialDodge(PlayerEntity player, IPlayerCapabilities props) {
		if (props.getAerialDodgeTicks() > 0) {
			props.setAerialDodgeTicks(props.getAerialDodgeTicks() - 1);
		} else {
			if (player.onGround) {
				props.setHasJumpedAerialDodge(false);
				props.setAerialDodgeTicks(0);
			} else {
				if (player.world.isRemote) {
					if (player.getMotion().y < 0 && Minecraft.getInstance().gameSettings.keyBindJump.isKeyDown() && !player.isSneaking()) {
						if (!props.hasJumpedAerialDodge()) {
							props.setHasJumpedAerialDodge(true);
							player.jump();
							int jumpLevel = props.getActiveDriveForm().equals("") ? props.getDriveFormLevel(Strings.Form_Master)-2 : props.getDriveFormLevel(Strings.Form_Master);//TODO eventually replace it with the skill
							float boost = DriveForm.MASTER_AERIAL_DODGE_BOOST[jumpLevel];
							player.setMotion(player.getMotion().mul(new Vec3d(boost, boost, boost)));
							PacketHandler.sendToServer(new CSSetAerialDodgeTicksPacket(true, 10));
						}
					}
				}
			}
		}
	}

	private void handleGlide(PlayerEntity player, IPlayerCapabilities props) {
		if (player.world.isRemote) {// Need to check if it's clientside for the keyboard key detection
			if (Minecraft.getInstance().player == player) { // Only the local player will send the packets
				if (!player.onGround && player.fallDistance > 0) {
					if (Minecraft.getInstance().gameSettings.keyBindJump.isKeyDown()) {
						if (!props.getIsGliding()) {
							props.setIsGliding(true);// Set props clientside
							props.setAerialDodgeTicks(0);
							PacketHandler.sendToServer(new CSSetGlidingPacket(true)); // Set props serverside
							PacketHandler.sendToServer(new CSSetAerialDodgeTicksPacket(true, 0)); // In case the player is still rotating stop it
						}
					} else { // If is no longer pressing space
						if (props.getIsGliding()) {
							props.setIsGliding(false);
							PacketHandler.sendToServer(new CSSetGlidingPacket(false));
						}
					}
				} else { // If touches the ground
					if (props.getIsGliding()) {
						props.setIsGliding(false);
						PacketHandler.sendToServer(new CSSetGlidingPacket(false));
						PacketHandler.sendToServer(new CSSetAerialDodgeTicksPacket(false, 0)); // In case the player is still rotating stop it
					}
				}
			}
		}

		if (props.getIsGliding()) {
			int glideLevel = props.getActiveDriveForm().equals("") ? props.getDriveFormLevel(Strings.Form_Final)-2 : props.getDriveFormLevel(Strings.Form_Final);//TODO eventually replace it with the skill
			float glide = DriveForm.FINAL_GLIDE[glideLevel];
			Vec3d motion = player.getMotion();
			player.setMotion(motion.x, glide, motion.z);
		}
	}
	
	@SubscribeEvent
	public void hitEntity(LivingHurtEvent event) {
		if (event.getSource().getTrueSource() instanceof PlayerEntity) {
			PlayerEntity player = (PlayerEntity) event.getSource().getTrueSource();
			KeybladeItem heldKeyblade = null;
			ItemStack heldOrgWeapon = null;
			
			if (player.getHeldItemMainhand().getItem() instanceof KeybladeItem) {
				heldKeyblade = (KeybladeItem) player.getHeldItemMainhand().getItem();
			} else if(player.getHeldItemOffhand().getItem() instanceof KeybladeItem) {
				heldKeyblade = (KeybladeItem) player.getHeldItemOffhand().getItem();
			}
			
			//System.out.println(event.getSource().getImmediateSource());
			if(heldKeyblade != null && event.getSource().getImmediateSource() instanceof PlayerEntity) {
				float dmg = DamageCalculation.getStrengthDamage(player, heldKeyblade);
				event.setAmount(dmg);
			}
			
			if (player.getHeldItemMainhand().getItem() instanceof IOrgWeapon) {
				heldOrgWeapon = player.getHeldItemMainhand();
			} else if(player.getHeldItemOffhand().getItem() instanceof KeybladeItem) {
				heldOrgWeapon = player.getHeldItemOffhand();
			}
			
			if(heldKeyblade == null && heldOrgWeapon != null && event.getSource().getImmediateSource() instanceof PlayerEntity) {
				float dmg = DamageCalculation.getOrgStrengthDamage(player, heldOrgWeapon);
				event.setAmount(dmg);
			}
		}
	}

	// Prevent attack when stopped
	@SubscribeEvent
	public void onLivingAttack(LivingAttackEvent event) {
		if (event.getSource().getTrueSource() instanceof LivingEntity) { // If attacker is a LivingEntity
			//LivingEntity attacker = (LivingEntity) event.getSource().getTrueSource();
			LivingEntity target = event.getEntityLiving();
			
			IGlobalCapabilities gProps = ModCapabilities.getGlobal(target);
			if (target instanceof PlayerEntity) {
				IPlayerCapabilities props = ModCapabilities.get((PlayerEntity) target);

				if (props.getReflectTicks() > 0) { // If is casting reflect
					if (!props.getReflectActive()) // If has been hit while casting reflect
						props.setReflectActive(true);
					event.setCanceled(true);
				}
			}

			if (gProps != null && event.getSource().getTrueSource() instanceof PlayerEntity) {
				PlayerEntity source = (PlayerEntity) event.getSource().getTrueSource();
				if (gProps.getStoppedTicks() > 0) {
					float dmg = event.getAmount();
					System.out.println(event.getSource());
					if (event.getSource().getTrueSource() instanceof PlayerEntity) {
						if(source.getHeldItemMainhand() != null && source.getHeldItemMainhand().getItem() instanceof KeybladeItem) {
							dmg = DamageCalculation.getStrengthDamage((PlayerEntity) event.getSource().getTrueSource(), (KeybladeItem) source.getHeldItemMainhand().getItem());
						} else if(source.getHeldItemOffhand() != null && source.getHeldItemOffhand().getItem() instanceof KeybladeItem) {
							dmg = DamageCalculation.getStrengthDamage((PlayerEntity) event.getSource().getTrueSource(), (KeybladeItem) source.getHeldItemOffhand().getItem());
						}
						if(dmg == 0) {
							dmg = event.getAmount();
						}
						//System.out.println(dmg);
					}
					gProps.addDamage((int) dmg);
					event.setCanceled(true);
				}
			}
		}
	}

	@SubscribeEvent
	public void onLivingDeathEvent(LivingDeathEvent event) {
		/*
		 * if (event.getEntity() instanceof EntityDragon) {
		 * WorldSavedDataKingdomKeys.get(DimensionManager.getWorld(DimensionType.
		 * OVERWORLD.getId())).setSpawnHeartless(true); }
		 */

		if (!event.getEntity().world.isRemote) {
			if (event.getSource().getImmediateSource() instanceof PlayerEntity || event.getSource().getTrueSource() instanceof PlayerEntity) {
				PlayerEntity player = (PlayerEntity) event.getSource().getTrueSource();

				if (event.getEntity() instanceof MobEntity) {
					IPlayerCapabilities props = ModCapabilities.get(player);

					MobEntity mob = (MobEntity) event.getEntity();

					props.addExperience(player, (int) ((mob.getAttribute(SharedMonsterAttributes.MAX_HEALTH).getValue() / 2) /* * MainConfig.entities.xpMultiplier */));
					if (event.getEntity() instanceof WitherEntity) {
						props.addExperience(player, 1500);
					}
					
					Entity entity = event.getEntity();
					double x = entity.getPosX();
					double y = entity.getPosY();
					double z = entity.getPosZ();
					entity.world.addEntity(new MunnyEntity(event.getEntity().world, x, y, z, 1000));
					entity.world.addEntity(new HPOrbEntity(event.getEntity().world, x, y, z, 10));
					entity.world.addEntity(new MPOrbEntity(event.getEntity().world, x, y, z, 10));
					entity.world.addEntity(new DriveOrbEntity(event.getEntity().world, x, y, z, 10));

					PacketHandler.sendTo(new SCSyncCapabilityPacket(props), (ServerPlayerEntity) player);
				}
			}

		}

		
	}

	@SubscribeEvent
	public void onPlayerClone(PlayerEvent.Clone event) {
		if (event.isWasDeath()) {
			PlayerEntity oPlayer = event.getOriginal();
			PlayerEntity nPlayer = event.getPlayer();
			IPlayerCapabilities oProps = ModCapabilities.get(oPlayer);
			IPlayerCapabilities nProps = ModCapabilities.get(nPlayer);
			nProps.setLevel(oProps.getLevel());
			nProps.setExperience(oProps.getExperience());
			nProps.setExperienceGiven(oProps.getExperienceGiven());
			nProps.setStrength(oProps.getStrength());
			nProps.setMagic(oProps.getMagic());
			nProps.setDefense(oProps.getDefense());
			nProps.setMaxHP(oProps.getMaxHP());
			nProps.setMP(oProps.getMP());
			nProps.setMaxMP(oProps.getMaxMP());
			nProps.setDP(oProps.getDP());
			nProps.setFP(oProps.getFP());
			nProps.setMaxDP(oProps.getMaxDP());
			nProps.setConsumedAP(oProps.getConsumedAP());
			nProps.setMaxAP(oProps.getMaxAP());
			
			nProps.setMunny(oProps.getMunny());
			
			nProps.setMagicsList(oProps.getMagicsList());
			nProps.setAbilitiesMap(oProps.getAbilitiesMap());
			nProps.setPortalList(oProps.getPortalList());

			nProps.setDriveFormsMap(oProps.getDriveFormsMap());
			
			nPlayer.setHealth(nProps.getMaxHP());
			nPlayer.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(nProps.getMaxHP());

			System.out.println(event.getPlayer().world.isRemote+": "+nProps.getMP());
			// TODO sync stuff
			//if(!event.getPlayer().world.isRemote) {
			//	PacketHandler.sendTo(new SCSyncCapabilityPacket(nProps), (ServerPlayerEntity) nPlayer);

				/*PacketHandler.sendTo(new SCSyncCapabilityPacket(ModCapabilities.get(event.getPlayer())), (ServerPlayerEntity)event.getPlayer());

				PacketHandler.syncToAllAround(nPlayer, ModCapabilities.get(nPlayer));*/
			//}

		}
	}

	// Sync drive form on Start Tracking
	@SubscribeEvent
	public void playerStartedTracking(PlayerEvent.StartTracking e) {
		if (e.getTarget() instanceof PlayerEntity) {
			//System.out.println(e.getTarget());
			PlayerEntity targetPlayer = (PlayerEntity) e.getTarget();
			IPlayerCapabilities props = ModCapabilities.get(targetPlayer);
			PacketHandler.syncToAllAround(targetPlayer, props);
		}
	}

}
