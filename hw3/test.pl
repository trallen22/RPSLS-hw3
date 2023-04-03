$opp = 'MixedBot';
$me = 'FirstBot';
$me2 = 'SecondBot';
$numRounds = 1000000;

$results = `time java -Xmx5m Tournament.java $opp $me $numRounds`;
print("$results\n");

$results = `time java -Xmx512m Tournament.java $opp $me2 $numRounds`;
print("$results\n");

$results = `time java -Xmx5m Tournament.java $me $opp $numRounds`;
print("$results\n");

$results = `time java -Xmx512m Tournament.java $me2 $opp $numRounds`;
print("$results\n");
