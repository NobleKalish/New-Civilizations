package com.github.noblekalish.block_entities;

import com.github.noblekalish.NewCivilization;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.CompoundTag;

public class TownHallEntity extends BlockEntity {
    public TownHallEntity() {
        super(NewCivilization.TOWN_HALL_ENTITY);
    }

    public CompoundTag toTag(CompoundTag tag) {
        super.toTag(tag);
//      Save the current value of the number to the tag
//      tag.putInt("number", number);
        return tag;
    }

    public void fromTag(BlockState state, CompoundTag tag) {
        super.fromTag(state, tag);
//        number = tag.getInt("number");
    }
}
