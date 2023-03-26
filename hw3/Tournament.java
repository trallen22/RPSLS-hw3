
/** Program that runs a Rock-Paper-Scissors-Lizard-Spock tournament between the 
  * two named players.
  * 
  * @author RR
  */
public class Tournament {
 
    public static void main(String[] args) {
        if (args.length != 3) {
            System.out.println("Usage: java Tournament <player1> <player2> <n>");
            System.out.println("where <player1> = class name of first bot.");
            System.out.println("      <player2> = class name of second bot.");
            System.out.println("      <n>       = number of rounds to play.");
            System.out.println("Example:");
            System.out.println("java Tournament NashBot MixedBot 10000");
        }
        
        // Determine number of rounds to run
        int numRounds = 0;
        try {
            numRounds = Integer.parseInt(args[2]);
        }
        catch (Exception e) {
            System.out.println("Error: invalid value for num rounds.");
            System.exit(-1);
        }
        
        // Instantiate players
        RoShamBot player1 = null;
        RoShamBot player2 = null;
        try {
            player1 = (RoShamBot)Class.forName(args[0]).
                        getDeclaredConstructor().newInstance();
            player2 = (RoShamBot)Class.forName(args[1]).
                        getDeclaredConstructor().newInstance();
        }
        catch (Exception e) {
            System.out.println("Error: one or more named bots could not be " +
                               "loaded.");
            System.out.println("Double check that your bots have been compiled" +
                               " and that the .class files are in the current" +
                               " directory.");
            System.exit(-1);
        }
        
        // Run tournament
        Arbiter judge = new Arbiter(player1, player2);
        for (int i = 0; i < numRounds; i++)
            judge.runRound();
        
        // Print scores
        int[] scores = judge.getCurrentScore();
        System.out.println(args[0] + ": " + scores[0]);
        System.out.println("Ties: " + scores[1]);
        System.out.println(args[1] + ": " + scores[2]);
    }
    
}