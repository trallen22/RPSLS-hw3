import java.util.*;

/**
 * A Nash Equilibrium Rock-Paper-Scissors player.
 * 
 * @author RR
 */
public class ThirdBot implements RoShamBot {

    /**
     * Returns an action assuming the opponent will rotate their move in an order.
     * Plays a move that would beat their next rotated move, with a 50% chance to be
     * either best move.
     * 
     * @param lastOpponentMove the action that was played by the opponent on
     *                         the last round (this is disregarded).
     * @return the next action to play.
     */
    public Action getNextMove(Action lastOpponentMove) {
        // Order: ROCK, PAPER, SCISSORS, LIZARD, SPOCK
        double coinFlip = Math.random();

        if (lastOpponentMove == Action.ROCK && coinFlip <= 1.0 / 2.0) // Next move will be Paper
            return Action.SCISSORS;
        else if (lastOpponentMove == Action.ROCK && coinFlip > 1.0 / 2.0)
            return Action.LIZARD;
        else if (lastOpponentMove == Action.PAPER && coinFlip <= 1.0 / 2.0) // Next move will be Scissors
            return Action.ROCK;
        else if (lastOpponentMove == Action.PAPER && coinFlip > 1.0 / 2.0)
            return Action.SPOCK;
        else if (lastOpponentMove == Action.SCISSORS && coinFlip <= 1.0 / 2.0) // Next move will be Lizard
            return Action.ROCK;
        else if (lastOpponentMove == Action.SCISSORS && coinFlip > 1.0 / 2.0)
            return Action.SCISSORS;
        else if (lastOpponentMove == Action.LIZARD && coinFlip <= 1.0 / 2.0) // Next move will be Spock
            return Action.LIZARD;
        else if (lastOpponentMove == Action.LIZARD && coinFlip > 1.0 / 2.0)
            return Action.PAPER;
        else if (lastOpponentMove == Action.SPOCK && coinFlip <= 1.0 / 2.0) // Next move will be Rock
            return Action.PAPER;
        else
            return Action.SPOCK;
    }

}