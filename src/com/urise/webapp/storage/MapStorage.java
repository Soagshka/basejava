package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

public class MapStorage extends ArrayStorage {
    Map<String, Resume> resumeLinkedHashMap = new LinkedHashMap<>();

    @Override
    public void clear() {
        resumeLinkedHashMap.clear();
    }

    @Override
    public void updateResume(Resume resume, Object searchKey) {
        resumeLinkedHashMap.put((String) searchKey, resume);
    }

    @Override
    public void saveResume(Resume resume, Object searchKey) {
        resumeLinkedHashMap.put(resume.getUuid(), resume);
    }

    @Override
    public Resume getResume(Object searchKey) {
        return resumeLinkedHashMap.get(searchKey);
    }

    @Override
    public void deleteResume(Object searchKey) {
        resumeLinkedHashMap.remove(searchKey);
    }

    @Override
    public Resume[] getAll() {
        return resumeLinkedHashMap.values().toArray(new Resume[resumeLinkedHashMap.size()]);
    }

    @Override
    public int size() {
        return resumeLinkedHashMap.size();
    }

    @Override
    public Object getStorageSearchKey(String uuid) {
        if (isExist(uuid)) {
            return uuid;
        }
        return null;
    }

    @Override
    protected boolean isExist(Object searchKey) {
        return resumeLinkedHashMap.containsKey(searchKey);
    }
}
