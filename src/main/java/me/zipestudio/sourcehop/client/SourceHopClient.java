package me.zipestudio.sourcehop.client;

import lombok.Getter;
import me.zipestudio.sourcehop.config.SourceHopConfig;
import me.zipestudio.sourcehop.keybinding.SourceHopKeybinding;
import net.fabricmc.api.ClientModInitializer;

public class SourceHopClient implements ClientModInitializer {

    @Getter
    private static SourceHopConfig config;

    @Override
    public void onInitializeClient() {

        SourceHopKeybinding.register();

        if (SourceHopConfig.GSON.load()) {
            config = SourceHopConfig.GSON.instance();
        }

    }

}