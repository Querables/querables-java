package io.github.querables;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        QuerableMapGetTest.class,
        QuerableMapSizeTest.class,
        QuerableMapIsEmptyTest.class,
        QuerableMapContainsKeyTest.class,
        QuerableMapContainsValueTest.class,
        QuerableMapEntrySetTest.class
})
public class QuerableMapTestSuite {
}