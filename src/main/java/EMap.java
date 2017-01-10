import javafx.util.Pair;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

public interface EMap<K, V> {

    Collection<V> get(K key);

    void put(K key, V value);

    int size();

    boolean isEmpty();

    boolean containsKey(K key);

    boolean containsValue(V value);

    void remove(K key);

    void putAll(Map<? extends K, ? extends V> map);

    void clear();

    Set<Pair<K, V>> entrySet(K key);
}
