package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage<Integer> {

    private List<Resume> resumeList = new ArrayList<>();

    @Override
    public void clear() {
        resumeList.clear();
    }

    @Override
    public void updateResume(Resume resume, Integer searchKey) {
        resumeList.set(searchKey, resume);
    }

    @Override
    public void saveResume(Resume resume, Integer searchKey) {
        resumeList.add(resume);
    }

    @Override
    public Resume getResume(Integer searchKey) {
        return resumeList.get(searchKey);
    }

    @Override
    public void deleteResume(Integer searchKey) {
        resumeList.remove((int) searchKey);
    }

    @Override
    public int size() {
        return resumeList.size();
    }

    @Override
    protected Integer getStorageSearchKey(String uuid) {
        for (int i = 0; i < resumeList.size(); i++) {
            if (uuid.equals(resumeList.get(i).getUuid())) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected boolean isExist(Integer searchKey) {
        return searchKey != -1;
    }

    @Override
    protected List<Resume> getAll() {
        return new ArrayList<>(resumeList);
    }
}
