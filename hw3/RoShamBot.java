
/** Declares the behavioral contract for a participant in the CSC 370 Rock-
  * Paper-Scissors-Lizard-Spock contest.
  * 
  * @author RR
  */
public interface RoShamBot {
    
    /** Returns the next action that this bot will take. */
    public Action getNextMove(Action lastOpponentMove);
    
}