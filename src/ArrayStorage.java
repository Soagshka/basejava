import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];
    int i = 0;

    void clear() {
        Arrays.fill(storage, null);
        i = 0;
    }

    void save(Resume r) {
        storage[i] = r;
        i++;
    }

    Resume get(String uuid) {
        for (int j = 0; j < i; j++) {
            if (uuid.equals(storage[j].uuid)) {
                return storage[j];
            }
        }
        return null;
    }

    void delete(String uuid) {
        for (int j = 0; j < i; j++) {
            if (storage[j].uuid.equals(uuid)) {
                storage[j] = null;
                for (int k = 0; k < i; k++) {
                    storage[k] = storage[k + 1];
                }
                i--;
            }
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        return Arrays.copyOf(storage, i);
    }

    int size() {
        return i;
    }
}
