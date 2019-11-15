package exercises;

import net.jetnet.functions.Effect;
import net.jetnet.functions.Function;
import net.jetnet.functions.Result;
import net.jetnet.functions.Result.Failure;
import net.jetnet.functions.Result.Success;

import java.util.regex.Pattern;

import static net.jetnet.functions.Case.match;
import static net.jetnet.functions.Case.mcase;

public class Chap2EmailValidation {
    private static Pattern emailPattern = Pattern.compile("^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$");

    private static Effect<String> success = s -> System.out.println("Mail sent to: " + s);
    private static Effect<String> failure = error -> System.err.println("Error message logged: " + error);

    public static void main(String... args) {
        emailChecker.apply("this.is@my.email").bind(success, failure);
        emailChecker.apply(null).bind(success, failure);
        emailChecker.apply("").bind(success, failure);
        emailChecker.apply("john.doe@acme.com").bind(success, failure);
    }

    private static Function<String, Result<String>> emailChecker = s -> match(
        mcase(() -> new Success<>(s)),
        mcase(() -> s == null, () -> new Failure<>("email must not be null")),
        mcase(() -> !emailPattern.matcher(s).matches(), () -> new Failure<>("email is invalid")));
}
