package com.github.noblekalish.block_entities;

import com.github.noblekalish.ImplementedInventory;
import com.github.noblekalish.NewCivilization;
import com.github.noblekalish.screen_handlers.FarmScreenHandler;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;

public class FarmEntity extends BlockEntity implements NamedScreenHandlerFactory, ImplementedInventory {
    private final DefaultedList<ItemStack> items = DefaultedList.ofSize(9, ItemStack.EMPTY);
    private int woodRequirement = 6;
    private Identifier structureName;

    public FarmEntity() {
        super(NewCivilization.FARM_ENTITY);
    }

    @Override
    public CompoundTag toTag(CompoundTag tag) {
        super.toTag(tag);

        // Save the current value of the number to the tag
        tag.putInt("woodRequirement", woodRequirement);
        Inventories.toTag(tag, items);
        return tag;
    }

    @Override
    public void fromTag(BlockState state, CompoundTag tag) {
        super.fromTag(state, tag);
        woodRequirement = tag.getInt("woodRequirement");
        Inventories.fromTag(tag, items);
    }

    @Override
    public DefaultedList<ItemStack> getItems() {
        return items;
    }

    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        //We provide *this* to the screenHandler as our class Implements Inventory
        //Only the Server has the Inventory at the start, this will be synced to the client in the ScreenHandler
        return new FarmScreenHandler(syncId, playerInventory, this);
    }

    @Override
    public Text getDisplayName() {
        return new TranslatableText(getCachedState().getBlock().getTranslationKey());
    }
}
