import exceptions.*;
import lombok.SneakyThrows;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

public class ElasticMap<K, V> {

    private final Class<K> keyType;
    private final List<Field> keyFields;
    private final Node rootNode = new Node();
    private int rootSize = 0;

    private class Node {
        final Map<Object, Node> children = new HashMap<>();
        V value = null;
    }

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

    public Collection<V> get(K key) {
        assertValidKeyType(key);
        List<Object> keyValues = convertToList(key);

        List<Node> nodes = Collections.singletonList(rootNode);
        for (Object keyValue : keyValues) {
            if (keyValue == null) {
                nodes = nodes.stream()
                        .flatMap(node -> node.children.values().stream())
                        .collect(Collectors.toList());
            }
            else {
                nodes = nodes.stream()
                        .map(node -> node.children.get(keyValue))
                        .filter(Objects::nonNull)
                        .collect(Collectors.toList());
            }
        }

        return nodes.stream()
                .map(node -> node.value)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    public void put(K key, V value) {
        assertValidKeyType(key);
        List<Object> keyValues = convertToList(key);
        assertNonNullFields(keyValues);

        Node node = rootNode;
        for (Object keyValue : keyValues) {
            node.children.putIfAbsent(keyValue, new Node());
            node = node.children.get(keyValue);
        }
        if (node.value == null)
            rootSize += 1;
        node.value = value;
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
            throw new InvalidKeyTypeException(keyType, actualType);
    }

    private static void assertNonNullFields(List<Object> key) {
        if (key.stream().anyMatch(Objects::isNull))
            throw new NullFieldException(key);
    }

    public int size() {
        return rootSize;
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

    public Set<Map.Entry<K, V>> entrySet() {
        return null;
    }
}
