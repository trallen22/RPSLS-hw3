import java.util.*;
/** A non-equilibrium Rock-Paper-Scissors player.
  * 
  * @author Tristan Allen 
  * @author Dylan Ameres
  */
public class FirstBot implements RoShamBot {
    
    private HashMap<Action, HashMap<Action, Integer>> nextMoveMap;
    private HashMap<Action, Integer> totalActions;
    private Action [] actionArray;
    private Action twoBack;
    private int curRound;
    
    public FirstBot(){
        curRound = 0;
        twoBack = Action.ROCK;
        nextMoveMap = new HashMap<Action, HashMap<Action, Integer>>(); // map of maps to try and keep track of tendency after throwing a certain thing 
        totalActions = new HashMap<Action, Integer>();
        actionArray = new Action[] { Action.ROCK, Action.PAPER, Action.SCISSORS, Action.LIZARD, Action.SPOCK };
        // load the map of maps
        for (int i = 0; i < 5; i++) { 
            totalActions.put(actionArray[i], 0);
            nextMoveMap.put(actionArray[i], new HashMap<Action, Integer>());
            for (int j = 0; j < 5; j++) {
                nextMoveMap.get(actionArray[i]).put(actionArray[j], 0);
            }
            
        }
    }
    
    private Action guessNextMove(Action twoBefore, Action lastMove) {
        int maxSpot = -1; 
        int maxVal = -1;
        int secondSpot = -1;
        int secondVal = -1;
        
        int [] tendency = new int[5];
        
        for (int i = 0; i < 5; i++) {
            tendency[i] = nextMoveMap.get(twoBefore).get(actionArray[i]);
            if (tendency[i] >= maxVal) {
                secondSpot = maxSpot; 
                secondVal = maxVal;
                maxSpot = i;
                maxVal = tendency[i];
            } else if (tendency[i] >= secondVal) {
                secondSpot = i;
                secondVal = tendency[i];
            }
        }
        
        return Action.ROCK;
    }
        
    /** Returns an action 
      * 
      * @param lastOpponentMove the action that was played by the opponent on
      *        the last round (this is disregarded).
      * @return the next action to play.
      */
    public Action getNextMove(Action lastOpponentMove) {
        Action tempTwoBack = twoBack;
        twoBack = lastOpponentMove;
        curRound++;
        totalActions.put(lastOpponentMove, totalActions.get(lastOpponentMove) + 1); // total number of each action 
        if (curRound < 3) { // throw random for first couple rounds
            int random = (int)Math.floor(Math.random() * 5);
            return actionArray[random];
        } else {
            // start trying to keep track of tendencies 
            nextMoveMap.get(tempTwoBack).put(lastOpponentMove, nextMoveMap.get(tempTwoBack).get(lastOpponentMove) + 1);
            if (nextMoveMap.get(tempTwoBack).get(lastOpponentMove) < 5) {
                return actionArray[2];
            }
            return actionArray[1];
        }
    }
    
}