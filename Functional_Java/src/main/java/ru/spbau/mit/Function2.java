package ru.spbau.mit;

public interface Function2<R, T1, T2> {
    R apply(T1 element1, T2 element2);

    default <U> Function2<U, T1, T2> compose(Function1<U, ? super R> g) {
        return (element1, element2) -> g.apply(apply(element1, element2));
    }

    default Function1<R, T2> bind1(T1 element1) {
        return element -> apply(element1, element);
    }

    default Function1<R, T1> bind2(T2 element2) {
        return element -> apply(element, element2);
    }

    default Function1<Function1<R, T2>, T1> curry() {
        return this::bind1;
    }


}
