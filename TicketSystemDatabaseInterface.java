// --== CS400 File Header Information ==--
// Name: Deming Xu
// Email: dxu227@wisc.edu
// Team: CG
// Role: Back End Developer 1
// TA: Yeping Wang
// Lecturer: Gary Dahl
// Notes to Grader: None

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

/**
 * Ticket System Database Interface, back end
 */
public interface TicketSystemDatabaseInterface {
    // Get the ticket info
    public TicketInfo getTicketInfo(String ticketID);

    // Get the ticket info by name
    public List<TicketInfo> getTicketInfoByName(String name);

    // Check whether database has the ticket
    public boolean hasTicketInfo(String ticketID);

    // num of tickets
    public int numOfTickets();

    // AddTicket method will return ticketID if the ticket is added successfully, it will return null if it fails. Option: 1. Economic travel option 2. Fast travel option
    public String addTicket(String name, String departCity, String arriveCity, LocalDateTime departTime, int option);

    // Delete ticket by ticket id
    public boolean deleteTicket(String ticketID);

    // update ticket time by ticket id
    public String updateTicketTime(String ticketID, LocalTime time);

    // update ticket month by ticket id
    public String updateTicketMonth(String ticketID, int month);

    // update ticket day by ticket id
    public String updateTicketDay(String ticketID, int day);

    // update ticket by ticket id, the program will recalculate the routine based on the updated option
    public String updateTicketOption(String ticketID, int option);

    // update ticket by ticket id, the program will recalculate the routine based on the updated depart city and option
    public String updateTicketDepartCity(String ticketID, String departCity, int option);

    // update ticket by ticket id, the program will recalculate the routine based on the updated arrive city and option
    public String updateTicketArriveCity(String ticketID, String arriveCity, int option);

    // add city to graphs
    public boolean addCity(String city);

    // delete city from graphs
    public boolean deleteCity(String city);

    // add connections to graphs
    public boolean addConnection(String city1, String city2, double price, double time);

    // delete connections from graphs
    public boolean deleteConnection(String city1, String city2);

    // get price cost between two cities
    public double getConnectionCost(String city1, String city2);

    // get time between two cities
    public double getConnectionTime(String city1, String city2);

    // check is city in the database
    public boolean checkCityName(String city);

    // check is connection in the database
    public boolean checkCitiesConnected(String dep, String arr);

    // change connection time
    public boolean updateConnectionTime(String city1, String city2, double time);

    // change connection price
    public boolean updateConnectionPrice(String city1, String city2, double price);

    // get the least cost of option 1 path
    public double getLowCost(String city1, String city2);

    // get the least time of option 2 path
    public double getLowTime(String city1, String city2);

    // get the path with least cost
    public List<String> getLowCostPath(String city1, String city2);

    // get the path with least time
    public List<String> getLowTimePath(String city1, String city2);

    // number of city
    public int numOfCities();

    // number of connections between cities
    public int numOfConnections();
}
