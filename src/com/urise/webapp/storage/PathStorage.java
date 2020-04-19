package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PathStorage extends AbstractStorage<Path> {
    private Path directory;

    protected Strategy strategy;

    protected PathStorage(String dir, Strategy strategy) {
        this.strategy = strategy;
        directory = Paths.get(dir);
        Objects.requireNonNull(directory, "directory must not be null");
        if (!Files.isDirectory(directory) || !Files.isWritable(directory)) {
            throw new IllegalArgumentException(dir + " is not directory or is not writable");
        }
    }

    @Override
    protected void updateResume(Resume resume, Path searchKey) {
        try {
            strategy.doWrite(resume, new BufferedOutputStream(new FileOutputStream(searchKey.toFile())));
        } catch (IOException e) {
            throw new StorageException("Path write error", resume.getUuid(), e);
        }
    }

    @Override
    protected void saveResume(Resume resume, Path searchKey) {
        Paths.get(searchKey.toUri());
        updateResume(resume, searchKey);
    }

    @Override
    protected Resume getResume(Path searchKey) {
        try {
            return strategy.doRead(new BufferedInputStream(new FileInputStream(searchKey.toFile())));
        } catch (IOException e) {
            throw new StorageException("File read error", searchKey.toString(), e);
        }
    }

    @Override
    protected void deleteResume(Path searchKey) {
        try {
            Files.delete(searchKey);
        } catch (IOException e) {
            throw new StorageException("File delete error", searchKey.toString(), e);
        }
    }

    @Override
    protected Path getStorageSearchKey(String uuid) {
        return directory.resolve(uuid);
    }

    @Override
    protected boolean isExist(Path searchKey) {
        return Files.exists(searchKey);
    }

    @Override
    protected List<Resume> getAll() {
        try {
            List<Resume> resumeList = new ArrayList<>();
            Stream<Path> paths = Files.list(directory);
            if (paths != null) {
                paths.forEach(path -> resumeList.add(getResume(path)));
            }
            return resumeList;
        } catch (IOException e) {
            throw new StorageException("IO Error", " inside getAll ", e);
        }
    }

    @Override
    public void clear() {
        try {
            Files.list(directory).forEach(this::deleteResume);
        } catch (IOException e) {
            throw new StorageException("Path delete error", null);
        }
    }

    @Override
    public int size() {
        try {
            return Files.list(directory).map(path -> path.resolve(directory)).collect(Collectors.toList()).size();
        } catch (IOException e) {
            throw new StorageException("Size get error ", null);
        }
    }
}
