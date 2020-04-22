package com.urise.webapp.storage.serializer;

import java.io.IOException;

public interface ReaderInterface<T> {
    void read() throws IOException;
}
