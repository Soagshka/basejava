package com.urise.webapp.model;

import java.util.List;

public class TextSection extends Section{
    private List<String> information;

    public TextSection(List<String> information) {
        this.information = information;
    }

    public List<String> getInformation() {
        return information;
    }

    public void setInformation(List<String> information) {
        this.information = information;
    }

    @Override
    public String toString() {
        return "TextSection{" +
                "information=" + information +
                '}';
    }
}
