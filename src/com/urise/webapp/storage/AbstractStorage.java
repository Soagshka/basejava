package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {

    public abstract void updateResume(Resume resume, Object searchKey);

    public abstract void saveResume(Resume resume, Object searchKey);

    public abstract Resume getResume(Object searchKey);

    public abstract void deleteResume(Object searchKey);

    protected abstract Object getStorageSearchKey(String uuid);

    protected abstract boolean isExist(Object searchKey);

    @Override
    public void update(Resume resume) {
        Object searchKey = getExistIndex(resume.getUuid());
        updateResume(resume, searchKey);
    }

    @Override
    public void save(Resume resume) {
        Object searchKey = getNotExistIndex(resume.getUuid());
        saveResume(resume, searchKey);
    }

    @Override
    public Resume get(String uuid) {
        Object searchKey = getExistIndex(uuid);
        return getResume(searchKey);
    }

    @Override
    public void delete(String uuid) {
        Object searchKey = getExistIndex(uuid);
        deleteResume(searchKey);
    }

    private Object getExistIndex(String uuid) {
        Object searchKey = getStorageSearchKey(uuid);
        if (!isExist(searchKey)) {
            throw new NotExistStorageException(uuid);
        }
        return searchKey;
    }

    private Object getNotExistIndex(String uuid) {
        Object searchKey = getStorageSearchKey(uuid);
        if (isExist(searchKey)) {
            throw new ExistStorageException(uuid);
        }
        return searchKey;
    }
}
