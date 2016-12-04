import lombok.val;
import org.junit.Before;
import org.junit.Test;

import java.util.Objects;

import static org.junit.Assert.*;

public class ValueHolderTest {

    private ValueHolder<Object> sut;

    @Before
    public void setUp() throws Exception {
        sut = new ValueHolder<>();
    }

    @Test
    public void notSetByDefault() {
        assertFalse(sut.isSet());
    }

    @Test(expected = NullPointerException.class)
    public void throwsWhenTryingToGetUnsetValue() {
        sut.getValue();
    }

    @Test
    public void answersTrueWhenSet() {
        sut.setValue(new Object());
        assertTrue(sut.isSet());
    }

    @Test
    public void returnsValidValue() {
        val object = new Object();
        sut.setValue(object);
        assertSame(object, sut.getValue());
    }

}