package com.github.noblekalish.block_entities;

import com.github.noblekalish.NewCivilization;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.CompoundTag;

public class FarmEntity extends BlockEntity {
    private int woodAmount = 6;

    public FarmEntity() {
        super(NewCivilization.FARM_ENTITY);
    }

    @Override
    public CompoundTag toTag(CompoundTag tag) {
        super.toTag(tag);

        // Save the current value of the number to the tag
        tag.putInt("woodAmount", woodAmount);

        return tag;
    }

    @Override
    public void fromTag(BlockState state, CompoundTag tag) {
        super.fromTag(state, tag);
        woodAmount = tag.getInt("woodAmount");
    }
}
 