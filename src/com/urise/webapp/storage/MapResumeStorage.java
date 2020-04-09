package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class MapResumeStorage extends AbstractStorage {
    private Map<String, Resume> mapStorage = new LinkedHashMap<>();

    @Override
    public void updateResume(Resume resume, Object searchKey) {
        mapStorage.replace(resume.getUuid(), resume);
    }

    @Override
    public void saveResume(Resume resume, Object searchKey) {
        mapStorage.put(resume.getUuid(), resume);
    }

    @Override
    public Resume getResume(Object searchKey) {
        return (Resume) searchKey;
    }

    @Override
    public void deleteResume(Object searchKey) {
        mapStorage.remove(((Resume) searchKey).getUuid());
    }

    @Override
    protected Object getStorageSearchKey(String uuid) {
        return mapStorage.get(uuid);
    }

    @Override
    protected boolean isExist(Object searchKey) {
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
