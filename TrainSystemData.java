// --== CS400 File Header Information ==--
// Name: Isaac Colbert
// Email: icolbert@wisc.edu
// Team: CG
// Role: Front End Developer 1
// TA: Yeping Wang
// Lecturer: Florian Heimerl
// Notes to Grader: <optional extra notes>
import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class TrainSystemData {

  public static void main(String[] args) {
    try {
      System.out.println("<html>");
      // Head
      System.out.println("<head>");
      System.out.println("<meta charset=\"UTF-8\" author=\"Isaac Colbert\"></meta>");
      System.out.println("<link rel=\"stylesheet\" type=\"text/css\" "
          + "href=\"TrainSystemStyle.css\"</link>");
      System.out.println("</head>");
      // Get the information from the CSV file
      double revenue = 0.0;
      int numTickets = 0;
      double totTime = 0.0;
      File file = new File("./ticketInfo.csv");
      ArrayList<String[]> info = new ArrayList<>();
      try (Scanner scnr = new Scanner(file)){
        while(scnr.hasNextLine()) {
          String[] line = scnr.nextLine().split(",");
          info.add(line);
          try {
            revenue += Double.parseDouble(line[7]);
            totTime += Double.parseDouble(line[6]);
            // If not able to get revenue or time, don't add to total tickets
            ++numTickets;
          } catch (Exception e) {
            // Ignore exception, don't add to revenue or totTime
            // Should catch exception on first iteration because of headers
          }
        }
      }
      
      double avgTime = totTime / numTickets;
      double avgPrice = revenue / numTickets;
      
      // Sales Information
      System.out.println("<h2>Sales Information</h2>");
      System.out.println("<p>Number of tickets sold: " + numTickets + "</p>");
      System.out.printf("<p>Total revenue from ticket sales: $%.2f\n</p>", revenue);
      System.out.printf("<p>Average ticket sale: $%.2f\n</p>", avgPrice);
      
      // Travel Time Information
      System.out.println("<h2>Travel Time Information</h2>");
      System.out.printf("<p>Total travel time (hours): %.2f</p>", totTime);
      System.out.printf("<p>Average travel time (hours): %.2f</p>", avgTime);

      // Body
      System.out.println("<body><p>");
      System.out.println("<h1>Train System Ticket Data</h1>");
      //System.out.println("<p>There are currently ");
      
      // Print out the table of tickets
      System.out.println("<table>");
      // Print out the headers of the CSV file
      String[] header = info.get(0);
      System.out.println("<tr>");
      for (int i = 0; i < header.length; ++i) {
        System.out.print("<th>" + header[i] + "</th>");
      }
      System.out.println("</tr>");
      // Print out table elements
      for (int i = 1; i < info.size(); ++i) {
        String[] line = info.get(i);
        System.out.println("<tr>");
        for (int j = 0; j < line.length; ++j) {
          System.out.print("<td>" + line[j] + "</td>");
        }
        System.out.println("</tr>");
      }
      System.out.println("</table>");
      
      System.out.println("</p></body>");
      System.out.println("</html>");
    } catch (Exception e) {
      System.out.println("<html><body><pre>");
      System.out.println("Something went wrong, there was an exception in TrainSystemData.java: ");
      e.printStackTrace(System.out);
      System.out.println("</pre></body></html>");
    }
  }
}
