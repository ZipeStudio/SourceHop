package me.zipestudio.sourcehop.keybinding;

import me.zipestudio.sourcehop.SHServer;
import me.zipestudio.sourcehop.client.SHClient;
import me.zipestudio.sourcehop.config.SourceHopConfig;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

public class SourceHopKeybinding {

    public static final KeyBinding SH_TOGGLE_KEYBINDING = new KeyBinding(
            "sourcehop.text.keybinding.modToggle",
            InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_H,
            SHServer.MOD_NAME
    );

    public static void register() {
        registerDefaultKeys();

        ClientTickEvents.START_CLIENT_TICK.register((client -> {

            if (SH_TOGGLE_KEYBINDING.wasPressed()) {
                if (client.player == null) {
                    return;
                }

                SourceHopConfig clientConfig = SHClient.getConfig();

                boolean toggle = !clientConfig.isEnableStrafing();
                clientConfig.setEnableStrafing(toggle);

                client.player.sendMessage(
                        Text.translatable(SHServer.MOD_NAME)
                                .append(" ")
                                .append(Text.translatable("sourcehop.text.keybinding.modToggle." + toggle))
                        ,
                        true
                );

            }

        }));
    }

    private static void registerDefaultKeys() {
        registerKeyBinding(SH_TOGGLE_KEYBINDING);
    }

    public static void registerKeyBinding(KeyBinding keyBinding) {
        KeyBindingHelper.registerKeyBinding(keyBinding);
    }

}
