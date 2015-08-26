package com.nullcognition.asyncservice;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.joanzapata.android.asyncservice.api.annotation.InjectService;
import com.joanzapata.android.asyncservice.api.annotation.OnMessage;
import com.joanzapata.android.asyncservice.api.internal.AsyncService;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ActivityMain extends Activity{

	@InjectService                 DemoService demoService;
	@Bind(R.id.TextView_getString) TextView    getString;

	@OnClick(R.id.Button_getString) void getString(final View view){
		String s = demoService.getString("passed from activity");
		if(null == s){getString.setText("string is null");}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ButterKnife.bind(this);

		AsyncService.inject(this);
	}

	@OnMessage public void onGetString(String result){
		getString.setText(result);
	}
}
