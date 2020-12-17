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
    //private final List<EntityType<?>> mobList;
    public BasicDropModifier(ILootCondition[] conditionsIn, Item reward/*, List<EntityType<?>> mobs*/){
        super(conditionsIn);
        itemReward = reward;
        //mobList = mobs;
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
            //int numSeeds = JSONUtils.getInt(object, "numSeeds");
            //Item seed = ForgeRegistries.ITEMS.getValue(new ResourceLocation((JSONUtils.getString(object, "seedItem"))));
            //Item wheat = ForgeRegistries.ITEMS.getValue(new ResourceLocation(JSONUtils.getString(object, "replacement")));
            Item reward = ForgeRegistries.ITEMS.getValue(new ResourceLocation(JSONUtils.getString(object, "reward")));

            /*List<EntityType<?>> mobs = new ArrayList<>();
            JsonArray mobs_array = JSONUtils.getJsonArray(object, "mobs");
            for(JsonElement jsonElement: mobs_array){
                EntityType<?> mob = ForgeRegistries.ENTITIES.getValue(new ResourceLocation(jsonElement.getAsString()));
                mobs.add(mob);
            }*/
            return new BasicDropModifier(conditionsIn, reward/*, mobs*/);
        }

        @Override
        public JsonObject write(BasicDropModifier instance){
            JsonObject json = makeConditions(instance.conditions);
            //json.addProperty("numSeeds", instance.numSeedsToConvert);
            //json.addProperty("seedItem", ForgeRegistries.ITEMS.getKey(instance.itemToCheck).toString());
            //json.addProperty("replacement", ForgeRegistries.ITEMS.getKey(instance.itemReward).toString());
            json.addProperty("reward", ForgeRegistries.ITEMS.getKey(instance.itemReward).toString());
            //json.addProperty("mobs", JSONUtils.toJson());

            /*JsonArray mobs_array = new JsonArray();
            for(EntityType<?> mob: instance.mobList){
                mobs_array.add(ForgeRegistries.ENTITIES.getKey(mob).toString());
            }
            json.addProperty("mobs", mobs_array.getAsString());*/

            return json;
        }
    }
}
