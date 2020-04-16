package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.util.Arrays;
import java.util.List;

public abstract class AbstractArrayStorage extends AbstractStorage<Integer> {

    protected abstract Integer getStorageSearchKey(String uuid);

    protected abstract void deleteResumeFromStorage(int index);

    protected abstract void saveResumeToStorage(Resume resume, int index);

    public int size() {
        return size;
    }

    public Resume getResume(Integer searchKey) {
        return storage[searchKey];
    }

    public void deleteResume(Integer searchKey) {
        deleteResumeFromStorage(searchKey);
        storage[size - 1] = null;
        size--;
    }

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public void saveResume(Resume resume, Integer searchKey) {
        if (size < storage.length) {
            saveResumeToStorage(resume, searchKey);
            size++;
        } else {
            throw new StorageException("Storage overflow", resume.getUuid());
        }
    }

    public void updateResume(Resume resume, Integer searchKey) {
        storage[searchKey] = resume;
    }

    @Override
    protected boolean isExist(Integer searchKey) {
        return searchKey >= 0;
    }

    @Override
    public List<Resume> getAll() {
        return Arrays.asList(Arrays.copyOf(storage, size));
    }
}
