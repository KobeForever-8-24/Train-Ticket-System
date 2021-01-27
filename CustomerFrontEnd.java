// --== CS400 File Header Information ==--
// Name: Isaac Colbert
// Email: icolbert@wisc.edu
// Team: CG
// Role: Front End Developer 1
// TA: Yeping Wang
// Lecturer: Florian Heimerl
// Notes to Grader: This file utilizes the LogInAndRegister method which was originally written
// by Weijie Zhou, and was originally created for team CG's Project 1.

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Scanner;

/**
 * Code that runs the front end interface for a customer of the ticket system.
 * <p>
 *
 * @author Isaac Colbert
 */
public class CustomerFrontEnd {
  static TicketSystemDatabase database = new TicketSystemDatabase();
  static LogInAndRegister logInAndRegister = new LogInAndRegister();

  /**
   * Main method that runs until exited.
   * <p>
   *
   * @param args - unused
   */
  public static void main(String[] args) {
    Scanner scnr = new Scanner(System.in);
    while (true) {
      String input = "";
      System.out.println("---------------Welcome to the Train Ticket System!---------------");
      System.out.println("Please select an option below:");
      System.out.println("1. Register");
      System.out.println("2. Login");
      System.out.println("3. Exit");
      System.out.println("-----------------------------------------------------------------");
      do {
        System.out.print("Enter selection (1/2/3): ");
        input = scnr.nextLine();
      } while (!input.equals("1") && !input.equals("2") && !input.equals("3"));
      if (input.equals("3")) {
        break;
      }
      String user = logInAndRegister(logInAndRegister, input);
      if (user == null) {
        continue;
      }
      System.out.println("Hello " + user + "!");
      String option = "";
      if (logInAndRegister.isAdmin(user)) {
        while (true) {
          System.out.println("----------------------Train Ticket System-----------------------");
          System.out.println("Available Options: ");
          System.out.println("1. Purchase Ticket");
          System.out.println("2. View Ticket Information");
          System.out.println("3. Update Ticket Information");
          System.out.println("4. Update City Information");
          System.out.println("5. Logout");
          System.out.println("----------------------------------------------------------------");
          do {
            System.out.print("Please select an option (1-5): ");
            option = scnr.nextLine();
          } while (!option.equals("1") && !option.equals("2") && !option.equals("3")
              && !option.equals("4") && !option.equals("5"));
          if (option.equals("5")) {
            System.out.println("Thank you for using the Train Ticket System!");
            System.out.println("----------------------------------------------------------------");
            break;
          }
          // Fill in Purchase Ticket Option here
          if (option.equals("1")) {
            purchaseTicket();
            System.out.println("----------------------------------------------------------------");
          }
          // Get Ticket Info Option
          if (option.equals("2")) {
            String method = "";
            System.out.println("1. Search by Ticket ID");
            System.out.println("2. Search by passenger name");
            do {
              System.out.print("Select Search Method (1-2): ");
              method = scnr.nextLine();
            } while (!method.equals("1") && !method.equals("2"));
            if (method.equals("1")) {
              String id = "";
              System.out.print("Enter Ticket ID: ");
              id = scnr.nextLine();
              getTicketInfoByID(id);
              if (option.equals("1")) {
                purchaseTicket();
                System.out
                    .println("----------------------------------------------------------------");
              }
            } else {
              String name = "";
              System.out.print("Enter Passenger Name: ");
              name = scnr.nextLine();
              getTicketInfoByName(name);
              if (option.equals("1")) {
                purchaseTicket();
                System.out
                    .println("----------------------------------------------------------------");
              }
            }
          }
          // Update Ticket Info Option
          if (option.equals("3")) {
            updateTicketInfo();
            System.out.println("----------------------------------------------------------------");
          }
          // Update City Information
          if (option.equals("4")) {
            updateCityInformation();
            System.out.println("----------------------------------------------------------------");
          }
        }
      } else {
        while (true) {
          System.out.println("----------------------Train Ticket System-----------------------");
          System.out.println("Available Options: ");
          System.out.println("1. Purchase Ticket");
          System.out.println("2. View Ticket Information");
          System.out.println("3. Update Ticket Information");
          System.out.println("4. Logout");
          System.out.println("----------------------------------------------------------------");
          do {
            System.out.print("Please select an option (1-4): ");
            option = scnr.nextLine();
          } while (!option.equals("1") && !option.equals("2") && !option.equals("3")
              && !option.equals("4"));
          if (option.equals("4")) {
            System.out.println("Thank you for using the Train Ticket System!");
            System.out.println("----------------------------------------------------------------");
            break;
          }
          // Fill in Purchase Ticket option here
          if (option.equals("1")) {
            purchaseTicket();
            System.out.println("----------------------------------------------------------------");
          }
          // View Ticket Info Option
          if (option.equals("2")) {
            String method = "";
            System.out.println("1. Search by Ticket ID");
            System.out.println("2. Search by passenger name");
            do {
              System.out.print("Select Search Method (1-2): ");
              method = scnr.nextLine();
            } while (!method.equals("1") && !method.equals("2"));
            if (method.equals("1")) {
              String id = "";
              System.out.print("Enter Ticket ID: ");
              id = scnr.nextLine();
              getTicketInfoByID(id);
              System.out
                  .println("----------------------------------------------------------------");
            } else {
              String name = "";
              System.out.print("Enter Passenger Name: ");
              name = scnr.nextLine();
              getTicketInfoByName(name);
              System.out
                  .println("----------------------------------------------------------------");
            }
          }
          // Update Ticket Info Option
          if (option.equals("3")) {
            updateTicketInfo();
            System.out.println("----------------------------------------------------------------");
          }
        }
      }
    }
  }

