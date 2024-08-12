# AWS flow log analyzer

## Assumptions
 1. Flow logs that are well formatted are handled. No data and skip records are not handled.
 2. Protocols listed in Protocol.java are the only ones considered for processing.

## Testing
 1. Supplied the flowlog.txt and lookuptable.txt in src\main\resources as input and validated the generated output manually.
 
## Build and execution
```bash
mvn clean install;  java -jar .\target\awsflowlog-1.0-SNAPSHOT.jar --log .\src\main\resources\flowlog.txt --lookup .\src\main\resources\lookuptable.txt --output .\target\output.txt 
```