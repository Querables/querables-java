import javafx.util.Pair;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
class Node<T> {
    private final Pair<Object, Node<T>> parentRef;
    private final Map<Object, Node<T>> children = new HashMap<>();
    private ValueHolder<T> valueHolder = new ValueHolder<>();

    Node(Pair<Object, Node<T>> parentRef) {
        this.parentRef = parentRef;
    }

    void removeItself() {
        if (parentRef == null)
            return;
        parentRef.getValue().children.remove(parentRef.getKey());
    }
}
