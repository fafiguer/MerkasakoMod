package com.FafaTeam.MerkaSako.loot_conditions;

import com.FafaTeam.MerkaSako.MerkaSakoMod;

import net.minecraft.loot.ILootSerializer;
import net.minecraft.loot.LootConditionType;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
//import net.minecraftforge.registries.DeferredRegister;
//import net.minecraftforge.registries.ForgeRegistries;

public class LootConditions{
    //public static final DeferredRegister<GlobalLootModifierSerializer<?>> GLOBAL_LOOT_MODIFIER = DeferredRegister.create(ForgeRegistries.LOOT_MODIFIER_SERIALIZERS, MerkaSakoMod.MOD_ID);
    public static final LootConditionType KILLED_ENTITIES_CONDITION = register("killed_entities_condition", new KilledEntitiesListCondition.Serializer());

    public static LootConditionType register(String resource_name, ILootSerializer<? extends ILootCondition> p_237475_1_) {
        return Registry.register(Registry.field_239704_ba_, new ResourceLocation(MerkaSakoMod.MOD_ID + ":" + resource_name), new LootConditionType(p_237475_1_));
    }
}
