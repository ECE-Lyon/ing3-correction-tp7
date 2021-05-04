package model;

import java.time.LocalDate;

public class Row {
    private final LocalDate extractDate;
    private final Double incidenceRate;
    private final Double r;
    private final Double occupancyRate;
    private final Double positivityRate;

    public Row(LocalDate extractDate, Double incidenceRate, Double r, Double occupancyRate, Double positivityRate) {
        this.extractDate = extractDate;
        this.incidenceRate = incidenceRate;
        this.r = r;
        this.occupancyRate = occupancyRate;
        this.positivityRate = positivityRate;
    }

    public LocalDate getExtractDate() {
        return extractDate;
    }

    public Double getIncidenceRate() {
        return incidenceRate;
    }

    public Double getR() {
        return r;
    }

    public Double getOccupancyRate() {
        return occupancyRate;
    }

    public Double getPositivityRate() {
        return positivityRate;
    }
}
