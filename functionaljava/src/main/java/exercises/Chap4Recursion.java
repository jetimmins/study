package exercises;

import net.jetnet.functions.Function;
import net.jetnet.functions.TailCall;

import java.math.BigInteger;
import java.util.List;

import static exercises.Chap3Collections.*;
import static net.jetnet.functions.TailCall.ret;
import static net.jetnet.functions.TailCall.sus;

public class Chap4Recursion {

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
        if(n.equals(BigInteger.ONE)){
            return acc1.add(acc2);
        }
        else {
            return tailRecursiveFib(n.subtract(BigInteger.ONE), acc1.add(acc2) , acc1);
        }
    }

    /*
    4.2: make it stack-safe recursive
     */
    private static TailCall<BigInteger> stackSafeTailRecursiveFib_(BigInteger n, BigInteger acc1, BigInteger acc2) {
        if (n.equals(BigInteger.ZERO)) {
            return ret(n);
        }
        if(n.equals(BigInteger.ONE)){
            return ret(acc1.add(acc2));
        }
        else {
            return sus(() -> stackSafeTailRecursiveFib_(n.subtract(BigInteger.ONE), acc1.add(acc2) , acc1));
        }
    }

    public static BigInteger stackSafeTailRecursiveFib(BigInteger n) {
        return stackSafeTailRecursiveFib_(n, BigInteger.ONE, BigInteger.ZERO).eval();
    }

    /*
    4.3: Create a stack-safe foldLeft
     */
    private static <T, U> TailCall<U> foldLeft_(List<T> list, U identity, Function<U, Function<T, U>> fold) {
        return list.isEmpty() ?
                ret(identity) :
                sus(() -> foldLeft_(tail(list), fold.apply(identity).apply(head(list)) , fold));
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



}
