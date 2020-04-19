package com.urise.webapp.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Organization {
    private String title;
    private String link;
    private List<Position> positionList = new ArrayList<>();
    private String description;

    public Organization(String title, String link, String description) {
        this.title = title;
        this.link = link;
        this.description = description;
    }

    public List<Position> getPositionList() {
        return positionList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Organization that = (Organization) o;
        return Objects.equals(title, that.title) &&
                Objects.equals(link, that.link) &&
                Objects.equals(positionList, that.positionList) &&
                Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, link, positionList, description);
    }

    @Override
    public String toString() {
        return "Organization{" +
                "title='" + title + '\'' +
                ", link='" + link + '\'' +
                ", positionList=" + positionList +
                ", description='" + description + '\'' +
                '}';
    }
}
