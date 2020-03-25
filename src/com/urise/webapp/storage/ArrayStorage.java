package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10_000];
    int size = 0;

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public void save(Resume resume) {
        if (size < storage.length) {
            int index = getStorageIndex(resume.getUuid());
            if (index != -1) {
                System.out.println("Resume with this uuid = " + resume.getUuid() + " already exists!");
            } else {
                storage[size] = resume;
                size++;
            }
        } else {
            System.out.println("Resume overflow!");
        }
    }

    public void update(Resume resume) {
        int index = getStorageIndex(resume.getUuid());
        if (index != -1) {
            storage[index] = resume;
        } else {
            System.out.println("There is no such resume for update with this uuid = " + resume.getUuid() + " !");
        }
    }

    public Resume get(String uuid) {
        int index = getStorageIndex(uuid);
        if (index != -1) {
            return storage[index];
        }
        System.out.println("There is no such resume with this uuid = " + uuid + " !");
        return null;
    }

    public void delete(String uuid) {
        int index = getStorageIndex(uuid);
        if (index != -1) {
            storage[index] = storage[size - 1];
            storage[size - 1] = null;
            size--;
        } else {
            System.out.println("There is no such resume for delete with this uuid = " + uuid + " !");
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    public int size() {
        return size;
    }

    public int getStorageIndex(String uuid) {
        for (int i = 0; i < size; i++) {
            if (uuid.equals(storage[i].getUuid())) {
                return i;
            }
        }
        return -1;
    }
}
