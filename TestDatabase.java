// --== CS400 File Header Information ==--
// Name: Weijie Zhou
// Email: wzhou226@wisc.edu
// Team: CG
// Role: Test Engineer 1
// TA: Yeping Wang
// Lecturer: Gary Daul
// Notes to Grader: <optional extra notes>

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @description: Test the ability in the class Database
 * @author: Weijie Zhou
 **/
public class TestDatabase {
    private Database database;

    @BeforeEach
    public void setUp(){
        // Initialize the database
        database = new Database();

        // Clear all data in the database
        database.clearDatabase();

        // Add cities into database
        database.addCity("Beijing");
        database.addCity("Tokyo");
        database.addCity("Washington");
        database.addCity("Madison");
        database.addCity("Harrisburg");

        // Add connections between cities into database
        database.addConnection("Beijing", "Tokyo", 380, 5);
        database.addConnection("Washington", "Tokyo", 730, 15);
        database.addConnection("Madison", "Harrisburg", 310, 7.5);
        database.addConnection("Madison", "Washington", 200, 7.5);
        database.addConnection("Washington", "Harrisburg", 200, 5);
        database.addConnection("Beijing", "Madison", 2400, 15);
        database.addConnection("Tokyo", "Madison", 900, 20);
        database.addConnection("Beijing", "Washington", 1820, 15);
        database.addConnection("Tokyo", "Harrisburg", 1400, 13.5);

        // Add Tickets into database
        database.addTicket("1990", new TicketInfo("1990", "Bob", "Beijing", "Madison", Arrays.asList("Beijing", "Tokyo", "Madison"), LocalDateTime.of(2020, 1, 3, 10, 00, 00), LocalDateTime.of(2020, 1, 3, 15, 00, 00), 5.0, 1280, 1));
        database.addTicket("1991", new TicketInfo("1991", "Kevin", "Madison", "Harrisburg", Arrays.asList("Madison", "Harrisburg"), LocalDateTime.of(2020, 2, 6, 8, 00, 00), LocalDateTime.of(2020, 2, 6, 10, 00, 00), 2.0, 310, 2));
        database.addTicket("1992", new TicketInfo("1992", "Rosa", "Madison", "Harrisburg", Arrays.asList("Madison", "Harrisburg"), LocalDateTime.of(2020, 3, 9, 18, 00, 00), LocalDateTime.of(2020, 3, 9, 20, 00, 00), 2.0, 310, 2));
        database.addTicket("1993", new TicketInfo("1993", "James", "Beijing", "Washington", Arrays.asList("Beijing", "Tokyo", "Washington"), LocalDateTime.of(2020, 4, 12, 15, 00, 00), LocalDateTime.of(2020, 4, 13, 11, 00, 00), 20.0, 1110, 1));
        database.addTicket("1994", new TicketInfo("1994", "Harden", "Beijing", "Madison", Arrays.asList("Beijing", "Tokyo", "Madison"), LocalDateTime.of(2020, 5, 11, 15, 00, 00), LocalDateTime.of(2020,  5, 12, 17, 00), 25.0, 2090, 1));
        database.addTicket("1995", new TicketInfo("1995", "Juliet", "Beijing", "Madison", Arrays.asList("Beijing", "Tokyo", "Madison"), LocalDateTime.of(2020, 11, 30, 18, 00, 00), LocalDateTime.of(2020, 12, 1, 19, 00, 00), 25.0, 2090, 1));
        database.addTicket("1996", new TicketInfo("1996", "Jack", "Washington", "Beijing", Arrays.asList("Beijing", "Washington"), LocalDateTime.of(2020, 6, 1, 15, 00, 00), LocalDateTime.of(2020, 6, 2, 6, 00, 00), 15.0, 1820, 2));
        database.addTicket("1997", new TicketInfo("1997", "John", "Madison", "Tokyo", Arrays.asList("Madison", "Tokyo"), LocalDateTime.of(2020, 7, 18, 8, 00, 00), LocalDateTime.of(2020, 7, 19, 4, 00, 00), 20.0, 900, 1));
        database.addTicket("1998", new TicketInfo("1998", "Amy", "Harrisburg", "Madison", Arrays.asList("Harrisburg", "Madison"), LocalDateTime.of(2020, 8, 22, 15, 00, 00), LocalDateTime.of(2020, 8, 22, 22, 30, 00), 7.5, 310, 1));
        database.addTicket("1999", new TicketInfo("1999", "Lisa", "Tokyo", "Harrisburg", Arrays.asList("Tokyo", "Harrisburg"), LocalDateTime.of(2020, 9, 20, 10, 00, 00), LocalDateTime.of(2020, 9, 20, 23, 30, 00), 13.5, 1400, 2));
        database.addTicket("2000", new TicketInfo("2000", "Rose", "Tokyo", "Harrisburg", Arrays.asList("Tokyo", "Madison", "Harrisburg"), LocalDateTime.of(2020, 10, 10, 15, 00, 00), LocalDateTime.of(2020, 10, 11, 18, 30, 00), 27.5, 1210, 1));
        database.addTicket("2001", new TicketInfo("2001", "Blink", "Madison", "Tokyo", Arrays.asList("Madison", "Tokyo"), LocalDateTime.of(2020, 11, 19, 18, 00, 00), LocalDateTime.of(2020, 11, 20, 14, 00, 00), 20.0, 900, 1));
        database.addTicket("2002", new TicketInfo("2002", "Romeo", "Beijing", "Washington", Arrays.asList("Beijing", "Tokyo", "Washington"), LocalDateTime.of(2020, 12, 21, 10, 00, 00), LocalDateTime.of(2020, 12, 22, 6, 00, 00), 20.0, 1110, 1));
        database.addTicket("2003", new TicketInfo("2003", "Jason", "Beijing", "Washington", Arrays.asList("Beijing", "Washington"), LocalDateTime.of(2020, 1, 23, 10, 00, 00), LocalDateTime.of(2020, 1, 24, 1, 00, 00), 15.0, 1820, 2));
        database.addTicket("2004", new TicketInfo("2004", "Michael", "Washington", "Harrisburg", Arrays.asList("Washington", "Harrisburg"), LocalDateTime.of(2020, 2, 27, 8, 00, 00), LocalDateTime.of(2020, 2, 27, 13, 00, 00), 5, 200, 1));
        database.addTicket("2005", new TicketInfo("2005", "Jack", "Washington", "Tokyo", Arrays.asList("Washington", "Tokyo"), LocalDateTime.of(2020, 5, 28, 15, 00, 00), LocalDateTime.of(2020, 5, 29, 6, 00, 00), 15.0, 730, 1));
        database.addTicket("2006", new TicketInfo("2006", "Durant", "Beijing", "Madison", Arrays.asList("Beijing", "Madison"), LocalDateTime.of(2020, 4, 14, 18, 00, 00), LocalDateTime.of(2020, 4, 15, 9, 00, 00), 15.0, 2400, 2));
    }

