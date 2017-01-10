import javafx.util.Pair;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
class Node<K, T> {
    private final Pair<Object, Node<K, T>> parentRef;
    private final Map<Object, Node<K, T>> children = new HashMap<>();
    private KeyValueHolder<K, T> keyValueHolder = new KeyValueHolder<>();

    Node(Pair<Object, Node<K, T>> parentRef) {
        this.parentRef = parentRef;
    }

    void removeItself() {
        if (parentRef == null)
            return;
        parentRef.getValue().children.remove(parentRef.getKey());
    }
}
