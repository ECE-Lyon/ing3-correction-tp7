package model;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Map;

public class Stats {

    private double averagePositivityRate;

    private List<Row> fullRows;

    private List<LocalDate> datesWithHighR;

    private List<LocalDate> highestIncidenceRate;

    private Map<Month, Double> occupancyByMonth;

    public Stats() {
    }

    public double getAveragePositivityRate() {
        return averagePositivityRate;
    }

    public void setAveragePositivityRate(double averagePositivityRate) {
        this.averagePositivityRate = averagePositivityRate;
    }

    public List<Row> getFullRows() {
        return fullRows;
    }

    public void setFullRows(List<Row> fullRows) {
        this.fullRows = fullRows;
    }

    public List<LocalDate> getDatesWithHighR() {
        return datesWithHighR;
    }

    public void setDatesWithHighR(List<LocalDate> datesWithHighR) {
        this.datesWithHighR = datesWithHighR;
    }

    public List<LocalDate> getHighestIncidenceRate() {
        return highestIncidenceRate;
    }

    public void setHighestIncidenceRate(List<LocalDate> highestIncidenceRate) {
        this.highestIncidenceRate = highestIncidenceRate;
    }

    public Map<Month, Double> getOccupancyByMonth() {
        return occupancyByMonth;
    }

    public void setOccupancyByMonth(Map<Month, Double> occupancyByMonth) {
        this.occupancyByMonth = occupancyByMonth;
    }
}
