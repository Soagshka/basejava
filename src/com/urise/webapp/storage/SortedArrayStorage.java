package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    protected Object getStorageSearchKey(String uuid) {
        Resume searchKey = new Resume(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }

    @Override
    protected void deleteResumeFromStorage(int index) {
        if (size - 1 - index >= 0) System.arraycopy(storage, index + 1, storage, index, size - 1 - index);
    }

    @Override
    protected void saveResumeToStorage(Resume resume, int index) {
        int idxToInsert = -index - 1;
        System.arraycopy(storage, idxToInsert, storage, idxToInsert + 1, size - idxToInsert);
        storage[idxToInsert] = resume;
    }
}
