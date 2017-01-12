import exceptions.InvalidKeyTypeException;
import exceptions.NullKeyException;
import lombok.val;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class QuerableMapContainsKeyTest {

    private QuerableMap<ValidKey, Object> sut;

    @Before
    public void setUp() {
        sut = new QuerableMap<>(ValidKey.class);
    }

    @Test(expected = InvalidKeyTypeException.class)
    public void throwOnGetOtherThanKey() {
        val sut = new QuerableMap(ValidKey.class);
        sut.containsKey(new Object());
    }

    @Test(expected = NullKeyException.class)
    public void throwWhenNullKeyOnGet() {
        sut.containsKey(null);
    }

    @Test
    public void falseByDefault() {
        assertFalse(sut.containsKey(ValidKey.FIRST));
    }

    @Test
    public void falseForNonMatchingKey() {
        sut.put(ValidKey.FIRST, new Object());
        assertFalse(sut.containsKey(ValidKey.SECOND));
    }

    @Test
    public void falseForNonMatchingFirstField() {
        sut.put(new ValidKey("first1", "second1", "third1"), new Object());
        assertFalse(sut.containsKey(new ValidKey("first2", null, null)));
    }

    @Test
    public void falseForNonMatchingSecondField() {
        sut.put(new ValidKey("first1", "second1", "third1"), new Object());
        assertFalse(sut.containsKey(new ValidKey(null, "second2", null)));
    }

    @Test
    public void falseForNonMatchingThirdField() {
        sut.put(new ValidKey("first1", "second1", "third1"), new Object());
        assertFalse(sut.containsKey(new ValidKey(null, null, "third2")));
    }

    @Test
    public void trueForExactlyMatchingKey() {
        sut.put(new ValidKey("first1", "second1", "third1"), new Object());
        assertTrue(sut.containsKey(new ValidKey("first1", "second1", "third1")));
    }

    @Test
    public void trueForMatchingFirstField() {
        sut.put(new ValidKey("first", "second", "third"), new Object());
        assertTrue(sut.containsKey(new ValidKey("first", null, null)));
    }

    @Test
    public void trueForMatchingSecondField() {
        sut.put(new ValidKey("first", "second", "third"), new Object());
        assertTrue(sut.containsKey(new ValidKey(null, "second", null)));
    }

    @Test
    public void trueForMatchingThirdField() {
        sut.put(new ValidKey("first", "second", "third"), new Object());
        assertTrue(sut.containsKey(new ValidKey(null, null, "third")));
    }
}
