package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;
import java.util.List;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    protected Object getStorageSearchKey(String identifier) {
        Resume searchKey = new Resume(identifier, "Ivanov");
        return Arrays.binarySearch(storage, 0, size, searchKey, AbstractArrayStorage.RESUME_COMPARATOR);
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

    @Override
    public List<Resume> sort() {
        return Arrays.asList(Arrays.copyOf(storage, size));
    }
}
