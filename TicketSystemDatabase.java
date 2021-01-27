// --== CS400 File Header Information ==--
// Name: Deming Xu
// Email: dxu227@wisc.edu
// Team: CG
// Role: Back End Developer 1
// TA: Yeping Wang
// Lecturer: Gary Dahl
// Notes to Grader: None

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Ticket System Database class, back end
 */
public class TicketSystemDatabase implements TicketSystemDatabaseInterface {
    private Database database;
    private List<String> names;

    /**
     * Constructor
     */
    public TicketSystemDatabase() {
        database = new Database();
        names = new ArrayList<>();
        importTickets();
    }

    public void importTickets() {
        String filePath = "ticketInfo.csv";
        FileReader fileReader;
        //This os section is from Wieji
        String os = System.getProperty("os.name");
        // If the current operation system is Windows , change the file path for windows
        if (os.toLowerCase().contains("window")) {
            filePath = "src\\ticketInfo.csv";
        } else if (os.toLowerCase().contains("mac"))
            filePath = "src/ticketInfo.csv";

        try {
            fileReader = new FileReader(filePath);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            bufferedReader.readLine();
            String line = bufferedReader.readLine();
            while (line != null && !line.equals("")) {
                // Split the line by comma and store them into a String array
                String[] info = line.split(",");
                String Name = info[0];
                String TicketID = info[1];
                String DepartCity = info[2];
                String ArriveCity = info[3];
                String DeTime = info[4];
                LocalDateTime DepartTime = LocalDateTime.parse(DeTime);
                String ArTime = info[5];
                LocalDateTime ArriveTime = LocalDateTime.parse(ArTime);
                Double DurationInHours = Double.parseDouble(info[6]);
                Double Price = Double.parseDouble(info[7]);
                int option = Integer.parseInt(info[8]);
                addTicketWithoutExport(Name, DepartCity, ArriveCity, DepartTime, option);
                line = bufferedReader.readLine();
            }
            bufferedReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Generate the ticket id, as a String
     *
     * @param name
     * @param departCity
     * @param arriveCity
     * @param departTime
     * @param arriveTime
     * @param time
     * @param option
     * @return
     */
    private String ticketIDGenerator(String name, String departCity, String arriveCity, LocalDateTime departTime, LocalDateTime arriveTime, double time, int option) {
        return name + departCity + arriveCity + departTime + arriveTime + time + option;
    }

    /**
     * Export all the ticket information to ticketInfo.csv
     */
    private void exportTicketInfo() {
        HashTableMap<String, List<TicketInfo>> tickets = database.getAllTickets();
        try {
            String os = System.getProperty("os.name");  // get the current operation system
            String filePath = "ticketInfo.csv";      // File path, linux' file path by default

            // If the current operation system is Windows, change the file path for windows. Same for MacOS
            if (os.toLowerCase().contains("win")) {
                filePath = "src\\ticketInfo.csv";
            } else if (os.toLowerCase().contains("mac")) {
                filePath = "src/ticketInfo.csv";
            }
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filePath));
            // table title
            bufferedWriter.write("Name,TicketID,DepartCity,ArriveCity,DepartTime,ArriveTime,DurationInHours,Price,Option\n");
            for (String name : names) {
                // output every ticket information
                List<TicketInfo> ticketInfoList = tickets.get(name);
                for (TicketInfo ticket : ticketInfoList) {
                    bufferedWriter.append(name).append(",")
                            .append(ticket.getTicketID()).append(",")
                            .append(ticket.getDepartCity()).append(",")
                            .append(ticket.getArriveCity()).append(",")
                            .append(ticket.getDepartTime().toString()).append(",")
                            .append(ticket.getArriveTime().toString()).append(",")
                            .append(String.valueOf(ticket.getDurationInHours())).append(",")
                            .append(String.valueOf(ticket.getPrice())).append(",")
                            .append(String.valueOf(ticket.getOption()))
                            .append("\n");
                }
            }
            bufferedWriter.close();
        } catch (Exception ignore) {

        }
    }

    /**
     * Get the ticket info
     *
     * @param ticketID
     * @return the TicketInfo object
     */
    @Override
    public TicketInfo getTicketInfo(String ticketID) {
        try {
            return database.getTicket(ticketID);
        } catch (Exception ignore) {
            return null;
        }
    }

