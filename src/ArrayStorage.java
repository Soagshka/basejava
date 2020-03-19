import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];
    int size = 0;

    void clear() {
        Arrays.fill(storage, 0, size + 1, null);
        size = 0;
    }

    void save(Resume r) {
        storage[size] = r;
        size++;
    }

    Resume get(String uuid) {
        for (int j = 0; j < size; j++) {
            if (uuid.equals(storage[j].uuid)) {
                return storage[j];
            }
        }
        return null;
    }

    void delete(String uuid) {
        for (int j = 0; j < size; j++) {
            if (storage[j].uuid.equals(uuid)) {
                for (int k = 0; k < size - 1; k++) {
                    storage[k] = storage[k + 1];
                }
                size--;
            }
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    int size() {
        return size;
    }
}
