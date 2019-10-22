package com.github.gruebite.commerce.competencies;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class CompetenciesContainerProvider implements INamedContainerProvider {
    @Nonnull
    @Override
    public ITextComponent getDisplayName() {
        return new StringTextComponent("Competencies");
    }

    @Nullable
    @Override
    public Container createMenu(int id, @Nonnull PlayerInventory inventory, @Nonnull PlayerEntity player) {
        return new CompetenciesContainer(id, inventory, player);
    }
}