package me.zipestudio.sourcehop;

import lombok.Getter;
import me.zipestudio.sourcehop.config.SHConfig;
import net.fabricmc.api.ModInitializer;

public class SourceHop implements ModInitializer {
	public static final String MOD_ID = "sourcehop";
	@Getter
	private static SHConfig config;

	@Override
	public void onInitialize() {
		if (SHConfig.GSON.load()) {
			config = SHConfig.GSON.instance();
		}
	}
}