package gripe._90.hydrophobe.fabric;

import gripe._90.hydrophobe.Hydrophobe;
import gripe._90.hydrophobe.HydrophobeConfig;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.CreativeModeTabs;

public class HydrophobeFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        HydrophobeConfig.load();

        Registry.register(BuiltInRegistries.BLOCK, Hydrophobe.HYDROPHOBE_ID, Hydrophobe.HYDROPHOBE);
        Registry.register(BuiltInRegistries.BLOCK, Hydrophobe.MAGMAPHOBE_ID, Hydrophobe.MAGMAPHOBE);

        Registry.register(BuiltInRegistries.ITEM, Hydrophobe.HYDROPHOBE_ID, Hydrophobe.HYDROPHOBE_ITEM);
        Registry.register(BuiltInRegistries.ITEM, Hydrophobe.MAGMAPHOBE_ID, Hydrophobe.MAGMAPHOBE_ITEM);

        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.FUNCTIONAL_BLOCKS).register(content -> {
            content.accept(Hydrophobe.HYDROPHOBE);
            content.accept(Hydrophobe.MAGMAPHOBE);
        });
    }
}
