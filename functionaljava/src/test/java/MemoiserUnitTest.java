import com.jayway.awaitility.Awaitility;
import net.jetnet.functions.Function;
import net.jetnet.functions.Memoizer;
import net.jetnet.functions.Tuple;
import org.assertj.core.data.Offset;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class MemoiserUnitTest {
    private static final Long CALC_TIME = 1_000L;
    private static final int ANY_INPUT = 5;
    private Integer longCalc(Integer x) {
        try {
            Thread.sleep(CALC_TIME);
        } catch (InterruptedException e) {
            //ignored
        }
        return x * 2;
    }

    @Test
    public void testMemoizer() {
        Function<Integer, Integer> memoized = Memoizer.memoize(this::longCalc);
        Long duration = durationOf(memoized, ANY_INPUT);
        Long memoizedDuration = durationOf(memoized, ANY_INPUT);

        assertThat(duration).isCloseTo(CALC_TIME, Offset.offset(100L));
        assertThat(memoizedDuration).isLessThan(duration);
    }

    private <T, U> Long durationOf(Function<T, U> timed, T input) {
        long start = System.currentTimeMillis();
        timed.apply(input);
        long end = System.currentTimeMillis();
        return end - start;
    }
}
