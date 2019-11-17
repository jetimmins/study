package net.jetnet.collections;

import net.jetnet.functions.Function;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/*
Chap 3 - iteration abstraction
 */
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
        return Collections.unmodifiableList(work);
    }

    public static <T> List<T> prepend(List<T> list, T element) {
        return foldLeft(list, list(element), x -> y -> append(x, y));
    }

    public static <T, U> U foldLeft(List<T> list, U identity, Function<U, Function<T, U>> fold) {
        U acc = identity;
        for (T t : list) {
            acc = fold.apply(acc).apply(t);
        }

        return acc;
    }

    public static <T, U> U foldRight(List<T> list, U identity, Function<T, Function<U, U>> fold) {
        U acc = identity;

        for (int i = list.size() - 1; i >= 0; i--) {
            acc = fold.apply(list.get(i)).apply(acc);
        }

        return acc;
    }

    public static <T, U> U recursiveFoldRight(List<T> list, U identity, Function<T, Function<U, U>> fold) {
        return list.isEmpty() ? identity :
            fold.apply(head(list))
                .apply(recursiveFoldRight(tail(list), identity, fold));
    }

    public static <T> List<T> reverse(List<T> list) {
        return Collections.unmodifiableList(foldLeft(list, list(), x -> y -> prepend(x, y)));
    }

}
