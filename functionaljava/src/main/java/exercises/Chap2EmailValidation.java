package exercises;

import net.jetnet.functions.Effect;
import net.jetnet.functions.Executable;
import net.jetnet.functions.Function;
import net.jetnet.functions.Result;

import java.util.regex.Pattern;

public class Chap2EmailValidation {
    private static Pattern emailPattern = Pattern.compile("^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$");
    private static Function<String, Result<String>> emailChecker = email -> {
        if (email == null) return new Result.Failure<>("email must not be null");
        else if (email.isEmpty()) return new Result.Failure<>("email must not be empty");
        else if (emailPattern.matcher(email).matches()) return new Result.Success<>(email);
        else return new Result.Failure<>("email is invalid");
    };

    private static Effect<String> success = Chap2EmailValidation::sendVerificationMail;
    private static Effect<String> failure = Chap2EmailValidation::logError;

    public static void main(String... args) {
        emailChecker.apply("this.is@my.email").bind(success, failure);
        emailChecker.apply(null).bind(success, failure);
        emailChecker.apply("").bind(success, failure);
        emailChecker.apply("john.doe@acme.com").bind(success, failure);
    }

    private static void logError(String error) {
        System.err.println("Error message logged: " + error);
    }

    private static void sendVerificationMail(String s) {
        System.out.println("Mail sent to: " + s);
    }

}
