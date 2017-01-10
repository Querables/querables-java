import exceptions.InvalidKeyTypeException;
import exceptions.NullKeyException;
import javafx.util.Pair;
import lombok.val;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ElasticMapEntrySetTest {

    private final static Object FIRST = new Object();
    private final static Object SECOND = new Object();

    private ElasticMap<ValidKey, Object> sut;

    @Before
    public void setUp() {
        sut = new ElasticMap<>(ValidKey.class);
    }

    @Test
    public void emptyByDefault() {
        assertTrue(sut.entrySet(ValidKey.EMPTY).isEmpty());
    }

    @Test(expected = NullKeyException.class)
    public void throwWhenNullKeyOnGet() {
        sut.entrySet(null);
    }

    @Test(expected = InvalidKeyTypeException.class)
    public void throwOnGetOtherThanKey() {
        val sut = new ElasticMap(ValidKey.class);
        sut.entrySet(new Object());
    }

    @Test
    public void returnsEmptyWhenKeyDoesNotExist() {
        assertTrue(sut.entrySet(ValidKey.FIRST).isEmpty());
    }

    @Test
    public void containsAdded() {
        sut.put(ValidKey.FIRST, FIRST);
        sut.put(ValidKey.SECOND, SECOND);
        assertTrue(sut.entrySet(ValidKey.EMPTY).contains(new Pair<>(ValidKey.FIRST, FIRST)));
        assertTrue(sut.entrySet(ValidKey.EMPTY).contains(new Pair<>(ValidKey.SECOND, SECOND)));
    }

    @Test
    public void returnsOnlyMatching() {
        sut.put(ValidKey.FIRST, FIRST);
        sut.put(ValidKey.SECOND, SECOND);
        val matchingFirst = new ValidKey(ValidKey.FIRST.getFirst(), null, null);
        assertTrue(sut.entrySet(matchingFirst).contains(new Pair<>(ValidKey.FIRST, FIRST)));
        assertFalse(sut.entrySet(matchingFirst).contains(new Pair<>(ValidKey.SECOND, SECOND)));
    }

    @Test
    public void returnsEmptyWhenExactKeyHasBeenRemoved() {
        sut.put(ValidKey.FIRST, new Object());
        sut.remove(ValidKey.FIRST);
        assertTrue(sut.entrySet(ValidKey.FIRST).isEmpty());
    }

    @Test
    public void returnsEmptyOnceCleared() {
        sut.put(ValidKey.FIRST, new Object());
        sut.clear();
        assertTrue(sut.entrySet(ValidKey.FIRST).isEmpty());
    }

}
