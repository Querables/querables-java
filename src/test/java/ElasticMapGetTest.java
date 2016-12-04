import exceptions.*;
import javafx.util.Pair;
import lombok.val;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ElasticMapGetTest {

    private static final String IMPORTANT = "IMPORTANT";
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

    @Test(expected = InvalidKeyTypeException.class)
    public void throwOnPutOtherThanKey() {
        val sut = new ElasticMap(ValidKey.class);
        sut.put(new Object(), VALUE);
    }

    @Test(expected = InvalidKeyTypeException.class)
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
        assertTrue(sut.get(ValidKey.FIRST).isEmpty());
    }

    @Test
    public void getSameKeyAsPut() {
        sut.put(ValidKey.FIRST, VALUE);
        assertEquals(Collections.singletonList(VALUE), sut.get(ValidKey.FIRST));
    }

    @Test
    public void replaceValueWithSameKey() {
        sut.put(ValidKey.FIRST, new Object());
        sut.put(ValidKey.FIRST, VALUE);
        assertEquals(Collections.singletonList(VALUE), sut.get(ValidKey.FIRST));
    }

    @Test
    public void getMatchingValuesWhenFirstFieldGiven() {
        List<Pair<ValidKey, Object>> inputs = Arrays.asList(
                new Pair<>(new ValidKey(IMPORTANT, "second1", "third1"), new Object()),
                new Pair<>(new ValidKey("first2", "second2", "third2"), new Object()),
                new Pair<>(new ValidKey(IMPORTANT, "second3", "third3"), new Object()));

        sut.put(inputs.get(0).getKey(), inputs.get(0).getValue());
        sut.put(inputs.get(1).getKey(), inputs.get(1).getValue());
        sut.put(inputs.get(2).getKey(), inputs.get(2).getValue());

        assertEquals(
                Arrays.asList(inputs.get(0).getValue(), inputs.get(2).getValue()),
                sut.get(new ValidKey(IMPORTANT, null, null)));
    }

    @Test
    public void getMatchingValuesWhenSecondFieldGiven() {
        List<Pair<ValidKey, Object>> inputs = Arrays.asList(
                new Pair<>(new ValidKey("first1", IMPORTANT, "third1"), new Object()),
                new Pair<>(new ValidKey("first2", "second2", "third2"), new Object()),
                new Pair<>(new ValidKey("first3", IMPORTANT, "third3"), new Object()));

        sut.put(inputs.get(0).getKey(), inputs.get(0).getValue());
        sut.put(inputs.get(1).getKey(), inputs.get(1).getValue());
        sut.put(inputs.get(2).getKey(), inputs.get(2).getValue());

        assertEquals(
                Arrays.asList(inputs.get(2).getValue(), inputs.get(0).getValue()),
                sut.get(new ValidKey(null, IMPORTANT, null)));
    }

    @Test
    public void getMatchingValuesWhenThirdFieldGiven() {
        List<Pair<ValidKey, Object>> inputs = Arrays.asList(
                new Pair<>(new ValidKey("first1", "second1", IMPORTANT), new Object()),
                new Pair<>(new ValidKey("first2", "second2", "third2"), new Object()),
                new Pair<>(new ValidKey("first3", "second3", IMPORTANT), new Object()));

        sut.put(inputs.get(0).getKey(), inputs.get(0).getValue());
        sut.put(inputs.get(1).getKey(), inputs.get(1).getValue());
        sut.put(inputs.get(2).getKey(), inputs.get(2).getValue());

        assertEquals(
                Arrays.asList(inputs.get(2).getValue(), inputs.get(0).getValue()),
                sut.get(new ValidKey(null, null, IMPORTANT)));
    }

}
