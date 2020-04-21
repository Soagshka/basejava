package com.urise.webapp.model;

import java.util.Objects;

public class SimpleTextSection extends AbstractSection {

    private String information;

    public SimpleTextSection(String information) {
        this.information = information;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SimpleTextSection that = (SimpleTextSection) o;
        return Objects.equals(information, that.information);
    }

    @Override
    public int hashCode() {
        return Objects.hash(information);
    }

    @Override
    public String toString() {
        return "SimpleTextSection{" +
                "information='" + information + '\'' +
                '}';
    }

    public String getInformation() {
        return information;
    }
}
