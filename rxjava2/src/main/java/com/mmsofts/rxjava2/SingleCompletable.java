package com.mmsofts.rxjava2;

import io.reactivex.CompletableObserver;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.disposables.Disposables;
import org.reactivestreams.Subscription;

/**
 * Created by mms on 10/31/16.
 */

public class SingleCompletable {

  // reactive base type to emit onSuccess or onError redesigned from scratch
  // three methods
  SingleObserver<String> singleObserver = new SingleObserver<String>() {
    @Override public void onSubscribe(Disposable d) {
    }

    @Override public void onSuccess(String value) {
    }

    @Override public void onError(Throwable e) {
    }
  };

  // disposable and disposables utility class
  Disposable disposed = Disposables.disposed();
  Disposable empty = Disposables.empty();
  Disposable fromSubscription = Disposables.fromSubscription(new Subscription() {
    @Override public void request(long n) {

    }

    @Override public void cancel() {

    }
  });
  Disposable fromAction = Disposables.fromAction(() -> {/* run */});
  Disposable disposable = new Disposable() {
    @Override public void dispose() {
    }

    @Override public boolean isDisposed() {
      return false;
    }
  };

  // remains largely the same,
  CompletableObserver completableObserver = new CompletableObserver() {
    @Override public void onSubscribe(Disposable d) {

    }

    @Override public void onComplete() {

    }

    @Override public void onError(Throwable e) {

    }
  };
}
