package nl.getagripgal.persistentspawn;

/**
 * The spawn config as stored on disk.
 */
public class PersistentSpawnConfig {
    public double x;
    public double y;
    public double z;
    public String dimension;
    public boolean enabled;

    public static PersistentSpawnConfig defaultConfig() {
        PersistentSpawnConfig config = new PersistentSpawnConfig();
        config.x = 0.0;
        config.y = 100.0;
        config.z = 0.0;
        config.dimension = "minecraft:overworld";
        config.enabled = false;
        return config;
    }
}
