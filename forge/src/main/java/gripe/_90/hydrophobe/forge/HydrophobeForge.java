package gripe._90.hydrophobe.forge;

import gripe._90.hydrophobe.Hydrophobe;
import gripe._90.hydrophobe.HydrophobeConfig;
import me.shedaniel.autoconfig.AutoConfig;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.ConfigScreenHandler;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegisterEvent;

@Mod(Hydrophobe.MODID)
public class HydrophobeForge {
    public HydrophobeForge() {
        HydrophobeConfig.load();
        var bus = FMLJavaModLoadingContext.get().getModEventBus();

        bus.addListener((RegisterEvent event) -> {
            if (event.getRegistryKey().equals(Registries.BLOCK)) {
                ForgeRegistries.BLOCKS.register(Hydrophobe.HYDROPHOBE_ID, Hydrophobe.HYDROPHOBE);
                ForgeRegistries.BLOCKS.register(Hydrophobe.MAGMAPHOBE_ID, Hydrophobe.MAGMAPHOBE);
            }

            if (event.getRegistryKey().equals(Registries.ITEM)) {
                ForgeRegistries.ITEMS.register(Hydrophobe.HYDROPHOBE_ID, Hydrophobe.HYDROPHOBE_ITEM);
                ForgeRegistries.ITEMS.register(Hydrophobe.MAGMAPHOBE_ID, Hydrophobe.MAGMAPHOBE_ITEM);
            }
        });

        bus.addListener((BuildCreativeModeTabContentsEvent event) -> {
            if (event.getTabKey().equals(CreativeModeTabs.FUNCTIONAL_BLOCKS)) {
                event.accept(Hydrophobe.HYDROPHOBE);
                event.accept(Hydrophobe.MAGMAPHOBE);
            }
        });

        if (FMLEnvironment.dist == Dist.CLIENT) {
            ModLoadingContext.get()
                    .registerExtensionPoint(
                            ConfigScreenHandler.ConfigScreenFactory.class,
                            () -> new ConfigScreenHandler.ConfigScreenFactory(
                                    (client, parent) -> AutoConfig.getConfigScreen(HydrophobeConfig.class, parent)
                                            .get()));
        }
    }
}
