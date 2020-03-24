package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];
    int size = 0;

    public void clear() {
        Arrays.fill(storage, 0, size + 1, null);
        size = 0;
    }

    public void save(Resume r) {
        if (size < 1000) {
            if (get(r.getUuid()) != null) {
                System.out.println("Resume already exists!");
            } else {
                storage[size] = r;
                size++;
            }
        } else {
            System.out.println("Resume overflow!");
        }
    }

    public void update(Resume oldResume, Resume newResume) {
        if (get(oldResume.getUuid()) !=null) {
            storage[getStorageIndex(oldResume.getUuid())] = newResume;
        } else {
            System.out.println("There is no such resume for update!");
        }
    }

    public Resume get(String uuid) {
        for (int j = 0; j < size; j++) {
            if (uuid.equals(storage[j].getUuid())) {
                return storage[j];
            }
        }
        System.out.println("There is no such resume!");
        return null;
    }

    public void delete(String uuid) {
        if (get(uuid) != null) {
            storage[getStorageIndex(uuid)] = storage[size - 1];
            storage[size - 1] = null;
            size--;
        } else {
            System.out.println("There is no such resume for delete");
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

    public Integer getStorageIndex(String uuid) {
        for (int i = 0; i < size; i++) {
            if (uuid.equals(storage[i].getUuid())) {
                return i;
            }
        }
        return null;
    }
}
