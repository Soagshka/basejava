package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {

    public int getStorageIndex(String uuid) {
        for (int i = 0; i < size; i++) {
            if (uuid.equals(storage[i].getUuid())) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected void deleteResume(int index) {
        storage[index] = storage[size - 1];
    }

    @Override
    protected void saveResume(int index, Resume resume) {
        storage[size] = resume;
    }
}
