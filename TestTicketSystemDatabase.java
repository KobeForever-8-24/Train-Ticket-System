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
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.fail;

public class TestTicketSystemDatabase {
    // test instance
    private TicketSystemDatabase ticketDatabase;

    @BeforeEach
    public void setUp(){
        // Initialize the database
        ticketDatabase = new TicketSystemDatabase();

        // Add cities into database
        ticketDatabase.addCity("Beijing");
        ticketDatabase.addCity("Tokyo");
        ticketDatabase.addCity("Washington");
        ticketDatabase.addCity("Madison");
        ticketDatabase.addCity("Harrisburg");

        // Add connections between cities into database
        ticketDatabase.addConnection("Beijing", "Tokyo", 380, 5);
        ticketDatabase.addConnection("Washington", "Tokyo", 730, 15);
        ticketDatabase.addConnection("Madison", "Harrisburg", 310, 7.5);
        ticketDatabase.addConnection("Madison", "Washington", 200, 7.5);
        ticketDatabase.addConnection("Washington", "Harrisburg", 200, 5);
        ticketDatabase.addConnection("Beijing", "Madison", 2400, 15);
        ticketDatabase.addConnection("Tokyo", "Madison", 900, 20);
        ticketDatabase.addConnection("Beijing", "Washington", 1820, 15);
        ticketDatabase.addConnection("Tokyo", "Harrisburg", 1400, 13.5);

        // Add Tickets into database
        ticketDatabase.addTicket("Bob", "Beijing", "Madison", LocalDateTime.of(2020, 1, 3, 10, 00, 00), 1);
        ticketDatabase.addTicket( "Kevin", "Madison", "Harrisburg",  LocalDateTime.of(2020, 2, 6, 8, 00, 00),  2);
        ticketDatabase.addTicket( "Rosa", "Madison", "Harrisburg", LocalDateTime.of(2020, 3, 9, 18, 00, 00),  2);
        ticketDatabase.addTicket("James", "Beijing", "Washington", LocalDateTime.of(2020, 4, 12, 15, 00, 00), 1);
        ticketDatabase.addTicket("Harden", "Beijing", "Madison", LocalDateTime.of(2020, 5, 11, 15, 00, 00),  1);
        ticketDatabase.addTicket("Jack", "Washington", "Beijing", LocalDateTime.of(2020, 6, 1, 15, 00, 00), 2);
        ticketDatabase.addTicket("John", "Madison", "Tokyo", LocalDateTime.of(2020, 7, 18, 8, 00, 00),1);
        ticketDatabase.addTicket( "Amy", "Harrisburg", "Madison", LocalDateTime.of(2020, 8, 22, 15, 00, 00), 1);
        ticketDatabase.addTicket("Lisa", "Tokyo", "Harrisburg", LocalDateTime.of(2020, 9, 20, 10, 00, 00), 2);
        ticketDatabase.addTicket("Rose", "Tokyo", "Harrisburg", LocalDateTime.of(2020, 10, 10, 15, 00, 00), 1);
        ticketDatabase.addTicket("Blink", "Madison", "Tokyo", LocalDateTime.of(2020, 11, 19, 18, 00, 00), 1);
        ticketDatabase.addTicket("Romeo", "Beijing", "Washington", LocalDateTime.of(2020, 12, 21, 10, 00, 00), 1);
        ticketDatabase.addTicket( "Jason", "Beijing", "Washington", LocalDateTime.of(2020, 1, 23, 10, 00, 00), 2);
        ticketDatabase.addTicket("Michael", "Washington", "Harrisburg", LocalDateTime.of(2020, 2, 27, 8, 00, 00), 1);
        ticketDatabase.addTicket("Jack", "Washington", "Tokyo", LocalDateTime.of(2020, 5, 28, 15, 00, 00), 1);
        ticketDatabase.addTicket( "Durant", "Beijing", "Madison", LocalDateTime.of(2020, 4, 14, 18, 00, 00),2);
    }

    /**
     * test add, delete, contains ticket method
     */
    @Test
    public void testAddAndRemoveAndContainsTicket(){

        //System.out.println(ticketDatabase.addTicket("Juliet", "Beijing", "Madison", LocalDateTime.of(2020, 11, 30, 18, 00, 00), 1).equals("JulietBeijingMadison2020-11-30T18:002020-12-01T19:0025.01"));
        // Test add ticket
        if(!ticketDatabase.addTicket("Juliet", "Beijing", "Madison", LocalDateTime.of(2020, 11, 30, 18, 00, 00), 1).equals("JulietBeijingMadison2020-11-30T18:002020-12-01T19:0025.01"))
            fail("Did not successfully add ticket");

        // Test check if contains ticket
        if(!ticketDatabase.hasTicketInfo("JulietBeijingMadison2020-11-30T18:002020-12-01T19:0025.01"))
            fail("contains method fails");

        // Test delete ticket

        if(!ticketDatabase.deleteTicket("JulietBeijingMadison2020-11-30T18:002020-12-01T19:0025.01") || ticketDatabase.hasTicketInfo("JulietBeijingMadison2020-11-30T18:002020-12-01T19:0025.01"))
            fail("Did not successfully delete ticket");

    }


