package gripe._90.hydrophobe;

import java.util.function.IntSupplier;
import java.util.function.Supplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.DeferredRegister;

@Mod(Hydrophobe.MODID)
public class Hydrophobe {
    static final String MODID = "hydrophobe";

    private static final DeferredRegister<Block> BLOCKS = DeferredRegister.createBlocks(MODID);
    private static final DeferredRegister<Item> ITEMS = DeferredRegister.createItems(MODID);

    public static final TagKey<Fluid> HYDROPHOBE_TAG = tag("hydrophobe");
    public static final IntSupplier HYDROPHOBE_RANGE = HydrophobeConfig.CONFIG::getHydrophobeRange;
    public static final Supplier<Block> HYDROPHOBE = block("hydrophobe", HYDROPHOBE_TAG, HYDROPHOBE_RANGE);

    public static final TagKey<Fluid> MAGMAPHOBE_TAG = tag("magmaphobe");
    public static final IntSupplier MAGMAPHOBE_RANGE = HydrophobeConfig.CONFIG::getMagmaphobeRange;
    public static final Supplier<Block> MAGMAPHOBE = block("magmaphobe", MAGMAPHOBE_TAG, MAGMAPHOBE_RANGE);

    public Hydrophobe(ModContainer container, IEventBus modBus) {
        container.registerConfig(ModConfig.Type.COMMON, HydrophobeConfig.SPEC);

        BLOCKS.register(modBus);
        ITEMS.register(modBus);

        modBus.addListener((BuildCreativeModeTabContentsEvent event) -> {
            if (event.getTabKey().equals(CreativeModeTabs.FUNCTIONAL_BLOCKS)) {
                event.accept(HYDROPHOBE.get());
                event.accept(MAGMAPHOBE.get());
            }
        });
    }

    private static TagKey<Fluid> tag(String block) {
        return TagKey.create(Registries.FLUID, ResourceLocation.fromNamespaceAndPath(MODID, block + "_affected"));
    }

    private static Supplier<Block> block(String id, TagKey<Fluid> tag, IntSupplier range) {
        return BLOCKS.register(id, () -> {
            var block = new HydrophobeBlock(tag, range);
            ITEMS.register(id, () -> new BlockItem(block, new Item.Properties()));
            return block;
        });
    }
}
