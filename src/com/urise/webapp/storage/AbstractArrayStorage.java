package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage extends AbstractStorage {
    protected static final int STORAGE_LIMIT = 10_000;

    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    protected abstract Object getStorageIndex(String uuid);

    protected abstract void deleteResumeFromStorage(int index);

    protected abstract void saveResumeToStorage(Resume resume, int index);

    public int size() {
        return size;
    }

    public Resume getResume(Object index) {
        return storage[(int) index];
    }

    public void deleteResume(Object index) {
        deleteResumeFromStorage((Integer) index);
        storage[size - 1] = null;
        size--;
    }

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public void saveResume(Resume resume, Object index) {
        if (size < storage.length) {
            saveResumeToStorage(resume, (Integer) index);
            size++;
        } else {
            throw new StorageException("Storage overflow", resume.getUuid());
        }
    }

    public void updateResume(Resume resume, Object index) {
        storage[(int) index] = resume;
    }

    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    @Override
    protected boolean isExist(Object index) {
        if ((int) index >= 0) {
            return true;
        }
        return false;
    }
}
