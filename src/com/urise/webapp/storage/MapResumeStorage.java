package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Comparator;
import java.util.Map;

public class MapResumeStorage extends MapStorage {
    protected static final Comparator<Resume> RESUME_COMPARATOR = Comparator.comparing(Resume::getFullName).thenComparing(Resume::getUuid);

    @Override
    protected Comparator<Resume> compare() {
        return RESUME_COMPARATOR;
    }

    @Override
    public void saveResume(Resume resume, Object searchKey) {
        mapStorage.put(resume.getFullName(), resume);
    }

    @Override
    protected String getKeyByUuid(String uuid) {
        for (Map.Entry<String, Resume> entry : mapStorage.entrySet()) {
            if (uuid.equals(entry.getValue().getUuid())) return entry.getValue().getFullName();
        }
        return null;
    }
}