    /**
     * test add and delete method by adding and deleting a city
     */
    @Test
    public void testAddAndDeleteCity(){
        assertFalse(database.containsCity("New York"));
        // Test add city
        if(!database.addCity("New York") || !database.containsCity("New York"))
            fail("add city fails");
        // Test delete city
        if(!database.deleteCity("New York") || database.containsCity("New York"))
           fail("delete city fails");
    }

    /**
     * test contains city method by checking a city
     */
    @Test
    public void testContainsCity(){
        if(!database.containsCity("Beijing"))
            fail("contain method fails");
    }

    /**
     * test add, delete, contains connection method
    */
    @Test
    public void testAddAndDeleteAndContainsConnection(){
        // Add more cities
        String city1 = "Lancaster";
        String city2 = "Salt Lake City";
        // Add cities into the database
        database.addCity(city1);
        database.addCity(city2);

        // Test add connection
        if(!database.addConnection(city1, city2, 255, 6.5) || !database.containsConnection(city1, city2) ||
                database.getConnectionCost(city1, city2) != 255 || database.getConnectionTime(city1, city2) != 6.5 )
            fail("Did not successfully add connection");

        // Test delete connection
        if(!database.deleteConnection(city1, city2) || database.containsConnection(city1, city2))
            fail("Did not successfully delete connection");

    }

    /**
     * test update connection method
    */
    @Test
    public void testUpdateConnection(){
        // Add more cities
        String city1 = "Lancaster";
        String city2 = "Salt Lake City";
        // Add cities into the database
        database.addCity(city1);
        database.addCity(city2);

        database.addConnection(city1, city2, 255, 6.5);

       // Invalid input
        if (database.updateConnectionTime("NotCity", "NotAnotherCity", 7.5) || database.containsConnection("NotCity", "NotAnotherCity")) {
            fail("Should not have been able to update connection");
        }

        if(!database.updateConnectionTime(city1, city2, 7.5) || database.getConnectionTime(city1, city2) != 7.5)
            fail("Did not successfully update connection time");

        if(!database.updateConnectionPrice(city1, city2, 300) || database.getConnectionCost(city1, city2) != 300)
            fail("Did not successfully update connection price");
    }

