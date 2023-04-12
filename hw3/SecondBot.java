import java.util.*;
/** A non-equilibrium Rock-Paper-Scissors player.
  * 
  * @author Tristan Allen 
  * @author Dylan Ameres
  */
public class SecondBot implements RoShamBot {
    
    private int [][] oppThenOpp; // oppNextMoveMap in matrix form
    private int [][] meThenOpp; // myNextMoveMap in matrix form 
    
    private int [] oppActionArray; // total for each action by opponent
    private int [] meActionArray; // total for each action by me 
    
    private Action [] actionArray; // [ ROCK, PAPER, SCISSORS, LIZARD, SPOCK ]
    private HashMap<Action, Integer> indexMap; // index of each Action in actionArray    
    
    private Action oppTwoBack; // opponent action before last action 
    private Action myTwoBack; // my action before last action 
    private Action myLastAction; // my last action 
    private int curRound; // keeps track of round number 
    private double [][] outcomeMatrix; // matrix to determine outcome of my move vs opponent move 
    
    private HashMap<Action, String> strMap; // used for testing
    
    public SecondBot(){
        curRound = 0;
        oppTwoBack = Action.ROCK;
        myTwoBack = Action.ROCK;
        myLastAction = Action.ROCK;
        
        oppThenOpp = new int [][] {             // opp response 
                                            { 0, 0, 0, 0, 0 }, 
                                            { 0, 0, 0, 0, 0 }, 
                                            { 0, 0, 0, 0, 0 }, // opp prev move
                                            { 0, 0, 0, 0, 0 }, 
                                            { 0, 0, 0, 0, 0 },         
                                        };
        
        meThenOpp = new int [][] {              // opp response 
                                            { 0, 0, 0, 0, 0 }, 
                                            { 0, 0, 0, 0, 0 }, 
                                            { 0, 0, 0, 0, 0 }, // my move
                                            { 0, 0, 0, 0, 0 }, 
                                            { 0, 0, 0, 0, 0 },         
                                        };
        
        oppActionArray = new int[] {0, 0, 0, 0, 0}; // total of each opponent actions
        meActionArray = new int[] {0, 0, 0, 0, 0}; // total of each my actions 
        
        actionArray = new Action [] { Action.ROCK, Action.PAPER, Action.SCISSORS, Action.LIZARD, Action.SPOCK };
        indexMap = new HashMap<Action, Integer>();
        
        for (int i = 0; i < 5; i++) {
            indexMap.put(actionArray[i], i);
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
    
    private double [] getTendency(Action lastMove, int[][] curMatrix, int[] totalArray) {
        
        double [] tendency = new double [5]; // the number of time an action was thrown after a given action
        int totalCurAction = totalArray[indexMap.get(lastMove)]; 
        
        for (int i = 0; i < 5; i++) {
            // number of times an action was thrown after the last action
            int numNextAction = curMatrix[indexMap.get(lastMove)][i];
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
        
        oppActionArray[indexMap.get(lastOpponentMove)] += 1;
        
        if (curRound < 3) { // throw random for first couple rounds
            int random = (int)Math.floor(Math.random() * 5);
            myLastAction = actionArray[random];
            meActionArray[indexMap.get(myLastAction)] += 1;
            return myLastAction;
        } else {
            // start trying to keep track of tendencies 
            oppThenOpp[indexMap.get(tempOppTwoBack)][indexMap.get(lastOpponentMove)] += 1;
            meThenOpp[indexMap.get(myTwoBack)][indexMap.get(lastOpponentMove)] += 1;
            
            // determine tendencies for successive opponent moves 
            double [] tendFromOpponent = getTendency(lastOpponentMove, oppThenOpp, oppActionArray);
            
            // determine tendencies for opponent after my moves 
            double [] tendFromMe = getTendency(myLastAction, meThenOpp, meActionArray);
            
            Action bestOutcome = bestActionByOutcome(tendFromOpponent, tendFromMe);
            myLastAction = bestOutcome;
            
            meActionArray[indexMap.get(myLastAction)] += 1;
            
            return myLastAction;
        }
    }
    
}