package ru.spbau.mit;

public interface Function1<R, T> {
    R apply(T element);

    default <U> Function1<U,T> compose(Function1<U, ? super R> g) {
        return element -> g.apply(apply(element));
    }
}
