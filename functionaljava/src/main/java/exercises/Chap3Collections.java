package exercises;

import net.jetnet.functions.Effect;
import net.jetnet.functions.Function;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/*
Chap 3 - iteration abstraction
 */
public class Chap3Collections {

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

    public static <T> List<T> prepend(T element, List<T> list) {
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
        return Collections.unmodifiableList(foldLeft(list, list(), x -> y -> prepend(y, x)));
    }

    public static <T, U> List<U> map(List<T> list, Function<T, U> map) {
        List<U> result = list();
        for (T value : list) {
            result.add(map.apply(value));
        }
        return Collections.unmodifiableList(result);
    }

    public static <T, U> List<U> mapLeft(List<T> list, Function<T, U> map) {
        return foldLeft(list, list(), x -> y -> append(x, map.apply(y)));
    }

    public static <T, U> List<U> mapRight(List<T> list, Function<T, U> map) {
        return foldRight(list, list(), x -> y -> prepend(map.apply(x), y));
    }

    public static <T> void foreach(List<T> list, Effect<T> effect) {
        for (T element : list) {
            effect.apply(element);
        }
    }

    public static List<Integer> range(int start, int end) {
        List<Integer> list = list();
        int index = start;
        while (index < end) {
            list = append(list, index);
            index++;
        }

        return list;
    }

    public static <T> List<T> unfold(T seed, Function<T, T> unfold, Function<T, Boolean> predicate) {
        List<T> list = list();
        T index = seed;
        while (predicate.apply(index)) {
            append(list, index);
            index = unfold.apply(index);
        }
        return list;
    }

    public static List<Integer> unfoldRange(int start, int end) {
        return unfold(start, x -> x++, x -> x < end);
    }

    public static List<Integer> recursiveRange(Integer start, Integer end) {
        return end <= start ?
                list() :
                prepend(start, recursiveRange(start++, end));
    }

    public static <T> List<T> iterate(T seed, Function<T, T> f, int n) {
        List<T> result = new ArrayList<>();
        T temp = seed;
        for (int i = 0; i < n; i++) {
            result.add(temp);
            temp = f.apply(temp);
        }
        return result;
    }

}
