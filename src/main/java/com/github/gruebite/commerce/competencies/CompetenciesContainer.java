package com.github.gruebite.commerce.competencies;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.ChestContainer;
import net.minecraft.inventory.container.ClickType;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.ItemStack;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CompetenciesContainer extends ChestContainer {
    private static final Logger LOGGER = LogManager.getLogger();

    private PlayerEntity player;

    private static Inventory buildPlayerCompetencies(PlayerEntity player) {
        Inventory inv = new Inventory(9*6);

        return inv;
    }

    public CompetenciesContainer(int id, PlayerInventory inventory, PlayerEntity player) {
        super(ContainerType.GENERIC_9X6, id, inventory, buildPlayerCompetencies(player), 6);
        this.player = player;
    }

    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        return true;
    }

    @Override
    public ItemStack slotClick(int slotId, int dragType, ClickType clickTypeIn, PlayerEntity player) {
        LOGGER.info("SLOTCLICK {} {} {} {}", slotId, dragType, clickTypeIn, player);
        ItemStack stack = super.slotClick(slotId, dragType, clickTypeIn, player);
        detectAndSendChanges();
        return stack;
        /*
        if (this.inventorySlots.get(slotId).getStack().getItem().equals(Items.PUMPKIN)) {
            LOGGER.info("SLOTCLICK {} {} {} {}", slotId, dragType, clickTypeIn, player);
            player.inventory.setItemStack(ItemStack.EMPTY);
            detectAndSendChanges();
        }
        return ItemStack.EMPTY;*/
    }
}