  /**
   * Method for returning ticket info by searching a ticket ID.
   * <p>
   *
   * @param ticketID - the ID of the ticket being searched for
   */
  private static void getTicketInfoByID(String ticketID) {
    TicketInfo ticket;
    if (!database.hasTicketInfo(ticketID)) {
      System.out.println("There is no ticket with the ID " + ticketID + " within the system.");
      return;
    } else {
      ticket = database.getTicketInfo(ticketID);
      System.out.println("Ticket ID: " + ticket.getTicketID());
      System.out.println("Name: " + ticket.getName());
      System.out.println("Depart city: " + ticket.getDepartCity());
      System.out.println("Departure time: " + ticket.getDepartTime().toString());
      System.out.println("Destination city: " + ticket.getArriveCity());
      System.out.println("Arrival time: " + ticket.getArriveTime().toString());
      System.out.println("Total duration (hours): " + ticket.getDurationInHours());
      System.out.printf("Price: $%.2f\n", ticket.getPrice());
      int option = ticket.getOption();
      if (option == 1) {
        System.out.println("Option: Economic travel");
      } else if (option == 2) {
        System.out.println("Option: Fast travel");
      }
      List<String> path = ticket.getTravelPath();
      if (path != null) {
        for (int i = 0; i < path.size(); ++i) {
          System.out.print(path.get(i));
          if (i != path.size() - 1) {
            System.out.print(" -> ");
          } else {
            System.out.println();
          }
        }
      }
    }
  }

  /**
   * Method for returning a list of tickets purchased under a specified name.
   * <p>
   *
   * @param name - the name under which the ticket(s) being searched for are purchased under.
   */
  private static void getTicketInfoByName(String name) {
    List<TicketInfo> tickets = database.getTicketInfoByName(name);
    if (tickets.isEmpty()) {
      System.out.println(name + " has not purchased any tickets.");
    } else {
      for (int i = 0; i < tickets.size(); ++i) {
        TicketInfo currTicket = tickets.get(i);
        System.out.println("----------------------------------------------------------------");
        System.out.println("Ticket ID: " + currTicket.getTicketID());
        System.out.println("Name: " + currTicket.getName());
        System.out.println("Depart city: " + currTicket.getDepartCity());
        System.out.println("Departure time: " + currTicket.getDepartTime().toString());
        System.out.println("Destination city: " + currTicket.getArriveCity());
        System.out.println("Arrival time: " + currTicket.getArriveTime().toString());
        System.out.println("Total duration (hours): " + currTicket.getDurationInHours());
        System.out.printf("Price: $%.2f\n", currTicket.getPrice());
        int option = currTicket.getOption();
        if (option == 1) {
          System.out.println("Option: Economic travel");
        } else if (option == 2) {
          System.out.println("Option: Fast travel");
        }
        List<String> path = currTicket.getTravelPath();
        if (path != null) {
          for (int j = 0; j < path.size(); ++j) {
            System.out.print(path.get(j));
            if (j != path.size() - 1) {
              System.out.print(" -> ");
            } else {
              System.out.println();
            }
          }
        }
        if (i != tickets.size() - 1) {
          System.out.println();
          System.out.println("-----------------------------------------------------------------");
          System.out.println();
        }
      }
    }
  }