    /**
     * test the ability to get ticket by ticket id and name
     */
    @Test
    public void testGetTicket(){
        // test to get a ticketInfo object by ticket id
        //System.out.println(ticketDatabase.ticketIDGenerator("Juliet", "Beijing", "Madison", LocalDateTime.of(2020, 11, 30, 18, 00, 00), LocalDateTime.of(2020, 12, 1, 20, 00, 00), 26.0, 1));
        String ticketID = ticketDatabase.addTicket( "Jake", "Madison", "Harrisburg",  LocalDateTime.of(2020, 2, 6, 8, 00, 00),  2);
        TicketInfo ticket = ticketDatabase.getTicketInfo(ticketID);

        System.out.println(ticket);
        // Check the ticketInfo object information
        if (!ticket.getName().equals("Jake") ||
                !ticket.getDepartCity().equals("Madison") ||
                !ticket.getArriveCity().equals("Harrisburg") ||
                !ticket.getTravelPath().toString().equals("[Madison, Harrisburg]") ||
                !ticket.getDepartTime().toString().equals(LocalDateTime.of(2020, 2, 6, 8, 00, 00).toString()) ||
                !ticket.getArriveTime().toString().equals(LocalDateTime.of(2020, 2, 6, 15, 00, 00).toString()) ||
                ticket.getDurationInHours() != 7.5 ||
                ticket.getPrice() != 310 ||
                ticket.getOption() != 2
        ){
            fail("Get ticket by id method fails" + ticket.getTravelPath() + ticket.getArriveTime() + ticket.getDurationInHours() + ticket.getPrice());
        }

        // test to get a ticketInfo object by name
        List<TicketInfo> names = ticketDatabase.getTicketInfoByName("Durant");
        if(ticketDatabase.getTicketInfoByName("Durant").size() != 1)
            fail("Get ticket by name method fails");

    }

    /**
     * test to update properties(Time, Month, Day, Option, Depart City, Arrive City) of ticket by ticket id
     * */
    @Test
    public void testUpdateTickets(){

        String id = ticketDatabase.addTicket("Cindy", "Harrisburg", "Tokyo", LocalDateTime.of(2020, 8, 1, 15, 00, 00), 2);
        TicketInfo ticket = ticketDatabase.getTicketInfo(id);

        // update ticket time by ticket id
        String newId = ticketDatabase.updateTicketTime(id, LocalTime.of(10, 0, 0));
        if(ticketDatabase.getTicketInfo(newId).getDepartTime().getHour() != 10)
            fail("update ticket time fails");

        // update ticket month by ticket id
        newId = ticketDatabase.updateTicketMonth(newId, 11);
        if(ticketDatabase.getTicketInfo(newId).getDepartTime().getMonth().getValue() != 11)
            fail("update ticket month fails");

        // update ticket day by ticket id
        newId = ticketDatabase.updateTicketDay(newId, 22);
        if(ticketDatabase.getTicketInfo(newId).getDepartTime().getDayOfMonth() != 22)
            fail("update ticket day fails");

        // update ticket option by ticket id
        newId = ticketDatabase.updateTicketOption(newId, 2);
        if(ticketDatabase.getTicketInfo(newId).getOption() != 2)
            fail("update ticket option fails");

        // update ticket arrive city by ticket id
        newId = ticketDatabase.updateTicketArriveCity(newId, "Madison", 1);
        if(!ticketDatabase.getTicketInfo(newId).getArriveCity().equals("Madison"))
            fail("update ticket arrive city fails");

        // update ticket depart city by ticket id
        newId = ticketDatabase.updateTicketDepartCity(newId, "Beijing", 1);
        if(!ticketDatabase.getTicketInfo(newId).getDepartCity().equals("Beijing"))
            fail("update ticket depart city fails");
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
        ticketDatabase.addCity(city1);
        ticketDatabase.addCity(city2);

        // Test add connection
        if(!ticketDatabase.addConnection(city1, city2, 255, 6.5) ||
                ticketDatabase.getConnectionCost(city1, city2) != 255 || ticketDatabase.getConnectionTime(city1, city2) != 6.5 )
            fail("Did not successfully add connection");

        // Test delete connection
        if(!ticketDatabase.deleteConnection(city1, city2))
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
        ticketDatabase.addCity(city1);
        ticketDatabase.addCity(city2);

        ticketDatabase.addConnection(city1, city2, 255, 6.5);

        // Invalid input
        if (ticketDatabase.updateConnectionTime("NotCity", "NotAnotherCity", 7.5)) {
            fail("Should not have been able to update connection");
        }

        if(!ticketDatabase.updateConnectionTime(city1, city2, 7.5) || ticketDatabase.getConnectionTime(city1, city2) != 7.5)
            fail("Did not successfully update connection time");

        double price = ticketDatabase.getConnectionCost(city1, city2);
        ticketDatabase.updateConnectionPrice(city1, city2, 300);
        price = ticketDatabase.getConnectionCost(city1, city2);
        if(!ticketDatabase.updateConnectionPrice(city1, city2, 300) || ticketDatabase.getConnectionCost(city1, city2) != 300)
            fail("Did not successfully update connection price");
    }

