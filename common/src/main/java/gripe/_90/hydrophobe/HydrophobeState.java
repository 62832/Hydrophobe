package gripe._90.hydrophobe;

import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import it.unimi.dsi.fastutil.longs.LongSet;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.saveddata.SavedData;
import org.jetbrains.annotations.NotNull;

public class HydrophobeState extends SavedData {
    private final LongSet hydrophobePositions = new LongOpenHashSet();
    private final LongSet magmaphobePositions = new LongOpenHashSet();
    private final LongSet inHydrophobeRange = new LongOpenHashSet();
    private final LongSet inMagmaphobeRange = new LongOpenHashSet();

    private HydrophobeState() {}

    @NotNull
    @Override
    public CompoundTag save(CompoundTag nbt) {
        nbt.putLongArray("hydrophobePositions", hydrophobePositions.toLongArray());
        nbt.putLongArray("magmaphobePositions", magmaphobePositions.toLongArray());
        nbt.putLongArray("inHydrophobeRange", inHydrophobeRange.toLongArray());
        nbt.putLongArray("inMagmaphobeRange", inMagmaphobeRange.toLongArray());
        return nbt;
    }

    public boolean isInRange(BlockPos pos, FluidState state) {
        if (state.is(Hydrophobe.HYDROPHOBE_TAG)) {
            return inHydrophobeRange.contains(pos.asLong());
        } else if (state.is(Hydrophobe.MAGMAPHOBE_TAG)) {
            return inMagmaphobeRange.contains(pos.asLong());
        } else {
            return false;
        }
    }

    void add(long pos, boolean magmaphobe) {
        (magmaphobe ? magmaphobePositions : hydrophobePositions).add(pos);
        addInRange(
                pos,
                magmaphobe ? inMagmaphobeRange : inHydrophobeRange,
                magmaphobe ? Hydrophobe.MAGMAPHOBE_RANGE : Hydrophobe.HYDROPHOBE_RANGE);
        setDirty();
    }

    void remove(long pos, boolean magmaphobe) {
        (magmaphobe ? magmaphobePositions : hydrophobePositions).remove(pos);

        if (magmaphobe) {
            inMagmaphobeRange.clear();
            magmaphobePositions.forEach(p -> addInRange(p, inMagmaphobeRange, Hydrophobe.MAGMAPHOBE_RANGE));
        } else {
            inHydrophobeRange.clear();
            hydrophobePositions.forEach(p -> addInRange(p, inHydrophobeRange, Hydrophobe.HYDROPHOBE_RANGE));
        }

        setDirty();
    }

    private void addInRange(long position, LongSet set, int range) {
        var blockPos = BlockPos.of(position);
        var p1 = blockPos.subtract(new Vec3i(range, range, range));
        var p2 = blockPos.subtract(new Vec3i(-range, -range, -range));
        set.addAll(BlockPos.betweenClosedStream(p1, p2).map(BlockPos::asLong).toList());
    }

    public static HydrophobeState getOrCreate(ServerLevel level) {
        return level.getDataStorage().computeIfAbsent(HydrophobeState::load, HydrophobeState::new, Hydrophobe.MODID);
    }

    private static HydrophobeState load(CompoundTag tag) {
        var state = new HydrophobeState();

        for (var position : tag.getLongArray("hydrophobePositions")) {
            state.hydrophobePositions.add(position);
        }

        for (var position : tag.getLongArray("magmaphobePositions")) {
            state.magmaphobePositions.add(position);
        }

        for (var position : tag.getLongArray("inHydrophobeRange")) {
            state.inHydrophobeRange.add(position);
        }

        for (var position : tag.getLongArray("inMagmaphobeRange")) {
            state.inMagmaphobeRange.add(position);
        }

        return state;
    }
}
