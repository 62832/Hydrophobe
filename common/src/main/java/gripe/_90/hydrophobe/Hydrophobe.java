package gripe._90.hydrophobe;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class Hydrophobe {
    public static final String MODID = "hydrophobe";

    public static final int WATER_RANGE = HydrophobeConfig.INSTANCE.waterRange;
    public static final int LAVA_RANGE = HydrophobeConfig.INSTANCE.lavaRange;

    public static final Block HYDROPHOBE = new HydrophobeBlock(FluidTags.WATER, WATER_RANGE);
    public static final Item HYDROPHOBE_ITEM = new BlockItem(HYDROPHOBE, new Item.Properties());
    public static final ResourceLocation HYDROPHOBE_ID = new ResourceLocation(MODID, MODID);

    public static final Block MAGMAPHOBE = new HydrophobeBlock(FluidTags.LAVA, LAVA_RANGE);
    public static final Item MAGMAPHOBE_ITEM = new BlockItem(MAGMAPHOBE, new Item.Properties());
    public static final ResourceLocation MAGMAPHOBE_ID = new ResourceLocation(MODID, "magmaphobe");
}