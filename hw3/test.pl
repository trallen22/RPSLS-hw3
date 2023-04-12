$opp = 'NashBot';
$me = 'FirstBot';
$me2 = 'SecondBot';
$me3 = 'ThirdBot';
$numRounds = 10000;

$results = `java -Xmx5m Tournament.java $opp $me $numRounds`;
print("$results\n");

$results = `java -Xmx512m Tournament.java $me $opp $numRounds`;
print("$results\n");

$results = `java -Xmx512m Tournament.java $opp $me2 $numRounds`;
print("$results\n");

$results = `java -Xmx512m Tournament.java $me2 $opp $numRounds`;
print("$results\n");

$results = `java -Xmx512m Tournament.java $opp $me3 $numRounds`;
print("$results\n");

$results = `java -Xmx512m Tournament.java $me3 $opp $numRounds`;
print("$results\n");