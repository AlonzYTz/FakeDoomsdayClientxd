package com.fakedoomsday.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

public class FakeDoomsdayClient implements ClientModInitializer {
    private static KeyBinding open;
    public static boolean guiOpen = false;

    @Override public void onInitializeClient() {
        open = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            "key.fakedoomsday.gui", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_RIGHT_SHIFT,
            "category.fakedoomsday"));
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (open.wasPressed()) guiOpen = !guiOpen;
            if (guiOpen && client.currentScreen == null)
                client.setScreen(new FakeScreen());
        });
    }
}
