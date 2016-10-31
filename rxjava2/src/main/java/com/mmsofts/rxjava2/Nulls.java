package com.mmsofts.rxjava2;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

import static com.mmsofts.rxjava2.MainActivity.LogV;

/**
 * Created by mms on 10/31/16.
 */

public class Nulls {

  // see the new consumer interface
  Consumer<String> consumer = s -> {
    // or
    new Consumer<String>() {
      @Override public void accept(String s) throws Exception {
        // full anon inner
      }
    };
  };

  static void observableObject() {

    Observable<Object> source = Observable.create((ObservableEmitter<Object> emitter) -> {
      System.out.println("Side-effect 1");
      emitter.onNext(Irrelevant.INSTANCE);

      System.out.println("Side-effect 2");
      emitter.onNext(Irrelevant.INSTANCE);

      System.out.println("Side-effect 3");
      emitter.onNext(Irrelevant.INSTANCE);
    });

    source.subscribeOn(Schedulers.newThread())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(e -> LogV(e.toString()), Throwable::printStackTrace);
  }

  void nulls() {

    Observable.just(null); // @NonNull annotated, will yield NullPointerException right away
    Single.just(null);

    // because of the null return will return to the throwable
    // anon inner, lambda, method ref, with jackOptions.enabled = true in module build.gradle
    Observable.<String>fromCallable(() -> null).subscribe(new Consumer<String>() {
      @Override public void accept(String s) throws Exception {
        LogV(s);
      }
    }, throwable -> LogV(throwable), MainActivity::onComplete);
  }

  // Observable<Void> does not emit values terminates normally or with exception
  enum Irrelevant {
    INSTANCE;
  }
}
