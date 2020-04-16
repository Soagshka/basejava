package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class MapUuidStorage extends AbstractStorage<String> {
    private Map<String, Resume> mapStorage = new LinkedHashMap<>();

    @Override
    public void updateResume(Resume resume, String searchKey) {
        mapStorage.replace(searchKey, resume);
    }

    @Override
    public void saveResume(Resume resume, String searchKey) {
        mapStorage.put(searchKey, resume);
    }

    @Override
    public Resume getResume(String searchKey) {
        return mapStorage.get(searchKey);
    }

    @Override
    public void deleteResume(String searchKey) {
        mapStorage.remove(searchKey);
    }

    @Override
    protected String getStorageSearchKey(String uuid) {
        return uuid;
    }

    @Override
    protected boolean isExist(String searchKey) {
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
