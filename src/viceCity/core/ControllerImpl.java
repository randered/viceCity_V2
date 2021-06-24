package viceCity.core;

import viceCity.common.ConstantMessages;
import viceCity.core.interfaces.Controller;
import viceCity.models.guns.Gun;
import viceCity.models.guns.Pistol;
import viceCity.models.guns.Rifle;
import viceCity.models.neighbourhood.GangNeighbourhood;
import viceCity.models.neighbourhood.Neighbourhood;
import viceCity.models.players.CivilPlayer;
import viceCity.models.players.MainPlayer;
import viceCity.models.players.Player;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Deque;

public class ControllerImpl implements Controller {
    private MainPlayer mainPlayer;
    private Collection<Player> civilPlayers;
    private Deque<Gun> guns;
    private Neighbourhood neighbourhood;

    public ControllerImpl() {
        this.mainPlayer = new MainPlayer();
        this.civilPlayers = new ArrayList<>();
        this.guns = new ArrayDeque<>();
        this.neighbourhood = new GangNeighbourhood();
    }

    @Override
    public String addPlayer(String name) {
        Player player = new CivilPlayer(name);
        this.civilPlayers.add(player);
        return String.format(ConstantMessages.PLAYER_ADDED, name);
//        Creates a civil player with the given name.
//        The method should return the following message:
//•	"Successfully added civil player: {player name}!"
    }

    @Override
    public String addGun(String type, String name) {
        Gun gun = null;
// Tried to use getName(), but not receiving the correct name as parameter.
        // The java.lang.Class.getSimpleName() returns the simple name of the underlying class as given in the source code.
        // Returns an empty string if the underlying class is anonymous.

        if (Pistol.class.getSimpleName().equals(type)) {
            gun = new Pistol(name);
        } else if (Rifle.class.getSimpleName().equals(type)) {
            gun = new Rifle(name);
        }
        if (gun == null) {
            return ConstantMessages.GUN_TYPE_INVALID;
        } else {
            this.guns.offer(gun);
            return String.format(ConstantMessages.GUN_ADDED, name, type);
        }
//        Creates a gun with the provided type and name.
//                If the gun type is invalid, the method should return the following message:
//•	"Invalid gun type!"
//        If the gun type is correct, keep the gun and return the following message:
//•	"Successfully added {gun name} of type: {gun type}"

    }

    @Override
    public String addGunToPlayer(String name) {
        Gun gun = guns.peek();

        if (gun == null) {
            return ConstantMessages.GUN_QUEUE_IS_EMPTY;
        }
        if (name.equals("Vercetti")) {
            gun = this.guns.poll();
            this.mainPlayer.getGunRepository().add(gun);
            return String.format(ConstantMessages.GUN_ADDED_TO_MAIN_PLAYER, gun.getName(), "Tommy Vercetti");
        } else {
            Player player = this.civilPlayers.stream()
                    .filter(e -> e.getName().equals(name))
                    .findFirst().orElse(null);
            if (player == null) {
                return ConstantMessages.CIVIL_PLAYER_DOES_NOT_EXIST;
            } else {
                gun = this.guns.poll();
                player.getGunRepository().add(gun);
                return String.format
                        (ConstantMessages.GUN_ADDED_TO_CIVIL_PLAYER, gun.getName(), player.getName());
            }
        }
//        Adds the first added gun to the player's gun repository.
//•	If there no guns in the queue, return the following message:
//        "There are no guns in the queue!"
//•	If the name of the player is "Vercetti", you need to add the gun to the main player's repository and return the following message:
//        "Successfully added {gun name} to the Main Player: Tommy Vercetti"
//•	If you receive a name which doesn't exist, you should return the following message:
//        "Civil player with that name doesn't exists!"
//•	If everything is successful, you should add the gun to the player's repository and return the following message:
//        "Successfully added {gun name} to the Civil Player: {player name}"

    }

    @Override
    public String fight() {

        this.neighbourhood.action(this.mainPlayer, this.civilPlayers);

        int deadCivilPlayers = 0;
        int civilPlayersLeft = 0;
        boolean allCivilAlive = true;
        StringBuilder finalMessage = new StringBuilder();

        for (Player civilPlayer : civilPlayers) {
            if (!civilPlayer.isAlive()) {
                deadCivilPlayers++;
            }
        }

        for (Player civilPlayer : civilPlayers) {
            if (civilPlayer.getLifePoints() != 50) {
                allCivilAlive = false;
            }
        }

        if (this.mainPlayer.getLifePoints() == 100 && allCivilAlive) {
            finalMessage.append(ConstantMessages.FIGHT_HOT_HAPPENED);
        } else {
            civilPlayersLeft = this.civilPlayers.size() - deadCivilPlayers;
            finalMessage.append(ConstantMessages.FIGHT_HAPPENED);
            finalMessage.append(System.lineSeparator() + String.format(ConstantMessages.MAIN_PLAYER_LIVE_POINTS_MESSAGE, this.mainPlayer.getLifePoints()));
            finalMessage.append(System.lineSeparator() + String.format(ConstantMessages.MAIN_PLAYER_KILLED_CIVIL_PLAYERS_MESSAGE, deadCivilPlayers));
            finalMessage.append(System.lineSeparator());
            finalMessage.append(String.format(ConstantMessages.CIVIL_PLAYERS_LEFT_MESSAGE, civilPlayersLeft));


        }
        return finalMessage.toString();

//        When the fight command is called, the action happens. You should start the action between the main player and all the civil players.
//        When a civil player is killed, it should be removed from the repository.
//•	If the main player still has all of his points and no one is dead or harmed from the civil players, you should return the following messages:
//        "Everything is okay!"
//•	If any of the players has different life points, you should return the following message:
//        "A fight happened:"
//        "Tommy live points: {main player life points}!"
//        "Tommy has killed: {dead civil players} players!"
//        "Left Civil Players: {civil players count}!"

    }
}
