package com.mmsofts.rxjava2;

import io.reactivex.CompletableObserver;
import io.reactivex.CompletableSource;
import io.reactivex.MaybeObserver;
import io.reactivex.MaybeSource;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.SingleObserver;
import io.reactivex.SingleSource;

/**
 * Created by mms on 10/31/16.
 *
 * https://github.com/ReactiveX/RxJava/wiki/What's-different-in-2.0#base-reactive-interfaces
 * come back to this
 */

public class BaseReactiveInterfaces {

  ObservableSource observableSource = new ObservableSource() {
    @Override public void subscribe(Observer observer) {
    }
  };

  SingleSource singleSource = new SingleSource() {
    @Override public void subscribe(SingleObserver observer) {
    }
  };

  CompletableSource completableSource = new CompletableSource() {
    @Override public void subscribe(CompletableObserver cs) {
    }
  };

  MaybeSource maybeSource = new MaybeSource() {
    @Override public void subscribe(MaybeObserver observer) {
    }
  };

  // operators that required reactive base types now accept publisher and xsource
  // no need to wrap them or convert them to flowable
}
