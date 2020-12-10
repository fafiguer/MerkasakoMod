package com.FafaTeam.MerkaSako.util;

import com.FafaTeam.MerkaSako.MerkaSakoMod;
import com.FafaTeam.MerkaSako.items.ItemBase;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class RegistryHandler {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MerkaSakoMod.MOD_ID);

    public static void init(){
        ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    //Items
    public static final RegistryObject<Item> MERKASAKO = ITEMS.register("merkasako", ItemBase::new);
    public static final RegistryObject<Item> MERKA_GEMA = ITEMS.register("merka_gema", ItemBase::new);
    public static final RegistryObject<Item> FRAGMENTO_DE_MERKA_GEMA = ITEMS.register("fragmento_de_merka_gema", ItemBase::new);
}
