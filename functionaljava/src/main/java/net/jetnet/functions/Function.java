package net.jetnet.functions;

@FunctionalInterface
public interface Function<T, U> {
    U apply(T arg);
}
