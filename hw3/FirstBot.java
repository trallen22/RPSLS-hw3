import java.util.*;
/** A non-equilibrium Rock-Paper-Scissors player.
  * 
  * @author Tristan Allen 
  * @author Dylan Ameres
  */
public class FirstBot implements RoShamBot {
    
    private HashMap<Action, HashMap<Action, Integer>> oppNextMoveMap; // { opp last action: { opp next action: # times action thrown }}
    private HashMap<Action, HashMap<Action, Integer>> myNextMoveMap; // { my last action: { opp next action: # times action thrown }}
    
    private HashMap<Action, Integer> totalOppActions; // { Action: # times action has been thrown by opponent }
    private HashMap<Action, Integer> totalMyActions; // { Action: # times action has been thrown by me }
    private Action [] actionArray; // [ ROCK, PAPER, SCISSORS, LIZARD, SPOCK ]
    private Action oppTwoBack; // opponent action before last action 
    private Action myTwoBack; // my action before last action 
    private Action myLastAction; // my last action 
    private int curRound; // keeps track of round number 
    private double [][] outcomeMatrix; // matrix to determine outcome of my move vs opponent move 
    
    private HashMap<Action, String> strMap; // used for testing
    
    public FirstBot(){
        curRound = 0;
        oppTwoBack = Action.ROCK;
        myTwoBack = Action.ROCK;
        myLastAction = Action.ROCK;
        
        oppNextMoveMap = new HashMap<Action, HashMap<Action, Integer>>(); // map of maps to keep track of tendencies 
        myNextMoveMap = new HashMap<Action, HashMap<Action, Integer>>();
        
        totalOppActions = new HashMap<Action, Integer>();
        totalMyActions = new HashMap<Action, Integer>();
        
        actionArray = new Action [] { Action.ROCK, Action.PAPER, Action.SCISSORS, Action.LIZARD, Action.SPOCK };
        
        // load the map of maps
        for (int i = 0; i < 5; i++) { 
            totalOppActions.put(actionArray[i], 0);
            totalMyActions.put(actionArray[i], 0);
            
            oppNextMoveMap.put(actionArray[i], new HashMap<Action, Integer>());
            myNextMoveMap.put(actionArray[i], new HashMap<Action, Integer>());
            
            for (int j = 0; j < 5; j++) {
                oppNextMoveMap.get(actionArray[i]).put(actionArray[j], 0);
                myNextMoveMap.get(actionArray[i]).put(actionArray[j], 0);
            }
        }
        
        outcomeMatrix = new double [][] {         // my move
                                                {0.0, 1.0, -1.0, -1.0, 1.0}, 
                                                {-1.0, 0.0, 1.0, 1.0, -1.0}, 
                                                {1.0, -1.0, 0.0, -1.0, 1.0}, // opponent move 
                                                {1.0, -1.0, 1.0, 0.0, -1.0}, 
                                                {-1.0, 1.0, -1.0, 1.0, 0.0}, 
                                            };
        
        // This is used for testing 
        strMap = new HashMap<Action, String>();
        strMap.put(Action.ROCK, "ROCK");
        strMap.put(Action.PAPER, "PAPER");
        strMap.put(Action.SCISSORS, "SCISSORS");
        strMap.put(Action.LIZARD, "LIZARD");
        strMap.put(Action.SPOCK, "SPOCK");
        
    }
    
    private Action bestActionByOutcome(double[] oppTendencies, double[] myTendencies) {
        int maxSpot = -1;
        double maxVal = -1;
        double [] sumOutcome = new double [] {0.0, 0.0, 0.0, 0.0, 0.0};
        
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                sumOutcome[j] += (oppTendencies[i] + myTendencies[i]) * outcomeMatrix[i][j];
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
    
    private double [] getTendency(Action lastMove, HashMap<Action, HashMap<Action, Integer>> curMap, HashMap<Action, Integer> totalActions) {
        
        double [] tendency = new double [5]; // the number of time an action was thrown after a given action
        int totalCurAction = totalActions.get(lastMove); 
        
        for (int i = 0; i < 5; i++) {
            // number of times an action was thrown after the last action
            int numNextAction = curMap.get(lastMove).get(actionArray[i]); 
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
        Action tempOppTwoBack = oppTwoBack;
        oppTwoBack = lastOpponentMove;
        
        myTwoBack = myLastAction;
        curRound++;
        
        totalOppActions.put(lastOpponentMove, totalOppActions.get(lastOpponentMove) + 1); // total number of each action 
        
        if (curRound < 3) { // throw random for first couple rounds
            int random = (int)Math.floor(Math.random() * 5);
            myLastAction = actionArray[random];
            totalMyActions.put(myLastAction, totalMyActions.get(myLastAction) + 1);
            return myLastAction;
        } else {
            // start trying to keep track of tendencies 
            oppNextMoveMap.get(tempOppTwoBack).put(lastOpponentMove, oppNextMoveMap.get(tempOppTwoBack).get(lastOpponentMove) + 1);
            myNextMoveMap.get(myTwoBack).put(lastOpponentMove, myNextMoveMap.get(myTwoBack).get(lastOpponentMove) + 1);
            
            // determine tendencies for successive opponent moves 
            double [] tendFromOpponent = getTendency(lastOpponentMove, oppNextMoveMap, totalOppActions);
            
            // determine tendencies for opponent after my moves 
            double [] tendFromMe = getTendency(myLastAction, myNextMoveMap, totalMyActions);
            
            Action bestOutcome = bestActionByOutcome(tendFromOpponent, tendFromMe);
            myLastAction = bestOutcome;
            
            totalMyActions.put(myLastAction, totalMyActions.get(myLastAction) + 1);
            
            return myLastAction;
        }
    }
    
}