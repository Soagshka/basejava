package com.urise.webapp.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class OrganizationSection extends AbstractSection {
    private List<Organization> positionList;

    public OrganizationSection(List<Organization> positionList) {
        this.positionList = new ArrayList<>(positionList);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrganizationSection that = (OrganizationSection) o;
        return Objects.equals(positionList, that.positionList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(positionList);
    }

    @Override
    public String toString() {
        return "ComplexTextSection{" +
                "positionList=" + positionList +
                '}';
    }
}
