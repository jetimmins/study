package net.jetnet.collections;

import net.jetnet.functions.Tuple;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

public class CollectionUtils {

    public static <T> List<T> list() {
        return Collections.emptyList();
    }

    public static <T> List<T> list(T t) {
        return Collections.singletonList(t);
    }

    public static <T> List<T> list(List<T> input) {
        return Collections.unmodifiableList(new ArrayList<>(input));
    }

    @SafeVarargs
    public static <T> List<T> list(T... t) {
        return Collections.unmodifiableList(Arrays.asList(Arrays.copyOf(t, t.length)));
    }

    private static <T> List<T> copy(List<T> input) {
        return new ArrayList<>(input);
    }

    public static <T> T head(List<T> input) {
        return copy(input).get(0);
    }

    public static <T> List<T> tail(List<T> input) {
        List<T> work = copy(input);
        work.remove(0);
        return Collections.unmodifiableList(work);
    }

    public static <T> List<T> append(List<T> list, T element) {
        List<T> work = copy(list);
        work.add(element);
        return Collections.unmodifiableList(list);
    }

    public static <T> T foldLeft(List<T> list, T identity, Function<T, Function<T, T>> fold) {
        T acc = identity;
        for (T t : list) {
            acc = fold.apply(acc).apply(t);
        }
        return acc;
    }
}
