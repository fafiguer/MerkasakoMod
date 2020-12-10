package com.FafaTeam.MerkaSako.util;

import com.FafaTeam.MerkaSako.MerkaSakoMod;
import com.FafaTeam.MerkaSako.items.ItemBase;
import net.minecraft.item.Item;
//import net.minecraft.item.ItemGroup;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class RegistryHandler {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MerkaSakoMod.MOD_ID);

    public static void init(){
        ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    //Items ("merkasako", () -> new Item(new Item.Properties().group(ItemGroup.instance).maxStackSize(1)));
    public static final RegistryObject<Item> MERKASAKO = ITEMS.register("merkasako", () -> new Item(new Item.Properties().group(MerkaSakoMod.TAB).maxStackSize(1)));
    public static final RegistryObject<Item> MERKAGEMA = ITEMS.register("merkagema", ItemBase::new);
    public static final RegistryObject<Item> FRAGMENTO_DE_MERKAGEMA = ITEMS.register("fragmento_de_merkagema", ItemBase::new);
}
