package online.kingdomkeys.kingdomkeys.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import online.kingdomkeys.kingdomkeys.KingdomKeys;
import online.kingdomkeys.kingdomkeys.capability.IPlayerCapabilities;
import online.kingdomkeys.kingdomkeys.capability.ModCapabilities;
import online.kingdomkeys.kingdomkeys.driveform.DriveForm;
import online.kingdomkeys.kingdomkeys.lib.Constants;
import online.kingdomkeys.kingdomkeys.lib.Strings;

//TODO cleanup + comments
public class PlayerPortraitGui extends Screen {

	public PlayerPortraitGui() {
		super(new TranslationTextComponent(""));
		minecraft = Minecraft.getInstance();
	}

	@SubscribeEvent
	public void onRenderOverlayPost(RenderGameOverlayEvent event) {
//        if (!MainConfig.displayGUI())
		// return;
		IPlayerCapabilities playerData = ModCapabilities.getPlayer(minecraft.player);
		// if(!minecraft.player.getCapability(ModCapabilities.PLAYER_STATS,
		// null).getHudMode())
		// return;
		int screenWidth = minecraft.getMainWindow().getScaledWidth();
		int screenHeight = minecraft.getMainWindow().getScaledHeight();
		if (event.getType() == RenderGameOverlayEvent.ElementType.TEXT) {
			RenderSystem.color3f(1, 1, 1);
			ResourceLocation skin = minecraft.player.getLocationSkin();
			minecraft.getTextureManager().bindTexture(skin);
			float scale = 0.5f;
			switch (minecraft.gameSettings.guiScale) {
			case Constants.SCALE_AUTO:
				scale = 0.85f;
				break;
			case Constants.SCALE_NORMAL:
				scale = 0.85f;
				break;
			default:
				scale = 0.65f;
				break;
			}

			if (playerData != null) {
				if (playerData.getActiveDriveForm().equals(Strings.Form_Anti)) {
					RenderSystem.color4f(0.3F, 0.3F, 0.3F, 1F);
				}

				RenderSystem.pushMatrix();
				{
					RenderSystem.translatef(-5, -1, 0);

					// HEAD
					int headWidth = 32;
					int headHeight = 32;
					float headPosX = 16;
					float headPosY = 32;
					float scaledHeadPosX = headPosX * scale;
					float scaledHeadPosY = headPosY * scale;

					RenderSystem.pushMatrix();
					{
						RenderSystem.translatef((screenWidth - headWidth * scale) - scaledHeadPosX, (screenHeight - headHeight * scale) - scaledHeadPosY, 0);
						RenderSystem.scalef(scale, scale, scale);
						this.blit(0, 0, 32, 32, headWidth, headHeight);
					}
					RenderSystem.popMatrix();

					// HAT
					int hatWidth = 32;
					int hatHeight = 32;
					float hatPosX = 16;
					float hatPosY = 32;
					float scaledHatPosX = hatPosX * scale;
					float scaledHatPosY = hatPosY * scale;

					RenderSystem.pushMatrix();
					{
						RenderSystem.translatef((screenWidth - hatWidth * scale) - scaledHatPosX, (screenHeight - hatHeight * scale) - scaledHatPosY, 0);
						RenderSystem.scalef(scale, scale, scale);
						this.blit(0, 0, 160, 32, hatWidth, hatHeight);
					}
					RenderSystem.popMatrix();

					// BODY
					int bodyWidth = 32;
					int bodyHeight = 64;
					float bodyPosX = 16;
					float bodyPosY = -32;
					float scaledBodyPosX = bodyPosX * scale;
					float scaledBodyPosY = bodyPosY * scale;

					RenderSystem.pushMatrix();
					{
						RenderSystem.translatef((screenWidth - bodyWidth * scale) - scaledBodyPosX, (screenHeight - bodyHeight * scale) - scaledBodyPosY, 0);
						RenderSystem.scalef(scale, scale, scale);
						this.blit(0, 0, 80, 80, bodyWidth, bodyHeight);
					}
					RenderSystem.popMatrix();

					// JACKET
					int jacketWidth = 32;
					int jacketHeight = 64;
					float jacketPosX = 16;
					float jacketPosY = -32;
					float scaledjacketPosX = jacketPosX * scale;
					float scaledjacketPosY = jacketPosY * scale;

					RenderSystem.pushMatrix();
					{
						RenderSystem.translatef((screenWidth - bodyWidth * scale) - scaledBodyPosX, (screenHeight - bodyHeight * scale) - scaledBodyPosY, 0);
						RenderSystem.scalef(scale, scale, scale);
						this.blit(0, 0, 80, 148, bodyWidth, bodyHeight);
					}
					RenderSystem.popMatrix();

					// ARMS
					int armWidth = 16;
					int armHeight = 64;
					float armRPosX = 48;
					float armRPosY = -32;
					float scaledArmRPosX = armRPosX * scale;
					float scaledArmRPosY = armRPosY * scale;
					float armLPosX = 0;
					float armLPosY = -32;
					float scaledArmLPosX = armLPosX * scale;
					float scaledArmLPosY = armLPosY * scale;

					RenderSystem.pushMatrix();
					{
						RenderSystem.translatef((screenWidth - armWidth * scale) - scaledArmRPosX, (screenHeight - armHeight * scale) - scaledArmRPosY, 0);
						RenderSystem.scalef(scale, scale, scale);
						this.blit(0, 0, 176, 80, armWidth, armHeight);
					}
					RenderSystem.popMatrix();

					RenderSystem.pushMatrix();
					{
						RenderSystem.translatef((screenWidth - armWidth * scale) - scaledArmLPosX, (screenHeight - armHeight * scale) - scaledArmLPosY, 0);
						RenderSystem.scalef(scale, scale, scale);
						this.blit(0, 0, 176, 80, armWidth, armHeight);
					}
					RenderSystem.popMatrix();
					RenderSystem.color4f(100.0F, 1.0F, 1.0F, 1.0F);

					// GLOVES
					int gloveWidth = 16;
					int gloveHeight = 64;
					float gloveRPosX = 48;
					float gloveRPosY = -32;
					float scaledgloveRPosX = gloveRPosX * scale;
					float scaledgloveRPosY = gloveRPosY * scale;
					float gloveLPosX = 0;
					float gloveLPosY = -32;
					float scaledgloveLPosX = gloveLPosX * scale;
					float scaledgloveLPosY = gloveLPosY * scale;

					RenderSystem.pushMatrix();
					{
						RenderSystem.translatef((screenWidth - gloveWidth * scale) - scaledgloveRPosX, (screenHeight - gloveHeight * scale) - scaledgloveRPosY, 0);
						RenderSystem.scalef(scale, scale, scale);
						this.blit(0, 0, 176, 150, gloveWidth, gloveHeight);
					}
					RenderSystem.popMatrix();

					RenderSystem.pushMatrix();
					{
						RenderSystem.translatef((screenWidth - gloveWidth * scale) - scaledgloveLPosX, (screenHeight - gloveHeight * scale) - scaledgloveLPosY, 0);
						RenderSystem.scalef(scale, scale, scale);
						this.blit(0, 0, 176, 150, gloveWidth, gloveHeight);
					}
					RenderSystem.popMatrix();

					RenderSystem.color4f(100.0F, 1.0F, 1.0F, 1.0F);

					if (!playerData.getActiveDriveForm().equals(DriveForm.NONE.toString()) && !playerData.getActiveDriveForm().equals(Strings.Form_Anti)) {
						String driveName = playerData.getActiveDriveForm().substring(playerData.getActiveDriveForm().indexOf("_") + 1);
						ResourceLocation texture = new ResourceLocation(KingdomKeys.MODID, "textures/models/armor/" + driveName + ".png");
						minecraft.textureManager.bindTexture(texture);

						RenderSystem.pushMatrix();
						{
							RenderSystem.translatef((screenWidth - 32 * scale) - 16 * scale, (screenHeight - 80 * scale) - -48 * scale, 0);
							RenderSystem.scalef(2, 1, 1);
							RenderSystem.scalef(0.5f, 0.5f, 0.5f);
							RenderSystem.scalef(scale, scale, scale);
							this.blit(0, 0, 80, 140, 32, 80);
						}
						RenderSystem.popMatrix();

						RenderSystem.pushMatrix();
						{
							RenderSystem.translatef((screenWidth - 16 * scale) - 48 * scale, (screenHeight - 80 * scale) - -48 * scale, 0);
							RenderSystem.scalef(2, 1, 1);
							RenderSystem.scalef(0.5f, 0.5f, 0.5f);
							RenderSystem.scalef(scale, scale, scale);
							this.blit(0, 0, 64, 140, 16, 80);
						}
						RenderSystem.popMatrix();

						RenderSystem.pushMatrix();
						{
							RenderSystem.translatef((screenWidth - 16 * scale) - 0 * scale, (screenHeight - 80 * scale) - -48 * scale, 0);
							RenderSystem.scalef(2, 1, 1);
							RenderSystem.scalef(0.5f, 0.5f, 0.5f);
							RenderSystem.scalef(scale, scale, scale);
							this.blit(0, 0, 112, 140, 16, 80);
						}
						RenderSystem.popMatrix();

					}
				}
				RenderSystem.popMatrix();
			}
		}
	}
}
