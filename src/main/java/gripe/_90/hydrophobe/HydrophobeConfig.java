package gripe._90.hydrophobe;

import net.neoforged.neoforge.common.ModConfigSpec;

public class HydrophobeConfig {
    static final HydrophobeConfig CONFIG;
    static final ModConfigSpec SPEC;

    static {
        var configured = new ModConfigSpec.Builder().configure(HydrophobeConfig::new);
        CONFIG = configured.getKey();
        SPEC = configured.getValue();
    }

    private final ModConfigSpec.IntValue hydrophobeRange;
    private final ModConfigSpec.IntValue magmaphobeRange;

    private HydrophobeConfig(ModConfigSpec.Builder builder) {
        hydrophobeRange = builder.defineInRange("hydrophobeRange", 3, 1, 8);
        magmaphobeRange = builder.defineInRange("magmaphobeRange", 1, 1, 8);
    }

    int getHydrophobeRange() {
        return hydrophobeRange.getAsInt();
    }

    int getMagmaphobeRange() {
        return magmaphobeRange.getAsInt();
    }
}
