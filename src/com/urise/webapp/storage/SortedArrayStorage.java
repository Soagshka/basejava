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
    public void delete(String uuid) {
        for (int j = 0; j < size; j++) {
            if (storage[j].getUuid().equals(uuid)) {
                for (int k = 0; k < size - 1; k++) {
                    storage[k] = storage[k + 1];
                }
                size--;
                break;
            }
        }
    }
}
