package com.github.noblekalish.blocks;

import com.github.noblekalish.block_entities.FarmEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.world.BlockView;

public class FarmBlock extends Block implements BlockEntityProvider {

    public FarmBlock(Settings settings) {
        super(settings);
    }

    @Override
    public BlockEntity createBlockEntity(BlockView world) {
        return new FarmEntity();
    }
}
