import lombok.val;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ElasticMapContainsValueTest {

    private ElasticMap<ValidKey, Object> sut;

    @Before
    public void setUp() {
        sut = new ElasticMap<>(ValidKey.class);
    }

    @Test
    public void falseByDefault() {
        assertFalse(sut.containsValue(ValidKey.FIRST));
    }

    @Test
    public void trueWhenContains() {
        val object = new Object();
        sut.put(ValidKey.FIRST, object);
        sut.put(ValidKey.SECOND, new Object());
        assertTrue(sut.containsValue(object));
    }

    @Test
    public void trueWhenContainsNull() {
        sut.put(ValidKey.FIRST, null);
        assertTrue(sut.containsValue(null));
    }

    @Test
    public void falseWhenHasBennOverwritten() {
        val expected = new Object();
        sut.put(ValidKey.FIRST, expected);
        sut.put(ValidKey.FIRST, new Object());
        assertFalse(sut.containsValue(expected));
    }

    @Test
    public void trueWhenOverridenIsSeeked() {
        val expected = new Object();
        sut.put(ValidKey.FIRST, new Object());
        sut.put(ValidKey.FIRST, expected);
        assertTrue(sut.containsValue(expected));
    }

}
