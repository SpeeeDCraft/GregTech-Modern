package com.gregtechceu.gtceu.api.data.worldgen.bedrockore;

import com.gregtechceu.gtceu.api.registry.GTRegistries;
import com.gregtechceu.gtceu.config.ConfigHolder;
import lombok.Getter;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * @author Screret
 * @date 2023/12/20
 * @implNote OreVeinWorldEntry
 */
public class OreVeinWorldEntry {
    @Nullable
    @Getter
    private BedrockOreDefinition definition;
    @Getter
    private int oreYield;
    @Getter
    private int operationsRemaining;

    public OreVeinWorldEntry(@Nullable BedrockOreDefinition vein, int oreYield, int operationsRemaining) {
        this.definition = vein;
        this.oreYield = oreYield;
        this.operationsRemaining = operationsRemaining;
    }

    private OreVeinWorldEntry() {

    }

    @SuppressWarnings("unused")
    public void setOperationsRemaining(int amount) {
        this.operationsRemaining = amount;
    }

    public void decreaseOperations(int amount) {
        operationsRemaining = ConfigHolder.INSTANCE.worldgen.oreVeins.infiniteBedrockOresFluids ? operationsRemaining : Math.max(0, operationsRemaining - amount);
    }

    public CompoundTag writeToNBT() {
        var tag = new CompoundTag();
        tag.putInt("oreYield", oreYield);
        tag.putInt("operationsRemaining", operationsRemaining);
        if (definition != null) {
            tag.putString("vein", GTRegistries.BEDROCK_ORE_DEFINITIONS.getKey(definition).toString());
        }
        return tag;
    }

    @Nonnull
    public static OreVeinWorldEntry readFromNBT(@Nonnull CompoundTag tag) {
        OreVeinWorldEntry info = new OreVeinWorldEntry();
        info.oreYield = tag.getInt("oreYield");
        info.operationsRemaining = tag.getInt("operationsRemaining");

        if (tag.contains("vein")) {
            info.definition = GTRegistries.BEDROCK_ORE_DEFINITIONS.get(new ResourceLocation(tag.getString("vein")));
        }
        return info;
    }
}
