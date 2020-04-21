package com.urise.webapp.storage;

import com.urise.webapp.storage.serializer.ObjectStreamPathStorage;

public class ObjectStreamPathStorageTest extends AbstractStorageTest {
    public ObjectStreamPathStorageTest() {
        super(new PathStorage(STORAGE_DIR, new ObjectStreamPathStorage()));
    }
}