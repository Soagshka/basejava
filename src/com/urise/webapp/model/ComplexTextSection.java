package com.urise.webapp.model;

import java.time.YearMonth;

public class ComplexTextSection extends Section{
    private String title;
    private YearMonth dateStart;
    private YearMonth dateEnd;
    private String information;
    private String description;

    public ComplexTextSection(String title, YearMonth dateStart, YearMonth dateEnd, String information, String description) {
        this.title = title;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.information = information;
        this.description = description;
    }
}
