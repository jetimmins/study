import org.junit.jupiter.api.Test;

import java.math.BigInteger;

import static exercises.Chap4Recursion.tailRecursiveFibHelper;

public class FibUnitTest {

    @Test
    public void callFib() {
        System.out.println(tailRecursiveFibHelper(BigInteger.valueOf(100000)));
    }
}
