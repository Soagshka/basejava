package com.urise.webapp.model;

import java.util.List;
import java.util.Objects;

public class ComplexTextSection extends AbstractSection{
    private List<Position> positionList;

    public ComplexTextSection(List<Position> positionList) {
        this.positionList = positionList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ComplexTextSection that = (ComplexTextSection) o;
        return Objects.equals(positionList, that.positionList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(positionList);
    }
}
