package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public abstract class AbstractStorageTest {
    private Storage storage;

    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";
    private static final String UUID_4 = "uuid4";
    private static final String UUID_5 = "uuid5";

    private static final Resume RESUME_1 = new Resume(UUID_1, "Ivanov");
    private static final Resume RESUME_2 = new Resume(UUID_2, "Sidorov");
    private static final Resume RESUME_3 = new Resume(UUID_3, "Petrov");
    private static final Resume RESUME_4 = new Resume(UUID_4, "Markov");
    private static final Resume RESUME_5 = new Resume(UUID_5, "Kar");

    public AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() throws Exception {
        storage.clear();
        storage.save(RESUME_1);
        storage.save(RESUME_2);
        storage.save(RESUME_3);
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
        Resume resume = new Resume(UUID_3, "Karpov");
        storage.update(resume);
        Assert.assertEquals(resume, storage.get(UUID_3));
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExists() {
        storage.update(RESUME_4);
    }

    @Test
    public void save() throws Exception {
        storage.save(RESUME_4);
        Assert.assertEquals(4, storage.size());
        Assert.assertEquals(RESUME_4, storage.get(UUID_4));
    }

    @Test(expected = ExistStorageException.class)
    public void saveExists() {
        storage.save(RESUME_1);
    }

    @Test(expected = NotExistStorageException.class)
    public void delete() throws Exception {
        storage.delete(UUID_2);
        Assert.assertEquals(2, storage.size());
        storage.get(UUID_2);
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExists() {
        storage.delete(UUID_4);
    }

    @Test
    public void get() throws Exception {
        storage.save(RESUME_5);
        Assert.assertEquals(RESUME_5, storage.get(UUID_5));
    }

    @Test
    public void getAllSorted() throws Exception {
        List<Resume> testListStorage = Arrays.asList(RESUME_1, RESUME_2, RESUME_3);
        Assert.assertEquals(testListStorage, storage.getAllSorted());
    }

    @Test
    public void getAllNotSorted() throws Exception {
        List<Resume> testListStorage = Arrays.asList(RESUME_1, RESUME_3, RESUME_2);
        Assert.assertNotEquals(testListStorage, storage.getAllSorted());
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() throws Exception {
        storage.get("dummy");
    }
}