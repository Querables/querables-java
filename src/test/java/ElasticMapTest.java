import exceptions.*;
import lombok.val;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;
import java.util.Objects;

import static org.junit.Assert.*;

public class ElasticMapTest {

    private static final ValidKey VALID_KEY = new ValidKey("first", "second", "third");
    private static final Object VALUE = new Object();

    private static class KeyWithoutGetters {
    }

    private ElasticMap<ValidKey, Object> sut;

    @Before
    public void setUp() {
        sut = new ElasticMap<>(ValidKey.class);
    }

    @Test(expected = NullKeyTypeException.class)
    public void throwWhenNullClassInCtor() {
        new ElasticMap(null);
    }

    @Test(expected = NoGettersInKeyException.class)
    public void throwWhenNoGettersInKey() {
        new ElasticMap(KeyWithoutGetters.class);
    }

    @Test(expected = InvalidKeyException.class)
    public void throwOnPutOtherThanKey() {
        val sut = new ElasticMap(ValidKey.class);
        sut.put(new Object(), VALUE);
    }

    @Test(expected = InvalidKeyException.class)
    public void throwOnGetOtherThanKey() {
        val sut = new ElasticMap(ValidKey.class);
        sut.get(new Object());
    }

    @Test(expected = NullKeyException.class)
    public void throwWhenNullKeyOnPut() {
        sut.put(null, VALUE);
    }

    @Test(expected = NullKeyException.class)
    public void throwWhenNullKeyOnGet() {
        sut.get(null);
    }

    @Test(expected = NullFieldException.class)
    public void throwWhenAtLeastOneFieldIsEmptyOnPut() {
        sut.put(new ValidKey("first", null, "third"), VALUE);
    }

    @Test
    public void getReturnsEmptyWhenKeyDoesNotExist() {
        assertTrue(sut.get(VALID_KEY).isEmpty());
    }

    @Test
    public void getSameKeyAsPut() {
        sut.put(VALID_KEY, VALUE);
        assertEquals(Collections.singletonList(VALUE), sut.get(VALID_KEY));
    }

}