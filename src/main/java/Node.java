import javafx.util.Pair;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class Node<T> {
    private final Pair<Object, Node<T>> parentRef;
    private final Map<Object, Node> children = new HashMap<>();
    private ValueHolder<T> value = new ValueHolder<>();

    public Node(Pair<Object, Node<T>> parentRef) {
        this.parentRef = parentRef;
    }

    public void removeItself() {
        if (parentRef == null)
            return;
        parentRef.getValue().children.remove(parentRef.getKey());
    }
}
