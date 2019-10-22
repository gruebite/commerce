package com.github.gruebite.commerce;

import com.github.gruebite.commerce.competencies.CompetenciesContainer;
import com.github.gruebite.commerce.competencies.CompetenciesContainerProvider;
import com.github.gruebite.commerce.playerdata.PlayerCompetencies;
import com.github.gruebite.commerce.playerdata.PlayerProperties;
import com.github.gruebite.commerce.playerdata.PropertiesDispatcher;
import com.sun.org.apache.xpath.internal.operations.String;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.inventory.container.SimpleNamedContainerProvider;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nullable;
import java.util.Objects;

public class ForgeEventHandlers {
    private static final Logger LOGGER = LogManager.getLogger();

    @SubscribeEvent
    public void onCraft(PlayerEvent.ItemCraftedEvent event) {
        if (event.getEntity().world.isRemote) {
            return;
        }
        ServerPlayerEntity player = (ServerPlayerEntity) event.getPlayer();
        //player.inventory.dropAllItems();
        //player.container.detectAndSendChanges();
    }

    @SubscribeEvent
    public void onBreak(PlayerEvent.BreakSpeed event) {
        if (event.getEntity().world.isRemote) {
            return;
        }
        //event.setCanceled(true);
    }

    @SubscribeEvent
    public void onRightClick(PlayerInteractEvent.RightClickItem event) {
        if (event.getPlayer().world.isRemote) {
            LOGGER.info("RIGHTCLICK on client");
            return;
        }
        LOGGER.info("RIGHTCLICK {}", event.getItemStack().getItem().getRegistryName());
        if (event.getItemStack().getItem().equals(Items.STICK))  {
            ItemStack trigger = new ItemStack(Items.PUMPKIN);
            int slot = event.getPlayer().inventory.getFirstEmptyStack();
            event.getPlayer().inventory.addItemStackToInventory(trigger);
            event.getPlayer().tick();
            //event.getPlayer().inventory.removeStackFromSlot(slot);
            event.getPlayer().container.detectAndSendChanges();
            event.setCanceled(true);
        } else if (event.getItemStack().getItem().equals(Items.PUMPKIN)) {
            event.getPlayer().openContainer(
                    new SimpleNamedContainerProvider(
                            new CompetenciesContainerProvider(),
                            new StringTextComponent("Competencies")));
        }
    }


    @SubscribeEvent
    public void onEntityConstructing(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof PlayerEntity) {
            if (!event.getObject().getCapability(PlayerProperties.PLAYER_COMPETENCIES).isPresent()) {
                event.addCapability(new ResourceLocation(Commerce.MODID, "properties"), new PropertiesDispatcher());
            }
        }
    }

    @SubscribeEvent
    public void onPlayerCloned(PlayerEvent.Clone event) {
        if (event.isWasDeath()) {
            LazyOptional<PlayerCompetencies> cap = event.getOriginal().getCapability(PlayerProperties.PLAYER_COMPETENCIES);
            cap.ifPresent(oldStore -> {
                event.getPlayer().getCapability(PlayerProperties.PLAYER_COMPETENCIES).ifPresent(newStore -> {
                    newStore.copyFrom(oldStore);
                });
            });
        }
    }

    @SubscribeEvent
    public void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        event.getPlayer().getCapability(PlayerProperties.PLAYER_COMPETENCIES).ifPresent(note -> {
            // Do player logged in stuff like add inventory.
        });
    }
}
