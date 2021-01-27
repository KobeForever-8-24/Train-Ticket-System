// --== CS400 File Header Information ==--
// Name: Deming Xu
// Email: dxu227@wisc.edu
// Team: CG
// Role: Back End Developer 1
// TA: Yeping Wang
// Lecturer: Gary Dahl
// Notes to Grader: None

import java.time.LocalDateTime;
import java.util.List;

/**
 * TicketInfo Class
 */
public class TicketInfo {
    private String ticketID;
    private String name;
    private String departCity;
    private String arriveCity;
    private List<String> travelPath;
    private LocalDateTime departTime;
    private LocalDateTime arriveTime;
    private double durationInHours;
    private double price;
    private int option;    // Option: 1. Economic travel option 2. Fast travel option

    /**
     * Constructor
     *
     * @param ticketID
     * @param name
     * @param departCity
     * @param arriveCity
     * @param travelPath
     * @param departTime
     * @param arriveTime
     * @param durationInHours
     * @param price
     * @param option
     */
    public TicketInfo(String ticketID, String name, String departCity, String arriveCity, List<String> travelPath,
                      LocalDateTime departTime, LocalDateTime arriveTime, double durationInHours, double price, int option) {
        this.ticketID = ticketID;
        this.name = name;
        this.departCity = departCity;
        this.arriveCity = arriveCity;
        this.travelPath = travelPath;
        this.departTime = departTime;
        this.arriveTime = arriveTime;
        this.durationInHours = durationInHours;
        this.price = price;
        this.option = option;
    }

    public String getTicketID() {
        return ticketID;
    }

    public void setTicketID(String ticketID) {
        this.ticketID = ticketID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDepartCity() {
        return departCity;
    }

    public void setDepartCity(String departCity) {
        this.departCity = departCity;
    }

    public String getArriveCity() {
        return arriveCity;
    }

    public void setArriveCity(String arriveCity) {
        this.arriveCity = arriveCity;
    }

    public List<String> getTravelPath() {
        return travelPath;
    }

    public void setTravelPath(List<String> travelPath) {
        this.travelPath = travelPath;
    }

    public LocalDateTime getDepartTime() {
        return departTime;
    }

    public void setDepartTime(LocalDateTime departTime) {
        this.departTime = departTime;
    }

    public LocalDateTime getArriveTime() {
        return arriveTime;
    }

    public void setArriveTime(LocalDateTime arriveTime) {
        this.arriveTime = arriveTime;
    }

    public double getDurationInHours() {
        return durationInHours;
    }

    public void setDurationInHours(double durationInHours) {
        this.durationInHours = durationInHours;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getOption() {
        return option;
    }

    public void setOption(int option) {
        this.option = option;
    }
}
