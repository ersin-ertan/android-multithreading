package com.mmsofts.rxjava2;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.observables.GroupedObservable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mms on 11/2/16.
 */

public class GroupObserv {

  public static List<Integer> ints = new ArrayList<>(100);

  static {
    for (int i = 0; i < 100; i++) {
      ints.add(i);
    }
  }

  /**
   * groupBy the mod of 5, thus the values into those who can starting at 0-4 respectively into
   * groups
   *
   * Must reason about the logic that is going on here.
   */
  public static void groupedObserv() {

    Observable.fromIterable(ints)
        // Function<Input, Output>;
        .groupBy((Function<Integer, Integer>) new Function<Integer, Integer>() {
          @Override public Integer apply(Integer integer) throws Exception {
            return integer % 5;
          }
        }).flatMap(new Function<GroupedObservable<Integer, Integer>, ObservableSource<?>>() {
      @Override public ObservableSource<?> apply( // each group is now a stream
          GroupedObservable<Integer, Integer> integerIntegerGroupedObservable) throws Exception {
        return integerIntegerGroupedObservable.toList().toObservable();
      }
    }).toList() // to list is important as it is the Single<List<Object>>
        .subscribe(new Consumer<List<Object>>() {
          @Override public void accept(List<Object> objects) throws Exception {
            for (Object o : objects) {
              System.out.println(o.toString());
            }
          }
        });
  }
}
