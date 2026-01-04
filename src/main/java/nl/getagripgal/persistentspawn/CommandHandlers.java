package nl.getagripgal.persistentspawn;

import com.mojang.brigadier.context.CommandContext;

import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.arguments.coordinates.Coordinates;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.phys.Vec3;

/**
 * The command handlers for the mod.
 */
public class CommandHandlers {
    /**
     * The command handler for the `setpersistentspawn` command.
     * 
     * @param context The command context.
     * @return The exit code.
     */
    public static int setPersistentSpawn(CommandContext<CommandSourceStack> context) {
        Coordinates positionArgument = context.getArgument("position", Coordinates.class);
        Identifier dimensionArgument = context.getArgument("dimension", Identifier.class);

        PersistentSpawnManager.CurrentSpawn = positionArgument.getPosition(context.getSource());
        PersistentSpawnManager.Dimension = ResourceKey.create(Registries.DIMENSION, dimensionArgument);

        PersistentSpawnManager.syncToDisk();

        Vec3 position = PersistentSpawnManager.CurrentSpawn;
        context.getSource()
                .sendSuccess(
                        () -> Component.literal("Set spawn at %s in %s".formatted(position.toString(),
                                dimensionArgument.toString())),
                        false);
        return 1;
    }

    /**
     * The command handler for the `setpersistentspawnenabled` command.
     * 
     * @param context The command context.
     * @return The exit code.
     */
    public static int enablePersistentSpawn(CommandContext<CommandSourceStack> context) {
        boolean enable = context.getArgument("enable", Boolean.class);

        PersistentSpawnManager.Enabled = enable;
        PersistentSpawnManager.syncToDisk();

        if (!enable) {
            context.getSource()
                    .sendSuccess(
                            () -> Component.literal("Disabled persistent spawn."), false);
            return 1;
        }
        context.getSource()
                .sendSuccess(
                        () -> Component.literal("Enabled persistent spawn."), false);
        return 1;
    }
}
