package com.github.gruebite.commerce.playerdata;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;

public class PlayerProperties {
    @CapabilityInject(PlayerCompetencies.class)
    public static Capability<PlayerCompetencies> PLAYER_COMPETENCIES;
}