    /**
     * Get the ticket info by name
     *
     * @param name
     * @return All tickets of a name
     */
    @Override
    public List<TicketInfo> getTicketInfoByName(String name) {
        try {
            return database.getTicketByName(name);
        } catch (Exception ignore) {
            return new ArrayList<>();
        }
    }

    /**
     * Check whether database has the ticket
     *
     * @param ticketID
     * @return true if found, or false otherwise
     */
    @Override
    public boolean hasTicketInfo(String ticketID) {
        try {
            return database.hasTicket(ticketID);
        } catch (Exception ignore) {
            return false;
        }
    }

    /**
     * get num of tickets
     *
     * @return numOfTicket
     */
    @Override
    public int numOfTickets() {
        try {
            return database.numOfTickets();
        } catch (Exception ignore) {
            return 0;
        }
    }

    /**
     * AddTicket method will return ticketID if the ticket is added successfully,
     * it will return null if it fails. Option: 1. Economic travel option 2. Fast travel option
     *
     * @param name
     * @param departCity
     * @param arriveCity
     * @param departTime
     * @param option
     * @return newTicketID
     */
    @Override
    public String addTicket(String name, String departCity, String arriveCity, LocalDateTime departTime, int option) {
        try {
            if(departCity.equals(arriveCity)){
                return null;
            }
            if (option == 1) {  // economic option
                double cost = database.getLowCost(departCity, arriveCity);  // total cost
                List<String> path = database.getLowCostPath(departCity, arriveCity);    // path between cites
                double time = 0;
                for (int i = 0; i < path.size() - 1; i++) {
                    // get the total time
                    time += database.getConnectionTime(path.get(i), path.get(i + 1));
                }
                LocalDateTime arriveTime = departTime.plusHours((long) time);   // calculate the arrive time
                String ticketID = ticketIDGenerator(name, departCity, arriveCity, departTime, arriveTime, time, option);
                TicketInfo ticket = new TicketInfo(ticketID, name, departCity, arriveCity, path, departTime, arriveTime, time, cost, option);
                // add the ticket to database
                if (database.addTicket(ticketID, ticket)) {
                    if (!names.contains(name)) {
                        names.add(name);
                    }
                    exportTicketInfo(); // update the csv file
                    return ticketID;
                } else {
                    return null;
                }
            } else if (option == 2) {   // fatest option
                double time = database.getLowTime(departCity, arriveCity);  // total time
                List<String> path = database.getLowtimePath(departCity, arriveCity);    // path between cites
                double cost = 0;
                for (int i = 0; i < path.size() - 1; i++) {
                    // get the total cost
                    cost += database.getConnectionCost(path.get(i), path.get(i + 1));
                }
                LocalDateTime arriveTime = departTime.plusHours((long) time);   // calculate the arrive time
                String ticketID = ticketIDGenerator(name, departCity, arriveCity, departTime, arriveTime, time, option);
                TicketInfo ticket = new TicketInfo(ticketID, name, departCity, arriveCity, path, departTime, arriveTime, time, cost, option);
                // add the ticket to database
                if (database.addTicket(ticketID, ticket)) {
                    if (!names.contains(name)) {
                        names.add(name);
                    }
                    exportTicketInfo(); // update the csv file
                    return ticketID;
                } else {
                    return null;
                }
            } else {
                throw new IllegalArgumentException("Invalid Option");
            }
        } catch (Exception ignore) {
            ignore.getMessage();
            return null;
        }
    }

