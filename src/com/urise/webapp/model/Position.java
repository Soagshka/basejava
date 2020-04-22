package com.urise.webapp.model;

import java.io.Serializable;
import java.time.YearMonth;
import java.util.Objects;

public class Position implements Serializable {
    private static final long serialVersionUID = 1L;

    private YearMonth dateStart;
    private YearMonth dateEnd;
    private String information;
    private String description;

    public Position(YearMonth dateStart, YearMonth dateEnd, String information, String description) {
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.information = information;
        if (description != null) {
            this.description = description;
        } else {
            this.description = "";
        }
    }

    public YearMonth getDateStart() {
        return dateStart;
    }

    public YearMonth getDateEnd() {
        return dateEnd;
    }

    public String getInformation() {
        return information;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return Objects.equals(dateStart, position.dateStart) &&
                Objects.equals(dateEnd, position.dateEnd) &&
                Objects.equals(information, position.information) &&
                Objects.equals(description, position.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dateStart, dateEnd, information, description);
    }

    @Override
    public String toString() {
        return "Position{" +
                "dateStart=" + dateStart +
                ", dateEnd=" + dateEnd +
                ", information='" + information + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
