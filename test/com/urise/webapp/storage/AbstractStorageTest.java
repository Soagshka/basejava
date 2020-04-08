package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractStorageTest {
    private Storage storage;

    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";
    private static final String UUID_4 = "uuid4";
    private static final String UUID_5 = "uuid5";

    public AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() throws Exception {
        storage.clear();
        storage.save(new Resume(UUID_1, "Ivanov"));
        storage.save(new Resume(UUID_2, "Sidorov"));
        storage.save(new Resume(UUID_3, "Petrov"));
    }

    @Test
    public void size() throws Exception {
        Assert.assertEquals(3, storage.size());
    }

    @Test
    public void clear() throws Exception {
        storage.clear();
        Assert.assertEquals(0, storage.size());
    }

    @Test()
    public void update() throws Exception {
        Resume resume = new Resume(UUID_3, "Petrov");
        storage.update(resume);
        Assert.assertEquals(resume, storage.get(UUID_3));
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExists() {
        storage.update(new Resume("uuid10", "Markov"));
    }

    @Test
    public void save() throws Exception {
        Resume resume = new Resume(UUID_4, "Kar");
        storage.save(resume);
        Assert.assertEquals(4, storage.size());
        Assert.assertEquals(resume, storage.get(UUID_4));
    }

    @Test(expected = ExistStorageException.class)
    public void saveExists() {
        storage.save(new Resume(UUID_1, "Ivanov"));
    }

    @Test(expected = NotExistStorageException.class)
    public void delete() throws Exception {
        storage.delete(UUID_2);
        Assert.assertEquals(2, storage.size());
        storage.get(UUID_2);
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExists() {
        storage.delete("uuid10");
    }

    @Test
    public void get() throws Exception {
        Resume resume = new Resume(UUID_5, "Kar");
        storage.save(resume);
        Assert.assertEquals(resume, storage.get(UUID_5));
    }

    @Test
    public void getAll() throws Exception {
        List<Resume> testListStorage = new ArrayList<Resume>();
        testListStorage.add(new Resume(UUID_1, "Ivanov"));
        testListStorage.add(new Resume(UUID_2, "Sidorov"));
        testListStorage.add(new Resume(UUID_3, "Petrov"));
        for (int i = 0; i < 3; i++) {
            Assert.assertEquals(testListStorage.get(i), storage.getAllSorted().get(i));
        }
    }

    @Test
    public void getAllSorted() throws Exception {
        List<Resume> testListStorage = new ArrayList<Resume>();
        testListStorage.add(new Resume(UUID_1, "Ivanov"));
        testListStorage.add(new Resume(UUID_3, "Petrov"));
        testListStorage.add(new Resume(UUID_2, "Sidorov"));
        Assert.assertNotEquals(testListStorage, storage.getAllSorted());
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() throws Exception {
        storage.get("dummy");
    }
}