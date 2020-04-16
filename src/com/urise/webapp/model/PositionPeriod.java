package com.urise.webapp.model;

import java.time.YearMonth;
import java.util.Objects;

public class PositionPeriod {
    private YearMonth dateStart;
    private YearMonth dateEnd;
    private String information;

    public PositionPeriod(YearMonth dateStart, YearMonth dateEnd, String information) {
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.information = information;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PositionPeriod that = (PositionPeriod) o;
        return Objects.equals(dateStart, that.dateStart) &&
                Objects.equals(dateEnd, that.dateEnd) &&
                Objects.equals(information, that.information);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dateStart, dateEnd, information);
    }

    @Override
    public String toString() {
        return "PositionPeriod{" +
                "dateStart=" + dateStart +
                ", dateEnd=" + dateEnd +
                ", information='" + information + '\'' +
                '}';
    }
}
