import java.util.*;
/** A non-equilibrium Rock-Paper-Scissors player.
  * 
  * @author Tristan Allen 
  * @author Dylan Ameres
  */
public class FirstBot implements RoShamBot {
    
    private HashMap<Action, HashMap<Action, Integer>> nextMoveMap;
    private HashMap<Action, Integer> totalActions; // { Action: number of times action has been thrown }
    private Action [] actionArray; // [ ROCK, PAPER, SCISSORS, LIZARD, SPOCK ]
    private Action twoBack; // opponent action before last action 
    private int curRound; // keeps track of round number 
    private HashMap<Action, String> strMap; // { "RP": best response } keys in alphabetical order 
    private double [][] outcomeMatrix;
    
    public FirstBot(){
        curRound = 0;
        twoBack = Action.ROCK;
        nextMoveMap = new HashMap<Action, HashMap<Action, Integer>>(); // map of maps to try and keep track of tendency after throwing a certain thing 
        strMap = new HashMap<Action, String>();
        totalActions = new HashMap<Action, Integer>();
        actionArray = new Action [] { Action.ROCK, Action.PAPER, Action.SCISSORS, Action.LIZARD, Action.SPOCK };
        
        // load the map of maps
        for (int i = 0; i < 5; i++) { 
            totalActions.put(actionArray[i], 0);
            nextMoveMap.put(actionArray[i], new HashMap<Action, Integer>());
            for (int j = 0; j < 5; j++) {
                nextMoveMap.get(actionArray[i]).put(actionArray[j], 0);
            }
            if (i == 0) {
                strMap.put(Action.ROCK, "R"); // R is ROCK
            } else if (i == 1) {
                strMap.put(Action.PAPER, "P"); // P is PAPER
            } else if (i == 2) {
                strMap.put(Action.SCISSORS, "S"); // S is SCISSORS
            } else if (i == 3) {
                strMap.put(Action.LIZARD, "L"); // L is LIZARD 
            } else if (i == 4) {
                strMap.put(Action.SPOCK, "K"); // K is SPOCK 
            }
        }
        
        outcomeMatrix = new double [][] {
                                                {0.5, 1.0, 0.0, 0.0, 1.0}, 
                                                {0.0, 0.5, 1.0, 1.0, 0.0}, 
                                                {1.0, 0.0, 0.5, 0.0, 1.0}, 
                                                {1.0, 0.0, 1.0, 0.5, 0.0}, 
                                                {0.0, 1.0, 0.0, 1.0, 0.5}, 
                                            };
        
    }
    
    private Action bestActionByOutcome(double[] tendencies) {
        int maxSpot = -1;
        double maxVal = -1;
        double [] sumOutcome = new double [] {0.0, 0.0, 0.0, 0.0, 0.0};
        
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                sumOutcome[j] += tendencies[i] * outcomeMatrix[i][j];
            }
        }
        
        for (int i = 0; i < 5; i++) {
            if (sumOutcome[i] >= maxVal) {
                maxSpot = i;
                maxVal = sumOutcome[i];
            }
        }
        
        return actionArray[maxSpot];
    }
    
    private double [] getTendency(Action lastMove) {
        
        double [] tendency = new double [5]; // the number of time an action was thrown after a given action
        int totalCurAction = totalActions.get(lastMove); 
        
        for (int i = 0; i < 5; i++) {
            // number of times an action was thrown after the last action
            int numNextAction = nextMoveMap.get(lastMove).get(actionArray[i]); 
            tendency[i] = (double)numNextAction / totalCurAction;
        }
        
        return tendency;
    }
        
    /** This bot tries to find the tendencies of the opponent by finding what they 
      * play most often back to back 
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
            double [] tempTendency = getTendency(lastOpponentMove);
            return bestActionByOutcome(tempTendency);
        }
    }
    
}