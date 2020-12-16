package com.github.noblekalish.gui.description;

import com.github.noblekalish.NewCivilization;
import io.github.cottonmc.cotton.gui.SyncedGuiDescription;
import io.github.cottonmc.cotton.gui.widget.WBar;
import io.github.cottonmc.cotton.gui.widget.WGridPanel;
import io.github.cottonmc.cotton.gui.widget.WItemSlot;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.util.Identifier;

public class FarmGuiDescription extends SyncedGuiDescription {
    private static final int INVENTORY_SIZE = 1;
    private static final int PROPERTY_COUNT = 2; // This should match PropertyDelegate.size().
    private static final Identifier BG_TEXTURE = new Identifier("newciv", "textures/gui/bg/bg.png");
    private static final Identifier BAR_TEXTURE = new Identifier("newciv", "textures/gui/bar/bar.png");

    public FarmGuiDescription(int syncId, PlayerInventory playerInventory, ScreenHandlerContext context) {
        super(NewCivilization.FARM_SCREEN_HANDLER, syncId, playerInventory, getBlockInventory(context, INVENTORY_SIZE), getBlockPropertyDelegate(context, PROPERTY_COUNT));

        WGridPanel root = new WGridPanel();
        setRootPanel(root);
        root.setSize(300, 200);

        WItemSlot itemSlot = WItemSlot.of(blockInventory, 0);
        WBar progressBar = new WBar(BG_TEXTURE, BAR_TEXTURE, this.propertyDelegate.get(0), this.propertyDelegate.get(1), WBar.Direction.RIGHT);
        progressBar.setSize(50, 10);
        root.add(itemSlot, 4, 1);
        root.add(progressBar, 10, 3);

        root.add(this.createPlayerInventoryPanel(), 0, 3);

        root.validate(this);
    }
}
