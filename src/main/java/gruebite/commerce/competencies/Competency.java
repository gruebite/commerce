package gruebite.commerce.competencies;

import net.minecraft.item.Item;
import net.minecraft.item.Items;

public enum Competency {
    // Labors
    RIDING(Items.SADDLE, 1, new String[]{"Ride animals."}),
    SEAFARING(Items.DARK_OAK_BOAT, 1, new String[]{"Ride boats."}),
    FISHING(Items.FISHING_ROD, 1, new String[]{"Learn to fish."}),
    TAMING(Items.LEAD, 2, new String[]{"Can tame animals."}),
    BREEDING(Items.EGG, 3, new String[]{"Can breed animals."}),
    FARMING(Items.WOODEN_HOE, 3, new String[]{"Learn to till soil."}),
    WOODCUTTING(Items.WOODEN_AXE, 3, new String[]{"Learn to use an axe."}),
    //FLYING("flying", Items.ELYTRA, 3, new String[]{"Learn to use an elytra."}),
    EXCAVATING(Items.WOODEN_SHOVEL, 3, new String[]{"Learn to dig dirt."}),
    ARMING(Items.IRON_CHESTPLATE, 6, new String[]{"Learn to use heavy armor and shield."}),
    SWORDFIGHTING(Items.WOODEN_SWORD, 6, new String[]{"Learn to use a sword."}),
    ARCHERY(Items.BOW, 6, new String[]{"Learn to use a bow and crossbow."}),
    MINING(Items.WOODEN_PICKAXE, 6, new String[]{"Learn to mine stone and ore/gems."}),
    // Crafts
    DYEING(Items.WHITE_DYE, 1, new String[]{"Learn to make dyes and paint."}),
    POTTERY(Items.FLOWER_POT, 1, new String[]{"Learn to craft pottery and clay."}),
    GLASSBLOWING(Items.GLASS, 2, new String[]{"Learn glass crafting."}),
    COOKING(Items.CAMPFIRE, 3, new String[]{"Learn cooking recipes.", "*Can use a smoker and fireplace.*"}),
    SMELTING(Items.FURNACE, 3, new String[]{"Learn to smelt.", "*Can use furnace.*"}),
    FLETCHING(Items.ARROW, 3, new String[]{"Learn arrow and bow crafting."}),
    CARTOGRAPHY(Items.MAP, 3, new String[]{"Learn compass and map crafting.", "*Can use cartography table.*"}),
    TOOLSMITHING(Items.SHEARS, 6, new String[]{"Learn to make tools."}),
    WEAPONSMITHING(Items.DIAMOND_SWORD, 6, new String[]{"Learn to make weapons."}),
    ARMORSMITHING(Items.DIAMOND_CHESTPLATE, 6, new String[]{"Learn to make armor."}),
    TAILORING(Items.LEATHER, 6, new String[]{"Learn leather and cloth crafting."}),
    ALCHEMY(Items.BLAZE_ROD, 6, new String[]{"Learn to craft potions.", "*Can use brewing stand.*"}),
    MASONRY(Items.COBBLESTONE, 6, new String[]{"Learn stone crafting."}),
    CARPENTRY(Items.OAK_PLANKS, 6, new String[]{"Learn wood crafting."}),
    ENCHANTING(Items.BOOK, 9, new String[]{"Learn to enchant.", "*Can use enchanting table.*"}),
    ENGINEERING(Items.REDSTONE, 9, new String[]{"Learn to craft special and redstone items."}),
    ;

    private final Item icon;
    private final int cost;
    private final String[] description;

    Competency(Item icon, int cost, String[] description) {
        this.icon = icon;
        this.cost = cost;
        this.description = description;
    }

    public String getName() {
        return toString().toLowerCase();
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
}
