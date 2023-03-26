
import java.util.Arrays;

/** Judge for running a single multi-round match between two Rock-Paper-
  *  Scissors-Lizard-Spock bots.
  * 
  * @author RR
  */
public class Arbiter {

    private int roundsCompleted;
    private Action[] lastRound; // 0 = player 1's action, 1 = player 2's action
    private RoShamBot player1;
    private RoShamBot player2;
    private int[] score; // score[0] = # player 1 wins, score[2] = # player 2 wins
    
    /** Intializes a new match between two specified bots.
      * 
      * @param player1 the first bot.
      * @param player2 the second bot.
      */
    public Arbiter(RoShamBot player1, RoShamBot player2) {
        this.roundsCompleted = 0;
        this.lastRound = new Action[2];
        this.player1 = player1;
        this.player2 = player2;
        this.score = new int[3];
    }
    
    /** Returns the current score of this match.
      * 
      * @return an array where the first element contains the number of wins
      * for player 1, the second element is the number of ties, and the third
      * element is the number of wins for player 2.
      */
    public int[] getCurrentScore() {
        return Arrays.copyOf(this.score, this.score.length);
    }
    
    
    /** Plays a single round between player 1 and 2. */
    public void runRound() {
        Action a1;
        Action a2;
        
        if (this.roundsCompleted == 0) {
            // For very first round, we pretend that both players threw ROCK
            // on previous round.
            a1 = player1.getNextMove(Action.ROCK);
            a2 = player2.getNextMove(Action.ROCK);
        }
        else {
            // Pass in opponent's last move
            a1 = player1.getNextMove(this.lastRound[1]);
            a2 = player2.getNextMove(this.lastRound[0]);
        }
        
        // Determine winner, update scores and record the player actions.
        boolean p1Win = (((a1 == Action.SCISSORS) && (a2 == Action.PAPER)) ||
                         ((a1 == Action.PAPER) && (a2 == Action.ROCK)) ||
                         ((a1 == Action.ROCK) && (a2 == Action.LIZARD)) ||
                         ((a1 == Action.LIZARD) && (a2 == Action.SPOCK)) ||
                         ((a1 == Action.SPOCK) && (a2 == Action.SCISSORS)) ||
                         ((a1 == Action.SCISSORS) && (a2 == Action.LIZARD)) ||
                         ((a1 == Action.LIZARD) && (a2 == Action.PAPER)) ||
                         ((a1 == Action.PAPER) && (a2 == Action.SPOCK)) ||
                         ((a1 == Action.SPOCK) && (a2 == Action.ROCK)) ||
                         ((a1 == Action.ROCK) && (a2 == Action.SCISSORS)));
        
        boolean p2Win = (((a2 == Action.SCISSORS) && (a1 == Action.PAPER)) ||
                         ((a2 == Action.PAPER) && (a1 == Action.ROCK)) ||
                         ((a2 == Action.ROCK) && (a1 == Action.LIZARD)) ||
                         ((a2 == Action.LIZARD) && (a1 == Action.SPOCK)) ||
                         ((a2 == Action.SPOCK) && (a1 == Action.SCISSORS)) ||
                         ((a2 == Action.SCISSORS) && (a1 == Action.LIZARD)) ||
                         ((a2 == Action.LIZARD) && (a1 == Action.PAPER)) ||
                         ((a2 == Action.PAPER) && (a1 == Action.SPOCK)) ||
                         ((a2 == Action.SPOCK) && (a1 == Action.ROCK)) ||
                         ((a2 == Action.ROCK) && (a1 == Action.SCISSORS)));
        
        if (p1Win)
            this.score[0]++;
        else if (p2Win)
            this.score[2]++;
        else // tie
            this.score[1]++;
        
        this.lastRound[0] = a1;
        this.lastRound[1] = a2;
        this.roundsCompleted++;
    }
}