  /**
   * Method allows a user to purchase a ticket.
   */
  private static void purchaseTicket() {
    Scanner scnr = new Scanner(System.in);
    // Get name
    String name = "";
    do {
      System.out.print("Enter passenger name: ");
      name = scnr.nextLine();
    } while (name.equals(""));
    // Get cities
    String departCity = "";
    String arriveCity = "";
    do {
      do {
        System.out.print("Enter departure city: ");
        departCity = scnr.nextLine();
        if (!database.checkCityName(departCity)) {
          System.out.println(departCity + " is not a valid city within the train system.");
        }
      } while (!database.checkCityName(departCity));
      do {
        System.out.print("Enter destination city: ");
        arriveCity = scnr.nextLine();
        if (!database.checkCityName(arriveCity)) {
          System.out.println(arriveCity + " is not a valid city within the train system.");
        }
      } while (!database.checkCityName(arriveCity));
      if (!database.checkCitiesConnected(departCity, arriveCity)) {
        System.out.println(
            arriveCity + " and " + departCity + " are not connected within the train system.");
      }
    } while (!database.checkCitiesConnected(departCity, arriveCity));

    // Get departure date & time
    LocalDateTime departDateTime;
    LocalDate departDate;
    LocalTime departTime = LocalTime.of(0, 0);
    while (true) {
      try {
        System.out.println("Departure date: ");
        System.out.println("All dates are in 2020.");
        // Get month
        int month;
        while (true) {
          System.out.print("Month (1-12): ");
          month = Integer.parseInt(scnr.nextLine());
          if (month < 1 || month > 12) {
            throw new Exception("Month is outside valid range.");
          }
          break;
        }
        // Get day
        int day;
        while (true) {
          if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10
              || month == 12) {
            System.out.print("Day (1-31): ");
            day = Integer.parseInt(scnr.nextLine());
            if (day < 1 || day > 31) {
              throw new Exception("Day is outside valid range.");
            }
            break;
          } else if (month == 4 || month == 6 || month == 9 || month == 11) {
            System.out.print("Day (1-30): ");
            day = Integer.parseInt(scnr.nextLine());
            if (day < 1 || day > 30) {
              throw new Exception("Day is outside valid range.");
            }
            break;
          } else if (month == 2) {
            System.out.print("Day (1-29): ");
            day = Integer.parseInt(scnr.nextLine());
            if (day < 1 || day > 29) {
              throw new Exception("Day is outside valid range.");
            }
            break;
          }
        }
        departDate = LocalDate.of(2020, month, day);

        // Get departure time
        String timeOpt = "";
        do {
          System.out.println("Available Departure Times: ");
          System.out.println("1. 8:00");
          System.out.println("2. 10:00");
          System.out.println("3. 15:00");
          System.out.println("4. 18:00");
          System.out.print("Select time (1-4): ");
          timeOpt = scnr.nextLine();
        } while (!timeOpt.equals("1") && !timeOpt.equals("2") && !timeOpt.equals("3")
            && !timeOpt.equals("4"));
        if (timeOpt.equals("1")) {
          departTime = LocalTime.of(8, 0);
        } else if (timeOpt.equals("2")) {
          departTime = LocalTime.of(10, 0);
        } else if (timeOpt.equals("3")) {
          departTime = LocalTime.of(15, 0);
        } else if (timeOpt.equals("4")) {
          departTime = LocalTime.of(18, 0);
        }
        if (departTime.compareTo(LocalTime.of(0, 0)) == 0) {
          throw new Exception("The departure time was not successfully selected.");
        }
        departDateTime = LocalDateTime.of(departDate, departTime);
        break;
      } catch (Exception e) {
        System.out.println("Invalid date. " + e.getMessage());
      }
    }

