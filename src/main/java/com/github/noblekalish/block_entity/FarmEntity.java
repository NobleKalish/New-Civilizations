package com.github.noblekalish.block_entity;

import com.github.noblekalish.ImplementedInventory;
import com.github.noblekalish.NewCivilization;
import com.github.noblekalish.screen_handlers.FarmScreenHandler;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.enums.StructureBlockMode;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.structure.Structure;
import net.minecraft.structure.StructureManager;
import net.minecraft.structure.StructurePlacementData;
import net.minecraft.structure.processor.BlockRotStructureProcessor;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.*;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;

import java.util.Random;


public class FarmEntity extends BlockEntity implements NamedScreenHandlerFactory, ImplementedInventory {
    private final String FARM_ID = "farm";
    private final int woodRequirement = 6;
    private final BlockPos offset = new BlockPos(0, -1, 0);

    private final Identifier structureName = new Identifier(NewCivilization.MODID, FARM_ID);
    private final DefaultedList<ItemStack> items = DefaultedList.ofSize(9, ItemStack.EMPTY);


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

//        BlockPos blockPos2 = structure.getSize();
//        boolean bl2 = this.size.equals(blockPos2);
//        if (!bl2) {
//            this.size = blockPos2;
//            this.markDirty();
//            BlockState blockState = serverWorld.getBlockState(blockPos);
//            serverWorld.updateListeners(blockPos, blockState, blockState, 3);
//        }
//        if (bl && !bl2) {
//            return false;
//        } else {
//            StructurePlacementData structurePlacementData = (new StructurePlacementData()).setMirror(this.mirror).setRotation(this.rotation).setIgnoreEntities(this.ignoreEntities).setChunkPosition((ChunkPos) null);
//            if (this.integrity < 1.0F) {
//                structurePlacementData.clearProcessors().addProcessor(new BlockRotStructureProcessor(MathHelper.clamp(this.integrity, 0.0F, 1.0F))).setRandom(createRandom(this.seed));
//            }
//
//            BlockPos blockPos3 = blockPos.add(this.offset);
//            structure.place(serverWorld, blockPos3, structurePlacementData, createRandom(this.seed));
//            return true;
//        }
        StructurePlacementData structurePlacementData = (new StructurePlacementData()).setMirror(BlockMirror.NONE).setRotation(BlockRotation.NONE).setIgnoreEntities(false).setChunkPosition((ChunkPos) null);
//        if (this.integrity < 1.0F) {
//            structurePlacementData.clearProcessors().addProcessor(new BlockRotStructureProcessor(MathHelper.clamp(this.integrity, 0.0F, 1.0F))).setRandom(createRandom(this.seed));
//        }

        BlockPos blockPos3 = blockPos.add(this.offset);
        structure.place(serverWorld, blockPos3, structurePlacementData, createRandom(0));
        return true;
    }

    private static Random createRandom(long seed) {
        return seed == 0L ? new Random(Util.getMeasuringTimeMs()) : new Random(seed);
    }
}
