package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage implements Storage {
    protected static final int STORAGE_LIMIT = 10_000;

    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    public int size() {
        return size;
    }

    public Resume get(String uuid) {
        int index = getStorageIndex(uuid);
        if (index >= 0) {
            return storage[index];
        }
        throw new NotExistStorageException(uuid);
    }

    protected abstract int getStorageIndex(String uuid);

    protected abstract void deleteResume(int index);

    protected abstract void saveResume(int index, Resume resume);

    @Override
    public void delete(String uuid) {
        int index = getStorageIndex(uuid);
        if (index >= 0) {
            deleteResume(index);
            storage[size - 1] = null;
            size--;
        } else {
            throw new NotExistStorageException(uuid);
        }
    }

    @Override
    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    @Override
    public void save(Resume resume) {
        if (size < storage.length) {
            int index = getStorageIndex(resume.getUuid());
            if (index >= 0) {
                throw new ExistStorageException(resume.getUuid());
            } else {
                saveResume(index, resume);
                size++;
            }
        } else {
            throw new StorageException("Storage overflow", resume.getUuid());
        }
    }

    @Override
    public void update(Resume resume) {
        int index = getStorageIndex(resume.getUuid());
        if (index >= 0) {
            storage[index] = resume;
        } else {
            throw new NotExistStorageException(resume.getUuid());
        }
    }

    @Override
    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }
}
