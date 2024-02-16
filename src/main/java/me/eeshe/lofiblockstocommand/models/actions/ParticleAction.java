package me.eeshe.lofiblockstocommand.models.actions;

import me.eeshe.lofiblockstocommand.models.ActiveCommandBlock;
import me.eeshe.lofiblockstocommand.models.config.ConfigParticle;
import org.bukkit.entity.Player;

public class ParticleAction extends Action {
    private final ConfigParticle configParticle;

    public ParticleAction(ConfigParticle configParticle) {
        this.configParticle = configParticle;
    }

    @Override
    public void run(ActiveCommandBlock activeCommandBlock, Player player) {
        configParticle.spawn(activeCommandBlock.getCenteredLocation());
    }
}
