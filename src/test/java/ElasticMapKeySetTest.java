import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ElasticMapKeySetTest {

    private ElasticMap<ValidKey, Object> sut;

    @Before
    public void setUp() {
        sut = new ElasticMap<>(ValidKey.class);
    }

    @Test
    public void emptyByDefault() {
        assertTrue(sut.keySet().isEmpty());
    }

    @Test
    public void containsValidKeys() {
        sut.put(ValidKey.FIRST, new Object());
        sut.put(ValidKey.SECOND, new Object());
        assertTrue(sut.keySet().contains(ValidKey.FIRST));
        assertTrue(sut.keySet().contains(ValidKey.SECOND));
    }

    @Test
    public void doesNotContainRemovedKey() {
        sut.put(ValidKey.FIRST, new Object());
        sut.put(ValidKey.SECOND, new Object());
        sut.remove(ValidKey.FIRST);
        assertFalse(sut.keySet().contains(ValidKey.FIRST));
        assertTrue(sut.keySet().contains(ValidKey.SECOND));
    }

    @Test
    public void doesNotContainAnyKeysWhenCleared() {
        sut.put(ValidKey.FIRST, new Object());
        sut.put(ValidKey.SECOND, new Object());
        sut.clear();
        assertTrue(sut.keySet().isEmpty());
    }

}
