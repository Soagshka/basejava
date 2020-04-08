package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.util.Arrays;
import java.util.Comparator;

public abstract class AbstractArrayStorage extends AbstractStorage {
    protected static final int STORAGE_LIMIT = 10_000;

    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    protected static final Comparator<Resume> RESUME_COMPARATOR = (o1, o2) -> o1.getUuid().compareTo(o2.getUuid());

    protected abstract Object getStorageSearchKey(String identifier);

    protected abstract void deleteResumeFromStorage(int index);

    protected abstract void saveResumeToStorage(Resume resume, int index);

    public int size() {
        return size;
    }

    public Resume getResume(Object searchKey) {
        return storage[(int) searchKey];
    }

    public void deleteResume(Object searchKey) {
        deleteResumeFromStorage((Integer) searchKey);
        storage[size - 1] = null;
        size--;
    }

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public void saveResume(Resume resume, Object searchKey) {
        if (size < storage.length) {
            saveResumeToStorage(resume, (Integer) searchKey);
            size++;
        } else {
            throw new StorageException("Storage overflow", resume.getUuid());
        }
    }

    public void updateResume(Resume resume, Object searchKey) {
        storage[(int) searchKey] = resume;
    }

    @Override
    protected boolean isExist(Object searchKey) {
        return (int) searchKey >= 0;
    }

    @Override
    protected String getKeyByUuid(String uuid) {
        return uuid;
    }
}
