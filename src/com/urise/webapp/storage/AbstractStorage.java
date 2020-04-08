package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

import java.util.List;

public abstract class AbstractStorage implements Storage {

    public abstract void updateResume(Resume resume, Object searchKey);

    public abstract void saveResume(Resume resume, Object searchKey);

    public abstract Resume getResume(Object searchKey);

    public abstract void deleteResume(Object searchKey);

    protected abstract Object getStorageSearchKey(String identifier);

    protected abstract boolean isExist(Object searchKey);

    protected abstract List<Resume> sort();

    protected abstract String getKeyByUuid(String uuid);

    @Override
    public void update(Resume resume) {
        Object searchKey = getExistSearchKey(getKeyByUuid(resume.getUuid()));
        updateResume(resume, searchKey);
    }

    @Override
    public void save(Resume resume) {
        Object searchKey = getNotSearchKey(getKeyByUuid(resume.getUuid()));
        saveResume(resume, searchKey);
    }

    @Override
    public Resume get(String uuid) {
        Object searchKey = getExistSearchKey(getKeyByUuid(uuid));
        return getResume(searchKey);
    }

    @Override
    public void delete(String uuid) {
        Object searchKey = getExistSearchKey(getKeyByUuid(uuid));
        deleteResume(searchKey);
    }

    private Object getExistSearchKey(String uuid) {
        Object searchKey = getStorageSearchKey(uuid);
        if (!isExist(searchKey)) {
            throw new NotExistStorageException(uuid);
        }
        return searchKey;
    }

    private Object getNotSearchKey(String uuid) {
        Object searchKey = getStorageSearchKey(uuid);
        if (isExist(searchKey)) {
            throw new ExistStorageException(uuid);
        }
        return searchKey;
    }

    public List<Resume> getAllSorted() {
        return sort();
    }
}
