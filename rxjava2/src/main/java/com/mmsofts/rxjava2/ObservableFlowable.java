package com.mmsofts.rxjava2;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by mms on 10/31/16.
 */

public class ObservableFlowable {

  /* because observable can cause back pressure, and it's hard to deal with them
  observable is non bcak pressured and flowable is back pressure enabled

  You may use Observable for example:

  http://stackoverflow.com/questions/40323307/observable-vs-flowable-rxjava2

handling GUI events
working with short sequences (less than 1000 elements total)
You may use Flowable for example:

cold and non-timed sources
generator like sources
network and database accessors

  */

  public static List<Integer> ints = new ArrayList<>(1001);

  static {
    for (int i = 1000; i < 2000; i++) {
      ints.add(i);
    }
  }

  /**
   * not a good example
   */
  public static void backpressure() {
    Observable.fromIterable(ints).flatMap((Function<Integer, ObservableSource<?>>) integer -> {
      List<Integer> back = new ArrayList<>(integer);
      for (int i = integer; i > 0; i--) {
        back.add(i);
      }
      return Observable.fromIterable(back);
    }).subscribe(new Consumer<Object>() {
      @Override public void accept(Object o) throws Exception {
        System.out.println(o.toString());
      }
    }, Throwable::printStackTrace, () -> {
      System.out.println("complete");
    });
  }

  public static void bp1() {
    Observable<Integer> os = Observable.interval(500, TimeUnit.MILLISECONDS).range(0, 100_000);
    Observable<Integer> oss = Observable.interval(100, TimeUnit.MILLISECONDS).range(0, 100_000);

    oss.zipWith(os, (integer, integer2) -> integer + integer2)
        .observeOn(Schedulers.computation())
        .subscribeOn(AndroidSchedulers.mainThread())
        .subscribe(System.out::println, Throwable::printStackTrace,
            () -> System.out.println("complete"));
  }

  /*
  http://stackoverflow.com/documentation/rx-java/2341/backpressure/7701/introduction#t=201610300716353419349
   */
  public static void bp2() {

    PublishSubject<Integer> source = PublishSubject.create();
    source.observeOn(Schedulers.computation(), true, 512 * 512)
        // last two params added true, 512 * 512
        // true  indicates if the onError notification may not cut ahead of onNext notification on the other side of the scheduling boundary.
        //asynchronously with an unbounded buffer of configurable "island size" and optionally delays onError notifications
        .subscribe(ObservableFlowable::compute, Throwable::printStackTrace);

    for (int i = 0; i < 1_000_000; i++) {
      source.onNext(i);
    }
  }

  /*
  Not sure whats happening here, I tried to change observable to flowable, and the range to something quite high
  , also tried to change the computation threads buffer size, but what I expect to see is some kind of error
  like a buffer overflow or some values to be skipped as the observers buffers fill and try to make up for
  the variance in emission speed.
   */
  public static void bp3() {
    Flowable.<Integer>range(1, 10_000).zipWith(
        Flowable.interval(100, TimeUnit.MILLISECONDS).<Integer>range(2_001, 30_000),
        (integer, integer2) -> new Integer(integer + integer2))
        .observeOn(Schedulers.computation(), false, 102)
        .subscribeOn(AndroidSchedulers.mainThread())
        .subscribe(System.out::println, Throwable::printStackTrace,
            () -> System.out.println("complete"));
  }

  public static void compute(int v) {
    try {
      Thread.sleep(1_000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    System.out.println(v);
  }
}
