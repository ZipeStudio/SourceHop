package me.zipestudio.sourcehop;

import me.zipestudio.sourcehop.config.SHConfig;
import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SourceHop implements ModInitializer {
	public static final String MOD_ID = "sourcehop";

	@Override
	public void onInitialize() {
		SHConfig.GSON.load();
	}
}