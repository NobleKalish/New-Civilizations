package com.github.noblekalish;

import com.github.noblekalish.block_entities.TownHallEntity;
import com.github.noblekalish.blocks.TownHallBlock;
import com.github.noblekalish.items.TownHallItem;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class NewCivilization implements ModInitializer {
    public static final Logger LOGGER = LogManager.getLogger();
    public static final String MODID = "newciv";

    public static final TownHallBlock TOWN_HALL_BLOCK = new TownHallBlock(FabricBlockSettings.of(Material.METAL).hardness(4.0f));
    public static final TownHallItem TOWN_HALL_ITEM = new TownHallItem(TOWN_HALL_BLOCK, new Item.Settings().group(ItemGroup.MISC));
    public static BlockEntityType<TownHallEntity> TOWN_HALL_ENTITY;

    @Override
    public void onInitialize() {
        TOWN_HALL_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, "modid:newciv", BlockEntityType.Builder.create(TownHallEntity::new, TOWN_HALL_BLOCK).build(null));
        Registry.register(Registry.BLOCK, new Identifier(MODID, "town_hall_block"), TOWN_HALL_BLOCK);
        Registry.register(Registry.ITEM, new Identifier(MODID, "town_hall_block"), TOWN_HALL_ITEM);
    }
}
