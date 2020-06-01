package online.kingdomkeys.kingdomkeys.handler;

import java.util.Base64;

import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Vector3f;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.event.TickEvent.RenderTickEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.world.BlockEvent.EntityPlaceEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import online.kingdomkeys.kingdomkeys.capability.IGlobalCapabilities;
import online.kingdomkeys.kingdomkeys.capability.IPlayerCapabilities;
import online.kingdomkeys.kingdomkeys.capability.ModCapabilities;

public class ClientEvents {

	@SubscribeEvent
	public void onRenderTick(RenderTickEvent event) {
		//System.out.println(event.player.world.isRemote);
		PlayerEntity player = Minecraft.getInstance().player;
		if(InputHandler.lockOn != null && player != null) {
			if(InputHandler.lockOn.removed) {
                InputHandler.lockOn = null;
                return;	
			}
            LivingEntity target = InputHandler.lockOn;

            double dx = player.getPosX() - target.getPosX();
            double dz = player.getPosZ() - target.getPosZ();
           // double dy = player.posY - (target.posY - (target.height / 2.0F));
            double dy = player.getPosY() - (target.getPosY() + (target.getHeight() / 2.0F)-player.getHeight());
            double angle = Math.atan2(dz, dx) * 180 / Math.PI;
            double pitch = Math.atan2(dy, Math.sqrt(dx * dx + dz * dz)) * 180 / Math.PI;
            double distance = player.getDistance(target);
            float rYaw = (float) (angle - player.rotationYaw);
            while (rYaw > 180) {
                rYaw -= 360;
            }
            while (rYaw < -180) {
                rYaw += 360;
            }
            rYaw += 90F;
            float rPitch = (float) pitch - (float) (10.0F / Math.sqrt(distance)) + (float) (distance * Math.PI / 90);
            //System.out.println(target.height + (target.height / 2.0F));
          //  player.setHeadRotation(rYaw, (int) -(rPitch - player.rotationPitch));
            
            float f = player.rotationPitch;
            float f1 = player.rotationYaw;
            player.rotationYaw = (float)((double)player.rotationYaw + (double)rYaw * 0.15D);
            player.rotationPitch = (float)((double)player.rotationPitch - (double)-(rPitch - player.rotationPitch) * 0.15D);
            player.rotationPitch = MathHelper.clamp(player.rotationPitch, -90.0F, 90.0F);
            player.prevRotationPitch += player.rotationPitch - f;
            player.prevRotationYaw += player.rotationYaw - f1;

            if (player.getRidingEntity() != null) {
                player.getRidingEntity().applyOrientationToEntity(player);
            }

		}
	}
	
	float yaw = 0, pitch = 0;

	@SubscribeEvent
	public void onLivingUpdate(LivingUpdateEvent event) {
		IGlobalCapabilities gProps = ModCapabilities.getGlobal(event.getEntityLiving());
		if (gProps != null) {
			if (gProps.getStoppedTicks() > 0) {
				event.getEntityLiving().rotationPitch = pitch;
				event.getEntityLiving().rotationYaw = yaw;
				event.setCanceled(true);
			} else {
				yaw = event.getEntityLiving().rotationYaw;
				pitch = event.getEntityLiving().rotationPitch;
			}
		}		
		
	}
	
	@SubscribeEvent
	public void RenderEntity(RenderLivingEvent.Pre event) {
		if(event.getEntity() instanceof PlayerEntity) {
			PlayerEntity player = (PlayerEntity) event.getEntity();
			IPlayerCapabilities props = ModCapabilities.get(player);
			//Glide animation
			if(props.getIsGliding()) {
				event.getMatrixStack().rotate(Vector3f.XP.rotationDegrees(90));
				event.getMatrixStack().rotate(Vector3f.ZP.rotationDegrees(player.prevRotationYaw));
				event.getMatrixStack().rotate(Vector3f.YP.rotationDegrees(player.prevRotationYaw));
			}
			
			//Aerial Dodge rotation
			if(props.getAerialDodgeTicks() > 0) {
				event.getMatrixStack().rotate(Vector3f.YP.rotationDegrees(player.ticksExisted*80));
			}
		}
	}

}