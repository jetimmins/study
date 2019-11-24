package exercises;

import net.jetnet.functions.Function;
import net.jetnet.functions.TailCall;

import java.math.BigInteger;

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
    4.1: Create a tail recursive version of the Fibonnaci functional method
     */
//    Function<BigInteger, Function<BigInteger, BigInteger>> fib
}
