$results = `java -Xmx5m Tournament.java SolidAsARockBot FirstBot 10000`;
print("$results\n");

$results = `java -Xmx512m Tournament.java NashBot FirstBot 500`;
print("$results\n");

$results = `java -Xmx512m Tournament.java MixedBot FirstBot 500`;
print("$results\n");

$results = `java -Xmx512m Tournament.java ApeBot FirstBot 500`;
print("$results\n");