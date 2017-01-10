import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        ElasticMapGetTest.class,
        ElasticMapSizeTest.class,
        ElasticMapIsEmptyTest.class,
        ElasticMapContainsKeyTest.class,
        ElasticMapContainsValueTest.class,
        ElasticMapKeySetTest.class,
        ElasticMapValuesTest.class
})
public class ElasticMapTestSuite {
}