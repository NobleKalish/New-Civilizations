package com.github.noblekalish.client.renderer;

import com.github.noblekalish.client.model.FarmerEntityModel;
import com.github.noblekalish.entity.FarmerEntity;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;

public class FarmerEntityRenderer extends MobEntityRenderer<FarmerEntity, FarmerEntityModel> {
    public FarmerEntityRenderer(EntityRenderDispatcher entityRenderDispatcher) {
        super(entityRenderDispatcher, new FarmerEntityModel(), 0.5f);
    }

    @Override
    public Identifier getTexture(FarmerEntity entity) {
        return new Identifier("newciv", "textures/entity/farmer/farmer.png");
    }
}