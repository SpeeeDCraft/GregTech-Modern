package com.gregtechceu.gtceu.integration.kjs.built;

import com.gregtechceu.gtceu.api.data.chemical.material.Material;
import com.gregtechceu.gtceu.api.data.chemical.material.info.MaterialIconType;
import com.gregtechceu.gtceu.api.data.tag.TagPrefix;
import com.gregtechceu.gtceu.api.data.tag.TagType;
import lombok.experimental.Accessors;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

import java.util.function.BiFunction;
import java.util.function.Predicate;

import static com.gregtechceu.gtceu.api.data.tag.TagPrefix.Conditions.hasOreProperty;

@Accessors(fluent = true, chain = true)
public class KJSTagPrefix extends TagPrefix {

    public KJSTagPrefix(String name) {
        super(name);
    }

    public static KJSTagPrefix oreTagPrefix(String name) {
        return new KJSTagPrefix(name)
            .prefixTagPath("ores/%s/%s")
            .defaultTagPath("ores/%s")
            .prefixOnlyTagPath("ores_in_ground/%s")
            .unformattedTagPath("ores")
            .materialIconType(MaterialIconType.ore)
            .unificationEnabled(true)
            .generationCondition(hasOreProperty);
    }

    @Override
    public KJSTagPrefix defaultTagPath(String path) {
        this.tags.add(TagType.withDefaultFormatter(path));
        return this;
    }

    @Override
    public KJSTagPrefix prefixTagPath(String path) {
        this.tags.add(TagType.withPrefixFormatter(path));
        return this;
    }

    @Override
    public KJSTagPrefix prefixOnlyTagPath(String path) {
        this.tags.add(TagType.withPrefixOnlyFormatter(path));
        return this;
    }

    @Override
    public KJSTagPrefix unformattedTagPath(String path) {
        this.tags.add(TagType.withNoFormatter(path));
        return this;
    }

    @Override
    public KJSTagPrefix customTagPath(String path, BiFunction<TagPrefix, Material, TagKey<Item>> formatter) {
        this.tags.add(TagType.withCustomFormatter(path, formatter));
        return this;
    }

    public KJSTagPrefix materialIconType(MaterialIconType type) {
        super.materialIconType(type);
        return this;
    }

    public KJSTagPrefix unificationEnabled(boolean unificationEnabled) {
        super.unificationEnabled(unificationEnabled);
        return this;
    }

    public KJSTagPrefix generationCondition(Predicate<Material> condition) {
        super.generationCondition(condition);
        return this;
    }
}
