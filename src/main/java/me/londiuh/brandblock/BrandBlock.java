package me.londiuh.brandblock;

import java.io.File;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import me.londiuh.brandblock.storage.Config;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;

public class BrandBlock implements ModInitializer {
	private static final File CONFIG_FILE = FabricLoader.getInstance().getConfigDir().resolve("brandblock.json").toFile();
	public static final Logger LOGGER = LogManager.getLogger("BrandBlock");
	public static final Config CONFIG = Config.loadConfig(CONFIG_FILE);

	@Override
	public void onInitialize() {
		LOGGER.info("[BrandBlock] Mod loaded");
	}
}
