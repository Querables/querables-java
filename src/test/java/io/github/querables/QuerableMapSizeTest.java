package io.github.querables;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class QuerableMapSizeTest {

    private QuerableMap<ValidKey, Object> sut;

    @Before
    public void setUp() {
        sut = new QuerableMap<>(ValidKey.class);
    }

    @Test
    public void zeroByDefault() {
        assertEquals(0, sut.size());
    }

    @Test
    public void twoForTwoTotallyDifferetPuts() {
        sut.put(ValidKey.FIRST, new Object());
        sut.put(ValidKey.SECOND, new Object());
        assertEquals(2, sut.size());
    }

    @Test
    public void twoForTwoPartiallyDifferetPuts() {
        sut.put(new ValidKey("first1", "second", "third"), new Object());
        sut.put(new ValidKey("first2", "second", "third"), new Object());
        assertEquals(2, sut.size());
    }

    @Test
    public void twoForTwoPartiallyElementsInOnePut() {
        Map<ValidKey, Object> map = new HashMap<>();
        map.put(new ValidKey("first1", "second", "third"), new Object());
        map.put(new ValidKey("first2", "second", "third"), new Object());
        sut.putAll(map);
        assertEquals(2, sut.size());
    }

    @Test
    public void oneForTwoDuplicates() {
        sut.put(ValidKey.FIRST, new Object());
        sut.put(ValidKey.FIRST, new Object());
        assertEquals(1, sut.size());
    }

    @Test
    public void zeroWhenCleared() {
        sut.put(ValidKey.FIRST, new Object());
        sut.clear();
        assertEquals(0, sut.size());
    }

}
