package com.mmsofts.rxjava2;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.observables.GroupedObservable;
import io.reactivex.schedulers.Schedulers;
import java.util.ArrayList;
import java.util.List;
import org.reactivestreams.Subscription;

/**
 * Created by mms on 11/3/16.
 */

public class Practice01 {

  public static List<Integer> ints = new ArrayList<>(100);

  static {
    for (int i = 0; i < 100; i++) {
      ints.add(i);
    }
  }

  public static void nulls() {
    Consumer<String> consumer = s -> {
      // is the new action, BiConsumer is Action2
    };
  }

  public static void noValue() {
    Observable.create(emitter -> {

      System.out.println("side effect1");
      emitter.onNext(NULL.OBJECT);

      System.out.println("side effect2");
      emitter.onNext(NULL.OBJECT);
    })
        .subscribeOn(Schedulers.computation())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(nullObject -> {
        });

    Observable.just(null); // null pointer exception right away
    Observable.fromCallable(() -> "S").subscribe(str -> str += "");
  }

  public static void no() {
    Observable.just(null); // npe
    Observable.fromCallable(() -> {
      int a = 3;
      return a + 5;
    }).subscribe(System.out::println, throwable -> {
    });

    Observable.fromCallable(Computation::compute).subscribe(Computation::compute);
    // the first compute is with no input, but the second from subscribe refers to the compute method
    // that takes in a int
  }

  public static int doComputation() {
    return 4;
  }

  public static void flow() {
    Flowable.fromCallable(() -> doComputation()).subscribe(integer -> {
    }, throwable -> {
    }, () -> { // new Action for onComplete
    }, new Consumer<Subscription>() {
      @Override public void accept(Subscription subscription) throws Exception {
        // add to a composite subscription for handling`
      }
    });
  }

  public static void group() {

    // we have the list of ints, now we have group of key 'e', key 'o'
    Observable.fromIterable(ints)
        .groupBy(Practice01::evenOdder)
        .subscribe(characterIntegerGroupedObservable -> {
          // so this method is reached twice, once for key e's and for key o's
          System.out.println(characterIntegerGroupedObservable.toString());
        });

    // toList return a Single<List<GroupedObservable<Integer,Character>>>
    Observable.fromIterable(ints)
        .groupBy(Practice01::evenOdder)
        .toList()
        .subscribe(new Consumer<List<GroupedObservable<Character, Integer>>>() {
          @Override
          public void accept(List<GroupedObservable<Character, Integer>> groupedObservables)
              throws Exception {
            // return the list of 2 grouped observables, the 2 from the above method
            // only called once
            System.out.println(groupedObservables.toString());
          }
        });

    Observable.fromIterable(ints)
        .groupBy(Practice01::evenOdder)
        .toList()
        .toObservable()
        .subscribe(new Consumer<List<GroupedObservable<Character, Integer>>>() {
          @Override
          public void accept(List<GroupedObservable<Character, Integer>> groupedObservables)
              throws Exception {
            // does not do anything with the added observable method
            System.out.println(groupedObservables.toString());
          }
        });

    Observable.fromIterable(ints)
        .groupBy(Practice01::evenOdder)
        .flatMap(new Function<GroupedObservable<Character, Integer>, ObservableSource<?>>() {
          @Override public ObservableSource<?> apply(
              GroupedObservable<Character, Integer> characterIntegerGroupedObservable)
              throws Exception {
            return characterIntegerGroupedObservable;
          }
        })
        .subscribe(new Consumer<Object>() {
          @Override public void accept(Object o) throws Exception {
            o.toString(); // will return 0 o then flatmap the second new Func and return 100 o's
          }
        });

    Observable.fromIterable(ints)
        .groupBy(Practice01::evenOdder)
        .flatMap(new Function<GroupedObservable<Character, Integer>, ObservableSource<?>>() {
          @Override public ObservableSource<?> apply(
              GroupedObservable<Character, Integer> characterIntegerGroupedObservable)
              throws Exception {
            return characterIntegerGroupedObservable;
          }
        })
        .toList()
        .subscribe(new Consumer<List<Object>>() {
          @Override public void accept(List<Object> objects) throws Exception {
            System.out.println(objects); // prints out the numbers 0-99
          }
        });

    Observable.fromIterable(ints)
        .groupBy(Practice01::evenOdder)
        .flatMap(new Function<GroupedObservable<Character, Integer>, ObservableSource<?>>() {
          @Override public ObservableSource<?> apply(
              GroupedObservable<Character, Integer> characterIntegerGroupedObservable)
              throws Exception {
            return characterIntegerGroupedObservable.toList().toObservable();
          }
        })
        .subscribe(new Consumer<Object>() {
          @Override public void accept(Object o) throws Exception {
            System.out.println(o.toString()); // prints out the even numbers list
            // prints out the odd numbers list
          }
        });

    Observable.fromIterable(ints)
        .groupBy(Practice01::evenOdder)
        .flatMap(new Function<GroupedObservable<Character, Integer>, ObservableSource<?>>() {
          @Override public ObservableSource<?> apply(
              GroupedObservable<Character, Integer> characterIntegerGroupedObservable)
              throws Exception {
            return characterIntegerGroupedObservable.toList().toObservable();
          }
        })
        .toList()
        .subscribe(new Consumer<List<Object>>() {
          @Override public void accept(List<Object> objects) throws Exception {
            System.out.println(objects); // list of 2 array lists of the above answers even and odd
          }
        });
  }

  // Predicate<T> A functional interface (callback) that returns true or false for the given input value.

  private static char evenOdder(int integer) {
    return integer % 2 == 0 ? 'e' : 'o'; // instead of having this logic in the groupBy method it is
    // extracted, and a method reference is used instead
  }

  enum NULL {OBJECT;}
}
































