package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage extends AbstractStorage {
    protected static final int STORAGE_LIMIT = 10_000;

    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    protected abstract int getStorageIndex(String uuid);

    protected abstract void deleteResumeFromStorage(int index);

    protected abstract void saveResumeToStorage(Resume resume, int index);

    public int size() {
        return size;
    }

    public Resume getResume(int index) {
        return storage[index];
    }

    public void deleteResume(int index) {
        deleteResumeFromStorage(index);
        storage[size - 1] = null;
        size--;
    }

    public void clearStorage() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public void saveResume(Resume resume, int index) {
        if (size < storage.length) {
            saveResumeToStorage(resume, index);
            size++;
        } else {
            throw new StorageException("Storage overflow", resume.getUuid());
        }
    }

    public void updateResume(Resume resume, int index) {
        storage[index] = resume;
    }

    public Resume[] getAllResumes() {
        return Arrays.copyOf(storage, size);
    }

    @Override
    protected int getStorageSize() {
        return size;
    }
}