    /**
     * AddTicket method will return ticketID if the ticket is added successfully,
     * it will return null if it fails. Option: 1. Economic travel option 2. Fast travel option
     *This method will not export csv file
     *
     * @param name
     * @param departCity
     * @param arriveCity
     * @param departTime
     * @param option
     * @return newTicketID
     */
    public String addTicketWithoutExport(String name, String departCity, String arriveCity, LocalDateTime departTime, int option) {
        try {
            if(departCity.equals(arriveCity)){
                return null;
            }
            if (option == 1) {  // economic option
                double cost = database.getLowCost(departCity, arriveCity);  // total cost
                List<String> path = database.getLowCostPath(departCity, arriveCity);    // path between cites
                double time = 0;
                for (int i = 0; i < path.size() - 1; i++) {
                    // get the total time
                    time += database.getConnectionTime(path.get(i), path.get(i + 1));
                }
                LocalDateTime arriveTime = departTime.plusHours((long) time);   // calculate the arrive time
                String ticketID = ticketIDGenerator(name, departCity, arriveCity, departTime, arriveTime, time, option);
                TicketInfo ticket = new TicketInfo(ticketID, name, departCity, arriveCity, path, departTime, arriveTime, time, cost, option);
                // add the ticket to database
                if (database.addTicket(ticketID, ticket)) {
                    if (!names.contains(name)) {
                        names.add(name);
                    }
                    return ticketID;
                } else {
                    return null;
                }
            } else if (option == 2) {   // fatest option
                double time = database.getLowTime(departCity, arriveCity);  // total time
                List<String> path = database.getLowtimePath(departCity, arriveCity);    // path between cites
                double cost = 0;
                for (int i = 0; i < path.size() - 1; i++) {
                    // get the total cost
                    cost += database.getConnectionCost(path.get(i), path.get(i + 1));
                }
                LocalDateTime arriveTime = departTime.plusHours((long) time);   // calculate the arive time
                String ticketID = ticketIDGenerator(name, departCity, arriveCity, departTime, arriveTime, time, option);
                TicketInfo ticket = new TicketInfo(ticketID, name, departCity, arriveCity, path, departTime, arriveTime, time, cost, option);
                // add the ticket to database
                if (database.addTicket(ticketID, ticket)) {
                    if (!names.contains(name)) {
                        names.add(name);
                    }
                    return ticketID;
                } else {
                    return null;
                }
            } else {
                throw new IllegalArgumentException("Invalid Option");
            }
        } catch (Exception ignore) {
            ignore.getMessage();
            return null;
        }
    }

    /**
     * Delete ticket by ticket id
     *
     * @param ticketID
     * @return true if deleted, false if fails
     */
    @Override
    public boolean deleteTicket(String ticketID) {
        try {
            // ge the user name
            String name = database.getTicket(ticketID).getName();
            if (database.removeTicket(ticketID)) {
                // remove the ticket
                if (database.getTicketByName(name) == null) {
                    names.remove(name);
                }
                exportTicketInfo(); // update the csv file
                return true;
            } else {
                return false;
            }
        } catch (Exception ignore) {
            return false;
        }
    }

    /**
     * update ticket time by ticket id
     *
     * @param ticketID
     * @param time
     * @return newTicketID
     */
    @Override
    public String updateTicketTime(String ticketID, LocalTime time) {
        try {
            TicketInfo ticket = database.getTicket(ticketID);   // get the old ticket
            // update the ticket time
            LocalDateTime oldDateTime = ticket.getArriveTime();
            LocalDateTime newDepartDateTime = LocalDateTime.of(oldDateTime.toLocalDate(), time);
            LocalDateTime newArriveDateTime = newDepartDateTime.plusHours((long) ticket.getDurationInHours());
            // set the new time
            ticket.setDepartTime(newDepartDateTime);
            ticket.setArriveTime(newArriveDateTime);
            // get the new ticket id
            String id = ticketIDGenerator(ticket.getName(), ticket.getDepartCity(), ticket.getArriveCity(), newDepartDateTime, newArriveDateTime, ticket.getDurationInHours(), ticket.getOption());
            // set the new ticket id
            ticket.setTicketID(id);
            database.changeIdInTable(ticketID, id);
            exportTicketInfo(); // update the csv file
            return id;
        } catch (Exception ignore) {
            return null;
        }
    }

    /**
     * update ticket month by ticket id
     *
     * @param ticketID
     * @param month
     * @return newTicketID
     */
    @Override
    public String updateTicketMonth(String ticketID, int month) {
        try {
            TicketInfo ticket = database.getTicket(ticketID);
            // update the ticket date
            LocalDateTime oldDepartDateTime = ticket.getArriveTime();
            LocalDateTime oldArriveDateTime = ticket.getDepartTime();
            LocalDateTime newDepartDateTime = LocalDateTime.of(oldDepartDateTime.getYear(), month, oldDepartDateTime.getDayOfMonth(), oldDepartDateTime.getHour(), oldDepartDateTime.getMinute());
            LocalDateTime newArriveDateTime = LocalDateTime.of(oldArriveDateTime.getYear(), month, oldArriveDateTime.getDayOfMonth(), oldArriveDateTime.getHour(), oldArriveDateTime.getMinute());
            // set the new date
            ticket.setDepartTime(newDepartDateTime);
            ticket.setArriveTime(newArriveDateTime);
            // get the new ticket id
            String id = ticketIDGenerator(ticket.getName(), ticket.getDepartCity(), ticket.getArriveCity(), newDepartDateTime, newArriveDateTime, ticket.getDurationInHours(), ticket.getOption());
            // set the new ticket id
            ticket.setTicketID(id);
            database.changeIdInTable(ticketID, id);
            exportTicketInfo(); // update the csv file
            return id;
        } catch (Exception ignore) {
            return null;
        }
    }

