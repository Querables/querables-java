import exceptions.*;
import lombok.val;
import org.junit.Test;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;

public class ElasticMapTest {

    private static class KeyWithoutGetters {
    }

    @Test(expected = NullKeyTypeException.class)
    public void throwWhenNullClassInCtor() {
        new ElasticMap<>(null);
    }

    @Test(expected = NoGettersInKeyException.class)
    public void throwWhenNoGettersInKey() {
        new ElasticMap<>(KeyWithoutGetters.class);
    }

    @Test(expected = InvalidKeyException.class)
    public void throwOnPutOtherThanKey() {
        val sut = new ElasticMap<>(ValidKey.class);
        sut.put(new Object(), new Object());
    }

    @Test(expected = InvalidKeyException.class)
    public void throwOnGetOtherThanKey() {
        val sut = new ElasticMap<>(ValidKey.class);
        sut.get(new Object());
    }

    @Test(expected = NullKeyException.class)
    public void throwWhenNullKeyOnPut() {
        val sut = new ElasticMap<>(ValidKey.class);
        sut.put(null, new Object());
    }

    @Test(expected = NullKeyException.class)
    public void throwWhenNullKeyOnGet() {
        val sut = new ElasticMap<>(ValidKey.class);
        sut.get(null);
    }

    @Test(expected = NullFieldException.class)
    public void throwWhenAtLeastOneFieldIsEmptyOnPut() {
        val sut = new ElasticMap<>(ValidKey.class);
        sut.put(new ValidKey("x", null, "x"), new Object());
    }

    @Test
    public void getReturnsNullWhenKeyDoesNotExist() {
        val sut = new ElasticMap<>(ValidKey.class);
        assertNull(sut.get(new ValidKey("x", "x", "x")));
    }

    @Test
    public void getSameKeyAsPut() {
        val sut = new ElasticMap<>(ValidKey.class);

        val value = new Object();
        val key = new ValidKey("x", "x", "x");

        sut.put(key, value);
        assertSame(value, sut.get(key));
    }

}