import org.junit.jupiter.api.Test;

import java.math.BigInteger;

import static exercises.Chap4Recursion.stackSafeTailRecursiveFib;

public class FibUnitTest {

    @Test
    public void callFib() {
        System.out.println(stackSafeTailRecursiveFib(BigInteger.valueOf(100000)));
    }
}
