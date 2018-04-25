package ru.spbau.mit;

public interface Predicate<T> extends Function1<Boolean, T>{

    Predicate<Object> ALWAYS_TRUE = element -> true;
    Predicate<Object> ALWAYS_FALSE = element -> false;


    default Predicate<T> or(Predicate<? super T> p) {
        return element -> apply(element) || p.apply(element);
    }

    default Predicate<T> and(Predicate<? super T> p) {
        return element -> apply(element) && p.apply(element);
    }

    default Predicate<T> not() {
        return element -> !apply(element);
    }
}
