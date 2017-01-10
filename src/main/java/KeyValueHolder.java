import javafx.util.Pair;
import lombok.Getter;

class KeyValueHolder<K, V> {
    private Pair<K, V> keyValuePair;
    @Getter private boolean isSet = false;

    Pair<K, V> get() {
        if (!isSet)
            throw new NullPointerException();
        return keyValuePair;
    }

    void set(K key, V value) {
        this.keyValuePair = new Pair<>(key, value);
        this.isSet = true;
    }
}
