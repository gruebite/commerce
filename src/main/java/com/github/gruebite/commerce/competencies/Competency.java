package com.github.gruebite.commerce.competencies;

public enum Competency {
    // Laborers
    CARPENTRY("carpentry", 3, "Enable crafting wood recipes.")
    // Artisan
    ;

    private final String name;
    private final int cost;
    private final String description;

    Competency(String name, int cost, String description) {
        this.name = name;
        this.cost = cost;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public int getCost() {
        return cost;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return name;
    }
}
