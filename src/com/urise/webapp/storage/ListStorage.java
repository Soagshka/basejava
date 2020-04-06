package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage {
    List<Resume> resumeList = new ArrayList<>();

    @Override
    public void clear() {
        resumeList.clear();
    }

    @Override
    public void updateResume(Resume resume, Object index) {
        resumeList.set((Integer) index, resume);
    }

    @Override
    public void saveResume(Resume resume, Object index) {
        resumeList.add(resume);
    }

    @Override
    public Resume getResume(Object index) {
        return resumeList.get((Integer) index);
    }

    @Override
    public void deleteResume(Object index) {
        resumeList.remove((int) index);
    }

    @Override
    public Resume[] getAll() {
        return resumeList.toArray(new Resume[resumeList.size()]);
    }

    @Override
    public int size() {
        return resumeList.size();
    }

    @Override
    protected Object getStorageIndex(String uuid) {
        for (int i = 0; i < resumeList.size(); i++) {
            if (uuid.equals(resumeList.get(i).getUuid())) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected boolean isExist(Object index) {
        if ((int) index != -1) {
            return true;
        }
        return false;
    }
}