    // Get option
    int option = 0;
    while (true) {
      try {
        System.out.println("Available options: ");
        System.out.println("1. Economic travel (least expensive)");
        System.out.println("2. Fast travel (shortest travel duration)");
        System.out.print("Select option (1-2): ");
        option = Integer.parseInt(scnr.nextLine());
        if (option < 1 || option > 2) {
          throw new Exception("Invalid option");
        }
      } catch (Exception e) {
        System.out.println("Invalid option");
        continue;
      }

      // Add ticket to system
      String ticketID = database.addTicket(name, departCity, arriveCity, departDateTime, option);
      System.out.println("Your ticket ID is: " + ticketID);
      System.out.println("Please record this ID for future search purposes.");
      break;
    }
  }

  /**
   * Method to update various aspects of a tickets information. User is asked to select a method to
   * search for a ticket. If the ticket exists, the user can update the Departure City, the Arrival
   * City, the Departure Date and/or Time, or the Travel Method.
   */
  private static void updateTicketInfo() {
    Scanner scnr = new Scanner(System.in);
    String currID = "";

    // Get search method
    String method = "";
    System.out.println("1. Search by Ticket ID");
    System.out.println("2. Search by passenger name");
    do {
      System.out.print("Select Search Method (1-2): ");
      method = scnr.nextLine();
    } while (!method.equals("1") && !method.equals("2"));
    String field = "";
    if (method.equals("1")) {
      // Search by Ticket ID
      TicketInfo ticket;
      String id = "";
      System.out.print("Enter Ticket ID: ");
      id = scnr.nextLine();
      if (!database.hasTicketInfo(id)) {
        System.out.println("There is no ticket with the ID " + id + " within the system.");
        return;
      } else {
        // Get field to update
        do {
          ticket = database.getTicketInfo(id);
          System.out.println("1. Departure City");
          System.out.println("2. Arrival City");
          System.out.println("3. Departure Date and/or Time");
          System.out.println("4. Travel Method (Economic or Fast Travel)");
          System.out.println("5. Quit to Main Screen");
          System.out.println("Please select field to update (1-5): ");
          field = scnr.nextLine();
        } while (!field.equals("1") && !field.equals("2") && !field.equals("3")
            && !field.equals("4") && !field.equals("5"));
        if (field.equals("5")) {
          return;
        }
        if (field.equals("1")) {
          // Update departure city
          String departCity = "";
          do {
            System.out.print("Enter departure city: ");
            departCity = scnr.nextLine();
            if (!database.checkCityName(departCity)) {
              // Did not enter a valid departure city
              System.out.println(departCity + " is not a valid city within the train system.");
            } else if (!database.checkCitiesConnected(departCity, ticket.getArriveCity())
                && database.checkCityName(departCity)) {
              // New departure city is not connected with old arrival city
              System.out.println("Your entered departure city (" + departCity
                  + ") is not connected with your arrival city (" + ticket.getArriveCity() + ")");
            }
          } while (!database.checkCityName(departCity));
          currID = database.updateTicketDepartCity(id, departCity, ticket.getOption());
          // Give user their new ticket ID
          System.out.println("Your ticket ID is: " + currID);
          System.out.println("Please record this ID for future search purposes.");
        } else if (field.equals("2")) {
          // Update arrival city
          String arriveCity = "";
          do {
            System.out.print("Enter arrival city: ");
            arriveCity = scnr.nextLine();
            if (!database.checkCityName(arriveCity)) {
              System.out.println(arriveCity + " is not a valid city within the train system.");
            } else if (!database.checkCitiesConnected(ticket.getDepartCity(), arriveCity)
                && database.checkCityName(arriveCity)) {
              System.out.println("Your departure city (" + ticket.getDepartCity()
                  + ") is not connected with your entered arrival city (" + arriveCity + ")");
            }
          } while (!database.checkCityName(arriveCity)
              && !database.checkCitiesConnected(ticket.getDepartCity(), arriveCity));
          currID = database.updateTicketArriveCity(id, arriveCity, ticket.getOption());
          // Give user their new ticket ID
          System.out.println("Your ticket ID is: " + currID);
          System.out.println("Please record this ID for future search purposes.");
        } else if (field.equals("3")) {
          // Update departure date and/or time
          System.out.println(
              "Your current departure date and time is: " + ticket.getDepartTime().toString());

          LocalDateTime departDateTime = LocalDateTime.of(0, 0, 0, 0, 0);
          LocalDate departDate;
          LocalTime departTime = LocalTime.of(0, 0);
          while (true) {
            try {
              System.out.println("Departure date: ");
              System.out.println("All dates are in 2020.");
              // Get month
              int month;
              while (true) {
                System.out.print("Month (1-12): ");
                month = Integer.parseInt(scnr.nextLine());
                if (month < 1 || month > 12) {
                  throw new Exception("Month is outside valid range.");
                }
                // Update month of ticket, saving currID for new day
                currID = database.updateTicketMonth(id, month);
                break;
              }
              // Get day
              int day;
              while (true) {
                if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8
                    || month == 10 || month == 12) {
                  System.out.print("Day (1-31): ");
                  day = Integer.parseInt(scnr.nextLine());
                  if (day < 1 || day > 31) {
                    throw new Exception("Day is outside valid range.");
                  }
                  break;
                } else if (month == 4 || month == 6 || month == 9 || month == 11) {
                  System.out.print("Day (1-30): ");
                  day = Integer.parseInt(scnr.nextLine());
                  if (day < 1 || day > 30) {
                    throw new Exception("Day is outside valid range.");
                  }
                  break;
                } else if (month == 2) {
                  System.out.print("Day (1-29): ");
                  day = Integer.parseInt(scnr.nextLine());
                  if (day < 1 || day > 29) {
                    throw new Exception("Day is outside valid range.");
                  }
                  // Update the ticket day, saving id for new time
                  currID = database.updateTicketDay(currID, day);
                  break;
                }
              }
              departDate = LocalDate.of(2020, month, day);

              // Get departure time
              String timeOpt = "";
              do {
                System.out.println("Available Departure Times: ");
                System.out.println("1. 8:00");
                System.out.println("2. 10:00");
                System.out.println("3. 15:00");
                System.out.println("4. 18:00");
                System.out.print("Select time (1-4): ");
                timeOpt = scnr.nextLine();
              } while (!timeOpt.equals("1") && !timeOpt.equals("2") && !timeOpt.equals("3")
                  && !timeOpt.equals("4"));
              if (timeOpt.equals("1")) {
                departTime = LocalTime.of(8, 0);
              } else if (timeOpt.equals("2")) {
                departTime = LocalTime.of(10, 0);
              } else if (timeOpt.equals("3")) {
                departTime = LocalTime.of(15, 0);
              } else if (timeOpt.equals("4")) {
                departTime = LocalTime.of(18, 0);
              }
              if (departTime.compareTo(LocalTime.of(0, 0)) == 0) {
                throw new Exception("The departure time was not successfully selected.");
              }
              departDateTime = LocalDateTime.of(departDate, departTime);
              currID = database.updateTicketTime(id, departTime);
              break;
            } catch (Exception e) {
              System.out.println("Invalid date. " + e.getMessage());
            }
          }
          // Output new departure date and time and the tickets new ID
          System.out.println("Your new departure date and time "
              + database.getTicketInfo(id).getDepartTime().toString());
          System.out.println("Your new ticket ID is: " + currID);
          System.out.println("Please record this ID for future search purposes.");
        } else if (field.equals("4")) {
          // Update path calculation option
          int currOption = ticket.getOption();
          if (currOption == 1) {
            System.out.println("Your current route between " + ticket.getDepartCity() + " and "
                + ticket.getArriveCity() + " is calculated to be the most economic route.");
          } else {
            System.out.println("Your current route between " + ticket.getDepartCity() + " and "
                + ticket.getArriveCity() + " is calculated to be the fastest route.");
          }
          int newOption;
          while (true) {
            try {
              System.out.println("Available options: ");
              System.out.println("1. Economic travel (least expensive)");
              System.out.println("2. Fast travel (shortest travel duration)");
              System.out.print("Select option (1-2): ");
              newOption = Integer.parseInt(scnr.nextLine());
              if (newOption < 1 || newOption > 2) {
                throw new Exception("Invalid option");
              }
              currID = database.updateTicketOption(id, newOption);
              break;
            } catch (Exception e) {
              System.out.println("Invalid option");
            }
          }
          // Output new option and give the user their new ticket ID
          if (database.getTicketInfo(currID).getOption() == 1) {
            System.out.println("Your current route between " + ticket.getDepartCity() + " and "
                + ticket.getArriveCity() + " is now calulated to be the most economic route.");
          } else {
            System.out.println("Your current route between " + ticket.getDepartCity() + " and "
                + ticket.getArriveCity() + " is calulated to be the fastest route.");
          }
          System.out.println("Your new ticket ID is: " + currID);
          System.out.println("Please record this ID for future search purposes.");
        }
      }
    } else {
      // Search by name
      String name = "";
      String id = "";
      do {
        System.out.print("Enter Passenger Name: ");
        name = scnr.nextLine();
        // Repeat while name entered is empty string
      } while (name.equals(""));
      List<TicketInfo> tickets = database.getTicketInfoByName(name);
      // There are no tickets under that name
      if (tickets.isEmpty()) {
        System.out.println(name + " has not purchased any tickets.");
        return;
      } else {
        TicketInfo ticket;
        // Output the tickets (each with a number) available to update
        for (int i = 0; i < tickets.size(); ++i) {
          System.out.println("-----------------------------------------------------------------");
          System.out.print((i + 1) + ". ");
          getTicketInfoByID(tickets.get(i).getTicketID());
          System.out.println("-----------------------------------------------------------------");
        }
        // Get the ticket to update
        int ticketNum;
        while (true) {
          try {
            System.out.print("Please enter the number of the ticket you would like to update: ");
            ticketNum = Integer.parseInt(scnr.nextLine());
            // Check if ticket is within the valid range to get
            if (ticketNum < 1 || ticketNum > tickets.size()) {
              System.out.println("The number provided is not a valid ticket to update.");
            } else {
              ticket = tickets.get(ticketNum - 1);
              break;
            }
          } catch (Exception e) {
            // If input was not parse-able by Integer.parseInt()
            System.out.println("The provided input is not a valid ticket to update.");
          }
        }
        id = ticket.getTicketID();
        // Get the field to update
        do {
          ticket = database.getTicketInfo(id);
          System.out.println("1. Departure City");
          System.out.println("2. Arrival City");
          System.out.println("3. Departure Date and/or Time");
          System.out.println("4. Travel Method (Economic or Fast Travel)");
          System.out.println("Please select field to update (1-4): ");
          field = scnr.nextLine();
        } while (!field.equals("1") && !field.equals("2") && !field.equals("3")
            && !field.equals("4"));
        if (field.equals("1")) {
          // Update departure city
          String departCity = "";
          do {
            System.out.print("Enter departure city: ");
            departCity = scnr.nextLine();
            if (!database.checkCityName(departCity)) {
              // Did not enter a valid departure city
              System.out.println(departCity + " is not a valid city within the train system.");
            } else if (!database.checkCitiesConnected(departCity, ticket.getArriveCity())
                && database.checkCityName(departCity)) {
              // New departure city is not connected with old arrival city
              System.out.println("Your entered departure city (" + departCity
                  + ") is not connected with your arrival city (" + ticket.getArriveCity() + ")");
            }
          } while (!database.checkCityName(departCity));
          currID = database.updateTicketDepartCity(id, departCity, ticket.getOption());
          // Give user their new ticket ID
          System.out.println("Your ticket ID is: " + currID);
          System.out.println("Please record this ID for future search purposes.");
        } else if (field.equals("2")) {
          // Update arrival city
          String arriveCity = "";
          do {
            System.out.print("Enter arrival city: ");
            arriveCity = scnr.nextLine();
            if (!database.checkCityName(arriveCity)) {
              System.out.println(arriveCity + " is not a valid city within the train system.");
            } else if (!database.checkCitiesConnected(ticket.getDepartCity(), arriveCity)
                && database.checkCityName(arriveCity)) {
              System.out.println("Your departure city (" + ticket.getDepartCity()
                  + ") is not connected with your entered arrival city (" + arriveCity + ")");
            }
          } while (!database.checkCityName(arriveCity)
              && !database.checkCitiesConnected(ticket.getDepartCity(), arriveCity));
          currID = database.updateTicketArriveCity(id, arriveCity, ticket.getOption());
          // Give user their new ticket ID
          System.out.println("Your ticket ID is: " + currID);
          System.out.println("Please record this ID for future search purposes.");
        } else if (field.equals("3")) {
          // Update departure date and/or time
          System.out.println(
              "Your current departure date and time is: " + ticket.getDepartTime().toString());

          LocalDateTime departDateTime;
          LocalDate departDate;
          LocalTime departTime = LocalTime.of(0, 0);
          while (true) {
            try {
              System.out.println("Departure date: ");
              System.out.println("All dates are in 2020.");
              // Get month
              int month;
              while (true) {
                System.out.print("Month (1-12): ");
                month = Integer.parseInt(scnr.nextLine());
                if (month < 1 || month > 12) {
                  throw new Exception("Month is outside valid range.");
                }
                // Update month of ticket, saving currID for new day
                currID = database.updateTicketMonth(id, month);
                break;
              }
              // Get day
              int day;
              while (true) {
                if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8
                    || month == 10 || month == 12) {
                  System.out.print("Day (1-31): ");
                  day = Integer.parseInt(scnr.nextLine());
                  if (day < 1 || day > 31) {
                    throw new Exception("Day is outside valid range.");
                  }
                  currID = database.updateTicketDay(currID, day);
                  break;
                } else if (month == 4 || month == 6 || month == 9 || month == 11) {
                  System.out.print("Day (1-30): ");
                  day = Integer.parseInt(scnr.nextLine());
                  if (day < 1 || day > 30) {
                    throw new Exception("Day is outside valid range.");
                  }
                  currID = database.updateTicketDay(currID, day);
                  break;
                } else if (month == 2) {
                  System.out.print("Day (1-29): ");
                  day = Integer.parseInt(scnr.nextLine());
                  if (day < 1 || day > 29) {
                    throw new Exception("Day is outside valid range.");
                  }
                  // Update the ticket day, saving id for new time
                  currID = database.updateTicketDay(currID, day);
                  break;
                }
              }
              departDate = LocalDate.of(2020, month, day);

              // Get departure time
              String timeOpt = "";
              do {
                System.out.println("Available Departure Times: ");
                System.out.println("1. 8:00");
                System.out.println("2. 10:00");
                System.out.println("3. 15:00");
                System.out.println("4. 18:00");
                System.out.print("Select time (1-4): ");
                timeOpt = scnr.nextLine();
              } while (!timeOpt.equals("1") && !timeOpt.equals("2") && !timeOpt.equals("3")
                  && !timeOpt.equals("4"));
              if (timeOpt.equals("1")) {
                departTime = LocalTime.of(8, 0);
              } else if (timeOpt.equals("2")) {
                departTime = LocalTime.of(10, 0);
              } else if (timeOpt.equals("3")) {
                departTime = LocalTime.of(15, 0);
              } else if (timeOpt.equals("4")) {
                departTime = LocalTime.of(18, 0);
              }
              if (departTime.compareTo(LocalTime.of(0, 0)) == 0) {
                throw new Exception("The departure time was not successfully selected.");
              }
              departDateTime = LocalDateTime.of(departDate, departTime);
              currID = database.updateTicketTime(currID, departTime);
              break;
            } catch (Exception e) {
              System.out.println("Invalid date. " + e.getMessage());
            }
          }
          // Output new departure date and time and the tickets new ID
          System.out.println("Your new departure date and time "
              + database.getTicketInfo(currID).getDepartTime().toString());
          System.out.println("Your new ticket ID is: " + currID);
          System.out.println("Please record this ID for future search purposes.");
        } else if (field.equals("4")) {
          // Update path calculation option
          int currOption = ticket.getOption();
          if (currOption == 1) {
            System.out.println("Your current route between " + ticket.getDepartCity() + " and "
                + ticket.getArriveCity() + " is calculated to be the most economic route.");
          } else {
            System.out.println("Your current route between " + ticket.getDepartCity() + " and "
                + ticket.getArriveCity() + " is calculated to be the fastest route.");
          }
          int newOption;
          while (true) {
            try {
              System.out.println("Available options: ");
              System.out.println("1. Economic travel (least expensive)");
              System.out.println("2. Fast travel (shortest travel duration)");
              System.out.print("Select option (1-2): ");
              newOption = Integer.parseInt(scnr.nextLine());
              if (newOption < 1 || newOption > 2) {
                throw new Exception("Invalid option");
              }
              currID = database.updateTicketOption(id, newOption);
              break;
            } catch (Exception e) {
              System.out.println("Invalid option");
            }
          }
          // Output new option and give the user their new ticket ID
          if (database.getTicketInfo(currID).getOption() == 1) {
            System.out.println("Your current route between " + ticket.getDepartCity() + " and "
                + ticket.getArriveCity() + " is now calulated to be the most economic route.");
          } else {
            System.out.println("Your current route between " + ticket.getDepartCity() + " and "
                + ticket.getArriveCity() + " is calulated to be the fastest route.");
          }
          System.out.println("Your new ticket ID is: " + currID);
          System.out.println("Please record this ID for future search purposes.");
        }

      }

    }
  }

  /**
   * Method to update city and path information for the train system. This should only be accessed
   * by users who have Admin privileges.
   */
  private static void updateCityInformation() {
    Scanner scnr = new Scanner(System.in);

    String option = "";
    do {
      System.out.println("1. Add a City");
      System.out.println("2. Delete a City");
      System.out.println("3. Add a Connection");
      System.out.println("4. Delete a Connection");
      System.out.println("5. Update a Connection Time");
      System.out.println("6. Update a Connection Price");
      System.out.println("7. Return to Main Screen");
      System.out.print("Please select an option (1-7): ");
      option = scnr.nextLine();
    } while (!option.equals("1") && !option.equals("2") && !option.equals("3")
        && !option.equals("4") && !option.equals("5") && !option.equals("6")
        && !option.equals("7"));
    // Exit to Main Screen
    if (option.equals("7")) {
      return;
    }
    // Add a city
    if (option.equals("1")) {
      String cityToAdd = "";
      String tryAgain = "";
      while (true) {
        do {
          System.out.print("Enter the city name: ");
          cityToAdd = scnr.nextLine();
        } while (cityToAdd.equals(""));
        if (!database.addCity(cityToAdd)) {
          // City was not successfully added
          System.out.println(cityToAdd + " was not able to be added to the Train System.");
          do {
            System.out.print("Would you like to try to add a different city? (y/n): ");
            tryAgain = scnr.nextLine();
          } while (!tryAgain.toLowerCase().equals("y") && !tryAgain.toLowerCase().equals("n"));
          if (tryAgain.toLowerCase().equals("y")) {
            // Want to try again, so continue to while loop and execute do while again.
            continue;
          } else if (tryAgain.toLowerCase().equals("n")) {
            // Do not want to try again
            break;
          }
        } else {
          // City was successfully added
          break;
        }
      }
    } else if (option.equals("2")) {
      // Delete a City
      String cityToDelete = "";
      String tryAgain = "";
      while (true) {
        do {
          System.out.print("Enter the city name: ");
          cityToDelete = scnr.nextLine();
        } while (cityToDelete.equals(""));
        if (!database.deleteCity(cityToDelete)) {
          // City was not successfully deleted
          System.out.println(cityToDelete + " was not able to be deleted from the Train System.");
          do {
            System.out.print("Would you like to try to delete a different city? (y/n): ");
            tryAgain = scnr.nextLine();
          } while (!tryAgain.toLowerCase().equals("y") && !tryAgain.toLowerCase().equals("n"));
          if (tryAgain.toLowerCase().equals("y")) {
            // Want to try again, so continue to while loop and execute do while again.
            continue;
          } else if (tryAgain.toLowerCase().equals("n")) {
            // Do not want to try again
            break;
          }
        } else {
          // City was successfully deleted
          break;
        }
      }
    } else if (option.equals("3")) {
      // Add a Connection
      String city1 = "";
      String city2 = "";
      double price;
      double time;
      String tryAgain = "";
      while (true) {
        do {
          System.out.print("Enter the starting city: ");
          city1 = scnr.nextLine();
          System.out.print("Enter the ending city: ");
          city2 = scnr.nextLine();
          try {
            System.out.print("Enter the path price (will be rounded down to nearest cent): ");
            price = Double.parseDouble(scnr.nextLine());
          } catch (Exception e) {
            System.out.println("The input was not properly resolved to a price.");
            price = -1;
          }
          try {
            System.out.print("Enter the travel time duration of the path: ");
            time = Double.parseDouble(scnr.nextLine());
          } catch (Exception e) {
            System.out.println("The input was not properly resolved to a time.");
            time = -1;
          }
        } while (city1.equals("") || city2.equals(""));
        if (!database.addConnection(city1, city2, price, time) || time < 0 || price < 0) {
          // Connection was not successfully added
          System.out.println("The connection entered was not successfully added.");
          do {
            System.out.print("Would you like to try to add a new connection? (y/n): ");
            tryAgain = scnr.nextLine();
          } while (!tryAgain.toLowerCase().equals("y") && !tryAgain.toLowerCase().equals("n"));
          if (tryAgain.toLowerCase().equals("y")) {
            // Want to try again, so continue to while loop and execute do while again.
            continue;
          } else if (tryAgain.toLowerCase().equals("n")) {
            // Do not want to try again
            break;
          }
        } else {
          // City was successfully deleted
          break;
        }
      }
    } else if (option.equals("4")) {
      // Delete a Connection
      String city1 = "";
      String city2 = "";
      String tryAgain = "";
      while (true) {
        do {
          System.out.print("Enter the starting city: ");
          city1 = scnr.nextLine();
          System.out.println("Enter the ending city: ");
          city2 = scnr.nextLine();
        } while (city1.equals("") || city2.equals(""));
        if (!database.deleteConnection(city1, city2)) {
          // Connection was not successfully deleted
          System.out.println("The connection entered was not successfully deleted.");
          do {
            System.out.print("Would you like to try to delete a different connection? (y/n): ");
            tryAgain = scnr.nextLine();
          } while (!tryAgain.toLowerCase().equals("y") && !tryAgain.toLowerCase().equals("n"));
          if (tryAgain.toLowerCase().equals("y")) {
            // Want to try again, so continue to while loop and execute do while again.
            continue;
          } else if (tryAgain.toLowerCase().equals("n")) {
            // Do not want to try again
            break;
          }
        } else {
          // Connection was successfully deleted
          break;
        }
      }
    } else if (option.equals("5")) {
      // Update a Connection Price
      String city1 = "";
      String city2 = "";
      double price;
      String tryAgain = "";
      while (true) {
        do {
          System.out.print("Enter the starting city: ");
          city1 = scnr.nextLine();
          System.out.println("Enter the ending city: ");
          city2 = scnr.nextLine();
          try {
            System.out.print("Enter the new price (will be rounded down to nearest cent): ");
            price = Double.parseDouble(scnr.nextLine());
          } catch (Exception e) {
            System.out.println("The input was not properly resolved to a price.");
            price = -1;
          }
        } while (city1.equals("") || city2.equals(""));
        if (!database.updateConnectionPrice(city1, city2, price) || price < 0) {
          System.out.println("The price for the connection entered was not successfully updated");
          do {
            System.out.print("Would you like to try to update a new connection? (y/n): ");
            tryAgain = scnr.nextLine();
          } while (!tryAgain.toLowerCase().equals("y") && !tryAgain.toLowerCase().equals("n"));
          if (tryAgain.toLowerCase().equals("y")) {
            // Want to try again, so continue to while loop and execute do while again.
            continue;
          } else if (tryAgain.toLowerCase().equals("n")) {
            // Do not want to try again
            break;
          }
        } else {
          // Price was successfully updated
          break;
        }
      }
    } else if (option.equals("6")) {
      // Update Connection time
      String city1 = "";
      String city2 = "";
      double time;
      String tryAgain = "";
      while (true) {
        do {
          System.out.print("Enter the starting city: ");
          city1 = scnr.nextLine();
          System.out.println("Enter the ending city: ");
          city2 = scnr.nextLine();
          try {
            System.out.print("Enter the new time duration: ");
            time = Double.parseDouble(scnr.nextLine());
          } catch (Exception e) {
            System.out.println("The input was not properly resolved to a time.");
            time = -1;
          }
        } while (city1.equals("") || city2.equals(""));
        if (!database.updateConnectionTime(city1, city2, time) || time < 0) {
          System.out.println("The time for the connection entered was not successfully updated");
          do {
            System.out.print("Would you like to try to update a new connection? (y/n): ");
            tryAgain = scnr.nextLine();
          } while (!tryAgain.toLowerCase().equals("y") && !tryAgain.toLowerCase().equals("n"));
          if (tryAgain.toLowerCase().equals("y")) {
            // Want to try again, so continue to while loop and execute do while again.
            continue;
          } else if (tryAgain.toLowerCase().equals("n")) {
            // Do not want to try again
            break;
          }
        } else {
          // Time was successfully updated
          break;
        }
      }
    }
  }

  /**
   * @Description: This function is used to implement the basic registration and login functions
   * @Param: [logInAndRegister, input]
   * @return: java.lang.String
   * @Author: Weijie Zhou
   * @Date: 2020/9/27
   */
  public static String logInAndRegister(LogInAndRegister logInAndRegister, String input) {
    String username = "";
    String password = "";
    Scanner scanner = new Scanner(System.in);
    if (input.equals("1")) {
      System.out.println("Please register");
      System.out.print("username: ");
      username = scanner.nextLine();
      while (logInAndRegister.isUserExist(username)) {
        System.out.println("Username exists! Try another one.");
        System.out.print("username: ");
        username = scanner.nextLine();
      }
      System.out.print("password: ");
      password = scanner.nextLine();
      System.out.print("Are you administrator (y/n): ");
      input = scanner.nextLine();
      while (!input.equalsIgnoreCase("y") && !input.equalsIgnoreCase("n")) {
        System.out.println("Invalid input");
        System.out.print("Are you administrator (y/n): ");
        input = scanner.nextLine();
      }
      logInAndRegister.addUser(username, password, input.equalsIgnoreCase("y"));
    } else {
      System.out.println("Please log in");
      System.out.print("username: ");
      username = scanner.nextLine();
      System.out.print("password: ");
      password = scanner.nextLine();
      if (!logInAndRegister.logIn(username, password)) {
        return null;
      }
    }

    return username;
  }

}
