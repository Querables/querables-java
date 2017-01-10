import javafx.util.Pair;
import lombok.val;
import org.junit.Before;
import org.junit.Test;

import java.util.Objects;

import static org.junit.Assert.*;

public class NodeTest {

    @Test
    public void doesNotFailForNullParent() {
        val node = new Node<>(null);
        node.removeItself();
    }

    @Test
    public void removesOnlyItselfFromParent() {
        Node<Object, Object> parent = new Node<>(null);
        val key = "key";
        val anotherKey = "anotherKey";
        Node<Object, Object> node = new Node<>(new Pair<>(key, parent));
        Node<Object, Object> anotherNode = new Node<>(new Pair<>(anotherKey, parent));
        parent.getChildren().put(key, node);
        parent.getChildren().put(anotherKey, anotherNode);

        node.removeItself();

        assertTrue(parent.getChildren().containsValue(anotherNode));
        assertFalse(parent.getChildren().containsValue(node));
    }

}