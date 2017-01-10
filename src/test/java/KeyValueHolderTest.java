import lombok.val;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class KeyValueHolderTest {

    private KeyValueHolder<Object, Object> sut;

    @Before
    public void setUp() throws Exception {
        sut = new KeyValueHolder<>();
    }

    @Test
    public void notSetByDefault() {
        assertFalse(sut.isSet());
    }

    @Test(expected = NullPointerException.class)
    public void throwsWhenTryingToGetUnsetValue() {
        sut.get();
    }

    @Test
    public void answersTrueWhenSet() {
        sut.set(new Object(), new Object());
        assertTrue(sut.isSet());
    }

    @Test
    public void returnsValidKeyAndValue() {
        val key = new Object();
        val value = new Object();
        sut.set(key, value);
        assertSame(key, sut.get().getKey());
        assertSame(value, sut.get().getValue());
    }

}