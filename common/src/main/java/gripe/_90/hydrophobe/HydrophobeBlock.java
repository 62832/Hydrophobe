package gripe._90.hydrophobe;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BucketPickup;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.LiquidBlockContainer;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Material;

public class HydrophobeBlock extends Block {
    private final TagKey<Fluid> fluidTag;
    private final int fluidRange;

    HydrophobeBlock(TagKey<Fluid> fluidTag, int fluidRange) {
        super(Properties.of(Material.METAL).sound(SoundType.METAL).strength(2.2F, 11.0F));
        this.fluidTag = fluidTag;
        this.fluidRange = fluidRange;
    }

    @Override
    public void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean notify) {
        if (level instanceof ServerLevel serverLevel) {
            HydrophobeState.getOrCreate(serverLevel).add(pos.asLong(), fluidTag.equals(Hydrophobe.MAGMAPHOBE_TAG));

            var p1 = pos.subtract(new Vec3i(fluidRange, fluidRange, fluidRange));
            var p2 = pos.subtract(new Vec3i(-fluidRange, -fluidRange, -fluidRange));
            BlockPos.betweenClosed(p1, p2).forEach(workingPos -> {
                if (level.getFluidState(workingPos).is(fluidTag)) {
                    clearFluid(level, workingPos);
                }
            });
        }
    }

    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean moved) {
        super.onRemove(state, level, pos, newState, moved);

        if (level instanceof ServerLevel serverLevel) {
            HydrophobeState.getOrCreate(serverLevel).remove(pos.asLong(), fluidTag.equals(Hydrophobe.MAGMAPHOBE_TAG));

            var bound = fluidRange + 1;
            var p1 = pos.subtract(new Vec3i(bound, bound, bound));
            var p2 = pos.subtract(new Vec3i(-bound, -bound, -bound));
            BlockPos.betweenClosedStream(p1, p2)
                    .filter(p -> isOnBoundary(p, pos, bound))
                    .forEach(p -> level.scheduleTick(p, level.getFluidState(p).getType(), 1));
        }
    }

    private boolean isOnBoundary(BlockPos point, BlockPos centre, int radius) {
        return Math.abs(point.getX() - centre.getX()) == radius
                || Math.abs(point.getY() - centre.getY()) == radius
                || Math.abs(point.getZ() - centre.getZ()) == radius;
    }

    public static void clearFluid(Level level, BlockPos pos) {
        var blockState = level.getBlockState(pos);

        if (blockState.getBlock() instanceof LiquidBlock) {
            level.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
        } else if (blockState.getBlock() instanceof BucketPickup liquid) {
            liquid.pickupBlock(level, pos, blockState);
        } else if (blockState.getBlock() instanceof LiquidBlockContainer) {
            // account for underwater plants (kelp, seagrass etc.)
            dropResources(blockState, level, pos, level.getBlockEntity(pos));
            level.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
        }
    }
}
