package gripe._90.hydrophobe.mixin;

import gripe._90.hydrophobe.HydrophobeBlock;
import gripe._90.hydrophobe.HydrophobeState;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.FluidState;
import org.spongepowered.asm.mixin.Mixin;
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
        if (level instanceof ServerLevel serverLevel) {
            if (HydrophobeState.getOrCreate(serverLevel).isInRange(pos, fluidState)) {
                ci.cancel();
            }
        }
    }

    @Inject(method = "tick", at = @At("HEAD"), cancellable = true)
    private void clearWithinRange(Level level, BlockPos pos, FluidState state, CallbackInfo ci) {
        if (level instanceof ServerLevel serverLevel) {
            if (HydrophobeState.getOrCreate(serverLevel).isInRange(pos, state)) {
                HydrophobeBlock.clearFluid(level, pos);
                ci.cancel();
            }
        }
    }
}
