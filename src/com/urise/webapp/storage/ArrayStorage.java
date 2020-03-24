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
            if (get(resume.getUuid()) != null) {
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
        int i = getStorageIndex(resume.getUuid());
        if (i != -1) {
            storage[i] = resume;
        } else {
            System.out.println("There is no such resume for update with this uuid = " + resume.getUuid() + " !");
        }
    }

    public Resume get(String uuid) {
        int i = getStorageIndex(uuid);
        if (i != -1) {
            return storage[i];
        } else {
            System.out.println("There is no such resume with this uuid = " + uuid + " !");
            return null;
        }
    }

    public void delete(String uuid) {
        int i = getStorageIndex(uuid);
        if (i != -1) {
            storage[i] = storage[size - 1];
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
