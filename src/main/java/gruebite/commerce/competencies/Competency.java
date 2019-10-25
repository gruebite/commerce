package gruebite.commerce.competencies;

import net.minecraft.item.Item;
import net.minecraft.item.Items;

public enum Competency {
    // Labors
    RIDING("riding", Items.SADDLE, 1, new String[]{"Ride animals."}),
    SEAFARING("seafaring", Items.DARK_OAK_BOAT, 1, new String[]{"Ride boats."}),
    FISHING("fishing", Items.FISHING_ROD, 2, new String[]{"Learn to fish."}),
    FARMING("farming", Items.WOODEN_HOE, 3, new String[]{"Learn to till soil."}),
    WOODCUTTING("woodcutting", Items.WOODEN_AXE, 3, new String[]{"Learn to use an axe."}),
    //FLYING("flying", Items.ELYTRA, 3, new String[]{"Learn to use an elytra."}),
    EXCAVATING("excavating", Items.WOODEN_SHOVEL, 3, new String[]{"Learn to dig dirt."}),
    //SHIELDING("shielding", Items.SHIELD, 3, new String[]{"Learn to use a shield."}),
    //ARMORING("armoring", Items.IRON_CHESTPLATE, 6, new String[]{"Learn to use armor."}),
    SWORDFIGHTING("swordfighting", Items.WOODEN_SWORD, 6, new String[]{"Learn to use a sword."}),
    ARCHERY("archery", Items.BOW, 6, new String[]{"Learn to use a bow and crossbow."}),
    LANCING("lancing", Items.TRIDENT, 6, new String[]{"Learn to use a trident."}),
    MINING("mining", Items.WOODEN_PICKAXE, 6, new String[]{"Learn to use mine stone and ore/gems."}),
    // Crafts
    COOKING("cooking", Items.BREAD, 1, new String[]{"Learn cooking recipes.", "*Can use a smoker.*"}),
    DYEING("dyeing", Items.WHITE_DYE, 2, new String[]{"Learn to dye and paint."}),
    POTTERY("pottery", Items.FLOWER_POT, 2, new String[]{"Learn to craft pottery and clay."}),
    SMELTING("smelting", Items.FURNACE, 3, new String[]{"Learn to smelt.", "*Can use furnace.*"}),
    GLASSBLOWING("glassblowing", Items.GLASS, 3, new String[]{"Learn glass crafting."}),
    FLETCHING("fletching", Items.ARROW, 3, new String[]{"Learn arrow and bow crafting."}),
    CARTOGRAPHY("cartography", Items.MAP, 3, new String[]{"Learn map crafting.", "*Can use cartography table.*"}),
    TAILORING("tailoring", Items.LEATHER, 6, new String[]{"Learn leather/cloth crafting."}),
    ALCHEMY("alchemy", Items.BLAZE_ROD, 6, new String[]{"Learn to craft potions.", "*Can use brewing stand.*"}),
    METALSMITHING("metalsmithing", Items.IRON_INGOT, 9, new String[]{"Learn metal crafting."}),
    MASONRY("masonry", Items.COBBLESTONE, 9, new String[]{"Learn stone crafting."}),
    ENCHANTING("enchanting", Items.BOOK, 9, new String[]{"Learn to enchant.", "*Can use enchanting table.*"}),
    ENGINEERING("engineering", Items.REDSTONE, 9, new String[]{"Learn to craft using redstone."}),
    CARPENTRY("carpentry", Items.OAK_PLANKS, 9, new String[]{"Learn wood crafting."}),
    GEMCUTTING("gemcutting", Items.DIAMOND, 9, new String[]{"Learn to craft using diamonds."}),
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
