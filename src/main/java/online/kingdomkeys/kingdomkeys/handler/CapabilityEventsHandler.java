package online.kingdomkeys.kingdomkeys.handler;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import online.kingdomkeys.kingdomkeys.KingdomKeys;
import online.kingdomkeys.kingdomkeys.capability.CapabilitiesProvider;

public class CapabilityEventsHandler {

	@SubscribeEvent
	public void attachCapabilities(AttachCapabilitiesEvent<Entity> event) {
		if (event.getObject() instanceof PlayerEntity) {
			event.addCapability(new ResourceLocation(KingdomKeys.MODID, "level_capability"), new CapabilitiesProvider());
		//	event.addCapability(new ResourceLocation(Reference.MODID, "test"), new TestCapabilityProvider());
		}
	}
	
	/*@SubscribeEvent
	public void onPlayerJoin(EntityJoinWorldEvent event) { //I used this to sync to the client the other players skins in RayCraft idk if it's gonna be useful for drive forms
		if (!event.getEntity().world.isRemote && event.getEntity() instanceof PlayerEntity) {
			ILevelCapabilities props = ModCapabilities.get((PlayerEntity) event.getEntity());
			PacketHandler.sendTo(new PacketSyncCapability(props), (ServerPlayerEntity) event.getEntity());
		}
	}*/

}