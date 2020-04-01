package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    protected int getStorageIndex(String uuid) {
        Resume searchKey = new Resume();
        searchKey.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }

    @Override
    protected void deleteResume(int index) {
        for (int k = index; k < size - 1; k++) {
            storage[k] = storage[k + 1];
        }
    }

    @Override
    protected void saveResume(Resume resume) {
        int i;
        for (i = size - 1; (i >= 0 && i >= (getStorageIndex(resume.getUuid()) * (-1) - 1)); i--) {
            storage[i + 1] = storage[i];
        }
        storage[i + 1] = resume;
    }
}
