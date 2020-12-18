package com.FafaTeam.MerkaSako.loot_conditions;

import com.FafaTeam.MerkaSako.MerkaSakoMod;

import net.minecraft.loot.ILootSerializer;
import net.minecraft.loot.LootConditionType;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;

public class LootConditions{
    public static final LootConditionType KILLED_ENTITIES_CONDITION = register("killed_entities_condition", new KilledEntitiesListCondition.Serializer());

    public static void init(){
    }

    public static LootConditionType register(String resource_name, ILootSerializer<? extends ILootCondition> loot_serializer) {
        return Registry.register(Registry.field_239704_ba_, new ResourceLocation(MerkaSakoMod.MOD_ID + ":" + resource_name), new LootConditionType(loot_serializer));
    }
}
