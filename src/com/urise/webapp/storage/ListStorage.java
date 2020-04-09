package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage {

    private List<Resume> resumeList = new ArrayList<>();

    @Override
    public void clear() {
        resumeList.clear();
    }

    @Override
    public void updateResume(Resume resume, Object searchKey) {
        resumeList.set((Integer) searchKey, resume);
    }

    @Override
    public void saveResume(Resume resume, Object searchKey) {
        resumeList.add(resume);
    }

    @Override
    public Resume getResume(Object searchKey) {
        return resumeList.get((Integer) searchKey);
    }

    @Override
    public void deleteResume(Object searchKey) {
        resumeList.remove((int) searchKey);
    }

    @Override
    public int size() {
        return resumeList.size();
    }

    @Override
    protected Object getStorageSearchKey(String uuid) {
        for (int i = 0; i < resumeList.size(); i++) {
            if (uuid.equals(resumeList.get(i).getUuid())) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected boolean isExist(Object searchKey) {
        return (int) searchKey != -1;
    }

    @Override
    protected List<Resume> getAll() {
        return resumeList;
    }
}