    /**
     * test get connection price method and get connection time method
    */
    @Test
    public void testGetConnection(){
        // Test connection price between cities
        if(database.getConnectionCost("Beijing", "Madison") != 2400.0
                || database.getConnectionCost("Beijing", "Tokyo") != 380
                || database.getConnectionCost("Madison", "Harrisburg") != 310)
            fail("The get connection price method is not correct!");
        // Test connection time between cities
        if(database.getConnectionTime("Beijing", "Madison") != 15
                || database.getConnectionTime("Beijing", "Tokyo") != 5
                || database.getConnectionTime("Madison", "Harrisburg") != 7.5)
            fail("The get connection price method is not correct!");
    }

    /**
     * test check city name method and check city connected method
    */
    @Test
    public void testCheckCity(){
        // Test check city name method
        if(!database.checkCityName("Beijing") || !database.checkCityName("Tokyo")
                || !database.checkCityName("Washington") || !database.checkCityName("Harrisburg")
                || !database.checkCityName("Madison"))
            fail("Check city name method fail!");

        // Test check city connected
        if(!database.checkCitiesConnected("Beijing", "Tokyo") || !database.checkCitiesConnected("Tokyo", "Washington")
                || !database.checkCitiesConnected("Madison", "Harrisburg") || !database.checkCitiesConnected("Madison", "Washington")
                || !database.checkCitiesConnected("Madison", "Tokyo") || !database.checkCitiesConnected("Washington", "Harrisburg")
                || !database.checkCitiesConnected("Beijing", "Madison") || !database.checkCitiesConnected("Tokyo", "Madison"))
            fail("Check connection between cities method fail!");
    }

    /**
     * test get low cost path and get low time path
    */
    @Test
    public void testGetLowPath(){
        // Test get low cost path
        assertTrue(database.getLowCostPath("Madison", "Beijing").toString().equals(
                "[Madison, Tokyo, Beijing]"
        ));

        assertTrue(database.getLowCostPath("Harrisburg", "Tokyo").toString().equals(
                "[Harrisburg, Washington, Tokyo]"
        ));
        // Test get low time path
        assertTrue(database.getLowtimePath("Madison", "Beijing").toString().equals(
                "[Madison, Beijing]"
        ));

        assertTrue(database.getLowtimePath("Harrisburg", "Tokyo").toString().equals(
                "[Harrisburg, Tokyo]"
        ));
    }

    /**
     * test get low cost and get low time
    */
    @Test
    public void testGetLow(){
        // Test get low cost
        assertTrue(database.getLowCost("Madison", "Beijing") == 1280.0);
        assertTrue(database.getLowCost("Harrisburg", "Tokyo") == 930.0);

        // Test get low time
        assertTrue(database.getLowTime("Madison", "Beijing") == 15.0);
        assertTrue(database.getLowTime("Harrisburg", "Tokyo") == 13.5);
    }

    /**
     * test get number of cities and get number of connections
    */
    @Test
    public void testGetNumbers(){
        // Test get initial number of cities
        if (database.getNumOfCities() != 5) {
            fail("There should be 5 cities. Returned: " + database.getNumOfCities());
        }

        // Test get initial number of connections
        if(database.getNumOfConnections() != 18){
            fail("There should be 18 connections. Returned: " + database.getNumOfConnections());
        }

        // Add more cities
        String city1 = "Lancaster";
        String city2 = "Salt Lake City";
        // Add cities into the database
        database.addCity(city1);
        database.addCity(city2);

        database.addConnection(city1, city2, 255, 400);

        // Test get new number of cities
        if (database.getNumOfCities() != 7) {
            fail("There should be 7 cities. Returned: " + database.getNumOfCities());
        }
        // Test get new number of connections
        if(database.getNumOfConnections() != 20){
            fail("There should be 20 connections. Returned: " + database.getNumOfConnections());
        }
    }

