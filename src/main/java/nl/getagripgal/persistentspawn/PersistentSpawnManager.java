package nl.getagripgal.persistentspawn;

import java.io.File;

import com.moandjiezana.toml.Toml;
import com.moandjiezana.toml.TomlWriter;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

/**
 * Manages the persistent spawn point.
 */
public class PersistentSpawnManager {
    /**
     * The config file path.
     */
    public static final String CONFIG_FILE = "config/persistentspawn.toml";

    /**
     * The currently registered spawn position.
     */
    public static Vec3 CurrentSpawn = Vec3.ZERO;

    /**
     * The dimension of the spawn location.
     */
    public static ResourceKey<Level> Dimension = null;

    /**
     * Whether the persistent spawn is enabled.
     */
    public static boolean Enabled = false;

    /**
     * Load the spawn config from disk.
     */
    public static void loadFromDisk() {
        try {
            Toml toml = new Toml().read(new File(CONFIG_FILE));
            PersistentSpawnConfig config = toml.to(PersistentSpawnConfig.class);

            CurrentSpawn = new Vec3(config.x, config.y, config.z);
            Dimension = ResourceKey.create(Registries.DIMENSION, Identifier.parse(config.dimension));
            Enabled = config.enabled;
        } catch (Exception e) {
            PersistentSpawn.LOGGER.error(
                    "Failed to load persistent spawn config from disk, using defaults. This means persistent spawn will be disabled.",
                    e);
            PersistentSpawnConfig defaultConfig = PersistentSpawnConfig.defaultConfig();
            CurrentSpawn = new Vec3(defaultConfig.x, defaultConfig.y, defaultConfig.z);
            Dimension = ResourceKey.create(Registries.DIMENSION, Identifier.parse(defaultConfig.dimension));
            Enabled = defaultConfig.enabled;
        }
    }

    /**
     * Sync the current spawn config to spawn.
     */
    public static void syncToDisk() {
        try {
            PersistentSpawnConfig config = new PersistentSpawnConfig();
            config.x = (int) CurrentSpawn.x;
            config.y = (int) CurrentSpawn.y;
            config.z = (int) CurrentSpawn.z;
            config.dimension = Dimension.identifier().toString();
            config.enabled = Enabled;

            TomlWriter writer = new TomlWriter();
            writer.write(config, new File(CONFIG_FILE));
        } catch (Exception e) {
            PersistentSpawn.LOGGER.error("Failed to save persistent spawn config to disk.", e);
        }
    }
}
