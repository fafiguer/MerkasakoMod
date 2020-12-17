package com.FafaTeam.MerkaSako.loot_conditions;

import java.util.ArrayList;
import java.util.List;

import com.FafaTeam.MerkaSako.MerkaSakoMod;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;

import net.minecraft.advancements.criterion.EntityPredicate;
import net.minecraft.entity.Entity;
//import net.minecraft.entity.EntityType;
import net.minecraft.loot.ILootSerializer;
import net.minecraft.loot.LootConditionType;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootParameters;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.util.JSONUtils;
//import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.server.ServerWorld;
//import net.minecraftforge.registries.ForgeRegistries;

public class KilledEntitiesListCondition implements ILootCondition{
    private final LootContext.EntityTarget target;
    private final List<EntityPredicate> mobs_predicate;
    public KilledEntitiesListCondition(LootContext.EntityTarget targetIn, List<EntityPredicate> mobs) {
        target = targetIn;
        mobs_predicate = mobs;
    }

    @Override
    public boolean test(LootContext p_test_1_) {
        // TODO Auto-generated method stub

        MerkaSakoMod.LOGGER.info("\n\n");
        MerkaSakoMod.LOGGER.info("this.target == null:" + (this.target == null));
        if(this.target != null){
            MerkaSakoMod.LOGGER.info("this.target.toString():" + (this.target.toString()));
        }
        MerkaSakoMod.LOGGER.info("this.mobs_predicate == null:" + (this.mobs_predicate == null));
        if(this.mobs_predicate != null){
            MerkaSakoMod.LOGGER.info("this.mobs_predicate.size():" + (this.mobs_predicate.size()));
            for (EntityPredicate entityPredicate : mobs_predicate) {
                MerkaSakoMod.LOGGER.info("entityPredicate.toString():" + (entityPredicate.serialize().toString()));
            }
        }

        Entity entity = p_test_1_.get(this.target.getParameter());
        Vector3d vector3d = p_test_1_.get(LootParameters.field_237457_g_);
        //return this.predicate.func_217993_a(p_test_1_.getWorld(), vector3d, entity);

        ServerWorld world = p_test_1_.getWorld();
        for(EntityPredicate entityPredicate: this.mobs_predicate){
            if(entityPredicate.func_217993_a(world, vector3d, entity)){
                return true;
            }
        }

        return false;
    }

    @Override
    public LootConditionType func_230419_b_() {
        // TODO Auto-generated method stub
        return LootConditions.KILLED_ENTITIES_CONDITION;
    }

    public static class Serializer implements ILootSerializer<KilledEntitiesListCondition>{
        @Override
        public void func_230424_a_(JsonObject object, KilledEntitiesListCondition instance, JsonSerializationContext p_230424_3_) {
            // TODO Auto-generated method stub

            //object.add("predicate", instance.predicate.serialize());
            //object.add("entity", p_230424_3_.serialize(instance.target));
            object.add("entity", p_230424_3_.serialize(instance.target));


            JsonArray mobs_array = new JsonArray();
            /*for(EntityPredicate mob: instance.mobs_predicate){
                mobs_array.add(ForgeRegistries.ENTITIES.getKey(mob).toString());
            }*/
            // p_230424_1_.add("predicate", p_230424_2_.predicate.serialize());
            for(EntityPredicate entitypredicate: instance.mobs_predicate){
                //EntityType<?> mob = ForgeRegistries.ENTITIES.getValue(new ResourceLocation(jsonElement.getAsString()));
                //mobs.add(mob);
                //EntityPredicate entitypredicate = EntityPredicate.deserialize(jsonElement);
                //mobs.add(entitypredicate);
                mobs_array.add(entitypredicate.serialize());
            }
            object.add("mobs_predicate", mobs_array);
        }

        @Override
        public KilledEntitiesListCondition func_230423_a_(JsonObject object, JsonDeserializationContext p_230423_2_) {
            // TODO Auto-generated method stub

            //LootContext.EntityTarget target = JSONUtils.deserializeClass(object, "entity", p_230423_2_, LootContext.EntityTarget.class);
            LootContext.EntityTarget target = LootContext.EntityTarget.fromString(object.get("entity").getAsString());

            List<EntityPredicate> mobs = new ArrayList<>();
            //EntityPredicate entitypredicate = EntityPredicate.deserialize(p_230423_1_.get("predicate"));
            JsonArray mobs_array = JSONUtils.getJsonArray(object, "mobs_predicate");
            for(JsonElement jsonElement: mobs_array){
                //EntityType<?> mob = ForgeRegistries.ENTITIES.getValue(new ResourceLocation(jsonElement.getAsString()));
                //mobs.add(mob);
                EntityPredicate entitypredicate = EntityPredicate.deserialize(jsonElement);
                mobs.add(entitypredicate);
            }

            return new KilledEntitiesListCondition(target, mobs);
        }

    }
}
