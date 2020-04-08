package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;
import java.util.List;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {

    public Object getStorageSearchKey(String identifier) {
        for (int i = 0; i < size; i++) {
            if (identifier.equals(storage[i].getUuid())) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected void deleteResumeFromStorage(int index) {
        storage[index] = storage[size - 1];
    }

    @Override
    protected void saveResumeToStorage(Resume resume, int index) {
        storage[size] = resume;
    }

    @Override
    public List<Resume> sort() {
        List<Resume> resumeList = Arrays.asList(Arrays.copyOf(storage, size));
        resumeList.sort(RESUME_COMPARATOR);
        return resumeList;
    }
}
