package online.kingdomkeys.kingdomkeys.network.cts;

import java.util.function.Supplier;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkEvent;
import online.kingdomkeys.kingdomkeys.capability.IPlayerCapabilities;
import online.kingdomkeys.kingdomkeys.capability.ModCapabilities;
import online.kingdomkeys.kingdomkeys.client.sound.ModSounds;
import online.kingdomkeys.kingdomkeys.entity.OrgPortalEntity;
import online.kingdomkeys.kingdomkeys.network.PacketHandler;
import online.kingdomkeys.kingdomkeys.network.stc.SCSyncCapabilityPacket;
import online.kingdomkeys.kingdomkeys.network.stc.SCSyncOrgPortalPacket;

public class CSSpawnOrgPortalPacket {

	BlockPos pos;
	BlockPos destPos;
	RegistryKey<World> dimension;

	public CSSpawnOrgPortalPacket() {
	}

	public CSSpawnOrgPortalPacket(BlockPos pos, BlockPos dest, RegistryKey<World> dim) {
		this.pos = pos;
		this.destPos = dest;
		this.dimension = dim;
	}

	public void encode(PacketBuffer buffer) {
		buffer.writeBlockPos(pos);
		buffer.writeBlockPos(destPos);
		buffer.writeResourceLocation(dimension.getLocation());
	}

	public static CSSpawnOrgPortalPacket decode(PacketBuffer buffer) {
		CSSpawnOrgPortalPacket msg = new CSSpawnOrgPortalPacket();
		msg.pos = buffer.readBlockPos();
		msg.destPos = buffer.readBlockPos();
		msg.dimension = RegistryKey.getOrCreateKey(Registry.WORLD_KEY, buffer.readResourceLocation());
		return msg;
	}

	public static void handle(CSSpawnOrgPortalPacket message, final Supplier<NetworkEvent.Context> ctx) {
		ctx.get().enqueueWork(() -> {
			PlayerEntity player = ctx.get().getSender();
			player.world.playSound(null, message.pos, ModSounds.portal.get(), SoundCategory.PLAYERS, 2F, 1F);
			player.world.playSound(null, message.destPos, ModSounds.portal.get(), SoundCategory.PLAYERS, 2F, 1F);

			IPlayerCapabilities playerData = ModCapabilities.getPlayer(player);
			playerData.remMP(300);
			PacketHandler.sendTo(new SCSyncCapabilityPacket(playerData), (ServerPlayerEntity)player);
			OrgPortalEntity portal = new OrgPortalEntity(player.world, player, message.pos, message.destPos, message.dimension, true);
			player.world.addEntity(portal);

			OrgPortalEntity destPortal = new OrgPortalEntity(player.world, player, message.destPos.up(), message.destPos, message.dimension, false);
			player.world.addEntity(destPortal);
			
			PacketHandler.sendToAll(new SCSyncOrgPortalPacket(message.pos, message.destPos, message.dimension), player);

		});
		ctx.get().setPacketHandled(true);
	}

}
