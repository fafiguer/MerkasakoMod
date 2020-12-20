package com.FafaTeam.MerkaSako.loot_tables;

import java.util.List;

import javax.annotation.Nonnull;

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

public class BasicDropModifier extends LootModifier{
    private final Item itemReward;
    public BasicDropModifier(ILootCondition[] conditionsIn, Item reward){
        super(conditionsIn);
        itemReward = reward;
    }

    @Nonnull
    @Override
    public List<ItemStack> doApply(List<ItemStack> generatedLoot, LootContext context){
        generatedLoot.add(new ItemStack(itemReward, 1));
        return generatedLoot;
    }


    public static class Serializer extends GlobalLootModifierSerializer<BasicDropModifier>{
        @Override
        public BasicDropModifier read(ResourceLocation name, JsonObject object, ILootCondition[] conditionsIn){
            Item reward = ForgeRegistries.ITEMS.getValue(new ResourceLocation(JSONUtils.getString(object, "reward")));
            return new BasicDropModifier(conditionsIn, reward);
        }

        @Override
        public JsonObject write(BasicDropModifier instance){
            JsonObject json = makeConditions(instance.conditions);
            json.addProperty("reward", ForgeRegistries.ITEMS.getKey(instance.itemReward).toString());
            return json;
        }
    }
}
