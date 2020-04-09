package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

import java.util.Comparator;
import java.util.List;

public abstract class AbstractStorage implements Storage {
    protected Resume[] storage = new Resume[STORAGE_LIMIT];

    protected static final int STORAGE_LIMIT = 10_000;

    protected int size = 0;

    protected static final Comparator<Resume> RESUME_COMPARATOR = Comparator.comparing(Resume::getUuid).thenComparing(Resume::getFullName);

    public abstract void updateResume(Resume resume, Object searchKey);

    public abstract void saveResume(Resume resume, Object searchKey);

    public abstract Resume getResume(Object searchKey);

    public abstract void deleteResume(Object searchKey);

    protected abstract Object getStorageSearchKey(String uuid);

    protected abstract boolean isExist(Object searchKey);

    protected abstract List<Resume> getAll();

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
        List<Resume> resumeList = getAll();
        resumeList.sort(RESUME_COMPARATOR);
        return getAll();
    }

    @Override
    public void update(Resume resume) {
        Object searchKey = getExistSearchKey(resume.getUuid());
        updateResume(resume, searchKey);
    }

    @Override
    public void save(Resume resume) {
        Object searchKey = getNotSearchKey(resume.getUuid());
        saveResume(resume, searchKey);
    }

    @Override
    public Resume get(String uuid) {
        Object searchKey = getExistSearchKey(uuid);
        return getResume(searchKey);
    }

    @Override
    public void delete(String uuid) {
        Object searchKey = getExistSearchKey(uuid);
        deleteResume(searchKey);
    }
}
