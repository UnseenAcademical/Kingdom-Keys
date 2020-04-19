package online.kingdomkeys.kingdomkeys.client.render;

import javax.annotation.Nullable;

import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.Vector3f;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import online.kingdomkeys.kingdomkeys.KingdomKeys;
import online.kingdomkeys.kingdomkeys.entity.magic.EntityFire;
import online.kingdomkeys.kingdomkeys.models.ModelFire;

@OnlyIn(Dist.CLIENT)
public class EntityFireRenderer extends EntityRenderer<EntityFire> {

	public static final Factory FACTORY = new EntityFireRenderer.Factory();
	ModelFire shot;
	int test;

	public EntityFireRenderer(EntityRendererManager renderManager, ModelFire fist) {
		super(renderManager);
		this.shot = fist;
		this.shadowSize = 0.25F;
	}

	@Override
	public void render(EntityFire entity, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
		matrixStackIn.push();
		{
			float r = 0, g = 1, b = 0;
				
			matrixStackIn.rotate(Vector3f.YP.rotationDegrees(entity.prevRotationYaw + (entity.rotationYaw - entity.prevRotationYaw)));
			matrixStackIn.rotate(Vector3f.XN.rotationDegrees(entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch)));

			if (entity.ticksExisted > 1) //Prevent entity rendering in your face
				shot.render(matrixStackIn, bufferIn.getBuffer(shot.getRenderType(getEntityTexture(entity))), packedLightIn, OverlayTexture.NO_OVERLAY, r, g, b, 1F);

		}
		matrixStackIn.pop();
		super.render(entity, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
	}

	@Nullable
	@Override
	public ResourceLocation getEntityTexture(EntityFire entity) {
		return new ResourceLocation(KingdomKeys.MODID, "textures/models/fist.png");
	}

	public static class Factory implements IRenderFactory<EntityFire> {
		@Override
		public EntityRenderer<? super EntityFire> createRenderFor(EntityRendererManager manager) {
			return new EntityFireRenderer(manager, new ModelFire());
		}
	}
}
