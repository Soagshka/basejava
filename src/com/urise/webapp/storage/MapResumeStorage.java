package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class MapResumeStorage extends AbstractStorage<Resume> {
    private Map<String, Resume> mapStorage = new LinkedHashMap<>();

    @Override
    public void updateResume(Resume resume, Resume searchKey) {
        mapStorage.replace(resume.getUuid(), resume);
    }

    @Override
    public void saveResume(Resume resume, Resume searchKey) {
        mapStorage.put(resume.getUuid(), resume);
    }

    @Override
    public Resume getResume(Resume searchKey) {
        return (Resume) searchKey;
    }

    @Override
    public void deleteResume(Resume searchKey) {
        mapStorage.remove(((Resume) searchKey).getUuid());
    }

    @Override
    protected Resume getStorageSearchKey(String uuid) {
        return mapStorage.get(uuid);
    }

    @Override
    protected boolean isExist(Resume searchKey) {
        return searchKey != null;
    }

    @Override
    protected List<Resume> getAll() {
        return new ArrayList<>(mapStorage.values());
    }

    @Override
    public void clear() {
        mapStorage.clear();
    }

    @Override
    public int size() {
        return mapStorage.size();
    }
}
