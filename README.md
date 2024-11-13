## Start the server
mvn exec:java -Dexec.mainClass="com.example.DualServer" -Dexec.environmentVariables="TCP_PORT=2121,HTTP_PORT=2180"

## Test Web (Http)
curl http://localhost:2080

## Test TCP
nc localhost 2021