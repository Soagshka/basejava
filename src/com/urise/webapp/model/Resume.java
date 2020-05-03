package com.urise.webapp.model;

/**
 * Initial resume class
 */

import java.io.Serializable;
import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class Resume implements Comparable<Resume>, Serializable {
    private static final long serialVersionUID = 1L;

    // Unique identifier
    private final String uuid;
    private String fullName;
    private Map<ContactType, String> contactMap = new EnumMap<>(ContactType.class);
    private Map<SectionType, AbstractSection> sectionMap = new EnumMap<>(SectionType.class);

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

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Map<SectionType, AbstractSection> getSectionMap() {
        return sectionMap;
    }

    public void setSectionMap(Map<SectionType, AbstractSection> sectionMap) {
        this.sectionMap = sectionMap;
    }

    public Map<ContactType, String> getContactMap() {
        return contactMap;
    }

    public void setContactMap(Map<ContactType, String> contactMap) {
        this.contactMap = contactMap;
    }

    public void addContact(ContactType type, String value) {
        contactMap.put(type, value);
    }

    public void addSection(SectionType type, AbstractSection value) {
        sectionMap.put(type, value);
    }

    public String getContact(ContactType type) {
        return contactMap.get(type);
    }

    public AbstractSection getSection(SectionType type) {
        return sectionMap.get(type);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Resume resume = (Resume) o;
        return Objects.equals(uuid, resume.uuid) &&
                Objects.equals(fullName, resume.fullName)
                && Objects.equals(sectionMap, resume.sectionMap)
                && Objects.equals(contactMap, resume.contactMap);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, fullName, sectionMap, contactMap);
    }

    @Override
    public String toString() {
        return uuid;
    }

    @Override
    public int compareTo(Resume o) {
        int cmp = fullName.compareTo(o.fullName);
        return cmp != 0 ? cmp : uuid.compareTo(o.uuid);
    }
}