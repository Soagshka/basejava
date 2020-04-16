package com.urise.webapp.model;

import java.util.List;
import java.util.Objects;

public class Position {
    private String title;
    private List<PositionPeriod> positionPeriodList;
    private String description;

    public Position(String title, List<PositionPeriod> positionPeriodList, String description) {
        this.title = title;
        this.positionPeriodList = positionPeriodList;
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return Objects.equals(title, position.title) &&
                Objects.equals(positionPeriodList, position.positionPeriodList) &&
                Objects.equals(description, position.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, positionPeriodList, description);
    }

    @Override
    public String toString() {
        return "Position{" +
                "title='" + title + '\'' +
                ", positionPeriodList=" + positionPeriodList +
                ", description='" + description + '\'' +
                '}';
    }
}
