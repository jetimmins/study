package exercises;

import net.jetnet.functions.Function;
import net.jetnet.functions.TailCall;
import net.jetnet.functions.Tuple;

import java.math.BigInteger;
import java.util.List;

import static exercises.Chap3Collections.*;
import static net.jetnet.functions.Function.*;
import static net.jetnet.functions.TailCall.ret;
import static net.jetnet.functions.TailCall.sus;

public class Chap4Recursion {
    private static final String COMMA_SEP = ", ";

    public static int add(int x, int y) {
        return addRec(x, y).eval();
    }

    private static TailCall<Integer> addRec(int x, int y) {
        return y == 0 ?
                ret(x) :
                sus(() -> addRec(x + 1, y - 1));
    }

    /*
    4.1: Create a tail recursive version of the Fibonnaci functional method that makes a single recursive call
     */
    private static BigInteger tailRecursiveFib(BigInteger n, BigInteger acc1, BigInteger acc2) {
        if (n.equals(BigInteger.ZERO)) {
            return n;
        }
        if (n.equals(BigInteger.ONE)) {
            return acc1.add(acc2);
        } else {
            return tailRecursiveFib(n.subtract(BigInteger.ONE), acc1.add(acc2), acc1);
        }
    }

    /*
    4.2: make it stack-safe recursive
     */
    private static TailCall<BigInteger> stackSafeTailRecursiveFib_(BigInteger n, BigInteger acc1, BigInteger acc2) {
        if (n.equals(BigInteger.ZERO)) {
            return ret(n);
        }
        if (n.equals(BigInteger.ONE)) {
            return ret(acc1.add(acc2));
        } else {
            return sus(() -> stackSafeTailRecursiveFib_(n.subtract(BigInteger.ONE), acc1.add(acc2), acc1));
        }
    }

    public static BigInteger stackSafeTailRecursiveFib(BigInteger n) {
        return stackSafeTailRecursiveFib_(n, BigInteger.ONE, BigInteger.ZERO).eval();
    }

    /*
    4.9 Write a stack safe tail recursive fib returning a comma separated sequence of the values from 0->n
     */
    private static TailCall<List<BigInteger>> fibSequence_(BigInteger n, List<BigInteger> series, BigInteger acc1, BigInteger acc2) {
        return n.equals(BigInteger.ZERO) ?
                ret(series) :
                sus(() -> fibSequence_(n.subtract(BigInteger.ONE), append(series, acc1.add(acc2)), acc1.add(acc2), acc1));
    }

    public static String fibSequence(int n) {
        List<BigInteger> sequence = fibSequence_(BigInteger.valueOf(n), list(BigInteger.ZERO), BigInteger.ONE, BigInteger.ZERO).eval();
        return toSeqStr(sequence, COMMA_SEP);
    }

    private static String toSeqStr(List<BigInteger> seq, String delim) {
        return seq.isEmpty() ?
                "" :
                tail(seq).isEmpty() ?
                        head(seq).toString() :
                        head(seq) + foldLeft(tail(seq), new StringBuilder(), x -> y -> x.append(delim).append(y)).toString();
    }

    public static String fiboCorecursive(int n) {
        Tuple<BigInteger, BigInteger> seed = new Tuple<>(BigInteger.ZERO, BigInteger.ONE);
        Function<Tuple<BigInteger, BigInteger>, Tuple<BigInteger, BigInteger>> next = x -> new Tuple<>(x._2, x._1.add(x._2));
        List<BigInteger> list = map(iterate(seed, next, n + 1), x -> x._1);
        return toSeqStr(list, COMMA_SEP);
    }

    /*
    4.3: Create a stack-safe foldLeft
     */
    private static <T, U> TailCall<U> foldLeft_(List<T> list, U identity, Function<U, Function<T, U>> fold) {
        return list.isEmpty() ?
                ret(identity) :
                sus(() -> foldLeft_(tail(list), fold.apply(identity).apply(head(list)), fold));
    }

    public static <T, U> U foldLeft(List<T> list, U identity, Function<U, Function<T, U>> fold) {
        return foldLeft_(list, identity, fold).eval();
    }

    /*
    4.4 Create a fully recursive version of the recursive range method
     */
    private static TailCall<List<Integer>> recursiveRange_(List<Integer> acc, Integer start, Integer end) {
        return end <= start ?
                ret(list()) :
                sus(() -> recursiveRange_(append(acc, start), start + 1, end));
    }

    public static List<Integer> recursiveRange(Integer start, Integer end) {
        return recursiveRange_(list(), start, end).eval();
    }

    /*
    4.5 Create a stack-safe recursive version of the foldRight method
     */
    private static <T, U> TailCall<U> recursiveFoldRight_(U acc, List<T> list, Function<T, Function<U, U>> fold) {
        return list.isEmpty() ?
                ret(acc) :
                sus(() -> recursiveFoldRight_(fold.apply(head(list)).apply(acc), tail(list), fold));
    }

    //recursive foldRight is unperformant as requires O(n) reverse
    public static <T, U> U recursiveFoldright(List<T> list, U identity, Function<T, Function<U, U>> fold) {
        return recursiveFoldRight_(identity, reverse(list), fold).eval();
    }

    /*
    4.6 Write a function composeAll that takes a list of functions T to T
    and returns a result of composing all the functions in the list
     */
    public static <T> Function<T, T> unsafeComposeAll(List<Function<T, T>> functions) {
        return foldLeft(functions, identity(), x -> y -> x.compose(y));
    }

    /*
    4.7 Make the composeAll stack-safe
     */
    public static <T> Function<T, T> composeAll(List<Function<T, T>> functions) {
        return x -> foldLeft(reverse(functions), x, a -> b -> b.apply(a));
    }

    /*
    4.8 composeAll must be reversed!
     */
    public static <T> Function<T, T> andThenAll(List<Function<T, T>> functions) {
        return x -> foldLeft(functions, x, a -> b -> b.apply(a));
    }


}
