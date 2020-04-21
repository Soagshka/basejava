package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import com.urise.webapp.storage.serializer.StreamSerializer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PathStorage extends AbstractStorage<Path> {
    private Path directory;

    protected StreamSerializer streamSerializer;

    protected PathStorage(String dir, StreamSerializer streamSerializer) {
        this.streamSerializer = streamSerializer;
        directory = Paths.get(dir);
        Objects.requireNonNull(directory, "directory must not be null");
        if (!Files.isDirectory(directory) || !Files.isWritable(directory)) {
            throw new IllegalArgumentException(dir + " is not directory or is not writable");
        }
    }

    @Override
    protected void updateResume(Resume resume, Path searchKey) {
        try {
            streamSerializer.doWrite(resume, Files.newOutputStream(searchKey));
        } catch (IOException e) {
            throw new StorageException("Path write error", resume.getUuid(), e);
        }
    }

    @Override
    protected void saveResume(Resume resume, Path searchKey) {
        try {
            Files.createFile(searchKey);
            updateResume(resume, searchKey);
        } catch (IOException e) {
            throw new StorageException("File create error ", searchKey.toString(), e);
        }
    }

    @Override
    protected Resume getResume(Path searchKey) {
        try {
            return streamSerializer.doRead(Files.newInputStream(searchKey));
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
        return getFilesList().map(this::getResume).collect(Collectors.toList());
    }

    @Override
    public void clear() {
        getFilesList().forEach(this::deleteResume);
    }

    @Override
    public int size() {
        return (int) getFilesList().count();
    }

    protected Stream<Path> getFilesList() {
        try {
            return Files.list(directory);
        } catch (IOException e) {
            throw new StorageException("Files.list ", null);
        }
    }
}
