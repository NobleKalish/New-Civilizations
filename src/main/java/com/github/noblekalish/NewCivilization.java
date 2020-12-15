package com.github.noblekalish;

import com.github.noblekalish.block.FarmBlock;
import com.github.noblekalish.block_entity.FarmEntity;
import com.github.noblekalish.block_entity.TownHallEntity;
import com.github.noblekalish.item.FarmItem;
import com.github.noblekalish.screen_handlers.FarmScreenHandler;
import net.fabricmc.api.ModInitializer;
import com.github.noblekalish.block.TownHallBlock;
import com.github.noblekalish.entity.FarmerEntity;
import com.github.noblekalish.item.TownHallItem;
import com.github.noblekalish.structure_feature.FarmFeature;
import com.github.noblekalish.structure_generator.FarmGenerator;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.fabricmc.fabric.api.structure.v1.FabricStructureBuilder;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.structure.StructurePieceType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.ConfiguredStructureFeature;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.StructureFeature;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class NewCivilization implements ModInitializer {
    public static final Logger LOGGER = LogManager.getLogger();
    public static final String MODID = "newciv";
    public static BlockEntityType<TownHallEntity> TOWN_HALL_ENTITY;
    public static BlockEntityType<FarmEntity> FARM_ENTITY;
    public static ScreenHandlerType<FarmScreenHandler> FARM_SCREEN_HANDLER;
    public static final Identifier FARM = new Identifier(MODID, "farm_block");

    public static final ItemGroup NEW_CIV_GROUP = FabricItemGroupBuilder.create(
            new Identifier(MODID, "general"))
            .build();

    public static final FarmBlock FARM_BLOCK = new FarmBlock(FabricBlockSettings.of(Material.WOOD).hardness(4.0f));
    public static final TownHallBlock TOWN_HALL_BLOCK = new TownHallBlock(FabricBlockSettings.of(Material.METAL).hardness(4.0f));

    public static final FarmItem FARM_ITEM = new FarmItem(FARM_BLOCK, new Item.Settings().group(NEW_CIV_GROUP));
    public static final TownHallItem TOWN_HALL_ITEM = new TownHallItem(TOWN_HALL_BLOCK, new Item.Settings().group(NEW_CIV_GROUP));

    public static final StructurePieceType FARM_PIECE = FarmGenerator.FarmPiece::new;
    private static final StructureFeature<DefaultFeatureConfig> FARM_STRUCTURE = new FarmFeature(DefaultFeatureConfig.CODEC);
    private static final ConfiguredStructureFeature<?, ?> MY_CONFIGURED = FARM_STRUCTURE.configure(DefaultFeatureConfig.DEFAULT);
    public static final EntityType<FarmerEntity> FARMER_ENTITY = Registry.register(
            Registry.ENTITY_TYPE,
            new Identifier("newciv", "farmer"),
            FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, FarmerEntity::new).dimensions(EntityDimensions.fixed(0.75f, 0.75f)).build()
    );

    @Override
    public void onInitialize() {
        TOWN_HALL_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, "newciv:townhall", BlockEntityType.Builder.create(TownHallEntity::new, TOWN_HALL_BLOCK).build(null));
        FARM_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, "newciv:farm", BlockEntityType.Builder.create(FarmEntity::new, FARM_BLOCK).build(null));
        FARM_SCREEN_HANDLER = ScreenHandlerRegistry.registerSimple(FARM, FarmScreenHandler::new);
        Registry.register(Registry.BLOCK, new Identifier(MODID, "farm_block"), FARM_BLOCK);
        Registry.register(Registry.BLOCK, new Identifier(MODID, "town_hall_block"), TOWN_HALL_BLOCK);

        Registry.register(Registry.ITEM, new Identifier(MODID, "town_hall_block"), TOWN_HALL_ITEM);
        Registry.register(Registry.ITEM, new Identifier(MODID, "farm_block"), FARM_ITEM);
        FabricDefaultAttributeRegistry.register(FARMER_ENTITY, FarmerEntity.createMobAttributes());

        Registry.register(Registry.STRUCTURE_PIECE, new Identifier("tutorial", "my_piece"), FARM_PIECE);
        FabricStructureBuilder.create(new Identifier("newciv", "farm"), FARM_STRUCTURE)
                .step(GenerationStep.Feature.SURFACE_STRUCTURES)
                .defaultConfig(32, 8, 12345)
                .adjustsSurface()
                .register();

        RegistryKey<ConfiguredStructureFeature<?, ?>> myConfigured = RegistryKey.of(Registry.CONFIGURED_STRUCTURE_FEATURE_WORLDGEN,
                new Identifier("newciv", "farm"));
        BuiltinRegistries.add(BuiltinRegistries.CONFIGURED_STRUCTURE_FEATURE, myConfigured.getValue(), MY_CONFIGURED);
    }
}
