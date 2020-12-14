package com.github.noblekalish;

import com.github.noblekalish.blocks.ExampleBlock;
import com.github.noblekalish.items.FabricItem;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ExampleMod implements ModInitializer {
	// an instance of our new item
	public static final FabricItem FABRIC_ITEM = new FabricItem(new FabricItemSettings().group(ItemGroup.MISC));

	/* Declare and initialize our custom block instance.
    We set our block material to METAL, which requires a pickaxe to efficiently break.
  	Hardness represents how long the break takes to break. Stone has a hardness of 1.5f, while Obsidian has a hardness of 50.0f.
	*/
	public static final ExampleBlock EXAMPLE_BLOCK = new ExampleBlock(FabricBlockSettings.of(Material.METAL).hardness(4.0f));

	@Override
	public void onInitialize() {
		Registry.register(Registry.ITEM, new Identifier("tutorial", "fabric_item"), FABRIC_ITEM);
		Registry.register(Registry.BLOCK, new Identifier("tutorial", "example_block"), EXAMPLE_BLOCK);
		Registry.register(Registry.ITEM, new Identifier("tutorial", "example_block"), new BlockItem(EXAMPLE_BLOCK, new Item.Settings().group(ItemGroup.MISC)));
	}
}
