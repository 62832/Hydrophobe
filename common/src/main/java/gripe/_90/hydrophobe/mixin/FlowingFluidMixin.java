package gripe._90.hydrophobe.mixin;

import gripe._90.hydrophobe.Hydrophobe;
import gripe._90.hydrophobe.HydrophobeBlock;
import java.util.stream.Stream;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.FluidState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FlowingFluid.class)
public class FlowingFluidMixin {
    @Inject(method = "spread", at = @At("HEAD"), cancellable = true)
    private void suppressFlow(Level level, BlockPos pos, FluidState state, CallbackInfo ci) {
        if (state.is(FluidTags.WATER)) {
            hydrophobe$suppress(level, pos, state, ci, Hydrophobe.WATER_RANGE);
        } else if (state.is(FluidTags.LAVA)) {
            hydrophobe$suppress(level, pos, state, ci, Hydrophobe.LAVA_RANGE);
        }
    }

    @Unique
    private void hydrophobe$suppress(Level level, BlockPos pos, FluidState state, CallbackInfo ci, int range) {
        var bound = range + 1;
        var p1 = pos.subtract(new Vec3i(bound, bound, bound));
        var p2 = pos.subtract(new Vec3i(-bound, -bound, -bound));

        BlockPos.betweenClosedStream(p1, p2)
                .filter(p -> Stream.of(pos.getX() - p.getX(), pos.getY() - p.getY(), pos.getZ() - p.getZ())
                        .map(Math::abs)
                        .anyMatch(i -> i == bound))
                .forEach(p -> {
                    if (level.getBlockState(p).getBlock() instanceof HydrophobeBlock hydrophobe) {
                        if (state.is(hydrophobe.getFluidTag())) {
                            ci.cancel();
                        }
                    }
                });
    }
}