    /**
     * test get connection cost method and get connection time method
     */
    @Test
    public void testGetConnection(){
        // Test connection price between cities
        if(ticketDatabase.getConnectionCost("Beijing", "Madison") != 2400)
            fail("The get connection price method is not correct!");
        // Test connection time between cities
        if(ticketDatabase.getConnectionTime("Beijing", "Madison") != 15
                || ticketDatabase.getConnectionTime("Beijing", "Tokyo") != 5
                || ticketDatabase.getConnectionTime("Madison", "Harrisburg") != 7.5)
            fail("The get connection price method is not correct!");
    }

    /**
     * test check city name method and check city connected method
     */
    @Test
    public void testCheckCity(){
        // Test check city name method
        if(!ticketDatabase.checkCityName("Beijing") || !ticketDatabase.checkCityName("Tokyo")
                || !ticketDatabase.checkCityName("Washington") || !ticketDatabase.checkCityName("Harrisburg")
                || !ticketDatabase.checkCityName("Madison"))
            fail("Check city name method fail!");

        // Test check city connected
        if(!ticketDatabase.checkCitiesConnected("Beijing", "Tokyo") || !ticketDatabase.checkCitiesConnected("Tokyo", "Washington")
                || !ticketDatabase.checkCitiesConnected("Madison", "Harrisburg") || !ticketDatabase.checkCitiesConnected("Madison", "Washington")
                || !ticketDatabase.checkCitiesConnected("Madison", "Tokyo") || !ticketDatabase.checkCitiesConnected("Washington", "Harrisburg")
                || !ticketDatabase.checkCitiesConnected("Beijing", "Madison") || !ticketDatabase.checkCitiesConnected("Tokyo", "Madison"))
            fail("Check connection between cities method fail!");
    }

    /**
     * test get low cost path and get low time path
     */
    @Test
    public void testGetLowPath(){
        List<String> path = ticketDatabase.getLowCostPath("Harrisburg", "Tokyo");
        // Test get low cost path
        assertTrue(ticketDatabase.getLowCostPath("Madison", "Beijing").toString().equals(
                "[Madison, Tokyo, Beijing]"
        ));

        assertTrue(ticketDatabase.getLowCostPath("Harrisburg", "Tokyo").toString().equals(
                "[Harrisburg, Washington, Tokyo]"
        ));
        // Test get low time path
        assertTrue(ticketDatabase.getLowTimePath("Madison", "Beijing").toString().equals(
                "[Madison, Beijing]"
        ));

        assertTrue(ticketDatabase.getLowTimePath("Harrisburg", "Tokyo").toString().equals(
                "[Harrisburg, Tokyo]"
        ));
    }

    /**
     * test get number of cities and get number of connections
     */
    @Test
    public void testGetNumbers(){
        // Test get initial number of cities
        if (ticketDatabase.numOfCities() != 472) {
            fail("There should be 472 cities. Returned: " + ticketDatabase.numOfCities());
        }

        // Test get initial number of connections
        if(ticketDatabase.numOfConnections() != 4774){
            fail("There should be 4774 connections. Returned: " + ticketDatabase.numOfConnections());
        }

        // Add more cities
        String city1 = "Lancaster";
        String city2 = "Salt Lake City";
        // Add cities into the database
        ticketDatabase.addCity(city1);
        ticketDatabase.addCity(city2);

        ticketDatabase.addConnection(city1, city2, 255, 6.5);

        // Test get new number of cities
        if (ticketDatabase.numOfCities() != 473) {
            fail("There should be 473 cities. Returned: " + ticketDatabase.numOfCities());
        }

        // Test get new number of connections
        if(ticketDatabase.numOfConnections() != 4776){
            fail("There should be 4776 connections. Returned: " + ticketDatabase.numOfConnections());
        }
    }

}
