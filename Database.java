// --== CS400 File Header Information ==--
// Name: Dawson Bauer
// Email: djbauer2@wisc.edu
// Team: CG
// TA: Yeping Wang
// Lecturer: Florian Heimerl

import javax.xml.crypto.Data;
import java.time.LocalDateTime;
import java.util.*;
import java.io.BufferedReader;
import java.io.FileReader;


public class Database {
    private CS400Graph<String> timeGraph;
    private CS400Graph<String> costGraph;
    private HashTableMap<String, List<TicketInfo>> userTable;
    private HashTableMap<String, String> ticketIdTable;

    public Database() {
        costGraph = new CS400Graph<>();
        timeGraph = new CS400Graph<>();
        userTable = new HashTableMap<>();
        ticketIdTable = new HashTableMap<>();
        importData();
    }

    /**
     * Reads and imports the data from the csv file.
     */
    public void importData() {
        String filePath = "trainInfo.csv";
        FileReader fileReader;
        //This os section is from Wieji
        String os = System.getProperty("os.name");
        // If the current operation system is Windows , change the file path for windows
        if (os.toLowerCase().contains("window")) {
            filePath = "src\\trainInfo.csv";
        } else if (os.toLowerCase().contains("mac"))
            filePath = "src/trainInfo.csv";

        try {
            fileReader = new FileReader(filePath);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            bufferedReader.readLine();
            String line = bufferedReader.readLine();
            while (line != null && !line.equals("")) {
                // Split the line by comma and store them into a String array
                String[] info = line.split(",");
                String cityA = info[0];
                String cityB = info[1];
                double price = Double.parseDouble(info[2]);
                double time = Double.parseDouble(info[3]);

                costGraph.insertVertex(cityA);
                costGraph.insertVertex(cityB);
                costGraph.insertEdge(cityA, cityB, price);
                costGraph.insertEdge(cityB, cityA, price);

                timeGraph.insertVertex(cityA);
                timeGraph.insertVertex(cityB);
                timeGraph.insertEdge(cityA, cityB, time);
                timeGraph.insertEdge(cityB, cityA, time);
                line = bufferedReader.readLine();
            }
            bufferedReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Reads and imports the data from the csv file.
     *
     * @param city - string of the city name
     */
    public boolean addCity(String city) {
        if (costGraph.containsVertex(city)) {
            return false;
        } else {
            costGraph.insertVertex(city);
            timeGraph.insertVertex(city);
            return true;
        }
    }

    /**
     * Reads and imports the data from the csv file.
     *
     * @param city - string of the city name
     */
    public boolean deleteCity(String city) {
        if (!costGraph.containsVertex(city)) {
            return false;
        } else {
            costGraph.removeVertex(city);
            timeGraph.removeVertex(city);
            return true;
        }
    }

    /**
     * Checks if the name of the city is in the graph.
     *
     * @param city - string of the city name
     */
    public boolean containsCity(String city) {
        if (costGraph.containsVertex(city)) {
            return true;
        }
        return false;
    }

    /**
     * Finds the lowest time between 2 cities
     *
     * @param city1 - string of the dep city name
     * @param city2 - string of the arriving city name
     * @param price - the cost between the cities
     * @param time  - length in between cities
     */
    public boolean addConnection(String city1, String city2, double price, double time) {
        try {
            return costGraph.insertEdge(city1, city2, price) &&
                    costGraph.insertEdge(city2, city1, price) && timeGraph.insertEdge(city2, city1, time)
                    && timeGraph.insertEdge(city1, city2, time);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Deletes the connections between cities
     *
     * @param city1 - string of the dep city name
     * @param city2 - string of the arriving city name
     */
    public boolean deleteConnection(String city1, String city2) {
        try {
            return costGraph.removeEdge(city1, city2) && costGraph.removeEdge(city2, city1)
                    && timeGraph.removeEdge(city1, city2) && timeGraph.removeEdge(city2, city1);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Checks the connections between cities
     *
     * @param city1 - string of the dep city name
     * @param city2 - string of the arriving city name
     */
    public boolean containsConnection(String city1, String city2) {
        try {
            return costGraph.containsEdge(city1, city2) && timeGraph.containsEdge(city1, city2);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Finds the price of the connection
     *
     * @param city1 - string of the dep city name
     * @param city2 - string of the arriving city name
     */
    public double getConnectionCost(String city1, String city2) {
        return costGraph.getWeight(city1, city2);
    }

    /**
     * Finds the time between the cities
     *
     * @param city1 - string of the dep city name
     * @param city2 - string of the arriving city name
     */
    public double getConnectionTime(String city1, String city2) {
        return timeGraph.getWeight(city1, city2);
    }

    /**
     * Updates the time inbetween the cities
     *
     * @param city1 - string of the dep city name
     * @param city2 - string of the arriving city name
     * @param time  - the new time inbetween 2 cities
     */
    public boolean updateConnectionTime(String city1, String city2, double time) {
        try {
            timeGraph.removeEdge(city1, city2);
            return timeGraph.insertEdge(city1, city2, time);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Updates the cost inbetween the cities
     *
     * @param city1 - string of the dep city name
     * @param city2 - string of the arriving city name
     * @param price - the new cost inbetween 2 cities
     */
    public boolean updateConnectionPrice(String city1, String city2, double price) {
        try {
            costGraph.removeEdge(city1, city2);
            return costGraph.insertEdge(city1, city2, price);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Finds the lowest cost between 2 cities
     *
     * @param dep - string of the dep city name
     * @param arr - string of the arriving city name
     */
    public double getLowCost(String dep, String arr) {
        return costGraph.getPathCost(dep, arr);
    }

    /**
     * Finds the lowest time between 2 cities
     *
     * @param dep - string of the dep city name
     * @param arr - string of the arriving city name
     */
    public double getLowTime(String dep, String arr) {
        return timeGraph.getPathCost(dep, arr);
    }

    /**
     * Returns the number of the cities in the graph
     */
    public int getNumOfCities() {
        return costGraph.getVertexCount();
    }

    /**
     * Returns the number of edges in the graph
     */
    public int getNumOfConnections() {
        return costGraph.getEdgeCount();
    }

    public boolean addTicket(String ticketID, TicketInfo ticket) {
        try {
            List<TicketInfo> tickets = userTable.get(ticket.getName());
            if (!tickets.contains(ticket)) {
                tickets.add(ticket);
            }
            return ticketIdTable.put(ticketID, ticket.getName());
        } catch (Exception e) {
            List<TicketInfo> ts = new ArrayList<>();
            ts.add(ticket);
            if (userTable.put(ticket.getName(), ts)) {
                return ticketIdTable.put(ticketID, ticket.getName());
            } else {
                return false;
            }
        }
    }

    public boolean removeTicket(String ticketID) {
        if (hasTicket(ticketID)) {
            try {
                TicketInfo ticket = getTicket(ticketID);
                List<TicketInfo> tickets = userTable.get(ticket.getName());
                tickets.remove(ticket);
                if (tickets.size() == 0) {
                    userTable.remove(ticket.getName());
                }
                return ticketIdTable.remove(ticketID) != null;
            } catch (Exception e) {
                return false;
            }
        } else {
            return false;
        }
    }

    public boolean hasTicket(String ticketID) {
        try {
            TicketInfo ticket = getTicket(ticketID);
            if (ticket == null) {
                return false;
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean updateTicket(String ticketID, TicketInfo ticket) {
        try {
            TicketInfo oldTicket = getTicket(ticketID);
            if (oldTicket == null) {
                return false;
            }
            oldTicket.setTicketID(ticket.getTicketID());
            oldTicket.setArriveTime(ticket.getArriveTime());
            oldTicket.setDepartTime(ticket.getDepartTime());
            oldTicket.setArriveCity(ticket.getArriveCity());
            oldTicket.setDepartCity(ticket.getDepartCity());
            oldTicket.setName(ticket.getName());
            oldTicket.setDurationInHours(ticket.getDurationInHours());
            oldTicket.setOption(ticket.getOption());
            oldTicket.setPrice(ticket.getPrice());
            oldTicket.setTravelPath(ticket.getTravelPath());
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public TicketInfo getTicket(String ticketID) {
        String name = ticketIdTable.get(ticketID);
        List<TicketInfo> tickets = userTable.get(name);
        for (TicketInfo ticket : tickets) {
            if (ticket.getTicketID().equals(ticketID)) {
                return ticket;
            }
        }
        return null;
    }

    public void changeIdInTable(String oldID, String newId){
        String name = ticketIdTable.get(oldID);
        ticketIdTable.remove(oldID);
        ticketIdTable.put(newId, name);
    }


    public List<TicketInfo> getTicketByName(String name) {
        try {
            return userTable.get(name);
        }catch (Exception e){
            return null;
        }
    }

    public HashTableMap<String, List<TicketInfo>> getAllTickets() {
        return userTable;
    }

    int numOfTickets() {
        return ticketIdTable.size();
    }

    /**
     * Clears the entire database
     */
    public void clearDatabase() {
        costGraph = new CS400Graph<>();
        timeGraph = new CS400Graph<>();
        userTable = new HashTableMap<>();
        ticketIdTable = new HashTableMap<>();
    }

    public List<String> getLowCostPath(String departCity, String arriveCity) {
        return costGraph.shortestPath(departCity, arriveCity);
    }

    public List<String> getLowtimePath(String departCity, String arriveCity) {
        return timeGraph.shortestPath(departCity, arriveCity);
    }

    public Boolean checkCityName(String city) {
        return costGraph.containsVertex(city);
    }

    public Boolean checkCitiesConnected(String dep, String arr) {
        try{
        	List<String> path = costGraph.shortestPath(dep, arr);
        	return true;
		}catch (Exception e){
        	return false;
		}
    }
}
