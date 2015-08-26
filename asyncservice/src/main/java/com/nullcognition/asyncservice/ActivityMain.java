package com.nullcognition.asyncservice;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.joanzapata.android.asyncservice.api.annotation.InjectService;
import com.joanzapata.android.asyncservice.api.annotation.OnMessage;
import com.joanzapata.android.asyncservice.api.internal.AsyncService;

import java.lang.ref.WeakReference;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ActivityMain extends Activity{

	@InjectService                 DemoService demoService;
	@Bind(R.id.TextView_getString) TextView    getString;

	@OnClick(R.id.Button_getString) void getString(final View view){
//		A caller only receive the messages originated from its own requests by default
		String s = demoService.getString("passed from activity");
		if(null == s){getString.setText("string is null");}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ButterKnife.bind(this);

		AsyncService.inject(this);

		handler.postDelayed(new Runnable(){
			@Override public void run(){
				demoService.getInt();

			}
		}, 2000);
	}

	private final MyHandler handler = new MyHandler(this);


	private static class MyHandler extends Handler{

		private final WeakReference<ActivityMain> activity;

		public MyHandler(ActivityMain activity){
			this.activity = new WeakReference<ActivityMain>(activity);
		}

		@Override
		public void handleMessage(Message msg){
			ActivityMain activity = this.activity.get();
			if(activity != null){
			}
		}
	}

	@OnMessage public void onFromService(Object result){ // will catch all objects from the service,
		// but if typed ex.String will only catch String results
		getString.setText(result.toString());
	}

//	@OnMessage public void getInt(){demoService.getInt();} // cannot have multiple @OnMessage annotations

	@OnMessage(from = OnMessage.Sender.ALL) public void logger(Object object){
		Toast.makeText(ActivityMain.this, "Logging:" + object.toString(), Toast.LENGTH_SHORT).show();
	}
}

// odd behaviour to investigate, when pushing the button then leaving the app, the toast will trigger
// as expected and also if you enter and exit the app quickly, but if you enter press, exit, enter,
// the handlers request will occur first and block the getString result from populating the text field
// but it will still be seen by the logger. If you do multiple presses they start to get cached and
// will replay an increment amount of times.
