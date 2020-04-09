package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class MapUuidStorage extends AbstractStorage {
    private Map<String, Resume> mapStorage = new LinkedHashMap<>();

    @Override
    public void updateResume(Resume resume, Object searchKey) {
        mapStorage.replace((String) searchKey, resume);
    }

    @Override
    public void saveResume(Resume resume, Object searchKey) {
        mapStorage.put((String) searchKey, resume);
    }

    @Override
    public Resume getResume(Object searchKey) {
        return mapStorage.get(searchKey);
    }

    @Override
    public void deleteResume(Object searchKey) {
        mapStorage.remove(searchKey);
    }

    @Override
    protected Object getStorageSearchKey(String uuid) {
        return uuid;
    }

    @Override
    protected boolean isExist(Object searchKey) {
        return mapStorage.containsKey(searchKey);
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
