package com.urise.webapp.model;

/**
 * Initial resume class
 */

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Resume {

    // Unique identifier
    private final String uuid;
    private String fullName;
    private Map<SectionType, Section> sectionMap = new HashMap<>();
    private Map<SectionType, List<Section>> complexSectionListMap = new HashMap<>();
    private Map<ContactType, Section> contactType = new HashMap<>();

    public Resume(String fullName) {
        this(UUID.randomUUID().toString(), fullName);
    }

    public Resume(String uuid, String fullName) {
        this.uuid = uuid;
        this.fullName = fullName;
    }

    public String getUuid() {
        return uuid;
    }

    public String getFullName() {
        return fullName;
    }

    public Map<SectionType, Section> getSectionMap() {
        return sectionMap;
    }

    public void setSectionMap(Map<SectionType, Section> sectionMap) {
        this.sectionMap = sectionMap;
    }

    public Map<SectionType, List<Section>> getComplexSectionListMap() {
        return complexSectionListMap;
    }

    public void setComplexSectionListMap(Map<SectionType, List<Section>> complexSectionListMap) {
        this.complexSectionListMap = complexSectionListMap;
    }

    public Map<ContactType, Section> getContactType() {
        return contactType;
    }

    public void setContactType(Map<ContactType, Section> contactType) {
        this.contactType = contactType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Resume resume = (Resume) o;

        return uuid.equals(resume.uuid);
    }

    @Override
    public int hashCode() {
        return uuid.hashCode();
    }

    @Override
    public String toString() {
        return uuid;
    }
}