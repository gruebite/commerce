package gruebite.commerce;

import gruebite.commerce.competencies.CompetenciesContainerProvider;
import gruebite.commerce.competencies.Competency;
import gruebite.commerce.playerdata.PlayerProperties;
import gruebite.commerce.playerdata.PropertiesDispatcher;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.BoatEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.SimpleNamedContainerProvider;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.enchanting.EnchantmentLevelSetEvent;
import net.minecraftforge.event.entity.EntityMountEvent;
import net.minecraftforge.event.entity.player.*;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
        if (!(event.getEntity() instanceof ServerPlayerEntity)) {
            return;
        }
        ToolType toolType = event.getState().getBlock().getHarvestTool(event.getState());
        ServerPlayerEntity player = (ServerPlayerEntity)event.getPlayer();
        if (toolType == ToolType.SHOVEL && !PlayerProperties.isCompetent(player, Competency.EXCAVATING)) {
            event.setCanceled(true);
        } else if (toolType == ToolType.AXE && !PlayerProperties.isCompetent(player, Competency.WOODCUTTING)) {
            event.setCanceled(true);
        } else if (toolType == ToolType.PICKAXE &&
                !PlayerProperties.isCompetent(player, Competency.MINING)) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public void onRightClick(PlayerInteractEvent.RightClickItem event) {
        if (event.getPlayer().world.isRemote) {
            return;
        }
        LOGGER.info("RIGHTCLICK {}", event.getItemStack().getItem().getRegistryName());
        if (event.getItemStack().getItem().equals(Items.STICK)) {
            PlayerProperties.becomeCompetent((ServerPlayerEntity) event.getPlayer(), Competency.CARPENTRY);
        } else if (event.getItemStack().getItem() == Items.EMERALD &&
                PlayerProperties.isCompetent((ServerPlayerEntity)event.getPlayer(), Competency.ENCHANTING)) {
            event.getPlayer().setHeldItem(Hand.MAIN_HAND, ItemStack.EMPTY);
            event.getPlayer().giveExperiencePoints(500);
        } else if (event.getItemStack().getItem().equals(Items.PUMPKIN)) {
            event.getPlayer().openContainer(
                    new SimpleNamedContainerProvider(
                            new CompetenciesContainerProvider(),
                            new StringTextComponent("Competencies")));
        }
    }

    @SubscribeEvent
    public void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        World world = event.getPlayer().world;
        if (world.isRemote) {
            return;
        }

        Block block = world.getBlockState(event.getPos()).getBlock();
        if (block == Blocks.ENCHANTING_TABLE && !PlayerProperties.isCompetent((ServerPlayerEntity)event.getPlayer(), Competency.ENCHANTING)) {
            event.setCanceled(true);
        }
        if (block == Blocks.ANVIL && !PlayerProperties.isCompetent((ServerPlayerEntity)event.getPlayer(), Competency.ENCHANTING)) {
            event.setCanceled(true);
        }
        if (block == Blocks.BREWING_STAND && !PlayerProperties.isCompetent((ServerPlayerEntity)event.getPlayer(), Competency.ALCHEMY)) {
            event.setCanceled(true);
        }
        if (block == Blocks.FURNACE && !PlayerProperties.isCompetent((ServerPlayerEntity)event.getPlayer(), Competency.SMELTING)) {
            event.setCanceled(true);
        }
        if (block == Blocks.SMOKER && !PlayerProperties.isCompetent((ServerPlayerEntity)event.getPlayer(), Competency.COOKING)) {
            event.setCanceled(true);
        }
        if (block == Blocks.CARTOGRAPHY_TABLE && !PlayerProperties.isCompetent((ServerPlayerEntity)event.getPlayer(), Competency.CARTOGRAPHY)) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public void onMount(EntityMountEvent event) {
        Entity entity = event.getEntityMounting();
        if (entity.world.isRemote) {
            return;
        }

        if (entity instanceof ServerPlayerEntity) {
            if (event.getEntityBeingMounted() instanceof BoatEntity &&
                    !PlayerProperties.isCompetent((ServerPlayerEntity) entity, Competency.SEAFARING)) {
                event.setCanceled(true);
            } else if (!PlayerProperties.isCompetent((ServerPlayerEntity)entity, Competency.RIDING)) {
                event.setCanceled(true);
            }
        }
    }

    @SubscribeEvent
    public void onUseHoe(UseHoeEvent event) {
        if (event.getPlayer().world.isRemote) {
            return;
        }

        if (!PlayerProperties.isCompetent((ServerPlayerEntity)event.getPlayer(), Competency.FARMING)) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public void onEntityAttack(AttackEntityEvent event) {
        if (event.getPlayer().world.isRemote) {
            return;
        }

        Item item = event.getPlayer().getHeldItem(Hand.MAIN_HAND).getItem();
        boolean isSword = item == Items.WOODEN_SWORD || item == Items.STONE_SWORD || item == Items.IRON_SWORD || item == Items.DIAMOND_SWORD;
        boolean isAxe = item == Items.WOODEN_AXE || item == Items.STONE_AXE || item == Items.IRON_AXE || item == Items.DIAMOND_AXE;
        boolean isTrident = item == Items.TRIDENT;

        if (isSword && !PlayerProperties.isCompetent((ServerPlayerEntity)event.getPlayer(), Competency.SWORDFIGHTING)) {
            event.setCanceled(true);
        }
        if (isAxe && !PlayerProperties.isCompetent((ServerPlayerEntity)event.getPlayer(), Competency.WOODCUTTING)) {
            event.setCanceled(true);
        }
        if (isTrident && !PlayerProperties.isCompetent((ServerPlayerEntity)event.getPlayer(), Competency.LANCING)) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public void onArrowLoose(ArrowLooseEvent event) {
        if (event.getPlayer().world.isRemote) {
            return;
        }

        if (!PlayerProperties.isCompetent((ServerPlayerEntity)event.getPlayer(), Competency.ARCHERY)) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public void onItemFished(ItemFishedEvent event) {
        if (event.getPlayer().world.isRemote) {
            return;
        }

        if (!PlayerProperties.isCompetent((ServerPlayerEntity)event.getPlayer(), Competency.FISHING)) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public void onEntityConstructing(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof PlayerEntity) {
            if (!event.getObject().getCapability(PlayerProperties.MARKER).isPresent()) {
                event.addCapability(new ResourceLocation(Commerce.MODID, "properties"), new PropertiesDispatcher());
            }
        }
    }

    @SubscribeEvent
    public void onPlayerCloned(PlayerEvent.Clone event) {
        if (event.isWasDeath()) {
            LazyOptional<PlayerProperties> cap = event.getOriginal().getCapability(PlayerProperties.MARKER);
            cap.ifPresent(oldStore -> {
                event.getPlayer().getCapability(PlayerProperties.MARKER).ifPresent(newStore -> {
                    newStore.copyFrom(oldStore);
                });
            });
        }
    }

    @SubscribeEvent
    public void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        event.getPlayer().getCapability(PlayerProperties.MARKER).ifPresent(note -> {
            // Do player logged in stuff like add inventory.
        });
    }
}