    /**
     * update ticket day by ticket id
     *
     * @param ticketID
     * @param day
     * @return newTicketID
     */
    @Override
    public String updateTicketDay(String ticketID, int day) {
        try {
            TicketInfo ticket = database.getTicket(ticketID);
            // update the ticket day
            LocalDateTime oldDepartDateTime = ticket.getArriveTime();
            LocalDateTime oldArriveDateTime = ticket.getDepartTime();
            LocalDateTime newDepartDateTime = LocalDateTime.of(oldDepartDateTime.getYear(), oldDepartDateTime.getMonth(), day, oldDepartDateTime.getHour(), oldDepartDateTime.getMinute());
            LocalDateTime newArriveDateTime = LocalDateTime.of(oldArriveDateTime.getYear(), oldDepartDateTime.getMonth(), day, oldArriveDateTime.getHour(), oldArriveDateTime.getMinute());
            // set the new day
            ticket.setDepartTime(newDepartDateTime);
            ticket.setArriveTime(newArriveDateTime);
            // get the new ticket id
            String id = ticketIDGenerator(ticket.getName(), ticket.getDepartCity(), ticket.getArriveCity(), newDepartDateTime, newArriveDateTime, ticket.getDurationInHours(), ticket.getOption());
            // set the new ticket id
            ticket.setTicketID(id);
            database.changeIdInTable(ticketID, id);
            exportTicketInfo(); // update the csv file
            return id;
        } catch (Exception ignore) {
            System.out.println(ignore.getMessage());
            return null;
        }
    }

    /**
     * update ticket by ticket id, the program will recalculate the routine based on the updated option
     *
     * @param ticketID
     * @param option
     * @return newTicketID
     */
    @Override
    public String updateTicketOption(String ticketID, int option) {
        try {
            // get the old ticket
            TicketInfo ticket = database.getTicket(ticketID);
            // remove the ticket and add again
            if (database.removeTicket(ticketID)) {
                String id = addTicket(ticket.getName(), ticket.getDepartCity(), ticket.getArriveCity(), ticket.getDepartTime(), option);
                if (id != null) {
                    exportTicketInfo();
                    return id;
                } else {
                    return null;
                }
            } else {
                return null;
            }
        } catch (Exception ignore) {
            return null;
        }
    }

    /**
     * update ticket by ticket id, the program will recalculate the routine based on the updated depart city and option
     *
     * @param ticketID
     * @param departCity
     * @param option
     * @return newTicketID
     */
    @Override
    public String updateTicketDepartCity(String ticketID, String departCity, int option) {
        try {
            // get the old ticket
            TicketInfo ticket = database.getTicket(ticketID);
            // remove the ticket and add again
            if (database.removeTicket(ticketID)) {
                String id = addTicket(ticket.getName(), departCity, ticket.getArriveCity(), ticket.getDepartTime(), option);
                if (id != null) {
                    exportTicketInfo();
                    return id;
                } else {
                    return null;
                }
            } else {
                return null;
            }
        } catch (Exception ignore) {
            return null;
        }
    }

    /**
     * update ticket by ticket id, the program will recalculate the routine based on the updated arrive city and option
     *
     * @param ticketID
     * @param arriveCity
     * @param option
     * @return newTicketID
     */
    @Override
    public String updateTicketArriveCity(String ticketID, String arriveCity, int option) {
        try {
            // get the old ticket
            TicketInfo ticket = database.getTicket(ticketID);
            // remove the ticket and add again
            if (database.removeTicket(ticketID)) {
                String id = addTicket(ticket.getName(), ticket.getDepartCity(), arriveCity, ticket.getDepartTime(), option);
                if (id != null) {
                    exportTicketInfo();
                    return id;
                } else {
                    return null;
                }
            } else {
                return null;
            }
        } catch (Exception ignore) {
            return null;
        }
    }

    /**
     * add city to graphs
     *
     * @param city
     * @return true if added, false otherwise
     */
    @Override
    public boolean addCity(String city) {
        try {
            return database.addCity(city);
        } catch (Exception ignore) {
            return false;
        }
    }

