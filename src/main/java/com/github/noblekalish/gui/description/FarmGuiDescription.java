package com.github.noblekalish.gui.description;

import com.github.noblekalish.NewCivilization;
import io.github.cottonmc.cotton.gui.SyncedGuiDescription;
import io.github.cottonmc.cotton.gui.widget.WButton;
import io.github.cottonmc.cotton.gui.widget.WGridPanel;
import io.github.cottonmc.cotton.gui.widget.WItemSlot;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.text.LiteralText;

public class FarmGuiDescription extends SyncedGuiDescription {
    private static final int INVENTORY_SIZE = 1;
    private static final int PROPERTY_COUNT = 2; // This should match PropertyDelegate.size().

    public FarmGuiDescription(int syncId, PlayerInventory playerInventory, ScreenHandlerContext context) {
        super(NewCivilization.FARM_SCREEN_HANDLER, syncId, playerInventory, getBlockInventory(context, INVENTORY_SIZE), getBlockPropertyDelegate(context, PROPERTY_COUNT));

        WGridPanel root = new WGridPanel();
        setRootPanel(root);
        root.setSize(300, 200);

        WItemSlot itemSlot = WItemSlot.of(blockInventory, 0);
        root.add(itemSlot, 4, 1);

        root.add(this.createPlayerInventoryPanel(), 0, 3);
        WButton buildButton = new WButton(new LiteralText("Build Farm!"));
        buildButton.setOnClick(() -> {
            System.out.println("Button clicked!");
        });
        root.add(buildButton, 20, 20, 50, 20);

        root.validate(this);
    }
}
