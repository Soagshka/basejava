package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.*;

public abstract class MapStorage extends AbstractStorage {
    protected abstract Comparator<Resume> compare();

    Map<String, Resume> mapStorage = new LinkedHashMap<>();

    @Override
    public void clear() {
        mapStorage.clear();
    }

    @Override
    public void updateResume(Resume resume, Object searchKey) {
        mapStorage.put((String) searchKey, resume);
    }

    @Override
    public abstract void saveResume(Resume resume, Object searchKey);

    @Override
    public Resume getResume(Object searchKey) {
        return mapStorage.get(searchKey);
    }

    @Override
    public void deleteResume(Object searchKey) {
        mapStorage.remove(searchKey);
    }

    @Override
    public int size() {
        return mapStorage.size();
    }

    @Override
    public Object getStorageSearchKey(String identifier) {
        if (isExist(identifier)) {
            return identifier;
        }
        return null;
    }

    @Override
    protected boolean isExist(Object searchKey) {
        return mapStorage.containsKey(searchKey);
    }

    @Override
    protected List<Resume> sort() {
        List<Resume> resumeList = new ArrayList<>(mapStorage.values());
        resumeList.sort(compare());
        return new ArrayList<>(mapStorage.values());
    }
}
