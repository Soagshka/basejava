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
        if (size - 1 - index >= 0) System.arraycopy(storage, index + 1, storage, index, size - 1 - index);
    }

    @Override
    protected void saveResume(int index, Resume resume) {
        int i;
        for (i = size - 1; (i >= 0 && i >= (index * (-1) - 1)); i--) {
            storage[i + 1] = storage[i];
        }
        storage[i + 1] = resume;
    }
}
