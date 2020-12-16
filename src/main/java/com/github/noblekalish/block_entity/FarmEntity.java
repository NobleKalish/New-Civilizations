package com.github.noblekalish.block_entity;

import com.github.noblekalish.NewCivilization;
import com.github.noblekalish.gui.description.FarmGuiDescription;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.LootableContainerBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.structure.Structure;
import net.minecraft.structure.StructureManager;
import net.minecraft.structure.StructurePlacementData;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.*;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;

import java.util.Random;


public class FarmEntity extends LootableContainerBlockEntity {
    private final String FARM_ID = "farm";
    private final int woodRequirement = 6;
    private final BlockPos offset = new BlockPos(0, -1, 0);

    private final Identifier structureName = new Identifier(NewCivilization.MODID, FARM_ID);
    private DefaultedList<ItemStack> items = DefaultedList.ofSize(1, ItemStack.EMPTY);


    public FarmEntity() {
        super(NewCivilization.FARM_ENTITY);
    }

    @Override
    public CompoundTag toTag(CompoundTag tag) {
        super.toTag(tag);
        Inventories.toTag(tag, items);
        return tag;
    }

    @Override
    public void fromTag(BlockState state, CompoundTag tag) {
        super.fromTag(state, tag);
        Inventories.fromTag(tag, items);
    }

    @Override
    protected DefaultedList<ItemStack> getInvStackList() {
        return items;
    }

    @Override
    protected void setInvStackList(DefaultedList<ItemStack> list) {
        this.items = list;
    }

    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory inventory, PlayerEntity player) {
        return new FarmGuiDescription(syncId, inventory, ScreenHandlerContext.create(world, pos));
    }

    @Override
    protected ScreenHandler createScreenHandler(int syncId, PlayerInventory playerInventory) {
        return null;
    }

    @Override
    public Text getDisplayName() {
        return new TranslatableText(getCachedState().getBlock().getTranslationKey());
    }

    @Override
    protected Text getContainerName() {
        return Text.of("Farm");
    }

    public boolean loadStructure(ServerWorld serverWorld) {
            StructureManager structureManager = serverWorld.getStructureManager();

            Structure structure2;
            try {
                structure2 = structureManager.getStructure(this.structureName);
            } catch (InvalidIdentifierException var6) {
                return false;
            }

            return structure2 != null && this.place(serverWorld, structure2);
    }

    public boolean place(ServerWorld serverWorld, Structure structure) {
        BlockPos blockPos = this.getPos();
        StructurePlacementData structurePlacementData = (new StructurePlacementData()).setMirror(BlockMirror.NONE).setRotation(BlockRotation.NONE).setIgnoreEntities(false).setChunkPosition((ChunkPos) null);

        BlockPos blockPos3 = blockPos.add(this.offset);
        structure.place(serverWorld, blockPos3, structurePlacementData, createRandom(0));
        return true;
    }

    private static Random createRandom(long seed) {
        return seed == 0L ? new Random(Util.getMeasuringTimeMs()) : new Random(seed);
    }

    @Override
    public int size() {
        return items.size();
    }
}
