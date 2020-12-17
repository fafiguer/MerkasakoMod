package com.FafaTeam.MerkaSako.loot_tables;

import com.FafaTeam.MerkaSako.MerkaSakoMod;

import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class LootTables{
    public static final DeferredRegister<GlobalLootModifierSerializer<?>> GLOBAL_LOOT_MODIFIER = DeferredRegister.create(ForgeRegistries.LOOT_MODIFIER_SERIALIZERS, MerkaSakoMod.MOD_ID);

    public static final RegistryObject<BasicDropModifier.Serializer> BASIC_DROP = GLOBAL_LOOT_MODIFIER.register("basic_drop", BasicDropModifier.Serializer::new);
    
}
