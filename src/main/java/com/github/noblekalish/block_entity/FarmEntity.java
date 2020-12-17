package com.github.noblekalish.block_entity;

import com.github.noblekalish.NewCivilization;
import com.github.noblekalish.gui.description.FarmGuiDescription;
import io.github.cottonmc.cotton.gui.PropertyDelegateHolder;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.LootableContainerBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.screen.PropertyDelegate;
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
import net.minecraft.util.registry.Registry;

import java.util.Random;


public class FarmEntity extends LootableContainerBlockEntity implements PropertyDelegateHolder, Tickable {
    private final String FARM_ID = "farm";
    private static final int WOOD_REQUIREMENT = 6;
    private final BlockPos offset = new BlockPos(0, -1, 1);
    private static final int MAX_PROGRESS = 75;
    private int progress = 0;

    private final Identifier structureName = new Identifier(NewCivilization.MODID, FARM_ID);
    private DefaultedList<ItemStack> items = DefaultedList.ofSize(1, ItemStack.EMPTY);


    public FarmEntity() {
        super(NewCivilization.FARM_ENTITY);
    }

    @Override
    public CompoundTag toTag(CompoundTag tag) {
        Inventories.toTag(tag, items);
        tag.putInt("progress", progress);
        return super.toTag(tag);
    }

    @Override
    public void fromTag(BlockState state, CompoundTag tag) {
        super.fromTag(state, tag);
        Inventories.fromTag(tag, items);
        progress = tag.getInt("progress");
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

    private final PropertyDelegate propertyDelegate = new PropertyDelegate() {
        @Override
        public int size() {
            // This is how many properties you have. We have two of them, so we'll return 2.
            return 2;
        }

        @Override
        public int get(int index) {
            // Each property has a unique index that you can choose.
            // Our properties will be 0 for the progress and 1 for the maximum.

            if (index == 0) {
                return progress;
            } else if (index == 1) {
                return MAX_PROGRESS;
            }

            // Unknown property IDs will fall back to -1
            return -1;
        }

        @Override
        public void set(int index, int value) {
            // This is used on the other side of the sync if you're using extended screen handlers.
            // Generally you'll want to have a working implementation for mutable properties, such as our progress.

            if (index == 0) {
                progress = value;
            }
        }
    };

    @Override
    public PropertyDelegate getPropertyDelegate() {
        return propertyDelegate;
    }

    @Override
    public void tick() {
        if (!this.world.isClient) {
            ItemStack itemStack = (ItemStack)this.items.get(0);
            if (!itemStack.isEmpty()) {
                Item item = itemStack.getItem();
                if (item.toString().contains("planks") && itemStack.getCount() >= WOOD_REQUIREMENT) {
                    ++progress;
                    if (progress == MAX_PROGRESS) {
                        this.progress = 0;
                        itemStack.decrement(WOOD_REQUIREMENT);
                        this.loadStructure((ServerWorld)this.world);
                        this.markDirty();
                    }
                } else {
                    this.progress = 0;
                }
            } else {
                this.progress = 0;
            }
        }
    }
}
