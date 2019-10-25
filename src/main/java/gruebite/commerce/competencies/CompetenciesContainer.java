package gruebite.commerce.competencies;

import gruebite.commerce.playerdata.PlayerProperties;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.*;
import net.minecraft.inventory.container.ChestContainer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.StringNBT;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponent;
import net.minecraft.util.text.TextFormatting;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Collections;

public class CompetenciesContainer extends ChestContainer {
    private static final Logger LOGGER = LogManager.getLogger();

    private static final int INFO_IDX = 9*6-1;
    private static final int CONVERT_IDX = 9*5;

    private static ItemStack simpleStack(Item item, String title, TextFormatting titleFormat, String[] lores, TextFormatting loresFormat) {
        ItemStack stack = new ItemStack(item);
        stack.setDisplayName(new StringTextComponent(title).setStyle(new Style().setColor(titleFormat)));
        ListNBT list = new ListNBT();
        for (String lore : lores) {
            list.add(new StringNBT(TextComponent.Serializer.toJson(new StringTextComponent(lore).setStyle(new Style().setColor(loresFormat)))));
        }
        stack.getOrCreateChildTag("display").put("Lore", list);
        return stack;
    }

    private static Inventory buildPlayerCompetencies(PlayerEntity player) {
        Inventory inv = new Inventory(9*6);

        for (Competency comp : Competency.values()) {
            ArrayList<String> description = new ArrayList<>();
            description.add("Cost: " + comp.getCost());
            Collections.addAll(description, comp.getDescription());
            Item icon = comp.getIcon();
            if (PlayerProperties.isCompetent((ServerPlayerEntity)player, comp)) {
                icon = Items.TORCH;
            }
            ItemStack stack = simpleStack(icon,
                    StringUtils.capitalize(comp.getName()), TextFormatting.YELLOW,
                    description.toArray(new String[0]), TextFormatting.WHITE);
            inv.setInventorySlotContents(comp.ordinal(), stack);
        }

        int kp = PlayerProperties.getKnowledgePoints((ServerPlayerEntity)player);
        String kpString = "Knowledge Points: " + kp;
        ItemStack bell = simpleStack(Items.BELL,
                "Info", TextFormatting.BLUE,
                new String[]{kpString}, TextFormatting.WHITE);
        inv.setInventorySlotContents(INFO_IDX, bell);

        ItemStack convert = simpleStack(Items.EMERALD,
                "Experience -> Emerald", TextFormatting.GREEN,
                new String[]{"Convert 300exp to 1 emerald"}, TextFormatting.WHITE);
        inv.setInventorySlotContents(CONVERT_IDX, convert);
        return inv;
    }

    public CompetenciesContainer(int id, PlayerInventory playerInventoryIn, PlayerEntity player) {
        super(ContainerType.GENERIC_9X6, id, playerInventoryIn, buildPlayerCompetencies(player), 6);
    }

    @Nonnull
    @Override
    public ItemStack slotClick(int slotId, int dragType, ClickType clickTypeIn, PlayerEntity player) {
        ItemStack itemStack = this.inventorySlots.get(slotId).getStack();

        if (itemStack.hasDisplayName()) {
            if (slotId < Competency.values().length) {
                Competency competency = Competency.valueOf(itemStack.getDisplayName().getString().toUpperCase());

                int kp = PlayerProperties.getKnowledgePoints((ServerPlayerEntity) player);
                if (competency.getCost() <= kp && !PlayerProperties.isCompetent((ServerPlayerEntity) player, competency)) {
                    PlayerProperties.setKnowledgePoints((ServerPlayerEntity) player, kp - competency.getCost());
                    PlayerProperties.becomeCompetent((ServerPlayerEntity) player, competency);
                    String kpString = "Knowledge Points: " + (kp - competency.getCost());
                    ItemStack stack = simpleStack(Items.BELL,
                            "Info", TextFormatting.BLUE,
                            new String[]{kpString}, TextFormatting.WHITE);
                    getLowerChestInventory().setInventorySlotContents(INFO_IDX, stack);
                    ItemStack old = getLowerChestInventory().getStackInSlot(slotId);

                    ItemStack n = new ItemStack(Items.TORCH);
                    n.setDisplayName(old.getDisplayName());
                    n.setTag(old.getTag());
                    getLowerChestInventory().setInventorySlotContents(slotId, n);
                } else if (competency.getCost() > kp) {
                    player.sendMessage(new StringTextComponent("You do not have enough knowledge points!"));
                }
            } else if (slotId == CONVERT_IDX) {
                if (player.experience >= 300) {
                    player.giveExperiencePoints(-300);
                    player.inventory.addItemStackToInventory(new ItemStack(Items.EMERALD));
                } else {
                    player.sendMessage(new StringTextComponent("You do not have enough experience (" + player.experience + ")!"));
                }
            }
        }

        if (itemStack.equals(ItemStack.EMPTY) || clickTypeIn == ClickType.QUICK_MOVE) {
            return new ItemStack(Items.PUMPKIN);
        }
        return ItemStack.EMPTY;
    }
}
