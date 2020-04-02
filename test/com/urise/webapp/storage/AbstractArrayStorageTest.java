package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public abstract class AbstractArrayStorageTest {
    private Storage storage;

    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";

    public AbstractArrayStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() throws Exception {
        storage.clear();
        storage.save(new Resume(UUID_1));
        storage.save(new Resume(UUID_2));
        storage.save(new Resume(UUID_3));
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
        Resume resume = new Resume("uuid3");
        storage.update(resume);
        Assert.assertEquals(resume, storage.get("uuid3"));
    }

    @Test
    public void getAll() throws Exception {
        Resume[] testStorage = storage.getAll();
        for (Resume resume : testStorage) {
            Assert.assertEquals(resume, storage.get(resume.getUuid()));
        }
    }

    @Test
    public void save() throws Exception {
        Resume resume = new Resume("uuid4");
        storage.save(resume);
        Assert.assertEquals(4, storage.size());
    }

    @Test
    public void delete() throws Exception {
        storage.delete("uuid2");
        Assert.assertEquals(2, storage.size());
    }

    @Test
    public void get() throws Exception {
        Resume resume = new Resume("uuid5");
        storage.save(resume);
        Assert.assertEquals(storage.get("uuid5"), resume);
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() throws Exception {
        storage.get("dummy");
    }

    @Test(expected = StorageException.class)
    public void storageOverFlow() {
        try {
            for (int i = 4; i <= AbstractArrayStorage.STORAGE_LIMIT; i++) {
                storage.save(new Resume());
            }
        } catch (StorageException e) {
            Assert.fail("Exception not thrown");
        }
        storage.save(new Resume());
    }
}