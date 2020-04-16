package com.urise.webapp.model;

import java.time.YearMonth;
import java.util.List;
import java.util.Objects;

public class Position extends AbstractSection {
    private String title;
    private YearMonth dateStart;
    private YearMonth dateEnd;
    private List<String> information;
    private String description;

    public Position(String title, YearMonth dateStart, YearMonth dateEnd, List<String> information, String description) {
        this.title = title;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.information = information;
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return Objects.equals(title, position.title) &&
                Objects.equals(dateStart, position.dateStart) &&
                Objects.equals(dateEnd, position.dateEnd) &&
                Objects.equals(information, position.information) &&
                Objects.equals(description, position.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, dateStart, dateEnd, information, description);
    }

    @Override
    public String toString() {
        return "Position{" +
                "title='" + title + '\'' +
                ", dateStart=" + dateStart +
                ", dateEnd=" + dateEnd +
                ", information=" + information +
                ", description='" + description + '\'' +
                '}';
    }
}