    /**
     * test add, delete, has ticket method
    */
    @Test
    public void testAddAndRemoveAndHasTicket(){

        // Test add ticket
        if(!database.addTicket("1889", new TicketInfo("1889", "Lucy", "Washington", "Tokyo", Arrays.asList("Washington, Tokyo"), LocalDateTime.of(2020, 6, 8, 15, 00, 00), LocalDateTime.of(2020, 6, 9, 6, 00, 00), 15, 730, 1)) || !database.hasTicket("1889"))
            fail("Did not successfully add ticket");

        // Test check if has ticket
        if(!database.hasTicket("1889"))
            fail("Has ticket method fails");

        // Test delete ticket
        if(!database.removeTicket("1889") || database.hasTicket("1889"))
            fail("Did not successfully delete ticket");

    }

    /**
     * test update ticket method
    */
    @Test
    public void testUpdateTicket(){
        // check whether the update is complete or not
        if (!database.updateTicket("1996", new TicketInfo("1996", "Romeo", "Beijing", "Washington", Arrays.asList("Beijing", "Tokyo", "Washington"), LocalDateTime.of(2020, 12, 21, 10, 00, 00), LocalDateTime.of(2020, 12, 22, 6, 00, 00), 20, 1110, 1))) {
            fail("updateTicket method fails");
        }

        // check whether the information is updated
        if (!database.getTicket("1996").getName().equals("Romeo") ||
                !database.getTicket("1996").getDepartCity().equals("Beijing") ||
                !database.getTicket("1996").getArriveCity().equals("Washington") ||
                !database.getTicket("1996").getTravelPath().toString().equals("[Beijing, Tokyo, Washington]") ||
                !database.getTicket("1996").getDepartTime().toString().equals(LocalDateTime.of(2020, 12, 21, 10, 00, 00).toString()) ||
                !database.getTicket("1996").getArriveTime().toString().equals( LocalDateTime.of(2020, 12, 22, 6, 00, 00).toString()) ||
                database.getTicket("1996").getDurationInHours() != 20 ||
                database.getTicket("1996").getPrice() != 1110 ||
                database.getTicket("1996").getOption() != 1
        ) {
            fail("updateTicket method fails");
        }
    }

    /**
     * test the ability to get ticket by ticket id and name
    */
    @Test
    public void testGetTicket(){
        // test to get a ticketInfo object by ticket id
        TicketInfo ticket = database.getTicket("1995");

        System.out.println(ticket.getArriveTime().toString());
        // Check the ticketInfo object information
        if (!ticket.getName().equals("Juliet") ||
                !ticket.getDepartCity().equals("Beijing") ||
                !ticket.getArriveCity().equals("Madison") ||
                !ticket.getTravelPath().toString().equals("[Beijing, Tokyo, Madison]") ||
                !ticket.getDepartTime().toString().equals(LocalDateTime.of(2020, 11, 30, 18, 00, 00).toString()) ||
                !ticket.getArriveTime().toString().equals( LocalDateTime.of(2020, 12, 1, 19, 00, 00).toString()) ||
               ticket.getDurationInHours() != 25.0 ||
               ticket.getPrice() != 2090.0 ||
               ticket.getOption() != 1
        ){
            fail("Get ticket by id method fails");
        }

        // test to get a ticketInfo object by name
        if(database.getTicketByName("Jack").size() != 2)
            fail("Get ticket by name method fails");

    }

    /**
     * test to get number of Tickets
    */
    @Test
    public void testGetAllTickets(){
        // Test get initial number of tickets
        if (database.numOfTickets() != 17) {
            fail("There should be 17 tickets. Returned: " + database.numOfTickets());
        }

        // Add more tickets
        database.addTicket("3002", new TicketInfo("3002", "Russel", "Beijing", "Washington", Arrays.asList("Beijing", "Tokyo", "Washington"), LocalDateTime.of(2020, 5, 28, 15, 00, 00), LocalDateTime.of(2020, 5, 29, 11, 00, 00), 20, 1110, 1));

        // Test get new number of tickets
        if (database.numOfTickets() != 18) {
            fail("There should be 18 tickets. Returned: " + database.numOfTickets());
        }

    }

    /**
     * test to clear the database
    */
    @Test
    public void testClearDatabase() {
        // clear database
        database.clearDatabase();
        if (database.numOfTickets() != 0 || database.getNumOfCities() != 0 || database.getNumOfConnections() != 0){
            fail("There should be no element in the database.");
        }
    }
}
