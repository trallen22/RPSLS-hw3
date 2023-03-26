
/** A non-equilibrium Rock-Paper-Scissors player.
  * 
  * @author RR
  */
public class MixedBot implements RoShamBot {
 
    /** Returns an action according to the mixed strategy (0.5, 0.5, 0, 0, 0).
      * 
      * @param lastOpponentMove the action that was played by the opponent on
      *        the last round (this is disregarded).
      * @return the next action to play.
      */
    public Action getNextMove(Action lastOpponentMove) {
        double coinFlip = Math.random();
        
        if (coinFlip <= 0.5)
            return Action.ROCK;
        else
            return Action.PAPER;
    }
    
}