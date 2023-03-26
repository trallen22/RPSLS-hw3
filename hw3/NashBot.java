
/** A Nash Equilibrium Rock-Paper-Scissors player.
  * 
  * @author RR
  */
public class NashBot implements RoShamBot {
 
    /** Returns an action according to the mixed strategy (1/3, 1/3, 1/3).
      * 
      * @param lastOpponentMove the action that was played by the opponent on
      *        the last round (this is disregarded).
      * @return the next action to play.
      */
    public Action getNextMove(Action lastOpponentMove) {
        double coinFlip = Math.random();
        
        if (coinFlip <= 1.0/5.0)
            return Action.ROCK;
        else if (coinFlip <= 2.0/5.0)
            return Action.PAPER;
        else if (coinFlip <= 3.0/5.0)
            return Action.SCISSORS;
        else if (coinFlip <= 4.0/5.0)
            return Action.LIZARD;
        else 
            return Action.SPOCK;
    }
    
}