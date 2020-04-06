package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {

    public abstract void updateResume(Resume resume, Object index);

    public abstract void saveResume(Resume resume, Object index);

    public abstract Resume getResume(Object index);

    public abstract void deleteResume(Object index);

    protected abstract Object getStorageIndex(String uuid);

    protected abstract boolean isExist(Object index);

    @Override
    public void update(Resume resume) {
        Object index = getExistIndex(resume.getUuid());
        updateResume(resume, index);
    }

    @Override
    public void save(Resume resume) {
        Object index = getNotExistIndex(resume.getUuid());
        saveResume(resume, index);
    }

    @Override
    public Resume get(String uuid) {
        Object index = getExistIndex(uuid);
        return getResume(index);
    }

    @Override
    public void delete(String uuid) {
        Object index = getExistIndex(uuid);
        deleteResume(index);
    }

    private Object getExistIndex(String uuid) {
        Object index = getStorageIndex(uuid);
        if (!isExist(index)) {
            throw new NotExistStorageException(uuid);
        }
        return index;
    }

    private Object getNotExistIndex(String uuid) {
        Object index = getStorageIndex(uuid);
        if (isExist(index)) {
            throw new ExistStorageException(uuid);
        }
        return index;
    }
}
