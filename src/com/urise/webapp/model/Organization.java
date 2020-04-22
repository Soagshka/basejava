package com.urise.webapp.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Organization implements Serializable {
    private static final long serialVersionUID = 1L;

    private String title;
    private String link;
    private List<Position> positionList = new ArrayList<>();

    public Organization(String title, String link) {
        this.title = title;
        if (link != null) {
            this.link = link;
        } else {
            this.link = "";
        }
    }

    public List<Position> getPositionList() {
        return positionList;
    }

    public String getTitle() {
        return title;
    }

    public String getLink() {
        return link;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Organization that = (Organization) o;
        return Objects.equals(title, that.title) &&
                Objects.equals(link, that.link) &&
                Objects.equals(positionList, that.positionList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, link, positionList);
    }

    @Override
    public String toString() {
        return "Organization{" +
                "title='" + title + '\'' +
                ", link='" + link + '\'' +
                ", positionList=" + positionList +
                '}';
    }
}
