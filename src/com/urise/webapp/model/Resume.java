package com.urise.webapp.model;

/**
 * Initial resume class
 */

import java.util.*;

public class Resume {

    // Unique identifier
    private final String uuid;
    private String fullName;
    private Map<SectionType, AbstractSection> sectionMap = new EnumMap<SectionType, AbstractSection>(SectionType.class);
    private Map<ContactType, AbstractSection> contactType = new EnumMap<ContactType, AbstractSection>(ContactType.class);

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

    public Map<SectionType, AbstractSection> getSectionMap() {
        return sectionMap;
    }

    public void setSectionMap(Map<SectionType, AbstractSection> sectionMap) {
        this.sectionMap = sectionMap;
    }

    public Map<ContactType, AbstractSection> getContactType() {
        return contactType;
    }

    public void setContactType(Map<ContactType, AbstractSection> contactType) {
        this.contactType = contactType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Resume resume = (Resume) o;
        return Objects.equals(uuid, resume.uuid) &&
                Objects.equals(fullName, resume.fullName) &&
                Objects.equals(sectionMap, resume.sectionMap) &&
                Objects.equals(contactType, resume.contactType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, fullName, sectionMap, contactType);
    }

    @Override
    public String toString() {
        return uuid;
    }
}