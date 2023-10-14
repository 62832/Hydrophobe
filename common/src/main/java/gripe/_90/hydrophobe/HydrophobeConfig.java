package gripe._90.hydrophobe;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;

@Config(name = Hydrophobe.MODID)
public class HydrophobeConfig implements ConfigData {
    @ConfigEntry.Gui.Excluded
    static HydrophobeConfig INSTANCE;

    @ConfigEntry.BoundedDiscrete(min = 1, max = 8)
    @ConfigEntry.Gui.RequiresRestart
    int hydrophobeRange = 3;

    @ConfigEntry.BoundedDiscrete(min = 1, max = 8)
    @ConfigEntry.Gui.RequiresRestart
    int magmaphobeRange = 1;

    public static void load() {
        if (INSTANCE != null) {
            throw new IllegalStateException("Config has already been loaded");
        }

        AutoConfig.register(HydrophobeConfig.class, GsonConfigSerializer::new);
        INSTANCE = AutoConfig.getConfigHolder(HydrophobeConfig.class).getConfig();
    }
}
