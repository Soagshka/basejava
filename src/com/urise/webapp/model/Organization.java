package com.urise.webapp.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Organization {
    private String title;
    private List<Position> positionList = new ArrayList<>();
    private String description;

    public Organization(String title, String description) {
        this.title = title;
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Organization position = (Organization) o;
        return Objects.equals(title, position.title) &&
                Objects.equals(positionList, position.positionList) &&
                Objects.equals(description, position.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, positionList, description);
    }

    @Override
    public String toString() {
        return "Organization{" +
                "title='" + title + '\'' +
                ", positionList=" + positionList +
                ", description='" + description + '\'' +
                '}';
    }

    public List<Position> getPositionList() {
        return positionList;
    }
}
