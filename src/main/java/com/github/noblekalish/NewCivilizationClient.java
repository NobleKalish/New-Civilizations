package com.github.noblekalish;

import com.github.noblekalish.client.renderer.FarmerEntityRenderer;
import com.github.noblekalish.gui.description.FarmGuiDescription;
import com.github.noblekalish.screen.FarmScreen;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;

@Environment(EnvType.CLIENT)
public class NewCivilizationClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.INSTANCE.register(NewCivilization.FARMER_ENTITY, (dispatcher, context) -> new FarmerEntityRenderer(dispatcher));
        ScreenRegistry.<FarmGuiDescription, FarmScreen>register(NewCivilization.FARM_SCREEN_HANDLER, (gui, inventory, title) -> new FarmScreen(gui, inventory.player, title));
    }
}
