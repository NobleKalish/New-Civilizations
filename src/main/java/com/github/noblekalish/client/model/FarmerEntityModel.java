package com.github.noblekalish.client.model;

import com.github.noblekalish.entity.FarmerEntity;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;

public class FarmerEntityModel extends EntityModel<FarmerEntity> {
    private final ModelPart base;

    public FarmerEntityModel() {
        base = new ModelPart(this, 0, 0);
        base.addCuboid(-6, -6, -6, 12, 12, 12);
        this.textureHeight = 64;
        this.textureWidth = 64;
    }

    @Override
    public void setAngles(FarmerEntity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {

    }

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha) {
        // translate model down
        matrices.translate(0, 1.125, 0);

        // render cube
        base.render(matrices, vertices, light, overlay);
    }
}
