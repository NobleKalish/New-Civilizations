package com.github.noblekalish;

import com.github.noblekalish.client.renderer.FarmerEntityRenderer;
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
        /*
         * Registers our Cube Entity's renderer, which provides a model and texture for the entity.
         *
         * Entity Renderers can also manipulate the model before it renders based on entity context (EndermanEntityRenderer#render).
         */
        EntityRendererRegistry.INSTANCE.register(NewCivilization.FARMER_ENTITY, (dispatcher, context) -> {
            return new FarmerEntityRenderer(dispatcher);
        });
        ScreenRegistry.register(NewCivilization.FARM_SCREEN_HANDLER, FarmScreen::new);
    }
}
