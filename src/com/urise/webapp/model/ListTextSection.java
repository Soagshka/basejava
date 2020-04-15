package com.urise.webapp.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ListTextSection extends AbstractSection {
    private List<String> information;

    public ListTextSection(List<String> information) {
        this.information = new ArrayList<>(information);
    }

    @Override
    public String toString() {
        return "TextSection{" +
                "information=" + information +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ListTextSection that = (ListTextSection) o;
        return Objects.equals(information, that.information);
    }

    @Override
    public int hashCode() {
        return Objects.hash(information);
    }
}
