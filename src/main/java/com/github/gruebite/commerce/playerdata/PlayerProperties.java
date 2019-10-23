package com.github.gruebite.commerce.playerdata;

import com.github.gruebite.commerce.competencies.AdvancementTriggers;
import com.github.gruebite.commerce.competencies.Competency;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.util.LazyOptional;

import java.util.HashMap;
import java.util.Map;

public class PlayerProperties {
    @CapabilityInject(PlayerProperties.class)
    public static Capability<PlayerProperties> MARKER;

    public static int DEFAULT_KNOWLEDGE_POINTS = 18;

    public static boolean isCompetent(ServerPlayerEntity player, Competency competency) {
        LazyOptional<PlayerProperties> props = player.getCapability(PlayerProperties.MARKER);
        if (props.isPresent()) {
            return props.orElse(new PlayerProperties()).isCompetent(competency);
        }
        return false;
    }

    public static void becomeCompetent(ServerPlayerEntity player, Competency competency) {
        if (isCompetent(player, competency)) {
            return;
        }
        player.getCapability(PlayerProperties.MARKER).ifPresent(props -> {
            props.becomeCompetent(competency);
            AdvancementTriggers.COMPETENCY_TRIGGER.trigger(player, competency);
        });
    }

    public static int getKnowledgePoints(ServerPlayerEntity player) {
        LazyOptional<PlayerProperties> props = player.getCapability(PlayerProperties.MARKER);
        if (props.isPresent()) {
            return props.orElse(new PlayerProperties()).getKnowledgePoints();
        }
        return DEFAULT_KNOWLEDGE_POINTS;
    }

    public static void setKnowledgePoints(ServerPlayerEntity player, int to) {
        player.getCapability(PlayerProperties.MARKER).ifPresent(props -> {
            props.setKnowledgePoints(to);
        });
    }

    private HashMap<String, Integer> competencies = new HashMap<>();
    private int knowledgePoints = DEFAULT_KNOWLEDGE_POINTS;

    public PlayerProperties() {
        for (Competency idx : Competency.values()) {
            competencies.put(idx.toString(), 0);
        }
    }

    public HashMap<String, Integer> getCompetencies() {
        return competencies;
    }

    public boolean isCompetent(Competency idx) {
        return competencies.get(idx.toString()) > 0;
    }

    public void becomeCompetent(Competency idx) {
        competencies.put(idx.toString(), 1);
    }

    public int getKnowledgePoints() {
        return knowledgePoints;
    }

    public void setKnowledgePoints(int to) {
        knowledgePoints = to;
    }

    public void copyFrom(PlayerProperties source) {
        competencies = source.competencies;
        knowledgePoints = source.knowledgePoints;
    }

    public void saveNBTData(CompoundNBT compound) {
        for (Map.Entry<String, Integer> entry : competencies.entrySet()) {
            compound.putInt(entry.getKey(), entry.getValue());
        }
        compound.putInt("kp", knowledgePoints);
    }

    public void loadNBTData(CompoundNBT compound) {
        for (Map.Entry<String, Integer> entry : competencies.entrySet()) {
            competencies.put(entry.getKey(), compound.getInt(entry.getKey()));
        }
        knowledgePoints = compound.getInt("kp");
    }
}
