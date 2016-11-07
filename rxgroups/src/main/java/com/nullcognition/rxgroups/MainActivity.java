package com.nullcognition.rxgroups;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import com.airbnb.rxgroups.GroupLifecycleManager;
import com.airbnb.rxgroups.ObservableGroup;

public class MainActivity extends AppCompatActivity {

  private static final String OBSERVABLE_TAG = "arbitrary_tag";
  private TextView output;
  //private FloatingActionButton button;
  private GroupLifecycleManager groupLifecycleManager;
  private ObservableGroup observableGroup;
  //private Observable<Long>observable = Observable.interval(1, 1, TimeUnit.SECOND);

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
  }
}
