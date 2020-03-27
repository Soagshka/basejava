package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage implements Storage {
    protected static final int STORAGE_LIMIT = 10_000;

    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    public int size() {
        return size;
    }

    public Resume get(String uuid) {
        int index = getStorageIndex(uuid);
        if (index >= 0) {
            return storage[index];
        }
        System.out.println("There is no such resume with this uuid = " + uuid + " !");
        return null;
    }

    protected abstract int getStorageIndex(String uuid);

    @Override
    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    @Override
    public void save(Resume resume) {
        if (size < storage.length) {
            int index = getStorageIndex(resume.getUuid());
            System.out.println(index);
            if (index >= 0) {
                System.out.println("Resume with this uuid = " + resume.getUuid() + " already exists!");
            } else {
                storage[size] = resume;
                size++;
            }
        } else {
            System.out.println("Resume overflow!");
        }
    }

    @Override
    public void update(Resume resume) {
        int index = getStorageIndex(resume.getUuid());
        if (index >= 0) {
            storage[index] = resume;
        } else {
            System.out.println("There is no such resume for update with this uuid = " + resume.getUuid() + " !");
        }
    }

    @Override
    public void delete(String uuid) {
        int index = getStorageIndex(uuid);
        if (index >= 0) {
            storage[index] = storage[size - 1];
            storage[size - 1] = null;
            size--;
        } else {
            System.out.println("There is no such resume for delete with this uuid = " + uuid + " !");
        }
    }

    @Override
    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }
}
