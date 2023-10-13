package gripe._90.hydrophobe.fabric;

import gripe._90.hydrophobe.Hydrophobe;
import gripe._90.hydrophobe.HydrophobeConfig;
import net.fabricmc.api.ModInitializer;
import net.minecraft.core.Registry;

public class HydrophobeFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        HydrophobeConfig.load();

        Registry.register(Registry.BLOCK, Hydrophobe.HYDROPHOBE_ID, Hydrophobe.HYDROPHOBE);
        Registry.register(Registry.BLOCK, Hydrophobe.MAGMAPHOBE_ID, Hydrophobe.MAGMAPHOBE);

        Registry.register(Registry.ITEM, Hydrophobe.HYDROPHOBE_ID, Hydrophobe.HYDROPHOBE_ITEM);
        Registry.register(Registry.ITEM, Hydrophobe.MAGMAPHOBE_ID, Hydrophobe.MAGMAPHOBE_ITEM);
    }
}
