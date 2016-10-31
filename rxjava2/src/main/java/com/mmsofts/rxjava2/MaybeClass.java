package com.mmsofts.rxjava2;

import io.reactivex.Maybe;
import io.reactivex.MaybeObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by mms on 10/31/16.
 */

public class MaybeClass {

  // union of single and completable, capture emission of 0 or 1 or error
  // accompanied by MaybeSource
  static void maybe() {

    Maybe<String> maybe = Maybe.just("test");
    Maybe<String> maybeComplete = Maybe.fromAction(() -> {
    });

    maybe.subscribeOn(Schedulers.newThread())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new MaybeObserver<String>() {
          @Override public void onSubscribe(Disposable d) {

          }

          @Override public void onSuccess(String value) {

          }

          @Override public void onError(Throwable e) {

          }

          @Override public void onComplete() {

          }
        });

    Disposable subscribe = maybe.subscribeOn(Schedulers.newThread())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(MainActivity::LogV, // runs
            MainActivity::LogV, // doesn't run
            new Action() { // onComplete
              @Override public void run() throws Exception {
                MainActivity.LogV("test complete"); // doesn't run
              }
            });

    maybeComplete.subscribeOn(Schedulers.newThread())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(MainActivity::LogV, // doesn't run
            MainActivity::LogV, // doesn't run
            new Action() { // onComplete
              @Override public void run() throws Exception {
                MainActivity.LogV("maybe complete"); // runs
              }
            });
  }

  //MaybeSource<String> maybeSource = ;
}
