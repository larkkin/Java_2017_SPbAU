package ru.spbau.mit;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Collections {

    // принимает f и a, применяет f к каждому элементу a_i и возвращает список [f(a_1), ..., f(a_n)]
    public static <R, T> List<R> map(Function1<R, ? super T> f, Iterable<T> container) {
    List<R> res = new ArrayList<>();
        for (T i: container) {
            res.add(f.apply(i));
        }
        return res;
    }

    //принимает p и a, возвращает список, содержащий элементы a_i, на которых p(a_i) == true
    public static <T> List<T> filter(Predicate<? super T> pred, Iterable<T> container) {
        List<T> res = new ArrayList<>();
        for (T i: container) {
            if (pred.apply(i))
            {
                res.add(i);
            }
        }
        return res;
    }

    //принимает p и a, возвращает список с началом a до первого элемента a_i, для которого p(a_i) == false
    public static <T> List<T> takeWhile(Predicate<? super T> pred, Iterable<T> container) {
        List<T> res = new ArrayList<>();
        for (T i: container) {
            if (!(pred.apply(i))) {
                break;
            }
            res.add(i);
        }
        return res;


    }

    //takeWhile, только для p(a_i) == true
    public static <T> List<T> takeUnless(Predicate<? super T> pred, Iterable<T> container) {
        return takeWhile(pred.not(), container);
    }

    public static <T1, T2> T1 foldl(T1 init,
                                    Function2<? extends T1, ? super T1, ? super T2> fun,
                                    Iterable<T2> container) {
        Iterator<T2> it = container.iterator();
        T1 res = init;
        while (it.hasNext()) {
            res = fun.apply(res, it.next());
        }
        return res;
    }

    public static <T1, T2> T1 foldr(T1 init,
                                    Function2<? extends T1, ? super T2, ? super T1> fun,
                                    Iterable<T2> container) {
        return foldr(init, fun, container.iterator());
    }

    private static <T1, T2> T1 foldr(T1 init,
                                    Function2<? extends T1, ? super T2, ? super T1> fun,
                                    Iterator<T2> it) {
        if (!it.hasNext()) {
            return init;
        }
        return fun.apply(it.next(), foldr(init, fun, it));
    }




}
