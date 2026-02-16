package nl.getagripgal.persistentspawn;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mojang.brigadier.arguments.BoolArgumentType;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.DimensionArgument;
import net.minecraft.commands.arguments.coordinates.Vec3Argument;

/**
 * A very simple mod that implements the functionality of spawning a player at
 * the same location on every join.
 */
public class PersistentSpawn implements ModInitializer {
	public static final String MOD_ID = "persistentspawn";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	/**
	 * The permission level for the operator.
	 */
	private static final int PERMISSION_OP = 2;

	@Override
	public void onInitialize() {
		registerCommands();
		registerEvents();
		PersistentSpawnManager.loadFromDisk();
	}

	/**
	 * Register the commands.
	 */
	private void registerCommands() {
		CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> dispatcher
				.register(Commands.literal("setpersistentspawn")
						.requires(source -> source.hasPermission(PERMISSION_OP))
						.then(Commands.argument("position", Vec3Argument.vec3())
								.then(Commands.argument("dimension", DimensionArgument.dimension())
										.executes(CommandHandlers::setPersistentSpawn)))));

		CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> dispatcher
				.register(Commands.literal("setpersistentspawnenabled")
						.requires(source -> source.hasPermission(PERMISSION_OP))
						.then(Commands.argument("enable", BoolArgumentType.bool())
								.executes(CommandHandlers::enablePersistentSpawn))));
	}

	/**
	 * Register the events.
	 */
	private void registerEvents() {
		ServerPlayerEvents.JOIN.register(EventHandlers::onPlayerJoin);
	}
}