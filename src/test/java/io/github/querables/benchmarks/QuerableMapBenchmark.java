package io.github.querables.benchmarks;

import io.github.querables.QuerableMap;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@State(Scope.Benchmark)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
public class QuerableMapBenchmark {

    private static final String[] ELEMENTS = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j"};
    private static final Random RAND = new Random(42);

    @AllArgsConstructor
    @NoArgsConstructor
    static class Key {
        String first;
        String second;
        String third;
    }

    private QuerableMap<Key, Object> cut;
    private Key middleKey = new Key(nextElement(), nextElement(), nextElement());

    @Param({"1000", "10000", "100000"})
    private int population;


    private void populateCutAndChooseMiddleKey() {
        for (int i = 0; i < population; ++i) {
            Key key = new Key(nextElement(), nextElement(), nextElement());
            cut.put(key, new Object());
            if (i == population / 2)
                middleKey = key;
        }
    }

    private static String nextElement() {
        return ELEMENTS[Math.abs(RAND.nextInt()) % ELEMENTS.length];
    }


    @Setup
    public void setup() {
        cut = new QuerableMap<>(Key.class);
        populateCutAndChooseMiddleKey();
    }

    @Benchmark
    public void exactMatch() {
        cut.get(new Key(middleKey.first, middleKey.second, middleKey.third));
    }

    @Benchmark
    public void thirdNull() {
        cut.get(new Key(middleKey.first, middleKey.second, null));
    }

    @Benchmark
    public void secondNull() {
        cut.get(new Key(middleKey.first, null, middleKey.third));
    }

    @Benchmark
    public void firstNull() {
        cut.get(new Key(null, middleKey.second, middleKey.third));
    }

    @Benchmark
    public void firstMatch() {
        cut.get(new Key(middleKey.first, null, null));
    }

    public static void main(String[] args) throws IOException, RunnerException {
        Options opt = new OptionsBuilder()
                .include(QuerableMapBenchmark.class.getSimpleName())
                .forks(1)
                .warmupIterations(5)
                .measurementIterations(5)
                .build();
        new Runner(opt).run();
    }

}
