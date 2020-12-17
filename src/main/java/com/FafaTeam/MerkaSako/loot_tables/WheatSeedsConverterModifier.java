package com.FafaTeam.MerkaSako.loot_tables;

import java.util.List;

import javax.annotation.Nonnull;

import com.FafaTeam.MerkaSako.MerkaSakoMod;
import com.google.gson.JsonObject;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.common.loot.LootModifier;
import net.minecraftforge.registries.ForgeRegistries;

/**
 * When harvesting wheat with shears, this modifier is invoked via the wheat_harvest loot_modifier json<br/>
 * This modifier checks how many seeds were harvested and turns X seeds into Y wheat (3:1)
 *
 */
public class WheatSeedsConverterModifier extends LootModifier{
    private final int numSeedsToConvert;
    private final Item itemToCheck;
    private final Item itemReward;
    public WheatSeedsConverterModifier(ILootCondition[] conditionsIn, int numSeeds, Item itemCheck, Item reward) {
        super(conditionsIn);
        numSeedsToConvert = numSeeds;
        itemToCheck = itemCheck;
        itemReward = reward;
    }

    @Nonnull
    @Override
    public List<ItemStack> doApply(List<ItemStack> generatedLoot, LootContext context) {
        //
        // Additional conditions can be checked, though as much as possible should be parameterized via JSON data.
        // It is better to write a new ILootCondition implementation than to do things here.
        //
        MerkaSakoMod.LOGGER.info("\n\n\t\t doApply()\n\n");
        int numSeeds = 0;
        for(ItemStack stack : generatedLoot) {
            if(stack.getItem() == itemToCheck)
                numSeeds+=stack.getCount();
        }
        if(numSeeds >= numSeedsToConvert) {
            generatedLoot.removeIf(x -> x.getItem() == itemToCheck);
            generatedLoot.add(new ItemStack(itemReward, (numSeeds/numSeedsToConvert)));
            numSeeds = numSeeds%numSeedsToConvert;
            if(numSeeds > 0)
                generatedLoot.add(new ItemStack(itemToCheck, numSeeds));
        }
        return generatedLoot;
    }

    public static class Serializer extends GlobalLootModifierSerializer<WheatSeedsConverterModifier>{
        @Override
        public WheatSeedsConverterModifier read(ResourceLocation name, JsonObject object, ILootCondition[] conditionsIn) {
            int numSeeds = JSONUtils.getInt(object, "numSeeds");
            Item seed = ForgeRegistries.ITEMS.getValue(new ResourceLocation((JSONUtils.getString(object, "seedItem"))));
            Item wheat = ForgeRegistries.ITEMS.getValue(new ResourceLocation(JSONUtils.getString(object, "replacement")));
            return new WheatSeedsConverterModifier(conditionsIn, numSeeds, seed, wheat);
        }

        @Override
        public JsonObject write(WheatSeedsConverterModifier instance) {
            JsonObject json = makeConditions(instance.conditions);
            json.addProperty("numSeeds", instance.numSeedsToConvert);
            json.addProperty("seedItem", ForgeRegistries.ITEMS.getKey(instance.itemToCheck).toString());
            json.addProperty("replacement", ForgeRegistries.ITEMS.getKey(instance.itemReward).toString());
            return json;
        }
    }
}
