package com.mmsofts.rxjava2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import io.reactivex.Observable;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

  public static List<Integer> ints = new ArrayList<>(100);

  static {
    for (int i = 0; i < 100; i++) {
      ints.add(i);
    }
  }

  static void LogV(Throwable throwable) {
    Log.v("MA", "", throwable);
  }

  static void LogV(String s) {
    Log.v("MA", s);
  }

  static void onComplete() {
  }

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    //Changes.observableObject();
    //MaybeClass.maybe();
    //GroupObserv.groupedObserv();
    //Functionss.f("e");

    //Practice01.group();
    //Practice01.may();
    //ObservableFlowable.backpressure();
    //ObservableFlowable.bp1();
    //ObservableFlowable.bp2();
    //ObservableFlowable.bp3();

    //list().subscribe();

  }

  // @RxLogObservable(RxLogObservable.Scope.EVERYTHING)
  // @RxLogObservable(RxLogObservable.Scope.STREAM)
  // @RxLogObservable(RxLogObservable.Scope.SCHEDULERS)
  // @RxLogObservable(RxLogObservable.Scope.EVENTS)
  // @RxLogObservable(RxLogObservable.Scope.NOTHING)

  // @RxLogObservable // not working for rxjava 2 @see https://github.com/android10/frodo/issues/33
  public Observable<List<Integer>> list() {
    return Observable.just(ints);
  }
}
