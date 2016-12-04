import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        ElasticMapGetTest.class,
        ElasticMapSizeTest.class,
        ElasticMapIsEmptyTest.class
})
public class ElasticMapTestSuite {
}