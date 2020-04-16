package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

import java.util.Comparator;
import java.util.List;

public abstract class AbstractStorage<SK> implements Storage {
    protected Resume[] storage = new Resume[STORAGE_LIMIT];

    protected static final int STORAGE_LIMIT = 10_000;

    protected int size = 0;

    protected static final Comparator<Resume> RESUME_COMPARATOR = Comparator.comparing(Resume::getFullName).thenComparing(Resume::getUuid);

    protected abstract void updateResume(Resume resume, SK searchKey);

    protected abstract void saveResume(Resume resume, SK searchKey);

    protected abstract Resume getResume(SK searchKey);

    protected abstract void deleteResume(SK searchKey);

    protected abstract SK getStorageSearchKey(String uuid);

    protected abstract boolean isExist(SK searchKey);

    protected abstract List<Resume> getAll();

    private SK getExistSearchKey(String uuid) {
        SK searchKey = getStorageSearchKey(uuid);
        if (!isExist(searchKey)) {
            throw new NotExistStorageException(uuid);
        }
        return searchKey;
    }

    private SK getNotSearchKey(String uuid) {
        SK searchKey = getStorageSearchKey(uuid);
        if (isExist(searchKey)) {
            throw new ExistStorageException(uuid);
        }
        return searchKey;
    }

    public List<Resume> getAllSorted() {
        List<Resume> resumeList = getAll();
        resumeList.sort(RESUME_COMPARATOR);
        return resumeList;
    }

    @Override
    public void update(Resume resume) {
        SK searchKey = getExistSearchKey(resume.getUuid());
        updateResume(resume, searchKey);
    }

    @Override
    public void save(Resume resume) {
        SK searchKey = getNotSearchKey(resume.getUuid());
        saveResume(resume, searchKey);
    }

    @Override
    public Resume get(String uuid) {
        SK searchKey = getExistSearchKey(uuid);
        return getResume(searchKey);
    }

    @Override
    public void delete(String uuid) {
        SK searchKey = getExistSearchKey(uuid);
        deleteResume(searchKey);
    }
}
