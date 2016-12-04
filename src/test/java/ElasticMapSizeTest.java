import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ElasticMapSizeTest {

    private ElasticMap<ValidKey, Object> sut;

    @Before
    public void setUp() {
        sut = new ElasticMap<>(ValidKey.class);
    }

    @Test
    public void zeroByDefault() {
        assertEquals(0, sut.size());
    }

    @Test
    public void twoForTwoTotallyDifferetPuts() {
        sut.put(new ValidKey("first1", "second1", "third1"), new Object());
        sut.put(new ValidKey("first2", "second2", "third2"), new Object());
        assertEquals(2, sut.size());
    }

    @Test
    public void twoForTwoPartiallyDifferetPuts() {
        sut.put(new ValidKey("first1", "second", "third"), new Object());
        sut.put(new ValidKey("first2", "second", "third"), new Object());
        assertEquals(2, sut.size());
    }

    @Test
    public void oneForTwoDuplicates() {
        sut.put(new ValidKey("first", "second", "third"), new Object());
        sut.put(new ValidKey("first", "second", "third"), new Object());
        assertEquals(1, sut.size());
    }

}
