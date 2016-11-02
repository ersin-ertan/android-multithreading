package com.mmsofts.rxjava2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

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

  }
}
