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
    public void updateResume(Resume resume, int index) {
        resumeList.set(index, resume);
    }

    @Override
    public void saveResume(Resume resume, int index) {
        resumeList.add(resume);
    }

    @Override
    public Resume getResume(int index) {
        return resumeList.get(index);
    }

    @Override
    public void deleteResume(int index) {
        resumeList.remove(index);
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
    protected int getStorageIndex(String uuid) {
        for (int i = 0; i < resumeList.size(); i++) {
            if (uuid.equals(resumeList.get(i).getUuid())) {
                return i;
            }
        }
        return -1;
    }
}
