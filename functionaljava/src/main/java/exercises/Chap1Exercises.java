package exercises;

import net.jetnet.functions.Function;
import net.jetnet.functions.Tuple;

public class Chap1Exercises {
    /*
    2.1: Write the compose method for functions of integer
    2.2: Simplify using lambdas
     */
    public Function<Integer, Integer> compose(Function<Integer, Integer> f1, Function<Integer, Integer> f2) {
        return arg -> f1.apply(f2.apply(arg));
    }

    public void useComposeMethod() {
        compose(square, triple).apply(5);
    }

    /*
    2.3: Define a function for adding two integers
     */
    public Function<Integer, Function<Integer, Integer>> add = a -> b -> a + b;

    /*
    Object notation manner of using curried add function
     */
    public void useAdd() {
        add.apply(2).apply(3);
    }

    /*
    2.4: Write compose as a function rather than a method, this is a HOF. Compose two functions `square` and `triple`.
     */
    private Function<Integer, Integer> square = x -> x * x;
    private Function<Integer, Integer> triple = x -> x * 3;
    private Function<Function<Integer, Integer>,
            Function<Function<Integer, Integer>,
                    Function<Integer, Integer>>> compose = f1 -> f2 -> arg -> f1.apply(f2.apply(arg));

    public void useComposeFunction() {
        compose.apply(square).apply(triple).apply(5);
    }

    /*
    2.5: Write a polymorphic compose function
     */
    //generics can't be applied to fields, so we'll write it in a static method. The method just provides the function,
    //it doesn't take parameters, it's a constant.
    public static <T, U, V> Function<Function<U, V>, Function<Function<T, U>, Function<T, V>>> higherCompose() {
        return f1 -> f2 -> arg -> f1.apply(f2.apply(arg));
    }

    //type inference fails so defaults to <Object> which is not variant with parameterised functions
    public void useHigherCompose() {
        Chap1Exercises.<Integer, Integer, Integer>higherCompose().apply(square).apply(triple).apply(5);
    }

    /*
    2.6: Write a higherAndThen function which composes functions the other way round
     */
    public static <T, U, V> Function<Function<T, U>, Function<Function<U, V>, Function<T, V>>> higherAndThen() {
        return f1 -> f2 -> arg -> f2.apply(f1.apply(arg));
    }

    /*
    2.7: Write a functional method to partially apply a curried function of two arguments to its first argument
     */
    public static <T, U, V> Function<U, V> partialApply(T t, Function<T, Function<U, V>> f) {
        return f.apply(t);
    }

    public void usePartialApply() {
        Function<Integer, Integer> g = Chap1Exercises.partialApply(5, x -> y -> x * y);
        g.apply(3);
    }

    /*
    2.8: Write a functional method to partially apply a curried function of two arguments to its second argument
     */
    public static <T, U, V> Function<T, V> partialAndThen(U u, Function<T, Function<U, V>> f) {
        return t -> f.apply(t).apply(u);
    }

    /*
    2.9: Convert the following method into a curried function
     */
    <A, B, C, D> String func(A a, B b, C c, D d) {
        return String.format("%s, %s, %s, %s", a, b, c, d);
    }

    public static <A, B, C, D> Function<A, Function<B, Function<C, Function<D, String>>>> curried() {
        return a -> b -> c -> d -> String.format("%s, %s, %s, %s", a, b, c, d);
    }

    public void useCurried() {
        Chap1Exercises.curried().apply("a").apply("b").apply("c").apply("d");
    }

    /*
    2.10: Write a method to curry a function of a Tuple<A, B> to C
     */
    public <A, B, C> Function<A, Function<B, C>> curryFunction(Function<Tuple<A, B>, C> f) {
        return a -> b -> f.apply(new Tuple<>(a, b));
    }

    public <A, B, C> Function<B, Function<A, C>> swapArguments(Function<A, Function<B, C>> f) {
        return b -> a -> f.apply(a).apply(b);
    }

    /*
    Write a recursive function for factorials
     */
    //a recursive method
    public int factorialMethod(int n) {
        return n == 0 ? 1 : n * factorialMethod(n - 1);
    }

    //can't self reference a function during definition, so implementation is put in initializer
    public Function<Integer, Integer> factorial;
    {
        factorial = n -> n == 0 ? 1 : n * factorial.apply(n - 1);
    }

    //alternatively can be declared final using `this` like so
    public final Function<Integer, Integer> finalFactorial = n -> n == 0 ? 1 : n * this.factorial.apply(n - 1);
}
