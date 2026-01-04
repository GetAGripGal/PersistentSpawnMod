package nl.getagripgal.persistentspawn;

import java.util.Set;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;

/**
 * The event handlers for the mod.
 */
public class EventHandlers {
    /**
     * The handler for the player join event.
     * 
     * @param player
     */
    @SuppressWarnings("null")
    public static void onPlayerJoin(ServerPlayer player) {
        if (!PersistentSpawnManager.Enabled) {
            return;
        }

        ServerLevel level = (ServerLevel) player.level().getServer().getLevel(PersistentSpawnManager.Dimension);
        player.teleportTo(level, PersistentSpawnManager.CurrentSpawn.x, PersistentSpawnManager.CurrentSpawn.y,
                PersistentSpawnManager.CurrentSpawn.z, Set.of(), 0.0f, 0.0f, false);
    }
}
