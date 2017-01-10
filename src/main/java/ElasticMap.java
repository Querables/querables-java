import exceptions.*;
import javafx.util.Pair;
import lombok.SneakyThrows;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

public class ElasticMap<K, V> {

    private final Class<K> keyType;
    private final List<Field> keyFields;
    private Node<K, V> rootNode = new Node<>(null);
    private int rootSize = 0;

    public ElasticMap(Class<K> keyType) {
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
        return getMatchingNodes(key).stream()
                .map(Node::getKeyValueHolder)
                .filter(KeyValueHolder::isSet)
                .map(KeyValueHolder::get)
                .map(Pair::getValue)
                .collect(Collectors.toList());
    }

    public void put(K key, V value) {
        assertValidKeyType(key);
        List<Object> keyValues = convertToList(key);
        assertNonNullFields(keyValues);

        Node<K, V> node = rootNode;
        for (Object keyValue : keyValues) {
            node.getChildren().putIfAbsent(keyValue, new Node<>(new Pair<>(keyValue, node)));
            node = node.getChildren().get(keyValue);
        }
        if (!node.getKeyValueHolder().isSet()) {
            rootSize += 1;
        }
        node.getKeyValueHolder().set(key, value);
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

    private void assertValidKeyType(K key) {
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
        return rootSize == 0;
    }

    public boolean containsKey(K key) {
        return !getMatchingNodes(key).isEmpty();
    }

    private List<Node<K, V>> getMatchingNodes(K key) {
        assertValidKeyType(key);
        List<Object> keyValues = convertToList(key);

        List<Node<K, V>> nodes = Collections.singletonList(rootNode);
        for (Object keyValue : keyValues) {
            if (keyValue == null) {
                nodes = nodes.stream()
                        .flatMap(node -> node.getChildren().values().stream())
                        .collect(Collectors.toList());
            }
            else {
                nodes = nodes.stream()
                        .map(node -> node.getChildren().get(keyValue))
                        .filter(Objects::nonNull)
                        .collect(Collectors.toList());
            }
        }

        return nodes;
    }

    public boolean containsValue(V value) {
        List<Node<K, V>> nodes = Collections.singletonList(rootNode);
        while (!nodes.isEmpty()) {
            if (nodes.stream().anyMatch(n -> n.getKeyValueHolder().isSet() && n.getKeyValueHolder().get().getValue() == value)) {
                return true;
            }
            nodes = nodes.stream()
                    .flatMap(n -> n.getChildren().values().stream())
                    .collect(Collectors.toList());
        }
        return false;
    }

    public void remove(K key) {
        List<Node<K, V>> nodes = getMatchingNodes(key);
        for (Node node : nodes) {
            node.removeItself();
        }
    }

    public void putAll(Map<? extends K, ? extends V> map) {
        map.forEach(this::put);
    }

    public void clear() {
        rootNode = new Node<>(null);
        rootSize = 0;
    }

    public Set<Map.Entry<K, V>> entrySet() {
        return null;
    }
}
