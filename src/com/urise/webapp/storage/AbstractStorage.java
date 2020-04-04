package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {

    public abstract void clearStorage();
    public abstract void updateResume(Resume resume, int index);
    public abstract void saveResume(Resume resume, int index);
    public abstract Resume getResume(int index);
    public abstract void deleteResume(int index);
    public abstract Resume[] getAllResumes();
    protected abstract int getStorageSize();
    protected abstract int getStorageIndex(String uuid);

    @Override
    public void clear() {
        clearStorage();
    }

    @Override
    public void update(Resume resume) {
        int index = getExistResume(resume.getUuid());
        updateResume(resume, index);
    }

    @Override
    public void save(Resume resume) {
        int index = getNotExistResume(resume.getUuid());
        saveResume(resume, index);
    }

    @Override
    public Resume get(String uuid) {
        int index = getExistResume(uuid);
        return getResume(index);
    }

    @Override
    public void delete(String uuid) {
        int index = getExistResume(uuid);
        deleteResume(index);
    }

    @Override
    public Resume[] getAll() {
        return getAllResumes();
    }

    @Override
    public int size() {
        return getStorageSize();
    }

    public int getExistResume(String uuid) {
        int index = getStorageIndex(uuid);
        if (index < 0) {
            throw new NotExistStorageException(uuid);
        }
        return index;
    }

    public int getNotExistResume(String uuid) {
        int index = getStorageIndex(uuid);
        if (index >= 0) {
            throw new ExistStorageException(uuid);
        }
        return index;
    }
}
