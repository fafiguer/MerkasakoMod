package com.FafaTeam.MerkaSako.loot_conditions;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;

import net.minecraft.advancements.criterion.EntityPredicate;
import net.minecraft.entity.Entity;
import net.minecraft.loot.ILootSerializer;
import net.minecraft.loot.LootConditionType;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootParameters;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.server.ServerWorld;

public class KilledEntitiesListCondition implements ILootCondition{
    private final LootContext.EntityTarget target;
    private final List<EntityPredicate> mobs_predicate;
    public KilledEntitiesListCondition(LootContext.EntityTarget targetIn, List<EntityPredicate> mobs) {
        target = targetIn;
        mobs_predicate = mobs;
    }

    @Override
    public boolean test(LootContext context){
        Entity entity = context.get(this.target.getParameter());
        Vector3d vector3d = context.get(LootParameters.field_237457_g_);
        ServerWorld world = context.getWorld();

        for(EntityPredicate entityPredicate: this.mobs_predicate){
            if(entityPredicate.func_217993_a(world, vector3d, entity)){
                return true;
            }
        }

        return false;
    }

    @Override
    public LootConditionType func_230419_b_(){
        return LootConditions.KILLED_ENTITIES_CONDITION;
    }

    public static class Serializer implements ILootSerializer<KilledEntitiesListCondition>{
        @Override
        public void func_230424_a_(JsonObject object, KilledEntitiesListCondition instance, JsonSerializationContext json_context){
            object.add("entity", json_context.serialize(instance.target)); // TODO

            JsonArray mobs_array = new JsonArray();
            for(EntityPredicate entitypredicate: instance.mobs_predicate){
                mobs_array.add(entitypredicate.serialize());
            }
            object.add("mobs_predicate", mobs_array);
        }

        @Override
        public KilledEntitiesListCondition func_230423_a_(JsonObject object, JsonDeserializationContext json_context){
            LootContext.EntityTarget target = LootContext.EntityTarget.fromString(object.get("entity").getAsString());

            List<EntityPredicate> mobs = new ArrayList<>();
            JsonArray mobs_array = JSONUtils.getJsonArray(object, "mobs_predicate");
            for(JsonElement jsonElement: mobs_array){
                EntityPredicate entitypredicate = EntityPredicate.deserialize(jsonElement);
                mobs.add(entitypredicate);
            }

            return new KilledEntitiesListCondition(target, mobs);
        }

    }
}
