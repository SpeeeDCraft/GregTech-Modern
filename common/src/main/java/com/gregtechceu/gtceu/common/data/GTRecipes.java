package com.gregtechceu.gtceu.common.data;

import com.gregtechceu.gtceu.api.registry.GTRegistries;
import com.gregtechceu.gtceu.data.recipe.MaterialInfoLoader;
import com.gregtechceu.gtceu.data.recipe.TagLoader;
import com.gregtechceu.gtceu.data.recipe.configurable.RecipeAddition;
import com.gregtechceu.gtceu.data.recipe.configurable.RecipeRemoval;
import com.gregtechceu.gtceu.data.recipe.generated.*;
import com.gregtechceu.gtceu.data.recipe.misc.*;
import com.gregtechceu.gtceu.data.recipe.serialized.chemistry.ChemistryRecipes;
import com.tterrag.registrate.providers.ProviderType;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;

import java.util.function.Consumer;

public class GTRecipes {

    /*
     * Called as part of data generation, serialized to JSON.
     *
     * These methods CANNOT be used to remove recipes, nor can they be dependent
     * on config options, such as ConfigHolder.
     *
     * These recipes are meant to be changeable with Datapacks.
     */
    public static void init() {
        // chemistry
        GTRegistries.REGISTRATE.addDataGenerator(ProviderType.RECIPE, ChemistryRecipes::init);

        // misc
        GTRegistries.REGISTRATE.addDataGenerator(ProviderType.RECIPE, AssemblerRecipeLoader::init);
        GTRegistries.REGISTRATE.addDataGenerator(ProviderType.RECIPE, AssemblyLineLoader::init);
        GTRegistries.REGISTRATE.addDataGenerator(ProviderType.RECIPE, BatteryRecipes::init);
        GTRegistries.REGISTRATE.addDataGenerator(ProviderType.RECIPE, CircuitRecipes::init);
        GTRegistries.REGISTRATE.addDataGenerator(ProviderType.RECIPE, ComponentRecipes::init);
        GTRegistries.REGISTRATE.addDataGenerator(ProviderType.RECIPE, CraftingRecipeLoader::init);
        GTRegistries.REGISTRATE.addDataGenerator(ProviderType.RECIPE, FuelRecipes::init);
        GTRegistries.REGISTRATE.addDataGenerator(ProviderType.RECIPE, FusionLoader::init);
        GTRegistries.REGISTRATE.addDataGenerator(ProviderType.RECIPE, MachineRecipeLoader::init);
        GTRegistries.REGISTRATE.addDataGenerator(ProviderType.RECIPE, MetaTileEntityLoader::init);
        GTRegistries.REGISTRATE.addDataGenerator(ProviderType.RECIPE, MetaTileEntityMachineRecipeLoader::init);
        GTRegistries.REGISTRATE.addDataGenerator(ProviderType.RECIPE, MiscRecipeLoader::init);
        GTRegistries.REGISTRATE.addDataGenerator(ProviderType.RECIPE, VanillaStandardRecipes::init);
        GTRegistries.REGISTRATE.addDataGenerator(ProviderType.RECIPE, WoodMachineRecipes::init);

        // NOT YET MENTIONED: VanillaOverrideRecipes
    }

    /*
     * Called on resource reload in-game.
     *
     * These methods are meant for recipes that cannot be reasonably changed by a Datapack,
     * such as "X Ingot -> 2 X Rods" types of recipes, that follow a pattern for many recipes.
     *
     * This should also be used for recipes that need
     * to respond to a config option in ConfigHolder.
     */
    public static void autoGenerated(Consumer<FinishedRecipe> consumer) {
        // Tag loading
        TagLoader.init();
        MaterialInfoLoader.init();
        // com.gregtechceu.gtceu.data.recipe.generated.*
        DecompositionRecipeHandler.init(consumer);
        MaterialRecipeHandler.init(consumer);
        OreRecipeHandler.init(consumer);
        PartsRecipeHandler.init(consumer);
        PipeRecipeHandler.init(consumer);
        PolarizingRecipeHandler.init(consumer);
        RecyclingRecipeHandler.init(consumer);
        ToolRecipeHandler.init(consumer);
        WireCombiningHandler.init(consumer);
        WireRecipeHandler.init(consumer);

        // Config-dependent recipes
        RecipeAddition.init(consumer);
        // Must run recycling recipes very last
        RecyclingRecipes.init(consumer);
    }

    /*
     * Called on resource reload in-game, just before the above method.
     *
     * This is also where any recipe removals should happen.
     */
    public static void recipeRemoval(Consumer<ResourceLocation> consumer) {
        RecipeRemoval.init(consumer);
    }
}
