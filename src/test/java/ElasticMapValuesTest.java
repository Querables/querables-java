import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ElasticMapValuesTest {

    private final static Object FIRST = new Object();
    private final static Object SECOND = new Object();

    private ElasticMap<ValidKey, Object> sut;

    @Before
    public void setUp() {
        sut = new ElasticMap<>(ValidKey.class);
    }

    @Test
    public void emptyByDefault() {
        assertTrue(sut.values().isEmpty());
    }

    @Test
    public void containsAllValues() {
        sut.put(ValidKey.FIRST, FIRST);
        sut.put(ValidKey.SECOND, SECOND);
        assertTrue(sut.values().contains(FIRST));
        assertTrue(sut.values().contains(SECOND));
    }

    @Test
    public void doesNotContainRemovedKey() {
        sut.put(ValidKey.FIRST, FIRST);
        sut.put(ValidKey.SECOND, SECOND);
        sut.remove(ValidKey.FIRST);
        assertFalse(sut.values().contains(FIRST));
        assertTrue(sut.values().contains(SECOND));
    }

    @Test
    public void doesNotContainAnyKeysWhenCleared() {
        sut.put(ValidKey.FIRST, FIRST);
        sut.put(ValidKey.SECOND, SECOND);
        sut.clear();
        assertTrue(sut.values().isEmpty());
    }


}
