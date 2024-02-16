package me.eeshe.lofiblockstocommand.models.actions;

import me.eeshe.lofiblockstocommand.models.ActiveCommandBlock;
import me.eeshe.lofiblockstocommand.models.config.ConfigSound;
import org.bukkit.entity.Player;

public class SoundAction extends Action {
    private final ConfigSound configSound;

    public SoundAction(ConfigSound configSound) {
        this.configSound = configSound;
    }

    @Override
    public void run(ActiveCommandBlock activeCommandBlock, Player player) {
        configSound.play(activeCommandBlock.getCenteredLocation());
    }
}
