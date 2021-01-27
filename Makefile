run: compile
	java TrainSystemData
	java CustomerFrontEnd

compile: CustomerFrontEnd.class TrainSystemData.class

test: TestDatabase.class TestLogInAndRegister.class TestTicketSystemDatabase.class
	java -jar junit5.jar --class-path . --scan-classpath --details tree

CustomerFrontEnd.class: LogInAndRegister.class User.class TicketInfo.class TicketSystemDatabase.class TicketSystemDatabaseInterface.class CustomerFrontEnd.java
	javac CustomerFrontEnd.java

TrainSystemData.class: TrainSystemData.java
	javac -source 1.7 -target 1.7 -cp . TrainSystemData.java

LogInAndRegister.class: User.class LogInAndRegister.java
	javac LogInAndRegister.java

User.class: User.java
	javac User.java

TicketInfo.class: TicketInfo.java
	javac TicketInfo.java

TicketSystemDatabaseInterface.class: TicketInfo.class TicketSystemDatabaseInterface.java
	javac TicketSystemDatabaseInterface.java

TicketSystemDatabase.class: TicketInfo.class Database.class TicketSystemDatabase.java
	javac TicketSystemDatabase.java

Database.class: TicketInfo.class GraphADT.class MapADT.class HashTableMap.class CS400Graph.class Database.java
	javac Database.java

GraphADT.class: GraphADT.java
	javac GraphADT.java

CS400Graph.class: GraphADT.class CS400Graph.java
	javac CS400Graph.java

MapADT.class: MapADT.java
	javac MapADT.java

HashTableMap.class: MapADT.class HashTableMap.java
	javac HashTableMap.java

TestDatabase.class: Database.class TestDatabase.java junit5.jar
	javac -cp .:junit5.jar TestDatabase.java

TestLogInAndRegister.class: LogInAndRegister.class TestLogInAndRegister.java junit5.jar
	javac -cp .:junit5.jar TestLogInAndRegister.java

TestTicketSystemDatabase.class: TicketSystemDatabase.class TestTicketSystemDatabase.java junit5.jar
	javac -cp .:junit5.jar TestTicketSystemDatabase.java

clean:
	$(RM) *.class
