package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Comparator;

public class MapUuidStorage extends MapStorage {
    protected static final Comparator<Resume> RESUME_COMPARATOR = (o1, o2) -> o1.getUuid().compareTo(o2.getUuid());

    @Override
    protected Comparator<Resume> compare() {
        return RESUME_COMPARATOR;
    }

    @Override
    public void saveResume(Resume resume, Object searchKey) {
        mapStorage.put(resume.getUuid(), resume);
    }

    @Override
    protected String getKeyByUuid(String uuid) {
        return uuid;
    }

}
