package viceCity.models.neighbourhood;

import viceCity.models.guns.Gun;
import viceCity.models.players.Player;

import java.util.Collection;

public class GangNeighbourhood implements Neighbourhood {
    @Override
    public void action(Player mainPlayer, Collection<Player> civilPlayers) {
        for (Player civilPlayer : civilPlayers) {
            for (Gun gunModel:mainPlayer.getGunRepository().getModels()) {
                while (civilPlayer.isAlive() && gunModel.canFire()){
                    civilPlayer.takeLifePoints(gunModel.fire());
                }
                if(!civilPlayer.isAlive()){
                    break;
                }
            }
        }
        for (Player civilPlayer : civilPlayers) {
            if(!civilPlayer.isAlive()){
                continue;
            }
            for (Gun gunModel: civilPlayer.getGunRepository().getModels()) {
                if(mainPlayer.isAlive() && gunModel.canFire()){
                    mainPlayer.takeLifePoints(gunModel.fire());
                }
                if (!mainPlayer.isAlive()){
                    break;
                }
            }
            if (!mainPlayer.isAlive()){
                break;
            }
        }

    }
}
