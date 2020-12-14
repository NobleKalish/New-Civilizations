package com.github.noblekalish.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ExampleBlock extends Block {
    public static final BooleanProperty HARDENED = BooleanProperty.of("hardened");

    public ExampleBlock(Settings settings) {
        super(settings);
        setDefaultState(getStateManager().getDefaultState().with(HARDENED, false));
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> stateManager) {
        stateManager.add(HARDENED);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        world.setBlockState(pos, state.with(HARDENED, true));
        return ActionResult.SUCCESS;
    }


}
