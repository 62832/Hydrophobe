package gripe._90.hydrophobe;

import java.util.function.Function;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluid;

public class Hydrophobe {
    public static final String MODID = "hydrophobe";
    private static final Function<String, ResourceLocation> ID = id -> new ResourceLocation(MODID, id); // :^)

    public static final int HYDROPHOBE_RANGE = HydrophobeConfig.INSTANCE.hydrophobeRange;
    public static final int MAGMAPHOBE_RANGE = HydrophobeConfig.INSTANCE.magmaphobeRange;

    private static final Item.Properties ITEM_PROPS = new Item.Properties();

    public static final ResourceLocation HYDROPHOBE_ID = ID.apply("hydrophobe");
    public static final TagKey<Fluid> HYDROPHOBE_TAG = TagKey.create(Registries.FLUID, ID.apply("hydrophobe_affected"));
    public static final Block HYDROPHOBE = new HydrophobeBlock(HYDROPHOBE_TAG, HYDROPHOBE_RANGE);
    public static final Item HYDROPHOBE_ITEM = new BlockItem(HYDROPHOBE, ITEM_PROPS);

    public static final ResourceLocation MAGMAPHOBE_ID = ID.apply("magmaphobe");
    public static final TagKey<Fluid> MAGMAPHOBE_TAG = TagKey.create(Registries.FLUID, ID.apply("magmaphobe_affected"));
    public static final Block MAGMAPHOBE = new HydrophobeBlock(MAGMAPHOBE_TAG, MAGMAPHOBE_RANGE);
    public static final Item MAGMAPHOBE_ITEM = new BlockItem(MAGMAPHOBE, ITEM_PROPS);
}
