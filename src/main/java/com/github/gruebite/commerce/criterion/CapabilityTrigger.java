package com.github.gruebite.commerce.criterion;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import net.minecraft.advancements.ICriterionTrigger;
import net.minecraft.advancements.PlayerAdvancements;
import net.minecraft.advancements.criterion.CriterionInstance;
import net.minecraft.util.ResourceLocation;

public class CapabilityTrigger implements ICriterionTrigger<CapabilityTrigger.Instance> {

    @Override
    public ResourceLocation getId() {
        return null;
    }

    @Override
    public void addListener(PlayerAdvancements playerAdvancementsIn, Listener<Instance> listener) {

    }

    @Override
    public void removeListener(PlayerAdvancements playerAdvancementsIn, Listener<Instance> listener) {

    }

    @Override
    public void removeAllListeners(PlayerAdvancements playerAdvancementsIn) {

    }

    @Override
    public Instance deserializeInstance(JsonObject json, JsonDeserializationContext context) {
        return null;
    }

    public static class Instance extends CriterionInstance {

        public Instance(ResourceLocation criterionIn) {
            super(criterionIn);
        }
    }
}
