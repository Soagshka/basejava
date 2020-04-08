package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ListStorage extends AbstractStorage {
    protected static final Comparator<Resume> RESUME_COMPARATOR = (o1, o2) -> o1.getUuid().compareTo(o2.getUuid());

    List<Resume> resumeList = new ArrayList<>();

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
    protected Object getStorageSearchKey(String identifier) {
        for (int i = 0; i < resumeList.size(); i++) {
            if (identifier.equals(resumeList.get(i).getUuid())) {
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
    protected List<Resume> sort() {
        resumeList.sort(RESUME_COMPARATOR);
        return resumeList;
    }

    @Override
    protected String getKeyByUuid(String uuid) {
        return uuid;
    }
}
