package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.List;

public interface Storage {
    void clear();

    void update(Resume resume);

    void save(Resume resume);

    Resume get(String searchKey);

    void delete(String searchKey);

    List<Resume> getAllSorted();

    int size();
}