    /**
     * delete city from graphs
     *
     * @param city
     * @return true if deleted, false otherwise
     */
    @Override
    public boolean deleteCity(String city) {
        try {
            return database.deleteCity(city);
        } catch (Exception ignore) {
            return false;
        }
    }

    /**
     * add connections to graphs
     *
     * @param city1
     * @param city2
     * @param price
     * @param time
     * @return true if added, false otherwise
     */
    @Override
    public boolean addConnection(String city1, String city2, double price, double time) {
        try {
            return database.addConnection(city1, city2, price, time);
        } catch (Exception ignore) {
            return false;
        }
    }

    /**
     * delete connections from graphs
     *
     * @param city1
     * @param city2
     * @return true if deleted, false otherwise
     */
    @Override
    public boolean deleteConnection(String city1, String city2) {
        try {
            return database.deleteConnection(city1, city2);
        } catch (Exception ignore) {
            return false;
        }
    }

    /**
     * get price cost between two nearby cities
     *
     * @param city1
     * @param city2
     * @return the price
     */
    @Override
    public double getConnectionCost(String city1, String city2) {
        try {
            return database.getConnectionCost(city1, city2);
        } catch (Exception ignore) {
            return 0;
        }
    }

    /**
     * get time between two nearby cities
     *
     * @param city1
     * @param city2
     * @return time
     */
    @Override
    public double getConnectionTime(String city1, String city2) {
        try {
            return database.getConnectionTime(city1, city2);
        } catch (Exception ignore) {
            return 0;
        }
    }

    /**
     * check is city in the database
     *
     * @param city
     * @return true if contained, false otherwise
     */
    @Override
    public boolean checkCityName(String city) {
        try {
            return database.checkCityName(city);
        } catch (Exception ignore) {
            return false;
        }
    }

    /**
     * check is connection in the database
     *
     * @param dep
     * @param arr
     * @return true if contained, false otherwise
     */
    @Override
    public boolean checkCitiesConnected(String dep, String arr) {
        try {
            return database.checkCitiesConnected(dep, arr);
        } catch (Exception ignore) {
            return false;
        }
    }

    /**
     * change connection time
     *
     * @param city1
     * @param city2
     * @param time
     * @return true if updated, false otherwise
     */
    @Override
    public boolean updateConnectionTime(String city1, String city2, double time) {
        try {
            return database.updateConnectionTime(city1, city2, time);
        } catch (Exception ignore) {
            return false;
        }
    }

    /**
     * change connection price
     *
     * @param city1
     * @param city2
     * @param price
     * @return true if updated, false otherwise
     */
    @Override
    public boolean updateConnectionPrice(String city1, String city2, double price) {
        try {
            return database.updateConnectionPrice(city1, city2, price);
        } catch (Exception ignore) {
            return false;
        }
    }

    /**
     * get the least cost of option 1 path
     *
     * @param city1
     * @param city2
     * @return total cost of a path with option 1 chosen
     */
    public double getLowCost(String city1, String city2) {
        try {
            return database.getLowCost(city1, city2);
        } catch (Exception ignore) {
            return 0;
        }
    }

    /**
     * get the least time of option 2 path
     *
     * @param city1
     * @param city2
     * @return total time of a path with option 2 chosen
     */
    public double getLowTime(String city1, String city2) {
        try {
            return database.getLowTime(city1, city2);
        } catch (Exception ignore) {
            return 0;
        }
    }

    /**
     * get the path with least cost
     *
     * @param city1
     * @param city2
     * @return path with lowest cost
     */
    @Override
    public List<String> getLowCostPath(String city1, String city2) {
        try {
            return database.getLowCostPath(city1, city2);
        } catch (Exception ignore) {
            return new ArrayList<>();
        }
    }

    /**
     * get the path with least time
     *
     * @param city1
     * @param city2
     * @return path with lowest time
     */
    @Override
    public List<String> getLowTimePath(String city1, String city2) {
        try {
            return database.getLowtimePath(city1, city2);
        } catch (Exception ignore) {
            return new ArrayList<>();
        }
    }

    /**
     * number of city
     *
     * @return numOfCity
     */
    @Override
    public int numOfCities() {
        try {
            return database.getNumOfCities();
        } catch (Exception ignore) {
            return 0;
        }
    }

    /**
     * number of connections between cities
     *
     * @return numOfConnections
     */
    @Override
    public int numOfConnections() {
        try {
            return database.getNumOfConnections();
        } catch (Exception ignore) {
            return 0;
        }
    }
}
