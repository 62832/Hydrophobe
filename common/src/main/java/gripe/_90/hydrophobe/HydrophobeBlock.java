package gripe._90.hydrophobe;

import java.util.stream.Stream;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BucketPickup;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.LiquidBlockContainer;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.MapColor;

public class HydrophobeBlock extends Block {
    private final TagKey<Fluid> fluidTag;
    private final int fluidRange;

    HydrophobeBlock(TagKey<Fluid> fluidTag, int fluidRange) {
        super(Properties.of().mapColor(MapColor.METAL).sound(SoundType.METAL).strength(2.2F, 11F));
        this.fluidTag = fluidTag;
        this.fluidRange = fluidRange;
    }

    public TagKey<Fluid> getFluidTag() {
        return fluidTag;
    }

    @Override
    public void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean notify) {
        clearFluid(level, pos);
    }

    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean moved) {
        super.onRemove(state, level, pos, newState, moved);

        var bound = fluidRange + 1;
        var p1 = pos.subtract(new Vec3i(bound, bound, bound));
        var p2 = pos.subtract(new Vec3i(-bound, -bound, -bound));

        BlockPos.betweenClosedStream(p1, p2)
                .filter(p -> Stream.of(pos.getX() - p.getX(), pos.getY() - p.getY(), pos.getZ() - p.getZ())
                        .map(Math::abs)
                        .anyMatch(i -> i == bound))
                .forEach(p -> level.getBlockState(p).neighborChanged(level, p, this, pos, false));
    }

    public void clearFluid(LevelAccessor level, BlockPos pos) {
        if (level.isClientSide()) return;

        var p1 = pos.subtract(new Vec3i(fluidRange, fluidRange, fluidRange));
        var p2 = pos.subtract(new Vec3i(-fluidRange, -fluidRange, -fluidRange));

        BlockPos.betweenClosed(p1, p2).forEach(workingPos -> {
            if (level.getFluidState(workingPos).is(fluidTag)) {
                var blockState = level.getBlockState(workingPos);

                if (blockState.getBlock() instanceof LiquidBlock) {
                    level.setBlock(workingPos, Blocks.AIR.defaultBlockState(), 3);
                } else if (blockState.getBlock() instanceof BucketPickup liquid) {
                    liquid.pickupBlock(level, workingPos, blockState);
                } else if (blockState.getBlock() instanceof LiquidBlockContainer) {
                    // account for underwater plants (kelp, seagrass etc.)
                    dropResources(blockState, level, workingPos, level.getBlockEntity(workingPos));
                    level.setBlock(workingPos, Blocks.AIR.defaultBlockState(), 3);
                }
            }
        });
    }
}
