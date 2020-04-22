package com.urise.webapp.storage.serializer;

import java.io.IOException;

public interface WriterInterface<T> {
    void write(T element) throws IOException;
}
