package io.github.querables;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class QuerableMapIsEmptyTest {

    private QuerableMap<ValidKey, Object> sut;

    @Before
    public void setUp() {
        sut = new QuerableMap<>(ValidKey.class);
    }

    @Test
    public void emptyByDefault() {
        assertTrue(sut.isEmpty());
    }

    @Test
    public void nonEmptyAfterPut() {
        sut.put(ValidKey.FIRST, new Object());
        assertFalse(sut.isEmpty());
    }

}
