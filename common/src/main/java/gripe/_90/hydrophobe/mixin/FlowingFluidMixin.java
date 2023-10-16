package gripe._90.hydrophobe.mixin;

import gripe._90.hydrophobe.Hydrophobe;
import gripe._90.hydrophobe.HydrophobeBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.FluidState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FlowingFluid.class)
public class FlowingFluidMixin {
    @Inject(method = "spreadTo", at = @At("HEAD"), cancellable = true)
    private void suppressFlow(
            LevelAccessor level,
            BlockPos pos,
            BlockState blockState,
            Direction direction,
            FluidState fluidState,
            CallbackInfo ci) {
        if (fluidState.is(Hydrophobe.HYDROPHOBE_TAG)) {
            hydrophobe$suppress(level, pos, fluidState, ci, Hydrophobe.HYDROPHOBE_RANGE);
        } else if (fluidState.is(Hydrophobe.MAGMAPHOBE_TAG)) {
            hydrophobe$suppress(level, pos, fluidState, ci, Hydrophobe.MAGMAPHOBE_RANGE);
        }
    }

    @Unique
    private void hydrophobe$suppress(LevelAccessor level, BlockPos pos, FluidState state, CallbackInfo ci, int range) {
        var p1 = pos.subtract(new Vec3i(range, range, range));
        var p2 = pos.subtract(new Vec3i(-range, -range, -range));

        for (var p : BlockPos.betweenClosed(p1, p2)) {
            if (level.getBlockState(p).getBlock() instanceof HydrophobeBlock hydrophobe) {
                if (state.is(hydrophobe.getFluidTag())) {
                    hydrophobe.clearFluid(level, p);
                    ci.cancel();
                    break;
                }
            }
        }
    }
}
