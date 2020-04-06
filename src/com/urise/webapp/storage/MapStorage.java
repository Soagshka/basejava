package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

public class MapStorage extends ArrayStorage {
    Map<String, Resume> resumeHashMap = new LinkedHashMap<>();

    @Override
    public void clear() {
        resumeHashMap.clear();
    }

    @Override
    public void updateResume(Resume resume, Object index) {
        resumeHashMap.put((String) index, resume);
    }

    @Override
    public void saveResume(Resume resume, Object index) {
        resumeHashMap.put(resume.getUuid(), resume);
    }

    @Override
    public Resume getResume(Object index) {
        return resumeHashMap.get(index);
    }

    @Override
    public void deleteResume(Object index) {
        resumeHashMap.remove(index);
    }

    @Override
    public Resume[] getAll() {
        System.out.println(Arrays.toString(resumeHashMap.values().toArray(new Resume[resumeHashMap.size()])));
        return resumeHashMap.values().toArray(new Resume[resumeHashMap.size()]);
    }

    @Override
    public int size() {
        return resumeHashMap.size();
    }

    @Override
    public Object getStorageIndex(String uuid) {
        for (Map.Entry<String, Resume> entry : resumeHashMap.entrySet()) {
            if (uuid.equals(entry.getKey())) {
                return entry.getKey();
            }
        }
        return null;
    }

    @Override
    protected boolean isExist(Object index) {
        if (index != null) {
            return true;
        }
        return false;
    }
}
