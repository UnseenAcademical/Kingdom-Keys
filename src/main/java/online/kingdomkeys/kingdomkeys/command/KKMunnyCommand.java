package online.kingdomkeys.kingdomkeys.command;

import java.util.Collection;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.Util;
import net.minecraft.util.text.TranslationTextComponent;
import online.kingdomkeys.kingdomkeys.KingdomKeys;
import online.kingdomkeys.kingdomkeys.capability.IPlayerCapabilities;
import online.kingdomkeys.kingdomkeys.capability.ModCapabilities;

public class KKMunnyCommand extends BaseCommand{ //kk_munny <give/take/set> <amount> [player]
	public static void register(CommandDispatcher<CommandSource> dispatcher) {
		LiteralArgumentBuilder<CommandSource> builder = Commands.literal("kk_munny").requires(source -> source.hasPermissionLevel(3));
		
		builder.then(Commands.literal("set")
			.then(Commands.argument("value", IntegerArgumentType.integer(1,Integer.MAX_VALUE))
				.then(Commands.argument("targets", EntityArgument.players())
					.executes(KKMunnyCommand::setValue)
				)
				.executes(KKMunnyCommand::setValue)
			)
		);
		
		builder.then(Commands.literal("give")
			.then(Commands.argument("value", IntegerArgumentType.integer(1,Integer.MAX_VALUE))
				.then(Commands.argument("targets", EntityArgument.players())
					.executes(KKMunnyCommand::addValue)
				)
				.executes(KKMunnyCommand::addValue)
			)
		);
		
		builder.then(Commands.literal("take")
			.then(Commands.argument("value", IntegerArgumentType.integer(1,Integer.MAX_VALUE))
				.then(Commands.argument("targets", EntityArgument.players())
					.executes(KKMunnyCommand::removeValue)
				)
				.executes(KKMunnyCommand::removeValue)
			)
		);
		
		dispatcher.register(builder);
		KingdomKeys.LOGGER.warn("Registered command "+builder.getLiteral());
	}

	private static int setValue(CommandContext<CommandSource> context) throws CommandSyntaxException {
		Collection<ServerPlayerEntity> players = getPlayers(context, 3);
		int value = IntegerArgumentType.getInteger(context, "value");
		
		for (ServerPlayerEntity player : players) {
			setValue(context,value,player);
		}
		return 1;
	}
	
	private static int setValue(CommandContext<CommandSource> context, int value, ServerPlayerEntity player) throws CommandSyntaxException {
		IPlayerCapabilities playerData = ModCapabilities.getPlayer(player);
		playerData.setMunny(value);
		if(player != context.getSource().asPlayer()) {
			context.getSource().sendFeedback(new TranslationTextComponent("Set "+player.getDisplayName().getString()+" munny to "+value), true);
		}
		player.sendMessage(new TranslationTextComponent("Your munny has been set to "+value),Util.DUMMY_UUID);
		return 1;
	}
	
	private static int addValue(CommandContext<CommandSource> context) throws CommandSyntaxException {
		Collection<ServerPlayerEntity> players = getPlayers(context, 3);
		int value = IntegerArgumentType.getInteger(context, "value");
		
		for (ServerPlayerEntity player : players) {
			addValue(context,value,player);
		}
		return 1;
	}
	private static int addValue(CommandContext<CommandSource> context, int value, ServerPlayerEntity player) throws CommandSyntaxException {
		IPlayerCapabilities playerData = ModCapabilities.getPlayer(player);
		playerData.setMunny(playerData.getMunny() + value);
		if(player != context.getSource().asPlayer()) {
			context.getSource().sendFeedback(new TranslationTextComponent("Added "+value+" munny to "+player.getDisplayName().getString()), true);
		}
		player.sendMessage(new TranslationTextComponent("Your munny has been increased by "+value),Util.DUMMY_UUID);
		return 1;
	}
	
	private static int removeValue(CommandContext<CommandSource> context) throws CommandSyntaxException {
		Collection<ServerPlayerEntity> players = getPlayers(context, 3);
		int value = IntegerArgumentType.getInteger(context, "value");
		
		for (ServerPlayerEntity player : players) {
			removeValue(context,value,player);
		}
		return 1;
	}
	
	private static int removeValue(CommandContext<CommandSource> context, int value, ServerPlayerEntity player) throws CommandSyntaxException {
		IPlayerCapabilities playerData = ModCapabilities.getPlayer(player);
		playerData.setMunny(playerData.getMunny() - value);
		if(player != context.getSource().asPlayer()) {
			context.getSource().sendFeedback(new TranslationTextComponent("Taken "+value+" munny from "+player.getDisplayName().getString()), true);
		}
		player.sendMessage(new TranslationTextComponent("Your munny has been decreased by "+value),Util.DUMMY_UUID);
		return 1;
	}
}
