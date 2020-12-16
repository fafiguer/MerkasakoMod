package com.FafaTeam.MerkaSako;

import java.util.List;

import javax.annotation.Nonnull;

import com.FafaTeam.MerkaSako.util.RegistryHandler;
import com.google.gson.JsonObject;

import net.minecraft.advancements.criterion.ItemPredicate;
import net.minecraft.block.Blocks;
import net.minecraft.data.DataGenerator;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.conditions.BlockStateProperty;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.loot.conditions.MatchTool;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.data.GlobalLootModifierProvider;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.common.loot.LootModifier;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


// The value here should match an entry in the META-INF/mods.toml file
@Mod(MerkaSakoMod.MOD_ID)
public class MerkaSakoMod{
    private static final Logger LOGGER = LogManager.getLogger();
    public static final String MOD_ID = "merkasako";

    public static final DeferredRegister<GlobalLootModifierSerializer<?>> GLOBAL_LOOT_MODIFIER = DeferredRegister.create(ForgeRegistries.LOOT_MODIFIER_SERIALIZERS, MerkaSakoMod.MOD_ID);

    public MerkaSakoMod() {
        // Register the setup method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        // Register the doClientStuff method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);

        GLOBAL_LOOT_MODIFIER.register(FMLJavaModLoadingContext.get().getModEventBus());
        RegistryHandler.init();

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event) { }

    private void doClientStuff(final FMLClientSetupEvent event) { }

    public static final ItemGroup TAB = new ItemGroup("MerkasakoTAB") {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(RegistryHandler.MERKASAKO.get());
        }
    };




    private static final RegistryObject<WheatSeedsConverterModifier.Serializer> WHEATSEEDS = GLOBAL_LOOT_MODIFIER.register("wheat_harvest", WheatSeedsConverterModifier.Serializer::new);



    @EventBusSubscriber(modid = MOD_ID, bus = EventBusSubscriber.Bus.MOD)
    public static class EventHandlers {
        @SubscribeEvent
        public static void runData(GatherDataEvent event)
        {
            //LOGGER.info("\n\n\trunData()\n\n");
            event.getGenerator().addProvider(new DataProvider(event.getGenerator(), MOD_ID));
        }
    }



    private static class DataProvider extends GlobalLootModifierProvider
    {
        public DataProvider(DataGenerator gen, String modid)
        {
            super(gen, modid);
        }

        @Override
        protected void start()
        {
            add("wheat_harvest", WHEATSEEDS.get(), new WheatSeedsConverterModifier(
                    new ILootCondition[] {
                            MatchTool.builder(ItemPredicate.Builder.create().item(Items.SHEARS)).build(),
                            BlockStateProperty.builder(Blocks.WHEAT).build()
                    },
                    2, Items.WHEAT_SEEDS, Items.WHEAT)
            );
        }
    }
    

    /**
     * When harvesting wheat with shears, this modifier is invoked via the wheat_harvest loot_modifier json<br/>
     * This modifier checks how many seeds were harvested and turns X seeds into Y wheat (3:1)
     *
     */
    private static class WheatSeedsConverterModifier extends LootModifier {
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
            LOGGER.info("\n\n\t\t doApply()\n\n");
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

        private static class Serializer extends GlobalLootModifierSerializer<WheatSeedsConverterModifier> {

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

}
