package com.github.noblekalish;

import com.github.noblekalish.block_entities.FarmEntity;
import com.github.noblekalish.block_entities.TownHallEntity;
import com.github.noblekalish.blocks.FarmBlock;
import com.github.noblekalish.blocks.TownHallBlock;
import com.github.noblekalish.entities.FarmerEntity;
import com.github.noblekalish.items.FarmItem;
import com.github.noblekalish.items.TownHallItem;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class NewCivilization implements ModInitializer {
    public static final Logger LOGGER = LogManager.getLogger();
    public static final String MODID = "newciv";
    public static BlockEntityType<TownHallEntity> TOWN_HALL_ENTITY;
    public static BlockEntityType<FarmEntity> FARM_ENTITY;

    public static final ItemGroup NEW_CIV_GROUP = FabricItemGroupBuilder.create(
            new Identifier(MODID, "general"))
            .build();

    public static final FarmBlock FARM_BLOCK = new FarmBlock(FabricBlockSettings.of(Material.WOOD).hardness(4.0f));
    public static final TownHallBlock TOWN_HALL_BLOCK = new TownHallBlock(FabricBlockSettings.of(Material.METAL).hardness(4.0f));

    public static final FarmItem FARM_ITEM = new FarmItem(FARM_BLOCK, new Item.Settings().group(NEW_CIV_GROUP));
    public static final TownHallItem TOWN_HALL_ITEM = new TownHallItem(TOWN_HALL_BLOCK, new Item.Settings().group(NEW_CIV_GROUP));

    public static final EntityType<FarmerEntity> FARMER_ENTITY = Registry.register(
            Registry.ENTITY_TYPE,
            new Identifier("newciv", "farmer"),
            FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, FarmerEntity::new).dimensions(EntityDimensions.fixed(0.75f, 0.75f)).build()
    );

    @Override
    public void onInitialize() {
        TOWN_HALL_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, "modid:newciv", BlockEntityType.Builder.create(TownHallEntity::new, TOWN_HALL_BLOCK).build(null));
        FARM_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, "modid:newciv", BlockEntityType.Builder.create(FarmEntity::new, TOWN_HALL_BLOCK).build(null));

        Registry.register(Registry.BLOCK, new Identifier(MODID, "farm_block"), FARM_BLOCK);
        Registry.register(Registry.BLOCK, new Identifier(MODID, "town_hall_block"), TOWN_HALL_BLOCK);

        Registry.register(Registry.ITEM, new Identifier(MODID, "town_hall_block"), TOWN_HALL_ITEM);
        Registry.register(Registry.ITEM, new Identifier(MODID, "farm_block"), FARM_ITEM);

        FabricDefaultAttributeRegistry.register(FARMER_ENTITY, FarmerEntity.createMobAttributes());
    }
}
