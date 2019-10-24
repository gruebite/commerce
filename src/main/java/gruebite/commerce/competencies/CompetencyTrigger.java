package gruebite.commerce.competencies;

import gruebite.commerce.Commerce;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.advancements.ICriterionTrigger;
import net.minecraft.advancements.PlayerAdvancements;
import net.minecraft.advancements.criterion.CriterionInstance;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CompetencyTrigger implements ICriterionTrigger<CompetencyTrigger.Instance> {
    private static final ResourceLocation ID = new ResourceLocation(Commerce.MODID, "gained_competencies");
    private final Map<PlayerAdvancements, Listeners> listeners = Maps.newHashMap();

    @Override
    public ResourceLocation getId() {
        return ID;
    }

    @Override
    public void addListener(PlayerAdvancements playerAdvancementsIn, Listener<Instance> listener) {
        CompetencyTrigger.Listeners capabilityListeners = this.listeners.get(playerAdvancementsIn);
        if (capabilityListeners == null) {
            capabilityListeners = new CompetencyTrigger.Listeners(playerAdvancementsIn);
            this.listeners.put(playerAdvancementsIn, capabilityListeners);
        }

        capabilityListeners.add(listener);
    }

    @Override
    public void removeListener(PlayerAdvancements playerAdvancementsIn, Listener<Instance> listener) {
        CompetencyTrigger.Listeners capabilityListeners = this.listeners.get(playerAdvancementsIn);
        if (capabilityListeners != null) {
            capabilityListeners.remove(listener);
            if (capabilityListeners.isEmpty()) {
                this.listeners.remove(playerAdvancementsIn);
            }
        }
    }

    @Override
    public void removeAllListeners(PlayerAdvancements playerAdvancementsIn) {
        this.listeners.remove(playerAdvancementsIn);
    }

    @Override
    public Instance deserializeInstance(JsonObject json, JsonDeserializationContext context) {
        ArrayList<Competency> competencies = new ArrayList<>();
        JsonElement element = json.get("competencies");
        if (element != null && element.isJsonArray()) {
            JsonArray array = (JsonArray)element;
            for (int i = 0; i < array.size(); ++i) {
                String maybeComp = array.get(i).getAsString().toUpperCase();
                competencies.add(Competency.valueOf(maybeComp));
            }
        }
        return new Instance(this.getId(), competencies);
    }

    public void trigger(ServerPlayerEntity player, Competency competencyGained) {
        CompetencyTrigger.Listeners capabilityListeners = this.listeners.get(player.getAdvancements());
        if (capabilityListeners != null) {
            capabilityListeners.trigger(competencyGained);
        }
    }

    public static class Instance extends CriterionInstance {
        private final List<Competency> competencies;

        public Instance(ResourceLocation criterionIn, List<Competency> competencies) {
            super(criterionIn);
            this.competencies = competencies;
        }

        public boolean test(Competency competency) {
            for (Competency i : competencies) {
                if (i.equals(competency)) {
                    return true;
                }
            }
            return false;
        }
    }

    public static class Listeners {
        private final PlayerAdvancements playerAdvancements;
        private final Set<ICriterionTrigger.Listener<CompetencyTrigger.Instance>> listeners = Sets.newHashSet();

        public Listeners(PlayerAdvancements playerAdvancements) {
            this.playerAdvancements = playerAdvancements;
        }

        public boolean isEmpty() {
            return this.listeners.isEmpty();
        }

        public void add(ICriterionTrigger.Listener<CompetencyTrigger.Instance> listener) {
            this.listeners.add(listener);
        }

        public void remove(ICriterionTrigger.Listener<CompetencyTrigger.Instance> listener) {
            this.listeners.remove(listener);
        }

        public void trigger(Competency competency) {
            List<ICriterionTrigger.Listener<CompetencyTrigger.Instance>> list = null;

            for (ICriterionTrigger.Listener<CompetencyTrigger.Instance> listener : this.listeners) {
                if (listener.getCriterionInstance().test(competency)) {
                    if (list == null) {
                        list = Lists.newArrayList();
                    }

                    list.add(listener);
                }
            }

            if (list != null) {
                for (ICriterionTrigger.Listener<CompetencyTrigger.Instance> listener1 : list) {
                    listener1.grantCriterion(this.playerAdvancements);
                }
            }

        }
    }
}
