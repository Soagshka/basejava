package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {

    public abstract void updateResume(Resume resume, int index);
    public abstract void saveResume(Resume resume, int index);
    public abstract Resume getResume(int index);
    public abstract void deleteResume(int index);
    protected abstract int getStorageIndex(String uuid);

    @Override
    public void update(Resume resume) {
        int index = getExistIndex(resume.getUuid());
        updateResume(resume, index);
    }

    @Override
    public void save(Resume resume) {
        int index = getNotExistIndex(resume.getUuid());
        saveResume(resume, index);
    }

    @Override
    public Resume get(String uuid) {
        int index = getExistIndex(uuid);
        return getResume(index);
    }

    @Override
    public void delete(String uuid) {
        int index = getExistIndex(uuid);
        deleteResume(index);
    }

    public int getExistIndex(String uuid) {
        int index = getStorageIndex(uuid);
        if (index < 0) {
            throw new NotExistStorageException(uuid);
        }
        return index;
    }

    public int getNotExistIndex(String uuid) {
        int index = getStorageIndex(uuid);
        if (index >= 0) {
            throw new ExistStorageException(uuid);
        }
        return index;
    }
}
