package com.mmsofts.rxjava2;

import io.reactivex.Flowable;

/**
 * Created by mms on 11/2/16.
 */

public class Functionss {

  /**
   * Following java 8 naming conventions, Function is now the proper name and BiFunction, and
   * Function3-9 instead of func
   *
   * FuncN is now Function<Object[]>, R>
   *
   *   if operator required a predicate, no longer use Func1<T, Boolean> but instead
   *   return and primitive returning type of Predicate<T> no autoboxing better inlining
   *
   *   Functionss is the utility class for common function sources and conversions to Function<Object[],R>
   *
   */

  {
    //Functions.actionConsumer();
    //Functions.alwaysFalse();
    //Functions.castFunction();
    //Functions.createArrayList();
    //Functions.futureAction();
    //Functions.identity();
    //Functions.isInstanceOf();
    //Functions.justCallable();
    //Functions.naturalOrder();
    //Functions.notificationOnComplete();
    //Functions.nullSupplier();
    //Functions.timestampWith();
    //Functions.toFunction();
    //Functions.toMapKeyValueSelector();

  }

  public static void f(String input) {
    Flowable.just(input) // input "2" for normal sys out,
        .map(
            Integer::parseInt) // would need a try catch but is now included in the functional interface
        // throwing an exception
        .subscribe(System.out::println, // input "e" for throwable message
            throwable -> System.out.println(throwable.getMessage()));
  }
}
