
/** A mimicry-based Rock-Paper-Scissors player.
  * 
  * @author RR
  */
public class ApeBot implements RoShamBot {
    
    /** Returns the same action as the opponent's previous action.
      * 
      * @param lastOpponentMove the action that was played by the opposing 
      *        agent on the last round.
      *
      * @return the next action to play.
    */
    public Action getNextMove(Action lastOpponentMove) {
        return lastOpponentMove;
    }
    
}