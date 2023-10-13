package gripe._90.hydrophobe.forge;

import gripe._90.hydrophobe.Hydrophobe;
import gripe._90.hydrophobe.HydrophobeConfig;
import me.shedaniel.autoconfig.AutoConfig;
import net.minecraft.core.Registry;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.ConfigScreenHandler;
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
            if (event.getRegistryKey().equals(Registry.BLOCK_REGISTRY)) {
                ForgeRegistries.BLOCKS.register(Hydrophobe.HYDROPHOBE_ID, Hydrophobe.HYDROPHOBE);
                ForgeRegistries.BLOCKS.register(Hydrophobe.MAGMAPHOBE_ID, Hydrophobe.MAGMAPHOBE);
            }

            if (event.getRegistryKey().equals(Registry.ITEM_REGISTRY)) {
                ForgeRegistries.ITEMS.register(Hydrophobe.HYDROPHOBE_ID, Hydrophobe.HYDROPHOBE_ITEM);
                ForgeRegistries.ITEMS.register(Hydrophobe.MAGMAPHOBE_ID, Hydrophobe.MAGMAPHOBE_ITEM);
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
