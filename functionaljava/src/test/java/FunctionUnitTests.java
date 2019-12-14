import exercises.Chap4Recursion;
import net.jetnet.functions.Function;
import org.junit.jupiter.api.Test;

import java.util.List;

import static exercises.Chap3Collections.list;
import static exercises.Chap3Collections.mapLeft;
import static exercises.Chap3Collections.range;
import static exercises.Chap4Recursion.composeAll;
import static exercises.Chap4Recursion.andThenAll;
import static org.assertj.core.api.Assertions.assertThat;

public class FunctionUnitTests {
    private final Function<Integer, Integer> add = x -> x + 1;

    @Test
    public void testStackSafeCompose() {
        Integer result = composeAll(mapLeft(range(0, 500), x -> add)).apply(0);
        assertThat(result).isEqualTo(500);
    }

    @Test
    public void testRange() {
        List<Integer> range = range(0, 500);
        assertThat(range.size()).isEqualTo(500);
    }

    @Test
    public void testMap() {
        List<Function<Integer, Integer>> map = mapLeft(range(0, 500), x -> add);
        assertThat(map.size()).isEqualTo(500);
    }

    @Test
    public void testSafeComposeAll() {
        Function<String, String> f1 = x -> "(a" + x + ")";
        Function<String, String> f2 = x -> "(b" + x + ")";
        Function<String, String> f3 = x -> "(c" + x + ")";

        System.out.println(Chap4Recursion.composeAll(list(f1, f2, f3)).apply("x"));
        System.out.println(andThenAll(list(f1, f2, f3)).apply("x"));
    }
}
