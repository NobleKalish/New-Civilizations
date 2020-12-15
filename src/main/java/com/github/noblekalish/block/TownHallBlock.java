package com.github.noblekalish.block;

import com.github.noblekalish.block_entity.TownHallEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.world.BlockView;

public class TownHallBlock extends Block implements BlockEntityProvider {
    public TownHallBlock(Settings settings) {
        super(settings);
    }

    @Override
    public BlockEntity createBlockEntity(BlockView world) {
        return new TownHallEntity();
    }
}
