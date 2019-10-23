package com.github.gruebite.commerce.competencies;

import net.minecraft.item.Item;
import net.minecraft.item.Items;

public enum Competency {
    // Laborers
    RIDING("riding", Items.SADDLE, 1, new String[]{"Ride animals."}),
    SEAFARING("seafaring", Items.DARK_OAK_BOAT, 1, new String[]{"Ride boats."}),
    FISHING("fishing", Items.FISHING_ROD, 1, new String[]{"Learn to fish and fishing rod crafting."}),
    FARMING("farming", Items.WOODEN_HOE, 3, new String[]{"Learn to till soil."}),
    WOODCUTTING("woodcutting", Items.WOODEN_AXE, 3, new String[]{"Learn to use an axe."}),
    FLYING("flying", Items.ELYTRA, 3, new String[]{"Learn to use an elytra."}),
    EXCAVATING("excavating", Items.WOODEN_SHOVEL, 6, new String[]{"Learn to dig dirt and stone."}),
    SWORDFIGHTING("swordfighting", Items.WOODEN_SWORD, 6, new String[]{"Learn to use a sword."}),
    ARCHERY("archery", Items.BOW, 6, new String[]{"Learn to use a bow and crossbow."}),
    LANCING("lancing", Items.TRIDENT, 6, new String[]{"Learn to use a trident."}),
    MINING("mining", Items.WOODEN_PICKAXE, 9, new String[]{"Learn to use mine stone and ore/gems."}),
    // Artisan
    GLASSBLOWING("glassblowing", Items.GLASS, 1, new String[]{"Learn glass crafting."}),
    LEATHERWORKING("leatherworking", Items.LEATHER, 3, new String[]{"Learn leather crafting."}),
    FLETCHING("fletching", Items.ARROW, 3, new String[]{"Learn arrow and bow crafting."}),
    MASONRY("masonry", Items.COBBLESTONE, 3, new String[]{"Learn stone crafting."}),
    CARTOGRAPHY("cartography", Items.MAP, 3, new String[]{"Learn map crafting.", "*Can use cartography table.*"}),
    COOKING("cooking", Items.BREAD, 3, new String[]{"Learn cooking recipes."}),
    DYEING("dyeing", Items.WHITE_DYE, 3, new String[]{"Learn to dye."}),
    CARPENTRY("carpentry", Items.OAK_PLANKS, 6, new String[]{"Learn wood crafting."}),
    METALSMITHING("metalsmithing", Items.IRON_INGOT, 6, new String[]{"Learn metal crafting."}),
    WEAPONSMITHING("weaponsmithing", Items.IRON_SWORD, 6, new String[]{"Learn metal weapon crafting."}),
    ARMORSMITHING("armorsmithing", Items.IRON_HELMET, 6, new String[]{"Learn metal armor and shield crafting."}),
    ALCHEMY("alchemy", Items.BLAZE_ROD, 9, new String[]{"Learn to craft potions.", "*Can use brewing stand.*"}),
    ECHANTING("enchanting", Items.BOOK, 9, new String[]{"Learn to enchant.", "*Can use enchanting table.*"}),
    ENGINEERING("engineering", Items.REDSTONE, 9, new String[]{"Learn to craft using redstone."}),
    GEMCUTTING("gemcutting", Items.DIAMOND, 12, new String[]{"Learn to craft using diamonds."}),

    ;

    private final String name;
    private final Item icon;
    private final int cost;
    private final String[] description;

    Competency(String name, Item icon, int cost, String[] description) {
        this.name = name;
        this.icon = icon;
        this.cost = cost;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public Item getIcon() {
        return icon;
    }

    public int getCost() {
        return cost;
    }

    public String[] getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return name;
    }
}
