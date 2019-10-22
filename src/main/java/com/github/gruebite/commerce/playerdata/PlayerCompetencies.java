package com.github.gruebite.commerce.playerdata;

import net.minecraft.nbt.CompoundNBT;

import java.util.HashMap;
import java.util.Map;

public class PlayerCompetencies {
    public enum Index {
        CARPENTER("");

        public String name = "";

        Index(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }
    private HashMap<String, Integer> competencies = new HashMap<>();

    public PlayerCompetencies() {
        for (Index idx : Index.values()) {
            competencies.put(idx.toString(), 0);
        }
    }

    public HashMap<String, Integer> getCompetencies() {
        return competencies;
    }

    public boolean isCompetent(Index idx) {
        return competencies.get(idx.toString()) > 0;
    }

    public void becomeCompetent(Index idx) {
        competencies.put(idx.toString(), 1);
    }

    public void copyFrom(PlayerCompetencies source) {
        competencies = source.competencies;
    }

    public void saveNBTData(CompoundNBT compound) {
        for (Map.Entry<String, Integer> entry : competencies.entrySet()) {
            compound.putInt(entry.getKey(), entry.getValue());
        }
    }

    public void loadNBTData(CompoundNBT compound) {
        for (Map.Entry<String, Integer> entry : competencies.entrySet()) {
            competencies.put(entry.getKey(), compound.getInt(entry.getKey()));
        }
    }
}
