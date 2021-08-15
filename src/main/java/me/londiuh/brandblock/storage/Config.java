package me.londiuh.brandblock.storage;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import net.minecraft.text.LiteralText;

import static me.londiuh.brandblock.BrandBlock.LOGGER;

public class Config {
	private List<String> blockedBrands = new ArrayList<>(Arrays.asList("forge", "vanilla"));
	private String kickMsg = "This client brand is not allowed";

	public boolean isBlockedBrand(String brand) {
		return blockedBrands.contains(brand);
	}

	// TODO: JSON text
	public LiteralText getKickMsg() {
		return new LiteralText(kickMsg);
	}

	public static Config loadConfig(File file) {
		Config config;
		if (file.exists()) {
			try (var fileReader = Files.newBufferedReader(file.toPath(), StandardCharsets.UTF_8);) {
				config = new Gson().fromJson(fileReader, Config.class);
			} catch (IOException e) {
				LOGGER.error("[BrandBlock] Couldn't read config file", e);
				config = new Config();
			}
		} else {
			config = new Config();
		}
		config.saveConfig(file);
		return config;
	}

	public void saveConfig(File file) {
		try (var fileWriter = Files.newBufferedWriter(file.toPath(), StandardCharsets.UTF_8)) {
			var gson = new GsonBuilder().setPrettyPrinting().create();
			gson.toJson(this, fileWriter);
		} catch (IOException e) {
			LOGGER.error("[BrandBlock] Couldn't write config", e);
		}
	}
}
