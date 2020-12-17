package com.github.noblekalish.screen;

import com.github.noblekalish.gui.description.FarmGuiDescription;
import com.mojang.blaze3d.systems.RenderSystem;
import io.github.cottonmc.cotton.gui.client.CottonInventoryScreen;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class FarmScreen extends CottonInventoryScreen<FarmGuiDescription> {
    public FarmScreen(FarmGuiDescription gui, PlayerEntity player, Text title) {
        super(gui, player, title);
    }
}
