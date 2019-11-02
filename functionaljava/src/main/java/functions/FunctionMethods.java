package functions;

public class FunctionMethods {
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
    public static <T, U, V> Function<Function<T, U>, Function<Function<V, T>, Function<V, U>>> higherCompose() {
        return f1 -> f2 -> arg -> f1.apply(f2.apply(arg));
    }

    //type inference fails so defaults to <Object> which is not variant with parameterised functions
    public void useHigherCompose() {
        FunctionMethods.<Integer, Integer, Integer>higherCompose().apply(square).apply(triple).apply(5);
    }
}
