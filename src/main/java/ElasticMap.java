import exceptions.*;
import lombok.SneakyThrows;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

public class ElasticMap<K, V> implements Map<K, V> {

    private final Class<K> keyType;
    private final List<Field> keyFields;

    public ElasticMap(Class keyType) {
        if (keyType == null)
            throw new NullKeyTypeException();

        if (Arrays.stream(keyType.getDeclaredFields()).count() == 0)
            throw new NoGettersInKeyException(keyType);

        this.keyType = keyType;

        this.keyFields = Arrays.stream(keyType.getDeclaredFields()).map(field -> {
            field.setAccessible(true);
            return field;
        }).collect(Collectors.toList());
    }

    public int size() {
        return 0;
    }

    public boolean isEmpty() {
        return false;
    }

    public boolean containsKey(Object o) {
        return false;
    }

    public boolean containsValue(Object o) {
        return false;
    }

    public V get(Object key) {
        assertValidKeyType(key);
        return null;
    }

    public V put(K key, V value) {
        assertValidKeyType(key);
        List<Object> keyList = convertToList(key);
        assertNonNullFields(keyList);
        return null;
    }

    private List<Object> convertToList(K key) {
        return keyFields.stream()
                .map(field -> get(field, key))
                .collect(Collectors.toList());
    }

    @SneakyThrows(IllegalAccessException.class)
    private static Object get(Field field, Object object) {
        return field.get(object);
    }

    private void assertValidKeyType(Object key) {
        if (key == null)
            throw new NullKeyException();
        Class actualType = key.getClass();
        if (actualType != keyType)
            throw new InvalidKeyException(keyType, actualType);
    }

    private static void assertNonNullFields(List<Object> key) {
        if (key.stream().anyMatch(Objects::isNull))
            throw new NullFieldException(key);
    }

    public V remove(Object o) {
        return null;
    }

    public void putAll(Map<? extends K, ? extends V> map) {

    }

    public void clear() {

    }

    public Set<K> keySet() {
        return null;
    }

    public Collection<V> values() {
        return null;
    }

    public Set<Entry<K, V>> entrySet() {
        return null;
    }
}
